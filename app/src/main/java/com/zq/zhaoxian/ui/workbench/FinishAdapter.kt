package com.zq.zhaoxian.ui.workbench

import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zq.zhaoxian.R
import com.zq.zhaoxian.databinding.AppRecycleviewFinishItemBinding
import javax.inject.Inject

/**
 * @program: zhaoxian
 *
 * @description:已完成的适配器
 *
 * @author: 闫世豪
 *
 * @create: 2021-06-28 15:53
 **/
class FinishAdapter @Inject constructor() :
    BaseQuickAdapter<WorkbenchModel, BaseViewHolder>(R.layout.app_recycleview_finish_item) {
    init {
        addChildClickViewIds(R.id.details)
    }

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        super.onItemViewHolderCreated(viewHolder, viewType)
        DataBindingUtil.bind<AppRecycleviewFinishItemBinding>(viewHolder.itemView)
    }

    override fun convert(holder: BaseViewHolder, item: WorkbenchModel) {
        val binding = DataBindingUtil.getBinding<AppRecycleviewFinishItemBinding>(holder.itemView)
        binding?.let {
            it.data = item
        }
    }
}
