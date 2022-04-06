package com.zq.base.activity

import android.content.Intent
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.XXPermissions
import com.hjq.toast.ToastUtils
import com.zq.base.permission.RequestPermissionListener
import com.zq.base.view.CommonDialog
import com.zq.base.viewmodel.BaseViewModel

/**
 * @program: mvvm
 *
 * @description:
 *
 * @author: 闫世豪
 *
 * @create: 2021-03-03 17:34
 */
abstract class BasePermissionActivity<VM : BaseViewModel, DB : ViewDataBinding> :
    BaseActivity<VM, DB>(), OnPermissionCallback {
    private var mCommonDialog: CommonDialog? = null
    private var mListener: RequestPermissionListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCommonDialog = CommonDialog(mActivityContext)
    }

    protected fun checkPermission(
        listener: RequestPermissionListener?,
        vararg permissions: String?
    ) {
        mListener = listener
        XXPermissions.with(this)
            .permission(*permissions)
            .request(this)
    }

    override fun onGranted(permissions: MutableList<String>?, all: Boolean) {
        if (mListener != null && all) {
            mListener!!.agreeAllPermission() //同意了全部权限的回调
        } else {
            //  ToastUtils.show("获取部分权限成功，但部分权限未正常授予")
        }
    }

    override fun onDenied(permissions: MutableList<String>?, never: Boolean) {
        super.onDenied(permissions, never)
        if (never) { //被永久拒绝授权，
            //缺少权限
            mCommonDialog!!.showDialog("当前应用缺少必要权限。\n请点击\"设置\"-\"权限\"-打开所需权限。",
                "设置",
                "取消",
                { _, _ ->
                    XXPermissions.startPermissionActivity(
                        mActivityContext,
                        permissions
                    )
                }) { _, _ -> mCommonDialog!!.dismiss() }
        } else {
            ToastUtils.show("获取权限失败")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == XXPermissions.REQUEST_CODE) {
//            if (XXPermissions.isGranted(this, Permission.RECORD_AUDIO) &&
//                XXPermissions.isGranted(this, Permission.Group.CALENDAR)
//            ) {
//                //"用户已经在权限设置页授予了录音和日历权限"
//            } else {
//                //"用户没有在权限设置页授予权限"
//            }
            mListener?.agreeAllPermission()
        }
    }

}