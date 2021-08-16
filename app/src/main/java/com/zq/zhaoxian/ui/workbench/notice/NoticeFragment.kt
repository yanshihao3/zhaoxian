package com.zq.zhaoxian.ui.workbench.notice

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.tencent.mmkv.MMKV
import com.zq.base.decoration.SpacesItemDecoration

import com.zq.base.fragment.BaseLazyFragment
import com.zq.zhaoxian.R
import com.zq.zhaoxian.databinding.AppFragmentNoticeBinding
import com.zq.zhaoxian.http.model.UserInfo
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


private const val ARG_PARAM = "type"

/**
 * 通知公告 界面中的viewpager界面
 */
@AndroidEntryPoint
class NoticeFragment private constructor() :
    BaseLazyFragment<NoticeViewModel, AppFragmentNoticeBinding>() {
    override val layoutId: Int = R.layout.app_fragment_notice
    private var param: String? = null

    private var id = ""

    @Inject
    lateinit var adapter: NoticeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param = it.getString(ARG_PARAM)
        }
    }

    override fun onFragmentFirstVisible() {
        val boolean = MMKV.defaultMMKV().decodeBool("isLogin")
        if (boolean) {
            val string = MMKV.defaultMMKV().decodeString("userInfo")
            if (string != null) {
                val info = Gson().fromJson(string, UserInfo.Info::class.java)
                id = info.id
                setLoadSir(mDataBind.root)
                mViewModel.loadInitial(id, param!!)
            }
        }

    }

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
        mDataBind.refreshLayout.setEnableLoadMore(false)
        mDataBind.refreshLayout.setOnRefreshListener {
            mViewModel.loadRefresh(id, param!!)
            mDataBind.refreshLayout.finishRefresh()
        }
        adapter.setOnItemClickListener { _, _, position ->
            val noticeModel = mViewModel.data.value?.get(position)
            val intent = Intent(context, NoticeDetailsActivity::class.java)
            intent.putExtra("data", noticeModel)
            startActivity(intent)
        }
        adapter.setEmptyView(R.layout.app_recycler_view_empty_item)
        adapter.setExpandListener(object : ExpandStatusListener {
            override fun expend(position: Int) {
                val noticeModel = adapter.data[position]
                mViewModel.updateNotic(id, noticeModel.NoticeId)

            }
        })
    }

    private fun loadData() {

    }

    override fun initData() {
        mViewModel.data.observe(this) {
            adapter.data = it
            adapter.notifyDataSetChanged()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param: String) =
            NoticeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM, param)
                }
            }
    }
}