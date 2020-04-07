package com.example.simple.simplethink.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simple.simplethink.R;
import com.example.simple.simplethink.base.BaseActivity;
import com.example.simple.simplethink.utils.ErrorHandler;
import com.example.simple.simplethink.utils.ValidationUtils;

/**
 * Created by mobileteam on 2019/7/25.
 */

public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener, ForgetPasswordContract.View{

    private Button submit_btn;
    private LinearLayout title_tool_back;
    private TextView countDown;
    private EditText phoneNumber;
    private EditText password1;
    private EditText password2;
    private EditText validate_sms_code;
    private boolean isTriggerCountDown = false;
    private String model;
    private Handler handler;
    private MyCountDownTimer mc;
    private ForgetPasswordPresenter presenter = new ForgetPasswordPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        presenter.bind(this);
        submit_btn = (Button)findViewById(R.id.submit_btn);
        submit_btn.setTag("submit");
        submit_btn.setOnClickListener(this);
        countDown = (TextView) findViewById(R.id.countDown);
        countDown.setTag("countDown");
        countDown.setOnClickListener(this);
        phoneNumber = (EditText)findViewById(R.id.login_phone_number);
        password1 = (EditText)findViewById(R.id.login_phone_number_pwd_1);
        password2 = (EditText)findViewById(R.id.login_phone_number_pwd_2);
        validate_sms_code = (EditText)findViewById(R.id.validate_sms_code);
        title_tool_back = (LinearLayout)findViewById(R.id.title_tool_back_all);
        title_tool_back.setTag("back");
        title_tool_back.setOnClickListener(this);
        Intent intent = getIntent();
        model = intent.getStringExtra("model");

    }

    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {

        if(view.getTag().equals("submit")){
            if(validation()){
                return;
            }
            String password_old = password1.getText().toString();
            String password_new = password2.getText().toString();
            String userName = phoneNumber.getText().toString();
            String code = validate_sms_code.getText().toString();
            if(model.equals("forgetPassword")){
                presenter.updateUserInfo(password_old, password_new, userName, code);
            }else if(model.equals("register")){
                presenter.register(password_old, password_new, userName, code);
            }

        }else if(view.getTag().equals("countDown")){
            if(isTriggerCountDown){
                return;
            }else{
                String phoneNumberText = phoneNumber.getText().toString();
                if(phoneNumberText.equals("")){
                    Toast.makeText(this, R.string.phone_number_is_empty, Toast.LENGTH_LONG).show();
                    return;
                }
                if(!ValidationUtils.isMobileNO(phoneNumberText) || phoneNumberText.length()< 11){
                    Toast.makeText(this, R.string.phone_number_is_invalid, Toast.LENGTH_LONG).show();
                    return;
                }
                presenter.sendSMS(Long.parseLong(phoneNumber.getText().toString()));
                mc = new ForgetPasswordActivity.MyCountDownTimer(60000, 1000);
                mc.start();
                isTriggerCountDown = true;
                countDown.setEnabled(false);
                countDown.setTextColor(getResources().getColorStateList(R.color.countDown));
                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mc.onFinish();
                        isTriggerCountDown = false;
                        countDown.setEnabled(true);
                        countDown.setTextColor(getResources().getColorStateList(R.color.logonButton));
                    }
                },60000);
            }
        }else if(view.getTag().equals("back")){
            finish();
        }

    }



    private boolean validation(){
        String phoneNumberText = phoneNumber.getText().toString();
        String smsText = validate_sms_code.getText().toString();
        String pwdText1 = password1.getText().toString();
        String pwdText2 = password2.getText().toString();

        if(phoneNumberText.equals("")){
            Toast.makeText(this, R.string.phone_number_is_empty, Toast.LENGTH_LONG).show();
            return true;
        }
        if(!ValidationUtils.isMobileNO(phoneNumberText) || phoneNumberText.length()< 11){
            Toast.makeText(this, R.string.phone_number_is_invalid, Toast.LENGTH_LONG).show();
            return true;
        }
        if(smsText.equals("")){
            Toast.makeText(this, R.string.sms_code_is_invalid, Toast.LENGTH_LONG).show();
            return true;
        }

        if(pwdText1.length() < 6 || pwdText2.length() < 6){
            Toast.makeText(this, R.string.password_is_lower_minimum, Toast.LENGTH_LONG).show();
            return true;
        }

        if(pwdText1.length() >18 || pwdText2.length() > 18){
            Toast.makeText(this, R.string.password_is_exceed_maximum, Toast.LENGTH_LONG).show();
            return true;
        }

        if(!pwdText1.equals(pwdText2)){
            Toast.makeText(this, R.string.password_is_not_align, Toast.LENGTH_LONG).show();
            return true;
        }

        return false;
    }

    @Override
    public void onRegisterSuccess() {
        Toast.makeText(this, R.string.register_success, Toast.LENGTH_LONG).show();
        this.finish();
    }

    @Override
    public void onSuccess() {
        Toast.makeText(this, R.string.sms_code, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailure(Throwable e) {
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
            countDown.setText(R.string.send_sms_code);
        }

        public void onTick(long millisUntilFinished) {
            countDown.setText(millisUntilFinished / 1000 + " " +getResources().getString(R.string.sms_countdown_time));
        }

    }
}
