package com.zq.zhaoxian.http.model

import com.zq.zhaoxian.ui.workbench.notice.NoticeModel

/**
 * @program: zhaoxian
 *
 * @description:
 *
 * @author: 闫世豪
 *
 * @create: 2021-06-25 17:04
 **/
data class NoticeEntry(val list: MutableList<NoticeModel>, val total: Int)