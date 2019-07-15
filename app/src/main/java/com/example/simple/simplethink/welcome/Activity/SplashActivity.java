package com.example.simple.simplethink.welcome.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.simple.simplethink.R;
import com.example.simple.simplethink.main.MainActivity;

/**
 * Created by mobileteam on 2019/7/15.
 */

public class SplashActivity extends Activity implements View.OnClickListener{

    private MyCountDownTimer mc;
    private TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        tv = (TextView) findViewById(R.id.tv_jump);
        tv.setTag("jump");
        tv.setOnClickListener(this);
        mc = new MyCountDownTimer(4000, 1000);
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


