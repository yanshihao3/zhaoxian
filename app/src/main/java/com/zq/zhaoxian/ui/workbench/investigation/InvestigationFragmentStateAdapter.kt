package com.zq.zhaoxian.ui.workbench.investigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import javax.inject.Inject

class InvestigationFragmentStateAdapter @Inject constructor(activity: FragmentActivity) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2


    override fun createFragment(position: Int): Fragment {
        return if (position == 0)
            InvestigationFragment.newInstance("")
        else InvestigationFragment.newInstance("")
    }
}