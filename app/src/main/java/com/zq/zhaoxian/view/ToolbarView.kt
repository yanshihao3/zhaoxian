package com.zq.zhaoxian.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.zq.zhaoxian.R

/**
 * @program: owner_app
 *
 * @description: 菜单项
 *
 * @author: 闫世豪
 *
 * @create: 2021-06-08 14:12
 **/
class ToolbarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    LinearLayout(context, attrs, defStyleAttr) {

    private var title: AppCompatTextView? = null
    private var icon: AppCompatImageView? = null

    init {
        initView()
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ToolbarView)
        val iconResourceId = typedArray.getResourceId(R.styleable.ToolbarView_toolbar_back, 0)
        val titleText = typedArray.getString(R.styleable.ToolbarView_toolbar_title)
        val visibility = typedArray.getInt(R.styleable.ToolbarView_toolbar_back_visibility, 0)
        typedArray.recycle()
        title?.text = titleText
        // icon?.setImageResource(iconResourceId)
        if (visibility != 0) {
            icon?.visibility = View.GONE
        } else {
            icon?.visibility = View.VISIBLE
        }
    }

    private fun initView() {
        val view = View.inflate(context, R.layout.app_view_tool_tar_item, this)
        title = view.findViewById(R.id.title)
        icon = view.findViewById(R.id.back_iv)
    }

    public fun setBackOnClickListener(block: () -> Unit) {
        icon?.setOnClickListener {
            block()
        }
    }

    public fun setTitle(titleString: String) {
        title?.text = titleString
    }


}