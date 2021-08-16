package com.zq.zhaoxian.ui.workbench.alarm

import com.google.android.material.tabs.TabLayoutMediator
import com.zq.base.activity.BaseNoModelActivity
import com.zq.zhaoxian.R
import com.zq.zhaoxian.databinding.AppActivityAlarmWorkBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * @program: zhaoxian
 * @description: 告警任务工单界面
 * @author: 闫世豪
 * @create: 2021-08-16 11:17
 */
@AndroidEntryPoint
class AlarmWorkActivity : BaseNoModelActivity<AppActivityAlarmWorkBinding>() {
    override val layoutId: Int
        get() = R.layout.app_activity_alarm_work

    @Inject
    lateinit var fragmentStateAdapter: AlarmFragmentStateAdapter


    private val tabTitle = arrayOf("未完成", "已完成")
    val dataBinding by lazy {
        getDataBind()
    }
    override fun initView() {
        dataBinding.toolbar.setBackOnClickListener {
            finish()
        }
        dataBinding.viewPager.adapter = fragmentStateAdapter
        dataBinding.viewPager.isUserInputEnabled = false
        TabLayoutMediator(
            dataBinding.tabLayout,
            dataBinding.viewPager
        ) { tab, position -> // Styling each tab here
            tab.text = tabTitle[position]
        }.attach()

        val tab = intent.getIntExtra("tab", -1)
        if (tab != -1) switchTab(tab)
    }

    private fun switchTab(type: Int) {
        dataBinding.tabLayout.getTabAt(type)?.select()
    }

    override fun initData() {

    }

}