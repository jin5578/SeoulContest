package com.tistory.jeongs0222.seoulcontest.ui.map

import android.content.Context
import android.support.v7.widget.RecyclerView


interface MapContract {

    interface View {
        fun recyclerView(): RecyclerView
    }

    interface Presenter {
        fun setView(view: View, context: Context)

        fun setUpRecyclerView()

    }
}