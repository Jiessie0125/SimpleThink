package com.example.simple.simplethink.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * Created by mobileteam on 2019/6/6.
 */
object FilesUtils{
    val APP_IMAGE_DIR = "sort_item"
    var filename = Environment.getExternalStorageDirectory().toString() + File.separator + APP_IMAGE_DIR

        fun savaBitmap(message : ResponseBody, strFileName: String) {
            try {
                val bitmap = BitmapFactory.decodeStream(message.byteStream())
                val folder = File(filename)
                if (!folder.exists()) {
                    folder.mkdirs()
                }
                val savePath = folder.getPath() + File.separator + strFileName + ".png"
                val f = File(savePath)
                var fOut: FileOutputStream?
                try {
                    fOut = FileOutputStream(f)
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut)//把Bitmap对象解析成流
                    fOut!!.flush()
                    fOut!!.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }catch (e : IOException){
                e.printStackTrace()
            }

        }

}