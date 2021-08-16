package com.zq.zhaoxian.ui.workbench.notice


import com.google.android.material.tabs.TabLayout
import com.zq.base.activity.BaseNoModelActivity
import com.zq.zhaoxian.R
import com.zq.zhaoxian.databinding.AppActivityNoticeBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NoticeActivity : BaseNoModelActivity<AppActivityNoticeBinding>() {
    override val layoutId: Int = R.layout.app_activity_notice

    @Inject
    lateinit var fragmentStateAdapter: NoticeFragmentStateAdapter
    val dataBinding by lazy {
        getDataBind()
    }
    override fun initView() {
        dataBinding.toolbar.setBackOnClickListener {
            finish()
        }
        dataBinding.viewPager.adapter = fragmentStateAdapter
        dataBinding.viewPager.isUserInputEnabled = false
        dataBinding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.text) {
                    "待阅读" -> dataBinding.viewPager.currentItem = 0
                    "已阅读" -> dataBinding.viewPager.currentItem = 1
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }

    override fun initData() {

    }

}