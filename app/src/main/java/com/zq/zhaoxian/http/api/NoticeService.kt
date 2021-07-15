package com.zq.zhaoxian.http.api


import com.zq.base.network.BaseResult
import com.zq.zhaoxian.http.model.*
import com.zq.zhaoxian.ui.workbench.hiddendanger.DangerModel
import com.zq.zhaoxian.ui.workbench.hiddendanger.TaskModel
import okhttp3.RequestBody
import retrofit2.http.*

/**
 *
 */
interface NoticeService {

    /**
     * 获取access_token
     */

    @FormUrlEncoded
    @POST("/baas/auth/v1.0/oauth2/token")
    suspend fun getToken(
        @Field("grant_type") grant_type: String = "client_credentials",
        @Field("client_id") client_id: String = "df63c88a58e04ac1b894074e8314a162",
        @Field("client_secret") client_secret: String = "de45ec4b5a60153d0c278f04e2d1e40ba8d869cbf94a9142"
    ): TokenModel


    /**
     * 查询公告
     */
    @Headers("Content-Type: application/json", "Accept: */*")//需要添加头
    @POST("/service/ZQIOT__InformationDelivery/1.0.0/queryNotice")
    suspend fun queryNotice(@Body route: RequestBody): BaseResult<NoticeEntry>

    @Headers("Content-Type: application/json", "Accept: */*")//需要添加头
    @POST("/service/ZQIOT__InformationDelivery/1.0.0/queryPersonByPhone")
    suspend fun queryLogin(@Body body: RequestBody): UserInfo


    /**
     * 查询工单
     */
    @Headers("Content-Type: application/json", "Accept: */*")//需要添加头
    @POST("/service/ZQIOT__InformationDelivery/0.0.1/queryWorkOderByStatus")
    suspend fun queryWork(@Body body: RequestBody): BaseResult<WorkOrder>


    /**
     * 改变工单状态
     */
    @Headers("Content-Type: application/json", "Accept: */*")//需要添加头
    @POST("/service/ZQIOT__InformationDelivery/0.0.1/changcheWorkOderStatus")
    suspend fun changeWorkOderStatus(@Body body: RequestBody): BaseResult<ChangeWork>


    /**
     * 改变工单状态
     */
    @Headers("Content-Type: application/json", "Accept: */*")//需要添加头
    @POST("/service/Workorder/0.1.0/Workorder/update/{id}")
    suspend fun updateWorkorder(
        @Path("id") id: String,
        @Body body: RequestBody
    ): ChangeWork

    /**
     * 查询排查任务
     */
    @Headers("Content-Type: application/json", "Accept: */*")//需要添加头
    @POST("/service/ZQIOT__Troubleshooting/0.0.1/queryTaskinfoByPersonId")
    suspend fun queryTaskinfoByPersonId(
        @Body body: RequestBody
    ): BaseResult<TaskModel>

    /**
     * 查询排查任务
     */
    @Headers("Content-Type: application/json", "Accept: */*")//需要添加头
    @POST("/service/ZQIOT__Troubleshooting/0.0.1/queryhidderInfoBytaskId")
    suspend fun queryhidderInfoBytaskId(
        @Body body: RequestBody
    ): BaseResult<DangerModel>


    /**
     * 一键更改
     */
    @Headers("Content-Type: application/json", "Accept: */*")//需要添加头
    @POST("/service/ZQIOT__Troubleshooting/0.0.1/rectification")
    suspend fun rectification(
        @Body body: RequestBody
    ): ChangeWork

    /**
     * 更新公告已读状态
     */
    @Headers("Content-Type: application/json", "Accept: */*")//需要添加头
    @POST("/service/ZQIOT__InformationDelivery/1.0.0/updateNotic")
    suspend fun updateNotic(@Body body: RequestBody): ChangeWork

    /**
     * 查询天气
     */
    @GET
    suspend fun queryWeather(@Url url: String): Weather

    /**
     * 查询头条新闻
     */
    @GET
    suspend fun queryTouTiao(@Url url: String): TouTiao

}