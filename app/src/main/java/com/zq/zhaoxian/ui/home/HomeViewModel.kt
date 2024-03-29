package com.zq.zhaoxian.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zq.base.BaseApplication
import com.zq.base.network.ACache
import com.zq.base.utils.GsonUtils
import com.zq.base.viewmodel.BaseViewModel
import com.zq.zhaoxian.http.HomeNetWork
import com.zq.zhaoxian.http.model.TouTiao
import com.zq.zhaoxian.http.model.Weather
import com.zq.zhaoxian.utils.toRequestBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class HomeViewModel : BaseViewModel() {


    val touTiaoData = MutableLiveData<List<TouTiao.Data>>()

    val weather = MutableLiveData<Weather>()

    val homeModel = MutableLiveData<HomeModel>()

    private val aCache = ACache.get(BaseApplication.getApplication())

    override fun load() {

    }

    fun queryWeather() {
        val city = "赵县"
        val url =
            "http://apis.juhe.cn/simpleWeather/query?city=$city&key=e6b5a883742db6c1015315e92cf290ba"
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    val data = HomeNetWork.getInstance().queryWeather(url)
                    if (data.result != null) {
                        weather.postValue(data)
                        aCache.put("weather", GsonUtils.toJson(data))
                    } else {
                        val asString = aCache.getAsString("weather")
                        val fromLocalJson =
                            GsonUtils.fromLocalJson<Weather>(asString, Weather::class.java)
                        weather.postValue(fromLocalJson!!)
                    }
                }
            } catch (e: Exception) {
                Log.e("TAG", "queryWeather: ${e.printStackTrace()}")
            }
        }

    }

    fun queryTouTiao() {
        val url =
            "http://v.juhe.cn/toutiao/index?type=top&page_size=10&key=15130bf81254128ba911f0abe002f260"
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    val data = HomeNetWork.getInstance().queryTouTiao(url)
                    if (data.result?.data != null) {
                        touTiaoData.postValue(data.result.data)
                        aCache.put("toutiao", GsonUtils.toJson(data))
                    } else {
                        val asString = aCache.getAsString("toutiao")
                        val fromLocalJson =
                            GsonUtils.fromLocalJson<TouTiao>(asString, TouTiao::class.java)
                        touTiaoData.postValue(fromLocalJson?.result?.data)
                    }
                }

            } catch (e: Exception) {
                val asString = aCache.getAsString("toutiao")
                asString?.let {
                    val fromLocalJson =
                        GsonUtils.fromLocalJson<TouTiao>(asString, TouTiao::class.java)
                    touTiaoData.value = fromLocalJson?.result?.data
                }
            }
        }
    }

    fun queryHomeInfo(userId: String, portalUserId: String) {
        val params = hashMapOf<String, Any>()
        params["id"] = userId
        params["portalUserId"] = portalUserId
        launchOnlyresult({
            HomeNetWork.getInstance().queryHomeInfo(params.toRequestBody())
        }, {
            homeModel.value = it
        }, isShowDialog = false
        )
    }
}