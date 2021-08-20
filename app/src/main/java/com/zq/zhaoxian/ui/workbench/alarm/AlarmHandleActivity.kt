package com.zq.zhaoxian.ui.workbench.alarm


import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.view.TimePickerView
import com.hjq.toast.ToastUtils
import com.zq.base.activity.BaseNoModelActivity
import com.zq.zhaoxian.R
import com.zq.zhaoxian.common.MessageEvent
import com.zq.zhaoxian.databinding.AppActivityAlarmHandleBinding
import com.zq.zhaoxian.http.HomeNetWork
import com.zq.zhaoxian.ui.my.CommitDialogFragment
import com.zq.zhaoxian.utils.toRequestBody
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.greenrobot.eventbus.EventBus
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * 处理界面
 */
@AndroidEntryPoint
class AlarmHandleActivity : BaseNoModelActivity<AppActivityAlarmHandleBinding>() {

    override val layoutId: Int = R.layout.app_activity_alarm_handle

    val dataBinding by lazy { getDataBind() }

    private lateinit var pvTime: TimePickerView

    val workOrderId by lazy {
        intent.getStringExtra("workorderId ")
    }

    @Inject
    lateinit var commitDialogFragment: CommitDialogFragment

    override fun initView() {
        dataBinding.toolbar.setBackOnClickListener {
            finish()
        }

        dataBinding.desc.addTextChangedListener {
            dataBinding.number.text = "${it?.length}/100"
        }

        dataBinding.time.setOnClickListener {
            pvTime.show()
        }
        dataBinding.cancel.setOnClickListener {
            finish()
        }
        dataBinding.btn.setOnClickListener {
            request()
        }
    }

    private fun request() {
        if (dataBinding.time.text.toString().trim()
                .isEmpty() || dataBinding.time.text.toString() == "点击选择时间"
        ) {
            ToastUtils.show("请选择时间")
            return
        }
        if (dataBinding.desc.text.toString().trim().isEmpty()
        ) {
            ToastUtils.show("请填写处理描述")
            return
        }
        lifecycleScope.launch {
            try {
                commitDialogFragment.show(supportFragmentManager, "")
                val params = hashMapOf<String, Any>()
                params["workorderId"] = workOrderId!!
                params["description"] = dataBinding.desc.text.toString().trim()
                params["time"] = dataBinding.time.text.toString()
                val result = withContext(Dispatchers.IO) {
                    HomeNetWork.getInstance().saveProcessWork(params.toRequestBody())
                }
                if (result.data().code == "200") {
                    ToastUtils.show("处理成功")
                    EventBus.getDefault().post(MessageEvent("home", "0"))
                    setResult(2)
                    finish()
                } else {
                    ToastUtils.show("处理失败")
                }
            } catch (e: Exception) {
                ToastUtils.show("网络异常")
            } finally {
                commitDialogFragment.dismiss()
            }
        }

    }

    private fun initTimePicker() {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val startDate: Calendar = Calendar.getInstance()
        val endDate = Calendar.getInstance()
        endDate.set(2100, 12, 31)
        pvTime = TimePickerBuilder(this) { date, _ ->
            dataBinding.time.text = simpleDateFormat.format(date)
        }.setType(booleanArrayOf(true, true, true, false, false, false)) //分别对应年月日时分秒，默认全部显示
            .setTitleText("选择时间") //标题文字
            .setOutSideCancelable(false) //点击屏幕，点在控件外部范围时，是否取消显示
            .setRangDate(startDate, endDate) //起始终止年月日设定
            .setLabel(" ", " ", " ", " ", " ", " ")
            .build()
    }

    override fun initData() {
        initTimePicker()
    }
}

