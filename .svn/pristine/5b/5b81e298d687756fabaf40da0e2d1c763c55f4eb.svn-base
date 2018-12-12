package com.dikai.chenghunjiclient;

import android.app.Application;
import android.support.multidex.MultiDex;
import android.view.View;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.awen.photo.FrescoImageLoader;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.GlideImageLoader;
import com.lzy.imagepicker.ImagePicker;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.commonsdk.UMConfigure;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Lucio on 2017/4/7.
 */

public class BaseApplication extends Application {

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener;
    public AMapLocationClientOption mLocationOption = null;
    public static BaseApplication mApplication;
    private IWXAPI mWxApi;

    public static BaseApplication getInstance() {
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    public void init() {

        mApplication = this;

        /**
         * 初始化高德地图定位
         */
        initLocation();
        FrescoImageLoader.init(this);
        //下面是配置toolbar颜色和存储图片地址的
//        FrescoImageLoader.init(this,android.R.color.holo_blue_light);
//        FrescoImageLoader.init(this,android.R.color.holo_blue_light,"/storage/xxxx/xxx");

        //初始化MulitDex
        MultiDex.install(this);
        //初始化腾讯Bugly
        CrashReport.initCrashReport(getApplicationContext(), "efb00565fd", true);
        //初始化友盟
        UMConfigure.init(this, "5be14491f1f55636b4000338", "common", UMConfigure.DEVICE_TYPE_PHONE, "");
        //初始化图片选择器
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(GlideImageLoader.getInstance()); //设置图片加载器
        //初始化NoHttp
        NoHttp.initialize(this);
        // 开启NoHttp的调试模式, 配置后可看到请求过程、日志和错误信息。
        Logger.setDebug(true);
        //初始化内存泄漏检测工具
//        LeakCanary.install(this);
        //首页悬浮窗
//        View view = View.inflate(this, R.layout.float_window_caigoujie_layout, null);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                EventBus.getDefault().post(new EventBusBean(Constants.CAIGOUJIE_CLICK));
//            }
//        });
        //注册微信
        registToWX();
    }

    private void registToWX() {
        //AppConst.WEIXIN.APP_ID是指你应用在微信开放平台上的AppID，记得替换。
        mWxApi = WXAPIFactory.createWXAPI(this,  Constants.WX_APP_ID, false);
        // 将该app注册到微信
        mWxApi.registerApp( Constants.WX_APP_ID);
    }

    public IWXAPI getWxApi() {
        return mWxApi;
    }

    /**
     * 初始化定位服务
     */
    public void initLocation(){

        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //声明mLocationOption对象
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);

        if(mLocationOption.isOnceLocationLatest()){
            mLocationOption.setOnceLocationLatest(false);
            //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。
            //如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会。
        }

        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);

    }

    public void setLocationListener(AMapLocationListener locationListener) {
        mLocationListener = locationListener;
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
    }

    public void beginLocation(){
        mLocationClient.startLocation();
    }

    public void unRegisterLocation(AMapLocationListener locationListener){
        mLocationClient.unRegisterLocationListener(locationListener);
    }
}
