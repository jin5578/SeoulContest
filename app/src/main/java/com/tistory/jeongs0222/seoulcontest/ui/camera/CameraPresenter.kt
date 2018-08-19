package com.tistory.jeongs0222.seoulcontest.ui.camera

import android.content.Context


class CameraPresenter: CameraContract.Presenter {

    private lateinit var view: CameraContract.View
    private lateinit var context: Context

    override fun setView(view: CameraContract.View, context: Context) {
        this.view = view
        this.context = context
    }

}