package com.zq.zhaoxian.ui.workbench.alarm

import androidx.appcompat.widget.AppCompatImageView
import coil.load
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zq.zhaoxian.R
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
    BaseQuickAdapter<String, BaseViewHolder>(R.layout.app_recycleview_alarm_detail_image_item) {
    init {
        addChildClickViewIds(R.id.image, R.id.play)
    }

    override fun convert(holder: BaseViewHolder, item: String) {
        val view = holder.getView<AppCompatImageView>(R.id.image)
        if (item.contains("mp4")) {
            holder.setVisible(R.id.play, true)
        } else {
            view.load(item)
            holder.setVisible(R.id.play, false)

        }


    }
}