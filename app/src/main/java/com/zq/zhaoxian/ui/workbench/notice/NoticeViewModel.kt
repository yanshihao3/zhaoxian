package com.zq.zhaoxian.ui.workbench.notice

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
 * @create: 2021-06-25 14:58
 **/
class NoticeViewModel : BaseAppViewModel() {

    val data by lazy {
        MutableLiveData<MutableList<NoticeModel>>()
    }

    var start: Int = 0

    private val limit = 10


    override fun load() {
        val params = hashMapOf<String, String>()
        params["start"] = start.toString()
        params["limit"] = limit.toString()
        params["id"] = ""
        params["readType"] = "0"

        launchOnlyresult({
            HomeNetWork.getInstance().queryNotice(params.toRequestBody())
        }, {
            data.value = it.list
        }, {
            //如果错误为 token过期 ，那就 刷新 token
//            getToken()
//            load()
        }
        )
    }

    fun loadInitial(id: String, readType: String) {
        val params = hashMapOf<String, Any>()
        params["id"] = id
        params["readType"] = readType

        launchOnlyresult({
            HomeNetWork.getInstance().queryNotice(params.toRequestBody())
        }, {
            data.value = it.list
        }
        )
    }

    fun loadRefresh(id: String, readType: String) {
        val params = hashMapOf<String, Any>()
        params["id"] = id
        params["readType"] = readType

        launchOnlyresult({
            HomeNetWork.getInstance().queryNotice(params.toRequestBody())
        }, {
            data.value = it.list
        }, isShowDialog = false
        )
    }

    fun updateNotic(personId: String, noticeId: String) {
        val params = hashMapOf<String, Any>()
        params["personId"] = personId
        params["noticeId"] = noticeId

        launchGo(
            {
                HomeNetWork.getInstance().updateNotic(params.toRequestBody())
            }, isShowDialog = false
        )
    }


}