package com.example.simple.simplethink.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simple.simplethink.R;
import com.example.simple.simplethink.model.ShareItem;
import com.example.simple.simplethink.model.bean.ShareMediaBean;
import com.example.simple.simplethink.totle.adapter.ShareAdapter;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
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
    private RecyclerView popContent;
    public static String WECHAT = "wechat";
    public static String MOMENTS = "moments";
    public static String QQ = "qq";
    public static String QQSPACE = "qq_space";
    public static String WEIBO = "weibo";
    private ShareMediaBean bean;
    private Context context;
    private ShareAdapter shareItemAdpter;;


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
        ArrayList<ShareItem> shareItemArray = new ArrayList();

        if(null != supportedMeidaList && supportedMeidaList.length > 0){
            for(int i = 0; i < supportedMeidaList.length; i++){
                if(supportedMeidaList[i].equals(WECHAT)){
                    Platform wechat = ShareSDK.getPlatform (Wechat.NAME);
                    boolean clientValid = wechat.isClientValid();
                    if(clientValid){
                        ShareItem shareItem = new ShareItem(R.drawable.weixin, ResourcesUtils.Companion.getString(R.string.wechat_friends));
                        shareItemArray.add(shareItem);
                        continue;
                    }
                }
                if(supportedMeidaList[i].equals(MOMENTS)){
                    Platform wechat = ShareSDK.getPlatform (Wechat.NAME);
                    boolean clientValid = wechat.isClientValid();
                    if(clientValid){
                        ShareItem shareItem = new ShareItem(R.drawable.pengyouquan, ResourcesUtils.Companion.getString(R.string.wechat_moments));
                        shareItemArray.add(shareItem);
                        continue;
                    }
                }
                if(supportedMeidaList[i].equals(QQ)){
                    Platform qq = ShareSDK.getPlatform (cn.sharesdk.tencent.qq.QQ.NAME);
                    boolean clientValid = qq.isClientValid();
                    if(clientValid){
                        ShareItem shareItem = new ShareItem(R.drawable.qq, ResourcesUtils.Companion.getString(R.string.qq_friends));
                        shareItemArray.add(shareItem);
                        continue;
                    }
                }
                if(supportedMeidaList[i].equals(QQSPACE)){
                    Platform qq = ShareSDK.getPlatform (cn.sharesdk.tencent.qq.QQ.NAME);
                    boolean clientValid = qq.isClientValid();
                    if(clientValid){
                        ShareItem shareItem = new ShareItem(R.drawable.qq_space, ResourcesUtils.Companion.getString(R.string.qq_space));
                        shareItemArray.add(shareItem);
                        continue;
                    }
                }
                if(supportedMeidaList[i].equals(WEIBO)){
                    Platform weibo = ShareSDK.getPlatform (SinaWeibo.NAME);
                    boolean clientValid = weibo.isClientValid();
                    if(clientValid){
                        ShareItem shareItem = new ShareItem(R.drawable.weibo, ResourcesUtils.Companion.getString(R.string.sina_weibo));
                        shareItemArray.add(shareItem);
                        continue;
                    }
                }
            }
        }
        shareItemAdpter = new ShareAdapter(this.context ,shareItemArray);
        popContent = mPopView.findViewById(R.id.popContent);
        popContent.isNestedScrollingEnabled();
        popContent.setLayoutManager(new GridLayoutManager(this.context, 3));
        popContent.setAdapter(shareItemAdpter);
        shareItemAdpter.setOnItemClickListener(new ShareAdapter.OnSharetemClickListener(){
            @Override
            public void onItemClick(@Nullable View v, int position, @Nullable String shareItem) {
                if(shareItem.equals(ResourcesUtils.Companion.getString(R.string.wechat_friends))){
                    showWechatShare(bean);
                }else if(shareItem.equals(ResourcesUtils.Companion.getString(R.string.wechat_moments))){
                    showCOFShare(bean);
                }else if(shareItem.equals(ResourcesUtils.Companion.getString(R.string.qq_friends))){
                    showQQShare(bean);
                }else if(shareItem.equals(ResourcesUtils.Companion.getString(R.string.qq_space))){
                    showQQSpaceShare(bean);
                }else if(shareItem.equals(ResourcesUtils.Companion.getString(R.string.sina_weibo))){
                    showWeiboShare(bean);
                }
            }
        });
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

