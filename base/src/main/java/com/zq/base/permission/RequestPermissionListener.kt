package com.zq.base.permission

interface RequestPermissionListener {
    //权限通过后的回调方法
    fun agreeAllPermission()

    //权限部分通过后的回调方法
    fun agreePermission()

    //权限获取失败
    fun onDenied()
}