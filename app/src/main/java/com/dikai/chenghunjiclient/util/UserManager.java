package com.dikai.chenghunjiclient.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import com.dikai.chenghunjiclient.bean.BeanGetUserInfo;
import com.dikai.chenghunjiclient.bean.BeanGetVersion;
import com.dikai.chenghunjiclient.bean.BeanID;
import com.dikai.chenghunjiclient.bean.BeanNull;
import com.dikai.chenghunjiclient.bean.BeanType;
import com.dikai.chenghunjiclient.entity.AccountBean;
import com.dikai.chenghunjiclient.entity.IdentityBean;
import com.dikai.chenghunjiclient.entity.NewIdentityBean;
import com.dikai.chenghunjiclient.entity.NewUserInfo;
import com.dikai.chenghunjiclient.entity.ResultGetIdentity;
import com.dikai.chenghunjiclient.entity.ResultGetNewUserInfo;
import com.dikai.chenghunjiclient.entity.ResultGetVersion;
import com.dikai.chenghunjiclient.entity.ResultNewIdentity;
import com.dikai.chenghunjiclient.entity.ResultNewPhoneCode;
import com.dikai.chenghunjiclient.entity.UserInfo;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lucio on 2017/4/24.
 */

public class UserManager {
    private int myCartNum;
    private static UserManager sUserManager;
    private Context sContext;
    private UserInfo userInfo;
    private boolean isLogin;
    private String logoVersion;
    private List<IdentityBean> mIdentityBeanList;
    private Map<String, String> allIdentMap;
    private String mVersionInfo = "已是最新版本";
    private boolean haveNewVersion;
    private String location;

    private List<NewIdentityBean> newProfessions;
    private Map<String, String> professionMap;

    /**==============================对外接口（新版）===========================*/

    private NewUserInfo mNewUserInfo;

    private UserManager(Context context){
        sContext = context.getApplicationContext();
    }

    /**
     * 获取实例
     */
    public static UserManager getInstance(Context context){
        if(sUserManager == null){
            sUserManager = new UserManager(context);
        }
        return sUserManager;
    }

    /**
     * 获取用户信息
     */
    public NewUserInfo getNewUserInfo() {
        return mNewUserInfo;
    }

    /**
     * 设置用户登录信息
     */
    public void setLogin(NewUserInfo info, boolean isEvent){
        isLogin = true;
        mNewUserInfo = info;
        NewUserDBManager.getInstance(sContext).updateUserInfo(info);
        AccountDBManager.getInstance(sContext)
                .addRecord(new AccountBean(info.getUserId(),info.getName(),info.getPhone(),info.getHeadportrait()));
        if(isEvent)
        EventBus.getDefault().post(new EventBusBean(Constants.USER_INFO_CHANGE));
    }

    /**
     * 用户注销
     */
    public void setLogout(){
        isLogin = false;
        mNewUserInfo = new NewUserInfo();
        mNewUserInfo = new NewUserInfo("","","","","","");
        NewUserDBManager.getInstance(sContext).updateUserInfo(mNewUserInfo);
        AccountDBManager.getInstance(sContext).clearAll();
        EventBus.getDefault().post(new EventBusBean(Constants.USER_INFO_CHANGE));
    }

    /**
     * 检测登录信息
     */
    public boolean checkNewUserInfo(){
        mNewUserInfo = NewUserDBManager.getInstance(sContext).getUserInfo();
        if("".equals(mNewUserInfo.getUserId())||mNewUserInfo.getUserId() == null){
            isLogin = false;
        }else {
            isLogin = true;
        }
        return isLogin;
    }

    /**
     * 获取登录状态
     */
    public boolean isLogin() {
        return isLogin;
    }

    /**
     * 获取登录状态,未登录则跳转登录界面
     */
    public boolean checkLogin() {
        if(!isLogin){
            EventBus.getDefault().post(new EventBusBean(Constants.CHECK_LOGIN));
        }
        return isLogin;
    }

    /**
     * 获取职业信息
     * @return
     */
    public List<NewIdentityBean> getNewProfessions() {
        return newProfessions;
    }

    /**
     * 获取职业信息
     * @return
     */
    public Map<String, String> getProfessionMap() {
        return professionMap;
    }

    /**
     * 根据职业代码职业信息
     * @return
     */
    public String getProfession(String code) {
        if(professionMap != null){
            return professionMap.get(code);
        }else {
            return "";
        }
    }

    /**
     * 设置职业信息
     */
    public void setProfessionMap(List<NewIdentityBean> identityBeanList) {
        newProfessions = identityBeanList;
        professionMap = new HashMap<>();
        for (NewIdentityBean bean:identityBeanList) {
            professionMap.put(bean.getOccupationID(),bean.getOccupationName());
        }
    }

    /**
     * 获取购物车数量
     */
    public int getMyCartNum() {
        return myCartNum;
    }
    /**
     * 设置购物车数量
     */
    public void setMyCartNum(int myCartNum) {
        this.myCartNum = myCartNum;
    }

