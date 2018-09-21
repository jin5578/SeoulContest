package com.tistory.jeongs0222.seoulcontest.ui.photoshop

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.AssetManager
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.tistory.jeongs0222.seoulcontest.util.DynamicViewUtil


class PhotoshopPresenter: PhotoshopContract.Presenter {

    private val TAG = "PhotoshopPresenter"

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
                divideStamp(it)
            }


            2 -> pAdapter = PhotoshopPaintAdapter(context) {
                dividePaint(it)
            }

            3 -> fAdapter = PhotoshopFontAdapter(context) {
                divideFont(it)
            }
        }

        view.recyclerView().apply {
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
    private fun divideStamp(it: Int) {
        when(it) {
            0 -> {
                if(existTextView(0)) {
                    view.tempLinearLayout().findViewById<TextView>(1).apply {
                        Log.e(TAG, "1")

                        gravity = Gravity.LEFT or Gravity.TOP

                    }

                    view.tempLinearLayout().findViewById<TextView>(2).apply {
                        Log.e(TAG, "2")

                        gravity = Gravity.RIGHT or Gravity.BOTTOM
                    }
                    //view.tempLinearLayout().findViewById<TextView>(1).gravity = Gravity.LEFT
                    //view.tempLinearLayout().findViewById<TextView>(1).gravity = Gravity.START and Gravity.TOP

                    //view.tempLinearLayout().findViewById<TextView>(2).gravity = Gravity.END and Gravity.BOTTOM
                }
            }
        }
    }

    @SuppressLint("ResourceType")
    private fun dividePaint(it: Int) {
        if(existTextView(0)) {
            DynamicViewUtil.dynamicFontColor(it, view.tempLinearLayout().findViewById(1))
            DynamicViewUtil.dynamicFontColor(it, view.tempLinearLayout().findViewById(2))
        } else if(existTextView(1)) {
            DynamicViewUtil.dynamicFontColor(it, view.tempLinearLayout().findViewById(1))
        } else if(existTextView(2)) {
            DynamicViewUtil.dynamicFontColor(it, view.tempLinearLayout().findViewById(2))
        }
    }

    @SuppressLint("ResourceType")
    private fun divideFont(it: Int) {
        if (existTextView(0)) {
            DynamicViewUtil.dynamicFont(it, view.tempLinearLayout().findViewById(1), assets)
            DynamicViewUtil.dynamicFont(it, view.tempLinearLayout().findViewById(2), assets)
        } else if (existTextView(1)) {
            DynamicViewUtil.dynamicFont(it, view.tempLinearLayout().findViewById(1), assets)
        } else if(existTextView(2)) {
            DynamicViewUtil.dynamicFont(it, view.tempLinearLayout().findViewById(2), assets)
        }
    }


    @SuppressLint("ResourceType")
    private fun dynamicStoreTextView(charSequence: CharSequence) {
        /*if(view.tempLinearLayout().findViewById<TextView>(1) != null) {
            view.tempLinearLayout().removeView(view.tempLinearLayout().findViewById(1))
        }*/
        view.tempLinearLayout().removeAllViews()

        view.tempLinearLayout().addView(DynamicViewUtil.dynamicStoreText(context, assets, charSequence))

        if(charSequence.isBlank()) {
            view.tempLinearLayout().removeView(view.tempLinearLayout().findViewById(1))
        }

        //GrayScale
        //view.tempImageView().setColorFilter(DynamicViewUtil.grayScale())
    }

    @SuppressLint("ResourceType")
    private fun dynamicMenuTextView(charSequence: CharSequence) {

        if(existTextView(2)) {
            view.tempLinearLayout().removeView(view.tempLinearLayout().findViewById(2))
        }

        view.tempLinearLayout().addView(DynamicViewUtil.dynamicMenuText(context, assets, charSequence))
    }

    @SuppressLint("ResourceType")
    private fun existTextView(sort: Int): Boolean {
        when(sort) {
            0 -> return view.tempLinearLayout().findViewById<TextView>(1) != null && view.tempLinearLayout().findViewById<TextView>(2) != null


            1 -> return view.tempLinearLayout().findViewById<TextView>(1) != null


            2 -> return view.tempLinearLayout().findViewById<TextView>(2) != null
        }

        return false
    }
}