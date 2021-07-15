package com.zq.base.network

data class BaseResult<T>(
    val resMsg: String,
    val resCode: Int,
    val result: T
) : IBaseResponse<T> {

    override fun code() = resCode

    override fun msg() = resMsg

    override fun data() = result

    override fun isSuccess() = resCode == 0

}