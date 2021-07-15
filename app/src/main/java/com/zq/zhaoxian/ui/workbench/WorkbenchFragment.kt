package com.zq.zhaoxian.ui.workbench


import com.google.android.material.tabs.TabLayoutMediator
import com.gyf.immersionbar.ktx.immersionBar
import com.zq.base.fragment.BaseLazyFragment
import com.zq.zhaoxian.R
import com.zq.zhaoxian.databinding.AppFragmentWorkbenchBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * 工作台
 */
@AndroidEntryPoint
class WorkbenchFragment @Inject constructor() :
    BaseLazyFragment<WorkbenchViewModel, AppFragmentWorkbenchBinding>() {


    @Inject
    lateinit var fragmentStateAdapter: WorkbenchFragmentStateAdapter


    override val layoutId: Int = R.layout.app_fragment_workbench

    private val tabTitle = arrayOf("未完成","已完成")

    override fun initView() {
        mDataBind.viewPager.adapter = fragmentStateAdapter
        mDataBind.viewPager.isUserInputEnabled = false
        TabLayoutMediator(
            mDataBind.tabLayout,
            mDataBind.viewPager
        ) { tab, position -> // Styling each tab here
            tab.text = tabTitle[position]
        }.attach()

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