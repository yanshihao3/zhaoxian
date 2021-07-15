package com.zq.zhaoxian.ui.workbench.notice

import java.io.Serializable

/**
 * @program: zhaoxian
 *
 * @description:
 *
 * @author: 闫世豪
 *
 * @create: 2021-06-25 15:45
 **/
data class NoticeModel(
    val ZQIOT__detail__CST: String,
    val ZQIOT__noticType__CST: String,
    val ZQIOT__readType__CST: String,
    val ZQIOT__time__CST: String,
    val ZQIOT__title__CST: String,
    val createdDate: String,
    val currencyIsoCode: String,
    val NoticeId: String
) : Serializable