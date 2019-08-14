package com.example.simple.simplethink.welcome.Activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.ImageView

import com.example.simple.simplethink.R
import com.example.simple.simplethink.main.MainActivity
import com.example.simple.simplethink.model.ActivityResponse
import com.example.simple.simplethink.model.BannerResponse
import com.example.simple.simplethink.model.BottomActivityResponse
import com.example.simple.simplethink.model.bean.ShareMediaBean
import com.example.simple.simplethink.utils.ShareMediaPopupWindow

import cn.sharesdk.framework.Platform

/**
 * Created by mobileteam on 2019/7/17.
 */

class AdvertisementActivity : Activity(), View.OnClickListener {

    private var webView: WebView? = null
    private var bannerResponse: BannerResponse? = null
    private var activityResponse: ActivityResponse? = null
    private var bottomActivityResponse: BottomActivityResponse? = null
    private var back_btn: ImageView? = null
    private var share_btn: ImageView? = null
    private val supportMediaList = arrayOf<String>(ShareMediaPopupWindow.WECHAT, ShareMediaPopupWindow.MOMENTS, ShareMediaPopupWindow.QQ, ShareMediaPopupWindow.QQSPACE, ShareMediaPopupWindow.WEIBO)
    private val bean = ShareMediaBean()
    private var from: String? = null

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advertisement)
        webView = findViewById<View>(R.id.ad_webView) as WebView
        webView!!.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN)
        from = getIntent().getStringExtra("from")
        bannerResponse = getIntent().getSerializableExtra("BannerResponse") as BannerResponse
        activityResponse = getIntent().getSerializableExtra("ActivityResponse") as ActivityResponse
        bottomActivityResponse = getIntent().getSerializableExtra("BottomActivityResponse") as BottomActivityResponse
        if (bannerResponse != null) {
            webView!!.loadUrl(bannerResponse!!.lessionsID)
        }
        if (activityResponse != null) {
            webView!!.loadUrl(activityResponse!!.lessionsID)
        }
        if (bottomActivityResponse != null) {
            webView!!.loadUrl(bottomActivityResponse!!.lessionsID)
        }
        back_btn = findViewById<View>(R.id.ad_back_btn) as ImageView
        back_btn!!.setTag("back")
        back_btn!!.setOnClickListener(this)
        share_btn = findViewById<View>(R.id.ad_share_btn) as ImageView
        share_btn!!.setTag("share")
        share_btn!!.setOnClickListener(this)

    }

    protected override fun onDestroy() {
        super.onDestroy()
    }

    private fun initBean() {
        bean.setShareType(Platform.SHARE_WEBPAGE)
        bean.setTitle("简单冥想")
        bean.setText("累了？来放松一下")

        if (bannerResponse != null) {
            bean.setImageUrl(bannerResponse!!.imgURL)
            bean.setUrl(bannerResponse!!.lessionsID)
        }
        if (activityResponse != null) {
            bean.setImageUrl(activityResponse!!.imgURL)
            bean.setUrl(activityResponse!!.lessionsID)
        }
        if (bottomActivityResponse != null) {
            bean.setImageUrl(bottomActivityResponse!!.imgURL)
            bean.setUrl(bottomActivityResponse!!.lessionsID)
        }

    }

    public override fun onClick(view: View) {
        if (view.getTag() == "back") {
            if (from == "main") {
                enterHomeActivity()
            }
        } else {
            initBean()
            showPopFormBottom()
        }
    }

    private fun showPopFormBottom() {
        val shareMediaPopupWindow = ShareMediaPopupWindow(this, supportMediaList, bean)
        shareMediaPopupWindow.showAtLocation(findViewById<View>(R.id.ad_popup), Gravity.BOTTOM, 0, 0)
    }


    private fun enterHomeActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}

