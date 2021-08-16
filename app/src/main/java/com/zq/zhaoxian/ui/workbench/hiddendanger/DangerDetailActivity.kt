package com.zq.zhaoxian.ui.workbench.hiddendanger

import androidx.recyclerview.widget.LinearLayoutManager

import com.zq.base.activity.BaseActivity
import com.zq.zhaoxian.R
import com.zq.zhaoxian.databinding.AppActivityDangerDetailBinding
import com.zq.zhaoxian.view.recycle.FormLayoutManager
import com.zq.zhaoxian.view.recycle.FormScrollHelper


/**
 * 隐患排查任务详情
 */
class DangerDetailActivity : BaseActivity<ViewModel, AppActivityDangerDetailBinding>() {


    override val layoutId: Int = R.layout.app_activity_danger_detail

    private val list = mutableListOf<DangerModel.Info>()

    private lateinit var adapter: MonsterHAdapter
    val dataBinding by lazy {
        getDataBind()
    }
    override fun initView() {

        dataBinding.toolbar.setBackOnClickListener {
            finish()
        }

        val mRecyclerView = dataBinding.rvHorizontal
        val rvTitle = dataBinding.rvTitle

        rvTitle.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val titles = resources.getStringArray(R.array.form_title)
        val titleList = listOf(*titles)
        val rightTitleAdapter = TitleAdapter(titleList)
        rvTitle.adapter = rightTitleAdapter

        val layoutManager = FormLayoutManager(5, mRecyclerView)
        mRecyclerView.layoutManager = layoutManager

        adapter = MonsterHAdapter(this)
        mRecyclerView.adapter = adapter


        val formScrollHelper = FormScrollHelper()
        formScrollHelper.connectRecyclerView(mRecyclerView)
        formScrollHelper.connectRecyclerView(rvTitle)

    }

    override fun initData() {
        val taskInfo = intent.getSerializableExtra("data") as TaskModel.TaskInfo
        getViewModel().loadInitial(taskInfo.id)
        dataBinding.data = taskInfo
        getViewModel().data.observe(this) {
            list.clear()
            list.addAll(it.info)
            adapter.mList = list
            adapter.notifyDataSetChanged()
        }
    }


}