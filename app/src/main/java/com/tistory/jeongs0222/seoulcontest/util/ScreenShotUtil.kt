package com.tistory.jeongs0222.seoulcontest.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Rect
import android.net.Uri
import android.os.AsyncTask
import android.os.Environment
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.security.AccessController.getContext

class ScreenShotUtil(internal val context: Context) {

    fun takeScreenShot(activity: Activity): ByteArray {
        val view = activity.window.decorView

        view.isDrawingCacheEnabled = true
        view.buildDrawingCache()

        val b1 = view.getDrawingCache()
        val frame = Rect()

        activity.window.decorView.getWindowVisibleDisplayFrame(frame)

        val statusBarHeight = frame.top

        val width = activity.windowManager.defaultDisplay.width
        val height = activity.windowManager.defaultDisplay.height

        val b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height-statusBarHeight)

        view.destroyDrawingCache()

        //bitmap을 byte array로 변환
        val stream = ByteArrayOutputStream()
        b.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val currentData = stream.toByteArray()

        return currentData
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
            context.sendBroadcast(mediaScanIntent)


            return null
        }


    }
}