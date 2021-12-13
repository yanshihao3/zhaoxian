package com.zq.zhaoxian.ui.workbench.hiddendanger

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.tencent.mmkv.MMKV
import com.zq.base.decoration.SpacesItemDecoration
import com.zq.base.fragment.BaseLazyFragment
import com.zq.zhaoxian.R
import com.zq.zhaoxian.common.MessageEvent
import com.zq.zhaoxian.databinding.AppFragmentDangerBinding
import com.zq.zhaoxian.http.model.UserInfo
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject


private const val ARG_PARAM1 = "param"

/**

 */
@AndroidEntryPoint
class DangerFragment : BaseLazyFragment<DangerViewModel, AppFragmentDangerBinding>() {

    private var param: String? = null

    private var status: String? = null

    val dataBinding by lazy {
        getDataBind()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param = it.getString(ARG_PARAM1)
            status = if (param == "0") {
                "待处理"
            } else {
                "已完成"
            }
        }
    }

    override val layoutId: Int = R.layout.app_fragment_danger

    @Inject
    lateinit var adapter: DangerAdapter

    private var isRefresh = true

    private val dataList = mutableListOf<TaskModel.TaskInfo>()

    var userId = ""

    var isFirst = false

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
            getViewModel().loadMore(userId, status!!)
        }
        dataBinding.refreshLayout.setOnRefreshListener {
            isRefresh = true
            getViewModel().loadRefresh(userId, status!!)
            dataBinding.refreshLayout.finishRefresh()
        }
        getViewModel().data.observe(this) {
            if (isRefresh) {
                dataList.clear()
                dataList.addAll(it.taskInfo)
                adapter.setDiffNewData(it.taskInfo.toMutableList())
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
                val intent = Intent(context, DangerHandleActivity::class.java)
                intent.putExtra("data", dataList[position])
                startActivity(intent)
            } else {
                val intent = Intent(context, DangerDetailActivity::class.java)
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
                getViewModel().loadInitial(userId, status!!)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        if (userId != "" && isFirst) {
            isRefresh = true
            getViewModel().loadRefresh(userId, status!!)
            dataBinding.refreshLayout.resetNoMoreData()
        }
        isFirst = true
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        if (event.type == "danger") {
            if (userId != "" && event.param == param) {
                isRefresh = true
                getViewModel().loadRefresh(userId, status!!)
                dataBinding.refreshLayout.resetNoMoreData()
            }
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


    companion object {
        @JvmStatic
        fun newInstance(param: String) =
            DangerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param)
                }
            }
    }

}