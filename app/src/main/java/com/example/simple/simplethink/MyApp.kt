package com.example.simple.simplethink

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.simple.simplethink.utils.ResourcesUtils
import com.example.simple.simplethink.welcome.Activity.WelcomeActivity
import com.mob.MobSDK

//import com.mob.MobSDK

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
        initBackgroundCallBack()
    }


    private fun initBackgroundCallBack() {
        var count = 0
        var isRunInBackground = false
        var time = System.currentTimeMillis()
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
            }

            override fun onActivityResumed(activity: Activity?) {
            }

            override fun onActivityPaused(activity: Activity?) {
            }

            override fun onActivityStarted(activity: Activity?) {
                count++
                if (isRunInBackground) {
                    // 后台到前台，在此进行相应操作
                    isRunInBackground = false
                    if (System.currentTimeMillis() - time > 180000) {
                        val intent = Intent(context, WelcomeActivity::class.java)
                        intent.putExtra("isAppRestart", true)
                        startActivity(intent)
                    }

                }
            }

            override fun onActivityStopped(activity: Activity?) {
                count--
                if (count == 0) {
                    // 前台到后台，在此进行相应操作
                    isRunInBackground = true
                    time = System.currentTimeMillis()
                }
            }

            override fun onActivityDestroyed(activity: Activity?) {
            }

            override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
            }
        })
    }
}
