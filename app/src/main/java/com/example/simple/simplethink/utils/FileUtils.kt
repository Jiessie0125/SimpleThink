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
object FileUtils{
    const val APP_IMAGE_DIR = "sort_item"
    var filename = Environment.getExternalStorageDirectory().appendText(APP_IMAGE_DIR) as String

   fun saveFileToDisc(res : ResponseBody?,fileName : String) : Boolean{
       res?.let {
           val bitmap = BitmapFactory.decodeByteArray(res.bytes(), 0, res.bytes().size)
           savaBitmap(bitmap,fileName)
           return true
       }
       return  false
   }

    fun savaBitmap(bitmap: Bitmap, strFileName: String) {
        val file = File(filename)
        if (!file.exists()) {
            file.mkdirs()
        }
        val f = File(file, strFileName)
        var fOut: FileOutputStream? = null
        try {
            fOut = FileOutputStream(f)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut)//把Bitmap对象解析成流
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            fOut!!.flush()
            fOut!!.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
}