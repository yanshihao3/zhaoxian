package com.zq.zhaoxian.ui.workbench

/**
 * @program: zhaoxian
 *
 * @description:
 *
 * @author: 闫世豪
 *
 * @create: 2021-07-07 14:43
 **/

data class BasicInfo(
    val workorderId: String,
    val createTime: String,
    val workorderPriority: String,
    val workorderType: String,
    val sourceId: String,
    val workorderState: String,
    val workorderTitle: String,
    val description: String,
    val deadlineTime: String,
    val spaceId: String,
    val attachmentPath: String,
    val workorderCreator: String
)

data class Image(val name: String, val url: String)