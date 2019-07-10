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
        /*首先启动该Activity，并判断是否是第一次启动,注意，需要添加默认值,
         * 如果是第一次启动，则先进入功能引导页*/
        val isFirstOpen = SharedPreferencesUtil.getBoolean(this, SharedPreferencesUtil.FIRST_OPEN, true)!!
        if (isFirstOpen) {
            val intent = Intent(this, WelcomeGuideActivity::class.java)
            startActivity(intent)
            /*注意，需要使用finish将该activity进行销毁，否则，在按下手机返回键时，会返回至启动页*/
            finish()
            return
        }
        /*如果不是第一次启动app，则启动页*/
        setContentView(R.layout.activity_main)

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
