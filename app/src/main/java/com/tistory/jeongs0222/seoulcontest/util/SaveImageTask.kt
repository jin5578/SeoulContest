package com.tistory.jeongs0222.seoulcontest.util

import android.os.AsyncTask
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileOutputStream

/*class SaveImageTask : AsyncTask<ByteArray, Void, Void>() {

    private val TAG = "SaveImageTask"


    override fun doInBackground(vararg p0: ByteArray?): Void {
        var outStream: FileOutputStream

        val path = File(Environment.getExternalStorageDirectory().absolutePath + "/camtest")

        if(!path.exists()) {
            path.mkdirs()
        }

        val filename = String.format("%d.jpg", System.currentTimeMillis())

        var outputFile = File(path, filename)

        outStream = FileOutputStream(outputFile)

        outStream.write(p0[0])
        outStream.flush()
        outStream.close()

        Log.d(TAG, "onPictureTaken - wrote bytes: " + p0.size + " to "
                + outputFile.getAbsolutePath());



    }


}*/