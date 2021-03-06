package com.tistory.jeongs0222.seoulcontest.ui.photoshop


import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.transition.TransitionManager
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.tistory.jeongs0222.seoulcontest.R
import com.tistory.jeongs0222.seoulcontest.util.ScreenShotUtil
import com.tistory.jeongs0222.seoulcontest.util.copy
import com.tistory.jeongs0222.seoulcontest.util.updateWith
import kotlinx.android.synthetic.main.activity_photoshop.*

class PhotoshopActivity : AppCompatActivity(), PhotoshopContract.View {

    private lateinit var mPresenter: PhotoshopPresenter

    private lateinit var tempUri: Uri

    private var screenShotPath: String = " "

    private lateinit var originalConstraintSet: ConstraintSet
    private lateinit var constraintSet1: ConstraintSet
    private lateinit var constraintLayout: ConstraintLayout

    private lateinit var mScreenShotUtil: ScreenShotUtil

    var posXY = IntArray(2)


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

        //tempLinearWidthHeight()
    }

    private fun onClickEvent() {
        photoshop_down_imageView.setOnClickListener {
            mScreenShotUtil = ScreenShotUtil(this, photoshop_temp_imageView)

            photoshop_temp_imageView.getLocationOnScreen(posXY)

            mScreenShotUtil.SaveImageTask() {
                screenShotPath = it
            }.execute(mScreenShotUtil.takeScreenShot(this))

            showSnackbar(findViewById(R.id.parent), "FOODSTAMP에 사진을 업로드하시겠습니까?", Snackbar.LENGTH_LONG)

        }

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


        photoshop_placeHolder_imageView.setOnClickListener {
            mPresenter.setUpRecyclerView(0)
        }

        photoshop_stamp_imageView.setOnClickListener {
            mPresenter.setUpRecyclerView(1)
        }

        photoshop_size_imageView.setOnClickListener {
            mPresenter.setUpRecyclerView(2)
        }


        photoshop_paint_imageView.setOnClickListener {
            mPresenter.setUpRecyclerView(3)
        }

        photoshop_font_imageView.setOnClickListener {
            mPresenter.setUpRecyclerView(4)
        }
    }

    private fun showSnackbar(view: View, message: String, duration: Int) {
        val snackbar = Snackbar.make(view, message, duration)
        snackbar.setActionTextColor(Color.BLACK)

        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(resources.getColor(R.color.colorTabSelected))

        val snackbarTextView = snackbarView.findViewById<TextView>(android.support.design.R.id.snackbar_text)
        snackbarTextView.apply {
            setTextColor(Color.WHITE)
            setTextSize(13F)
            typeface = Typeface.createFromAsset(assets, "fonts/goyang.otf")
        }

        val snackbarActionTextView = snackbarView.findViewById<TextView>(android.support.design.R.id.snackbar_action)
        snackbarActionTextView.apply {
            setTextColor(Color.WHITE)
            setTextSize(13F)
            typeface = Typeface.createFromAsset(assets, "fonts/goyang.otf")
        }

        snackbar.setAction("확인", View.OnClickListener {
            snackbar.dismiss()

            confirmClickable(0)

            mPresenter.postData(screenShotPath)
        })

        snackbar.show()
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

    override fun tempImageView(): ImageView = photoshop_temp_imageView

    override fun tempRelativeLayout(): RelativeLayout = photoshop_temp_linearLayout

    override fun confirmClickable(value: Int) {
        when(value) {
            0 -> photoshop_progressBar.visibility = View.VISIBLE

            1 -> photoshop_progressBar.visibility = View.GONE
        }
    }

    override fun viewFinish() {
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == RESULT_OK) {
            if(requestCode == 2) {
                mPresenter.dynamicStoreTextView(data!!.getStringExtra("name"))
            }
        }
    }

    override fun onDestroy() {
        mPresenter.disposableClear()

        super.onDestroy()
    }

    override fun onBackPressed() {
        super.onBackPressed()

        finish()
    }
}
