package com.zq.zhaoxian.ui.workbench.hiddendanger

import java.io.Serializable

/**
 * @program: zhaoxian
 *
 * @description:
 *
 * @author: 闫世豪
 *
 * @create: 2021-07-09 14:38
 **/

data class DangerModel(
    val info: List<Info>

) {
    data class Info(
        val ZQIOT__AddrDetail__CST: String,
        val ZQIOT__DangerInfo__CST: String,
        val ZQIOT__ProjectName__CST: String,
        val ZQIOT__condition__CST: String,
        val ZQIOT__step__CST: String,
        val ZQIOT__taskInfoId__CST: String
    ) : Serializable
}