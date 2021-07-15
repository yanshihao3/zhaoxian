package com.zq.zhaoxian.ui.workbench.hiddendanger

import androidx.lifecycle.MutableLiveData
import com.zq.zhaoxian.http.HomeNetWork
import com.zq.zhaoxian.utils.toRequestBody
import com.zq.zhaoxian.viewmodel.BaseAppViewModel

/**
 * @program: zhaoxian
 *
 * @description:
 *
 * @author: 闫世豪
 *
 * @create: 2021-07-12 10:27
 **/
class DangerViewModel : BaseAppViewModel() {

    val data by lazy {
        MutableLiveData<TaskModel>()
    }

    var pageSize = 10

    var current = 0

    override fun load() {

    }

    fun loadRefresh(userId: String, status: String) {
        current = 0
        pageSize = 10
        val params = hashMapOf<String, Any>()
        params["pageSize"] = pageSize
        params["current"] = current
        params["status"] = status
        params["id"] = userId

        launchOnlyresult({
            HomeNetWork.getInstance().queryTaskinfoByPersonId(params.toRequestBody())
        }, {
            data.value = it
        }, isShowDialog = false
        )
    }

    fun loadInitial(userId: String, status: String) {
        current = 0
        pageSize = 10
        val params = hashMapOf<String, Any>()
        params["pageSize"] = pageSize
        params["current"] = current
        params["status"] = status
        params["id"] = userId

        launchOnlyresult({
            HomeNetWork.getInstance().queryTaskinfoByPersonId(params.toRequestBody())
        }, {
            data.value = it
        }, isShowDialog = true
        )
    }

    fun loadMore(userId: String,status: String) {
        current++
        val params = hashMapOf<String, Any>()
        params["pageSize"] = pageSize
        params["current"] = current
        params["status"] = status
        params["id"] = userId

        launchOnlyresult({
            HomeNetWork.getInstance().queryTaskinfoByPersonId(params.toRequestBody())
        }, {
            data.value = it
        }, isShowDialog = false
        )
    }
}