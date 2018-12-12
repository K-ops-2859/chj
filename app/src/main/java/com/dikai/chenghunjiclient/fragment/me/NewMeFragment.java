package com.dikai.chenghunjiclient.fragment.me;


import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.discover.MyDynamicActivity;
import com.dikai.chenghunjiclient.activity.me.ClearActivity;
import com.dikai.chenghunjiclient.activity.me.EditRoomActivity;
import com.dikai.chenghunjiclient.activity.me.FocusActivity;
import com.dikai.chenghunjiclient.activity.me.HomepageActivity;
import com.dikai.chenghunjiclient.activity.me.HotelEditInfoActivity;
import com.dikai.chenghunjiclient.activity.me.MyCarActivity;
import com.dikai.chenghunjiclient.activity.me.MyCaseActivity;
import com.dikai.chenghunjiclient.activity.me.MyOrderActivity;
import com.dikai.chenghunjiclient.activity.me.MyTeamActivity;
import com.dikai.chenghunjiclient.activity.me.MyVideoActivity;
import com.dikai.chenghunjiclient.activity.me.MyWalletActivity;
import com.dikai.chenghunjiclient.activity.me.NewPlanActivity;
import com.dikai.chenghunjiclient.activity.me.SettingActivity;
import com.dikai.chenghunjiclient.activity.me.ShareAppActivity;
import com.dikai.chenghunjiclient.activity.me.SupEditInfoActivity;
import com.dikai.chenghunjiclient.activity.me.UserInfoActivity;
import com.dikai.chenghunjiclient.activity.me.WeddingPhotoActivity;
import com.dikai.chenghunjiclient.activity.me.XinRenPhotoActivity;
import com.dikai.chenghunjiclient.activity.newregister.NewLoginActivity;
import com.dikai.chenghunjiclient.activity.wedding.ConvertGiftActivity;
import com.dikai.chenghunjiclient.activity.wedding.InviteWedActivity;
import com.dikai.chenghunjiclient.bean.BeanCustomerInfoList;
import com.dikai.chenghunjiclient.bean.BeanID;
import com.dikai.chenghunjiclient.bean.BeanUserId;
import com.dikai.chenghunjiclient.entity.CustomerInfoData;
import com.dikai.chenghunjiclient.entity.NewUserInfo;
import com.dikai.chenghunjiclient.entity.UserInfo;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.DownloadUtil;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.TextLUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.flyco.dialog.widget.MaterialDialog;
import com.google.gson.Gson;
import com.joooonho.SelectableRoundedImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewMeFragment extends Fragment implements View.OnClickListener {
    private SelectableRoundedImageView logo;
    private TextView name;
    private TextView profession;
    private TextView editInfo;
    private TextView fans;
    private TextView focus;
    private TextView homePage;
    private LinearLayout hotelInfo;
    private LinearLayout roomEdit;
    private LinearLayout myCarTeam;
    private LinearLayout carEdit;
    private LinearLayout wedPhoto;
    private LinearLayout wedVideo;
    private LinearLayout myCase;
    private LinearLayout clearRecord;
    private LinearLayout download;
    private CustomerInfoData.DataList mData;
    private String code;
    private NotificationManager notifiManager;
    private NotificationCompat.Builder notifiBuilder;
    private MaterialDialog dialogDown;
    private int infoType = 3;
//    private String pageType;
    private String supId;
    private ActionSheetDialog moreDialog;

    public NewMeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        EventBus.getDefault().register(this);
        return inflater.inflate(R.layout.fragment_new_me, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logo = (SelectableRoundedImageView) view.findViewById(R.id.iv_user_logo);
        name = (TextView) view.findViewById(R.id.tv_user_name);
        profession = (TextView) view.findViewById(R.id.tv_user_profession);
        editInfo = (TextView) view.findViewById(R.id.tv_edit);
        fans = (TextView) view.findViewById(R.id.tv_fans);
        focus = (TextView) view.findViewById(R.id.tv_focus);
        hotelInfo = (LinearLayout) view.findViewById(R.id.ll_hotel);
        roomEdit = (LinearLayout) view.findViewById(R.id.ll_room);
        myCarTeam = (LinearLayout) view.findViewById(R.id.ll_mycar);
        carEdit = (LinearLayout) view.findViewById(R.id.ll_car);
        wedPhoto = (LinearLayout) view.findViewById(R.id.ll_photo);
        wedVideo = (LinearLayout) view.findViewById(R.id.ll_video);
        myCase = (LinearLayout) view.findViewById(R.id.ll_case);
        clearRecord = (LinearLayout) view.findViewById(R.id.ll_clear);
        download = (LinearLayout) view.findViewById(R.id.ll_down);
        homePage = (TextView) view.findViewById(R.id.go_home);
        editInfo.setOnClickListener(this);
        hotelInfo.setOnClickListener(this);
        roomEdit.setOnClickListener(this);
        myCarTeam.setOnClickListener(this);
        carEdit.setOnClickListener(this);
        wedPhoto.setOnClickListener(this);
        wedVideo.setOnClickListener(this);
        myCase.setOnClickListener(this);
        clearRecord.setOnClickListener(this);
        download.setOnClickListener(this);
        homePage.setOnClickListener(this);
        view.findViewById(R.id.iv_setting).setOnClickListener(this);
        view.findViewById(R.id.ll_plan).setOnClickListener(this);
        view.findViewById(R.id.ll_dongtai).setOnClickListener(this);
        view.findViewById(R.id.ll_order).setOnClickListener(this);
        view.findViewById(R.id.ll_money).setOnClickListener(this);
        view.findViewById(R.id.ll_invite).setOnClickListener(this);
        view.findViewById(R.id.ll_getprize).setOnClickListener(this);
        view.findViewById(R.id.ll_share).setOnClickListener(this);
        view.findViewById(R.id.ll_focus).setOnClickListener(this);
        view.findViewById(R.id.ll_fans).setOnClickListener(this);
        initNotify();
        dialogDown = new MaterialDialog(getContext());
        dialogDown.isTitleShow(false)//
                .btnNum(2)
                .content("是否下载成婚纪婚庆版？")//
                .btnText("取消", "确定")
                .setOnBtnClickL(new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialogDown.dismiss();
                    }
                }, new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialogDown.dismiss();
                        download();
                    }
                });
        final String[] stringItems = {"编辑个人资料", "编辑供应商资料"};
        moreDialog = new ActionSheetDialog(getContext(), stringItems,null);
        moreDialog.isTitleShow(false)
                .setOnOperItemClickL(new OnOperItemClickL() {
                    @Override
                    public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                        moreDialog.dismiss();
                        if (position == 0) {
                            startActivity(new Intent(getContext(), UserInfoActivity.class));
                        } else if (position == 1) {
                            startActivity(new Intent(getContext(), SupEditInfoActivity.class));
                        }
                    }
                });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (UserManager.getInstance(getContext()).isLogin()){
            getUser(UserManager.getInstance(getContext()).getNewUserInfo().getUserId());
        }else {
            refresh();
        }
    }

    private void refresh() {
        hotelInfo.setVisibility(View.GONE);
        roomEdit.setVisibility(View.GONE);
        myCarTeam.setVisibility(View.GONE);
        carEdit.setVisibility(View.GONE);
        wedPhoto.setVisibility(View.GONE);
        wedVideo.setVisibility(View.GONE);
        myCase.setVisibility(View.GONE);
        clearRecord.setVisibility(View.GONE);
        download.setVisibility(View.GONE);
        homePage.setVisibility(View.VISIBLE);
        if (UserManager.getInstance(getContext()).isLogin()) {
            NewUserInfo info = UserManager.getInstance(getContext()).getNewUserInfo();
            supId = UserManager.getInstance(getContext()).getNewUserInfo().getFacilitatorId();
//            refreshUser();
            code = info.getProfession().toUpperCase();
            Log.e("zhiye", code);
            switch (code) {
                case "99C06C5A-DDB8-46A1-B860-CD1227B4DB68"://酒店
//                    hotelInfo.setVisibility(View.VISIBLE);
                    roomEdit.setVisibility(View.VISIBLE);
                    myCase.setVisibility(View.VISIBLE);
                    infoType = 1;
                    break;
                case "2526D327-B0AE-4D88-922E-1F7A91722422"://婚车
                    myCarTeam.setVisibility(View.VISIBLE);
                    carEdit.setVisibility(View.VISIBLE);
                    myCase.setVisibility(View.VISIBLE);
//                    clearRecord.setVisibility(View.VISIBLE);
                    infoType = 1;
                    break;
                case "F209497C-2F2E-4394-AF20-312ED665F67A"://车手
                    myCarTeam.setVisibility(View.VISIBLE);
                    carEdit.setVisibility(View.VISIBLE);
                    homePage.setVisibility(View.GONE);
                    infoType = 2;
                    break;
                case "70CD854E-D943-4607-B993-91912329C61E"://用户（新人）
//                    wedPhoto.setVisibility(View.VISIBLE);
//                    wedVideo.setVisibility(View.VISIBLE);
                    homePage.setVisibility(View.GONE);
                    infoType = 2;
                    break;
                case "5C1D8DA0-9BB6-4CA0-8801-6EA3E187884F"://摄像
                case "41A3BF32-BBB1-4957-9914-50E17E96795B"://摄影
//                    wedPhoto.setVisibility(View.VISIBLE);
//                    wedVideo.setVisibility(View.VISIBLE);
                    myCase.setVisibility(View.VISIBLE);
//                    clearRecord.setVisibility(View.VISIBLE);
                    infoType = 1;
                    break;
                case "7DC8EDF8-A068-400F-AFD0-417B19DB3C7C"://婚庆
                    myCase.setVisibility(View.VISIBLE);
//                    clearRecord.setVisibility(View.VISIBLE);
                    download.setVisibility(View.VISIBLE);
                    infoType = 1;
                    break;
                default://其他
                    myCase.setVisibility(View.VISIBLE);
//                    clearRecord.setVisibility(View.VISIBLE);
                    infoType = 1;
                    break;

            }
            editInfo.setText("点击编辑");
            profession.setVisibility(View.VISIBLE);
            TextLUtil.setLength(getContext(),name,profession,info.getName(),
                    UserManager.getInstance(getContext()).getProfession(info.getProfession()),160,8);
            fans.setText(info.getFansNumber()+"");
            focus.setText(info.getFollowNumber()+"");
            Glide.with(getContext()).load(info.getHeadportrait()).into(logo);
        } else {
            profession.setVisibility(View.GONE);
            logo.setImageResource(R.mipmap.ic_logo_new);
            name.setText("未登录");
            editInfo.setText("点击登录");
            fans.setText("0");
            focus.setText("0");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_edit://编辑个人资料
                if (!UserManager.getInstance(getContext()).isLogin()) {
                    startActivity(new Intent(getContext(), NewLoginActivity.class));
                } else {
                    if (infoType == 1) {
                        moreDialog.show();
                    } else {
                        startActivity(new Intent(getContext(), UserInfoActivity.class));
                    }
                }
                break;
            case R.id.iv_setting://设置
                startActivity(new Intent(getContext(), SettingActivity.class));
                break;
            case R.id.ll_plan://安排
                if (UserManager.getInstance(getContext()).checkLogin()) {
                    startActivity(new Intent(getContext(), NewPlanActivity.class));
                }
                break;
            case R.id.ll_dongtai://动态
                if (UserManager.getInstance(getContext()).checkLogin()) {
                    startActivity(new Intent(getContext(), MyDynamicActivity.class));
                }
                break;
            case R.id.ll_order://订单
                if (UserManager.getInstance(getContext()).checkLogin()) {
                    startActivity(new Intent(getContext(), MyOrderActivity.class));
                }
                break;
            case R.id.ll_money://钱包
                if (UserManager.getInstance(getContext()).checkLogin()) {
                    startActivity(new Intent(getContext(), MyWalletActivity.class));
                }
                break;
            case R.id.ll_invite://邀请结婚
                if (UserManager.getInstance(getContext()).checkLogin()) {
                    startActivity(new Intent(getContext(), InviteWedActivity.class));
                }
                break;
            case R.id.ll_getprize://领取奖品
                if (UserManager.getInstance(getContext()).checkLogin()) {
                    startActivity(new Intent(getContext(), ConvertGiftActivity.class));
                }
                break;
            case R.id.ll_share://分享
                if (UserManager.getInstance(getContext()).checkLogin()) {
//                    startActivity(new Intent(getContext(), ShareAppActivity.class));
                    showShare();
                }
                break;
            case R.id.ll_hotel://酒店资料
                startActivity(new Intent(getContext(), HotelEditInfoActivity.class));
                break;
            case R.id.ll_room://宴会厅管理
                startActivity(new Intent(getContext(), EditRoomActivity.class));
                break;
            case R.id.ll_mycar://我的车队
                startActivity(new Intent(getContext(), MyTeamActivity.class));
                break;
            case R.id.ll_car://车辆管理
                startActivity(new Intent(getContext(), MyCarActivity.class));
                break;
            case R.id.ll_photo://婚礼照片
                if (UserManager.getInstance(getContext()).checkLogin()) {
                    if (code.equals("SF_12001000")) {//TODO:未转换身份代码
                        Intent intent = new Intent(getContext(), XinRenPhotoActivity.class);
                        if (mData != null) {
                            intent.putExtra("id", mData.getCustomerInfoID());
                            startActivity(intent);
                        } else {
                            intent.putExtra("id", -1);
                            startActivity(intent);
                        }
                    } else {
                        Intent intent = new Intent(getContext(), WeddingPhotoActivity.class);
                        intent.putExtra("identity", code);
                        intent.putExtra("type", 0);
                        startActivity(intent);
                    }
                }
                break;
            case R.id.ll_video://婚礼视频
                if (UserManager.getInstance(getContext()).checkLogin()) {
                    if (code.equals("SF_12001000")) {//TODO:未转换身份代码
                        Intent intent = new Intent(getContext(), MyVideoActivity.class);
                        if (mData != null) {
                            intent.putExtra("id", mData.getCustomerInfoID());
                            startActivity(intent);
                        } else {
                            intent.putExtra("id", -1);
                            startActivity(intent);
                        }
                    } else {
                        Intent intent = new Intent(getContext(), WeddingPhotoActivity.class);
                        intent.putExtra("identity", code);
                        intent.putExtra("type", 1);
                        startActivity(intent);
                    }
                }
                break;
            case R.id.ll_case://我的案例
                if (UserManager.getInstance(getContext()).checkLogin()) {
                    startActivity(new Intent(getContext(), MyCaseActivity.class));
                }
                break;
            case R.id.ll_clear://结算记录
                if (UserManager.getInstance(getContext()).checkLogin()) {
                    startActivity(new Intent(getContext(), ClearActivity.class));
                }
                break;
            case R.id.ll_focus://我的关注
                if (UserManager.getInstance(getContext()).checkLogin()) {
                    startActivity(new Intent(getContext(), FocusActivity.class).putExtra("type",0));
                }
                break;
            case R.id.ll_fans://我的粉丝
                if (UserManager.getInstance(getContext()).checkLogin()) {
                    startActivity(new Intent(getContext(), FocusActivity.class).putExtra("type",1));
                }
                break;
            case R.id.ll_down://下载婚庆版
                if(isAvilible(getContext(),"com.dikai.chenghunji")){
                    Toast.makeText(getContext(), "您的手机已经安装了成婚纪婚庆版！", Toast.LENGTH_SHORT).show();
                }else {
                    dialogDown.show();
                }
                break;
            case R.id.go_home://前往主页
                if (UserManager.getInstance(getContext()).checkLogin()) {
                    startActivity(new Intent(getContext(), HomepageActivity.class)
                            .putExtra("pageId", supId));
                }
                break;
        }
    }

    /**
     * 获取用户信息
     */
    public void getUser(String userID) {
        NetWorkUtil.setCallback("HQOAApi/GetUserFacilitatorInfo",
                new BeanID(userID), new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(String respose) {
                        Log.e("返回值",respose);
                        try {
                            NewUserInfo result = new Gson().fromJson(respose, NewUserInfo.class);
                            if (result.getMessage().getCode().equals("200")) {
                                UserManager.getInstance(getContext()).setLogin(result,false);
                                refresh();
                            }else {
                                Toast.makeText(getContext(), result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            Log.e("",e.toString());
                        }
                    }
                    @Override
                    public void onError(String e) {
                        Log.e("网络请求错误",e);
                    }
                });
    }

    /**
     * 获取客户信息
     */
    private void refreshUser() {
        String userID = UserManager.getInstance(getContext()).getNewUserInfo().getUserId();
        NetWorkUtil.setCallback("User/GetCustomerInfoList",
                new BeanCustomerInfoList(userID), new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(String respose) {
                        try {
                            CustomerInfoData infoData = new Gson().fromJson(respose, CustomerInfoData.class);
                            if (infoData.getMessage().getCode().equals("200")) {
                                List<CustomerInfoData.DataList> dataList = infoData.getData();
//                                System.out.println("用户选择供应商" + dataList.size());
                                if (dataList.size() > 0) {
                                    mData = dataList.get(0);
                                }
                            }
                        }catch (Exception e){
                            Log.e("",e.toString());
                        }
                    }
                    @Override
                    public void onError(String e) {}
                });
    }

    /**
     * 初始化更新通知
     */
    private void initNotify() {
        notifiBuilder = new NotificationCompat.Builder(getContext())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("下载成婚纪婚庆版")
                .setContentText("下载进度")
                .setTicker("下载更新")
                .setPriority(Notification.PRIORITY_HIGH)
                .setProgress(100, 0, false);
//        notification = notifiBuilder.build();
//        notification.flags |= Notification.FLAG_ONGOING_EVENT;
        notifiManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
    }

    private void download(){
        Toast.makeText(getContext(), "开始下载", Toast.LENGTH_SHORT).show();
        Notification notification = notifiBuilder.build();
        notification.flags |= Notification.FLAG_ONGOING_EVENT;
        notifiManager.notify(0,notification);
        String url = "http://www.chenghunji.com/Download/Corp/corpchenghunji.apk";
//        String fileFolder = Environment.getExternalStorageDirectory() + "/ChengHunJi/update";
        final String cachePath = (getContext().getExternalFilesDir("upgrade_apk").getAbsolutePath());
        DownloadUtil.setCallback(url, cachePath, new DownloadUtil.CallBackListener() {
            @Override
            public void onFinish(String respose) {
                notifiManager.cancel(0);
                installApk(new File(cachePath + "/corpchenghunji.apk"));
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
            installApkIntent.setDataAndType(FileProvider.getUriForFile(getContext().getApplicationContext(),
                    "com.dikai.chenghunjiclient.file_provider", apkFile), "application/vnd.android.package-archive");
            installApkIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            installApkIntent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }

        if (getContext().getPackageManager().queryIntentActivities(installApkIntent, 0).size() > 0) {
            startActivity(installApkIntent);
        }
    }

    /**
     * 检查手机上是否安装了指定的软件
     * @return
     */
    public boolean isAvilible(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        List<String> packageNames = new ArrayList<String>();

        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        // 判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
//        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("成婚纪，一站式婚礼承办有惊喜！");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://www.chenghunji.com/Download/User/chenghunji.apk");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("与好友一起起航，登上“小成梦想号”，赴免费盛宴，掌握赚钱秘籍，赢高额奖励金哦！");
//        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("");//确保SDcard下面存在此张图片
        oks.setImageUrl("http://www.chenghunji.com/img/index/B_LOGO_01.png");
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://www.chenghunji.com/Download/User/chenghunji.apk");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("成婚纪——婚礼原来如此简单");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("成婚纪");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://www.chenghunji.com/");
        // 启动分享GUI
        oks.show(getContext());
    }

    /**
     * 事件总线刷新
     * @param bean
     */
    @Subscribe
    public void onEventMainThread(final EventBusBean bean) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (bean.getType() == Constants.USER_INFO_CHANGE) {
                    if (UserManager.getInstance(getContext()).isLogin()){
                        getUser(UserManager.getInstance(getContext()).getNewUserInfo().getUserId());
                    }else {
                        refresh();
                    }
                }else if(bean.getType() == Constants.FOCUS_CHANGE){
                    if (UserManager.getInstance(getContext()).isLogin()){
                        getUser(UserManager.getInstance(getContext()).getNewUserInfo().getUserId());
                    }
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
