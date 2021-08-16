package com.zq.zhaoxian.ui.workbench.task

import android.os.Bundle

import androidx.recyclerview.widget.LinearLayoutManager
import com.zq.base.decoration.SpacesItemDecoration
import com.zq.base.fragment.BaseLazyFragment
import com.zq.zhaoxian.R
import com.zq.zhaoxian.databinding.AppFragmentTaskBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


private const val ARG_PARAM = "type"

@AndroidEntryPoint
class TaskFragment private constructor() :
    BaseLazyFragment<TaskViewModel, AppFragmentTaskBinding>() {
    override val layoutId: Int = R.layout.app_fragment_task
    private var param: String? = null
    val dataBinding by lazy {
        getDataBind()
    }

    @Inject
    lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param = it.getString(ARG_PARAM)
        }
    }

    override fun onFragmentFirstVisible() {
        getViewModel().load()
        adapter.data = mutableListOf(TaskModel(), TaskModel())
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
            TaskFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM, param)
                }
            }
    }
}