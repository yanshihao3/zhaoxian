package com.zq.zhaoxian.ui.workbench.appointment


import android.content.Intent
import com.google.android.material.tabs.TabLayout
import com.zq.base.activity.BaseNoModelActivity
import com.zq.zhaoxian.R
import com.zq.zhaoxian.databinding.AppActivityAppointmentBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * 预约申请
 */
@AndroidEntryPoint
class AppointmentActivity : BaseNoModelActivity<AppActivityAppointmentBinding>() {

    override val layoutId: Int = R.layout.app_activity_appointment

    @Inject
    lateinit var fragmentStateAdapter: AppointmentFragmentStateAdapter

    override fun initView() {
        mDataBind.toolbar.setBackOnClickListener {
            finish()
        }
        mDataBind.viewPager.adapter = fragmentStateAdapter
        mDataBind.viewPager.isUserInputEnabled = false
        mDataBind.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.text) {
                    "未完成" -> mDataBind.viewPager.currentItem = 0
                    "已完成" -> mDataBind.viewPager.currentItem = 1
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        mDataBind.btnRequest.setOnClickListener {
            startActivity(Intent(mActivityContext, RequestActivity::class.java))
        }
    }

    override fun initData() {

    }

}