package com.zq.zhaoxian.ui.my


import android.content.Intent
import com.google.gson.Gson
import com.gyf.immersionbar.ktx.immersionBar
import com.tencent.mmkv.MMKV
import com.zq.base.fragment.BaseLazyFragment
import com.zq.zhaoxian.R
import com.zq.zhaoxian.databinding.AppFragmentMyBinding
import com.zq.zhaoxian.http.model.UserInfo
import com.zq.zhaoxian.ui.login.LoginActivity
import javax.inject.Inject

class MyFragment @Inject constructor() : BaseLazyFragment<MyViewModel, AppFragmentMyBinding>() {
    override val layoutId: Int = R.layout.app_fragment_my
    val dataBinding by lazy {
        getDataBind()
    }
    override fun initView() {
        dataBinding.about.setOnClickListener {
            startActivity(Intent(mContext, AboutActivity::class.java))

        }
        dataBinding.account.setOnClickListener {

            startActivityForResult(Intent(mContext, AccountActivity::class.java), 0)
        }
        dataBinding.opinion.setOnClickListener {

            startActivity(Intent(mContext, OpinionActivity::class.java))
        }
        dataBinding.userName.setOnClickListener {
            //用户没有登录就去登录 登录了就去userinfo界面
            val boolean = MMKV.defaultMMKV().decodeBool("isLogin")
            if (boolean) {
                startActivity(Intent(mContext, UserInfoActivity::class.java))
            } else {
                startActivityForResult(Intent(mContext, LoginActivity::class.java), 0)
            }
        }
    }

    override fun initData() {
        val boolean = MMKV.defaultMMKV().decodeBool("isLogin")
        if (boolean) {
            val string = MMKV.defaultMMKV().decodeString("userInfo")
            if (string != null) {
                val info = Gson().fromJson(string, UserInfo.Info::class.java)
                dataBinding.userName.text = info.personName
                dataBinding.phone.text = info.phone
            }
        }
    }

    override fun onFragmentFirstVisible() {

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val string = MMKV.defaultMMKV().decodeString("userInfo")
        val boolean = MMKV.defaultMMKV().decodeBool("isLogin")
        if (boolean) {
            if (string != null) {
                val info = Gson().fromJson(string, UserInfo.Info::class.java)
                dataBinding.userName.text = info.personName
                dataBinding.phone.text = info.phone
            }
        } else {
            dataBinding.userName.text = "登录"
            dataBinding.userName.text = "**********"
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = MyFragment()
    }

    override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar {
            titleBar(dataBinding.view)
            transparentStatusBar()
            statusBarDarkFont(true, 0.2f)
        }
    }

}