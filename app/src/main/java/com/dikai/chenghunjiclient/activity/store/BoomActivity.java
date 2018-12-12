package com.dikai.chenghunjiclient.activity.store;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.register.LoginActivity;
import com.dikai.chenghunjiclient.activity.wedding.WedRuleActivity;
import com.dikai.chenghunjiclient.bean.BeanGetPopcorn;
import com.dikai.chenghunjiclient.bean.BeanNone;
import com.dikai.chenghunjiclient.bean.BeanUserId;
import com.dikai.chenghunjiclient.entity.LuckyBean;
import com.dikai.chenghunjiclient.entity.ResultGetBoomInfo;
import com.dikai.chenghunjiclient.entity.ResultGetLucky;
import com.dikai.chenghunjiclient.entity.ResultGetPrize;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.popcorn.LuckyMonkeyPanelView;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyImageView;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;
import com.flyco.dialog.widget.base.BaseDialog;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import cn.iwgang.countdownview.CountdownView;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.wechat.moments.WechatMoments;

public class BoomActivity extends AppCompatActivity implements View.OnClickListener {


    private static final int CALL_REQUEST_CODE = 120;
    private ServiceDialog3 shareFinish;
    private ServiceDialog2 getPopcorn;

    private CountdownView mCvCountdownView;
    private LuckyMonkeyPanelView luckyPanelView;
    private TextView boomInfo;
    private TextView luckyNum;
    private long drawTime; //抽奖时间
    private static Handler handler = new Handler();
    private boolean canLottery = false;
    private ImageView getPopcornImg;
    private MaterialDialog ruleDialog;
    private List<LuckyBean> prizeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boom);
        EventBus.getDefault().register(this);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        MyImageView bg = (MyImageView) findViewById(R.id.boom_heand_bg);
        MyImageView hint = (MyImageView) findViewById(R.id.boom_hint);
        getPopcornImg = (ImageView) findViewById(R.id.get_popcorn);
        boomInfo = (TextView) findViewById(R.id.boom_time);
        luckyNum = (TextView) findViewById(R.id.get_prize_num);
        mCvCountdownView = (CountdownView)findViewById(R.id.boom_counter);
        mCvCountdownView.start(0); // 毫秒
        mCvCountdownView.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {
                getInfo();
            }
        });
        luckyPanelView = (LuckyMonkeyPanelView) findViewById(R.id.lucky_panel);
        luckyPanelView.setStartListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserManager.getInstance(BoomActivity.this).isLogin()){
                    if(!canLottery){
                        Toast.makeText(BoomActivity.this, "用光了╥﹏╥...您未达到抽奖资格，领四次爆米花可获得一次抽奖机会哦~", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (System.currentTimeMillis() - drawTime < 5000) {
                        return;
                    }
                    //开始抽奖
                    if (!luckyPanelView.isGameRunning()) {
                        drawTime = System.currentTimeMillis();
                        luckyPanelView.startGame();
                        getPrize();
                    }
                }else {
                    startActivity(new Intent(BoomActivity.this, LoginActivity.class));
                }
            }
        });
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.boom_gift).setOnClickListener(this);
        findViewById(R.id.boom_site).setOnClickListener(this);
        findViewById(R.id.boom_record).setOnClickListener(this);
        findViewById(R.id.rule).setOnClickListener(this);
        getPopcornImg.setOnClickListener(this);
        shareFinish = new ServiceDialog3(this);
        shareFinish.widthScale(1);
        shareFinish.heightScale(1);
        getPopcorn = new ServiceDialog2(this);
        getPopcorn.widthScale(1);
        getPopcorn.heightScale(1);
        ruleDialog = new MaterialDialog(this);
        ruleDialog.isTitleShow(false)//
                .btnNum(1)
                .content(getResources().getString(R.string.boom_rule))//
                .btnText("确定")
                .setOnBtnClickL(new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        ruleDialog.dismiss();
                    }
                });
        Glide.with(this).load(R.drawable.boom_head_bg_1).into(bg);
        Glide.with(this).load(R.drawable.boom_hint).into(hint);
        Glide.with(this).load(R.drawable.code_get_boom_2).into(getPopcornImg);
        getPopcornImg.setClickable(false);
        getInfo();
        getLuckyList();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.rule:
                ruleDialog.show();
                break;
            case R.id.get_popcorn:
                if(UserManager.getInstance(BoomActivity.this).isLogin()){
                    request();
                }else {
                    startActivity(new Intent(BoomActivity.this, LoginActivity.class));
                }
                break;
            case R.id.boom_gift:
                if(UserManager.getInstance(BoomActivity.this).isLogin()){
                    startActivity(new Intent(this,MyPrizeActivity.class));
                }else {
                    startActivity(new Intent(BoomActivity.this, LoginActivity.class));
                }
                break;
            case R.id.boom_site:
                String location = UserManager.getInstance(this).getLocation();
                if(location != null && !"".equals(location)){
                    String[] info = location.split(",");
                    String regionID = info[0];
                    startActivity(new Intent(this, WedRuleActivity.class)
                            .putExtra("type",1)
                            .putExtra("title","领取地点")
                            .putExtra("url","http://www.chenghunji.com/Map/Index?regionId="+regionID));
                }else {
                    Toast.makeText(this, "请先在首页设置当前所处地区！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.boom_record:
                if(UserManager.getInstance(BoomActivity.this).isLogin()){
                    startActivity(new Intent(this,BoomRecordActivity.class));
                }else {
                    startActivity(new Intent(BoomActivity.this, LoginActivity.class));
                }
                break;
        }
    }

    private void getLuck(final ResultGetPrize prize) {
        long delay = 0; //延长时间
        long duration = System.currentTimeMillis() - drawTime;
        if (duration < 5000) {
            delay = 5000 - duration;
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (BoomActivity.this.isFinishing()) {
                    return;
                }
                luckyPanelView.tryToStop(prize.getPrizeSubscript());
                luckyPanelView.setGameListener(new LuckyMonkeyPanelView.LuckyMonkeyAnimationListener() {
                    @Override
                    public void onAnimationEnd() {
                        //延长1S弹出抽奖结果
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getInfo();
                                Toast.makeText(BoomActivity.this, "恭喜您获得" +
                                        prizeList.get(prize.getPrizeSubscript()).getPrizeName()+"！可点击-奖品按钮查看领取", Toast.LENGTH_LONG).show();
                            }
                        }, 1000);
                    }
                });
            }
        }, delay);
    }

    private void setData(final ResultGetBoomInfo result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mCvCountdownView.start(result.getReceiveTimeinterval()*1000);
                canLottery = result.getLotteryQualification() > 0;
                luckyNum.setText("当前剩余抽奖次数 "+result.getLotteryQualification()+" 次");
                boomInfo.setText(result.getActivityEndTime());
                setImgEnabled(result.getQualification() == 1);
            }
        });
    }

    private void setImgEnabled(boolean enabled){
        Log.e("enabled",enabled+"");
        getPopcornImg.setImageResource(enabled?R.drawable.code_get_boom_1:R.drawable.code_get_boom_2);
        getPopcornImg.setClickable(enabled);
    }

    /**
     * 获取当前页面信息
     */
    private void getInfo(){
        NetWorkUtil.setCallback("User/GetDrawQualification",
                new BeanUserId(UserManager.getInstance(this).getUserInfo().getUserID()),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultGetBoomInfo result = new Gson().fromJson(respose, ResultGetBoomInfo.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                setData(result);
                            } else {
                                Toast.makeText(BoomActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        Toast.makeText(BoomActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 领取小份爆米花
     */
    private void getPopcorn(String shopID){
        NetWorkUtil.setCallback("User/PeopleReceivePopcorn",
                new BeanGetPopcorn(UserManager.getInstance(this).getUserInfo().getUserID(),shopID),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                getPopcorn.show();
                                getInfo();
                            } else {
                                Toast.makeText(BoomActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        Toast.makeText(BoomActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 领取大份爆米花
     */
    private void shareBigPopcorn(){
        NetWorkUtil.setCallback("User/PeopleReceiveBigPopcorn",
                new BeanUserId(UserManager.getInstance(this).getUserInfo().getUserID()),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                getPopcorn.dismiss();
                                shareFinish.show();
                                getInfo();
                            } else {
                                Toast.makeText(BoomActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        Toast.makeText(BoomActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 获取转盘列表
     */
    private void getLuckyList(){
        NetWorkUtil.setCallback("User/GetAllPrizesList",
                new BeanNone(),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultGetLucky result = new Gson().fromJson(respose, ResultGetLucky.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                prizeList = result.getData();
                                luckyPanelView.setData(result.getData());
                            } else {
                                Toast.makeText(BoomActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        Toast.makeText(BoomActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    /**
     * 抽奖
     */
    private void getPrize(){
        NetWorkUtil.setCallback("User/UserGetPrizes",
                new BeanUserId(UserManager.getInstance(this).getUserInfo().getUserID()),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultGetPrize result = new Gson().fromJson(respose, ResultGetPrize.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                getLuck(result);
                            } else {
                                luckyPanelView.tryToStop(0);
                                luckyPanelView.reset();
                                Toast.makeText(BoomActivity.this, "请重试！", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            luckyPanelView.tryToStop(0);
                            luckyPanelView.reset();
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        luckyPanelView.tryToStop(0);
                        luckyPanelView.reset();
                        Toast.makeText(BoomActivity.this, "请重试！", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     *点击领取爆米花
     */
    private class ServiceDialog2 extends BaseDialog<ServiceDialog2> implements View.OnClickListener {
        private ImageView close;
        private TextView getNow;
        public ServiceDialog2(Context context) {
            super(context);
        }
        @Override
        public View onCreateView() {
            View view = View.inflate(mContext, R.layout.dialog_get_boom_1, null);
            close = (ImageView) view.findViewById(R.id.close);
            getNow = (TextView) view.findViewById(R.id.share);
            close.setOnClickListener(this);
            getNow.setOnClickListener(this);
            return view;
        }
        @Override
        public void onViewCreated(View view) {
            super.onViewCreated(view);
        }
        @Override
        public void setUiBeforShow() {}
        @Override
        public void onClick(View v) {
            if(v == close){
                this.dismiss();
            }else if(v == getNow){
                showShare();
            }
        }
    }

    /**
     * 分享领取大桶爆米花
     */
    private class ServiceDialog3 extends BaseDialog<ServiceDialog3> implements View.OnClickListener {
        private ImageView close;
        public ServiceDialog3(Context context) {
            super(context);
        }
        @Override
        public View onCreateView() {
            View view = View.inflate(mContext, R.layout.dialog_get_boom_2, null);
            close = (ImageView) view.findViewById(R.id.close);
            close.setOnClickListener(this);
            return view;
        }
        @Override
        public void onViewCreated(View view) {
            super.onViewCreated(view);
        }
        @Override
        public void setUiBeforShow() {}

        @Override
        public void onClick(View v) {
            if(v == close){
                this.dismiss();
            }
        }
    }

    private void request() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            //申请CALL_PHONE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CALL_REQUEST_CODE);
        } else {
            startActivity(new Intent(this,ScanActivity.class));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode, grantResults);
    }

    private void doNext(int requestCode, int[] grantResults) {
        if (requestCode == CALL_REQUEST_CODE) {
            if (grantResults != null && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivity(new Intent(this,ScanActivity.class));
            } else {
                Toast.makeText(this, "已禁止", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("今天准备干嘛？没事来桶爆米花吧！成婚纪免费送爆米花！");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://www.chenghunji.com/map/baomihuafenxiang");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("在这和你相遇，2018天天有惊喜！最高可享100元现金福利哦！还有更多福利，尽在成婚纪！");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("");//确保SDcard下面存在此张图片
        oks.setImageUrl("http://www.chenghunji.com/img/index/B_LOGO_01.png");
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://www.chenghunji.com/map/baomihuafenxiang");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        // oks.setComment("成婚纪——婚礼原来如此简单");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("成婚纪");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://www.chenghunji.com/");
        oks.setPlatform(WechatMoments.NAME);//微信朋友圈
//        oks.addHiddenPlatform(SinaWeibo.NAME);
//        oks.addHiddenPlatform(QZone.NAME);
//        oks.addHiddenPlatform(QQ.NAME);
//        oks.addHiddenPlatform(Wechat.NAME);
        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                shareBigPopcorn();
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Toast.makeText(BoomActivity.this, "分享失败！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(Platform platform, int i) {
                Toast.makeText(BoomActivity.this, "取消分享！", Toast.LENGTH_SHORT).show();
            }
        });
        // 启动分享GUI
        oks.show(BoomActivity.this);
//        shareRedPacket(isToFriends);
    }

    /**
     * 事件总线刷新
     * @param bean
     */
    @Subscribe
    public void onEventMainThread(final EventBusBean bean) {
        runOnUiThread(new Runnable() {
            public void run() {
                if(bean.getType() == Constants.SCAN_RESULT){
                    try {
                        String result = bean.getData();
                        String shopid = result.substring(result.lastIndexOf("=")+1);
                        Log.e("shopid = ",shopid);
                        getPopcorn(shopid);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}
