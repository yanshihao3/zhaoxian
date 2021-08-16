package com.zq.zhaoxian.ui.workbench


import android.content.Intent
import com.gyf.immersionbar.ktx.immersionBar
import com.zq.base.fragment.BaseLazyFragment
import com.zq.zhaoxian.R
import com.zq.zhaoxian.databinding.AppFragmentWorkbenchBinding
import com.zq.zhaoxian.ui.workbench.alarm.AlarmWorkActivity
import com.zq.zhaoxian.ui.workbench.hiddendanger.DangerActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * 工作台
 */
@AndroidEntryPoint
class WorkbenchFragment @Inject constructor() :
    BaseLazyFragment<WorkbenchViewModel, AppFragmentWorkbenchBinding>() {


    override val layoutId: Int = R.layout.app_fragment_workbench


    override fun initView() {
        mDataBind.alarm.setOnClickListener {
            startActivity(Intent(mContext, AlarmWorkActivity::class.java))
        }

        mDataBind.work.setOnClickListener {
            startActivity(Intent(mContext, DangerActivity::class.java))
        }

    }

    override fun initData() {

    }

    override fun onFragmentFirstVisible() {

    }


    override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar {
            titleBar(mDataBind.toolbar)
            transparentStatusBar()
            statusBarDarkFont(true, 0.2f)
        }
    }
}