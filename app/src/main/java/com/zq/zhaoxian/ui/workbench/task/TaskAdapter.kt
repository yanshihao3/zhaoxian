package com.zq.zhaoxian.ui.workbench.task

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
 * @create: 2021-06-29 13:55
 **/
class TaskAdapter @Inject constructor() :
    BaseQuickAdapter<TaskModel, BaseViewHolder>(R.layout.app_recycleview_task_item) {
    init {
        addChildClickViewIds(R.id.details)
    }

    override fun convert(holder: BaseViewHolder, item: TaskModel) {

    }
}