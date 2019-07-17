package com.example.simple.simplethink.welcome.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.simple.simplethink.R;
import com.example.simple.simplethink.main.MainActivity;
import com.example.simple.simplethink.model.BannerResponse;
import com.example.simple.simplethink.utils.ImageUtil;
import com.example.simple.simplethink.utils.LocalDataCache;
import com.example.simple.simplethink.utils.URLConstant;

import java.io.File;

/**
 * Created by mobileteam on 2019/7/15.
 */

public class SplashActivity extends Activity implements View.OnClickListener{

    private MyCountDownTimer mc;
    private TextView tv;
    private ImageView iv;
    private String APP_IMAGE_DIR = "sort_item";
    private String imagePath = Environment.getExternalStorageDirectory().toString() + File.separator + APP_IMAGE_DIR+ File.separator + "splashBanner.jpg";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        tv = (TextView) findViewById(R.id.tv_jump);
        tv.setTag("jump");
        tv.setOnClickListener(this);
        iv = (ImageView) findViewById(R.id.iv_picture);
        initSplashPage();
    }

     void initSplashPage(){

        BannerResponse bannerResponse = (BannerResponse) LocalDataCache.INSTANCE.getLocalData(URLConstant.GETSPLASHBANNER);
        String url = bannerResponse.getImgURL();
        String linkPage = bannerResponse.getLessionsID();
        ImageUtil.INSTANCE.showBKImage(imagePath,this, iv);
        mc = new MyCountDownTimer(5000, 1000);
        mc.start();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mc.onFinish();
                enterHomeActivity();
            }
        },4000);

    }

    private void enterHomeActivity(){
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        if(view.getTag().equals("jump")){
            mc.onFinish();
            enterHomeActivity();
            return;
        }

    }


    class MyCountDownTimer extends CountDownTimer {
        /**
         *
         * @param millisInFuture
         *      表示以毫秒为单位 倒计时的总数
         *
         *      例如 millisInFuture=1000 表示1秒
         *
         * @param countDownInterval
         *      表示 间隔 多少微秒 调用一次 onTick 方法
         *
         *      例如: countDownInterval =1000 ; 表示每1000毫秒调用一次onTick()
         *
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        public void onFinish() {
            tv.setText("正在跳转");
        }

        public void onTick(long millisUntilFinished) {
            tv.setText("跳转 " + millisUntilFinished / 1000 + "");
        }

    }
}


