package com.zq.zhaoxian.ui.workbench

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zq.base.viewmodel.BaseViewModel

class WorkbenchViewModel : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text
    override fun load() {

    }
}