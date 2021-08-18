package com.zq.zhaoxian.ui

import android.content.Intent
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.hjq.toast.ToastUtils
import com.zq.base.activity.BaseActivity
import com.zq.zhaoxian.R
import com.zq.zhaoxian.application.MyActivityManager
import com.zq.zhaoxian.common.MessageEvent
import com.zq.zhaoxian.databinding.AppActivityMainBinding
import com.zq.zhaoxian.ui.home.HomeFragment
import com.zq.zhaoxian.ui.my.MyFragment
import com.zq.zhaoxian.ui.workbench.WorkbenchFragment
import com.zq.zhaoxian.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Runnable
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

@AndroidEntryPoint
class AppMainActivity : BaseActivity<MainViewModel, AppActivityMainBinding>() {

    override val layoutId: Int = R.layout.app_activity_main

    @Inject
    lateinit var homeFragment: HomeFragment

    @Inject
    lateinit var myFragment: MyFragment

    @Inject
    lateinit var workbenchFragment: WorkbenchFragment

    private lateinit var fromFragment: Fragment

    override fun initView() {
        fromFragment = homeFragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.container, fromFragment)
        transaction.commit()
        getDataBind().bottomView.setOnNavigationItemSelectedListener {
            val fragCategory = when (it.itemId) {
                R.id.navigation_home -> homeFragment
                R.id.navigation_workbench -> workbenchFragment
                R.id.navigation_my -> myFragment
                else -> homeFragment
            }
            switchFragment(fromFragment, fragCategory)
            fromFragment = fragCategory
            true
        }

    }

    override fun initData() {
        // mViewModel.load()
    }

    private fun switchFragment(from: Fragment, to: Fragment) {
        if (from !== to) {
            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            if (!to.isAdded) {
                transaction.hide(from)
                transaction.add(R.id.container, to).commit()
            } else {
                transaction.hide(from)
                transaction.show(to).commit()
            }
            if (to is HomeFragment) {
                EventBus.getDefault().post(MessageEvent("home", "0"))
            }
            if (to is HomeFragment) {
                EventBus.getDefault().post(MessageEvent("home", "0"))
            }

        }
    }



    fun jumpFragment() {
        getDataBind().bottomView.selectedItemId = R.id.navigation_workbench
    }

    private var exitTime: Long = 0 //退出activity计时

    //双击退出app事件
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                try {
                    ToastUtils.show("再按一次返回键确认退出")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                exitTime = System.currentTimeMillis()
            } else {
                MyActivityManager.getInstance().exitApp()
            }
            return false
        }
        return super.onKeyDown(keyCode, event)
    }

}