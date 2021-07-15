package com.zq.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.zq.base.viewmodel.BaseViewModel

/**
 * 懒加载的fragment （viewpager 中 嵌套fragment 情况）
 *
 * @author
 * @package
 */
abstract class BaseLazyFragment<VM : BaseViewModel, DB : ViewDataBinding> :
    BaseFragment<VM, DB>() {
    //fragment 生命周期：
    // onAttach -> onCreate -> onCreatedView -> onActivityCreated -> onStart -> onResume -> onPause -> onStop -> onDestroyView -> onDestroy -> onDetach
    //对于 ViewPager + Fragment 的实现我们需要关注的几个生命周期有：
    //onCreatedView + onActivityCreated + onResume + onPause + onDestroyView
    private var isViewCreated = false
    private var isSupportVisible = false
    private var mIsFirstVisible = true
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        isViewCreated = true
        //初始化的时候，判断当前fragment可见状态
        //todo, isHidden()什么时候调用？原理
        if (!isHidden && userVisibleHint) {
            dispatchUserVisibleHint(true)
        }
        return view
    }

    //修改fragment的可见性
    //setUserVisibleHint 被调用有两种情况：1） 在切换tab的时候，会先于所有fragment的其他生命周期，先调用这个函数，可以看log
    //2）对于之前已经调用过setUserVisibleHint 方法的fragment后，让fragment从可见到不可见之间状态的变化
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        //  对于情况1）不予处理，用 isViewCreated 进行判断，如果isViewCreated false，说明它没有被创建
        if (isViewCreated) {
            //对于情况2）要分情况考虑，如果是不可见->可见是下面的情况 2.1），如果是可见->不可见是下面的情况2.2）
            //对于2.1）我们需要如何判断呢？首先必须是可见的（isVisibleToUser 为true）而且只有当可见状态进行改变的时候才需要切换，否则会出现反复调用的情况
            //从而导致事件分发带来的多次更新
            if (isVisibleToUser && !isSupportVisible) {
                dispatchUserVisibleHint(true)
            } else if (!isVisibleToUser && isSupportVisible) {
                dispatchUserVisibleHint(false)
            }
        }
    }

    /**
     * 统一处理用户可见信息分发
     *
     * @param isVisible
     */
    private fun dispatchUserVisibleHint(isVisible: Boolean) {
        //事实上作为父 Fragment 的 BottomTabFragment2 并没有分发可见事件，
        // 他通过 getUserVisibleHint() 得到的结果为 false，首先我想到的
        // 是能在负责分发事件的方法中判断一下当前父 fragment 是否可见，
        // 如果父 fragment 不可见我们就不进行可见事件的分发
        if (isVisible && isParentInvisible) {
            return
        }
        //为了代码严谨
        if (isSupportVisible == isVisible) {
            return
        }
        isSupportVisible = isVisible
        if (isVisible) {
            if (mIsFirstVisible) {
                mIsFirstVisible = false
                onFragmentFirstVisible()
            }
            onFragmentResume()
            //在双重ViewPager嵌套的情况下，第一次滑到Frgment 嵌套ViewPager(fragment)的场景的时候
            //此时只会加载外层Fragment的数据，而不会加载内嵌viewPager中的fragment的数据，因此，我们
            //需要在此增加一个当外层Fragment可见的时候，分发可见事件给自己内嵌的所有Fragment显示
            dispatchChildVisibleState(true)
        } else {
            onFragmentPause()
            dispatchChildVisibleState(false)
        }
    }

    private val isParentInvisible: Boolean
        get() {
            val parentFragment = parentFragment
            if (parentFragment is BaseLazyFragment<*, *>) {
                return !parentFragment.isSupportVisible
            }
            return false
        }

    private fun dispatchChildVisibleState(visible: Boolean) {
        val fragmentManager = childFragmentManager
        val fragments = fragmentManager.fragments
        if (fragments != null) {
            for (fragment in fragments) {
                if (fragment is BaseLazyFragment<*, *> &&
                    !fragment.isHidden() &&
                    fragment.userVisibleHint
                ) {
                    fragment.dispatchUserVisibleHint(visible)
                }
            }
        }
    }

    /**
     * 用FragmentTransaction来控制fragment的hide和show时，
     * 那么这个方法就会被调用。每当你对某个Fragment使用hide
     * 或者是show的时候，那么这个Fragment就会自动调用这个方法。
     * https://blog.csdn.net/u013278099/article/details/72869175
     *
     * @param hidden
     */
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden) {
            dispatchUserVisibleHint(false)
        } else {
            dispatchUserVisibleHint(true)
        }
    }

    protected abstract fun onFragmentFirstVisible()
    private fun onFragmentResume() {}
    private fun onFragmentPause() {}
    override fun onResume() {
        super.onResume()

        //在滑动或者跳转的过程中，第一次创建fragment的时候均会调用onResume方法，类似于在tab1 滑到tab2，此时tab3会缓存，这个时候会调用tab3 fragment的
        //onResume，所以，此时是不需要去调用 dispatchUserVisibleHint(true)的，因而出现了下面的if
        if (!mIsFirstVisible) {
            //由于Activit1 中如果有多个fragment，然后从Activity1 跳转到Activity2，此时会有多个fragment会在activity1缓存，此时，如果再从activity2跳转回
            //activity1，这个时候会将所有的缓存的fragment进行onResume生命周期的重复，这个时候我们无需对所有缓存的fragnment 调用dispatchUserVisibleHint(true)
            //我们只需要对可见的fragment进行加载，因此就有下面的if
            if (!isHidden && !isSupportVisible && userVisibleHint) {
                dispatchUserVisibleHint(true)
            }
        }
    }

    /**
     * 只有当当前页面由可见状态转变到不可见状态时才需要调用 dispatchUserVisibleHint
     * currentVisibleState && getUserVisibleHint() 能够限定是当前可见的 Fragment
     * 当前 Fragment 包含子 Fragment 的时候 dispatchUserVisibleHint 内部本身就会通知子 Fragment 不可见
     * 子 fragment 走到这里的时候自身又会调用一遍
     */
    override fun onPause() {
        super.onPause()
        if (isSupportVisible && userVisibleHint) {
            dispatchUserVisibleHint(false)
        }
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isViewCreated = false
        mIsFirstVisible = false
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()
    }

    companion object {
        private const val TAG = "LazyFragment"
    }
}