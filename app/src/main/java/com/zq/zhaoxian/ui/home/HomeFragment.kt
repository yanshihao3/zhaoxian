package com.zq.zhaoxian.ui.home


import android.app.Activity
import  android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.ViewAnimationUtils
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.google.gson.Gson
import com.gyf.immersionbar.ktx.immersionBar
import com.tencent.mmkv.MMKV
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import com.zq.base.decoration.SpacesItemDecoration
import com.zq.base.fragment.BaseLazyFragment
import com.zq.zhaoxian.R
import com.zq.zhaoxian.common.MessageEvent
import com.zq.zhaoxian.databinding.AppFragmentHomeBinding
import com.zq.zhaoxian.http.model.TouTiao
import com.zq.zhaoxian.http.model.UserInfo
import com.zq.zhaoxian.ui.AppMainActivity
import com.zq.zhaoxian.ui.workbench.alarm.AlarmWorkActivity
import com.zq.zhaoxian.ui.workbench.hiddendanger.DangerActivity
import com.zq.zhaoxian.ui.workbench.notice.NoticeActivity
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment @Inject constructor() :
    BaseLazyFragment<HomeViewModel, AppFragmentHomeBinding>() {

    override val layoutId: Int = R.layout.app_fragment_home

    lateinit var activity: AppMainActivity

    @Inject
    lateinit var adapter: HomeAdapter


    private val data = mutableListOf<TouTiao.Data>()
    private val list = mutableListOf<String>()

    private var userId: String = ""

    private val bannerAdapter = object : BannerImageAdapter<String>(list) {
        override fun onBindView(
            holder: BannerImageHolder,
            data: String,
            position: Int,
            size: Int
        ) {
            holder.imageView.load(data)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initView() {
        mDataBind.banner.setAdapter(bannerAdapter).addBannerLifecycleObserver(this).indicator =
            CircleIndicator(mContext)

        mDataBind.banner.pivotX
        mDataBind.recyclerView.adapter = adapter
        mDataBind.recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        mDataBind.recyclerView.addItemDecoration(
            SpacesItemDecoration(context).setParam(
                resources.getColor(
                    R.color.line1_color
                ), 1.0f, 16f, 0f
            )
        )

        adapter.setOnItemClickListener { _, _, position ->
            toDetails(data[position].url)
        }

        mDataBind.refreshLayout.setEnableLoadMore(false)

        mDataBind.refreshLayout.setOnRefreshListener {
            mViewModel.queryHomeInfo(userId)
            mDataBind.refreshLayout.finishRefresh()
        }

        mDataBind.unfinnish.setOnClickListener {
            val intent = Intent(mContext, DangerActivity::class.java)
            intent.putExtra("tab", 0)
            startActivity(intent)
        }

        mDataBind.finnish.setOnClickListener {
            val intent = Intent(mContext, DangerActivity::class.java)
            intent.putExtra("tab", 1)
            startActivity(intent)
        }
        mDataBind.alarmUnfinnish.setOnClickListener {
            val intent = Intent(mContext, AlarmWorkActivity::class.java)
            intent.putExtra("tab", 0)
            startActivity(intent)
        }
        mDataBind.alarmFinnish.setOnClickListener {
            val intent = Intent(mContext, AlarmWorkActivity::class.java)
            intent.putExtra("tab", 1)
            startActivity(intent)
        }
    }

    override fun initData() {
        mDataBind.notice.setOnClickListener {
            startActivity(Intent(mContext, NoticeActivity::class.java))
        }

        mViewModel.touTiaoData.observe(this) {
            data.addAll(it)
            adapter.data = data
            adapter.notifyDataSetChanged()
        }

        mViewModel.weather.observe(this) {
            mDataBind.temperature.text = it.result.realtime.temperature
            mDataBind.windPower.text = it.result.realtime.direct + it.result.realtime.power
            mDataBind.weather.text = it.result.realtime.info
            val temperature = it.result.future[0].temperature
            val split = temperature.split("/")
            mDataBind.desc.text = "最低${split[0]}℃ 最高${split[1]}"
            showWeatherImage(it.result.realtime.wid)

        }
        mViewModel.homeModel.observe(this) {
            mDataBind.notice.text = it.notice.ZQIOT__title__CST
            val workInfo = it.info
            list.clear()
            list.addAll(it.picture)
            bannerAdapter.setDatas(list);
            bannerAdapter.notifyDataSetChanged()
            for (info in workInfo) {
                if (info.ZQIOT__state__CST == "未处理") {
                    mDataBind.pendingNumber.text = info.count.toString()
                }
                if (info.ZQIOT__state__CST == "已完成") {
                    mDataBind.finishNumber.text = info.count.toString()
                }
            }
        }
    }

    private fun showWeatherImage(wid: String) {
        when (wid) {
            "00" -> {
                mDataBind.weatherImage.setImageResource(R.mipmap.tq_01qing)
            }
            "01" -> {
                mDataBind.weatherImage.setImageResource(R.mipmap.tq_03duoyun)
            }
            "02" -> {
                mDataBind.weatherImage.setImageResource(R.mipmap.tq_05yin)
            }
            "03" -> {
                mDataBind.weatherImage.setImageResource(R.mipmap.tq_07zhenyu)
            }
            "04" -> {
                mDataBind.weatherImage.setImageResource(R.mipmap.tq_23leizhenyu)
            }
            "05" -> {
                mDataBind.weatherImage.setImageResource(R.mipmap.tq_25leizhenyubingbanyoubingbao)
            }
            "06" -> {
                mDataBind.weatherImage.setImageResource(R.mipmap.tq_27yujiaxue)
            }
            "07" -> {
                mDataBind.weatherImage.setImageResource(R.mipmap.tq_11xiaoyu)
            }
            "08" -> {
                mDataBind.weatherImage.setImageResource(R.mipmap.tq_13zhongyu)
            }
            "09" -> {
                mDataBind.weatherImage.setImageResource(R.mipmap.tq_15dayu)
            }
            "10" -> {
                mDataBind.weatherImage.setImageResource(R.mipmap.tq_17baoyu)
            }
            "11" -> {
                mDataBind.weatherImage.setImageResource(R.mipmap.tq_19dabaoyu)
            }
            "12" -> {
                mDataBind.weatherImage.setImageResource(R.mipmap.tq_21tedabaoyu)
            }
            "13" -> {
                mDataBind.weatherImage.setImageResource(R.mipmap.tq_09zhenxue)
            }
            "14" -> {
                mDataBind.weatherImage.setImageResource(R.mipmap.tq_29xiaoxue)
            }
            "15" -> {
                mDataBind.weatherImage.setImageResource(R.mipmap.tq_31zhongxue)
            }
            "16" -> {
                mDataBind.weatherImage.setImageResource(R.mipmap.tq_33daxue)
            }
            "17" -> {
                mDataBind.weatherImage.setImageResource(R.mipmap.tq_35baoxue)
            }
            "18" -> {
                mDataBind.weatherImage.setImageResource(R.mipmap.tq_37wu)
            }
            "19" -> {
                mDataBind.weatherImage.setImageResource(R.mipmap.tq_41dongyu)
            }
            "20" -> {
                mDataBind.weatherImage.setImageResource(R.mipmap.tq_45shachenbao)
            }
            "21" -> {
                mDataBind.weatherImage.setImageResource(R.mipmap.tq_11xiaoyu)
            }
            "22" -> {
                mDataBind.weatherImage.setImageResource(R.mipmap.tq_13zhongyu)
            }
            "23" -> {
                mDataBind.weatherImage.setImageResource(R.mipmap.tq_15dayu)
            }
            "24" -> {
                mDataBind.weatherImage.setImageResource(R.mipmap.tq_17baoyu)
            }
            "25" -> {
                mDataBind.weatherImage.setImageResource(R.mipmap.tq_19dabaoyu)
            }
            "26" -> {
                mDataBind.weatherImage.setImageResource(R.mipmap.tq_29xiaoxue)
            }
            "27" -> {
                mDataBind.weatherImage.setImageResource(R.mipmap.tq_31zhongxue)
            }
            "28" -> {
                mDataBind.weatherImage.setImageResource(R.mipmap.tq_33daxue)
            }
            "29" -> {
                mDataBind.weatherImage.setImageResource(R.mipmap.tq_49fuchen)
            }
            "30" -> {
                mDataBind.weatherImage.setImageResource(R.mipmap.tq_43yangsha)
            }
            "31" -> {
                mDataBind.weatherImage.setImageResource(R.mipmap.tq_47qiangshachenbao)
            }
            "53" -> {
                mDataBind.weatherImage.setImageResource(R.mipmap.tq_39mai)
            }

        }
    }

    override fun onFragmentFirstVisible() {
        mViewModel.queryWeather()
        mViewModel.queryTouTiao()

        val defaultMMKV = MMKV.defaultMMKV()
        val string = defaultMMKV.decodeString("userInfo")
        if (string != null) {
            val info = Gson().fromJson(string, UserInfo.Info::class.java)
            userId = info.id
            mViewModel.queryHomeInfo(userId)
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        if (event.type == "home") {
            if (userId != "")
                mViewModel.queryHomeInfo(userId)
        }
    }

    override fun onAttach(context: Activity) {
        super.onAttach(context)
        //对传递进来的Activity进行接口转换
        activity = context as AppMainActivity
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


    override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar {
            statusBarView(mDataBind.barView)
            transparentStatusBar()
            statusBarDarkFont(true, 0.2f)
        }
    }


    private fun toDetails(url: String) {
        val intent = Intent(context, MainWebViewActivity::class.java)
        intent.putExtra("url", url)
        intent.putExtra("title", "我的博客")
        startActivity(intent)
    }
}