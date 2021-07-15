package com.zq.zhaoxian.ui.workbench.facilities

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
 * @create: 2021-06-28 18:44
 **/
class FacilitiesAdapter @Inject constructor() :
    BaseQuickAdapter<FacilitiesModel, BaseViewHolder>(R.layout.app_recycleview_facilities_item) {
    init {
        addChildClickViewIds(R.id.details)
    }

    override fun convert(holder: BaseViewHolder, item: FacilitiesModel) {

    }
}