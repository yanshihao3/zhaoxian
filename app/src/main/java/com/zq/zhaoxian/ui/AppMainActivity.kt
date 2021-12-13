package com.zq.zhaoxian.ui

import android.os.Bundle
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

    private var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            position = savedInstanceState.getInt("position")
            initFragment()
        }
        addFragment()

    }

    private fun addFragment() {

        val transaction = supportFragmentManager.beginTransaction()
        if (!homeFragment.isAdded) {
            transaction.add(R.id.container, homeFragment, "home")
        }
        if (!workbenchFragment.isAdded) {
            transaction.add(R.id.container, workbenchFragment, "work")
        }
        if (!myFragment.isAdded) {
            transaction.add(R.id.container, myFragment, "my")
        }
        when (position) {
            0 -> {
                transaction.hide(myFragment).hide(workbenchFragment).show(homeFragment)
                fromFragment = homeFragment
            }
            1 -> {
                transaction.hide(myFragment).hide(homeFragment).show(workbenchFragment)
                fromFragment = workbenchFragment
            }
            2 -> {
                transaction.hide(homeFragment).hide(workbenchFragment).show(myFragment)
                fromFragment = myFragment
            }
        }
        transaction.commit()

    }


    private fun initFragment() {
        homeFragment = supportFragmentManager.findFragmentByTag("home") as HomeFragment
        workbenchFragment = supportFragmentManager.findFragmentByTag("work") as WorkbenchFragment
        myFragment = supportFragmentManager.findFragmentByTag("my") as MyFragment

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("TAG", "onDestroy: ")
    }

    override fun initView() {

        getDataBind().bottomView.setOnNavigationItemSelectedListener {
            val fragCategory = when (it.itemId) {
                R.id.navigation_home -> {
                    position = 0
                    homeFragment
                }
                R.id.navigation_workbench -> {
                    position = 1
                    workbenchFragment
                }
                R.id.navigation_my -> {
                    position = 2
                    myFragment
                }
                else -> {
                    position = 0
                    homeFragment
                }
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
        if (from != to) {
            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()

            transaction.hide(from)
            transaction.show(to).commit()

            if (to is HomeFragment) {
                EventBus.getDefault().post(MessageEvent("home", "0"))
            }

        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("position", position)
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