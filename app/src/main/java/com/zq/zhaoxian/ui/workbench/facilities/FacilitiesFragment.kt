package com.zq.zhaoxian.ui.workbench.facilities

import android.content.Intent
import android.os.Bundle

import androidx.recyclerview.widget.LinearLayoutManager
import com.zq.base.decoration.SpacesItemDecoration
import com.zq.base.fragment.BaseLazyFragment
import com.zq.zhaoxian.R
import com.zq.zhaoxian.databinding.AppFragmentAppointmentBinding
import com.zq.zhaoxian.databinding.AppFragmentFacilitiesBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


private const val ARG_PARAM = "type"

@AndroidEntryPoint
class FacilitiesFragment private constructor() :
    BaseLazyFragment<FacilitiesViewModel, AppFragmentFacilitiesBinding>() {
    override val layoutId: Int = R.layout.app_fragment_facilities
    private var param: String? = null
    val dataBinding by lazy {
        getDataBind()
    }
    @Inject
    lateinit var adapter: FacilitiesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param = it.getString(ARG_PARAM)
        }
    }

    override fun onFragmentFirstVisible() {
        getViewModel().load()
        adapter.data = mutableListOf(FacilitiesModel(), FacilitiesModel())
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
            FacilitiesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM, param)
                }
            }
    }
}