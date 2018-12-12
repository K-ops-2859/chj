package com.dikai.chenghunjiclient;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.bean.BeanNone;
import com.dikai.chenghunjiclient.bean.BeanType;
import com.dikai.chenghunjiclient.citypicker.DBManager;
import com.dikai.chenghunjiclient.entity.ResultGetHomeAd;
import com.dikai.chenghunjiclient.entity.ResultNewIdentity;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserDBManager;
import com.dikai.chenghunjiclient.util.UserManager;
import com.google.gson.Gson;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Timer timer;
    private int tempNum;
    private boolean isReady = false;
    private int READ_REQUEST_CODE = 22;
    private ImageView img;
    private String code;
    private int type;
    private TextView skip;
    private AMapLocationListener mLocationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        img = (ImageView) findViewById(R.id.img);
        skip = (TextView) findViewById(R.id.skip);
        img.setOnClickListener(this);
        if(!"".equals(UserDBManager.getInstance(WelcomeActivity.this).getHomeAd())){
            Glide.with(this).load(UserDBManager.getInstance(WelcomeActivity.this).getHomeAd()).into(img);
        }else {
            Glide.with(this).load(R.drawable.ic_welcome).into(img);
        }
        tempNum = 0;
        timer = new Timer(true);
        timer.schedule(new task(), 0, 1000); //延时1000ms后执行，1000ms执行一次
        code = UserDBManager.getInstance(WelcomeActivity.this).getHomeAdCode();
        type = UserDBManager.getInstance(WelcomeActivity.this).getHomeAdType();
        skip.setOnClickListener(this);
        request();
    }

    @Override
    public void onClick(View v) {
        if(v == img && isReady){
            if (timer != null){
                timer.cancel();
            }
            startActivity(new Intent(WelcomeActivity.this,MainActivity.class)
                    .putExtra("code",code)
                    .putExtra("type",type));
            finish();
        }else if(v == skip && isReady){
            if (timer != null){
                timer.cancel();
            }
            if(UserDBManager.getInstance(WelcomeActivity.this).isFirstUse()){
                startActivity(new Intent(WelcomeActivity.this,LeadActivity.class));
            }else {
                startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
            }
            finish();
        }
    }

    private void autoLogin(){
        if(!UserManager.getInstance(this).isLogin()){
            UserManager.getInstance(this).checkNewUserInfo();
        }
        getVersion();
    }

    private void getVersion(){
        UserManager.getInstance(this).getVersion(new UserManager.OnGetVersionListener() {
            @Override
            public void onFinish() {
                next();
            }

            @Override
            public void onError(String e) {
                next();
            }
        });
    }

    private void next(){
        UserManager.getInstance(this).getProfession(0,new UserManager.OnGetIdentListener() {
            @Override
            public void onFinish(ResultNewIdentity result) {
                UserManager.getInstance(WelcomeActivity.this).setProfessionMap(result.getData());
                startLocation();
            }

            @Override
            public void onError(String e) {
                startLocation();
                //Toast.makeText(WelcomeActivity.this, "获取身份失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private final Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    tempNum++;
                    if((4 - tempNum) >= 0){
                        skip.setText("跳过 "+(4 - tempNum));
                    }
                    if(tempNum >= 4 && isReady){
                        if (timer != null){
                            timer.cancel();
                        }
                        if(UserDBManager.getInstance(WelcomeActivity.this).isFirstUse()){
                            startActivity(new Intent(WelcomeActivity.this,LeadActivity.class));
                        }else {
                            startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                        }
                        finish();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public class task extends TimerTask {
        @Override
        public void run() {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }

    }

    @Override
    protected void onDestroy() {
        if (timer != null){
            timer.cancel();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        //
    }

    //申请读取权限
    //ACCESS_COARSE_LOCATION ，ACCESS_FINE_LOCATION ，READ_SETTINGS，WRITE_EXTERNAL_STORAGE
    private void request() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE}, READ_REQUEST_CODE);
        } else {
            autoLogin();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode, grantResults);
    }

    private void doNext(int requestCode, int[] grantResults) {
        if (requestCode == READ_REQUEST_CODE) {
            if (grantResults != null && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                autoLogin();
            } else {
                Toast.makeText(this, "已禁止", Toast.LENGTH_SHORT).show();
                autoLogin();
            }
        }
    }

    public void startLocation(){
        mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                if (amapLocation != null) {
                    Log.e("定位执行","----------->" + amapLocation.getErrorCode());
                    if (amapLocation.getErrorCode() == 0) {
                        //定位成功回调信息，设置相关消息
//                        amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
//                        amapLocation.getAccuracy();//获取精度信息
//                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                        Date date = new Date(amapLocation.getTime());
//                        df.format(date);//定位时间
//                        amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
//                        amapLocation.getCountry();//国家信息
//                        amapLocation.getProvince();//省信息
//                        amapLocation.getCity();//城市信息
//                        amapLocation.getStreet();//街道信息
//                        amapLocation.getStreetNum();//街道门牌号信息
//                        amapLocation.getCityCode();//城市编码
//                        amapLocation.getAdCode();//地区编码
//                        amapLocation.getAoiName();//获取当前定位点的AOI信息
                        String info = amapLocation.getAdCode();//
                        Double latitude = amapLocation.getLatitude();//获取纬度
                        Double longitude = amapLocation.getLongitude();//获取经度
                        String dizhi = amapLocation.getDistrict();//城区信息
                        BaseApplication.getInstance().unRegisterLocation(mLocationListener);
                        Log.e("定位执行1", "----------->" + info);
                        UserManager.getInstance(WelcomeActivity.this).setLocation(DBManager.getInstance(WelcomeActivity.this).getCodeCity(info));
                        try {
                            Log.e("定位执行2", "----------->" + UserManager.getInstance(WelcomeActivity.this).getLocation());
                        }catch (Exception e){
                            Log.e("getLocation:", e.toString());
                        }
                    } else {
                        BaseApplication.getInstance().unRegisterLocation(mLocationListener);
//                        UserManager.getInstance(WelcomeActivity.this).setLocationInfo(new MyLocationInfo("370211", 35.7040929707, 119.7867577226, "黄岛区"));
                        UserManager.getInstance(WelcomeActivity.this).setLocation("1740,黄岛区");
                        //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                        Toast.makeText(WelcomeActivity.this, "" + amapLocation.getErrorInfo(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    BaseApplication.getInstance().unRegisterLocation(mLocationListener);
                    UserManager.getInstance(WelcomeActivity.this).setLocation("1740,黄岛区");
//                        UserManager.getInstance(WelcomeActivity.this).getLocationInfo().setRegionid("1740");
                    Toast.makeText(WelcomeActivity.this, "定位服务出错", Toast.LENGTH_SHORT).show();
                }
            }
        };
        BaseApplication.getInstance().setLocationListener(mLocationListener);
        BaseApplication.getInstance().beginLocation();
        isReady = true;
    }

}
