package com.zq.zhaoxian.http.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AlarmInfoResult(val list: List<AlarmInfoEntry>, val totalCount: Int) : Parcelable

@Parcelize
data class AlarmInfoEntry(
    val alarmId: String?,
    val alarmOpenTime: String?,
    val alarmTypeStr: String?,
    val assigneeId: String?,
    val assigneeName: String?,
    val completeTime: String?,
    val createBy: String?,
    val createDate: String?,
    val description: String?,
    val flowStatusName: String?,
    val id: String?,
    val incidentId: String?,
    val occurPlace: String?,
    val priorityForSort: Int?,
    val schemeId: String?,
    val spaceId: String?,
    val statusCode: String?,
    val statusNameCn: String?,
    val taskDetail: String?,
    val taskNumber: String?,
    val taskTitle: String?,
    val workOrderCode: String?,
    val workorderCreator: String?,
    val workorderPriority: String?
) : Parcelable