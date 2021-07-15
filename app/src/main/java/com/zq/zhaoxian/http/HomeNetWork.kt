package com.zq.zhaoxian.http

import com.zq.base.network.RetrofitClient
import com.zq.zhaoxian.http.api.NoticeService
import com.zq.zhaoxian.http.model.TouTiao
import okhttp3.RequestBody
import retrofit2.http.Url


/**
 */
class HomeNetWork {


    private val mNoticeService by lazy {
        RetrofitClient.getInstance().create(NoticeService::class.java)
    }

    suspend fun getToken() = mNoticeService.getToken()


    suspend fun queryNotice(body: RequestBody) = mNoticeService.queryNotice(body)

    suspend fun queryWork(body: RequestBody) = mNoticeService.queryWork(body)

    suspend fun changeWorkOderStatus(body: RequestBody) = mNoticeService.changeWorkOderStatus(body)

    /**
     * 处理工单
     */
    suspend fun updateWorkorder(id: String, body: RequestBody) =
        mNoticeService.updateWorkorder(id, body)

    suspend fun updateNotic(body: RequestBody) = mNoticeService.updateNotic(body)

    suspend fun queryTaskinfoByPersonId(body: RequestBody) = mNoticeService.queryTaskinfoByPersonId(body)

    suspend fun queryhidderInfoBytaskId(body: RequestBody) = mNoticeService.queryhidderInfoBytaskId(body)

    suspend fun rectification(body: RequestBody) = mNoticeService.rectification(body)

    suspend fun queryLogin(body: RequestBody) = mNoticeService.queryLogin(body)


    suspend fun queryWeather(url: String) = mNoticeService.queryWeather(url)

    suspend fun queryTouTiao(url: String) = mNoticeService.queryTouTiao(url)

    companion object {

        @Volatile
        private var netWork: HomeNetWork? = null

        fun getInstance() = netWork ?: synchronized(this) {
            netWork ?: HomeNetWork().also { netWork = it }
        }
    }


}