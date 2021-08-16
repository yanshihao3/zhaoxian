package com.zq.base.activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.gyf.immersionbar.ktx.immersionBar
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.zq.base.baseinterface.IBaseView
import com.zq.base.loadsir.EmptyCallback
import com.zq.base.loadsir.ErrorCallback
import com.zq.base.loadsir.LoadingCallback

/**
 * @program: mvvm
 *
 * @description:
 *
 * @author: 闫世豪
 *
 * @create: 2021-03-03 17:34
 */
abstract class BaseNoModelActivity<DB : ViewDataBinding> : AppCompatActivity(), IBaseView {
    private var mLoadService: LoadService<*>? = null
    protected var mDataBinding: DB? = null
    protected lateinit var mActivityContext: Activity

    @get:LayoutRes
    protected abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beforeSetContentView()
        mDataBinding = DataBindingUtil.setContentView(this, layoutId)
        initStatusBar()
        mDataBinding!!.lifecycleOwner = this
        mActivityContext = this
        otherInit()
        initView()
        initData()
    }

    protected fun getDataBind() = mDataBinding!!

    /**
     * 初始化视图
     */
    protected abstract fun initView()

    /**
     * 初始化数据
     */
    protected abstract fun initData()


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
    fun setLoadSir(view: View) {
        mLoadService = LoadSir.getDefault().register(view) { onRetryBtnClick() }
    }

    override fun onDestroy() {
        super.onDestroy()
        mDataBinding!!.unbind()
        mDataBinding = null
        if (mLoadService != null) {
            mLoadService = null
        }
    }

    /**
     * 加载失败重试事件
     */
    open fun onRetryBtnClick() {

    }

    /**
     * 其他需要做的事情
     */
    open fun otherInit() {

    }

    /**
     * 其他需要做的事情
     */
    open fun beforeSetContentView() {

    }

    /**
     * 默认的状态栏 ，不需要可以重写
     */

    open fun initStatusBar() {
        immersionBar {
            keyboardEnable(false)
            transparentStatusBar()
            statusBarDarkFont(true, 0.2f)
        }
    }

}