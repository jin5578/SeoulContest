package com.tistory.jeongs0222.seoulcontest.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.AssetManager
import android.graphics.*
import android.view.Gravity
import android.widget.TextView


object DynamicViewUtil {

    @SuppressLint("ResourceType")
    fun dynamicStoreText(context: Context, assets: AssetManager, charSequence: CharSequence): TextView {
        val textView = TextView(context)

        textView.id = 1
        //textView.text = "SHAKE SHACK BURGER\n\n CHEESE BURGER"
        textView.text = charSequence
        textView.typeface = Typeface.createFromAsset(assets, "fonts/goyang.otf")
        textView.textSize = 20.toFloat()
        textView.gravity = Gravity.CENTER_HORIZONTAL
        textView.setTextColor(Color.WHITE)

        return textView
    }

    @SuppressLint("ResourceType")
    fun dynamicMenuText(context: Context, assets: AssetManager, charSequence: CharSequence): TextView {
        val textView = TextView(context)

        textView.id = 2
        textView.text = charSequence
        textView.typeface = Typeface.createFromAsset(assets, "fonts/goyang.otf")
        textView.textSize = 15.toFloat()
        textView.gravity = Gravity.CENTER_HORIZONTAL
        textView.setTextColor(Color.WHITE)

        return textView
    }

    fun dynamicTagText(context: Context, assets: AssetManager, store: String): TextView {
        val textView = TextView(context)

        textView.text = store
        textView.typeface = Typeface.createFromAsset(assets, "fonts/goyang.otf")
        textView.textSize = 14.toFloat()
        textView.gravity = Gravity.CENTER
        textView.setTextColor(Color.DKGRAY)

        return textView
    }

    fun dynamicPosition(textView: TextView) {

    }

    fun dynamicFontColor(sort: Int, textView: TextView) {
        textView.setTextColor(Color.parseColor(ArrayUtil.paintColorList[sort]))

        //return textView
    }

    fun dynamicFont(sort: Int, textView: TextView, assets: AssetManager) {
        textView.typeface = Typeface.createFromAsset(assets, ArrayUtil.fontsList[sort])
    }

    fun dynamicTextSize(size: Int, textView: TextView) {
        textView.textSize = size.toFloat()
    }

    fun grayScale(): ColorMatrixColorFilter {
        val matrix = ColorMatrix()
        matrix.setSaturation(0F)

        val colorFilter = ColorMatrixColorFilter(matrix)

        return colorFilter
    }





}