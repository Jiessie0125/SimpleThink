package com.example.simple.simplethink.welcome.Activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.example.simple.simplethink.R
import com.example.simple.simplethink.base.BaseActivity
import com.example.simple.simplethink.main.MainActivity
import com.example.simple.simplethink.model.BannerResponse
import com.example.simple.simplethink.netapi.HttpResposityImpl
import com.example.simple.simplethink.totle.TotleActivity
import com.example.simple.simplethink.utils.*
import java.util.*

class WelcomeActivity : BaseActivity() {
    lateinit var persenter: SplashContract.Presenter

    private var splashBanner:BannerResponse? = null
    private var isAppRestart:Boolean? = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var intent = getIntent()
        isAppRestart = intent.getBooleanExtra("isAppRestart",false)
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
           val date = DateUtils.DateToString(Date(),DateUtils.DATE_TO_STRING_DETAIAL_PATTERN)

               if(date >= splashBanner?.start_time.toString() && date <= splashBanner?.end_time.toString()) {
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
           if(isAppRestart as Boolean){
               finish()
               return@Runnable
           }
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
        intent.putExtra("isAppRestart", isAppRestart)
        startActivity(intent)
        finish()
    }

    private fun enterHomeActivity() {
        val intent = Intent(this, TotleActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun onFailure(e:Throwable){
        ErrorHandler.showErrorWithToast(this,e);
    }
}
