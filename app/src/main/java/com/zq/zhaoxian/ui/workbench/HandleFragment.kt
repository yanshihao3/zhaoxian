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
 * 处理中的界面
 */
@AndroidEntryPoint
class HandleFragment : BaseLazyFragment<PendingViewModel, AppFragmentPendingBinding>() {
    override val layoutId: Int = R.layout.app_fragment_pending

    @Inject
    lateinit var adapter: HandleAdapter
    private var isRefresh = true

    private val dataList = mutableListOf<WorkbenchModel>()

    var userId = ""
    override fun initView() {
        getDataBind().recyclerView.layoutManager = LinearLayoutManager(context)
        getDataBind().recyclerView.adapter = adapter
        getDataBind().recyclerView.addItemDecoration(
            SpacesItemDecoration(context).setParam(
                resources.getColor(
                    R.color.line_color
                ), 1.0f
            )
        )
        getDataBind().refreshLayout.setEnableLoadMore(true)
        getDataBind().refreshLayout.setOnLoadMoreListener {
            isRefresh = false
            getViewModel().loadMore(userId,"NORMAL")
        }
        getDataBind().refreshLayout.setOnRefreshListener {
            isRefresh = true
            getViewModel().load(userId,"NORMAL")
            getDataBind().refreshLayout.finishRefresh()
        }
        getViewModel().data.observe(this) {
            if (isRefresh) {
                dataList.clear()
                dataList.addAll(it.list)
                if (dataList.size == it.totalCount) {
                    getDataBind().refreshLayout.setEnableLoadMore(false)
                }
                adapter.data = dataList
            } else {
                dataList.addAll(it.list)
                if (dataList.size == it.totalCount) {
                    getDataBind().refreshLayout.finishLoadMoreWithNoMoreData()
                } else {
                    getDataBind().refreshLayout.finishLoadMore()
                }
                adapter.data = dataList
            }
            adapter.notifyDataSetChanged()

        }
    }

    override fun initData() {


        adapter.setEmptyView(R.layout.app_recycler_view_empty_item)
        setLoadSir(getDataBind().root)
        adapter.setOnItemChildClickListener { adapter, view, position ->
            val workbenchModel = dataList[position]
            val intent = Intent(context, DangerHandleActivity::class.java)
            intent.putExtra("data", workbenchModel)
            startActivityForResult(intent, 0)
//            val workbenchModel = dataList[position]
//            val id = workbenchModel.id
//            mViewModel.changeWorkOderStatus("", id, "END")
        }
    }

    override fun onFragmentFirstVisible() {
        val boolean = MMKV.defaultMMKV().decodeBool("isLogin")
        if (boolean) {
            val string = MMKV.defaultMMKV().decodeString("userInfo")
            if (string != null) {
                val info = Gson().fromJson(string, UserInfo.Info::class.java)
                userId = info.portalUser
                getViewModel().loadInitial(userId,"NORMAL")
            }

        }
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            HandleFragment()
    }
}