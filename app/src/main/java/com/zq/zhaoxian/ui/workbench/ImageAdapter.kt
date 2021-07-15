package com.zq.zhaoxian.ui.workbench

import androidx.appcompat.widget.AppCompatImageView
import coil.load
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zq.zhaoxian.R
import java.io.File
import javax.inject.Inject

/**
 * @program: zhaoxian
 *
 * @description:
 *
 * @author: 闫世豪
 *
 * @create: 2021-06-30 17:33
 **/
class ImageAdapter @Inject constructor() :
    BaseQuickAdapter<String, BaseViewHolder>(R.layout.app_recycleview_image_item) {
    init {
        addChildClickViewIds(R.id.image, R.id.delete)
    }

    override fun convert(holder: BaseViewHolder, item: String) {

        if (holder.adapterPosition >= 3) { //图片已选完时，隐藏添加按钮
            holder.setGone(R.id.image, true)
            holder.setGone(R.id.delete, true)
        } else {
            holder.setGone(R.id.image, false)
            holder.setGone(R.id.delete, false)
        }
        val view = holder.getView<AppCompatImageView>(R.id.image)
        if (item.contains(context.getString(R.string.glide_plus_icon_string))) {
            holder.setVisible(R.id.delete, false)
            view.load(item)
        } else {
            view.load(File(item))
        }

    }
}