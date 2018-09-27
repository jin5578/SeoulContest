package com.tistory.jeongs0222.seoulcontest.ui.map

import android.content.Context
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager


class MapPresenter: MapContract.Presenter {

    private lateinit var view: MapContract.View
    private lateinit var context: Context

    private lateinit var mAdapter: MapAdapter

    override fun setView(view: MapContract.View, context: Context) {
        this.view = view
        this.context = context
    }

    override fun setUpRecyclerView() {
        mAdapter = MapAdapter(context)

        view.recyclerView().apply {
            adapter = mAdapter
            layoutManager = GridLayoutManager(context, 3)
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
            scrollToPosition(0)
        }
    }


}