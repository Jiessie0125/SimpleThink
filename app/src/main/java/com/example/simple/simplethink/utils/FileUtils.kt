package com.example.simple.simplethink.utils

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Environment
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.simple.simplethink.R
import com.example.simple.simplethink.totle.adapter.SceneDetailAdapter
import kotlinx.android.synthetic.main.fragment_totle.*
import okhttp3.ResponseBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

/**
 * Created by mobileteam on 2019/6/6.
 */
object FilesUtils{
    val APP_IMAGE_DIR = "sort_item"
    var filename = Environment.getExternalStorageDirectory().toString() + File.separator + APP_IMAGE_DIR
    val pictureFolder = Environment.getExternalStorageDirectory().toString()

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

    fun getItemIcon(strItemIcon : String,activity: Activity,imageView: ImageView?){
        val options = RequestOptions()
                .placeholder(R.drawable.first_use)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
        try {
            val appDir = File(pictureFolder, APP_IMAGE_DIR)
            if (!appDir.exists()) throw Exception("can't find folder")
            val fileName = strItemIcon + ".jpg"
            val destFile = File(appDir, fileName)
            if (!destFile.exists()) throw Exception("can't find image")
            Glide.with(activity)
                           .load(destFile)
                           .apply(options)
                           .into(imageView!!)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getLocalFileUrl(sceneName : String ) : String{
        var filePath = Environment.getExternalStorageDirectory().toString() + File.separator + SceneDetailAdapter.SCENEDETAIL
        val appDir = File(filePath, sceneName)
        if (!appDir.exists()) throw Exception("can't find folder")
        return appDir.toString()

    }

    fun showImage(imageUrl: String,activity: Activity,imageView: ImageView?){
        Glide.with(activity)
                .load(imageUrl)
                .into(imageView!!)
    }

    fun downloadImage( activity: Activity, url: String, imageName :String) {

        Thread(Runnable {
            try {
                val target = Glide.with(activity)
                        .load(url)
                        .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get()
                activity?.runOnUiThread(Runnable {
                    //第二个参数为你想要保存的目录名称
                    val appDir = File(pictureFolder, APP_IMAGE_DIR);
                    if (!appDir.exists()) {
                        appDir.mkdirs();
                    }
                    val fileName = imageName + ".jpg"
                    val destFile = File(appDir, fileName)
                    copy(target,destFile)
                })
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }).start()
    }

    fun copy(source: File, target: File) {
        var fileInputStream: FileInputStream? = null
        var fileOutputStream: FileOutputStream? = null
        try {
            fileInputStream = FileInputStream(source)
            fileOutputStream = FileOutputStream(target)
            val buffer = ByteArray(1024)
            while (fileInputStream!!.read(buffer) > 0) {
                fileOutputStream.write(buffer)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream!!.close()
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }
    fun timeParse(duration: Long): String {
        var time = ""
        val minute = duration / 60000
        val seconds = duration % 60000
        val second = Math.round(seconds.toFloat() / 1000).toLong()
        if (minute < 10) {
            time += "0"
        }
        time += minute.toString() + ":"
        if (second < 10) {
            time += "0"
        }
        time += second
        return time
    }


}