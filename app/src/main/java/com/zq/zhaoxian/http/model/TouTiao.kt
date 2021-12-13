package com.zq.zhaoxian.http.model

/**
 * @program: zhaoxian
 *
 * @description:
 *
 * @author: 闫世豪
 *
 * @create: 2021-07-02 10:52
 **/
data class TouTiao(
    val error_code: Int,
    val reason: String,
    val result: Result?
) {
    data class Result(
        val `data`: List<Data>,
        val page: String,
        val pageSize: String,
        val stat: String
    )

    data class Data(
        val author_name: String,
        val category: String,
        val date: String,
        val is_content: String,
        val thumbnail_pic_s: String,
        val thumbnail_pic_s02: String,
        val thumbnail_pic_s03: String,
        val title: String,
        val uniquekey: String,
        val url: String
    )
}



