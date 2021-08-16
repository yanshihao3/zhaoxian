package com.zq.zhaoxian.ui.workbench

import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.tencent.mmkv.MMKV
import com.zq.base.decoration.SpacesItemDecoration
import com.zq.base.fragment.BaseLazyFragment
import com.zq.zhaoxian.R
import com.zq.zhaoxian.databinding.AppFragmentPendingBinding
import com.zq.zhaoxian.http.model.UserInfo
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * 待处理的界面
 */
@AndroidEntryPoint
class PendingFragment : BaseLazyFragment<PendingViewModel, AppFragmentPendingBinding>() {
    override val layoutId: Int = R.layout.app_fragment_pending

    @Inject
    lateinit var adapter: PendingAdapter

    private var isRefresh = true

    private val dataList = mutableListOf<WorkbenchModel>()

    var userId = ""

    override fun initView() {
        val dataBind=getDataBind()
        dataBind.recyclerView.layoutManager = LinearLayoutManager(context)
        dataBind.recyclerView.adapter = adapter
        dataBind.recyclerView.addItemDecoration(
            SpacesItemDecoration(context).setParam(
                resources.getColor(
                    R.color.line_color
                ), 1.0f
            )
        )
        dataBind.refreshLayout.setEnableLoadMore(true)
        dataBind.refreshLayout.setOnLoadMoreListener {
            isRefresh = false
            getViewModel().loadMore(userId, "START")
        }
        dataBind.refreshLayout.setOnRefreshListener {
            isRefresh = true
            getViewModel().load(userId, "START")
            dataBind.refreshLayout.finishRefresh()
        }
        getViewModel().data.observe(this) {
            if (isRefresh) {
                dataList.clear()
                dataList.addAll(it.list)
                if (dataList.size == it.totalCount) {
                    dataBind.refreshLayout.setEnableLoadMore(false)
                }
                adapter.data = dataList
            } else {
                dataList.addAll(it.list)
                if (dataList.size == it.totalCount) {
                    dataBind.refreshLayout.finishLoadMoreWithNoMoreData()
                } else {
                    dataBind.refreshLayout.finishLoadMore()
                }
                adapter.data = dataList
            }
            adapter.notifyDataSetChanged()

        }
    }

    override fun initData() {
        adapter.setOnItemChildClickListener { adapter, view, position ->
            val workbenchModel = dataList[position]
            val id = workbenchModel.id
            getViewModel().changeWorkOderStatus("", id, "NORMAL")
        }
        adapter.setEmptyView(R.layout.app_recycler_view_empty_item)
        setLoadSir(getDataBind().root)
        showContent()

    }

    override fun onFragmentFirstVisible() {
        val boolean = MMKV.defaultMMKV().decodeBool("isLogin")
        if (boolean) {
            val string = MMKV.defaultMMKV().decodeString("userInfo")

            if (string != null) {
                val info = Gson().fromJson(string, UserInfo.Info::class.java)
                userId = info.portalUser
                getViewModel().loadInitial(userId, "START")
            }
        }

    }


    companion object {
        @JvmStatic
        fun newInstance() =
            PendingFragment()
    }
}