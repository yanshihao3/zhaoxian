package com.zq.zhaoxian.http

import com.zq.base.network.RetrofitClient
import com.zq.zhaoxian.http.api.NoticeService
import okhttp3.RequestBody

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

    suspend fun queryHomeInfo(body: RequestBody) = mNoticeService.queryHomeInfo(body)

    suspend fun queryTaskinfoByPersonId(body: RequestBody) = mNoticeService.queryTaskinfoByPersonId(body)

    suspend fun querySecurityTaskList(body: RequestBody) = mNoticeService.querySecurityTaskList(body)

    suspend fun queryhidderInfoBytaskId(body: RequestBody) = mNoticeService.queryhidderInfoBytaskId(body)

    suspend fun getSecurityTaskDetail(body: RequestBody) = mNoticeService.getSecurityTaskDetail(body)

    suspend fun saveProcessWork(body: RequestBody) = mNoticeService.saveProcessWork(body)

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