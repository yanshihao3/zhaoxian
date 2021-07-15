package com.zq.zhaoxian.ui.home

import com.zq.base.activity.BaseNoModelActivity
import com.zq.zhaoxian.R
import com.zq.zhaoxian.databinding.ActivityWebviewBinding

class MainWebViewActivity : BaseNoModelActivity<ActivityWebviewBinding>() {
    private var mTitle: String? = ""
    private var mUrl: String? = ""

    public override fun initView() {
        mDataBind.wvLoading.addProgressBar()
        mDataBind.toolbar.setBackOnClickListener {
            finish()
        }
    }

    public override fun initData() {
        mTitle = intent.getStringExtra("title")
        mUrl = intent.getStringExtra("url")
        mDataBind.wvLoading.loadMessageUrl(mUrl)
    }


    public override fun onDestroy() {
        super.onDestroy()
        mDataBind.wvLoading.destroyWebView()
    }

    /**
     * 按返回键时， 不退出程序而是返回WebView的上一页面
     */
    override fun onBackPressed() {
        if (mDataBind.wvLoading.canGoBack()) mDataBind.wvLoading.goBack() else {
            super.onBackPressed()
        }
    }

    override val layoutId: Int = R.layout.activity_webview
}