package com.zq.zhaoxian.ui.workbench.dispatch

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.zq.base.decoration.SpacesItemDecoration
import com.zq.base.fragment.BaseLazyFragment
import com.zq.zhaoxian.R
import com.zq.zhaoxian.databinding.AppFragmentDispatchBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * @program: zhaoxian
 *
 * @description:
 *
 * @author: 闫世豪
 *
 * @create: 2021-06-28 10:34
 **/

private const val ARG_PARAM = "type"

@AndroidEntryPoint
class DispatchFragment private constructor() :
    BaseLazyFragment<DispatchViewModel, AppFragmentDispatchBinding>() {
    override val layoutId: Int = R.layout.app_fragment_dispatch
    private var param: String? = null

    @Inject
    lateinit var adapter: DispatchAdapter
    val dataBinding by lazy {
        getDataBind()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param = it.getString(ARG_PARAM)
        }
    }

    override fun onFragmentFirstVisible() {
        getViewModel().load()
        adapter.data = mutableListOf(Model(), Model())
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
        dataBinding.refreshLayout.setOnRefreshListener { loadData() }
        /**
         * 提交处理界面
         */
        adapter.setOnItemClickListener { _, _, position ->
            startActivity(Intent(context, SituationEditActivity::class.java))
        }
        /**
         * 详情界面
         */
        adapter.setOnItemChildClickListener { _, _, position ->
            startActivity(Intent(context, SituationDetailsActivity::class.java))
        }
    }

    private fun loadData() {

    }

    override fun initData() {

    }

    companion object {
        @JvmStatic
        fun newInstance(param: String) =
            DispatchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM, param)
                }
            }
    }
}