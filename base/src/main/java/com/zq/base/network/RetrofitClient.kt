package com.zq.base.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 *   @auther : Aleyn
 *   time   : 2019/09/04
 */
class RetrofitClient {
    companion object {
        fun getInstance() = SingletonHolder.INSTANCE
        private lateinit var retrofit: Retrofit
    }

    var url = "https://studio.e.huawei.com/"


    private object SingletonHolder {
        val INSTANCE = RetrofitClient()
    }


    private var okHttpClient = getOkHttpClient()

    fun init() {
        retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(url)
            .build()
    }

    fun setOkHttpClient(okHttpClient: OkHttpClient): RetrofitClient {
        this.okHttpClient = okHttpClient
        return this
    }

    fun setBaseUrl(baseUrl: String): RetrofitClient {
        this.url = baseUrl
        return this
    }

    private fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(20L, TimeUnit.SECONDS)
            .addInterceptor(HeaderInterceptor())
            .addInterceptor(HttpLogInterceptor.getInstance())
            .writeTimeout(20L, TimeUnit.SECONDS)
            .build()
    }

    fun <T> create(service: Class<T>?): T =
        retrofit.create(service!!) ?: throw RuntimeException("Api service is null!")
}