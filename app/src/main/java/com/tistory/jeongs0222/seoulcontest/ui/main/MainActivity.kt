package com.tistory.jeongs0222.seoulcontest.ui.main

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.tistory.jeongs0222.seoulcontest.R
import com.tistory.jeongs0222.seoulcontest.ui.camera.CameraActivity
import com.tistory.jeongs0222.seoulcontest.ui.picture.PictureActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {

        onClickEvent()
    }

    private fun onClickEvent() {
        main_camera_imageView.setOnClickListener {
            startActivity(CameraActivity::class.java)
        }

        main_picture_imageView.setOnClickListener {
            startActivity(PictureActivity::class.java)
        }
    }

    private fun startActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            val animate = ActivityOptions.makeCustomAnimation(applicationContext, R.anim.slide_from_right, R.anim.slide_to_left).toBundle()

            startActivity(intent, animate)
        } else {
            startActivity(intent)
        }
    }
}
