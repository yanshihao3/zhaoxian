package com.zq.base.utils

import android.content.Context
import com.zq.base.R

/**
 * 文件名:    DensityUtil
 * @author yanshihao
 */
object DisplayUtils {
    /**
     * 获取屏幕宽度（像素）
     *
     * @param context 上下文
     * @return px
     */
    @JvmStatic
    fun getWidth(context: Context): Int {
        val dm = context.resources.displayMetrics
        return dm.widthPixels
    }

    /**
     * 获取屏幕高度（像素）
     *
     * @param context 上下文
     * @return px
     */
    @JvmStatic
    fun getHeight(context: Context): Int {
        val dm = context.resources.displayMetrics
        return dm.heightPixels
    }

    /**
     * 获取状态栏的高度
     *
     * @param context 上下文
     * @return px
     */
    @JvmStatic
    fun getStatusBarHeight(context: Context): Int {
        var statusBarHeight = 0
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = context.resources.getDimensionPixelSize(resourceId)
        }
        return statusBarHeight
    }

    /**
     * 获取（ActionBar）的高度
     *
     * @param context 上下文
     * @return px
     */
    @JvmStatic
    fun getActionBarHeight(context: Context): Int {
        val values = context.theme.obtainStyledAttributes(intArrayOf(R.attr.actionBarSize))
        val actionBarHeight = values.getDimensionPixelSize(0, 0)
        values.recycle()
        return actionBarHeight
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    @JvmStatic
    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    @JvmStatic
    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }
}