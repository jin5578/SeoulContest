package com.tistory.jeongs0222.seoulcontest.ui.camera


import android.hardware.Camera
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.tistory.jeongs0222.seoulcontest.R
import com.tistory.jeongs0222.seoulcontest.util.CameraPreview
import com.tistory.jeongs0222.seoulcontest.util.CameraUtil
import kotlinx.android.synthetic.main.activity_camera.*

class CameraActivity : AppCompatActivity(), CameraContract.View {


    private lateinit var mPresenter: CameraPresenter
    private lateinit var mCameraUtil: CameraUtil


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //화면이 계속 켜진 상태를 유지하는 기능
        window.setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        setContentView(R.layout.activity_camera)

        init()
    }

    private fun init() {
        mPresenter = CameraPresenter()

        mPresenter.setView(this, this)

        camera_surfaceView.visibility = View.GONE

        mCameraUtil = CameraUtil(this, this, Camera.CameraInfo.CAMERA_FACING_BACK, camera_surfaceView)

        onClickEvent()
    }

    private fun onClickEvent() {
        camera_shooting_imageView.setOnClickListener {
            mCameraUtil.takePicture()
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()

        finish()
    }
}
