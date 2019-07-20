package com.example.simple.simplethink

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.simple.simplethink.utils.LocalDataCache
import com.example.simple.simplethink.utils.ResourcesUtils
import com.mob.MobSDK

class MyApp : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        var context: Context? = null
            private set

    }

    override fun onCreate() {
        super.onCreate()
        context = getApplicationContext()
        ResourcesUtils.init(this)
        MobSDK.init(this)
    }
}
