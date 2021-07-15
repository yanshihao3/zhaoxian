package com.zq.zhaoxian.ui.workbench.appointment

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
 * @create: 2021-06-28 15:53
 **/
class AppointmentAdapter @Inject constructor() :
    BaseQuickAdapter<AppointmentModel, BaseViewHolder>(R.layout.app_recycleview_appointment_item) {
    init {
        addChildClickViewIds(R.id.details)
    }

    override fun convert(holder: BaseViewHolder, item: AppointmentModel) {

    }
}
