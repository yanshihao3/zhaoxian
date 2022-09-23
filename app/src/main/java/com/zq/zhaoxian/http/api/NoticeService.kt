package com.zq.zhaoxian.http.api


import com.zq.base.network.BaseResult
import com.zq.zhaoxian.http.model.*
import com.zq.zhaoxian.ui.home.HomeModel
import com.zq.zhaoxian.ui.workbench.hiddendanger.DangerModel
import com.zq.zhaoxian.ui.workbench.hiddendanger.TaskModel
import okhttp3.MultipartBody
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
        //生产环境的client_id
        // @Field("client_id") client_id: String = "8634fa8d3e5e481da6a9be445f72621a",
        // @Field("client_secret") client_secret: String = "49d302017c0bcd9db8e9c9a607119ea5c9df11c4a697df85"
        //开发环境的client_id
        @Field("client_id") client_id: String = "e3f88dd0d4034e41b260f2791c6ae8ee",
        @Field("client_secret") client_secret: String = "fb9e39fc1dac10348f8ccfd89fb0fcfb6a066c490e56424f"
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
     * 查询告警任务
     */
    @Headers("Content-Type: application/json", "Accept: */*")//需要添加头
    @POST("/service/ZQIOT__InformationDelivery/1.0.0/querySecurityTaskList")
    suspend fun querySecurityTaskList(
        @Body body: RequestBody
    ): BaseResult<AlarmInfoResult>

    /**
     * 查询告警任务详情
     */
    @Headers("Content-Type: application/json", "Accept: */*")//需要添加头
    @POST("/service/ZQIOT__InformationDelivery/1.0.0/getSecurityTaskDetail")
    suspend fun getSecurityTaskDetail(
        @Body body: RequestBody
    ): BaseResult<AlarmInfoDetailEntry>

    /**
     * 处理告警任务
     */
    @Headers("Content-Type: application/json", "Accept: */*")//需要添加头
    @POST("/service/ZQIOT__InformationDelivery/1.0.0/saveProcessWork")
    suspend fun saveProcessWork(
        @Body body: RequestBody
    ): BaseResult<ProcessEntry>


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
     * 查询首页数据
     */
    @Headers("Content-Type: application/json", "Accept: */*")//需要添加头
    @POST("/service/ZQIOT__Troubleshooting/0.0.1/ZQIOT__queryTaskCountBypersonId")
    suspend fun queryHomeInfo(@Body body: RequestBody): BaseResult<HomeModel>

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

    /**
     *上传文件到minio中
     */
    @POST("/u-route/baas/sys/v1.1/connectors/objectstorageproxy/SmartCampus__FileOperator/putobject")
    fun fileUpload(@Body body: RequestBody): String


}