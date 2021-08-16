package com.zq.zhaoxian.ui.workbench.investigation

import android.os.Bundle

import androidx.recyclerview.widget.LinearLayoutManager
import com.zq.base.decoration.SpacesItemDecoration
import com.zq.base.fragment.BaseLazyFragment
import com.zq.zhaoxian.R
import com.zq.zhaoxian.databinding.AppFragmentInvestigationBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val ARG_PARAM = "type"

/**
排查任务界面
 */
@AndroidEntryPoint
class InvestigationFragment private constructor() :
    BaseLazyFragment<InvestigationViewModel, AppFragmentInvestigationBinding>() {
    override val layoutId: Int = R.layout.app_fragment_investigation
    private var param: String? = null

    @Inject
    lateinit var adapter: InvestigationAdapter
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
        adapter.data = mutableListOf(InvestigationModel(), InvestigationModel())
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

        }
        /**
         * 详情界面
         */
        adapter.setOnItemChildClickListener { _, _, position ->

        }
    }

    private fun loadData() {

    }

    override fun initData() {

    }

    companion object {
        @JvmStatic
        fun newInstance(param: String) =
            InvestigationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM, param)
                }
            }
    }
}