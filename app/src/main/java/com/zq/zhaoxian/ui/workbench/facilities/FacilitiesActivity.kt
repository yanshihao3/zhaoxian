package com.zq.zhaoxian.ui.workbench.facilities

import com.google.android.material.tabs.TabLayout
import com.zq.base.activity.BaseNoModelActivity
import com.zq.zhaoxian.R
import com.zq.zhaoxian.databinding.AppActivityFacilitiesBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * 设施点检
 */
@AndroidEntryPoint
class FacilitiesActivity : BaseNoModelActivity<AppActivityFacilitiesBinding>() {

    override val layoutId: Int = R.layout.app_activity_facilities
    val dataBinding by lazy {
        getDataBind()
    }
    @Inject
    lateinit var fragmentStateAdapter: FacilitiesFragmentStateAdapter

    override fun initView() {
        dataBinding.toolbar.setBackOnClickListener {
            finish()
        }
        dataBinding.viewPager.adapter = fragmentStateAdapter
        dataBinding.viewPager.isUserInputEnabled = false
        dataBinding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.text) {
                    "未完成" -> dataBinding.viewPager.currentItem = 0
                    "已完成" -> dataBinding.viewPager.currentItem = 1
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