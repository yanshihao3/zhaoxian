package com.zq.base.network

import com.zq.base.BaseApplication
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @program: zhaoxian
 *
 * @description:
 *
 * @author: 闫世豪
 *
 * @create: 2021-06-25 16:53
 **/
class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val builder = originalRequest.newBuilder()
        builder.header(
            "access-token",
            BaseApplication.access_token
        )
        val requestBuilder = builder.method(originalRequest.method(), originalRequest.body())
        val request = requestBuilder.build()
        return chain.proceed(request)

    }
}