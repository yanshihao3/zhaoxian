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
class DiffCallBack(
    private val oldData: List<TaskModel.TaskInfo>,
    private val newData: List<TaskModel.TaskInfo>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldData.size
    }

    override fun getNewListSize(): Int {
        return newData.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        if (oldItemPosition == 0) {
            return true
        }
        val old = oldData[oldItemPosition]
        val new = newData[newItemPosition]
        return old.id == new.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

        if (oldItemPosition == 0) {
            return true
        }
        val old = oldData[oldItemPosition]
        val new = newData[newItemPosition]
        if (old.ZQIOT__taskName__CST != new.ZQIOT__taskName__CST) return false

        return true
    }
}