    /**
     * 设置账户绑定位置
     */
    public void setLocation(String location) {
        this.location = location;
        Log.e("setLocation","===========" + location);
//        UserDBManager.getInstance(sContext).updateLocation(userInfo.getUserID(),location);
    }

    /**
     * 获取账户绑定位置
     */
    public String getLocation() {
        return location;
    }

    /**
     * 获取版本状态
     * @return
     */
    public boolean isHaveNewVersion() {
        return haveNewVersion;
    }

    /**
     * 获取当前版本信息
     * @return
     */
    public String getVersionInfo() {
        return mVersionInfo;
    }



    /**==============================具体执行方法===========================*/

    public void getVersion(final OnGetVersionListener mListener){
        NetWorkUtil.setCallback("HQOAApi/GetAPPEditionInfo",
                new BeanGetVersion("0"),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultGetVersion result = new Gson().fromJson(respose, ResultGetVersion.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                Object[] version = getAppVersionInfo();
                                int appCode = (int) version[0];
                                int newCode = result.getData().get(0).getVersion();
                                String versionInfo = (String) version[1];
                                if (appCode != -1 && newCode != 0)
                                    checkVersion(appCode, newCode, versionInfo);
                                mListener.onFinish();
                            } else {
                                mListener.onError(result.getMessage().getInform());
//                                Toast.makeText(sContext, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            mListener.onError(e.toString());
                            Log.e("json解析出错",e.toString());
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mListener.onError(e.toString());
                        Log.e("json解析出错","网络请求错误");
                    }
                });
    }

    /**
     * 获取供应商身份
     * @param type 0、获取所有
     * 1、注册（不包含公司、用户、车手、员工）
     * 2、主页（不包含 用户、车手、员工）
     * 3、主页（不包含 公司、用户、车手、员工,酒店）
     *  9A86A0AB-C13B-4A6D-AB97-1D123AF7C69E	化妆师
     *  2526D327-B0AE-4D88-922E-1F7A91722422	婚车
     *  72FE3832-CA92-44CF-9B73-28576E77FA3E	督导师
     *  0D2E7D67-57EA-4566-B2FE-2972DDE00306	主持人
     *  F209497C-2F2E-4394-AF20-312ED665F67A	车手
     *  7DC8EDF8-A068-400F-AFD0-417B19DB3C7C	婚庆
     *  76E2FC81-ADF4-4F3A-8805-499C4D634F23	花艺师
     *  F7CC4F9E-A518-47D8-BFF7-4FB9F033CDA8	演艺
     *  41A3BF32-BBB1-4957-9914-50E17E96795B	摄像师
     *  5C1D8DA0-9BB6-4CA0-8801-6EA3E187884F	摄影师
     *  3CBD4B30-87BF-48C3-98E6-7C210A7F4EFB	灯光师
     *  70CD854E-D943-4607-B993-91912329C61E	用户
     *  9FFDE235-61BF-408B-8C35-AE76D9113169	员工
     *  ADF7BAAC-AD51-4605-99EE-C59A40BD165D	婚纱
     *  99C06C5A-DDB8-46A1-B860-CD1227B4DB68	酒店
     *  CCE67F8C-66CF-4979-92FA-D751190583E6	大屏幕
     */
    public void getProfession(int type, final OnGetIdentListener mOnGetIdentListener){
        NetWorkUtil.setCallback("HQOAApi/GetAllOccupationList",
                new BeanType(type),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultNewIdentity result = new Gson().fromJson(respose, ResultNewIdentity.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                mOnGetIdentListener.onFinish(result);
                            } else {
                                mOnGetIdentListener.onError(result.getMessage().getInform());
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            mOnGetIdentListener.onError(e.toString());
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mOnGetIdentListener.onError(e);
                    }
                });
    }

    /**
     * 返回当前程序版本信息
     */
    private Object[] getAppVersionInfo() {
        int versioncode = -1;
        String versionName = null;
        try {
            // ---get the package info---
            PackageManager pm = sContext.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(sContext.getPackageName(), 0);
            versioncode = pi.versionCode;
            versionName = pi.versionName;
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return new Object[]{versioncode,versionName};
    }

    /**
     * 检查版本更新
     * @param appCode 当前应用版本号
     * @param newCode 应用最新版本号
     */
    private void checkVersion(int appCode,int newCode,String versionInfo){
        if(newCode > appCode){
            haveNewVersion = true;
            mVersionInfo = "发现新版本" + versionInfo;
        }else {
            haveNewVersion = false;
            mVersionInfo = "已是最新版本" + versionInfo;
        }
    }
    /**
     * 获取版本回调
     */
    public interface OnGetVersionListener{
        void onFinish();
        void onError(String e);
    }

    /**
     * 获取职业回调
     */
    public interface OnGetIdentListener{
        void onFinish(ResultNewIdentity result);
        void onError(String e);
    }

    /**
     * 获取用户信息回调
     */
    public interface OnGetInfoListener{
        void onFinish(NewUserInfo result);
        void onError(String e);
    }
    //======================================过时方法=======================================

    /**
     * 注销登录信息(已过时)
     */
    @Deprecated
    public void logout(){
        resetLogin();
    }

    /**
     * 自动登录(已过时)
     */
    @Deprecated
    public void autoLogin(OnLoginListener mOnLoginListener){
        checkUserInfo(mOnLoginListener);
    }

    /**
     * 获取用户信息(已过时)
     */
    @Deprecated
    public UserInfo getUserInfo() {
        return userInfo;
    }

    /**
     * 获取职业信息(已过时)
     * @return
     */
    @Deprecated
    public List<IdentityBean> getIdentityBeanList() {
        return mIdentityBeanList;
    }

    /**
     * 获取职业信息(已过时)
     * @return
     */
    @Deprecated
    public Map<String, String> getAllIdentMap() {
        return allIdentMap;
    }

    /**
     * 设置职业信息(已过时)
     * @return
     */
    @Deprecated
    public void setIdentityBeanList(List<IdentityBean> identityBeanList , OnGetIdentListener m) {
        mIdentityBeanList = identityBeanList;
        allIdentMap = new HashMap<>();
        for (IdentityBean bean:identityBeanList) {
            allIdentMap.put(bean.getOccupationCode(),bean.getOccupationName());
        }
    }

    /**
     * 根据职业代码职业信息(已过时)
     * @return
     */
    @Deprecated
    public String getIdentity(String code) {
        if(allIdentMap!=null){
            return allIdentMap.get(code);
        }else {
            return "";
        }
    }

    /**
     * 存储用户登录信息(已过时)
     * @param userName
     * @param pwd
     */
    @Deprecated
    private void saveUserPhone(String userName,String pwd){
        UserDBManager.getInstance(sContext).updateUserInfo(userName, pwd);
    }

    /**
     * 清空用户登录数据(已过时)
     */
    @Deprecated
    private void resetLogin(){
        userInfo = null;
        isLogin = false;
        UserDBManager.getInstance(sContext).updateUserInfo("", "");
        EventBus.getDefault().post(new EventBusBean(Constants.USER_INFO_CHANGE));
    }

    /**
     * 检测登录信息(已过时)
     * @return
     */
    @Deprecated
    private void checkUserInfo(OnLoginListener mOnLoginListener){
        String[] info = UserDBManager.getInstance(sContext).getUserInfo();
//        Log.e("登录信息：",info[0] + "<=======>" + info[1]);
        if(info[0] != null && info[1] != null && !"".equals(info[0]) && !"".equals(info[1])){
            login(info[0], info[1] , mOnLoginListener);
        }else {
            mOnLoginListener.onError("无登录记录");
        }
    }

    /**
     * 用户登录(已过时)
     * @param name
     * @param password
     */
    @Deprecated
    public void login(final String name, final String password, final OnLoginListener mOnLoginListener){
        NetWorkUtil.setCallback("User/SupplierLogin",
                new BeanGetUserInfo(name,MD5Util.md5(password)),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            UserInfo result = new Gson().fromJson(respose, UserInfo.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                userInfo = result;
                                isLogin = true;
                                saveUserPhone(name, password);
                                //CrashReport.setUserId(result.getPhone());
//
//                                Log.e("执行至此","=========1");
//                                String loca = UserDBManager.getInstance(sContext).getLocation(result.getUserID());
//                                if(loca == null || "".equals(loca)){
//                                    EventBus.getDefault().post(new EventBusBean(Constants.SET_USER_LOCATION));
//                                }else {
//                                    location = loca;
//                                }
//                                Log.e("执行至此","=========" + location);
                                EventBus.getDefault().post(new EventBusBean(Constants.USER_INFO_CHANGE));
                                mOnLoginListener.onFinish();
                            } else {
                                mOnLoginListener.onError(result.getMessage().getInform());
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            mOnLoginListener.onError("网络请求错误");
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mOnLoginListener.onError("网络请求错误");
                    }
                });
    }

    /**
     * 获取职业(已过时)
     */
    @Deprecated
    public void getIdentity(final OnGetIdentListener mOnGetIdentListener){
        NetWorkUtil.setCallback("User/GetAllOccupationList",
                new BeanNull("0"),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultGetIdentity result = new Gson().fromJson(respose, ResultGetIdentity.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                setIdentityBeanList(result.getData(),mOnGetIdentListener);
                            } else {
                                mOnGetIdentListener.onError(result.getMessage().getInform());
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            mOnGetIdentListener.onError("json解析出错");
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mOnGetIdentListener.onError("网络错误");
                    }
                });
    }

    /**
     * 登录回调(已过时)
     */
    public interface OnLoginListener{
        void onFinish();
        void onError(String e);
    }

}
