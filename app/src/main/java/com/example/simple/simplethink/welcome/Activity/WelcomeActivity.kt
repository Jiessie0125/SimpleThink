package com.example.simple.simplethink.welcome.Activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.annotation.RequiresApi
import com.example.simple.simplethink.R
import com.example.simple.simplethink.main.MainActivity
import com.example.simple.simplethink.model.BannerResponse
import com.example.simple.simplethink.netapi.HttpResposityImpl

import com.example.simple.simplethink.utils.SharedPreferencesUtil
import java.util.*

class WelcomeActivity : Activity() {
    lateinit var persenter: SplashContract.Presenter


    companion object {
        const val SPLASHBANNER = "SPLASHBANNER"
        fun newIntent (context: Context?, bannerResponse: BannerResponse) : Intent {
            var intent = Intent(context, WelcomeActivity::class.java)
            intent.putExtra(SPLASHBANNER,bannerResponse)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isFirstOpen = SharedPreferencesUtil.getBoolean(this, SharedPreferencesUtil.FIRST_OPEN, true)!!
        if (isFirstOpen) {
            val intent = Intent(this, WelcomeGuideActivity::class.java)
            startActivity(intent)
            finish()
            return
        }


//        val splashBanner = SharedPreferencesUtil.getBannerBean(this)
//        val date = Date()
//        if(splashBanner != null && date >= Date(splashBanner.start_time) && date <= Date(splashBanner.end_time)){
//            enterSplashActivity()
//        }else{
//            initSplashBannerData()
//        }

       /*如果不是第一次启动app，则启动页*/
        setContentView(R.layout.activity_welcome)

       Handler().postDelayed(Runnable {
            /*2秒后进入主页*/
            enterHomeActivity()
        }, 2000)
    }

    fun initSplashBannerData(){
        val httpResposityImpl = HttpResposityImpl()
        persenter = SplashBannerPresenter(httpResposityImpl, this)
        val intent = intent
        val bannerResponse = intent.getSerializableExtra(SPLASHBANNER) as BannerResponse
        SharedPreferencesUtil.setBannerBean(this,bannerResponse)
    }

    private fun enterSplashActivity(){
        val intent = Intent(this, SplashActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun enterHomeActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
