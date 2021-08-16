package com.zq.zhaoxian.ui.workbench.alarm

import androidx.lifecycle.MutableLiveData
import com.zq.base.viewmodel.BaseViewModel
import com.zq.zhaoxian.http.HomeNetWork
import com.zq.zhaoxian.ui.workbench.hiddendanger.TaskModel
import com.zq.zhaoxian.utils.toRequestBody

/**
 * @program: zhaoxian
 *
 * @description:
 *
 * @author: 闫世豪
 *
 * @create: 2021-08-16 13:33
 **/
class AlarmViewModel : BaseViewModel() {
    override fun load() {
    }
    val data by lazy {
        MutableLiveData<TaskModel>()
    }

    var pageSize = 10

    var current = 0


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