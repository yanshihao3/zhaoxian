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
        mDataBind.recyclerView.layoutManager = LinearLayoutManager(context)
        mDataBind.recyclerView.adapter = adapter
        mDataBind.recyclerView.addItemDecoration(
            SpacesItemDecoration(context).setParam(
                resources.getColor(
                    R.color.line_color
                ), 1.0f
            )
        )
        mDataBind.refreshLayout.setEnableLoadMore(true)
        mDataBind.refreshLayout.setOnLoadMoreListener {
            isRefresh = false
            mViewModel.loadMore(userId,"NORMAL")
        }
        mDataBind.refreshLayout.setOnRefreshListener {
            isRefresh = true
            mViewModel.load(userId,"NORMAL")
            mDataBind.refreshLayout.finishRefresh()
        }
        mViewModel.data.observe(this) {
            if (isRefresh) {
                dataList.clear()
                dataList.addAll(it.list)
                if (dataList.size == it.totalCount) {
                    mDataBind.refreshLayout.setEnableLoadMore(false)
                }
                adapter.data = dataList
            } else {
                dataList.addAll(it.list)
                if (dataList.size == it.totalCount) {
                    mDataBind.refreshLayout.finishLoadMoreWithNoMoreData()
                } else {
                    mDataBind.refreshLayout.finishLoadMore()
                }
                adapter.data = dataList
            }
            adapter.notifyDataSetChanged()

        }
    }

    override fun initData() {


        adapter.setEmptyView(R.layout.app_recycler_view_empty_item)
        setLoadSir(mDataBind.root)
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
                mViewModel.loadInitial(userId,"NORMAL")
            }

        }
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            HandleFragment()
    }
}