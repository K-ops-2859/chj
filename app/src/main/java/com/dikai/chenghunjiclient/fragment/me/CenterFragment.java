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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.ad.SupKeYuanActivity;
import com.dikai.chenghunjiclient.activity.discover.MyDynamicActivity;
import com.dikai.chenghunjiclient.activity.invitation.ComInviteMainActivity;
import com.dikai.chenghunjiclient.activity.invitation.VipInviteMainActivity;
import com.dikai.chenghunjiclient.activity.me.ClearActivity;
import com.dikai.chenghunjiclient.activity.me.EditRoomActivity;
import com.dikai.chenghunjiclient.activity.me.EditSupInfoActivity;
import com.dikai.chenghunjiclient.activity.me.FocusActivity;
import com.dikai.chenghunjiclient.activity.me.HomepageActivity;
import com.dikai.chenghunjiclient.activity.me.MyCarActivity;
import com.dikai.chenghunjiclient.activity.me.MyCaseActivity;
import com.dikai.chenghunjiclient.activity.me.MyTeamActivity;
import com.dikai.chenghunjiclient.activity.me.MyVideoActivity;
import com.dikai.chenghunjiclient.activity.me.NewOrderActivity;
import com.dikai.chenghunjiclient.activity.me.NewPlanActivity;
import com.dikai.chenghunjiclient.activity.me.NewRoomActivity;
import com.dikai.chenghunjiclient.activity.me.SettingActivity;
import com.dikai.chenghunjiclient.activity.me.SupEditInfoActivity;
import com.dikai.chenghunjiclient.activity.me.SwitchAccountActivity;
import com.dikai.chenghunjiclient.activity.me.UserInfoActivity;
import com.dikai.chenghunjiclient.activity.me.WalletActivity;
import com.dikai.chenghunjiclient.activity.me.WeddingPhotoActivity;
import com.dikai.chenghunjiclient.activity.me.XinRenPhotoActivity;
import com.dikai.chenghunjiclient.activity.newregister.NewLoginActivity;
import com.dikai.chenghunjiclient.activity.store.PipelineActivity;
import com.dikai.chenghunjiclient.activity.store.SupPipelineActivity;
import com.dikai.chenghunjiclient.activity.wedding.ConvertGiftActivity;
import com.dikai.chenghunjiclient.adapter.me.ServiceAdapter;
import com.dikai.chenghunjiclient.bean.BeanID;
import com.dikai.chenghunjiclient.bean.BeanUserId;
import com.dikai.chenghunjiclient.entity.CustomerInfoData;
import com.dikai.chenghunjiclient.entity.NewUserInfo;
import com.dikai.chenghunjiclient.entity.ResultGetInvitationProfit;
import com.dikai.chenghunjiclient.entity.ServiceModule;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.DownloadUtil;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
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
import dmax.dialog.SpotsDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class CenterFragment extends Fragment implements View.OnClickListener {

    private RecyclerView serviceRecycler;
    private ServiceAdapter mServiceAdapter;

    private ImageView identImg;
    private SelectableRoundedImageView logo;
    private TextView name;
    private TextView profession;
    private TextView fans;
    private TextView focus;
    private TextView homePage;

    private CustomerInfoData.DataList mData;
    private String code;
    private NotificationManager notifiManager;
    private NotificationCompat.Builder notifiBuilder;
    private MaterialDialog dialogDown;
    private int infoType = 3;
    private String supId;
    private ActionSheetDialog moreDialog;
    private int identType;//0 商家 1 用户

    private SpotsDialog mDialog;

    public CenterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        EventBus.getDefault().register(this);
        return inflater.inflate(R.layout.fragment_layout_center_gold, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDialog = new SpotsDialog(getContext(),R.style.DialogCustom);
        identImg = (ImageView) view.findViewById(R.id.tv_profession_img);
        logo = (SelectableRoundedImageView) view.findViewById(R.id.iv_user_logo);
        name = (TextView) view.findViewById(R.id.tv_user_name);
        profession = (TextView) view.findViewById(R.id.tv_user_profession);
        fans = (TextView) view.findViewById(R.id.tv_fans);
        focus = (TextView) view.findViewById(R.id.tv_focus);
        homePage = (TextView) view.findViewById(R.id.go_home);
        serviceRecycler = (RecyclerView) view.findViewById(R.id.service_recycler);
        view.findViewById(R.id.ll_plan).setOnClickListener(this);
        view.findViewById(R.id.ll_dongtai).setOnClickListener(this);
        view.findViewById(R.id.ll_order).setOnClickListener(this);
        view.findViewById(R.id.ll_money).setOnClickListener(this);
        view.findViewById(R.id.iv_setting).setOnClickListener(this);
        view.findViewById(R.id.switch_account).setOnClickListener(this);
        logo.setOnClickListener(this);
        homePage.setOnClickListener(this);
        focus.setOnClickListener(this);
        fans.setOnClickListener(this);
        name.setOnClickListener(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        serviceRecycler.setLayoutManager(gridLayoutManager);
        mServiceAdapter = new ServiceAdapter(getContext());
        mServiceAdapter.setOnItemClickListener(new ServiceAdapter.OnItemClickListener() {
            @Override
            public void onClick(ServiceModule bean) {
                switch (bean.getType()){
                    case 0://我的案例
                        if (UserManager.getInstance(getContext()).checkLogin()) {
                            startActivity(new Intent(getContext(), MyCaseActivity.class));
                        }
                        break;
                    case 1://结算记录
                        if (UserManager.getInstance(getContext()).checkLogin()) {
                            startActivity(new Intent(getContext(), ClearActivity.class));
                        }
                        break;
                    case 2://邀请结婚
                        if (UserManager.getInstance(getContext()).checkLogin()) {
//                            startActivity(new Intent(getContext(), InviteWedActivity.class));
                            getProfit(UserManager.getInstance(getContext()).getNewUserInfo().getUserId());
                        }
                        break;
                    case 3://宴会厅管理
                        startActivity(new Intent(getContext(), NewRoomActivity.class));
                        break;
                    case 4://车辆管理
                        startActivity(new Intent(getContext(), MyCarActivity.class));
                        break;
                    case 5://我的车队
                        startActivity(new Intent(getContext(), MyTeamActivity.class));
                        break;
                    case 6://婚礼照片
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
                    case 7://婚礼视频
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
                    case 8://分享
                        if (UserManager.getInstance(getContext()).checkLogin()) {
//                    startActivity(new Intent(getContext(), ShareAppActivity.class));
                            showShare();
                        }
                        break;
                    case 9://下载婚庆版
                        if(isAvilible(getContext(),"com.dikai.chenghunji")){
                            Toast.makeText(getContext(), "您的手机已经安装了成婚纪婚庆版！", Toast.LENGTH_SHORT).show();
                        }else {
                            dialogDown.show();
                        }
                        break;
                    case 10://领取奖品
                        if (UserManager.getInstance(getContext()).checkLogin()) {
                            startActivity(new Intent(getContext(), ConvertGiftActivity.class));
                        }
                        break;
                    case 11://婚礼支付
                        if (UserManager.getInstance(getContext()).checkLogin()) {
                            if(identType == 0){
                                startActivity(new Intent(getContext(), SupPipelineActivity.class));
                            }else {
                                startActivity(new Intent(getContext(), PipelineActivity.class));
                            }
                        }
                        break;
                    case 12://客源分配
                        if (UserManager.getInstance(getContext()).checkLogin()) {
                            startActivity(new Intent(getContext(), SupKeYuanActivity.class));
                        }
                        break;
                }
            }
        });
        serviceRecycler.setAdapter(mServiceAdapter);
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
        final String[] stringItems = {"编辑个人资料", "编辑商家资料"};
        moreDialog = new ActionSheetDialog(getContext(), stringItems,null);
        moreDialog.isTitleShow(false)
                .setOnOperItemClickL(new OnOperItemClickL() {
                    @Override
                    public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                        moreDialog.dismiss();
                        if (position == 0) {
                            startActivity(new Intent(getContext(), UserInfoActivity.class));
                        } else if (position == 1) {
//                            startActivity(new Intent(getContext(), SupEditInfoActivity.class));
                            startActivity(new Intent(getContext(), EditSupInfoActivity.class));
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
    /**
     * ServiceModule-type:
     * 0:我的案例
     * 1:结算记录
     * 2:邀请结婚
     * 3:宴会厅管理
     * 4:车辆管理
     * 5:我的车队
     * 6:婚礼照片
     * 7:婚礼视频
     * 8:分享APP
     * 9:下载婚庆版
     * 10:领取礼品
     * 11:婚礼支付
     * 12:客源分配
     */
    private void refresh() {
        identType = 0;
        infoType = 1;
        mServiceAdapter.setHide(0);
        mServiceAdapter.setHide(1);
//        mServiceAdapter.setHide(2);
        mServiceAdapter.setHide(3);
        mServiceAdapter.setHide(4);
        mServiceAdapter.setHide(5);
        mServiceAdapter.setHide(6);
        mServiceAdapter.setHide(7);
//        mServiceAdapter.setHide(8);
        mServiceAdapter.setHide(9);
        mServiceAdapter.setHide(10);
        mServiceAdapter.setHide(12);
//        mServiceAdapter.setHide(11);

        if (UserManager.getInstance(getContext()).isLogin()) {
            homePage.setVisibility(View.VISIBLE);
            NewUserInfo info = UserManager.getInstance(getContext()).getNewUserInfo();
            supId = UserManager.getInstance(getContext()).getNewUserInfo().getFacilitatorId();
            code = info.getProfession().toUpperCase();
            Log.e("zhiye", code);
            switch (code) {
                case "99C06C5A-DDB8-46A1-B860-CD1227B4DB68"://酒店
                    mServiceAdapter.setShow(0);
                    mServiceAdapter.setShow(3);
                    mServiceAdapter.setShow(12);

                    infoType = 1;
                    break;
                case "2526D327-B0AE-4D88-922E-1F7A91722422"://婚车
                    mServiceAdapter.setShow(0);
//                    mServiceAdapter.setShow(4);
//                    mServiceAdapter.setShow(5);
                    mServiceAdapter.setShow(12);
//                    clearRecord.setVisibility(View.VISIBLE);
                    infoType = 1;
                    break;
                case "F209497C-2F2E-4394-AF20-312ED665F67A"://车手
                    mServiceAdapter.setShow(4);
                    mServiceAdapter.setShow(5);
                    mServiceAdapter.setShow(12);

                    homePage.setVisibility(View.GONE);
                    infoType = 2;
                    break;
                case "70CD854E-D943-4607-B993-91912329C61E"://用户（新人）
//                    mServiceAdapter.setShow(6);
//                    mServiceAdapter.setShow(7);
                    homePage.setVisibility(View.GONE);
//                    mServiceAdapter.setShow(11);
                    infoType = 2;
                    identType = 1;
                    break;
                case "5C1D8DA0-9BB6-4CA0-8801-6EA3E187884F"://摄像
                case "41A3BF32-BBB1-4957-9914-50E17E96795B"://摄影
//                    mServiceAdapter.setShow(6);
//                    mServiceAdapter.setShow(7);
//                    clearRecord.setVisibility(View.VISIBLE);
                    mServiceAdapter.setShow(0);
                    mServiceAdapter.setShow(12);
                    infoType = 1;
                    break;
                case "7DC8EDF8-A068-400F-AFD0-417B19DB3C7C"://婚庆
                case "9FFDE235-61BF-408B-8C35-AE76D9113169"://婚庆员工
                    mServiceAdapter.setShow(0);
                    mServiceAdapter.setShow(9);
                    mServiceAdapter.setShow(12);
//                    clearRecord.setVisibility(View.VISIBLE);
                    infoType = 1;
                    break;
                default://其他
                    mServiceAdapter.setShow(0);
                    mServiceAdapter.setShow(12);
//                    clearRecord.setVisibility(View.VISIBLE);
                    infoType = 1;
                    break;

            }
            name.setText(info.getName());
            profession.setText(UserManager.getInstance(getContext()).getProfession(info.getProfession()));
            fans.setText(info.getFansNumber()+"");
            focus.setText(info.getFollowNumber()+"");
            Glide.with(getContext()).load(info.getHeadportrait()).into(logo);
            Glide.with(getContext()).load(identType == 0? R.mipmap.ic_app_gold_sup:R.mipmap.ic_app_gold_news).into(identImg);
        } else {
            homePage.setVisibility(View.GONE);
            name.setText("点击登录");
            profession.setText("新人");
            fans.setText("0");
            focus.setText("0");
            Glide.with(getContext()).load(R.drawable.logo_place).into(logo);
        }
        mServiceAdapter.refresh();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_user_logo://编辑个人资料
            case R.id.tv_user_name://编辑个人资料
                if (!UserManager.getInstance(getContext()).isLogin()) {
                    startActivity(new Intent(getContext(), NewLoginActivity.class));
                } else {
                    if (infoType == 1) {
                        startActivity(new Intent(getContext(), EditSupInfoActivity.class));
                    } else {
                        startActivity(new Intent(getContext(), UserInfoActivity.class));
                    }
                }
                break;
            case R.id.iv_setting://设置
                startActivity(new Intent(getContext(), SettingActivity.class));
                break;
            case R.id.switch_account://切换账号
                if (!UserManager.getInstance(getContext()).isLogin()) {
                    startActivity(new Intent(getContext(), NewLoginActivity.class));
                } else {
                    startActivity(new Intent(getContext(), SwitchAccountActivity.class));
                }
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
                    startActivity(new Intent(getContext(), NewOrderActivity.class));
                }
                break;
            case R.id.ll_money://钱包
                if (UserManager.getInstance(getContext()).checkLogin()) {
                    startActivity(new Intent(getContext(), WalletActivity.class));
                }
                break;
            case R.id.tv_focus://我的关注
                if (UserManager.getInstance(getContext()).checkLogin()) {
                    startActivity(new Intent(getContext(), FocusActivity.class).putExtra("type",0));
                }
                break;
            case R.id.tv_fans://我的粉丝
                if (UserManager.getInstance(getContext()).checkLogin()) {
                    startActivity(new Intent(getContext(), FocusActivity.class).putExtra("type",1));
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
        oks.setTitle("送您一份新婚礼，快去成婚纪APP领取!");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://www.chenghunji.com/Download/User/chenghunji.apk");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("婚庆、婚纱，全部花多少返多少！婚礼对戒等更多豪礼速来领!");
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
                                    startActivity(new Intent(getContext(), ComInviteMainActivity.class).putExtras(bundle));
                                }else {//Vip
                                    startActivity(new Intent(getContext(), VipInviteMainActivity.class).putExtras(bundle));
                                }
                            }else {
                                Toast.makeText(getContext(), result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
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
