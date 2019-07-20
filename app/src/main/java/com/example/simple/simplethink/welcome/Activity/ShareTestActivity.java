package com.example.simple.simplethink.welcome.Activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.simple.simplethink.R;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import cn.sharesdk.tencent.qzone.QZone;

/**
 * Created by mobileteam on 2019/7/18.
 */

public class ShareTestActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement);

        Button btn = (Button)findViewById(R.id.share_weChat);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap logo = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
                showWeiboShare("http://www.baidu.com", logo);
            //showQQSpaceShare("http://www.baidu.com", "");
            //showCOFShare("http://www.baidu.com","");
            //showQQShare("http://www.baidu.com","");
//                OnekeyShare oks = new OnekeyShare();
//                oks.setText("test");
//                oks.setUrl("http://www.baidu.com");
//                oks.setImageData(logo);
//                oks.show(ShareTestActivity.this);
            }
        });


    }

    private void showWechatShare(){
        Platform.ShareParams params = new Platform.ShareParams();
        Bitmap logo = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
        params.setShareType(Platform.SHARE_WEBPAGE);
        params.setImageData(logo);
        params.setText("Vincent吃屎");
        params.setTitle("吃的好开心");
        params.setUrl("http://www.baidu.com");
        Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
        wechat.share(params);
    }

    //wechat moments
    private void showCOFShare(String url, String logoUrl) {
        WechatMoments.ShareParams sp = new WechatMoments.ShareParams();
        sp.setShareType(Platform.SHARE_WEBPAGE);
        sp.setTitle("test");
        sp.setUrl(url);
        sp.setText("test11");
        sp.setImageUrl(logoUrl);
        Platform weixinGroup = ShareSDK.getPlatform(WechatMoments.NAME);
        weixinGroup.share(sp);
    }


    //qq sharing
    private void showQQShare(String url, String logoUrl) {
        QQ.ShareParams sp = new QQ.ShareParams();
        sp.setShareType(Platform.SHARE_WEBPAGE);
        sp.setTitle("标题");
        sp.setTitleUrl(url);
        sp.setText("文字");
        sp.setImageUrl(logoUrl);
        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        qq.share(sp);
    }


    public static void showQQSpaceShare(String url, String logoUrl) {
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setTitle("标题");
        sp.setTitleUrl(url); // 标题的超链接
        sp.setText("分享新用户注册链接，对方成功激活后，你的账户将获得20元感谢金。");
        sp.setImageUrl(logoUrl);
        sp.setSite("文字");
        sp.setSiteUrl(url);
        Platform qzone = ShareSDK.getPlatform(QZone.NAME);
        // 设置分享事件回调（注：回调放在不能保证在主线程调用，不可以在里面直接处理UI操作）
        qzone.setPlatformActionListener(new PlatformActionListener() {
            public void onError(Platform arg0, int arg1, Throwable arg2) {
                //失败的回调，arg:平台对象，arg1:表示当前的动作，arg2:异常信息
            }

            public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                //分享成功的回调
            }

            public void onCancel(Platform arg0, int arg1) {
                //取消分享的回调
            }
        });
        // 执行图文分享
        qzone.share(sp);
    }

    private void showWeiboShare(String url, Bitmap map){

        SinaWeibo.ShareParams sp = new SinaWeibo.ShareParams();
        sp.setShareType(Platform.SHARE_WEBPAGE);
        sp.setText("文字");
        Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
        weibo.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });
        weibo.share(sp);
    }
}
