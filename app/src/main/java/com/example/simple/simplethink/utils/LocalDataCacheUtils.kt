package com.example.simple.simplethink.utils

import android.annotation.SuppressLint
import android.content.Context
import com.example.simple.simplethink.MyApp
import java.io.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import kotlin.experimental.and

@SuppressLint("StaticFieldLeak")
/**
 * Created by mobileteam on 2019/6/26.
 */
object LocalDataCache {

    /**
     * 两个小时后为时间超时
     */
    val CACHE_DURATION = (1000 * 60 * 60 * 2).toLong()//2小时
    private var messageDigest: MessageDigest? = null
    /**
     * 获取缓存在本地的数据
     *
     * @param fileName 保存的文件名
     * @return
     */
    fun getLocalData(fileName: String): Any? {
        val path = MyApp.context?.getFilesDir().toString()
        val file = File(path + "/" + Md5(fileName) + ".config")
        file.getAbsolutePath()
        if (file.exists()) {
            if (isValid(file)) {
                try {
                    val fin = FileInputStream(file)
                    val oin = ObjectInputStream(fin)
                    val `object` = oin.readObject()
                    oin.close()
                    return `object`
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
        return null
    }

    /**
     * 保存数据
     *
     * @param object   保存数据的对象
     * @param fileName 保存的文件名
     */
    fun save(`object`: Any, fileName: String) {
        val path = MyApp.context?.getFilesDir().toString()
        val file = File(path + "/" + Md5(fileName) + ".config")
        if (file.exists()) {
            file.delete()
        }
        try {
            val fos = FileOutputStream(file, false)
            val oos = ObjectOutputStream(fos)
            oos.writeObject(`object`)
            oos.flush()
            oos.close()
        } catch (e: Exception) {
            // TODO: handle exception
            e.printStackTrace()
        }

    }

    /**
     * 判断该文件是否有效
     *
     * @param file
     * @return
     */
    private fun isValid(file: File): Boolean {
        //文件存在了多长时间
        val existDuration = System.currentTimeMillis() - file.lastModified()
        return existDuration <= CACHE_DURATION
    }

    fun Md5(message: String): String? {
        val sb = StringBuilder()
        try {
            //algorithm: 加密的方式
            if (messageDigest == null) {
                messageDigest = MessageDigest.getInstance("MD5")
            }
            //1.将数据转化成byte数组，并对byte数组进行第一次加密,返回的就是加密过的byte数组
            val digest = messageDigest!!.digest(message.toByteArray())
            for (i in digest.indices) {
                //2.将加密过的byte数组的元素和255进行与运算
                //-128 - 127
                val result = digest[i].toInt() and 0xff
                //因为得到int类型的值，可能会比较大，将int类型的值转化成十六进制的字符串
                val hexString = Integer.toHexString(result)
                if (hexString.length < 2) {
                    //System.out.print("0");
                    sb.append("0")
                }
                //System.out.println(hexString);
                sb.append(hexString)
            }
            return sb.toString()
        } catch (e: NoSuchAlgorithmException) {
            //找不到算法的异常
            e.printStackTrace()
        }

        return null
    }
}