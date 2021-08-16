package com.zq.zhaoxian.ui.my

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hjq.toast.ToastUtils
import com.tencent.mmkv.MMKV
import com.zq.base.activity.BaseNoModelActivity
import com.zq.base.view.BaseDialogFragment
import com.zq.zhaoxian.R
import com.zq.zhaoxian.databinding.AppActivityAccountBinding
import com.zq.zhaoxian.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * 账号安全界面
 */
@AndroidEntryPoint
class AccountActivity : BaseNoModelActivity<AppActivityAccountBinding>() {


    override val layoutId: Int = R.layout.app_activity_account

    @Inject
    lateinit var logoutDialogFragment: LogoutDialogFragment

    override fun initView() {
        mDataBind.toolbar.setBackOnClickListener {
            finish()
        }
        logoutDialogFragment.setCanceledOnTouchOutside(false)
        logoutDialogFragment.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT)
        logoutDialogFragment.logoutListener = object : LogoutListener {
            override fun logout() {
                startActivity(Intent(mActivityContext, LoginActivity::class.java))
                finish()
            }
        }
        mDataBind.phone.setOnClickListener {
            ToastUtils.show("该功能尚未开放")
        }
        mDataBind.password.setOnClickListener {
            ToastUtils.show("该功能尚未开放")
        }
        mDataBind.logout.setOnClickListener {
            logoutDialogFragment.show(supportFragmentManager, "")
        }
    }

    override fun initData() {

    }
}

class LogoutDialogFragment @Inject constructor() : BaseDialogFragment() {

    override fun getLayoutId(): Int = R.layout.app_dialog_logout

    lateinit var logoutListener: LogoutListener

    override fun initViews(v: View?) {
        v?.findViewById<TextView>(R.id.cancel)?.setOnClickListener {
            dismiss()
        }

        v?.findViewById<TextView>(R.id.commit)?.setOnClickListener {
            //退出登录
            val defaultMMKV = MMKV.defaultMMKV()
            defaultMMKV.encode("isLogin", false)

            dismiss()
            if (::logoutListener.isInitialized) {
                logoutListener.logout()
            }

        }
    }

}

interface LogoutListener {
    fun logout()
}