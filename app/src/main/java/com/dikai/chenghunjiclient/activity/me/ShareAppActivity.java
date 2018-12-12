package com.dikai.chenghunjiclient.activity.me;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.register.LoginActivity;
import com.dikai.chenghunjiclient.bean.BeanNone;
import com.dikai.chenghunjiclient.bean.BeanNull;
import com.dikai.chenghunjiclient.bean.BeanWeChatShare;
import com.dikai.chenghunjiclient.entity.ResultGetRedRule;
import com.dikai.chenghunjiclient.entity.ResultWeChatShare;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.flyco.dialog.widget.base.BaseDialog;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

public class ShareAppActivity extends AppCompatActivity implements View.OnClickListener {

    private ServiceDialog mRedDialog;
    private ServiceDialog2 mRedDialog2;
    private TextView rule;
    private ImageView share;
    private ImageView shareBg;
    private View main_grayView;
    private LinearLayout main_share;
    private LinearLayout main_friends;
    private LinearLayout main_moments;
    private TranslateAnimation main_mShowAction;
    private TranslateAnimation main_mHiddenAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_app);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.orange_deep)
                .statusBarDarkFont(false)
                .init();
        init();
    }

    private void init() {
        rule = (TextView) findViewById(R.id.rule);
        share = (ImageView) findViewById(R.id.share);
        shareBg = (ImageView) findViewById(R.id.share_bg);
        main_grayView = findViewById(R.id.main_gray_view);
        main_share = (LinearLayout) findViewById(R.id.main_share);
        main_friends = (LinearLayout) findViewById(R.id.main_friends);
        main_moments = (LinearLayout) findViewById(R.id.main_moments);
        mRedDialog = new ServiceDialog(this);
        mRedDialog.widthScale(1);
        mRedDialog.heightScale(1);
        mRedDialog2 = new ServiceDialog2(this);
        mRedDialog2.widthScale(1);
        mRedDialog2.heightScale(1);
        share.setOnClickListener(this);
        main_friends.setOnClickListener(this);
        main_moments.setOnClickListener(this);
        main_grayView.setOnClickListener(this);
        main_mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        main_mShowAction.setDuration(260);

        main_mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f);
        main_mHiddenAction.setDuration(260);
        Glide.with(this).load(R.drawable.share_bg).into(shareBg);
        getRedRule();
    }

    @Override
    public void onClick(View v) {
        if(v == share){
            if(UserManager.getInstance(ShareAppActivity.this).isLogin()){
                showBottom();
            }else {
                startActivity(new Intent(ShareAppActivity.this, LoginActivity.class));
            }
        }else if(v == main_grayView){
            hideBottom();
        }else if(v == main_friends){
            hideBottom();
            showShare(true);
        }else if(v == main_moments){
            hideBottom();
            showShare(false);
        }
    }

    private void showBottom(){
        main_grayView.setVisibility(View.VISIBLE);
        main_share.clearAnimation();
        main_share.setVisibility(View.VISIBLE);
        main_share.startAnimation(main_mShowAction);
    }

    private void hideBottom(){
        main_grayView.setVisibility(View.GONE);
        main_share.clearAnimation();
        main_share.startAnimation(main_mHiddenAction);
        main_share.setVisibility(View.GONE);
    }

    /**
     * 首次登录红包
     */
    private class ServiceDialog extends BaseDialog<ServiceDialog> implements View.OnClickListener {

        private ImageView close;
        private ImageView packedImg;
        private ImageView getNow;
        private TextView money;
        private String moneyNum = "0.5";

        public void setMoneyNum(String moneyNum) {
            this.moneyNum = moneyNum;
        }

        public ServiceDialog(Context context) {
            super(context);
        }

        @Override
        public View onCreateView() {
            View view = View.inflate(mContext, R.layout.dialog_share_app, null);
            close = (ImageView) view.findViewById(R.id.close);
            packedImg = (ImageView) view.findViewById(R.id.red_packed_img);
            getNow = (ImageView) view.findViewById(R.id.get_now);
            money = (TextView) view.findViewById(R.id.money);
            close.setOnClickListener(this);
            getNow.setOnClickListener(this);
            return view;
        }

        @Override
        public void onViewCreated(View view) {
            super.onViewCreated(view);
            money.setText(moneyNum);
            Glide.with(getContext()).load(R.drawable.share_app_success).into(packedImg);
        }

        @Override
        public void setUiBeforShow() {
            if(money != null)
            money.setText(moneyNum);
        }

        @Override
        public void onClick(View v) {
            if(v == close){
                this.dismiss();
            }else if(v == getNow){
                this.dismiss();
                showBottom();
            }
        }
    }

    /**
     * 首次登录红包
     */
    private class ServiceDialog2 extends BaseDialog<ServiceDialog2> implements View.OnClickListener {

        private ImageView close;
        private ImageView packedImg;
        private ImageView getNow;

        public ServiceDialog2(Context context) {
            super(context);
        }

        @Override
        public View onCreateView() {
            View view = View.inflate(mContext, R.layout.dialog_share_limit_layout, null);
            close = (ImageView) view.findViewById(R.id.close);
            packedImg = (ImageView) view.findViewById(R.id.red_packed_img);
            getNow = (ImageView) view.findViewById(R.id.get_now);
            close.setOnClickListener(this);
            getNow.setOnClickListener(this);
            return view;
        }

        @Override
        public void onViewCreated(View view) {
            super.onViewCreated(view);
            Glide.with(getContext()).load(R.drawable.share_app_fail).into(packedImg);
        }

        @Override
        public void setUiBeforShow() {

        }

        @Override
        public void onClick(View v) {
            if(v == close){
                this.dismiss();
            }else if(v == getNow){
                this.dismiss();
                showBottom();
            }
        }
    }


    private void showShare(final boolean isToFriends) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("【邀好友】成婚纪送现金福利！点开即得！");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://www.chenghunji.com/Redbag/index");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("在这和你相遇，2018天天有惊喜！最高可享100元现金福利哦！还有更多福利，尽在成婚纪！");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("");//确保SDcard下面存在此张图片
        oks.setImageUrl("http://www.chenghunji.com/Download/User/wechat_red_packet.png");
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://www.chenghunji.com/Redbag/index");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        // oks.setComment("成婚纪——婚礼原来如此简单");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("成婚纪");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://www.chenghunji.com/");
        if(isToFriends){
            oks.setPlatform(Wechat.NAME);//微信好友
        }else {
            oks.setPlatform(WechatMoments.NAME);//微信朋友圈
        }

        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                shareRedPacket(isToFriends);
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Toast.makeText(ShareAppActivity.this, "分享失败！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(Platform platform, int i) {
                Toast.makeText(ShareAppActivity.this, "取消分享！", Toast.LENGTH_SHORT).show();
            }
        });
        // 启动分享GUI
        oks.show(ShareAppActivity.this);
