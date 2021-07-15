package com.zq.base.loadsir

import android.content.Context
import android.util.Log
import android.view.View
import com.kingja.loadsir.callback.Callback
import com.zq.base.R

/**
 * @program: mvvm
 *
 * @description:
 *
 * @author: 闫世豪
 *
 * @create: 2021-03-03 17:34
 */
class LoadingCallback : Callback() {
    override fun onCreateView(): Int {
        return R.layout.layout_loading
    }

    override fun getSuccessVisible(): Boolean {
        return super.getSuccessVisible()
    }

    override fun onReloadEvent(context: Context, view: View): Boolean {
        Log.e("TAG", "onReloadEvent: ")
        return true
    }
}