package com.zq.zhaoxian.ui.workbench.alarm

import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zq.zhaoxian.R
import com.zq.zhaoxian.databinding.AppRecycleviewAlarmItemBinding
import com.zq.zhaoxian.http.model.AlarmInfoEntry
import javax.inject.Inject

/**
 * @program: zhaoxian
 *
 * @description:
 *
 * @author: 闫世豪
 *
 * @create: 2021-08-16 17:43
 **/
class AlarmAdapter @Inject constructor() :
    BaseQuickAdapter<AlarmInfoEntry, BaseViewHolder>(R.layout.app_recycleview_alarm_item) {
    init {
        addChildClickViewIds(R.id.details)
    }

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        super.onItemViewHolderCreated(viewHolder, viewType)
        DataBindingUtil.bind<AppRecycleviewAlarmItemBinding>(viewHolder.itemView)
    }

    override fun convert(holder: BaseViewHolder, item: AlarmInfoEntry) {
        val binding = DataBindingUtil.getBinding<AppRecycleviewAlarmItemBinding>(holder.itemView)
        binding?.let {
            binding.data = item
        }

    }
}