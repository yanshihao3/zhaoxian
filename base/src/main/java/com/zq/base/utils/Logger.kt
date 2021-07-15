package com.zq.base.utils


import android.util.Log

object Logger {
    private var sEnabled = false
    private const val sDefaultTag = "Main"

    fun setEnabled(enabled: Boolean) {
        sEnabled = enabled
    }

    fun v(msg: String) {
        if (sEnabled) {
            Log.v(sDefaultTag, "" + msg)
        }
    }

    fun v(tag: String, msg: String) {
        if (sEnabled) {
            Log.v(tag, "" + msg)
        }
    }

    fun d(msg: String) {
        if (sEnabled) {
            Log.d(sDefaultTag, "" + msg)
        }
    }

    fun d(tag: String, msg: String) {
        if (sEnabled) {
            Log.d(tag, "" + msg)
        }
    }

    fun e(tag: String, t: Throwable?) {
        if (sEnabled) {
            Log.e(tag, "exception", t)
        }
    }

    fun e(tag: String, msg: String, t: Throwable?) {
        if (sEnabled) {
            Log.e(tag, "" + msg, t)
        }
    }

    fun e(tag: String, msg: String) {
        if (sEnabled) {
            Log.e(tag, "" + msg)
        }
    }

    fun i(tag: String, msg: String) {
        if (sEnabled) {
            Log.i(tag, "" + msg)
        }
    }

    fun i(msg: String) {
        if (sEnabled) {
            Log.i(sDefaultTag, "" + msg)
        }
    }

    fun w(tag: String, msg: String) {
        if (sEnabled) {
            Log.w(tag, "" + msg)
        }

    }

    @JvmStatic
    fun printStackTrace(t: Throwable?) {
        if (sEnabled && t != null) t.printStackTrace()
    }
}