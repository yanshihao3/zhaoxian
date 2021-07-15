package com.zq.zhaoxian.ui.workbench.dispatch

import com.zq.base.activity.BaseNoModelActivity
import com.zq.zhaoxian.R
import com.zq.zhaoxian.databinding.AppActivitySituationEditBinding

class SituationEditActivity : BaseNoModelActivity<AppActivitySituationEditBinding>() {


    override val layoutId: Int = R.layout.app_activity_situation_edit

    override fun initView() {
        mDataBind.toolbar.setBackOnClickListener {
            finish()
        }
    }

    override fun initData() {

    }
}