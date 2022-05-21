package com.zq.zhaoxian.application


import com.tencent.bugly.crashreport.CrashReport
import com.tencent.mmkv.MMKV
import com.zq.base.BaseApplication
import com.zq.base.network.HeaderInterceptor
import com.zq.base.network.HttpLogInterceptor
import com.zq.base.network.RetrofitClient
import com.zq.base.utils.HttpsUtils
import com.zq.zhaoxian.http.TokenInterceptor
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.net.ssl.HttpsURLConnection


/**
 * @program: mvvm
 *
 * @description:
 *
 * @author: 闫世豪
 *
 * @create: 2021-03-03 17:40
 **/

@HiltAndroidApp
class MyApplication : BaseApplication() {


    override fun onCreate() {
        super.onCreate()
        val sslParams = HttpsUtils.getSslSocketFactory()

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(20L, TimeUnit.SECONDS)
            .addInterceptor(HeaderInterceptor())
            .addInterceptor(HttpLogInterceptor.getInstance())
            .addInterceptor(TokenInterceptor())
            .writeTimeout(20L, TimeUnit.SECONDS)
            .hostnameVerifier { _, _ ->
                true
            }
            .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
            .build()
        /**
         * 饺子播放器 忽略ssl证书
         */
        HttpsURLConnection.setDefaultSSLSocketFactory(sslParams.sSLSocketFactory)
        HttpsURLConnection.setDefaultHostnameVerifier { _, _ -> true }

        RetrofitClient.getInstance().setBaseUrl("https://172.100.25.201/")
            .setOkHttpClient(okHttpClient).init()

        registerActivityLifecycleCallbacks(MyActivityManager.getInstance())

        MMKV.initialize(this)
        CrashReport.initCrashReport(this, "5ff8e15ad1", false)

        CrashHandler.getInstance().init(applicationContext)

    }
}