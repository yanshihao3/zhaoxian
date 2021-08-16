package com.zq.zhaoxian.ui.alarm

import android.widget.TextView
import androidx.lifecycle.Observer
import com.zq.base.fragment.BaseFragment
import com.zq.zhaoxian.R
import com.zq.zhaoxian.databinding.AppFragmentAlarmBinding

class AlarmFragment : BaseFragment<AlarmViewModel, AppFragmentAlarmBinding>() {
    override val layoutId: Int = R.layout.app_fragment_alarm


    override fun initView() {
        val textView: TextView = getDataBind().textDashboard
        getViewModel().text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
    }

    override fun initData() {

    }
}