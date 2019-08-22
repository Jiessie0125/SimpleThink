package com.example.simple.simplethink.utils

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.simple.simplethink.MyApp.Companion.context
import com.example.simple.simplethink.R
import com.example.simple.simplethink.model.PracticeResponse
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

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

    fun getLocalFileUrl(sceneName : String , mp3Path : String) : String{
        var filePath = context?.getExternalFilesDir(mp3Path)
        val appDir = File(filePath, sceneName)
        if (!appDir.exists()) throw Exception("can't find folder")
        return appDir.toString()

    }

    fun isHaveFile(sceneName : String , mp3Path : String) :Boolean{
        var filePath = context?.getExternalFilesDir(mp3Path)
        val appDir = File(filePath, sceneName)
        if (!appDir.exists()) return false else return true
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
    fun timeParse(duration: Long?): String {
        var time = ""
        val minute = duration!! / 60000
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

    fun deleteFile(dirFile: File): Boolean {
        // 如果dir对应的文件不存在，则退出
        if (!dirFile.exists()) {
            return false
        }

        if (dirFile.isFile()) {
            return dirFile.delete()
        } else {

            for (file in dirFile.listFiles()) {
                deleteFile(file)
            }
        }

        return dirFile.delete()
    }
    fun belongCalendar( nowTime:Date,  beginTime:Date,  endTime:Date) : Boolean{
        return if (nowTime.after(beginTime) && nowTime.before(endTime)) { true} else {false}
    }

    fun getMap(jsonString: String): Map<String, List<PracticeResponse>>? {
        val jsonObject: JSONObject
        try {
            jsonObject = JSONObject(jsonString)
            val keyIter = jsonObject.keys()
            var key: String
            var value: Any
            var praticeItem : List<PracticeResponse>
            val valueMap = HashMap<String, List<PracticeResponse>>()
            while (keyIter.hasNext()) {
                key = keyIter.next()
                value = jsonObject.get(key) as JSONArray
                praticeItem = convertToPratice(value)
                valueMap.put(key, praticeItem)
            }
            return valueMap
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return null
    }

    fun convertToPratice(json : JSONArray) : List<PracticeResponse>{
        var pratice = ArrayList<PracticeResponse>()
        var gson = Gson()
        var itemPratice : PracticeResponse ?= null
        for(i in 0 until json.length()){
            itemPratice = gson.fromJson(json[i].toString(),PracticeResponse::class.java)
            pratice.add(itemPratice)
        }
        return pratice
    }
}