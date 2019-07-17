package com.example.simple.simplethink.welcome.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import com.example.simple.simplethink.R;
import com.example.simple.simplethink.model.BannerResponse;
import com.example.simple.simplethink.utils.LocalDataCache;
import com.example.simple.simplethink.utils.URLConstant;

/**
 * Created by mobileteam on 2019/7/17.
 */

public class AdvertisementActivity extends Activity {

    private WebView webView;
    private BannerResponse bannerResponse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement);
        webView = (WebView) findViewById(R.id.ad_webView);
        bannerResponse = (BannerResponse) LocalDataCache.INSTANCE.getLocalData(URLConstant.GETSPLASHBANNER);
        webView.loadUrl(bannerResponse.getLessionsID());
    }
}

