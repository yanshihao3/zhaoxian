package com.zq.base.network

import okhttp3.Interceptor
import java.util.logging.Level

object HttpLogInterceptor {
    fun getInstance(): Interceptor {
        val loggingInterceptor = HttpLoggingInterceptor("zhongqing")
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY)
        loggingInterceptor.setColorLevel(Level.INFO)
        return loggingInterceptor
    }
}

