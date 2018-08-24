package com.tistory.jeongs0222.seoulcontest.ui.photoshop

import android.content.Context
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper


class PhotoshopPresenter: PhotoshopContract.Presenter {

    private lateinit var view: PhotoshopContract.View
    private lateinit var context: Context

    private lateinit var mAdapter: PhotoshopStampAdapter


    override fun setView(view: PhotoshopContract.View, context: Context) {
        this.view = view
        this.context = context
    }

    override fun setUpRecyclerView() {
        mAdapter = PhotoshopStampAdapter(context)

        view.recyclerView().apply {
            adapter = mAdapter

            layoutManager = LinearLayoutManager(context, OrientationHelper.HORIZONTAL, false)
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
            scrollToPosition(0)
        }
    }
}