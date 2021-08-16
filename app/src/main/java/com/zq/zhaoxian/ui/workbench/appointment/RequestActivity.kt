package com.zq.zhaoxian.ui.workbench.appointment

import android.content.Intent
import android.graphics.Color
import coil.load
import com.yuyh.library.imgsel.ISNav
import com.yuyh.library.imgsel.config.ISListConfig
import com.zq.base.activity.BaseNoModelActivity
import com.zq.zhaoxian.R
import com.zq.zhaoxian.databinding.AppActivityRequestBinding
import com.zq.zhaoxian.utils.FileUtils
import java.util.ArrayList

class RequestActivity : BaseNoModelActivity<AppActivityRequestBinding>() {
    private lateinit var config: ISListConfig

    override val layoutId: Int
        get() = R.layout.app_activity_request
    val dataBinding by lazy {
        getDataBind()
    }
    override fun initView() {

        dataBinding.toolbar.setBackOnClickListener {
            finish()
        }
        // 自定义图片加载器
        ISNav.getInstance().init { _, path, imageView ->
            imageView.load(FileUtils.getImageContentUri(mActivityContext, path))
        }
        dataBinding.delete.setOnClickListener {


        }
        dataBinding.faceImage.setOnClickListener {
            upImage()
        }
        dataBinding.btnCommit.setOnClickListener {


        }
    }

    override fun initData() {
        initImagePicker()
    }

    private fun initImagePicker() {
        // 自由配置选项
        config = ISListConfig.Builder()
            // 是否多选, 默认true
            .multiSelect(false)
            // “确定”按钮背景色
            .btnBgColor(Color.parseColor("#3F51B5"))
            // “确定”按钮文字颜色
            .btnTextColor(Color.WHITE)
            // 使用沉浸式状态栏
            .statusBarColor(Color.parseColor("#3F51B5"))
            // 返回图标ResId
            .backResId(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
            // 标题
            .title("图片")
            // 标题文字颜色
            .titleColor(Color.WHITE)
            // TitleBar背景色
            .titleBgColor(Color.parseColor("#3F51B5"))
            // 裁剪大小。needCrop为true的时候配置
            .cropSize(1, 1, 200, 200)
            .needCrop(true)
            // 第一个是否显示相机，默认true
            .needCamera(true)
            // 最大选择图片数量，默认9
            .maxNum(1)
            .build()
    }

    private fun upImage() {
        ISNav.getInstance().toListActivity(this, config, REQUEST_LIST_CODE);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // 图片选择结果回调
        if (requestCode === REQUEST_LIST_CODE && resultCode === RESULT_OK && data != null) {
            val pathList: ArrayList<String>? = data.getStringArrayListExtra("result")

        }
    }

    companion object {
        const val REQUEST_LIST_CODE = 0
    }
}