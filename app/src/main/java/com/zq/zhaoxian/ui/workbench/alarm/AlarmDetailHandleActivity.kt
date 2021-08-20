package com.zq.zhaoxian.ui.workbench.alarm

import android.app.ActivityOptions
import android.content.Intent
import android.view.View
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.hjq.toast.ToastUtils
import com.wanglu.photoviewerlibrary.PhotoViewer
import com.zq.base.BaseApplication
import com.zq.base.activity.BaseNoModelActivity
import com.zq.zhaoxian.R
import com.zq.zhaoxian.databinding.AppActivityAlarmDetailHandleBinding
import com.zq.zhaoxian.http.HomeNetWork
import com.zq.zhaoxian.http.model.AlarmInfoDetailEntry
import com.zq.zhaoxian.http.model.AlarmInfoEntry
import com.zq.zhaoxian.http.model.Enclosure
import com.zq.zhaoxian.utils.toRequestBody
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.ArrayList
import javax.inject.Inject


@AndroidEntryPoint
class AlarmDetailHandleActivity : BaseNoModelActivity<AppActivityAlarmDetailHandleBinding>() {

    override val layoutId: Int = R.layout.app_activity_alarm_detail_handle

    val alarmInfoEntry by lazy {
        intent.getParcelableExtra<AlarmInfoEntry>("data")
    }
    val dataBinding by lazy {
        getDataBind()
    }
    val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { res ->
            if (res.resultCode == 2) finish()
        }

    val data = mutableListOf<Enclosure>()

    @Inject
    lateinit var adapter: AlarmDetailImageAdapter

    override fun initView() {
        dataBinding.toolbar.setBackOnClickListener {
            finish()
        }
        dataBinding.data = alarmInfoEntry
        dataBinding.recyclerView.layoutManager = GridLayoutManager(mActivityContext, 3)
        dataBinding.recyclerView.adapter = adapter
        setLoadSir(dataBinding.root)
        showContent()
        request()
    }


    override fun initData() {
        dataBinding.btnCommit.setOnClickListener {
            val intent = Intent(mActivityContext, AlarmHandleActivity::class.java)
            intent.putExtra("workorderId ", alarmInfoEntry?.taskNumber)
            launcher.launch(intent)
        }
        adapter.setOnItemChildClickListener { _, view, position ->
            if (data[position].type == "VIDEO") {
                val intent = Intent(this, VideoPlayActivity::class.java)
                intent.putExtra("path", data[position].path)
                startActivity(
                    intent, ActivityOptions.makeSceneTransitionAnimation(
                        this,
                        view, "share"
                    ).toBundle()
                )

            } else {
                val list = data.filter { it.type == "IMAGE" }.map { it.path }
                PhotoViewer
                    .setData(list as ArrayList<String>)
                    .setCurrentPage(position)
                    .setImgContainer(dataBinding.recyclerView)
                    .setIndicatorType(PhotoViewer.INDICATOR_TYPE_TEXT)
                    .setShowImageViewInterface(object : PhotoViewer.ShowImageViewInterface {
                        override fun show(iv: ImageView, url: String) {
                            iv.load(url) {
                                addHeader("access-token", BaseApplication.access_token)
                            }
                        }
                    })
                    .setOnPhotoViewerCreatedListener {
                        isShowImage = true
                    }
                    .setOnPhotoViewerDestroyListener {
                        isShowImage = false
                    }
                    .start(this)
            }
        }
    }

    private fun request() {
        lifecycleScope.launch {
            try {
                showLoading()
                var alarmDetail: AlarmInfoDetailEntry? = null
                withContext(Dispatchers.IO) {
                    val params = hashMapOf<String, Any>()
                    params["alarmId"] = alarmInfoEntry!!.alarmId
                    params["workOrderCode"] = alarmInfoEntry!!.taskNumber
                    params["isEnd"] = false
                    alarmDetail =
                        HomeNetWork.getInstance()
                            .getSecurityTaskDetail(params.toRequestBody()).result
                }
                alarmDetail?.let {
                    it.imgList?.forEach { img ->
                        data.add(img)
                    }
                    it.videoList?.forEach { video ->
                        data.add(video)
                    }
                    if (data.isEmpty()) {
                        dataBinding.rvTitle.visibility = View.GONE
                    }
                    adapter.setNewInstance(data)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                ToastUtils.show("网络异常")
            } finally {
                showContent()
            }
        }
    }

    private var isShowImage = false

    override fun onBackPressed() {
        if (isShowImage) PhotoViewer.exit()
        else
            super.onBackPressed()

    }

}