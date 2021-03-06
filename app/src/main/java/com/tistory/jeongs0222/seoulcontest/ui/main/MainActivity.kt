package com.tistory.jeongs0222.seoulcontest.ui.main

import android.Manifest
import android.app.Activity
import android.app.ActivityOptions
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.tistory.jeongs0222.seoulcontest.R
import com.tistory.jeongs0222.seoulcontest.ui.camera.CameraActivity
import com.tistory.jeongs0222.seoulcontest.ui.photoshop.PhotoshopActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContract.View {

    private lateinit var mPresenter: MainPresenter

    private val checkList = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)

    private val PERMISSION = 111;

    private val PICK_FROM_GALLERY = 222;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {

        mPresenter = MainPresenter()

        mPresenter.setView(this, this)

        checkPermission()

        onClickEvent()

        mPresenter.setUpRecyclerView()

        mPresenter.setUpData()

        mPresenter.loadMore()
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) ||
                    (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) ||
                    (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA))) {
            } else {
                ActivityCompat.requestPermissions(this, checkList, PERMISSION)
            }
        }
    }

    private fun onClickEvent() {

        main_camera_imageView.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                startActivity(CameraActivity::class.java)
            } else {
                permissionAlert()
            }
        }

        main_picture_imageView.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                getGallery()
            } else {
                permissionAlert()
            }
        }

    }

    private fun startActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            val animate = ActivityOptions.makeCustomAnimation(applicationContext, R.anim.slide_from_right, R.anim.slide_to_left).toBundle()

            startActivity(intent, animate)
        } else {
            startActivity(intent)
        }
    }

    fun getGallery() {
        val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE)

        startActivityForResult(intent, PICK_FROM_GALLERY)
    }

    private fun permissionAlert() {
        AlertDialog.Builder(this)
                .apply {
                    setTitle("알림")
                    setMessage("저장소 권한이 거부되었습니다. 사용을 원하시면 설정에서 해당 권한을 직접 허용하셔야 합니다.")
                    setNeutralButton("설정", DialogInterface.OnClickListener { dialogInterface, which ->
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        intent.setData(Uri.parse("package:" + packageName))

                        startActivity(intent)
                    })
                    setCancelable(false)
                }
                .create()
                .show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION) {
            if (grantResults.isNotEmpty()) {
                for (i in 0..grantResults.size - 1) {
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {

                        return
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == PICK_FROM_GALLERY) {

            if(resultCode == Activity.RESULT_OK) {

                Log.d("test","data : "+data)

                if(data != null) {
                    val temp = data.data

                    val intent = Intent(this, PhotoshopActivity::class.java)
                    intent.putExtra("temp", temp)

                    startActivity(intent)
                }
            }
        }
    }

    override fun recyclerView(): RecyclerView = main_recyclerView
}
