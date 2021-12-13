package com.zq.zhaoxian.ui.home


import android.app.Activity
import  android.content.Intent
import android.os.Build
import android.os.Bundle
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

    val dataBinding by lazy {
        getDataBind()
    }
    val viewModel by lazy {
        getViewModel()
    }
    private val data = mutableListOf<TouTiao.Data>()
    private val list = mutableListOf<String>()

    private var userId: String = ""
    private var portalUserId: String = ""

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
        dataBinding.banner.setAdapter(bannerAdapter).addBannerLifecycleObserver(this).indicator =
            CircleIndicator(mContext)

        dataBinding.banner.pivotX
        dataBinding.recyclerView.adapter = adapter
        dataBinding.recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        dataBinding.recyclerView.addItemDecoration(
            SpacesItemDecoration(context).setParam(
                resources.getColor(
                    R.color.line1_color
                ), 1.0f, 16f, 0f
            )
        )

        adapter.setOnItemClickListener { _, _, position ->
            toDetails(data[position].url)
        }

        dataBinding.refreshLayout.setEnableLoadMore(false)

        dataBinding.refreshLayout.setOnRefreshListener {
            viewModel.queryHomeInfo(userId, portalUserId)
            dataBinding.refreshLayout.finishRefresh()
        }

        dataBinding.unfinnish.setOnClickListener {
            val intent = Intent(mContext, DangerActivity::class.java)
            intent.putExtra("tab", 0)
            startActivityForResult(intent, 1)

        }

        dataBinding.finnish.setOnClickListener {
            val intent = Intent(mContext, DangerActivity::class.java)
            intent.putExtra("tab", 1)
            startActivityForResult(intent, 1)
        }
        dataBinding.alarmUnfinnish.setOnClickListener {
            val intent = Intent(mContext, AlarmWorkActivity::class.java)
            intent.putExtra("tab", 0)
            startActivityForResult(intent, 1)
        }
        dataBinding.alarmFinnish.setOnClickListener {
            val intent = Intent(mContext, AlarmWorkActivity::class.java)
            intent.putExtra("tab", 1)
            startActivityForResult(intent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            activity.jumpFragment()
        }
    }

    override fun initData() {
        dataBinding.notice.setOnClickListener {
            startActivity(Intent(mContext, NoticeActivity::class.java))
        }

        viewModel.touTiaoData.observe(this) {
            data.addAll(it)
            adapter.data = data
            adapter.notifyDataSetChanged()
        }

        viewModel.weather.observe(this) { weather ->
            weather.result?.let {
                dataBinding.temperature.text = it.realtime.temperature
                dataBinding.windPower.text = it.realtime.direct + it.realtime.power
                dataBinding.weather.text = it.realtime.info
                val temperature = it.future[0].temperature
                val split = temperature.split("/")
                dataBinding.desc.text = "最低${split[0]}℃ 最高${split[1]}"
                showWeatherImage(it.realtime.wid)
            }

        }
        viewModel.homeModel.observe(this) {
            dataBinding.notice.text = it.notice.ZQIOT__title__CST
            val workInfo = it.info
            list.clear()
            list.addAll(it.picture)
            bannerAdapter.setDatas(list);
            bannerAdapter.notifyDataSetChanged()
            for (info in workInfo) {
                if (info.type == "task") {
                    if (info.ZQIOT__state__CST == "未处理") {
                        dataBinding.pendingNumber.text = info.count.toString()
                    }
                    if (info.ZQIOT__state__CST == "已完成") {
                        dataBinding.finishNumber.text = info.count.toString()
                    }
                }
                if (info.type == "alarm") {
                    if (info.ZQIOT__state__CST == "未处理") {
                        dataBinding.alarmPendingNumber.text = info.count.toString()
                    }
                    if (info.ZQIOT__state__CST == "已完成") {
                        dataBinding.alarmFinishNumber.text = info.count.toString()
                    }
                }

            }
        }
    }

    private fun showWeatherImage(wid: String) {
        when (wid) {
            "00" -> {
                dataBinding.weatherImage.setImageResource(R.mipmap.tq_01qing)
            }
            "01" -> {
                dataBinding.weatherImage.setImageResource(R.mipmap.tq_03duoyun)
            }
            "02" -> {
                dataBinding.weatherImage.setImageResource(R.mipmap.tq_05yin)
            }
            "03" -> {
                dataBinding.weatherImage.setImageResource(R.mipmap.tq_07zhenyu)
            }
            "04" -> {
                dataBinding.weatherImage.setImageResource(R.mipmap.tq_23leizhenyu)
            }
            "05" -> {
                dataBinding.weatherImage.setImageResource(R.mipmap.tq_25leizhenyubingbanyoubingbao)
            }
            "06" -> {
                dataBinding.weatherImage.setImageResource(R.mipmap.tq_27yujiaxue)
            }
            "07" -> {
                dataBinding.weatherImage.setImageResource(R.mipmap.tq_11xiaoyu)
            }
            "08" -> {
                dataBinding.weatherImage.setImageResource(R.mipmap.tq_13zhongyu)
            }
            "09" -> {
                dataBinding.weatherImage.setImageResource(R.mipmap.tq_15dayu)
            }
            "10" -> {
                dataBinding.weatherImage.setImageResource(R.mipmap.tq_17baoyu)
            }
            "11" -> {
                dataBinding.weatherImage.setImageResource(R.mipmap.tq_19dabaoyu)
            }
            "12" -> {
                dataBinding.weatherImage.setImageResource(R.mipmap.tq_21tedabaoyu)
            }
            "13" -> {
                dataBinding.weatherImage.setImageResource(R.mipmap.tq_09zhenxue)
            }
            "14" -> {
                dataBinding.weatherImage.setImageResource(R.mipmap.tq_29xiaoxue)
            }
            "15" -> {
                dataBinding.weatherImage.setImageResource(R.mipmap.tq_31zhongxue)
            }
            "16" -> {
                dataBinding.weatherImage.setImageResource(R.mipmap.tq_33daxue)
            }
            "17" -> {
                dataBinding.weatherImage.setImageResource(R.mipmap.tq_35baoxue)
            }
            "18" -> {
                dataBinding.weatherImage.setImageResource(R.mipmap.tq_37wu)
            }
            "19" -> {
                dataBinding.weatherImage.setImageResource(R.mipmap.tq_41dongyu)
            }
            "20" -> {
                dataBinding.weatherImage.setImageResource(R.mipmap.tq_45shachenbao)
            }
            "21" -> {
                dataBinding.weatherImage.setImageResource(R.mipmap.tq_11xiaoyu)
            }
            "22" -> {
                dataBinding.weatherImage.setImageResource(R.mipmap.tq_13zhongyu)
            }
            "23" -> {
                dataBinding.weatherImage.setImageResource(R.mipmap.tq_15dayu)
            }
            "24" -> {
                dataBinding.weatherImage.setImageResource(R.mipmap.tq_17baoyu)
            }
            "25" -> {
                dataBinding.weatherImage.setImageResource(R.mipmap.tq_19dabaoyu)
            }
            "26" -> {
                dataBinding.weatherImage.setImageResource(R.mipmap.tq_29xiaoxue)
            }
            "27" -> {
                dataBinding.weatherImage.setImageResource(R.mipmap.tq_31zhongxue)
            }
            "28" -> {
                dataBinding.weatherImage.setImageResource(R.mipmap.tq_33daxue)
            }
            "29" -> {
                dataBinding.weatherImage.setImageResource(R.mipmap.tq_49fuchen)
            }
            "30" -> {
                dataBinding.weatherImage.setImageResource(R.mipmap.tq_43yangsha)
            }
            "31" -> {
                dataBinding.weatherImage.setImageResource(R.mipmap.tq_47qiangshachenbao)
            }
            "53" -> {
                dataBinding.weatherImage.setImageResource(R.mipmap.tq_39mai)
            }

        }
    }

    override fun onFragmentFirstVisible() {
        viewModel.queryWeather()
        viewModel.queryTouTiao()

        val defaultMMKV = MMKV.defaultMMKV()
        val string = defaultMMKV.decodeString("userInfo")
        if (string != null) {
            val info = Gson().fromJson(string, UserInfo.Info::class.java)
            userId = info.id
            portalUserId = info.portalUser
            viewModel.queryHomeInfo(userId, portalUserId)
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        if (event.type == "home") {
            if (userId != "")
                viewModel.queryHomeInfo(userId, portalUserId)
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
            statusBarView(dataBinding.barView)
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