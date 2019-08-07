package com.example.simple.simplethink.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;

import com.example.simple.simplethink.R;

/**
 * Created by mobileteam on 2019/7/30.
 */

public class ProtocolActivity extends Activity implements View.OnClickListener {

    private ImageView back;
    private WebView webView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocol);
        back = (ImageView) findViewById(R.id.protocol_back_btn);
        back.setOnClickListener(this);
        webView = (WebView) findViewById(R.id.protocol_webView);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.loadUrl("http://www.simplemeditation.cn/privacy/");
    }

    @Override
    public void onClick(View view) {
        finish();
    }


    public static Intent newIntent(Context context) {
            return new Intent(context, ProtocolActivity.class);
    }

}
