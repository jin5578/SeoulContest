package com.tistory.jeongs0222.seoulcontest.util

import android.content.Context
import android.content.Intent
import android.hardware.Camera
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.view.SurfaceHolder
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.AsyncTask
import android.os.Environment
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import android.hardware.Camera.AutoFocusCallback


class CameraUtil(internal val context: Context, val activity: AppCompatActivity, val cameraId: Int, surfaceView: SurfaceView): ViewGroup(context), SurfaceHolder.Callback {

    private lateinit var mCamera: Camera
    private lateinit var mCameraInfo: Camera.CameraInfo

    private var mHolder: SurfaceHolder
    private var mDisplayOrientation: Int = 0
    private var mSupportedPreviewSizes: List<Camera.Size>? = null
    private var mPreviewSize: Camera.Size? = null


    private var mIsPreview = false


    init {
        surfaceView.visibility = View.VISIBLE

        mHolder = surfaceView.holder
        mHolder.addCallback(this)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val width = resolveSize(suggestedMinimumWidth, widthMeasureSpec)
        val height = View.resolveSize(suggestedMinimumHeight, heightMeasureSpec)

        setMeasuredDimension(width, height)

        if(mSupportedPreviewSizes != null) {
            mPreviewSize = getOptimalPreviewSize(mSupportedPreviewSizes!!, width, height)
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if(changed && childCount > 0) {
            val child = getChildAt(0)

            val width = r - l
            val height = b - t

            var previewWidth = width
            var previewHeight = height

            if(mPreviewSize != null) {
                previewWidth = mPreviewSize!!.width
                previewHeight = mPreviewSize!!.height
            }

            if(width * previewHeight > height * previewWidth) {
                val scaledChildWidth = previewWidth * height / previewHeight

                child.layout((width - scaledChildWidth) / 2, 0, (width + scaledChildWidth) / 2, height)
            } else {
                val scaledChildHeight = previewHeight * width / previewWidth

                child.layout(0, (height - scaledChildHeight) / 2, width, (height + scaledChildHeight) / 2);
            }
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        mCamera = Camera.open(cameraId)

        val cameraInfo = Camera.CameraInfo()

        Camera.getCameraInfo(cameraId, cameraInfo)

        mCameraInfo = cameraInfo

        mDisplayOrientation = activity.windowManager.defaultDisplay.rotation

        val orientation = calculatePreviewOrientation(mCameraInfo, mDisplayOrientation)

        mCamera.setDisplayOrientation(orientation)

        mSupportedPreviewSizes = mCamera.parameters.supportedPreviewSizes
        requestLayout()

        var params = mCamera.parameters

        val focusModes = params.supportedFocusModes

        if (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
            params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            mCamera.setParameters(params);
        }

        mCamera.setPreviewDisplay(holder)

        mCamera.startPreview()

        mIsPreview = true
    }


    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {
        if(mHolder.surface == null) {

            return
        }

        mCamera.stopPreview()

        val orientation = calculatePreviewOrientation(mCameraInfo, mDisplayOrientation)
        mCamera.setDisplayOrientation(orientation)

        mCamera.setPreviewDisplay(mHolder)
        mCamera.startPreview()
    }

    override fun surfaceDestroyed(p0: SurfaceHolder?) {
        if(mCamera != null) {
            if(mIsPreview)
                mCamera.stopPreview()

            mCamera.release()
            mIsPreview = false
        }
    }

    private fun calculatePreviewOrientation(info: Camera.CameraInfo, rotation: Int): Int {
        var degrees = 0
        var result = 0

        when(rotation) {
            Surface.ROTATION_0 -> degrees = 0
            Surface.ROTATION_90 -> degrees = 90
            Surface.ROTATION_180 -> degrees = 180
            Surface.ROTATION_270 -> degrees = 270
        }

        if(info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360

            result = (360 - result) % 360
        } else {
            result = (info.orientation - degrees + 360) % 360
        }

        return result
    }

    private fun getOptimalPreviewSize(sizes: List<Camera.Size>, w: Int, h: Int): Camera.Size {
        val ASPECT_TOLERANCE = 0.1

        val targetRatio= ((w / h).toDouble())

        if(sizes == null) null

        var optimalSize: Camera.Size? = null

        var minDiff = Double.MAX_VALUE

        val targetHeight = h

        for(size in sizes) {
            val ratio = ((size.width / size.height).toDouble())

            if(Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue

            if(Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size

                minDiff = Math.abs(size.height - targetHeight).toDouble()
            }
        }

        if(optimalSize == null) {
            minDiff = Double.MAX_VALUE

            for(size in sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size
                    minDiff = Math.abs(size.height - targetHeight).toDouble()
                }
            }
        }

        return optimalSize!!
    }

    fun takePicture() {
        val mAutoFocus = AutoFocusCallback { success, camera ->
            if(success == true) {
                mCamera.takePicture(shutterCallback, rawCallback, jpegCallback)
            }
        }

        mCamera.autoFocus(mAutoFocus)
    }

    var shutterCallback: Camera.ShutterCallback = Camera.ShutterCallback { }

    var rawCallback: Camera.PictureCallback = Camera.PictureCallback { data, camera -> }

    var jpegCallback: Camera.PictureCallback = Camera.PictureCallback { data, camera ->
        //이미지의 너비와 높이 결정
        val w = camera.parameters.pictureSize.width
        val h = camera.parameters.pictureSize.height
        val orientation = calculatePreviewOrientation(mCameraInfo, mDisplayOrientation)


        //byte array를 bitmap으로 변환
        val options = BitmapFactory.Options()
        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        var bitmap = BitmapFactory.decodeByteArray(data, 0, data.size, options)


        //이미지를 디바이스 방향으로 회전
        val matrix = Matrix()
        matrix.postRotate(orientation.toFloat())
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true)

        //bitmap을 byte array로 변환
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val currentData = stream.toByteArray()

        //파일로 저장
        SaveImageTask().execute(currentData)

    }

    inner class SaveImageTask : AsyncTask<ByteArray, Void, Void>() {

        override fun doInBackground(vararg p0: ByteArray?): Void? {
            var outStream: FileOutputStream

            val path = File(Environment.getExternalStorageDirectory().absolutePath + "/FOODSTAMP")

            if(!path.exists()) {
                path.mkdirs()
            }

            val filename = String.format("%d.jpg", System.currentTimeMillis())

            var outputFile = File(path, filename)

            outStream = FileOutputStream(outputFile)

            outStream.write(p0[0])
            outStream.flush()
            outStream.close()

            val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)

            mediaScanIntent.setData(Uri.fromFile(outputFile))
            getContext().sendBroadcast(mediaScanIntent)

            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();

            return null
        }


    }

}