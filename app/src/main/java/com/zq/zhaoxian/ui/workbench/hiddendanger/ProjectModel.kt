package com.zq.zhaoxian.ui.workbench.hiddendanger

data class ProjectModel(
    val info: List<Info>
) {
    data class Info(
        val ZQIOT__AddrDetail__CST: String,
        val ZQIOT__DangerInfo__CST: String,
        val ZQIOT__ProjectName__CST: String,
        val ZQIOT__condition__CST: String,
        val ZQIOT__step__CST: String,
        val ZQIOT__taskInfoId__CST: String,
        val id: String,
        val name: String
    )
}