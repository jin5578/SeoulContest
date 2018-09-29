package com.tistory.jeongs0222.seoulcontest.ui.photoshop

import android.content.Context
import android.content.res.AssetManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.RelativeLayout


interface PhotoshopContract {

    interface View {

        fun recyclerView(): RecyclerView

        fun tempImageView(): ImageView

        fun tempRelativeLayout(): RelativeLayout

        fun confirmClickable(value: Int)

        fun viewFinish()
    }

    interface Presenter {
        fun setView(view: View, context: Context, assets: AssetManager)

        fun setUpRecyclerView(sort: Int)

        fun postData(path: String)

        fun disposableClear()
    }
}