package com.zq.zhaoxian.ui.workbench

import androidx.lifecycle.MutableLiveData
import com.hjq.toast.ToastUtils
import com.zq.zhaoxian.http.HomeNetWork
import com.zq.zhaoxian.http.model.WorkOrder
import com.zq.zhaoxian.utils.toRequestBody
import com.zq.zhaoxian.viewmodel.BaseAppViewModel

/**
 * @program: zhaoxian
 *
 * @description:
 *
 * @author: 闫世豪
 *
 * @create: 2021-07-06 16:38
 **/
class PendingViewModel : BaseAppViewModel() {

    val data by lazy {
        MutableLiveData<WorkOrder>()
    }

    var pageSize = 10

    var pageNum = 1

    override fun load() {

    }

    fun loadInitial(userId: String, status: String) {
        pageNum = 1
        pageSize = 10
        val params = hashMapOf<String, Any>()
        params["pageSize"] = pageSize
        params["pageNum"] = pageNum
        params["status"] = status
        params["useid"] = userId

        launchOnlyresult({
            HomeNetWork.getInstance().queryWork(params.toRequestBody())
        }, {
            data.value = it
        }, isShowDialog = true
        )
    }

    fun load(userId: String, status: String) {
        pageNum = 1
        pageSize = 10
        val params = hashMapOf<String, Any>()
        params["pageSize"] = pageSize
        params["pageNum"] = pageNum
        params["status"] = status
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


    fun loadMore(userId: String,status: String) {
        pageNum++
        val params = hashMapOf<String, Any>()
        params["pageSize"] = pageSize
        params["pageNum"] = pageNum
        params["status"] = status
        params["useid"] = userId

        launchOnlyresult({
            HomeNetWork.getInstance().queryWork(params.toRequestBody())
        }, {
            data.value = it
        }, isShowDialog = false
        )
    }
}