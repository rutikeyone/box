package com.box.view.screens.dashboard

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.box.R
import com.box.databinding.PartDashboardTileBinding
import com.box.domain.entity.BoxEntity

class DashboardTileView(
    context: Context,
    attributesSet: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
) : FrameLayout(context, attributesSet, defStyleAttr, defStyleRes) {

    constructor(context: Context, attributesSet: AttributeSet?, defStyleAttr: Int) : this(context, attributesSet, defStyleAttr, R.style.DefaultDashboardTileStyle)
    constructor(context: Context, attributesSet: AttributeSet?) : this(context, attributesSet, R.attr.dashboardTileStyle)
    constructor(context: Context) : this(context, null)

    private val binding: PartDashboardTileBinding

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.part_dashboard_tile, this, true)
        binding = PartDashboardTileBinding.bind(this)
        parseAttributes(attributesSet, defStyleAttr, defStyleRes)
    }

    fun setBox(box: BoxEntity) {
        val colorName = box.colorName
        val boxTitle = context.getString(R.string.box_title, colorName)
        setupTitle(boxTitle)
        setupColors(box.colorValue)
    }

    private fun parseAttributes(attributesSet: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        val defaultColor = ContextCompat.getColor(context, R.color.defaultDashboardColor)
        val defaultTitle = "No Title"

        val color: Int
        val title: String
        if (attributesSet != null) {
            val typedArray = context.obtainStyledAttributes(attributesSet, R.styleable.DashboardTileView, defStyleAttr, defStyleRes)
            color = typedArray.getColor(R.styleable.DashboardTileView_color, defaultColor)
            title = typedArray.getString(R.styleable.DashboardTileView_title) ?: defaultTitle
            typedArray.recycle()
        } else {
            color = defaultColor
            title = defaultTitle
        }
        setupColors(color)
        setupTitle(title)
    }

    private fun setupTitle(title: String) {
        binding.titleTextView.text = title
    }

    private fun setupColors(strokeColor: Int) {
        val bgColor = getBackgroundColor(strokeColor)

        val backgroundDrawable = GradientDrawable()
        backgroundDrawable.color = ColorStateList.valueOf(bgColor)
        backgroundDrawable.setStroke(resources.getDimensionPixelSize(R.dimen.dashboard_item_stroke_width), strokeColor)
        backgroundDrawable.cornerRadius = resources.getDimensionPixelSize(R.dimen.dp_8).toFloat()

        binding.titleTextView.setTextColor(strokeColor)
        background = RippleDrawable(ColorStateList.valueOf(Color.BLACK), backgroundDrawable, null)
    }

    companion object {
        fun getBackgroundColor(color: Int): Int {
            val red = Color.red(color)
            val green = Color.green(color)
            val blue = Color.blue(color)
            return Color.argb(64, red, green, blue)
        }
    }
}