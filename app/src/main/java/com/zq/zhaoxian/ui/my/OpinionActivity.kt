package com.zq.zhaoxian.ui.my


import androidx.lifecycle.lifecycleScope
import com.hjq.toast.ToastUtils
import com.zq.base.activity.BaseNoModelActivity
import com.zq.zhaoxian.R
import com.zq.zhaoxian.databinding.AppActivityOpinionBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

/**
 * 意见反馈界面
 */
@AndroidEntryPoint
class OpinionActivity : BaseNoModelActivity<AppActivityOpinionBinding>() {

    @Inject
    lateinit var commitDialogFragment: CommitDialogFragment
    override val layoutId: Int = R.layout.app_activity_opinion
    val dataBinding by lazy {
        getDataBind()
    }
    override fun initView() {
        dataBinding.toolbar.setBackOnClickListener {
            finish()
        }
        commitDialogFragment.setCanceledOnTouchOutside(false)
        dataBinding.btnCommit.setOnClickListener {
            commit()
        }
    }

    override fun initData() {
    }

    private fun commit() {

        lifecycleScope.launch {
            commitDialogFragment.show(supportFragmentManager, "")
            withContext(Dispatchers.IO) {
                delay(2000)
            }
            commitDialogFragment.dismiss()
            ToastUtils.show("提交成功")
        }

    }
}

