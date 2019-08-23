package com.example.simple.simplethink.welcome.Activity

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
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
import com.example.simple.simplethink.totle.TotleActivity

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
        getIntent().getSerializableExtra("BannerResponse")?.let {
            bannerResponse = it as BannerResponse
        }

        getIntent().getSerializableExtra("ActivityResponse")?.let {
            activityResponse = it as ActivityResponse
        }
        getIntent().getSerializableExtra("BottomActivityResponse")?.let {
           bottomActivityResponse = it as BottomActivityResponse
       }

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
        bean.shareType = Platform.SHARE_WEBPAGE
        bean.title = "简单冥想"
        bean.text = "累了？来放松一下"

        if (bannerResponse != null) {
            bean.imageUrl = bannerResponse!!.imgURL
            bean.url = bannerResponse!!.lessionsID
        }
        if (activityResponse != null) {
            bean.imageUrl = activityResponse!!.imgURL
            bean.url = activityResponse!!.lessionsID
        }
        if (bottomActivityResponse != null) {
            bean.imageUrl = bottomActivityResponse!!.imgURL
            bean.url = bottomActivityResponse!!.lessionsID
        }
        val logo = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)
        bean.imageData = logo

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
        val intent = Intent(this, TotleActivity::class.java)
        startActivity(intent)
        finish()
    }
}

