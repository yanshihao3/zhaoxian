package com.zq.zhaoxian.http.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class AlarmInfoDetailEntry(
    val imgList: List<Enclosure>,
    val logs: Logs,
    val videoList: List<Enclosure>
)

@Parcelize
data class Enclosure(
    val fromDevice: String,
    val id: String,
    val path: String,
    val type: String
) : Parcelable

data class Logs(
    val operationContent: String,
    val operationDate: String,
    val operationHandeler: String,
    val operationTitle: String
)