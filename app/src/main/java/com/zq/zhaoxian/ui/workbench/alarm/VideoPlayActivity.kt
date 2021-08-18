package com.zq.zhaoxian.ui.workbench.alarm

import android.util.Log
import cn.jzvd.Jzvd
import cn.jzvd.JzvdStd
import com.zq.base.activity.BaseNoModelActivity
import com.zq.zhaoxian.R
import com.zq.zhaoxian.custommedia.JZMediaExo
import com.zq.zhaoxian.databinding.AppActivityVideoPlayBinding


class VideoPlayActivity : BaseNoModelActivity<AppActivityVideoPlayBinding>() {


    override val layoutId: Int = R.layout.app_activity_video_play

    val dataBinding by lazy { getDataBind() }

    override fun initView() {
        /**
         * 这句加上，是为了点击图片放大之后，在进入视频，背景黑屏问题
         */
        window.decorView.setBackgroundResource(R.color.black)

        val path = intent.getStringExtra("path")
        dataBinding.videoplayer.setUp(
            path,
            "",
            JzvdStd.SCREEN_NORMAL,
            JZMediaExo::class.java
        )
        dataBinding.videoplayer.startVideo()
        dataBinding.back.setOnClickListener {
            finishAfterTransition()
        }
    }

    override fun initData() {

    }


    override fun onPause() {
        super.onPause()
        Jzvd.goOnPlayOnPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        Jzvd.releaseAllVideos()
    }

    override fun onBackPressed() {
        if (Jzvd.backPress()) {
            return
        }
        finishAfterTransition()
    }

}