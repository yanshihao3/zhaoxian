package com.zq.zhaoxian.ui.workbench.facilities

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import javax.inject.Inject

class FacilitiesFragmentStateAdapter @Inject constructor(activity: FragmentActivity) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2


    override fun createFragment(position: Int): Fragment {
        return if (position == 0)
            FacilitiesFragment.newInstance("")
        else FacilitiesFragment.newInstance("")
    }
}