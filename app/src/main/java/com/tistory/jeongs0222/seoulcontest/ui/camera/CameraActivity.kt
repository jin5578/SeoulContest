package com.tistory.jeongs0222.seoulcontest.ui.camera

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.tistory.jeongs0222.seoulcontest.R

class CameraActivity : AppCompatActivity(), CameraContract.View {

    private lateinit var mPresenter: CameraPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        init()
    }

    private fun init() {
        mPresenter = CameraPresenter()

        mPresenter.setView(this, this)
    }

    override fun onBackPressed() {
        super.onBackPressed()

        finish()
    }
}
