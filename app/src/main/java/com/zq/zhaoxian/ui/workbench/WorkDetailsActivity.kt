package com.zq.zhaoxian.ui.workbench


import com.zq.base.activity.BaseNoModelActivity
import com.zq.zhaoxian.R
import com.zq.zhaoxian.databinding.AppActivityWorkDetailsBinding

/**
 * 任务详情
 */
class WorkDetailsActivity : BaseNoModelActivity<AppActivityWorkDetailsBinding>() {


    override val layoutId: Int = R.layout.app_activity_work_details

    override fun initView() {
        getDataBind().toolbar.setBackOnClickListener {
            finish()
        }
    }

    override fun initData() {
    }
}