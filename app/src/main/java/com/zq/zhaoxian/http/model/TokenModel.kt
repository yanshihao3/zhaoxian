package com.zq.zhaoxian.http.model

/**
 * @program: zhaoxian
 *
 * @description:
 *
 * @author: 闫世豪
 *
 * @create: 2021-06-25 16:31
 **/
data class TokenModel(val access_token: String, val expires_in: Int, val token_type: String)