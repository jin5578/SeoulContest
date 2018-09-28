package com.tistory.jeongs0222.seoulcontest.ui.place

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.widget.EditText


interface PlaceContract {

    interface View {
        fun nameEditText(): EditText

        fun searchVisibility(sort: Int)

        fun recyclerView(): RecyclerView
    }

    interface Presenter {
        fun setView(view: PlaceContract.View, context: Context)

        fun setUpSearchFunc()

        fun setUpRecyclerView(order: Int, callback: (String) -> Unit)

        fun setUpData()

        fun loadMore()

        fun disposableClear()
    }
}