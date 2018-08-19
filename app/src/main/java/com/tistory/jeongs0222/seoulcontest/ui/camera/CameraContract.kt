package com.tistory.jeongs0222.seoulcontest.ui.camera

import android.content.Context

interface CameraContract {

    interface View {

    }

    interface Presenter {
        fun setView(view: View, context: Context)
    }
}