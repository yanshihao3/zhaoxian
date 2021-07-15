package com.zq.zhaoxian.ui.home


import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.gyf.immersionbar.ktx.immersionBar
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import com.zq.base.decoration.SpacesItemDecoration
import com.zq.base.fragment.BaseLazyFragment
import com.zq.zhaoxian.R
import com.zq.zhaoxian.databinding.AppFragmentHomeBinding
import com.zq.zhaoxian.http.model.TouTiao
import com.zq.zhaoxian.ui.workbench.notice.NoticeActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment @Inject constructor() :
    BaseLazyFragment<HomeViewModel, AppFragmentHomeBinding>() {

    override val layoutId: Int = R.layout.app_fragment_home

    @Inject
    lateinit var adapter: HomeAdapter

    private val data = mutableListOf<TouTiao.Data>()
    private val list = mutableListOf(
        "https://img.zcool.cn/community/013de756fb63036ac7257948747896.jpg",
        "https://img.zcool.cn/community/01639a56fb62ff6ac725794891960d.jpg",
        "https://img.zcool.cn/community/01270156fb62fd6ac72579485aa893.jpg",
        "https://img.zcool.cn/community/01233056fb62fe32f875a9447400e1.jpg",
        "https://img.zcool.cn/community/016a2256fb63006ac7257948f83349.jpg"

    )

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initView() {
        mDataBind.banner.setAdapter(object :
            BannerImageAdapter<String>(list) {
            override fun onBindView(
                holder: BannerImageHolder,
                data: String,
                position: Int,
                size: Int
            ) {
                holder.imageView.load(data)
            }
        }).addBannerLifecycleObserver(this).indicator = CircleIndicator(mContext)


        mDataBind.recyclerView.adapter = adapter
        mDataBind.recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        mDataBind.recyclerView.addItemDecoration(
            SpacesItemDecoration(
                context,
                SpacesItemDecoration.HORIZONTAL
            ).apply {
                context?.getColor(R.color.line2_color)?.let { setParam(it, 8f) }
            }
        )
        adapter.setOnItemClickListener { _, _, position ->
            if (data[position].is_content == "1")
                toDetails(data[position].url)
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
            mDataBind.weather.text = it.result.future[0].weather
            val temperature = it.result.future[0].temperature
            val split = temperature.split("/")
            mDataBind.desc.text = "最低${split[0]}℃ 最高${split[1]}"

        }
    }

    override fun onFragmentFirstVisible() {
        mViewModel.queryWeather()
        mViewModel.queryTouTiao()
    }

    override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar {
            statusBarView(mDataBind.barView)
            transparentStatusBar()
            statusBarDarkFont(true, 0.2f)
        }
    }

    fun toDetails(url: String) {
        val intent = Intent(context, MainWebViewActivity::class.java)
        intent.putExtra("url", url)
        intent.putExtra("title", "我的博客")
        startActivity(intent)
    }
}