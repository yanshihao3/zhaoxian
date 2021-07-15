package com.zq.base.view

import android.app.Activity
import android.content.DialogInterface
import android.view.View
import androidx.appcompat.app.AlertDialog

/**
 * 通用确认提示框
 * 主要封装了一下show和dismiss方法
 *
 * @author yanshihao
 * 2018.5.22
 */
class CommonDialog(private val mContext: Activity) {
    private val TAG = "CommonDialog"
    private var mDialog //APP版本升级提示专用弹框
            : AlertDialog? = null

    private fun initDialog() {
        if (null == mDialog) {
            val builder = AlertDialog.Builder(mContext)
            builder.setCancelable(false)
            mDialog = builder.create()
        }
    }

    fun showTipDialog(
        msg: String?,
        okBtnText: String?,
        onClickListener: DialogInterface.OnClickListener?
    ) {
        showTipDialog("温馨提示", msg, okBtnText, onClickListener)
    }

    fun showTipDialog(
        title: String?,
        msg: String?,
        okBtnText: String?,
        onClickListener: DialogInterface.OnClickListener?
    ) {
        initDialog()
        mDialog!!.setTitle(title)
        mDialog!!.setMessage(msg)
        mDialog!!.setCancelable(false)
        mDialog!!.setButton(AlertDialog.BUTTON_POSITIVE, okBtnText, onClickListener)
        show()
    }

    fun showDialog(
        msg: String?,
        leftBtnText: String?,
        rightBtnText: String?,
        leftOnClickListener: DialogInterface.OnClickListener?,
        rightOnClickListener: DialogInterface.OnClickListener?
    ) {
        showDialog(
            "温馨提示",
            msg,
            leftBtnText,
            rightBtnText,
            leftOnClickListener,
            rightOnClickListener
        )
    }

    fun showDialog(
        title: String?,
        msg: String?,
        leftBtnText: String?,
        rightBtnText: String?,
        leftOnClickListener: DialogInterface.OnClickListener?,
        rightOnClickListener: DialogInterface.OnClickListener?
    ) {
        initDialog()
        mDialog!!.setTitle(title)
        mDialog!!.setMessage(msg)
        mDialog!!.setCancelable(false)
        mDialog!!.setButton(AlertDialog.BUTTON_NEGATIVE, leftBtnText, leftOnClickListener)
        mDialog!!.setButton(AlertDialog.BUTTON_POSITIVE, rightBtnText, rightOnClickListener)
        show()
    }

    fun setContentView(layoutId: Int): CommonDialog {
        initDialog()
        mDialog!!.setView(View.inflate(mContext, layoutId, null))
        return this
    }

    val dialog: AlertDialog?
        get() {
            initDialog()
            return mDialog
        }

    fun setContentView(v: View?): CommonDialog {
        initDialog()
        mDialog!!.setView(v)
        return this
    }

    fun setTitle(title: String?): CommonDialog {
        initDialog()
        mDialog!!.setTitle(title)
        return this
    }

    fun setMessage(msg: String?): CommonDialog {
        initDialog()
        mDialog!!.setMessage(msg)
        return this
    }

    fun setButton(
        whichButton: Int,
        text: CharSequence?,
        listener: DialogInterface.OnClickListener?
    ): CommonDialog {
        initDialog()
        mDialog!!.setButton(whichButton, text, listener)
        return this
    }

    fun setButton(iconResId: Int): CommonDialog {
        initDialog()
        mDialog!!.setIcon(iconResId)
        return this
    }

    fun setCancelable(cancelable: Boolean): CommonDialog {
        initDialog()
        mDialog!!.setCancelable(cancelable)
        return this
    }

    fun setCanceledOnTouchOutside(cancelable: Boolean): CommonDialog {
        initDialog()
        mDialog!!.setCanceledOnTouchOutside(cancelable)
        return this
    }

    fun show() {
        if (!mContext.isFinishing && null != mDialog && !mDialog!!.isShowing) {
            mDialog!!.show()
        } else {
        }
    }

    fun dismiss() {
        if (!mContext.isFinishing && null != mDialog && mDialog!!.isShowing) {
            mDialog!!.dismiss()
            mDialog = null
        } else {
        }
    }

    init {
        initDialog()
    }
}