package com.zq.zhaoxian.ui.workbench.alarm


import androidx.core.widget.addTextChangedListener
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.view.TimePickerView
import com.zq.base.activity.BaseNoModelActivity
import com.zq.zhaoxian.R
import com.zq.zhaoxian.databinding.AppActivityAlarmHandleBinding
import java.text.SimpleDateFormat
import java.util.*

/**
 * 处理界面
 */
class AlarmHandleActivity : BaseNoModelActivity<AppActivityAlarmHandleBinding>() {

    override val layoutId: Int = R.layout.app_activity_alarm_handle

    val dataBinding by lazy { getDataBind() }

    private lateinit var pvTime: TimePickerView

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
            setResult(2)
            finish()
        }
    }

    private fun initTimePicker() {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
        val startDate: Calendar = Calendar.getInstance()
        val endDate = Calendar.getInstance()
        endDate.set(2100, 12, 31)
        pvTime = TimePickerBuilder(this) { date, _ ->
            dataBinding.time.text = simpleDateFormat.format(date)
        }.setType(booleanArrayOf(true, true, true, true, true, false)) //分别对应年月日时分秒，默认全部显示
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