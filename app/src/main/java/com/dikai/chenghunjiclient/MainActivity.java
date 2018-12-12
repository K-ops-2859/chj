package com.dikai.chenghunjiclient;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.activity.invitation.ComInviteMainActivity;
import com.dikai.chenghunjiclient.activity.invitation.VipInviteMainActivity;
import com.dikai.chenghunjiclient.activity.me.ShareAppActivity;
import com.dikai.chenghunjiclient.activity.newregister.NewLoginActivity;
import com.dikai.chenghunjiclient.activity.register.LoginActivity;
import com.dikai.chenghunjiclient.activity.store.BoomActivity;
import com.dikai.chenghunjiclient.activity.store.HotelADActivity;
import com.dikai.chenghunjiclient.activity.store.WeddingStoreActivity;
import com.dikai.chenghunjiclient.activity.wedding.FreeWedActivity;
import com.dikai.chenghunjiclient.activity.wedding.InviteWedActivity;
import com.dikai.chenghunjiclient.activity.wedding.MakeProjectActivity;
import com.dikai.chenghunjiclient.activity.wedding.WedRuleActivity;
import com.dikai.chenghunjiclient.activity.wedding.WeddingAssureActivity;
import com.dikai.chenghunjiclient.bean.BeanType;
import com.dikai.chenghunjiclient.bean.BeanUserId;
import com.dikai.chenghunjiclient.citypicker.SelectCityActivity;
import com.dikai.chenghunjiclient.entity.ResultGetHomeAd;
import com.dikai.chenghunjiclient.entity.ResultGetHomeRed;
import com.dikai.chenghunjiclient.entity.ResultGetInvitationProfit;
import com.dikai.chenghunjiclient.fragment.ad.NewADFragment;
import com.dikai.chenghunjiclient.fragment.discover.DiscoverFragment;
import com.dikai.chenghunjiclient.fragment.keyuan.KeYuanFragment;
import com.dikai.chenghunjiclient.fragment.me.CenterFragment;
import com.dikai.chenghunjiclient.fragment.me.NewMeFragment;
import com.dikai.chenghunjiclient.fragment.store.NewHomeFragment;
import com.dikai.chenghunjiclient.fragment.wedding.NewWeddingFragment;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.DownloadUtil;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserDBManager;
import com.dikai.chenghunjiclient.util.UserManager;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;
import com.flyco.dialog.widget.base.BaseDialog;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.lang.reflect.Field;

