package com.tistory.jeongs0222.seoulcontest.ui.photoshop

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.AssetManager
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.widget.TextView
import com.tistory.jeongs0222.seoulcontest.R
import com.tistory.jeongs0222.seoulcontest.util.DynamicViewUtil


class PhotoshopPresenter: PhotoshopContract.Presenter {

    private lateinit var view: PhotoshopContract.View
    private lateinit var context: Context
    private lateinit var assets: AssetManager

    private lateinit var hAdapter: PhotoshopPlaceAdapter
    private lateinit var mAdapter: PhotoshopStampAdapter
    private lateinit var pAdapter: PhotoshopPaintAdapter
    private lateinit var fAdapter: PhotoshopFontAdapter


    override fun setView(view: PhotoshopContract.View, context: Context, assets: AssetManager) {
        this.view = view
        this.context = context
        this.assets = assets
    }

    @SuppressLint("ResourceType")
    override fun setUpRecyclerView(sort: Int) {

        when(sort) {
            0 -> hAdapter = PhotoshopPlaceAdapter(context) { String, it ->
                if(String.equals("1")) {
                    dynamicStoreTextView(it)
                } else {
                    dynamicMenuTextView(it)
                }
            }

            1 -> mAdapter = PhotoshopStampAdapter(context) {
            }


            2 -> pAdapter = PhotoshopPaintAdapter(context) {
                if(view.tempLinearLayout().findViewById<TextView>(1) != null) {
                    DynamicViewUtil.dynamicFontColor(it, view.tempLinearLayout().findViewById(1))
                }
            }

            3 -> fAdapter = PhotoshopFontAdapter(context) {
                if(view.tempLinearLayout().findViewById<TextView>(1) != null) {
                    DynamicViewUtil.dynamicFont(it, view.tempLinearLayout().findViewById(1), assets)
                }
            }
        }

        view.recyclerView().apply {
            //adapter = if(sort == 0) mAdapter else fAdapter
            adapter = when(sort) {
                0 -> hAdapter

                1 -> mAdapter

                2 -> pAdapter

                3 -> fAdapter

                else -> null
            }

            layoutManager = LinearLayoutManager(context, OrientationHelper.HORIZONTAL, false)
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
            scrollToPosition(0)
        }
    }

    @SuppressLint("ResourceType")
    private fun dynamicStoreTextView(charSequence: CharSequence) {

        /*if(view.tempLinearLayout().findViewById<TextView>(1) != null) {
            view.tempLinearLayout().removeView(view.tempLinearLayout().findViewById(1))
        }*/
        view.tempLinearLayout().removeAllViews()

        view.tempLinearLayout().addView(DynamicViewUtil.dynamicStoreText(context, assets, charSequence))

        //GrayScale
        //view.tempImageView().setColorFilter(DynamicViewUtil.grayScale())
    }

    @SuppressLint("ResourceType")
    private fun dynamicMenuTextView(charSequence: CharSequence) {

        if(view.tempLinearLayout().findViewById<TextView>(2) != null) {
            view.tempLinearLayout().removeView(view.tempLinearLayout().findViewById(2))
        }

        view.tempLinearLayout().addView(DynamicViewUtil.dynamicMenuText(context, assets, charSequence))
    }

}