package com.zq.zhaoxian.utils

import com.zq.base.utils.GsonUtils
import okhttp3.MediaType
import okhttp3.RequestBody

/**
 * @program: zhaoxian
 *
 * @description:
 *
 * @author: 闫世豪
 *
 * @create: 2021-06-25 18:07
 **/

/**
 * map 转 json  RequestBody
 */
fun HashMap<*, *>.toRequestBody(): RequestBody {
    return RequestBody.create(
        MediaType.parse("application/json"),
        GsonUtils.toJson(this)
    )

}