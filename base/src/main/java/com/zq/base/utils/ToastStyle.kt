package com.zq.base.utils

import android.view.Gravity
import com.hjq.toast.style.WhiteToastStyle

/**
 * @program: mvvm
 *
 * @description:
 *
 * @author: 闫世豪
 *
 * @create: 2021-03-05 17:33
 **/
class ToastStyle : WhiteToastStyle() {
    override fun getGravity(): Int {
        return Gravity.BOTTOM
    }

    override fun getXOffset(): Int {
        return 0
    }

    override fun getYOffset(): Int {
        return 16
    }
}