package com.example.simple.simplethink.welcome.Activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Environment
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.bumptech.glide.Glide
import com.example.simple.simplethink.R
import com.example.simple.simplethink.base.BaseActivity
import com.example.simple.simplethink.main.MainActivity
import com.example.simple.simplethink.model.BannerResponse
import com.example.simple.simplethink.totle.TotleActivity
import com.example.simple.simplethink.totle.activity.course.CourseDetailActivity
import com.example.simple.simplethink.utils.ImageUtil
import com.example.simple.simplethink.utils.LocalDataCache
import com.example.simple.simplethink.utils.URLConstant
import com.example.simple.simplethink.vip.VIPCenterActivity

import java.io.File

/**
 * Created by mobileteam on 2019/7/15.
 */

class SplashActivity : BaseActivity(), View.OnClickListener {

    private var mc: MyCountDownTimer? = null
    private var tv: TextView? = null
    private var iv: ImageView? = null
    private var tag: String? = null
    private var linkPage: String? = null
    private var handler: Handler? = null
    private var isAppRestart = false
    private var bannerResponse: BannerResponse? = null
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val intent = getIntent()
        isAppRestart = intent.getBooleanExtra("isAppRestart", false)
        tv = findViewById(R.id.tv_jump) as TextView
        tv!!.setTag("jump")
        tv!!.setOnClickListener(this)
        iv = findViewById(R.id.iv_picture) as ImageView
        initSplashPage()
    }

    internal fun initSplashPage() {

        bannerResponse = LocalDataCache.getLocalData(URLConstant.GETSPLASHBANNER) as BannerResponse?
        tag = bannerResponse!!.tag
        linkPage = bannerResponse!!.lessionsID
        Glide.with(this).load(bannerResponse!!.imgURL).into(iv!!)
        iv!!.setOnClickListener(this)
        mc = MyCountDownTimer(5000, 1000)
        mc!!.start()
        handler = Handler()
        handler!!.postDelayed(object : Runnable {
            public override fun run() {
                mc!!.onFinish()
                enterActivity(TotleActivity::class.java, "", null)
            }
        }, 4000)

    }

    private fun enterActivity(activity: Class<*>, from: String, bannerResponse: BannerResponse?) {

        if (isAppRestart) {
            finish()
            return
        }

        val intent = Intent(this@SplashActivity, activity)

        if (from == "") {
            intent.putExtra("from", "main")
        } else {
            intent.putExtra("from", from)
        }

        if (bannerResponse != null) {
            intent.putExtra("BannerResponse", bannerResponse)
        }
        startActivity(intent)
        finish()
    }

    public override fun onClick(view: View) {
        handler!!.removeCallbacksAndMessages(null)
        if (view.getTag() == "jump") {
            mc!!.onFinish()
            enterActivity(TotleActivity::class.java, "", null)
            return
        } else {
            when (tag) {
                "vip" -> {
                    val intent = VIPCenterActivity.newIntent(this)
                    startActivity(intent)
                    finish()
                }
                "lessions" -> {
                    val intent = CourseDetailActivity.newIntent(linkPage!!.toInt(),this)
                    startActivity(intent)
                    finish()
                }
                "advertisment" -> enterActivity(AdvertisementActivity::class.java, "main", bannerResponse)
            };
        }

    }


    internal inner class MyCountDownTimer
    /**
     *
     * @param millisInFuture
     * 表示以毫秒为单位 倒计时的总数
     *
     * 例如 millisInFuture=1000 表示1秒
     *
     * @param countDownInterval
     * 表示 间隔 多少微秒 调用一次 onTick 方法
     *
     * 例如: countDownInterval =1000 ; 表示每1000毫秒调用一次onTick()
     */
    (millisInFuture: Long, countDownInterval: Long) : CountDownTimer(millisInFuture, countDownInterval) {

        public override fun onFinish() {
            tv!!.setText("正在跳转")
        }

        public override fun onTick(millisUntilFinished: Long) {
            tv!!.setText("跳转 " + millisUntilFinished / 1000 + "")
        }

    }
}


