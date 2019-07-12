package com.example.simple.simplethink.welcome.Activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.example.simple.simplethink.R
import com.example.simple.simplethink.main.MainActivity

import com.example.simple.simplethink.utils.SharedPreferencesUtil

class WelcomeActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isFirstOpen = SharedPreferencesUtil.getBoolean(this, SharedPreferencesUtil.FIRST_OPEN, true)!!
        if (isFirstOpen) {
            val intent = Intent(this, WelcomeGuideActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

       /*如果不是第一次启动app，则启动页*/   setContentView(R.layout.activity_welcome)

       Handler().postDelayed(Runnable {
            /*2秒后进入主页*/
            enterHomeActivity()
        }, 2000)
    }

    private fun enterHomeActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
