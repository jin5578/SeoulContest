package com.tistory.jeongs0222.seoulcontest.util

import android.content.Context
import android.content.res.Configuration
import android.hardware.Camera
import android.view.SurfaceHolder
import android.view.SurfaceView

class CameraPreview(internal val context: Context, val camera: Camera): SurfaceView(context), SurfaceHolder.Callback {




    init {

        holder.addCallback(this)
    }


    override fun surfaceCreated(p0: SurfaceHolder?) {
        val params: Camera.Parameters = camera.parameters

        var sizes: List<Camera.Size> = params.supportedPictureSizes

        var mSize: Camera.Size? = null

        for (size in sizes) {
            mSize = size
        }

        if(this.resources.configuration.orientation != Configuration.ORIENTATION_LANDSCAPE) {
            params.set("orientation", "portrait")

            camera.setDisplayOrientation(90)

            params.setRotation(90)
        } else {
            params.set("orientation", "landscape")

            camera.setDisplayOrientation(0)

            params.setRotation(0)
        }

        params.setPictureSize(mSize!!.width, mSize!!.height)

        camera.parameters = params

        camera.setPreviewDisplay(holder)

        camera.startPreview()
    }

    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {

    }

    override fun surfaceDestroyed(p0: SurfaceHolder?) {
        camera.stopPreview()

        camera.release()
    }


}