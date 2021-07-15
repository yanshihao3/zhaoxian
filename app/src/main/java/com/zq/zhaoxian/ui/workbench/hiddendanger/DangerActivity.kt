package com.zq.zhaoxian.ui.workbench.hiddendanger

import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.view.TimePickerView
import com.hjq.toast.ToastUtils

import com.zq.base.activity.BaseActivity
import com.zq.zhaoxian.R
import com.zq.zhaoxian.common.MessageEvent
import com.zq.zhaoxian.databinding.AppActivityDangerBinding
import com.zq.zhaoxian.http.HomeNetWork
import com.zq.zhaoxian.ui.my.CommitDialogFragment
import com.zq.zhaoxian.utils.toRequestBody
import com.zq.zhaoxian.view.recycle.FormLayoutManager
import com.zq.zhaoxian.view.recycle.FormScrollHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.greenrobot.eventbus.EventBus

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


/**
 * 隐患排查任务
 */
@AndroidEntryPoint
class DangerActivity : BaseActivity<ViewModel, AppActivityDangerBinding>() {


    override val layoutId: Int = R.layout.app_activity_danger

    private val list = mutableListOf<DangerModel.Info>()

    @Inject
    lateinit var commitDialogFragment: CommitDialogFragment
    private lateinit var adapter: MonsterHAdapter
    private lateinit var pvTime: TimePickerView
    override fun initView() {

        mDataBind.toolbar.setBackOnClickListener {
            finish()
        }


        val mRecyclerView = mDataBind.rvHorizontal
        val rvTitle = mDataBind.rvTitle

        rvTitle.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val titles = resources.getStringArray(R.array.form_title)
        val titleList = listOf(*titles)
        val rightTitleAdapter = TitleAdapter(titleList)
        rvTitle.adapter = rightTitleAdapter

        val layoutManager = FormLayoutManager(5, mRecyclerView)
        mRecyclerView.layoutManager = layoutManager

        adapter = MonsterHAdapter(this)
        mRecyclerView.adapter = adapter


        val formScrollHelper = FormScrollHelper()
        formScrollHelper.connectRecyclerView(mRecyclerView)
        formScrollHelper.connectRecyclerView(rvTitle)
        initTimePicker()
    }

    override fun initData() {
        val taskInfo = intent.getSerializableExtra("data") as TaskModel.TaskInfo
        mViewModel.loadInitial(taskInfo.id)
        mDataBind.data = taskInfo
        mViewModel.data.observe(this) {
            list.clear()
            list.addAll(it.info)
            adapter.mList = list
            adapter.notifyDataSetChanged()
        }

        mDataBind.time.setOnClickListener {
            pvTime.show()

        }
        mDataBind.btn.setOnClickListener {
            if (mDataBind.time.text.toString()
                    .isEmpty() || mDataBind.time.text.toString() == "请选择时间"
            ) {
                ToastUtils.show("请选择时间")
                return@setOnClickListener
            }
            lifecycleScope.launch {
                commitDialogFragment.show(supportFragmentManager, "")
                val params = hashMapOf<String, Any>()
                params["id"] = taskInfo.id
                params["data"] = mDataBind.time.text.toString()
                withContext(Dispatchers.IO) {
                    val result = HomeNetWork.getInstance().rectification(params.toRequestBody())
                    if (result.resCode == "0") {
                        ToastUtils.show("整改成功")
                        EventBus.getDefault().postSticky(MessageEvent())
                    }
                }
                commitDialogFragment.dismiss()
            }
        }
        mDataBind.cancel.setOnClickListener {
            finish()
        }
    }

    private fun initTimePicker() {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val startDate: Calendar = Calendar.getInstance()
        val endDate = Calendar.getInstance()
        endDate.set(2100, 12, 31)
        pvTime = TimePickerBuilder(this) { date, _ ->
            mDataBind.time.text = simpleDateFormat.format(date)
        }.setType(booleanArrayOf(true, true, true, false, false, false)) //分别对应年月日时分秒，默认全部显示
            .setTitleText("选择时间") //标题文字
            .setOutSideCancelable(false) //点击屏幕，点在控件外部范围时，是否取消显示
            .setRangDate(startDate, endDate) //起始终止年月日设定
            .setLabel(" ", " ", " ", " ", " ", " ")
            .build()
    }


}