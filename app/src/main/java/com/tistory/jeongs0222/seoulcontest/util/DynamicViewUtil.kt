package com.tistory.jeongs0222.seoulcontest.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.AssetManager
import android.graphics.*
import android.view.Gravity
import android.widget.TextView


object DynamicViewUtil {

    @SuppressLint("ResourceType")
    fun dynamicText(context: Context, assets: AssetManager): TextView {
        val textView = TextView(context)

        textView.id = 1
        textView.text = "SHAKE SHACK BURGER\n\n CHEESE BURGER"
        textView.typeface = Typeface.createFromAsset(assets, "fonts/goyang.otf")
        textView.textSize = 30F
        textView.gravity = Gravity.CENTER_HORIZONTAL
        textView.setTextColor(Color.WHITE)

        return textView
    }

    fun dynamicFontColor(sort: Int, textView: TextView) {
        textView.setTextColor(Color.parseColor(ArrayUtil.paintColorList[sort]))

        //return textView
    }

    fun dynamicFont(sort: Int, textView: TextView, assets: AssetManager) {
        textView.typeface = Typeface.createFromAsset(assets, ArrayUtil.fontsList[sort])
    }

    fun grayScale(): ColorMatrixColorFilter {
        val matrix = ColorMatrix()
        matrix.setSaturation(0F)

        val colorFilter = ColorMatrixColorFilter(matrix)

        return colorFilter
    }



}