package com.zq.zhaoxian.ui.workbench.dispatch

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
 * @create: 2021-06-28 10:53
 **/
class DispatchAdapter @Inject constructor() :
    BaseQuickAdapter<Model, BaseViewHolder>(R.layout.app_recycleview_dispatch_item) {
    init {
        addChildClickViewIds(R.id.details)
    }

    override fun convert(holder: BaseViewHolder, item: Model) {

    }
}