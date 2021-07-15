package com.zq.zhaoxian.ui.workbench

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.hjq.toast.ToastUtils
import com.obs.services.ObsClient
import com.zq.base.utils.GsonUtils
import com.zq.zhaoxian.common.OBS
import com.zq.zhaoxian.http.HomeNetWork
import com.zq.zhaoxian.http.model.WorkOrder
import com.zq.zhaoxian.utils.toRequestBody
import com.zq.zhaoxian.viewmodel.BaseAppViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.File


/**
 * @program: zhaoxian
 *
 * @description:
 *
 * @author: 闫世豪
 *
 * @create: 2021-06-30 16:20
 **/
class HandleViewModel : BaseAppViewModel() {
    val data by lazy {
        MutableLiveData<WorkOrder>()
    }

    var pageSize = 10

    var pageNum = 1

    override fun load() {

    }

    fun loadInitial(userId: String) {
        pageNum = 1
        pageSize = 10
        val params = hashMapOf<String, Any>()
        params["pageSize"] = pageSize
        params["pageNum"] = pageNum
        params["status"] = "END"
        params["useid"] = userId

        launchOnlyresult({
            HomeNetWork.getInstance().queryWork(params.toRequestBody())
        }, {
            data.value = it
        }, isShowDialog = true
        )
    }

    fun load(userId: String) {
        pageNum = 1
        pageSize = 10
        val params = hashMapOf<String, Any>()
        params["pageSize"] = pageSize
        params["pageNum"] = pageNum
        params["status"] = "END"
        params["useid"] = userId

        launchOnlyresult({
            HomeNetWork.getInstance().queryWork(params.toRequestBody())
        }, {
            data.value = it
        }, isShowDialog = false
        )
    }

    fun changeWorkOderStatus(description: String, workorderId: String, nextTransition: String) {
        val params = hashMapOf<String, Any>()
        params["description"] = description
        params["workorderId"] = workorderId
        params["nextTransition"] = nextTransition
        launchGo(
            {
                HomeNetWork.getInstance().changeWorkOderStatus(params.toRequestBody())
                ToastUtils.show("领取成功")
            }, error = {

            },
            complete = {

            }, isShowDialog = false
        )
    }


    fun loadMore(userId: String) {
        pageNum++
        val params = hashMapOf<String, Any>()
        params["pageSize"] = pageSize
        params["pageNum"] = pageNum
        params["status"] = "END"
        params["useid"] = userId

        launchOnlyresult({
            HomeNetWork.getInstance().queryWork(params.toRequestBody())
        }, {
            data.value = it
        }, isShowDialog = false
        )
    }


    fun commit(id: String, filePath: List<String>) {
        val list = mutableListOf<String>()
        launchGo({
            withContext(Dispatchers.IO) {
                filePath.forEach {
                    val string = uploadImage(it)
                    list.add(string)
                }
                updateWorkorder(id, "", "", list)
            }
        })
    }


    private suspend fun updateWorkorder(
        id: String,
        time: String,
        method: String,
        filePath: List<String>
    ) {
        val body = JSONObject()
        val attachmentPath = JSONObject()
        val image = JSONObject()
        val jsonArray = JSONArray()
        filePath.forEach {
            jsonArray.put(it)
        }
        image.put("image", jsonArray.toString())
        attachmentPath.put("attachmentPath", image)
        body.put("basicInfo", attachmentPath)
        body.put("extraInfo", JSONObject())
        val requestBody = RequestBody.create(
            MediaType.parse("application/json"),
            body.toString()
        )
        HomeNetWork.getInstance().updateWorkorder(id, requestBody)
    }


    private fun uploadImage(filePath: String): String {
        val endPoint = OBS.endpoint
        val ak = OBS.ak
        val sk = OBS.sk
        val obsClient = ObsClient(ak, sk, endPoint)

        val putObjectResult = obsClient.putObject(
            OBS.bucket,
            "image_${System.currentTimeMillis()}",
            File(filePath)
        )
        obsClient.close()
        return putObjectResult.objectUrl
    }
}