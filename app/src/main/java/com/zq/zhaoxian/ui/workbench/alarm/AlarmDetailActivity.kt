package com.zq.zhaoxian.ui.workbench.alarm

import android.app.ActivityOptions
import android.content.Intent
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.wanglu.photoviewerlibrary.PhotoViewer
import com.zq.base.activity.BaseNoModelActivity
import com.zq.zhaoxian.R
import com.zq.zhaoxian.databinding.AppActivityAlarmDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList
import javax.inject.Inject

@AndroidEntryPoint
class AlarmDetailActivity : BaseNoModelActivity<AppActivityAlarmDetailBinding>() {

    override val layoutId: Int = R.layout.app_activity_alarm_detail

    val dataBinding by lazy {
        getDataBind()
    }

    @Inject
    lateinit var adapter: AlarmDetailImageAdapter
    val data =
        mutableListOf(
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fn.sinaimg.cn%2Ftranslate%2F480%2Fw640h640%2F20180330%2F3hsz-fyssmmc7782215.jpg&refer=http%3A%2F%2Fn.sinaimg.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1631701370&t=06c1e5e6c3fa706b8ad96da3e60d7730",
            "https://img1.baidu.com/it/u=52681052,678098948&fm=253&fmt=auto&app=120&f=JPEG?w=1000&h=400",
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fbaike.soso.com%2Fp%2F20090713%2F20090713194934-1708924711.jpg&refer=http%3A%2F%2Fbaike.soso.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1631703722&t=9f4f34e1b9b8f7237f97a3e1b4dbbc35",
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fs11.sinaimg.cn%2Fbmiddle%2F494691675c8d85c6e184a&refer=http%3A%2F%2Fs11.sinaimg.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1631703722&t=6fb5e425741bb5deb764ae1d34c05bad",
            "https://stream7.iqilu.com/10339/upload_transcode/202002/18/20200218093206z8V1JuPlpe.mp4"
        )

    override fun initView() {
        dataBinding.toolbar.setBackOnClickListener {
            finish()
        }
        dataBinding.recyclerView.layoutManager = GridLayoutManager(mActivityContext, 2)
        dataBinding.recyclerView.adapter = adapter
        adapter.setNewInstance(data)
    }

    override fun initData() {
        adapter.setOnItemChildClickListener { _, view, position ->
            if (data[position].contains("mp4")) {
                val intent = Intent(this, VideoPlayActivity::class.java)
                intent.putExtra("path", data[position])
                startActivity(
                    intent, ActivityOptions.makeSceneTransitionAnimation(
                        this,
                        view, "share"
                    ).toBundle()
                )
            } else {
                val list = data.filter { !it.contains("mp4") }
                PhotoViewer
                    .setData(list as ArrayList<String>)
                    .setCurrentPage(position)
                    .setImgContainer(dataBinding.recyclerView)
                    .setIndicatorType(PhotoViewer.INDICATOR_TYPE_TEXT)
                    .setShowImageViewInterface(object : PhotoViewer.ShowImageViewInterface {
                        override fun show(iv: ImageView, url: String) {
                            iv.load(url)
                        }
                    })
                    .setOnPhotoViewerCreatedListener {
                        isShowImage = true
                    }
                    .setOnPhotoViewerDestroyListener {
                        isShowImage = false
                    }
                    .start(this)
                //ZoomUtils.zoomImageFromThumb(this,view,data[position])
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