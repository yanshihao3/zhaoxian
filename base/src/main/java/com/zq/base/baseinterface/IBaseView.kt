package com.zq.base.baseinterface

/**
 * @program: mvvm
 * @description:
 * @author: 闫世豪
 * @create: 2021-03-03 17:34
 */
interface IBaseView {
    /**
     * 显示内容
     */
    fun showContent()

    /**
     *显示loading
     */
    fun showLoading()

    /**
     * 刷新为null
     */
    fun onRefreshEmpty()

    /**
     * 刷新失败
     *
     * @param message
     */
    fun onRefreshFailure(message: String)
}