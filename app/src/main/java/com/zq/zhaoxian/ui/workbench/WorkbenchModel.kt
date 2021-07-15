package com.zq.zhaoxian.ui.workbench

import java.io.Serializable

/**
 * @program: zhaoxian
 *
 * @description:
 *
 * @author: 闫世豪
 *
 * @create: 2021-06-30 14:15
 **/

data class WorkbenchModel(
    val code: String,
    val completeTime: String,
    val createTime: String,
    val firstAssignTime: String,
    val flowStatus: String,
    val id: String,
    val incident: Incident,
    val processorId: String,
    val processorName: String,
    val remark: String,
    val responsiblePersonName: String,
    val title: String,
    val workorderPriority: String,
    val workorderState: String,
    val workorderType: String
) : Serializable {
    data class Incident(
        val alarmNumber: String
    ) : Serializable
}