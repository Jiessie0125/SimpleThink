package com.example.simple.simplethink.welcome.Activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.example.simple.simplethink.R
import com.example.simple.simplethink.main.MainActivity
import com.example.simple.simplethink.model.BannerResponse
import com.example.simple.simplethink.netapi.HttpResposityImpl
import com.example.simple.simplethink.utils.LocalDataCache
import com.example.simple.simplethink.utils.SharedPreferencesUtil
import com.example.simple.simplethink.utils.URLConstant
import java.util.*

class WelcomeActivity : Activity() {
    lateinit var persenter: SplashContract.Presenter

    private var splashBanner:BannerResponse? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isFirstOpen = SharedPreferencesUtil.getBoolean(this, SharedPreferencesUtil.FIRST_OPEN, true)!!
        if (isFirstOpen) {
            val intent = Intent(this, WelcomeGuideActivity::class.java)
            startActivity(intent)
            finish()
            return
        }
        val bannerData = LocalDataCache.getLocalData(URLConstant.GETSPLASHBANNER)
        if(bannerData is BannerResponse){
           splashBanner = bannerData
           val date = Date()
               if(date >= Date(splashBanner?.start_time) && date <= Date(splashBanner?.end_time)) {
                   enterSplashActivity()
                   return
               }
        }
        val httpResposityImpl = HttpResposityImpl()
        persenter = SplashBannerPresenter(httpResposityImpl, this)
        persenter.getSplashBanner()

       /*如果不是第一次启动app，则启动页*/

        setContentView(R.layout.activity_welcome)

       Handler().postDelayed(Runnable {
            /*2秒后进入主页*/
            enterHomeActivity()
        }, 2000)
    }


    fun initSplashBannerData(bannerResponse: BannerResponse){
        if(bannerResponse.imgURL == splashBanner?.imgURL && bannerResponse.end_time == splashBanner?.end_time
                && bannerResponse.start_time == splashBanner?.start_time){
            return
        }else if(bannerResponse.id == null){
            LocalDataCache.save(null, URLConstant.GETSPLASHBANNER)
        }else{
            LocalDataCache.save(bannerResponse, URLConstant.GETSPLASHBANNER)
        }

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
