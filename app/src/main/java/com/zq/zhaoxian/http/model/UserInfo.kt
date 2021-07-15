package com.zq.zhaoxian.http.model

/**
 * @program: zhaoxian
 *
 * @description:
 *
 * @author: 闫世豪
 *
 * @create: 2021-07-02 16:13
 **/
data class UserInfo(
    val resCode: String,
    val resMsg: String,
    val result: Result
) {
    data class Result(
        val list: List<Info>,
        val totalCount: Int
    )

    data class Info(
        val dateOfBirth: String,
        val gender: String,
        val personName: String,
        val url: Any,
        val id: String, //用户id
        val phone: String,
        val portalUser: String //业务用户id
    )
}



