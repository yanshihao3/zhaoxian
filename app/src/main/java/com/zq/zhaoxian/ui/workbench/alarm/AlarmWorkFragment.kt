package com.zq.zhaoxian.ui.workbench.alarm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.tencent.mmkv.MMKV
import com.zq.base.decoration.SpacesItemDecoration
import com.zq.base.fragment.BaseLazyFragment
import com.zq.zhaoxian.R
import com.zq.zhaoxian.databinding.AppFragmentAlarmWorkBinding
import com.zq.zhaoxian.http.model.AlarmInfoEntry
import com.zq.zhaoxian.http.model.UserInfo

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
                "START+NORMAL"
            } else {
                "END"
            }
        }
    }

    override val layoutId: Int
        get() = R.layout.app_fragment_alarm_work

    @Inject
    lateinit var adapter: AlarmAdapter

    private var isRefresh = true

    private val dataList = mutableListOf<AlarmInfoEntry>()

    var userId = ""

    var isFirst = false

    val dataBinding by lazy {
        getDataBind()
    }
    val viewModel by lazy {
        getViewModel()
    }

    override fun initView() {
        adapter.setDiffCallback(DiffCallBack())
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
                adapter.setDiffNewData(it.list.toMutableList())
            } else {
                dataList.addAll(it.list)
                if (it.list.isEmpty()) {
                    dataBinding.refreshLayout.finishLoadMoreWithNoMoreData()
                } else {
                    dataBinding.refreshLayout.finishLoadMore()
                }
                adapter.addData(it.list)
            }


        }
    }

    override fun initData() {

        adapter.setOnItemChildClickListener { _, _, position ->
            if (param == "0") {
                val intent = Intent(context, AlarmDetailHandleActivity::class.java)
                Log.e("TAG", "initData: ${adapter.data[position]}")
                intent.putExtra("data", adapter.data[position])
                startActivity(intent)
            } else {
                val intent = Intent(context, AlarmDetailActivity::class.java)
                intent.putExtra("data", adapter.data[position])
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
                userId = info.portalUser
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