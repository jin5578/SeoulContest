package com.tistory.jeongs0222.seoulcontest.ui.photoshop

import android.content.Context
import android.content.res.AssetManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.LinearLayout


interface PhotoshopContract {

    interface View {
        fun recyclerView(): RecyclerView

        fun tempImageView(): ImageView

        fun tempLinearLayout(): LinearLayout
    }

    interface Presenter {
        fun setView(view: View, context: Context, assets: AssetManager)

        fun setUpRecyclerView(sort: Int)
    }
}