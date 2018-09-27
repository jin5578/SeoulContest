package com.tistory.jeongs0222.seoulcontest.ui.map

import android.content.Context


class MapPresenter: MapContract.Presenter {

    private lateinit var view: MapContract.View
    private lateinit var context: Context

    override fun setView(view: MapContract.View, context: Context) {
        this.view = view
        this.context = context
    }


}