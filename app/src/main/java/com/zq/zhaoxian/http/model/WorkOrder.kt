package com.zq.zhaoxian.http.model

import com.zq.zhaoxian.ui.workbench.WorkbenchModel


data class WorkOrder(val list: MutableList<WorkbenchModel>, val totalCount: Int)
