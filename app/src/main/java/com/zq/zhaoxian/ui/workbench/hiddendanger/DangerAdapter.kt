package com.zq.zhaoxian.ui.workbench.hiddendanger

import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zq.zhaoxian.R
import com.zq.zhaoxian.databinding.AppRecycleviewDangerItemBinding
import javax.inject.Inject

/**
 * @program: zhaoxian
 *
 * @description:
 *
 * @author: 闫世豪
 *
 * @create: 2021-07-12 10:31
 **/
class DangerAdapter @Inject constructor() :
    BaseQuickAdapter<TaskModel.TaskInfo, BaseViewHolder>(R.layout.app_recycleview_danger_item) {
    init {
        addChildClickViewIds(R.id.details)
    }

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        super.onItemViewHolderCreated(viewHolder, viewType)
        DataBindingUtil.bind<AppRecycleviewDangerItemBinding>(viewHolder.itemView)
    }

    override fun convert(holder: BaseViewHolder, item: TaskModel.TaskInfo) {
        val binding = DataBindingUtil.getBinding<AppRecycleviewDangerItemBinding>(holder.itemView)
        binding?.let {
            it.data = item
        }

    }
}