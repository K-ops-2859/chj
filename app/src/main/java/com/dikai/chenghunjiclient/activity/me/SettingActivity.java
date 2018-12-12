package com.dikai.chenghunjiclient.activity.me;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.BaseApplication;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.newregister.NewForgetPwdActivity;
import com.dikai.chenghunjiclient.bean.BeanBoundWechat;
import com.dikai.chenghunjiclient.bean.BeanUserId;
import com.dikai.chenghunjiclient.entity.NewUserInfo;
import com.dikai.chenghunjiclient.entity.ResultBoundWechat;
import com.dikai.chenghunjiclient.entity.WXTokenBean;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.DownloadUtil;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;
import com.flyco.dialog.widget.base.BaseDialog;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;

import dmax.dialog.SpotsDialog;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView versionInfo;
    private TextView wechatTitle;
    private TextView wechatName;
    private static final int CALL_REQUEST_CODE = 120;
    private Intent intent;
    private ServiceDialog mServiceDialog;
    private NotificationManager notifiManager;
    private NotificationCompat.Builder notifiBuilder;
    private MaterialDialog dialog2;
    private SpotsDialog mDialog;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        EventBus.getDefault().register(this);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        mDialog = new SpotsDialog(this,R.style.DialogCustom);
        versionInfo = (TextView) findViewById(R.id.setting_update_info);
        wechatTitle = (TextView) findViewById(R.id.wechat_title);
        wechatName = (TextView) findViewById(R.id.wechat_name);
        findViewById(R.id.setting_back).setOnClickListener(this);
        findViewById(R.id.wechat_bound).setOnClickListener(this);
        findViewById(R.id.setting_pwd_layout).setOnClickListener(this);
        findViewById(R.id.setting_service_layout).setOnClickListener(this);
        findViewById(R.id.setting_update_layout).setOnClickListener(this);
        findViewById(R.id.setting_logout_btn).setOnClickListener(this);
        findViewById(R.id.setting_fankui_layout).setOnClickListener(this);
        mServiceDialog = new ServiceDialog(this);
        mServiceDialog.widthScale(1);
        mServiceDialog.heightScale(1);
        versionInfo.setText(UserManager.getInstance(this).getVersionInfo());
        if(UserManager.getInstance(this).isHaveNewVersion()){
            versionInfo.setTextColor(ContextCompat.getColor(this,R.color.red_money));
        }else {
            versionInfo.setTextColor(ContextCompat.getColor(this,R.color.gray_text));
        }
        initDialog();
        initNotify();
        initWechatInfo();
    }

    private void initWechatInfo(){
        wechatTitle.setText("绑定微信");
        wechatName.setVisibility(View.GONE);
        if(UserManager.getInstance(this).isLogin() && UserManager.getInstance(this).getNewUserInfo().getWeChatType() == 1){
            wechatTitle.setText("解绑微信");
            wechatName.setVisibility(View.VISIBLE);
            wechatName.setText(UserManager.getInstance(this).getNewUserInfo().getWeChatName());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setting_back:
                onBackPressed();
                break;
            case R.id.wechat_bound:
                if(!UserManager.getInstance(this).isLogin()){
                    startActivity(new Intent(this, SettingActivity.class));
                }else {
                    if( UserManager.getInstance(this).getNewUserInfo().getWeChatType() == 0){
                        wxRequest();//绑定微信
                    }else {
                        unbound();//解绑微信
                    }
                }
                break;
            case R.id.setting_pwd_layout:
                if(UserManager.getInstance(this).isLogin()){
                    startActivity(new Intent(this, NewForgetPwdActivity.class).putExtra("type", 1));
                }
                break;
            case R.id.setting_service_layout:
                startActivity(new Intent(this, ServiceActivity.class));
                break;
            case R.id.setting_fankui_layout:
                startActivity(new Intent(this, FeedBackActivity.class));
                break;
            case R.id.setting_update_layout:
                if(UserManager.getInstance(this).isHaveNewVersion()){
//                    Uri uri = Uri.parse("http://www.chenghunji.com/download/index");
//                    Intent it = new Intent(Intent.ACTION_VIEW, uri);
//                    startActivity(it);
                    dialog2.show();
                }
                break;
            case R.id.setting_logout_btn:
                UserManager.getInstance(this).setLogout();
                SharedPreferences sp = getSharedPreferences("accountCode", MODE_PRIVATE);
                sp.edit().clear().apply();
                finish();
                break;
        }
    }

    /**
     * 初始化Dialog
     */
    private void initDialog(){
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
                            download();
                        }
                    });
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
                .setProgress(100, 1, false);
