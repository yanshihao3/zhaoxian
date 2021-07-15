package com.zq.zhaoxian.ui.workbench.appointment


import android.util.Log
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.zq.base.activity.BaseNoModelActivity
import com.zq.base.utils.DisplayUtils
import com.zq.zhaoxian.R
import com.zq.zhaoxian.databinding.AppActivityAppointmentDetailsBinding


class AppointmentDetailsActivity : BaseNoModelActivity<AppActivityAppointmentDetailsBinding>() {


    override val layoutId: Int = R.layout.app_activity_appointment_details

    override fun initView() {
        val barcodeEncoder = BarcodeEncoder()
        val bitmap = barcodeEncoder.encodeBitmap(
            "content",
            BarcodeFormat.QR_CODE,
            DisplayUtils.dip2px(mActivityContext, 152f),
            DisplayUtils.dip2px(mActivityContext, 152f)
        )
        mDataBind.qrcode.setImageBitmap(bitmap)
    }

    override fun initData() {
    }
}