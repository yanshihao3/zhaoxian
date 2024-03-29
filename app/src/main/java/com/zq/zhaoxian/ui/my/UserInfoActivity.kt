package com.zq.zhaoxian.ui.my

import com.google.gson.Gson
import com.tencent.mmkv.MMKV
import com.zq.base.activity.BaseNoModelActivity
import com.zq.zhaoxian.R
import com.zq.zhaoxian.databinding.AppActivityUserInfoBinding
import com.zq.zhaoxian.http.model.UserInfo

/**
 * 个人信息界面
 */
class UserInfoActivity : BaseNoModelActivity<AppActivityUserInfoBinding>() {

    override val layoutId: Int = R.layout.app_activity_user_info
    val dataBinding by lazy {
        getDataBind()
    }
    override fun initView() {
        val string = MMKV.defaultMMKV().decodeString("userInfo")

        if (string != null) {
            val info = Gson().fromJson(string, UserInfo.Info::class.java)
            dataBinding.name.text = info.personName
            dataBinding.time.text = info.dateOfBirth
            dataBinding.sex.text = when (info.gender) {
                "MALE" -> "男"
                "FEMALE" -> "女"
                else -> "男"
            }
        }
        dataBinding.toolbar.setBackOnClickListener {
            finish()
        }
    }

    override fun initData() {
    }
}