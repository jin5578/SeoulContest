package com.tistory.jeongs0222.seoulcontest.util

import android.content.Context
import android.content.res.AssetManager
import android.graphics.*
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView


object DynamicViewUtil {

    fun dynamicText(context: Context, assets: AssetManager): TextView {
        val textView = TextView(context)

        textView.text = "2018\n AUGUST 4TH\n 13:00 PM"
        textView.typeface = Typeface.createFromAsset(assets, "fonts/goyang.otf")
        textView.textSize = 30F
        textView.gravity = Gravity.CENTER_HORIZONTAL
        textView.setTextColor(Color.BLACK)

        return textView
    }

}