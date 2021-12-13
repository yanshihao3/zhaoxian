package com.zq.zhaoxian.ui.workbench.hiddendanger

import androidx.recyclerview.widget.DiffUtil

/**
 * @program: zhaoxian
 *
 * @description:
 *
 * @author: 闫世豪
 *
 * @create: 2021-07-26 17:32
 **/
class DiffCallBack : DiffUtil.ItemCallback<TaskModel.TaskInfo>() {
    override fun areItemsTheSame(
        oldItem: TaskModel.TaskInfo,
        newItem: TaskModel.TaskInfo
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: TaskModel.TaskInfo,
        newItem: TaskModel.TaskInfo
    ): Boolean {

        return oldItem.ZQIOT__taskName__CST == newItem.ZQIOT__state__CST &&
                oldItem.name == newItem.name

    }


}