package com.zq.zhaoxian.ui.home

import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import coil.load
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zq.zhaoxian.R
import com.zq.zhaoxian.databinding.AppRecycleviewHomeItemBinding
import com.zq.zhaoxian.http.model.TouTiao
import javax.inject.Inject

/**
 * @program: zhaoxian
 *
 * @description:
 *
 * @author: 闫世豪
 *
 * @create: 2021-07-02 10:48
 **/
class HomeAdapter @Inject constructor() :
    BaseQuickAdapter<TouTiao.Data, BaseViewHolder>(R.layout.app_recycleview_home_item) {

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        super.onItemViewHolderCreated(viewHolder, viewType)
        DataBindingUtil.bind<AppRecycleviewHomeItemBinding>(viewHolder.itemView)
    }

    override fun convert(holder: BaseViewHolder, item: TouTiao.Data) {
        val image = holder.getView<ImageView>(R.id.image)
        image.load(item.thumbnail_pic_s)
        val binding = DataBindingUtil.getBinding<AppRecycleviewHomeItemBinding>(holder.itemView)
        binding?.data = item
    }
}