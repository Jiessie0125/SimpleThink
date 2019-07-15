package com.example.simple.simplethink.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.simple.simplethink.model.BannerResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.youth.banner.Banner
import java.util.*

object SharedPreferencesUtil {
    private val FILE_NAME = "welcomePage"
    val FIRST_OPEN = "first_open"
    val COOPEN_BANNER = "coopen_banner"

    fun getBannerBean(context: Context): BannerResponse{
        val sharedPreferences = context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE)
        val json = sharedPreferences.getString(COOPEN_BANNER,null)
        return Gson().fromJson<BannerResponse>(json,BannerResponse::class.java)
    }

    fun setBannerBean(context: Context, banner:BannerResponse){
        val sharedPreferences = context.getSharedPreferences(
                FILE_NAME, Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.putString(COOPEN_BANNER, Gson().toJson(banner))
        editor.commit()
    }

    fun getBoolean(context: Context, strKey: String,
                   strDefault: Boolean?): Boolean? {

        val sharedPreferences = context.getSharedPreferences(
                FILE_NAME, Context.MODE_PRIVATE
        )

        val result = sharedPreferences.getBoolean(strKey, strDefault!!)

        return sharedPreferences.getBoolean(strKey, strDefault)
    }

    fun getBoolean(context: Context, strKey: String): Boolean? {
        val setPreferences = context.getSharedPreferences(
                FILE_NAME, Context.MODE_PRIVATE)
        val result = setPreferences.getBoolean(strKey, false)
        return setPreferences.getBoolean(strKey, false)
    }

    fun setBoolean(context: Context, strKey: String,
                   strData: Boolean?) {
        val sharedPreferences = context.getSharedPreferences(
                FILE_NAME, Context.MODE_PRIVATE
        )
        /*往shared preference文件中写入值时，我们需要用到SharedPreferences.Editor*/
        val editor = sharedPreferences.edit()
        editor.putBoolean(strKey, strData!!)
        editor.commit()
    }

    fun getString(context: Context, strKey: String): String {
        val setPreferences = context.getSharedPreferences(
                FILE_NAME, Context.MODE_PRIVATE)
        return setPreferences.getString(strKey, "")
    }

    fun getString(context: Context, strKey: String,
                  strDefault: String): String {
        val setPreferences = context.getSharedPreferences(
                FILE_NAME, Context.MODE_PRIVATE)
        return setPreferences.getString(strKey, strDefault)
    }

    fun setString(context: Context, strKey: String, strData: String) {
        val activityPreferences = context.getSharedPreferences(
                FILE_NAME, Context.MODE_PRIVATE)
        val editor = activityPreferences.edit()
        editor.putString(strKey, strData)
        editor.commit()
    }

    fun getInt(context: Context, strKey: String): Int {
        val setPreferences = context.getSharedPreferences(
                FILE_NAME, Context.MODE_PRIVATE)
        return setPreferences.getInt(strKey, -1)
    }

    fun getInt(context: Context, strKey: String, strDefault: Int): Int {
        val setPreferences = context.getSharedPreferences(
                FILE_NAME, Context.MODE_PRIVATE)
        return setPreferences.getInt(strKey, strDefault)
    }

    fun setInt(context: Context, strKey: String, strData: Int) {
        val activityPreferences = context.getSharedPreferences(
                FILE_NAME, Context.MODE_PRIVATE)
        val editor = activityPreferences.edit()
        editor.putInt(strKey, strData)
        editor.commit()
    }

    fun getLong(context: Context, strKey: String): Long {
        val setPreferences = context.getSharedPreferences(
                FILE_NAME, Context.MODE_PRIVATE)
        return setPreferences.getLong(strKey, -1)
    }

    fun getLong(context: Context, strKey: String, strDefault: Long): Long {
        val setPreferences = context.getSharedPreferences(
                FILE_NAME, Context.MODE_PRIVATE)
        val result = setPreferences.getLong(strKey, strDefault)
        return setPreferences.getLong(strKey, strDefault)
    }

    fun setLong(context: Context, strKey: String, strData: Long) {
        val activityPreferences = context.getSharedPreferences(
                FILE_NAME, Context.MODE_PRIVATE)
        val editor = activityPreferences.edit()
        editor.putLong(strKey, strData)
        editor.commit()
    }
}
