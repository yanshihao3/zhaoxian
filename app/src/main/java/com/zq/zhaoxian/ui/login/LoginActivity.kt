package com.zq.zhaoxian.ui.login


import android.content.Intent
import android.view.KeyEvent
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.hjq.toast.ToastUtils
import com.tencent.mmkv.MMKV
import com.zq.base.activity.BaseNoModelActivity
import com.zq.zhaoxian.R
import com.zq.zhaoxian.application.MyActivityManager
import com.zq.zhaoxian.databinding.AppActivityLoginBinding
import com.zq.zhaoxian.http.HomeNetWork
import com.zq.zhaoxian.http.model.UserInfo
import com.zq.zhaoxian.ui.AppMainActivity
import com.zq.zhaoxian.ui.my.CommitDialogFragment
import com.zq.zhaoxian.utils.PhoneFormatCheckUtils
import com.zq.zhaoxian.utils.toRequestBody
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject
import kotlin.system.exitProcess

@AndroidEntryPoint

class LoginActivity : BaseNoModelActivity<AppActivityLoginBinding>() {


    override val layoutId: Int = R.layout.app_activity_login

    @Inject
    lateinit var commitDialogFragment: LoginDialogFragment
    val dataBinding by lazy {
        getDataBind()
    }
    override fun initView() {
        dataBinding.btn.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val phone = dataBinding.loginPhone.text.toString()
        if (PhoneFormatCheckUtils.isChinaPhoneLegal(phone)) {
            commit(phone)
        } else {
            ToastUtils.show("手机号输入有误，请重新输入")
        }
    }

    override fun initData() {

    }


    fun commit(phone: String) {
        lifecycleScope.launch {
            try {
                commitDialogFragment.show(supportFragmentManager, "")
                var userInfo: UserInfo? = null
                withContext(Dispatchers.IO) {

                    val params = hashMapOf<String, String>()
                    params["phoneNumber"] = phone
                    userInfo = HomeNetWork.getInstance().queryLogin(params.toRequestBody())

                }
                if (userInfo == null || userInfo!!.result.list.isEmpty()) {
                    ToastUtils.show("当前系统无此用户，请重新输入手机号")
                }
                userInfo?.let {
                    if (it.result.list.isNotEmpty()) {
                        ToastUtils.show("登录成功")
                        //登录成功
                        val mmkv = MMKV.defaultMMKV()
                        mmkv.encode("isLogin",true)
                        mmkv.encode("phone",phone)
                        mmkv.encode("userInfo",Gson().toJson(it.result.list[0]))

                        startActivity(Intent(mActivityContext, AppMainActivity::class.java))
                        finish()
                    }
                }

            } catch (e: Exception) {
                ToastUtils.show("系统异常，请重试")
            } finally {
                commitDialogFragment.dismiss()
            }
        }
    }

    private var exitTime: Long = 0 //退出activity计时

    //双击退出app事件
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                try {
                    ToastUtils.show("再按一次返回键确认退出")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                exitTime = System.currentTimeMillis()
            } else {
                MyActivityManager.getInstance().exitApp()
            }
            return false
        }
        return super.onKeyDown(keyCode, event)
    }
}