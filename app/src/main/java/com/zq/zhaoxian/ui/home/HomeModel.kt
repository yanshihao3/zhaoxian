package com.zq.zhaoxian.ui.home

data class HomeModel(
    val info: List<Info>,
    val notice: Notice,
    val picture: List<String>
) {
    data class Notice(
        val ZQIOT__detail__CST: String,
        val ZQIOT__noticType__CST: String,
        val ZQIOT__readType__CST: String,
        val ZQIOT__time__CST: String,
        val ZQIOT__title__CST: String,
        val createdDate: String,
        val id: String,

        )

    data class Info(
        val ZQIOT__state__CST: String,
        val count: Int
    )
}