package com.zq.base.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.hjq.toast.ToastUtils
import com.zq.base.viewmodel.BaseViewModel
import com.zq.base.viewmodel.ViewModelFactory
import java.lang.reflect.ParameterizedType

/**
 * @program: mvvm
 * @description:
 * @author: 闫世豪
 * @create: 2021-03-03 17:34
 */
abstract class BaseFragment<VM : BaseViewModel, DB : ViewDataBinding> :
    BaseNoModelFragment<DB>() {
    private lateinit var mViewModel: VM

    /**
     * 获取dataBinding 的 BR id
     *
     * @return
     */
    open val bindingVariable: Int = 0

    protected fun getViewModel() = mViewModel

    override fun initDataBinding(
        inflater: LayoutInflater?,
        @LayoutRes layoutId: Int,
        container: ViewGroup?
    ) {
        super.initDataBinding(inflater, layoutId, container)
        getDataBind().lifecycleOwner = this
        performBindView()
        initObserve()
    }

    /**
     * 绑定viewModel 数据
     */
    private fun performBindView() {
        createViewModel()
        if (bindingVariable > 0) {
            getDataBind().setVariable(bindingVariable, mViewModel)
        }
        getDataBind().executePendingBindings()
    }

    /**
     * 监听当前ViewModel中 showDialog和error的值
     */
    private fun initObserve() {
        if (!::mViewModel.isInitialized) return
        mViewModel.defUI.dismissDialog.observe(this, { showContent() })

        mViewModel.defUI.showDialog.observe(this) { showLoading() }

        mViewModel.defUI.toastEvent.observe(this, {
            ToastUtils.show(it)
        })

        mViewModel.defUI.msgEvent.observe(this) {

        }

    }

    /**
     * 是否和 Activity 共享 ViewModel,默认不共享
     * Fragment 要和宿主 Activity 的泛型是同一个 ViewModel
     */
    open fun isShareVM(): Boolean = false

    private fun createViewModel() {
        val type = javaClass.genericSuperclass
        if (type is ParameterizedType) {
            val tp = type.actualTypeArguments[0]
            val tClass = tp as? Class<VM> ?: BaseViewModel::class.java
            val viewModelStore =
                if (isShareVM()) requireActivity().viewModelStore else this.viewModelStore
            mViewModel = ViewModelProvider(viewModelStore, ViewModelFactory()).get(tClass) as VM
        }
    }

}