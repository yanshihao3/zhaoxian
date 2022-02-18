package com.zq.zhaoxian.http

import android.util.Log
import com.zq.base.BaseApplication
import kotlinx.coroutines.*
import okhttp3.Interceptor
import okhttp3.Response
import java.lang.Exception

/**
 * @program: zhaoxian
 *
 * @description:
 *
 * @author: 闫世豪
 *
 * @create: 2021-07-15 10:17
 **/
class TokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        if (isTokenExpired(response)) {
            if (getToken()) {
                response.close()
                val newRequest = chain.request()
                    .newBuilder()
                    .header("access-token", BaseApplication.access_token)
                    .build()
                return chain.proceed(newRequest)
            } else {
                return response
            }
        }
        return response
    }

    private fun isTokenExpired(response: Response): Boolean {
        if (response.code() == 401) {
            return true
        }
        return false
    }

    private fun getToken() =
        try {
            runBlocking {
                val token = HomeNetWork.getInstance().getToken().access_token
                BaseApplication.access_token = token
                true
            }
        } catch (e: Exception) {
            Log.e("TAG", "getToken:出现异常 ")
            false
        }

}