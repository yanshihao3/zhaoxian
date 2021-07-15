package com.zq.base.fragment

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.gyf.immersionbar.components.SimpleImmersionOwner
import com.gyf.immersionbar.components.SimpleImmersionProxy
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.zq.base.baseinterface.IBasePagingView
import com.zq.base.loadsir.EmptyCallback
import com.zq.base.loadsir.ErrorCallback
import com.zq.base.loadsir.LoadingCallback
import kotlin.math.log

/**
 * @program: mvvm
 * @description:
 * @author: 闫世豪
 * @create: 2021-03-03 17:34
 */
abstract class BaseNoModelFragment<DB : ViewDataBinding> : Fragment(), IBasePagingView,
    SimpleImmersionOwner {
    private var mLoadService: LoadService<*>? = null
    protected lateinit var mDataBind: DB
    protected var mContext: Context? = null
    private var mActivity: FragmentActivity? = null
    private val mSimpleImmersionProxy by lazy {
        SimpleImmersionProxy(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        mActivity = activity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initDataBinding(inflater, layoutId, container)
        return mDataBind.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mSimpleImmersionProxy.onActivityCreated(savedInstanceState)
        initView()
        initData()
    }

    /**
     * 初始化DataBinding
     */
    protected open fun initDataBinding(
        inflater: LayoutInflater?,
        @LayoutRes layoutId: Int,
        container: ViewGroup?
    ) {
        mDataBind = DataBindingUtil.inflate(inflater!!, layoutId, container, false)
    }

    /**
     * 初始化要加载的布局资源ID
     */
    @get:LayoutRes
    protected abstract val layoutId: Int

    /**
     * 初始化视图
     */
    protected abstract fun initView()

    /**
     * 初始化数据
     */
    protected abstract fun initData()
    override fun onDestroy() {
        super.onDestroy()
        mDataBind.unbind()
        mContext = null
        mActivity = null
        mSimpleImmersionProxy.onDestroy()

    }

    override fun onLoadMoreFailure(message: String) {}

    override fun onLoadMoreEmpty() {}

    override fun onRefreshEmpty() {
        if (mLoadService != null) {
            mLoadService!!.showCallback(EmptyCallback::class.java)
        }
    }

    override fun onRefreshFailure(message: String) {
        if (mLoadService != null) {
            mLoadService!!.showCallback(ErrorCallback::class.java)
        }
    }

    override fun showLoading() {
        if (mLoadService != null) {
            mLoadService!!.showCallback(LoadingCallback::class.java)
        }
    }

    override fun showContent() {
        if (mLoadService != null) {
            mLoadService!!.showSuccess()
        }
    }

    //设置loadSir
    fun setLoadSir(view: View?) {
        mLoadService = LoadSir.getDefault().register(view) { onRetryBtnClick() }
    }

    /**
     * 点击按钮重试
     */
    open fun onRetryBtnClick() {

    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        mSimpleImmersionProxy.isUserVisibleHint = isVisibleToUser
    }


    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        mSimpleImmersionProxy.onHiddenChanged(hidden);

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        mSimpleImmersionProxy.onConfigurationChanged(newConfig)
    }

    override fun initImmersionBar() {

    }

    override fun immersionBarEnabled(): Boolean = true
}