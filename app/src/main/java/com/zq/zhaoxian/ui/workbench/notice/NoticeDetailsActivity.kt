package com.zq.zhaoxian.ui.workbench.notice


import com.google.gson.Gson
import com.tencent.mmkv.MMKV
import com.zq.base.activity.BaseActivity
import com.zq.zhaoxian.R
import com.zq.zhaoxian.databinding.AppActivityNoticeDetailsBinding
import com.zq.zhaoxian.http.model.UserInfo

class NoticeDetailsActivity : BaseActivity<NoticeViewModel, AppActivityNoticeDetailsBinding>() {

    override val layoutId: Int = R.layout.app_activity_notice_details
    val dataBinding by lazy {
        getDataBind()
    }

    override fun initView() {
        dataBinding.toolbar.setBackOnClickListener {
            finish()
        }
        val noticeModel: NoticeModel = intent.getSerializableExtra("data") as NoticeModel
        dataBinding.data = noticeModel
        val boolean = MMKV.defaultMMKV().decodeBool("isLogin")
        if (boolean) {
            val string = MMKV.defaultMMKV().decodeString("userInfo")

            if (string != null) {
                val info = Gson().fromJson(string, UserInfo.Info::class.java)
                getViewModel().updateNotic(info.id, noticeModel.NoticeId)
            }

        }
    }

    override fun initData() {

    }
}