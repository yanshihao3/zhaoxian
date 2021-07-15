package com.zq.zhaoxian.ui.workbench.notice

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
 * @create: 2021-06-25 15:06
 **/
class NoticeFragmentStateAdapter @Inject constructor(activity: FragmentActivity) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2


    override fun createFragment(position: Int): Fragment {
        return if (position == 0)
            NoticeFragment.newInstance("0")
        else NoticeFragment.newInstance("1")
    }
}