//        shareRedPacket(isToFriends);
    }

    /**
     * 微信分享红包
     */
    private void shareRedPacket(boolean isToFriends){
        NetWorkUtil.setCallback("User/WeChatShareGrant",
                new BeanWeChatShare(UserManager.getInstance(this).getUserInfo().getUserID(),
                        "index","1",isToFriends?"2":"1"),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultWeChatShare result = new Gson().fromJson(respose, ResultWeChatShare.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                if(result.getURLShareCount()>2){
                                    mRedDialog2.show();
                                }else {
                                    mRedDialog.setMoneyNum(result.getMoney());
                                    mRedDialog.show();
                                }
                                Toast.makeText(ShareAppActivity.this, "分享成功！", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ShareAppActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        Toast.makeText(ShareAppActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 微信分享红包
     */
    private void getRedRule(){
        NetWorkUtil.setCallback("User/GetWeChatActivityMoney",
                new BeanNone(),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultGetRedRule result = new Gson().fromJson(respose, ResultGetRedRule.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                String rules = "分享给微信好友可获得"+result.getFriendMoney()+"元现金奖励\n分享到朋友圈可获得"+
                                        result.getCircleMoney()+"元现金奖励\n每天分享的前两次有现金奖励";
                                rule.setText(rules);
                            } else {
                                Toast.makeText(ShareAppActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        Toast.makeText(ShareAppActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if(main_share.getVisibility() == View.VISIBLE){
            hideBottom();
        }else {
            super.onBackPressed();
        }
    }
}
