package com.zq.zhaoxian.ui.workbench.dispatch

import com.zq.base.activity.BaseNoModelActivity
import com.zq.zhaoxian.R
import com.zq.zhaoxian.databinding.AppActivitySituationDetailsBinding

/**
 * 事态详情界面  -不可编辑界面
 */
class SituationDetailsActivity : BaseNoModelActivity<AppActivitySituationDetailsBinding>() {
    override val layoutId: Int = R.layout.app_activity_situation_details


    override fun initView() {
        getDataBind().toolbar.setBackOnClickListener {
            finish()
        }
    }

    override fun initData() {

    }
}