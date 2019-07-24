package com.example.simple.simplethink.welcome.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.example.simple.simplethink.R;
import com.example.simple.simplethink.main.MainActivity;
import com.example.simple.simplethink.model.BannerResponse;
import com.example.simple.simplethink.model.bean.ShareMediaBean;
import com.example.simple.simplethink.utils.LocalDataCache;
import com.example.simple.simplethink.utils.ShareMediaPopupWindow;
import com.example.simple.simplethink.utils.URLConstant;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by mobileteam on 2019/7/17.
 */

public class AdvertisementActivity extends Activity implements View.OnClickListener {

    private WebView webView;
    private BannerResponse bannerResponse;
    private ImageView back_btn;
    private ImageView share_btn;
    private String[] supportMediaList = {ShareMediaPopupWindow.WECHAT,ShareMediaPopupWindow.MOMENTS,ShareMediaPopupWindow.QQ,
    ShareMediaPopupWindow.QQSPACE,ShareMediaPopupWindow.WEIBO};
    private ShareMediaBean bean = new ShareMediaBean();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement);
        webView = (WebView) findViewById(R.id.ad_webView);
        bannerResponse = (BannerResponse) LocalDataCache.INSTANCE.getLocalData(URLConstant.GETSPLASHBANNER);
        webView.loadUrl(bannerResponse.getLessionsID());
        back_btn = (ImageView) findViewById(R.id.ad_back_btn);
        back_btn.setTag("back");
        back_btn.setOnClickListener(this);
        share_btn = (ImageView) findViewById(R.id.ad_share_btn);
        share_btn.setTag("share");
        share_btn.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initBean() {
        bean.setShareType(Platform.SHARE_WEBPAGE);
        bean.setTitle("简单冥想");
        bean.setText("累了？来放松一下");
        bean.setImageUrl(bannerResponse.getImgURL());
        bean.setUrl(bannerResponse.getLessionsID());
    }

    @Override
    public void onClick(View view) {
        if(view.getTag().equals("back")){
            enterHomeActivity();
        }else {
            initBean();
            showPopFormBottom();
        }
    }

    private void showPopFormBottom() {
        ShareMediaPopupWindow shareMediaPopupWindow = new ShareMediaPopupWindow(this,supportMediaList,bean);
        shareMediaPopupWindow.showAtLocation(findViewById(R.id.ad_popup), Gravity.BOTTOM, 0, 0);
    }


    private void enterHomeActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

