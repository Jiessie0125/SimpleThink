package com.example.simple.simplethink.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simple.simplethink.R;
import com.example.simple.simplethink.model.bean.ShareMediaBean;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by mobileteam on 2019/7/22.
 */

public class ShareMediaPopupWindow extends PopupWindow implements View.OnClickListener,PlatformActionListener  {

    private View mPopView;
    private TextView cancelPopup;
    private ImageView shareWeChat;
    private ImageView shareMoments;
    private ImageView shareQQ;
    private ImageView shareQQSpace;
    private ImageView shareWeibo;
    private TextView shareWeChatText;
    private TextView shareMomentsText;
    private TextView shareQQText;
    private TextView shareQQSpaceText;
    private TextView shareWeiboText;
    public static String WECHAT = "wechat";
    public static String MOMENTS = "moments";
    public static String QQ = "qq";
    public static String QQSPACE = "qq_space";
    public static String WEIBO = "weibo";
    private ShareMediaBean bean;
    private Context context;


    public ShareMediaPopupWindow(Context context, String[] supportedMeidaList, ShareMediaBean bean){
        init(context);
        setPopupWindow();
        initShareWidgets(supportedMeidaList);
        this.bean = bean;
    }


    private void init(Context context) {
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        //绑定布局
        mPopView = inflater.inflate(R.layout.share_media_pop, null);
        cancelPopup = (TextView) mPopView.findViewById(R.id.share_cancel);
        cancelPopup.setOnClickListener(this);
        cancelPopup.setTag("cancel");
    }

    private void initShareWidgets(String[] supportedMeidaList) {
        if(null != supportedMeidaList && supportedMeidaList.length > 0){
            for(int i = 0; i < supportedMeidaList.length; i++){
                int identifier= context.getResources().getIdentifier("share"+i, "id", context.getPackageName());
                int identifier1= context.getResources().getIdentifier("shareText"+i, "id", context.getPackageName());
                if(supportedMeidaList[i].equals(WECHAT)){
                    Platform wechat = ShareSDK.getPlatform (Wechat.NAME);
                    boolean clientValid = wechat.isClientValid();
                    if(clientValid){
                        if(identifier == 0)
                            continue;
                        shareWeChat = (ImageView) mPopView.findViewById(identifier);
                        shareWeChat.setImageDrawable(getDrawableFromId(R.drawable.weixin));
                        if(identifier1 == 0)
                            continue;
                        shareWeChatText = (TextView) mPopView.findViewById(identifier1);
                        shareWeChatText.setText(R.string.wechat_friends);
                        shareWeChat.setVisibility(View.VISIBLE);
                        shareWeChatText.setVisibility(View.VISIBLE);
                        shareWeChat.setTag(WECHAT);
                        shareWeChat.setOnClickListener(this);
                        continue;
                    }
                }
                if(supportedMeidaList[i].equals(MOMENTS)){
                    Platform wechat = ShareSDK.getPlatform (Wechat.NAME);
                    boolean clientValid = wechat.isClientValid();
                    if(clientValid){
                        if(identifier == 0)
                            continue;
                        shareMoments = (ImageView) mPopView.findViewById(identifier);
                        shareMoments.setImageDrawable(getDrawableFromId(R.drawable.pengyouquan));
                        if(identifier1 == 0)
                            continue;
                        shareMomentsText = (TextView) mPopView.findViewById(identifier1);
                        shareMoments.setVisibility(View.VISIBLE);
                        shareMomentsText.setVisibility(View.VISIBLE);
                        shareMomentsText.setText(R.string.wechat_moments);
                        shareMoments.setTag(MOMENTS);
                        shareMoments.setOnClickListener(this);
                        continue;
                    }
                }
                if(supportedMeidaList[i].equals(QQ)){
                    Platform qq = ShareSDK.getPlatform (cn.sharesdk.tencent.qq.QQ.NAME);
                    boolean clientValid = qq.isClientValid();
                    if(clientValid){
                        if(identifier == 0)
                            continue;
                        shareQQ = (ImageView) mPopView.findViewById(identifier);
                        shareQQ.setImageDrawable(getDrawableFromId(R.drawable.qq));
                        shareQQText = (TextView) mPopView.findViewById(identifier1);
                        shareQQText.setText(R.string.qq_friends);
                        shareQQ.setVisibility(View.VISIBLE);
                        shareQQText.setVisibility(View.VISIBLE);
                        shareQQ.setTag(QQ);
                        shareQQ.setOnClickListener(this);
                        continue;
                    }
                }
                if(supportedMeidaList[i].equals(QQSPACE)){
                    Platform qq = ShareSDK.getPlatform (cn.sharesdk.tencent.qq.QQ.NAME);
                    boolean clientValid = qq.isClientValid();
                    if(clientValid){
                        shareQQSpace = (ImageView) mPopView.findViewById(identifier);
                        shareQQSpace.setImageDrawable(getDrawableFromId(R.drawable.qq_space));
                        shareQQSpaceText = (TextView) mPopView.findViewById(identifier1);
                        shareQQSpaceText.setText(R.string.qq_space);
                        shareQQSpace.setVisibility(View.VISIBLE);
                        shareQQSpaceText.setVisibility(View.VISIBLE);
                        shareQQSpace.setTag(QQSPACE);
                        shareQQSpace.setOnClickListener(this);
                        continue;
                    }
                }
                if(supportedMeidaList[i].equals(WEIBO)){
                    Platform weibo = ShareSDK.getPlatform (SinaWeibo.NAME);
                    boolean clientValid = weibo.isClientValid();
                    if(clientValid){
                        shareWeibo = (ImageView) mPopView.findViewById(identifier);
                        shareWeibo.setImageDrawable(getDrawableFromId(R.drawable.weibo));
                        shareWeiboText = (TextView) mPopView.findViewById(identifier1);
                        shareWeiboText.setText(R.string.sina_weibo);
                        shareWeibo.setVisibility(View.VISIBLE);
                        shareWeiboText.setVisibility(View.VISIBLE);
                        shareWeibo.setTag(WEIBO);
                        shareWeibo.setOnClickListener(this);
                        continue;
                    }
                }
            }
        }
    }

