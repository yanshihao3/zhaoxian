package com.zq.zhaoxian.ui


import android.content.Intent
import com.hjq.permissions.Permission
import com.tencent.mmkv.MMKV
import com.zq.base.activity.BasePermissionActivity
import com.zq.base.permission.RequestPermissionListener
import com.zq.zhaoxian.R
import com.zq.zhaoxian.databinding.AppActivitySplashBinding
import com.zq.zhaoxian.ui.login.LoginActivity
import com.zq.zhaoxian.viewmodel.SplashViewModel


class SplashActivity : BasePermissionActivity<SplashViewModel, AppActivitySplashBinding>(),
    RequestPermissionListener {


    override val layoutId: Int = R.layout.app_activity_splash

    override fun initView() {
    }

    override fun onResume() {
        super.onResume()
        checkPermission(
            this,
            Permission.WRITE_EXTERNAL_STORAGE,
            Permission.CAMERA
        )
    }

    override fun initData() {

    }

    override fun agreeAllPermission() {
        toMain()
    }

    override fun agreePermission() {
        toMain()
    }


    override fun onDenied() {
        toMain()
    }

    private fun toMain() {
        mViewModel.load()
        val kv = MMKV.defaultMMKV()
        val boolean = kv.decodeBool("isLogin", false)
        if (boolean) {
            startActivity(Intent(this, AppMainActivity::class.java))
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        finish()
    }
}