package com.zq.base.viewmodel

import android.app.Application


/**
 * @program: zhaoxian
 *
 * @description:
 *
 * @author: 闫世豪
 *
 * @create: 2021-07-05 10:37
 **/

 abstract class BaseAndroidViewModel(private val mApplication: Application) : BaseViewModel() {
    fun <T : Application?> getApplication(): T {
        return mApplication as T
    }
}
