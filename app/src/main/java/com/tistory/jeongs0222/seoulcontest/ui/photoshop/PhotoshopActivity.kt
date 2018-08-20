package com.tistory.jeongs0222.seoulcontest.ui.photoshop

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.tistory.jeongs0222.seoulcontest.R
import kotlinx.android.synthetic.main.activity_photoshop.*

class PhotoshopActivity : AppCompatActivity() {

    private lateinit var tempUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photoshop)

        init()
    }

    private fun init() {
        val intent = intent

        tempUri = intent.getParcelableExtra("temp")

        Glide.with(this)
                .asBitmap()
                .load(tempUri)
                .into(photoshop_temp_imageView)

    }

    override fun onBackPressed() {
        super.onBackPressed()

        finish()
    }
}
