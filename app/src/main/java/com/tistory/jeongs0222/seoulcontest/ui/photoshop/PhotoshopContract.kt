package com.tistory.jeongs0222.seoulcontest.ui.photoshop

import android.content.Context
import android.support.v7.widget.RecyclerView


interface PhotoshopContract {

    interface View {
        fun recyclerView(): RecyclerView
    }

    interface Presenter {
        fun setView(view: View, context: Context)

        fun setUpRecyclerView()
    }
}