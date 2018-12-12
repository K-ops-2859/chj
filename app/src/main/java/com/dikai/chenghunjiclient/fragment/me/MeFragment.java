package com.dikai.chenghunjiclient.fragment.me;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.discover.MyDynamicActivity;
import com.dikai.chenghunjiclient.activity.me.ClearActivity;
import com.dikai.chenghunjiclient.activity.me.EditRoomActivity;
import com.dikai.chenghunjiclient.activity.me.HotelEditInfoActivity;
import com.dikai.chenghunjiclient.activity.me.MyCarActivity;
import com.dikai.chenghunjiclient.activity.me.MyCaseActivity;
import com.dikai.chenghunjiclient.activity.me.MyCollectActivity;
import com.dikai.chenghunjiclient.activity.me.MyOrderActivity;
import com.dikai.chenghunjiclient.activity.me.MyTeamActivity;
import com.dikai.chenghunjiclient.activity.me.MyVideoActivity;
import com.dikai.chenghunjiclient.activity.me.MyWalletActivity;
import com.dikai.chenghunjiclient.activity.me.SettingActivity;
import com.dikai.chenghunjiclient.activity.me.ShareAppActivity;
import com.dikai.chenghunjiclient.activity.me.SupEditInfoActivity;
import com.dikai.chenghunjiclient.activity.me.UserInfoActivity;
import com.dikai.chenghunjiclient.activity.me.WeddingPhotoActivity;
import com.dikai.chenghunjiclient.activity.me.XinRenPhotoActivity;
import com.dikai.chenghunjiclient.activity.register.LoginActivity;
import com.dikai.chenghunjiclient.activity.wedding.ConvertGiftActivity;
import com.dikai.chenghunjiclient.bean.BeanCustomerInfoList;
import com.dikai.chenghunjiclient.entity.CustomerInfoData;
import com.dikai.chenghunjiclient.entity.UserInfo;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.google.gson.Gson;
import com.joooonho.SelectableRoundedImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends Fragment implements View.OnClickListener {

    private LinearLayout mTopLayout;
    private LinearLayout mCarLayout;
    private LinearLayout clearLayout;
    private RelativeLayout mHotelLayout;
    private RelativeLayout mInfoLayout;
    private LinearLayout hotelInfo;
    private ImageView setting;
    private TextView mInfoText;
    private SelectableRoundedImageView logo;
    private TextView name;
    private TextView identity;
    private int infoType;

    private LinearLayout videoPhoto;
    private RelativeLayout logoLayout;
    private String code;
    private CustomerInfoData.DataList mData;

    public MeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
        logo = (SelectableRoundedImageView) view.findViewById(R.id.iv_user_logo);
        name = (TextView) view.findViewById(R.id.tv_user_name);
        identity = (TextView) view.findViewById(R.id.tv_identity);
        setting = (ImageView) view.findViewById(R.id.iv_setting);
        mTopLayout = (LinearLayout) view.findViewById(R.id.top_layout);
        mCarLayout = (LinearLayout) view.findViewById(R.id.car_layout);
        mHotelLayout = (RelativeLayout) view.findViewById(R.id.rl_feast_manager);
        mInfoLayout = (RelativeLayout) view.findViewById(R.id.rl_hotel_data);
        logoLayout = (RelativeLayout) view.findViewById(R.id.logo_layout);
        hotelInfo = (LinearLayout) view.findViewById(R.id.rl_hotel_info);
        videoPhoto = (LinearLayout) view.findViewById(R.id.video_photo);
        mInfoText = (TextView) view.findViewById(R.id.rl_hotel_text);
        hotelInfo.setOnClickListener(this);
        mHotelLayout.setOnClickListener(this);
        logo.setOnClickListener(this);
        setting.setOnClickListener(this);
        mInfoLayout.setOnClickListener(this);
        clearLayout = (LinearLayout) view.findViewById(R.id.ll_close_account);
        clearLayout.setOnClickListener(this);
        view.findViewById(R.id.ll_my_arrange).setOnClickListener(this);
        view.findViewById(R.id.rl_my_car).setOnClickListener(this);
        view.findViewById(R.id.rl_my_car_team).setOnClickListener(this);
        view.findViewById(R.id.rl_collection).setOnClickListener(this);
        view.findViewById(R.id.rl_hotel_data).setOnClickListener(this);
        view.findViewById(R.id.rl_share).setOnClickListener(this);
        view.findViewById(R.id.rl_event).setOnClickListener(this);
        view.findViewById(R.id.ll_login).setOnClickListener(this);
        view.findViewById(R.id.rl_gift).setOnClickListener(this);
        view.findViewById(R.id.rl_wallet).setOnClickListener(this);
        view.findViewById(R.id.rl_video).setOnClickListener(this);
        view.findViewById(R.id.rl_photo).setOnClickListener(this);
        view.findViewById(R.id.my_order).setOnClickListener(this);
        //view.findViewById(R.id.rl_get_gift).setOnClickListener(this);
//        view.findViewById(R.id.rl_invite_download).setOnClickListener(this);
     //   view.findViewById(R.id.rl_feedback).setOnClickListener(this);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refresh();
    }

    private void refresh() {
        if (UserManager.getInstance(getContext()).isLogin()) {
            refreshUser();
        }
        hotelInfo.setVisibility(View.GONE);
        videoPhoto.setVisibility(View.GONE);
        if (UserManager.getInstance(getContext()).isLogin()) {
            clearLayout.setVisibility(View.VISIBLE);
            mTopLayout.setVisibility(View.GONE);
            mCarLayout.setVisibility(View.GONE);
            mHotelLayout.setVisibility(View.GONE);
            UserInfo info = UserManager.getInstance(getContext()).getUserInfo();
            code = info.getProfession();

            Log.e("zhiye", code);
            switch (code) {
                case "SF_1001000"://酒店
                    mTopLayout.setVisibility(View.VISIBLE);
                    clearLayout.setVisibility(View.GONE);
                    hotelInfo.setVisibility(View.VISIBLE);
                    mHotelLayout.setVisibility(View.VISIBLE);
                    mInfoText.setText("酒店资料");
                    infoType = 0;
                    break;
                case "SF_2001000"://婚车
                    mTopLayout.setVisibility(View.VISIBLE);
                    mCarLayout.setVisibility(View.VISIBLE);
                    mHotelLayout.setVisibility(View.GONE);
                    mInfoText.setText("个人资料");
                    Log.e("执行至此", code);
                    infoType = 1;
                    break;
                case "SF_13001000"://车手
                    mTopLayout.setVisibility(View.GONE);
                    mCarLayout.setVisibility(View.VISIBLE);
                    mHotelLayout.setVisibility(View.GONE);
                    mInfoText.setText("个人资料");
                    infoType = 2;
                    break;
                case "SF_12001000"://用户（新人）
                    videoPhoto.setVisibility(View.VISIBLE);
                    mTopLayout.setVisibility(View.GONE);
                    mCarLayout.setVisibility(View.GONE);
                    mHotelLayout.setVisibility(View.GONE);
                    mInfoText.setText("个人资料");
                    infoType = 2;
                    break;
                case "SF_4001000"://摄像
                case "SF_5001000"://摄影
                    videoPhoto.setVisibility(View.VISIBLE);
                    mTopLayout.setVisibility(View.VISIBLE);
                    mCarLayout.setVisibility(View.GONE);
                    mHotelLayout.setVisibility(View.GONE);
                    mInfoText.setText("个人资料");
                    infoType = 1;
                    break;
                case "SF_14001000"://婚庆
                default://其他
                    mTopLayout.setVisibility(View.VISIBLE);
                    mCarLayout.setVisibility(View.GONE);
                    mHotelLayout.setVisibility(View.GONE);
                    mInfoText.setText("个人资料");
                    infoType = 1;
                    break;

            }

//            String midentity = UserManager.getInstance(getContext()).getAllIdentMap().get(code);
//            float nameLength = getTextViewLength(name, info.getTrueName());
//            float identityLength = getTextViewLength(identity, " • " + midentity);
//            int screenWidth = DensityUtil.getScreenWidth(getContext());
//            if (nameLength + identityLength > (screenWidth - logoLayout.getLayoutParams().width)) {
//                name.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
//            }
            name.setText(info.getTrueName());
//            identity.setText(" • " + midentity);
//            identity.setVisibility(View.VISIBLE);
//            name.setText(info.getTrueName());
//            identity.setText("•" + UserManager.getInstance(getContext()).getAllIdentMap().get(code));

            Glide.with(getContext()).load(info.getHeadportrait()).into(logo);
        } else {
            logo.setImageResource(R.mipmap.ic_logo_new);
            name.setText("点击登录");
            mInfoText.setText("个人资料");
            identity.setVisibility(View.GONE);
            mTopLayout.setVisibility(View.GONE);
            mCarLayout.setVisibility(View.GONE);
            mHotelLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        System.out.println(code);
        switch (v.getId()) {
            case R.id.iv_setting:
                startActivity(new Intent(getContext(), SettingActivity.class));
                break;
            case R.id.rl_hotel_info://酒店（供应商）资料
                if (!UserManager.getInstance(getContext()).isLogin()) {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                } else {
                    if (infoType == 0) {
                        startActivity(new Intent(getContext(), UserInfoActivity.class));
                    }
                }
                break;
            case R.id.rl_my_car:
                startActivity(new Intent(getContext(), MyCarActivity.class));
                break;
            case R.id.rl_my_car_team:
                startActivity(new Intent(getContext(), MyTeamActivity.class));
                break;
            case R.id.rl_feast_manager:
                startActivity(new Intent(getContext(), EditRoomActivity.class));
                break;
            case R.id.rl_collection:
                if (UserManager.getInstance(getContext()).checkLogin()) {
                    startActivity(new Intent(getContext(), MyCollectActivity.class));
                }
                break;
            case R.id.ll_close_account:
                if (UserManager.getInstance(getContext()).checkLogin()) {
                    startActivity(new Intent(getContext(), ClearActivity.class));
                }
                break;
            case R.id.ll_my_arrange:
                if (UserManager.getInstance(getContext()).checkLogin()) {
                    startActivity(new Intent(getContext(), MyCaseActivity.class));
                }
                break;
            case R.id.rl_share:
                if (UserManager.getInstance(getContext()).checkLogin()) {
                    startActivity(new Intent(getContext(), ShareAppActivity.class));
                }
                break;
            case R.id.rl_gift:
                if (UserManager.getInstance(getContext()).checkLogin()) {
                    startActivity(new Intent(getContext(), ConvertGiftActivity.class));
                }
                break;
            case R.id.rl_hotel_data://个人资料
                if (UserManager.getInstance(getContext()).checkLogin()) {
                    if (infoType == 0) {
                        startActivity(new Intent(getContext(), HotelEditInfoActivity.class));
                    } else if (infoType == 1) {
                        startActivity(new Intent(getContext(), SupEditInfoActivity.class));
                    } else {
                        startActivity(new Intent(getContext(), UserInfoActivity.class));
                    }
                }
                break;
            case R.id.rl_event:
                if (UserManager.getInstance(getContext()).checkLogin()) {
                    startActivity(new Intent(getContext(), MyDynamicActivity.class));
                }
                break;
            case R.id.my_order:
                if (UserManager.getInstance(getContext()).checkLogin()) {
                    startActivity(new Intent(getContext(), MyOrderActivity.class));
                }
                break;
            case R.id.ll_login:
                if (!UserManager.getInstance(getContext()).isLogin()) {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
                break;
            case R.id.rl_wallet:
                if (!UserManager.getInstance(getContext()).isLogin()) {
                    //TODO：我的钱包
                    startActivity(new Intent(getContext(), LoginActivity.class));
                } else {
                    startActivity(new Intent(getContext(), MyWalletActivity.class));
                }
                break;
            case R.id.rl_video:
                if (!UserManager.getInstance(getContext()).isLogin()) {
                    //TODO：婚礼视频
                    startActivity(new Intent(getContext(), LoginActivity.class));
                } else {
                    if (code.equals("SF_12001000")) {
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
            case R.id.rl_photo:
                if (!UserManager.getInstance(getContext()).isLogin()) {
                    //TODO：婚礼照片
                    startActivity(new Intent(getContext(), LoginActivity.class));
                } else {
                    if (code.equals("SF_12001000")) {
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
            default:
                break;
//            case R.id.rl_get_gift:
//                if(UserManager.getInstance(getContext()).checkLogin()){
//                    startActivity(new Intent(getContext(), InviteActivity.class));
//                }
//                break;
//            case R.id.rl_feedback:
//
//                startActivity(new Intent(getContext(), AddGiftActivity.class));
//                break;
        }
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
//        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("成婚纪——婚礼原来如此简单");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://www.chenghunji.com/Download/Index");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("一站式婚礼筹备，尽在成婚纪");
//        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("");//确保SDcard下面存在此张图片
//        oks.setImageUrl("http://www.chenghunji.com/img/index/B_LOGO_01.png");
        oks.setImageUrl("http://www.chenghunji.com/Download/User/wechat_red_packet.png");
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://www.chenghunji.com/Download/Index");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("成婚纪——婚礼原来如此简单");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("成婚纪");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://www.chenghunji.com/");
//        oks.setPlatform(WechatMoments.NAME);//微信朋友圈
//        oks.setPlatform(Wechat.NAME);//
        // 启动分享GUI
        oks.show(getContext());
    }

    /**
     * 用户选择供应商
     */
    private void refreshUser() {
        String userID = UserManager.getInstance(getContext()).getUserInfo().getUserID();
        NetWorkUtil.setCallback("User/GetCustomerInfoList",
                new BeanCustomerInfoList(userID), new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(String respose) {
                        try {
                            CustomerInfoData infoData = new Gson().fromJson(respose, CustomerInfoData.class);
                            if (infoData.getMessage().getCode().equals("200")) {
                                List<CustomerInfoData.DataList> dataList = infoData.getData();
                                System.out.println("用户选择供应商" + dataList.size());
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
     * 事件总线刷新
     *
     * @param bean
     */
    @Subscribe
    public void onEventMainThread(final EventBusBean bean) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (bean.getType() == Constants.USER_INFO_CHANGE) {
                    refresh();
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
