package com.zq.base.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zq.base.BaseApplication

/**
 *
 */
class ViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val type = modelClass.constructors[0].parameterTypes
        if (type.isNotEmpty()) {
            val tClass = type[0]
            if (Application::class.java.isAssignableFrom(tClass)) {
                return modelClass.getConstructor(tClass)
                    .newInstance(BaseApplication.getApplication())
            }
        }
        return modelClass.newInstance()
    }
}