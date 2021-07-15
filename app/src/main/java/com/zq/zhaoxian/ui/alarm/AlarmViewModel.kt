package com.zq.zhaoxian.ui.alarm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zq.base.viewmodel.BaseViewModel

class AlarmViewModel : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text

    override fun load() {

    }
}