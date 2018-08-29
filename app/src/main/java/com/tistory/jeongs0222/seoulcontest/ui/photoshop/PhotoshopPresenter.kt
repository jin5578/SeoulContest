package com.tistory.jeongs0222.seoulcontest.ui.photoshop

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.AssetManager
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.util.Log
import android.widget.TextView
import com.tistory.jeongs0222.seoulcontest.util.DynamicViewUtil


class PhotoshopPresenter: PhotoshopContract.Presenter {

    private lateinit var view: PhotoshopContract.View
    private lateinit var context: Context
    private lateinit var assets: AssetManager

    private lateinit var pAdapter: PhotoshopPaintAdapter
    private lateinit var mAdapter: PhotoshopStampAdapter
    private lateinit var fAdapter: PhotoshopFontAdapter


    override fun setView(view: PhotoshopContract.View, context: Context, assets: AssetManager) {
        this.view = view
        this.context = context
        this.assets = assets
    }

    @SuppressLint("ResourceType")
    override fun setUpRecyclerView(sort: Int) {

        when(sort) {
            0 -> pAdapter = PhotoshopPaintAdapter(context) {
                if(view.tempLinearLayout().findViewById<TextView>(1) != null) {
                    DynamicViewUtil.dynamicFontColor(it, view.tempLinearLayout().findViewById(1))
                }
            }

            1 -> mAdapter = PhotoshopStampAdapter(context) {
                dynamicTextView()
            }

            2 -> fAdapter = PhotoshopFontAdapter(context) {
                if(view.tempLinearLayout().findViewById<TextView>(1) != null) {
                    DynamicViewUtil.dynamicFont(it, view.tempLinearLayout().findViewById(1), assets)
                }
            }
        }

        view.recyclerView().apply {
            //adapter = if(sort == 0) mAdapter else fAdapter
            adapter = when(sort) {
                0 -> pAdapter

                1 -> mAdapter

                2 -> fAdapter

                else -> null
            }

            layoutManager = LinearLayoutManager(context, OrientationHelper.HORIZONTAL, false)
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
            scrollToPosition(0)
        }
    }

    private fun dynamicTextView() {

        /*view.tempLinearLayout().removeAllViews()

        val textView = TextView(context)

        textView.text = "2018\n AUGUST 4TH\n 13:00 PM"
        textView.typeface = Typeface.createFromAsset(assets, "fonts/goyang.otf")
        textView.textSize = 30F
        textView.gravity = Gravity.CENTER_HORIZONTAL
        textView.setTextColor(Color.BLACK)

        view.tempLinearLayout().addView(textView)*/

        view.tempLinearLayout().removeAllViews()

        //GrayScale
        //view.tempImageView().setColorFilter(DynamicViewUtil.grayScale())

        view.tempLinearLayout().addView(DynamicViewUtil.dynamicText(context, assets))
    }

}