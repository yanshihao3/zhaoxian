package com.zq.zhaoxian.ui.workbench.alarm

import androidx.appcompat.widget.AppCompatImageView
import coil.ImageLoader
import coil.decode.VideoFrameDecoder
import coil.fetch.VideoFrameFileFetcher
import coil.fetch.VideoFrameUriFetcher
import coil.load
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zq.base.BaseApplication
import com.zq.base.utils.HttpsUtils
import com.zq.base.utils.Logger
import com.zq.zhaoxian.R
import com.zq.zhaoxian.http.model.Enclosure
import okhttp3.OkHttpClient
import javax.inject.Inject

/**
 * @program: zhaoxian
 *
 * @description:
 *
 * @author: 闫世豪
 *
 * @create: 2021-08-16 17:54
 **/
class AlarmDetailImageAdapter @Inject constructor() :
    BaseQuickAdapter<Enclosure, BaseViewHolder>(R.layout.app_recycleview_alarm_detail_image_item) {
    val sslParams = HttpsUtils.getSslSocketFactory()
    val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .hostnameVerifier { _, _ ->
            true
        }
        .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
        .build()
    val imageLoader by lazy {
        ImageLoader.Builder(context)
            .componentRegistry {
                add(VideoFrameFileFetcher(context))
                add(VideoFrameUriFetcher(context))
                add(VideoFrameDecoder(context))
            }.okHttpClient(okHttpClient)
            .build()
    }


    init {
        addChildClickViewIds(R.id.image, R.id.play)
    }

    override fun convert(holder: BaseViewHolder, item: Enclosure) {
        val view = holder.getView<AppCompatImageView>(R.id.image)
        if (item.type == "VIDEO") {
            view.load(item.path, imageLoader) {
                addHeader("access-token", BaseApplication.access_token)
                placeholder(R.mipmap.app_ic_placeholder)
                error(R.mipmap.app_ic_placeholder)
            }
            holder.setVisible(R.id.play, true)
        } else {
            // Logger.e("handler", item.path)
            view.load(item.path, imageLoader) {
                placeholder(R.mipmap.app_ic_placeholder)
            }
            holder.setVisible(R.id.play, false)
        }
    }
}