package com.zq.zhaoxian.ui.my

import com.zq.base.activity.BaseNoModelActivity
import com.zq.zhaoxian.R
import com.zq.zhaoxian.databinding.AppActivityAboutBinding

/**
 * 关于我们
 */
class AboutActivity : BaseNoModelActivity<AppActivityAboutBinding>() {


    override val layoutId: Int = R.layout.app_activity_about

    override fun initView() {
        mDataBind.toolbar.setBackOnClickListener {
            finish()
        }
    }

    override fun initData() {
    }
}