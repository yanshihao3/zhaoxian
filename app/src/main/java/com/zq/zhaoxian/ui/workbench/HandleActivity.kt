package com.zq.zhaoxian.ui.workbench

import android.content.Intent
import android.graphics.Color
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import coil.load
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.view.TimePickerView
import com.hjq.toast.ToastUtils
import com.yuyh.library.imgsel.ISNav
import com.yuyh.library.imgsel.config.ISListConfig
import com.zq.base.activity.BaseActivity
import com.zq.base.utils.AppUtils
import com.zq.zhaoxian.R
import com.zq.zhaoxian.databinding.AppActivityHandleBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * 处理任务的界面
 */
@AndroidEntryPoint
class HandleActivity : BaseActivity<HandleViewModel, AppActivityHandleBinding>() {

    val IMAGE_SIZE = 3 //可添加图片最大数

    override val layoutId: Int = R.layout.app_activity_handle

    @Inject
    lateinit var adapter: ImageAdapter

    private lateinit var config: ISListConfig

    private val REQUEST_LIST_CODE = 0

    //添加按钮图片资源
    private var plusPath = ""
    private lateinit var pvTime: TimePickerView

    private val list = mutableListOf<String>()
    lateinit var workbenchModel: WorkbenchModel
    override fun initView() {
        getDataBind().toolbar.setBackOnClickListener {
            finish()
        }
        plusPath =
            getString(R.string.glide_plus_icon_string) + AppUtils.getPackageInfo(mActivityContext).packageName.toString() + "/mipmap/" + R.mipmap.app_ic_add_image

        // 自定义图片加载器
        ISNav.getInstance().init { _, path, imageView ->
            imageView.load(File(path))
        }
        getDataBind().recyclerView.adapter = adapter
        getDataBind().recyclerView.layoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        adapter.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.delete -> {
                    list.removeAt(position)
                    adapter.notifyDataSetChanged()
                }
                R.id.image -> {
                    if (list[position].contains(getString(R.string.glide_plus_icon_string))) {//打开相册
                        initImagePicker()
                        ISNav.getInstance().toListActivity(this, config, REQUEST_LIST_CODE)
                    } else { //预览照片

                    }
                }
            }
        }
        initTimePicker()
        getDataBind().time.setOnClickListener {
            pvTime.show()
        }
        getDataBind().btn.setOnClickListener {
            val handleMethod = getDataBind().handleMethod.text.toString()
            val time = getDataBind().time.text
            if (handleMethod.isEmpty()) {
                ToastUtils.show("请填写处理方式")
                return@setOnClickListener
            }
            if (time == "请选择时间") {
                ToastUtils.show("请选择处理时间")
                return@setOnClickListener
            }
            val data = mutableListOf<String>()
            list.forEach {
                if (!it.contains(getString(R.string.glide_plus_icon_string))) {
                    data.add(it)
                }
            }
            getViewModel().commit(workbenchModel.id, data)
        }
    }

    private fun initTimePicker() {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
        val startDate: Calendar = Calendar.getInstance()
        val endDate = Calendar.getInstance()
        endDate.set(2100, 12, 31)
        pvTime = TimePickerBuilder(this) { date, _ ->
            getDataBind().time.text = simpleDateFormat.format(date)
        }.setType(booleanArrayOf(true, true, true, true, true, false)) //分别对应年月日时分秒，默认全部显示
            .setTitleText("选择时间") //标题文字
            .setOutSideCancelable(false) //点击屏幕，点在控件外部范围时，是否取消显示
            .setRangDate(startDate, endDate) //起始终止年月日设定
            .setLabel(" ", " ", " ", " ", " ", " ")
            .build()
    }


    private fun initImagePicker() {
        // 自由配置选项
        config = ISListConfig.Builder()
            // 是否多选, 默认true
            .multiSelect(true)
            // 是否记住上次选中记录, 仅当multiSelect为true的时候配置，默认为true
            .rememberSelected(false)
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
            .needCamera(true)
            // 最大选择图片数量，默认9
            .maxNum(IMAGE_SIZE - list.size + 1)
            .build()
    }

    override fun initData() {
        list.add(plusPath)
        adapter.data = list
        workbenchModel = intent.getSerializableExtra("data") as WorkbenchModel
        getDataBind().data = workbenchModel
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // 图片选择结果回调
        if (requestCode === REQUEST_LIST_CODE && resultCode === RESULT_OK && data != null) {
            val pathList: ArrayList<String>? = data.getStringArrayListExtra("result")
            if (pathList != null) {
                list.addAll(0, pathList)
                adapter.data = list
                adapter.notifyDataSetChanged()
            }
        }
    }
}