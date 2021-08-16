package com.zq.zhaoxian.ui.workbench.alarm

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import javax.inject.Inject

/**
 * @program: zhaoxian
 *
 * @description:
 *
 * @author: 闫世豪
 *
 * @create: 2021-08-16 13:39
 **/
class AlarmFragmentStateAdapter @Inject constructor(activity: FragmentActivity) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2


    override fun createFragment(position: Int): Fragment {
        return AlarmWorkFragment.newInstance(position.toString())
    }
}