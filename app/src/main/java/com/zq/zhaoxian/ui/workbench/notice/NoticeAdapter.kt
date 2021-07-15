package com.zq.zhaoxian.ui.workbench.notice

import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.ctetin.expandabletextviewlibrary.ExpandableTextView
import com.ctetin.expandabletextviewlibrary.ExpandableTextView.OnExpandOrContractClickListener
import com.ctetin.expandabletextviewlibrary.app.StatusType
import com.zq.zhaoxian.R
import com.zq.zhaoxian.databinding.AppRecycleviewNoticeItemBinding
import javax.inject.Inject

/**
 * @program: zhaoxian
 *
 * @description:
 *
 * @author: 闫世豪
 *
 * @create: 2021-06-25 15:45
 **/
class NoticeAdapter @Inject constructor() :
    BaseQuickAdapter<NoticeModel, BaseViewHolder>(R.layout.app_recycleview_notice_item) {

    private lateinit var expandListener: ExpandStatusListener
    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        super.onItemViewHolderCreated(viewHolder, viewType)
        DataBindingUtil.bind<AppRecycleviewNoticeItemBinding>(viewHolder.itemView)

    }

    fun setExpandListener(expandListener: ExpandStatusListener) {
        this.expandListener = expandListener
    }

    override fun convert(holder: BaseViewHolder, item: NoticeModel) {
        val binding = DataBindingUtil.getBinding<AppRecycleviewNoticeItemBinding>(holder.itemView)
        binding?.data = item
        binding?.expanded?.setContent(
            item.ZQIOT__detail__CST
        )
        val expandableTextView = holder.getView<ExpandableTextView>(R.id.expanded)
        expandableTextView.setExpandOrContractClickListener {
            when (it) {
                StatusType.STATUS_EXPAND -> {
                    if (::expandListener.isInitialized) {
                        expandListener.expend(holder.adapterPosition)
                    }
                }
            }
        }
    }
}

interface ExpandStatusListener {
    fun expend(position: Int)
}