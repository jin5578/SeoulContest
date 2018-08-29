package com.tistory.jeongs0222.seoulcontest.ui.photoshop

import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v7.widget.RecyclerView
import android.transition.TransitionManager
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.tistory.jeongs0222.seoulcontest.R
import com.tistory.jeongs0222.seoulcontest.util.copy
import com.tistory.jeongs0222.seoulcontest.util.updateWith
import kotlinx.android.synthetic.main.activity_photoshop.*

class PhotoshopActivity : AppCompatActivity(), PhotoshopContract.View {

    private lateinit var mPresenter: PhotoshopPresenter

    private lateinit var tempUri: Uri

    private lateinit var originalConstraintSet: ConstraintSet
    private lateinit var constraintSet1: ConstraintSet
    private lateinit var constraintLayout: ConstraintLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photoshop)

        init()
    }

    private fun init() {
        constraintLayout = findViewById(R.id.parent) as ConstraintLayout

        originalConstraintSet = ConstraintSet()
        originalConstraintSet.clone(constraintLayout)

        constraintSet1 = originalConstraintSet.copy().apply { updateWith(this@PhotoshopActivity, R.layout.revise_layout) }

        mPresenter = PhotoshopPresenter()

        mPresenter.setView(this, this, assets)

        mPresenter.setUpRecyclerView(0)

        getValue()

        onClickEvent()
    }

    private fun getValue() {
        val intent = intent

        tempUri = intent.getParcelableExtra("temp")

        Glide.with(this)
                .asBitmap()
                .load(tempUri)
                .into(photoshop_temp_imageView)
    }

    private fun onClickEvent() {
        photoshop_revise_imageView.setOnClickListener {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                transitionSet(0)

                photoshopVisibility(0)
            }
        }

        photoshop_close_imageView.setOnClickListener {
            transitionSet(1)

            photoshopVisibility(1)
        }

        photoshop_stamp_imageView.setOnClickListener {
            mPresenter.setUpRecyclerView(0)
        }

        photoshop_font_imageView.setOnClickListener {
            mPresenter.setUpRecyclerView(1)
        }
    }

    private fun transitionSet(sort: Int) {
        when(sort) {
            0 -> {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    TransitionManager.beginDelayedTransition(constraintLayout)

                    constraintSet1.applyTo(constraintLayout)
                }
            }

            1 -> {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    TransitionManager.beginDelayedTransition(constraintLayout)

                    originalConstraintSet.applyTo(constraintLayout)
                }
            }
        }

    }

    private fun photoshopVisibility(sort: Int) {
        when(sort) {
            0 -> {
                photoshop_tool_constraintLayout.visibility = View.GONE

                photoshop_revise_constraintLayout.visibility = View.VISIBLE
            }

            1 -> {
                photoshop_tool_constraintLayout.visibility = View.VISIBLE

                photoshop_revise_constraintLayout.visibility = View.GONE
            }
        }
    }

    override fun recyclerView(): RecyclerView = photoshop_recyclerView

    override fun tempLinearLayout(): LinearLayout = photoshop_temp_linearLayout

    override fun onBackPressed() {
        super.onBackPressed()

        finish()
    }
}
