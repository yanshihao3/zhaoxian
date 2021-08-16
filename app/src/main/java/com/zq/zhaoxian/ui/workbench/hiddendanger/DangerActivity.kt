package com.zq.zhaoxian.ui.workbench.hiddendanger

import com.google.android.material.tabs.TabLayoutMediator
import com.zq.base.activity.BaseNoModelActivity
import com.zq.zhaoxian.R
import com.zq.zhaoxian.databinding.AppActivityDangerBinding
import com.zq.zhaoxian.ui.workbench.WorkbenchFragmentStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * 排查任务界面
 */
@AndroidEntryPoint
class DangerActivity : BaseNoModelActivity<AppActivityDangerBinding>() {

    @Inject
    lateinit var fragmentStateAdapter: WorkbenchFragmentStateAdapter


    private val tabTitle = arrayOf("未完成", "已完成")

    override fun initView() {
        mDataBind.toolbar.setBackOnClickListener {
            finish()
        }
        mDataBind.viewPager.adapter = fragmentStateAdapter
        mDataBind.viewPager.isUserInputEnabled = false
        TabLayoutMediator(
            mDataBind.tabLayout,
            mDataBind.viewPager
        ) { tab, position -> // Styling each tab here
            tab.text = tabTitle[position]
        }.attach()

        val tab = intent.getIntExtra("tab", -1)
        if (tab != -1) switchTab(tab)
    }


    private fun switchTab(type: Int) {
        mDataBind.tabLayout.getTabAt(type)?.select()
    }

    override fun initData() {

    }

    override val layoutId: Int
        get() = R.layout.app_activity_danger
}