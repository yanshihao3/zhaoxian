package com.zq.zhaoxian.ui.workbench.dispatch


import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.zq.base.activity.BaseNoModelActivity
import com.zq.base.decoration.SpacesItemDecoration
import com.zq.zhaoxian.R
import com.zq.zhaoxian.databinding.AppActivityDispatchResultBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DispatchResultActivity : BaseNoModelActivity<AppActivityDispatchResultBinding>() {

    override val layoutId: Int = R.layout.app_activity_dispatch_result

    @Inject
    lateinit var adapter: DispatchAdapter
    val dataBinding by lazy {
        getDataBind()
    }
    override fun initView() {
        dataBinding.recyclerView.layoutManager = LinearLayoutManager(mActivityContext)
        dataBinding.recyclerView.adapter = adapter
        dataBinding.recyclerView.addItemDecoration(
            SpacesItemDecoration(mActivityContext).setParam(
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
            startActivity(Intent(mActivityContext, SituationDetailsActivity::class.java))
        }
    }

    private fun loadData() {

    }

    override fun initData() {
        adapter.data = mutableListOf(Model(), Model())
    }
}