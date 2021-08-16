package com.zq.zhaoxian.ui.workbench

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.tencent.mmkv.MMKV
import com.zq.base.decoration.SpacesItemDecoration
import com.zq.base.fragment.BaseLazyFragment
import com.zq.zhaoxian.R
import com.zq.zhaoxian.databinding.AppFragmentPendingBinding
import com.zq.zhaoxian.http.model.UserInfo
import com.zq.zhaoxian.ui.workbench.hiddendanger.DangerHandleActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * 已完成的界面
 */
@AndroidEntryPoint
class FinishFragment : BaseLazyFragment<PendingViewModel, AppFragmentPendingBinding>() {
    override val layoutId: Int = R.layout.app_fragment_pending

    @Inject
    lateinit var adapter: FinishAdapter
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
            getViewModel().loadMore(userId,"END")
        }
        dataBind.refreshLayout.setOnRefreshListener {
            isRefresh = true
            getViewModel().load(userId, "END")
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
        adapter.setEmptyView(R.layout.app_recycler_view_empty_item)
        setLoadSir(getDataBind().root)
        showContent()
        adapter.setOnItemChildClickListener { adapter, view, position ->
            val workbenchModel = dataList[position]
            val intent = Intent(context, DangerHandleActivity::class.java)
            intent.putExtra("data", workbenchModel)
            startActivityForResult(intent, 0)
        }
    }

    override fun onFragmentFirstVisible() {
        val boolean = MMKV.defaultMMKV().decodeBool("isLogin")
        if (boolean) {
            val string = MMKV.defaultMMKV().decodeString("userInfo")
            if (string != null) {
                val info = Gson().fromJson(string, UserInfo.Info::class.java)
                userId = info.portalUser
                getViewModel().loadInitial(userId, "END")
            }

        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FinishFragment()
    }
}