//        notification = notifiBuilder.build();
//        notification.flags |= Notification.FLAG_ONGOING_EVENT;
        notifiManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    private void download(){
        Toast.makeText(this, "开始下载", Toast.LENGTH_SHORT).show();
        Notification notification = notifiBuilder.build();
        notification.flags |= Notification.FLAG_ONGOING_EVENT;
        notifiManager.notify(1,notification);
        String url = "http://www.chenghunji.com/Download/User/chenghunji.apk";
//        String fileFolder = Environment.getExternalStorageDirectory() + "/ChengHunJi/update";
        final String cachePath = (getExternalFilesDir("upgrade_apk").getAbsolutePath());
        DownloadUtil.setCallback(url, cachePath, new DownloadUtil.CallBackListener() {
            @Override
            public void onFinish(String respose) {
                notifiManager.cancel(1);
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
                notifiManager.notify(1,notification);
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

    public class ServiceDialog extends BaseDialog<ServiceDialog> implements View.OnClickListener {
        private RelativeLayout cancel;
        private RelativeLayout sure;
        public ServiceDialog(Context context) {
            super(context);
        }

        @Override
        public View onCreateView() {
            View view = View.inflate(mContext, R.layout.dialog_call_service, null);
            return view;
        }

        @Override
        public void onViewCreated(View view) {
            super.onViewCreated(view);
            cancel = (RelativeLayout) view.findViewById(R.id.button_cancel);
            sure = (RelativeLayout) view.findViewById(R.id.button_sure);
            cancel.setOnClickListener(this);
            sure.setOnClickListener(this);
        }

        @Override
        public void setUiBeforShow() {

        }

        @Override
        public void onClick(View v) {
            if(v == cancel){
                this.dismiss();
            }else if(v == sure){
                this.dismiss();
                intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "15192055999"));
                request();
            }
        }
    }

    private void request() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请CALL_PHONE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, CALL_REQUEST_CODE);
        } else {
            startActivity(intent);
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
                startActivity(intent);
            } else {
                Toast.makeText(this, "已禁止", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //==========================微信相关==========================

    private void wxRequest() {
        if (!((BaseApplication)getApplication()).getWxApi().isWXAppInstalled()) {
            Toast.makeText(this, "您还未安装微信客户端", Toast.LENGTH_SHORT).show();
            return;
        }
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "diandi_wx_login";
        ((BaseApplication)getApplication()).getWxApi().sendReq(req);
    }

    /**
     * 获取微信token
     */
    private void getWXToken(String code){
        mDialog.show();
        NetWorkUtil.wxLogin("https://api.weixin.qq.com/sns/oauth2/access_token?appid="+
                        Constants.WX_APP_ID+"&secret="+Constants.WX_APP_SECRET+"&code="+code+"&grant_type=authorization_code",
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(String respose) {
                        Log.e("返回值",respose);
                        try {
                            WXTokenBean mWXTokenBean = new Gson().fromJson(respose, WXTokenBean.class);
                            bound(mWXTokenBean.getOpenid(),mWXTokenBean.getAccess_token());
                        } catch (Exception e) {
                            mDialog.dismiss();
                            Log.e("json解析出错",e.toString());
                            Toast.makeText(SettingActivity.this, "微信请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(String e) {
                        mDialog.dismiss();
                        Log.e("网络错误",e);
                    }
                });
    }
    /**
     * 绑定微信
     */
    private void bound(String openid,String token){
        NetWorkUtil.setCallback("HQOAApi/UserBindingWX",
                new BeanBoundWechat(UserManager.getInstance(this).getNewUserInfo().getUserId(),openid,token),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultBoundWechat result = new Gson().fromJson(respose, ResultBoundWechat.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                NewUserInfo userInfo = UserManager.getInstance(SettingActivity.this).getNewUserInfo();
                                userInfo.setWeChatName(result.getWeChatName());
                                userInfo.setWeChatType(result.getWeChatType());
                                initWechatInfo();
                            } else {
                                Toast.makeText(SettingActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Toast.makeText(SettingActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 解绑微信
     */
    private void unbound(){
        mDialog.show();
        NetWorkUtil.setCallback("HQOAApi/UserJieBangWX",
                new BeanUserId(UserManager.getInstance(this).getNewUserInfo().getUserId()),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultBoundWechat result = new Gson().fromJson(respose, ResultBoundWechat.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                NewUserInfo userInfo = UserManager.getInstance(SettingActivity.this).getNewUserInfo();
                                userInfo.setWeChatName(result.getWeChatName());
                                userInfo.setWeChatType(result.getWeChatType());
                                initWechatInfo();
                            } else {
                                Toast.makeText(SettingActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Toast.makeText(SettingActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    /**
     * 事件总线刷新
     * @param bean
     */
    @Subscribe
    public void onEventMainThread(final EventBusBean bean) {
        runOnUiThread(new Runnable() {
            public void run() {
                if(bean.getType() == Constants.WX_LOGIN_TOKEN){
                    getWXToken(bean.getData());
                }else if(bean.getType() == Constants.WX_REGISTER_SUCCESS){
                    initWechatInfo();
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
