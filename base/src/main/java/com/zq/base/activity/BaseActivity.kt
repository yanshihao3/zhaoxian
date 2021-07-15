package com.zq.base.activity

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
abstract class BaseActivity<VM : BaseViewModel, DB : ViewDataBinding> :
    BaseNoModelActivity<DB>() {
    protected lateinit var mViewModel: VM

    override fun otherInit() {
        super.otherInit()
        performBindView()
        initObserve()
    }


    open val bindingVariable: Int = 0

    /**
     * 绑定viewModel 数据
     */
    private fun performBindView() {
        createViewModel()
        if (bindingVariable > 0) {
            mDataBind.setVariable(bindingVariable, mViewModel)
        }
        mDataBind.executePendingBindings()
    }

    /**
     * 监听当前ViewModel中 showDialog和error的值
     */
    private fun initObserve() {
        mViewModel.defUI.dismissDialog.observe(this, { showContent() })

        mViewModel.defUI.showDialog.observe(this) { showLoading() }

        mViewModel.defUI.toastEvent.observe(this, {
            ToastUtils.show(it)
        })

        mViewModel.defUI.msgEvent.observe(this) {

        }
    }

    private fun createViewModel() {
        val type = javaClass.genericSuperclass
        if (type is ParameterizedType) {
            val tp = type.actualTypeArguments[0]
            val tClass = tp as? Class<VM> ?: BaseViewModel::class.java
            mViewModel = ViewModelProvider(this, ViewModelFactory()).get(tClass) as VM
        }
    }

}