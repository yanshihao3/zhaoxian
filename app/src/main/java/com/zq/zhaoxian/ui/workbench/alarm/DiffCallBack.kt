package com.zq.zhaoxian.ui.workbench.alarm

import androidx.recyclerview.widget.DiffUtil
import com.zq.zhaoxian.http.model.AlarmInfoEntry

class DiffCallBack : DiffUtil.ItemCallback<AlarmInfoEntry>() {
    override fun areItemsTheSame(
        oldItem: AlarmInfoEntry,
        newItem: AlarmInfoEntry
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: AlarmInfoEntry,
        newItem: AlarmInfoEntry
    ): Boolean {

        return oldItem.alarmId == newItem.alarmId &&
                oldItem.incidentId == newItem.incidentId

    }


}