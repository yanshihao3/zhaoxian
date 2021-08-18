package com.zq.zhaoxian.ui.workbench.alarm

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.tencent.mmkv.MMKV
import com.zq.base.decoration.SpacesItemDecoration
import com.zq.base.fragment.BaseLazyFragment
import com.zq.zhaoxian.R
import com.zq.zhaoxian.databinding.AppFragmentAlarmWorkBinding
import com.zq.zhaoxian.http.model.UserInfo
import com.zq.zhaoxian.ui.workbench.hiddendanger.DangerDetailActivity
import com.zq.zhaoxian.ui.workbench.hiddendanger.DangerHandleActivity
import com.zq.zhaoxian.ui.workbench.hiddendanger.TaskModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val ARG_PARAM = "param"

@AndroidEntryPoint
class AlarmWorkFragment : BaseLazyFragment<AlarmViewModel, AppFragmentAlarmWorkBinding>() {

    private var param: String? = null

    private var status: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param = it.getString(ARG_PARAM)
            status = if (param == "0") {
                "待处理"
            } else {
                "已完成"
            }
        }
    }

    override val layoutId: Int
        get() = R.layout.app_fragment_alarm_work

    @Inject
    lateinit var adapter: AlarmAdapter

    private var isRefresh = true

    private val dataList = mutableListOf<TaskModel.TaskInfo>()

    var userId = ""

    var isFirst = false
    
    val dataBinding by lazy {
        getDataBind()
    }
    val viewModel by lazy { 
        getViewModel()
    }

    override fun initView() {
        dataBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        dataBinding.recyclerView.adapter = adapter
        dataBinding.recyclerView.addItemDecoration(
            SpacesItemDecoration(context).setParam(
                resources.getColor(
                    R.color.line_color
                ), 1.0f
            )
        )
        dataBinding.refreshLayout.setEnableLoadMore(true)
        dataBinding.refreshLayout.setOnLoadMoreListener {
            isRefresh = false
            viewModel.loadMore(userId, status!!)
        }
        dataBinding.refreshLayout.setOnRefreshListener {
            isRefresh = true
            viewModel.loadRefresh(userId, status!!)
            dataBinding.refreshLayout.finishRefresh()
        }
        viewModel.data.observe(this) {
            if (isRefresh) {
                dataList.clear()
                dataList.addAll(it.taskInfo)
                adapter.data = dataList
                adapter.notifyDataSetChanged()
            } else {
                dataList.addAll(it.taskInfo)
                if (it.taskInfo.isEmpty()) {
                    dataBinding.refreshLayout.finishLoadMoreWithNoMoreData()
                } else {
                    dataBinding.refreshLayout.finishLoadMore()
                }
                adapter.data = dataList
                adapter.notifyDataSetChanged()
            }


        }
    }

    override fun initData() {

        adapter.setOnItemChildClickListener { _, _, position ->
            if (param == "0") {
                val intent = Intent(context, AlarmDetailHandleActivity::class.java)
                intent.putExtra("data", dataList[position])
                startActivity(intent)
            } else {
                val intent = Intent(context, AlarmDetailActivity::class.java)
                intent.putExtra("data", dataList[position])
                startActivity(intent)
            }
        }

        adapter.setEmptyView(R.layout.app_recycler_view_empty_item)
        setLoadSir(dataBinding.root)
        showContent()

    }

    override fun onFragmentFirstVisible() {
        val boolean = MMKV.defaultMMKV().decodeBool("isLogin")
        if (boolean) {
            val string = MMKV.defaultMMKV().decodeString("userInfo")

            if (string != null) {
                val info = Gson().fromJson(string, UserInfo.Info::class.java)
                userId = info.id
                viewModel.loadInitial(userId, status!!)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        if (userId != "" && isFirst) {
            isRefresh = true
            viewModel.loadRefresh(userId, status!!)
            dataBinding.refreshLayout.resetNoMoreData()
        }
        isFirst = true
    }

    companion object {
        @JvmStatic
        fun newInstance(param: String) =
            AlarmWorkFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM, param)
                }
            }
    }
}