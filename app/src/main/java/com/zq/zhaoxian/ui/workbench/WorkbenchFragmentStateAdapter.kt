package com.zq.zhaoxian.ui.workbench

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.zq.zhaoxian.ui.workbench.hiddendanger.DangerFragment
import javax.inject.Inject

/**
 * @program: zhaoxian
 *
 * @description:
 *
 * @author: 闫世豪
 *
 * @create: 2021-06-30 10:41
 **/
class WorkbenchFragmentStateAdapter @Inject constructor(activity: FragmentActivity) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2


    override fun createFragment(position: Int) = when (position) {
        0 -> DangerFragment.newInstance("0")
        1 -> DangerFragment.newInstance("1")
        else -> DangerFragment.newInstance("0")
    }
}
