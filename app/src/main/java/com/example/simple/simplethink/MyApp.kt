package com.example.simple.simplethink

import android.app.Application
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.simple.simplethink.utils.LocalDataCache
import com.example.simple.simplethink.utils.ResourcesUtils

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        ResourcesUtils.init(this)
        LocalDataCache.init(this)
    }
}
