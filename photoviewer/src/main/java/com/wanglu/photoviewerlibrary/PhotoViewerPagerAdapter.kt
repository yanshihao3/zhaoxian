package com.wanglu.photoviewerlibrary

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class PhotoViewerPagerAdapter(
    private var mData: MutableList<PhotoViewerFragment>,
    fragmentManager: FragmentManager
) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): androidx.fragment.app.Fragment {
        return mData[position]
    }

    override fun getCount(): Int {
        return mData.size
    }

}
