package com.zq.base.baseinterface

/**
 * @program: mvvm
 * @description:
 * @author: 闫世豪
 * @create: 2021-03-03 17:34
 */
interface IBasePagingView : IBaseView {
    /**
     * 加载更多失败
     *
     * @param message
     */
    fun onLoadMoreFailure(message: String)

    /**
     * 加载更多没有了
     */
    fun onLoadMoreEmpty()
}