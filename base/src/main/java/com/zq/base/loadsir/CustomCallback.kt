package com.zq.base.loadsir

import android.content.Context
import android.view.View
import android.widget.Toast
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
class CustomCallback : Callback() {
    override fun onCreateView(): Int {
        return R.layout.layout_custom
    }

    override fun onReloadEvent(context: Context, view: View): Boolean {
        Toast.makeText(context.applicationContext, "Hello buddy, how r u! :p", Toast.LENGTH_SHORT)
            .show()
        view.findViewById<View>(R.id.iv_gift).setOnClickListener {
            Toast.makeText(
                context.applicationContext,
                "It's your gift! :p",
                Toast.LENGTH_SHORT
            ).show()
        }
        return true
    }
}