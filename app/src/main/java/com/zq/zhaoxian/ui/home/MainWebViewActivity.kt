package com.zq.zhaoxian.ui.home

import com.zq.base.activity.BaseNoModelActivity
import com.zq.zhaoxian.R
import com.zq.zhaoxian.databinding.ActivityWebviewBinding

class MainWebViewActivity : BaseNoModelActivity<ActivityWebviewBinding>() {
    private var mTitle: String? = ""
    private var mUrl: String? = ""
    val dataBinding by lazy {
        getDataBind()
    }
    public override fun initView() {
        dataBinding.wvLoading.addProgressBar()
        dataBinding.toolbar.setBackOnClickListener {
            finish()
        }
    }

    public override fun initData() {
        mTitle = intent.getStringExtra("title")
        mUrl = intent.getStringExtra("url")
        dataBinding.wvLoading.loadMessageUrl(mUrl)
    }


    public override fun onDestroy() {
        super.onDestroy()
        dataBinding.wvLoading.destroyWebView()
    }

    /**
     * 按返回键时， 不退出程序而是返回WebView的上一页面
     */
    override fun onBackPressed() {
        if (dataBinding.wvLoading.canGoBack()) dataBinding.wvLoading.goBack() else {
            super.onBackPressed()
        }
    }

    override val layoutId: Int = R.layout.activity_webview
}