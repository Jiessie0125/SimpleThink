package com.example.simple.simplethink.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Environment
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.simple.simplethink.R
import kotlinx.android.synthetic.main.fragment_totle.*
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

        fun savaBitmap(message : ByteArray, strFileName: String, type: String):Boolean {
            try {
                val bitmap = BitmapFactory.decodeByteArray(message,0,message.size)
                val folder = File(filename)
                if (!folder.exists()) {
                    folder.mkdirs()
                }
                val savePath = folder.getPath() + File.separator + strFileName + "." + type
                val f = File(savePath)
                var fOut: FileOutputStream?
                try {
                    fOut = FileOutputStream(f)
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut)//把Bitmap对象解析成流
                    fOut!!.flush()
                    fOut!!.close()
                    return true
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }catch (e : IOException){
                e.printStackTrace()
            }
            return false
        }

    fun getItemIcon(strItemIcon : String, type: String) : Bitmap? {
        try {
            val folder = File(filename)
            if (!folder.exists()) throw Exception("can't find folder")
            val savePath = folder.getPath() + File.separator + strItemIcon + "." + type
            val f = File(savePath)
            if (!f.exists()) throw Exception("can't find image")
            return BitmapFactory.decodeFile(savePath)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun downloadImage( activity: Activity,imageView: ImageView?, url: String) {
        val options = RequestOptions()
                .placeholder(R.drawable.first_use)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        Thread(Runnable {
            try {
                val target = Glide.with(activity)
                        .load(url)
                        .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                activity?.runOnUiThread(Runnable {
                    Glide.with(activity)
                            .load(url)
                            .apply(options)
                            .into(imageView!!)

                })
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }).start()
    }

}