package com.zq.zhaoxian.ui.workbench.investigation

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
 * @create: 2021-06-28 15:26
 **/
class InvestigationAdapter @Inject constructor() :
    BaseQuickAdapter<InvestigationModel, BaseViewHolder>(R.layout.app_recycleview_dispatch_item) {
    init {
        addChildClickViewIds(R.id.details)
    }

    override fun convert(holder: BaseViewHolder, item: InvestigationModel) {

    }
}
