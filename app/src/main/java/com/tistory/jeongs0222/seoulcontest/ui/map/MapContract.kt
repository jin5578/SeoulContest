package com.tistory.jeongs0222.seoulcontest.ui.map

import android.content.Context


interface MapContract {

    interface View {

    }

    interface Presenter {
        fun setView(view: View, context: Context)
    }
}