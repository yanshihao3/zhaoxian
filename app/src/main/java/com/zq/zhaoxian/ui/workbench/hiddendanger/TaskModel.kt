package com.zq.zhaoxian.ui.workbench.hiddendanger

import java.io.Serializable

data class TaskModel(
    val taskInfo: List<TaskInfo>
) {
    data class TaskInfo(
        val ZQIOT__checkDate__CST: String,
        val ZQIOT__checkPeople__CST: String,
        val ZQIOT__dutyPeople__CST: String,
        val ZQIOT__rectifyDate__CST: String,
        val ZQIOT__remark__CST: String,
        val ZQIOT__state__CST: String,
        val ZQIOT__taskName__CST: String,
        val createdDate: String,
        val id: String,
        val name: String
    ) : Serializable
}