    /**
     * 设置窗口的相关属性
     */
    private void setPopupWindow() {
        this.setContentView(mPopView);// 设置View
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);// 设置弹出窗口的宽
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);// 设置弹出窗口的高
        this.setFocusable(true);// 设置弹出窗口可
        this.setAnimationStyle(R.style.sharePopwindow_anim_style);// 设置动画
        mPopView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = mPopView.findViewById(R.id.share_popup).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }


    @Override
    public void onClick(View v) {
        if(v.getTag().equals("cancel")){
            dismiss();
        }else if(v.getTag().equals(WECHAT)){
            showWechatShare(this.bean);
        }else if(v.getTag().equals(MOMENTS)){
            showCOFShare(this.bean);
        }else if(v.getTag().equals(QQ)){
            showQQShare(this.bean);
        }else if(v.getTag().equals(QQSPACE)){
            showQQSpaceShare(this.bean);
        }else if(v.getTag().equals(WEIBO)){
            showWeiboShare(this.bean);
        }
    }


    private void showWechatShare(ShareMediaBean bean){
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setShareType(bean.getShareType());
        sp.setImageData(bean.getImageData());
        sp.setText(bean.getText());
        sp.setTitle(bean.getTitle());
        sp.setUrl(bean.getUrl());
        Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
        wechat.setPlatformActionListener(this);
        wechat.share(sp);
    }

    //wechat moments
    private void showCOFShare(ShareMediaBean bean) {
        WechatMoments.ShareParams sp = new WechatMoments.ShareParams();
        sp.setShareType(bean.getShareType());
        sp.setTitle(bean.getTitle());
        sp.setUrl(bean.getUrl());
        sp.setText(bean.getText());
        sp.setImageUrl(bean.getImageUrl());
        sp.setImageData(bean.getImageData());
        Platform weixinGroup = ShareSDK.getPlatform(WechatMoments.NAME);
        weixinGroup.setPlatformActionListener(this);
        weixinGroup.share(sp);
    }


    //qq sharing
    private void showQQShare(ShareMediaBean bean) {
        cn.sharesdk.tencent.qq.QQ.ShareParams sp = new QQ.ShareParams();
        sp.setShareType(bean.getShareType());
        sp.setTitle(bean.getTitle());
        sp.setTitleUrl(bean.getUrl());
        sp.setText(bean.getText());
        sp.setImageData(bean.getImageData());
        sp.setImageUrl(bean.getImageUrl());
        sp.setImagePath(bean.getImagePath());
        Platform qq = ShareSDK.getPlatform(cn.sharesdk.tencent.qq.QQ.NAME);
        qq.setPlatformActionListener(this);
        qq.share(sp);
    }


    private void showQQSpaceShare(ShareMediaBean bean) {
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setTitle(bean.getTitle());
        sp.setTitleUrl(bean.getUrl()); // 标题的超链接
        sp.setText(bean.getText());
        sp.setImageUrl(bean.getImageUrl());
        sp.setImagePath(bean.getImagePath());
        sp.setSite(bean.getSite());
        sp.setSiteUrl(bean.getSiteUrl());
        sp.setShareType(bean.getShareType());
        Platform qzone = ShareSDK.getPlatform(QZone.NAME);
        qzone.setPlatformActionListener(this);
        qzone.share(sp);
    }

    private void showWeiboShare(ShareMediaBean bean){
        SinaWeibo.ShareParams sp = new SinaWeibo.ShareParams();
        sp.setShareType(bean.getShareType());
        sp.setText(bean.getText());
        sp.setImageData(bean.getImageData());
        sp.setImagePath(bean.getImagePath());
        sp.setImageUrl(bean.getImageUrl());
        Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
        weibo.setPlatformActionListener(this);
        weibo.share(sp);
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        Toast.makeText(context, "分享成功", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        Toast.makeText(context, "分享失败", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCancel(Platform platform, int i) {
        Toast.makeText(context, "取消分享", Toast.LENGTH_LONG).show();
    }


    private BitmapDrawable getDrawableFromId(int id){
        Resources res = this.context.getResources();
        return (BitmapDrawable)res.getDrawable(id);
    }
}

