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
 * @create: 2021-07-09 14:20
 **/
class ViewModel : BaseAppViewModel() {

    val data by lazy {
        MutableLiveData<DangerModel>()
    }

    override fun load() {

    }

    fun loadInitial(taskId: String) {
        val params = hashMapOf<String, Any>()
        params["id"] = taskId

        launchOnlyresult({
            HomeNetWork.getInstance().queryhidderInfoBytaskId(params.toRequestBody())
        }, {
            data.value = it
        }, isShowDialog = false
        )
    }

    fun rectification(id: String, time: String) {
        val params = hashMapOf<String, Any>()
        params["id"] = id
        params["data"] = time
        launchGo({
            HomeNetWork.getInstance().rectification(params.toRequestBody())
        }, {

        }, isShowDialog = false
        )
    }

}