package com.zq.zhaoxian.viewmodel

import com.zq.base.BaseApplication
import com.zq.base.viewmodel.BaseViewModel
import com.zq.zhaoxian.http.HomeNetWork

/**
 * @program: zhaoxian
 *
 * @description:
 *
 * @author: 闫世豪
 *
 * @create: 2021-06-25 18:39
 **/
abstract class BaseAppViewModel : BaseViewModel() {


    fun getToken() {
//        launchGo({
//            BaseApplication.access_token = HomeNetWork.getInstance().getToken().access_token
//        })
    }


}