import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {
    private BottomNavigationBar mNavigationBar;
    //    private BadgeItem numberBadgeItem;
    private NewHomeFragment mStoreFragment;
    private KeYuanFragment mKeYuanFragment;
    private DiscoverFragment mDisFragment;
    private NewADFragment mAdFragment;
    private CenterFragment mMeFragment;
    private Fragment[] mFragments;
    private int nowPosition = 0;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private MaterialDialog dialog;
    private MaterialDialog dialog2;
    private NotificationCompat.Builder notifiBuilder;
    private ServiceDialog mRedDialog;
    private NotificationManager notifiManager;
    private SpotsDialog mDialog;
    //    private boolean canGetNewsRed = true;
    //    private boolean isSetView = false;
    //    private boolean mFull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
        setJump();
    }

    private void setJump() {
         try {
            String code = getIntent().getStringExtra("code");
            if(getIntent().getIntExtra("type",1) == 0){
                if(code != null && !"".equals(code)){
                    String[] info = code.split(",");
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("shareInfo",new ResultGetHomeRed(info[1],info[2],info[3],info[4]));
                    startActivity(new Intent(MainActivity.this, WedRuleActivity.class)
                            .putExtra("url",info[0])
                            .putExtra("share",1)
                            .putExtras(bundle));
                }
            }else if(code != null && !"".equals(code)){
                if("YQBHL".equals(code)){
                    if(UserManager.getInstance(MainActivity.this).isLogin()){
                        getProfit(UserManager.getInstance(this).getNewUserInfo().getUserId());
                    }
                }else if("FreeBHL".equals(code)){
                    startActivity(new Intent(this, FreeWedActivity.class));
                }else if("ShareApp".equals(code)){
                    startActivity(new Intent(this, ShareAppActivity.class));
                }else if("FreeBMH".equals(code)){
                    if(!UserManager.getInstance(MainActivity.this).isLogin()){
                        startActivity(new Intent(MainActivity.this, NewLoginActivity.class));
                    }else{
                        startActivity(new Intent(this, BoomActivity.class));
                    }
                }else if("HLDB".equals(code)){
                    startActivity(new Intent(this, WeddingAssureActivity.class));
                }else if("ChuFA".equals(code)){
                    startActivity(new Intent(this, MakeProjectActivity.class));
                }else if("JDHD".equals(code)){
                    startActivity(new Intent(this, HotelADActivity.class));
                }else if("HunLiFH".equals(code)){
                    if(!UserManager.getInstance(MainActivity.this).isLogin()){
                        startActivity(new Intent(MainActivity.this, NewLoginActivity.class));
                    }else{
                        startActivity(new Intent(this, WeddingStoreActivity.class));
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void init() {
        mDialog = new SpotsDialog(this,R.style.DialogCustom);
        mNavigationBar = (BottomNavigationBar) findViewById(R.id.main_bottom_bar);
        mNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        mNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        //        numberBadgeItem = new BadgeItem().setBorderWidth(3).setText("0");
        //        numberBadgeItem.hide();
        mNavigationBar
                .setActiveColor(R.color.pink_main)
                .setInActiveColor(R.color.black)
                .addItem(new BottomNavigationItem(R.mipmap.ic_app_new_home1, "首页")
                        .setInactiveIcon(ContextCompat.getDrawable(this,R.mipmap.ic_app_new_home0)))
                .addItem(new BottomNavigationItem(R.mipmap.ic_app_new_dynamic1, "新人说")
                        .setInactiveIcon(ContextCompat.getDrawable(this,R.mipmap.ic_app_new_dynamic0)))
//                        .setBadgeItem(numberBadgeItem))
                .addItem(new BottomNavigationItem(R.mipmap.ic_app_main_keyuan2, "客源")
                        .setInactiveIcon(ContextCompat.getDrawable(this,R.mipmap.ic_app_main_keyuan)))
                .addItem(new BottomNavigationItem(R.mipmap.ic_app_new_ad1, "特惠")
                        .setInactiveIcon(ContextCompat.getDrawable(this,R.mipmap.ic_app_new_ad0)))
                .addItem(new BottomNavigationItem(R.mipmap.ic_app_new_me1, "我的")
                        .setInactiveIcon(ContextCompat.getDrawable(this,R.mipmap.ic_app_new_me0)))
                .setFirstSelectedPosition(0)
                .initialise();
        setBottomNavigationItem(mNavigationBar,8,22,12);
        mStoreFragment = new NewHomeFragment();
        mDisFragment = new DiscoverFragment();
        mKeYuanFragment = new KeYuanFragment();
        mMeFragment = new CenterFragment();
        mAdFragment = new NewADFragment();

        mFragments = new Fragment[5];
        mFragments[0] = mStoreFragment;
        mFragments[1] = mDisFragment;
        mFragments[2] = mKeYuanFragment;
        mFragments[3] = mAdFragment;
        mFragments[4] = mMeFragment;

        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        for (Fragment fragment: mFragments) {
            transaction.add(R.id.main_frame,fragment);
            transaction.hide(fragment);
        }

        transaction.show(mFragments[0]);
        transaction.commit();
        mNavigationBar.setTabSelectedListener(this);
        initDialog();
        initNotify();
        if(UserManager.getInstance(this).isHaveNewVersion()){
            dialog2.show();
        }

//        checkRedPacket();
        getAd();
    }

    @Override
    public void onTabSelected(int position) {
        nowPosition = position;
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();

        for (Fragment fragment: mFragments) {
            transaction.hide(fragment);
        }

        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = mFragments[0];
                break;
            case 1:
                fragment = mFragments[1];
                break;
            case 2:
                fragment = mFragments[2];
                break;
            case 3:
                fragment = mFragments[3];
                break;
            case 4:
                fragment = mFragments[4];
                break;
        }
        if(fragment!=null){
            transaction.show(fragment);
        }
        transaction.commit();
        if(position == 4){
            ImmersionBar.with(this)
                    .statusBarView(R.id.top_view)
                    .statusBarColor(R.color.black_gold)
                    .statusBarDarkFont(false)
                    .init();

        }else {
            ImmersionBar.with(this)
                    .statusBarView(R.id.top_view)
                    .statusBarColor(R.color.white)
                    .statusBarDarkFont(true, 0.2f)
                    .init();
        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public void onBackPressed() {
//        if (mFull) {
//            EventBus.getDefault().post(new EventBusBean(Constants.VIDEO_FULL));
//        }else
        if(nowPosition != 0){
            mNavigationBar.selectTab(0);
            nowPosition = 0;
            manager = getSupportFragmentManager();
            transaction = manager.beginTransaction();
            for (Fragment fragment: mFragments) {
                transaction.hide(fragment);
            }
            transaction.show(mFragments[0]);
            transaction.commit();
        }else {
            super.onBackPressed();
        }
    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        //如果旋转了就全屏
//        if (newConfig.orientation != ActivityInfo.SCREEN_ORIENTATION_USER) {
//            mFull = false;
//        } else {
//            mFull = true;
//        }
//        Log.e("mFull",""+mFull);
//    }

    //===================================================================

    /**
     * 初始化Dialog
     */
    private void initDialog(){
        mRedDialog = new ServiceDialog(this);
        mRedDialog.widthScale(1);
        mRedDialog.heightScale(1);
        if(dialog == null){
            dialog = new MaterialDialog(this);
            dialog.isTitleShow(false)//
                    .btnNum(2)
                    .content("登录后才能进行操作，请先登陆")//
                    .btnText("取消", "确定")
                    .setOnBtnClickL(new OnBtnClickL() {
                        @Override
                        public void onBtnClick() {
                            dialog.dismiss();
                        }
                    }, new OnBtnClickL() {
                        @Override
                        public void onBtnClick() {
                            dialog.dismiss();
                            startActivity(new Intent(MainActivity.this, NewLoginActivity.class));
                        }
                    });
        }

        if(dialog2 == null){
            dialog2 = new MaterialDialog(this);
            dialog2.isTitleShow(false)//
                    .btnNum(2)
                    .content("发现新版本，是否进行下载更新？")//
                    .btnText("稍后再说", "立即更新")
                    .setOnBtnClickL(new OnBtnClickL() {
                        @Override
                        public void onBtnClick() {
                            dialog2.dismiss();
                        }
                    }, new OnBtnClickL() {
                        @Override
                        public void onBtnClick() {
                            dialog2.dismiss();
//                            "http://www.chenghunji.com/Download/User/chenghunji.apk"
//                            final Uri uri = Uri.parse("http://www.chenghunji.com/download/index");
//                            final Intent it = new Intent(Intent.ACTION_VIEW, uri);
//                            startActivity(it);
                            download();
                        }
                    });
        }
    }

    private void setRedDialog(ResultGetHomeRed homeRed){
        mRedDialog.setHomeRed(homeRed);
        if(homeRed.getPopup() == 1){
            mRedDialog.show();
            mRedDialog.setUiBeforShow();
        }
    }

    /**
     * 初始化更新通知
     */
    private void initNotify() {
        notifiBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("成婚纪更新")
                .setContentText("下载进度")
                .setTicker("下载更新")
                .setPriority(Notification.PRIORITY_HIGH)
                .setProgress(100, 0, false);
//        notification = notifiBuilder.build();
//        notification.flags |= Notification.FLAG_ONGOING_EVENT;
        notifiManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    private void download(){
        Toast.makeText(this, "开始下载", Toast.LENGTH_SHORT).show();
        Notification notification = notifiBuilder.build();
        notification.flags |= Notification.FLAG_ONGOING_EVENT;
        notifiManager.notify(0,notification);
        String url = "http://www.chenghunji.com/Download/User/chenghunji.apk";
//        String fileFolder = Environment.getExternalStorageDirectory() + "/ChengHunJi/update";
        final String cachePath = (getExternalFilesDir("upgrade_apk").getAbsolutePath());
        DownloadUtil.setCallback(url, cachePath, new DownloadUtil.CallBackListener() {
            @Override
            public void onFinish(String respose) {
                notifiManager.cancel(0);
                installApk(new File(cachePath + "/chenghunji.apk"));
//                Log.e("mingcheng:",respose);
//                Toast.makeText(MainActivity.this, "下载完成！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProgress(int progress) {
                notifiBuilder.setContentText("下载进度" + progress + "%")
                        .setProgress(100, progress, false);
                Notification notification = notifiBuilder.build();
                notification.flags |= Notification.FLAG_ONGOING_EVENT;
                notifiManager.notify(0,notification);
            }

            @Override
            public void onError(String e) {
                Log.e("下载失败",e);
            }
        });
    }

    /**
     * 下载完成,提示用户安装
     */
    private void installApk(File apkFile) {
        Intent installApkIntent = new Intent();
        installApkIntent.setAction(Intent.ACTION_VIEW);
        installApkIntent.addCategory(Intent.CATEGORY_DEFAULT);
        installApkIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            installApkIntent.setDataAndType(FileProvider.getUriForFile(getApplicationContext(),
                    "com.dikai.chenghunjiclient.file_provider", apkFile), "application/vnd.android.package-archive");
            installApkIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            installApkIntent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }

        if (getPackageManager().queryIntentActivities(installApkIntent, 0).size() > 0) {
            startActivity(installApkIntent);
        }
    }

    private void update(){
        UserManager.getInstance(this).autoLogin(new UserManager.OnLoginListener() {
            @Override
            public void onFinish() {
                EventBus.getDefault().post(Constants.USER_INFO_CHANGE);
            }

            @Override
            public void onError(String e) {

            }
        });
    }

    /**
     * 是否领取新人红包
     */
    private void checkRedPacket(){
        String userID = UserManager.getInstance(this).isLogin()?UserManager.getInstance(this).getNewUserInfo().getUserId():"0";
        NetWorkUtil.setCallback("User/GetUserPopup",
                new BeanUserId(userID),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultGetHomeRed result = new Gson().fromJson(respose, ResultGetHomeRed.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                setRedDialog(result);
                            } else {
                                Toast.makeText(MainActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        Toast.makeText(MainActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 获取ad
     */
    private void getAd() {
        NetWorkUtil.setCallback("HQOAApi/SoftwarePageStarts",
                new BeanType(1),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultGetHomeAd result = new Gson().fromJson(respose, ResultGetHomeAd.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                UserDBManager.getInstance(MainActivity.this).updateHomeAd(result.getHeadImg());
                                UserDBManager.getInstance(MainActivity.this).updateHomeAdType(result.getType());
                                if(result.getType() == 0){
                                    String codes = result.getJumpUrl()+","+result.getShareUrl()+","+
                                            result.getShareTitle()+","+result.getShareDescribe()+","+result.getShareImg();
                                    UserDBManager.getInstance(MainActivity.this).updateHomeAdCode(codes);
                                }else {
                                    UserDBManager.getInstance(MainActivity.this).updateHomeAdCode(result.getJumpUrl());
                                }
                            } else {
                                // Toast.makeText(WelcomeActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError(String e) {
                    }
                });
    }

    /**
     * 获取邀请结婚收益
     */
    public void getProfit(String userID) {
        mDialog.show();
        NetWorkUtil.setCallback("HQOAApi/GetInvitationProfit",
                new BeanUserId(userID), new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultGetInvitationProfit result = new Gson().fromJson(respose, ResultGetInvitationProfit.class);
                            if (result.getMessage().getCode().equals("200")) {
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("profit",result);
                                if (result.getRefereeStatus() == 0){//普通用户
                                    startActivity(new Intent(MainActivity.this, ComInviteMainActivity.class).putExtras(bundle));
                                }else {//Vip
                                    startActivity(new Intent(MainActivity.this, VipInviteMainActivity.class).putExtras(bundle));
                                }
                            }else {
                                Toast.makeText(MainActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            Log.e("",e.toString());
                        }
                    }
                    @Override
                    public void onError(String e) {
                        mDialog.dismiss();
                        Log.e("网络请求错误",e);
                    }
                });
    }

    /**
     * 首次登录红包
     */
    private class ServiceDialog extends BaseDialog<ServiceDialog> implements View.OnClickListener {

        private ImageView close;
        private ImageView packedImg;
        private ResultGetHomeRed mHomeRed;

        void setHomeRed(ResultGetHomeRed homeRed) {
            mHomeRed = homeRed;
        }

        public ServiceDialog(Context context) {
            super(context);
        }

        @Override
        public View onCreateView() {
            View view = View.inflate(mContext, R.layout.dialog_new_home_layout, null);
            close = (ImageView) view.findViewById(R.id.close);
            packedImg = (ImageView) view.findViewById(R.id.img);
            close.setOnClickListener(this);
            packedImg.setOnClickListener(this);
            return view;
        }

        @Override
        public void onViewCreated(View view) {
            super.onViewCreated(view);
            Glide.with(getContext()).load(R.drawable.red_packet).into(packedImg);
        }

        @Override
        public void setUiBeforShow() {
            Glide.with(getContext()).load(mHomeRed.getImgurl()).into(packedImg);
        }

        @Override
        public void onClick(View v) {
            if(v == close){
                this.dismiss();
            }else if(v == packedImg){
                this.dismiss();
                if(UserManager.getInstance(mContext).isLogin()){
                    if(mHomeRed.getDetailsurl() != null && "".equals(mHomeRed.getDetailsurl())){
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("shareInfo",mHomeRed);
                        startActivity(new Intent(MainActivity.this, WedRuleActivity.class)
                                .putExtra("url",mHomeRed.getDetailsurl())
                                .putExtra("share",1)
                                .putExtras(bundle));
                    }
                }else {
                    startActivity(new Intent(MainActivity.this, NewLoginActivity.class));
                }
            }
        }
    }

    /**
     * 事件总线刷新
     * @param bean
     */
    @Subscribe
    public void onEventMainThread(final EventBusBean bean) {
        runOnUiThread(new Runnable() {
            public void run() {
                if(bean.getType() == Constants.CHECK_LOGIN){
                    dialog.show();
                }else if(bean.getType() == Constants.USER_RELOGIN){
                    update();
                }else if(bean.getType() == Constants.USER_INFO_CHANGE){
//                    isSetView = true;
//                    if(UserManager.getInstance(MainActivity.this).isLogin() && !mRedDialog.isShowing()){
//                        checkRedPacket();
//                    }
                }else if(bean.getType() == Constants.SET_USER_LOCATION){
                    Log.e("执行至此：","main==");
                    startActivity(new Intent(MainActivity.this, SelectCityActivity.class).putExtra("type",Constants.HOME_SELECT_CITY));
                }else if(bean.getType() == Constants.GO_TO_ZIXUN){
                    mNavigationBar.selectTab(1);
                }else if(bean.getType() == Constants.KEYUAN_HOME){
                    mNavigationBar.selectTab(2);
                }

//                else if(bean.getType() == Constants.SHOW_RED_PACKET){
//                    if(UserManager.getInstance(MainActivity.this).isLogin()){
//                        if(canGetNewsRed){
//                            mRedDialog.setFinishShare(false);
//                            mRedDialog.show();
//                        }else {
//                            Toast.makeText(MainActivity.this, "您已经领取过新人红包了哦！", Toast.LENGTH_SHORT).show();
//                        }
//                    }else {
//                        mRedDialog.setFinishShare(false);
//                        mRedDialog.setUiBeforShow();
//                        mRedDialog.show();
//                    }
//                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        //友盟统计
        MobclickAgent.onResume(this);
    }
    @Override
    public void onPause() {
        super.onPause();
        //友盟统计
        MobclickAgent.onPause(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }

    private void setBottomNavigationItem(BottomNavigationBar bottomNavigationBar, int space, int imgLen, int textSize){
        Class barClass = bottomNavigationBar.getClass();
        Field[] fields = barClass.getDeclaredFields();
        for(int i = 0; i < fields.length; i++){
            Field field = fields[i];
            field.setAccessible(true);
            if(field.getName().equals("mTabContainer")){
                try{
                    //反射得到 mTabContainer
                    LinearLayout mTabContainer = (LinearLayout) field.get(bottomNavigationBar);
                    for(int j = 0; j < mTabContainer.getChildCount(); j++){
                        //获取到容器内的各个Tab
                        View view = mTabContainer.getChildAt(j);
                        //获取到Tab内的各个显示控件
                        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip2px(56));
                        FrameLayout container = (FrameLayout) view.findViewById(R.id.fixed_bottom_navigation_container);
                        container.setLayoutParams(params);
                        container.setPadding(dip2px(12), dip2px(0), dip2px(12), dip2px(0));

                        //获取到Tab内的文字控件
                        TextView labelView = (TextView) view.findViewById(com.ashokvarma.bottomnavigation.R.id.fixed_bottom_navigation_title);
                        //计算文字的高度DP值并设置，setTextSize为设置文字正方形的对角线长度，所以：文字高度（总内容高度减去间距和图片高度）*根号2即为对角线长度，此处用DP值，设置该值即可。
                        labelView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
                        labelView.setIncludeFontPadding(false);
                        labelView.setPadding(0,0,0,dip2px(20-textSize - space/2));

                        //获取到Tab内的图像控件
                        ImageView iconView = (ImageView) view.findViewById(com.ashokvarma.bottomnavigation.R.id.fixed_bottom_navigation_icon);
                        //设置图片参数，其中，MethodUtils.dip2px()：换算dp值
                        params = new FrameLayout.LayoutParams(dip2px(imgLen), dip2px(imgLen));
                        params.setMargins(0,0,0,space/2);
                        params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
                        iconView.setLayoutParams(params);
                    }
                } catch (IllegalAccessException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public int dip2px(float dpValue) {
        final float scale = getApplication().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}

//====================18/5/31弃用===============================

//    /**
//     * 首次登录红包
//     */
//    private class ServiceDialog extends BaseDialog<ServiceDialog> implements View.OnClickListener {
//
//        private ImageView close;
//        private ImageView packedImg;
//        private MyImageView getNow;
//        private View grayView;
//        private TextView text1;
//        private TextView text2;
//        private LinearLayout share;
//        private LinearLayout friends;
//        private LinearLayout moments;
//        private TranslateAnimation mShowAction;
//        private TranslateAnimation mHiddenAction;
//        private boolean finishShare = false;
//
//        public void setFinishShare(boolean finishShare) {
//            this.finishShare = finishShare;
//        }
//
//        public ServiceDialog(Context context) {
//            super(context);
//        }
//
//        @Override
//        public View onCreateView() {
//            View view = View.inflate(mContext, R.layout.dialog_login_red_packet, null);
//            close = (ImageView) view.findViewById(R.id.close);
//            packedImg = (ImageView) view.findViewById(R.id.red_packed_img);
//            getNow = (MyImageView) view.findViewById(R.id.get_now);
//            text1 = (TextView) view.findViewById(R.id.text1);
//            text2 = (TextView) view.findViewById(R.id.text2);
//            grayView = view.findViewById(R.id.gray_view);
//            share = (LinearLayout) view.findViewById(R.id.share);
//            friends = (LinearLayout) view.findViewById(R.id.friends);
//            moments = (LinearLayout) view.findViewById(R.id.moments);
//
//            close.setOnClickListener(this);
//            getNow.setOnClickListener(this);
//            grayView.setOnClickListener(this);
//            friends.setOnClickListener(this);
//            moments.setOnClickListener(this);
//
//            mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
//                    Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f,
//                    Animation.RELATIVE_TO_SELF, 0.0f);
//            mShowAction.setDuration(260);
//
//            mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
//                    0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
//                    Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
//                    1.0f);
//            mHiddenAction.setDuration(260);
//            return view;
//        }
//
//        @Override
//        public void onViewCreated(View view) {
//            super.onViewCreated(view);
//            Glide.with(getContext()).load(R.drawable.red_packet).into(packedImg);
//        }
//
//        @Override
//        public void setUiBeforShow() {
//            if(finishShare){
//                grayView.setVisibility(View.GONE);
//                share.setVisibility(View.GONE);
//                text1.setVisibility(View.GONE);
//                text2.setVisibility(View.GONE);
//                Glide.with(getContext()).load(R.drawable.ic_app_red_packed2).into(packedImg);
//                getNow.setVisibility(View.GONE);
//            }else {
//                Glide.with(getContext()).load(R.drawable.red_packet).into(packedImg);
//            }
//        }
//
//        @Override
//        public void onClick(View v) {
//            if(v == close){
//                this.dismiss();
//            }else if(v == getNow){
//                if(UserManager.getInstance(getContext()).isLogin()){
//                    showBottom();
//                }else {
//                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
//                }
//            }
//            else if(v == grayView){
//                hideBottom();
//            }
//            else if(v == friends){
//                hideBottom();
//                showShare(true);
//            }else if(v == moments){
//                hideBottom();
//                showShare(false);
//            }
//        }
//
//        private void showBottom(){
//            grayView.setVisibility(View.VISIBLE);
//            text1.setVisibility(View.VISIBLE);
//            text2.setVisibility(View.VISIBLE);
//            share.clearAnimation();
//            share.setVisibility(View.VISIBLE);
//            share.startAnimation(mShowAction);
//        }
//
//        private void hideBottom(){
//            grayView.setVisibility(View.GONE);
//            text1.setVisibility(View.GONE);
//            text2.setVisibility(View.GONE);
//            share.clearAnimation();
//            share.startAnimation(mHiddenAction);
//            share.setVisibility(View.GONE);
//        }
//    }


//    private void showShare(boolean isToFriends) {
//        OnekeyShare oks = new OnekeyShare();
//        //关闭sso授权
//        oks.disableSSOWhenAuthorize();
//        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
//        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
//        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
//        oks.setTitle("【邀好友】成婚纪送现金福利！点开即得！");
//        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
//        oks.setTitleUrl("http://www.chenghunji.com/Redbag/index");
//        // text是分享文本，所有平台都需要这个字段
//        oks.setText("在这和你相遇，2018天天有惊喜！最高可享100元现金福利哦！还有更多福利，尽在成婚纪！");
//        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath("");//确保SDcard下面存在此张图片
//        oks.setImageUrl("http://www.chenghunji.com/Download/User/wechat_red_packet.png");
//        // url仅在微信（包括好友和朋友圈）中使用
//        oks.setUrl("http://www.chenghunji.com/Redbag/index");
//        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        // oks.setComment("成婚纪——婚礼原来如此简单");
//        // site是分享此内容的网站名称，仅在QQ空间使用
//        oks.setSite("成婚纪");
//        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//        oks.setSiteUrl("http://www.chenghunji.com/");
//        if(isToFriends){
//            oks.setPlatform(Wechat.NAME);//微信好友
//        }else {
//            oks.setPlatform(WechatMoments.NAME);//微信朋友圈
//        }
//
//        oks.setCallback(new PlatformActionListener() {
//            @Override
//            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
//                mRedDialog.dismiss();
//                mRedDialog.setFinishShare(true);
//                mRedDialog.setUiBeforShow();
//                mRedDialog.show();
//                getRedPacket();
//                Toast.makeText(MainActivity.this, "分享成功！", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onError(Platform platform, int i, Throwable throwable) {
//                Toast.makeText(MainActivity.this, "分享失败！", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancel(Platform platform, int i) {
//                Toast.makeText(MainActivity.this, "取消分享！", Toast.LENGTH_SHORT).show();
//            }
//        });
//        // 启动分享GUI
//        oks.show(MainActivity.this);
//    }

//    /**
//     * 领取新人红包
//     */
//    private void getRedPacket(){
//        NetWorkUtil.setCallback("User/GetNewRedEnvelopes",
//                new BeanUserId(UserManager.getInstance(this).getUserInfo().getUserID()),
//                new NetWorkUtil.CallBackListener() {
//                    @Override
//                    public void onFinish(final String respose) {
//                        Log.e("返回值",respose);
//                        try {
//                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
//                            if ("200".equals(result.getMessage().getCode())) {
//                                Log.e("新人红包","领取成功！");
//                            } else {
//                                Toast.makeText(MainActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (Exception e) {
//                            Log.e("json解析出错",e.toString());
//                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onError(final String e) {
//                        Toast.makeText(MainActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }