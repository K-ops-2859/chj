package com.dikai.chenghunjiclient.fragment.wedding;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.register.LoginActivity;
import com.dikai.chenghunjiclient.activity.wedding.FreeWedActivity;
import com.dikai.chenghunjiclient.activity.wedding.InviteActivity1;
import com.dikai.chenghunjiclient.activity.wedding.InviteWedActivity;
import com.dikai.chenghunjiclient.activity.wedding.MakeProjectActivity;
import com.dikai.chenghunjiclient.activity.wedding.SignBillWelfareActivity;
import com.dikai.chenghunjiclient.activity.wedding.WedCaseActivity;
import com.dikai.chenghunjiclient.activity.wedding.WedProjectActivity;
import com.dikai.chenghunjiclient.activity.wedding.WeddingAssureActivity;
import com.dikai.chenghunjiclient.activity.wedding.WeddingKnowActivity;
import com.dikai.chenghunjiclient.bean.BeanUserInfo;
import com.dikai.chenghunjiclient.entity.ResultIsNewsAddCustom;
import com.dikai.chenghunjiclient.entity.UserInfo;
import com.dikai.chenghunjiclient.entity.WeddingImageData;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeddingFragment extends Fragment implements View.OnClickListener {

    private boolean HasAddCustom = false;
    private boolean hasGetState = false;
    private int state;
    private ImageView ivCase;

    public WeddingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wedding, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
        ivCase = (ImageView) view.findViewById(R.id.item_case_img);

        view.findViewById(R.id.fragment_wedding_my_project).setOnClickListener(this);
        view.findViewById(R.id.fragment_wedding_show_project).setOnClickListener(this);
        view.findViewById(R.id.fragment_wedding_prize).setOnClickListener(this);
        view.findViewById(R.id.fragment_wedding_danbao).setOnClickListener(this);
        view.findViewById(R.id.fragment_wedding_fuli).setOnClickListener(this);
        view.findViewById(R.id.fragment_wedding_xuzhi).setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getCustomState(false);
        getImage();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragment_wedding_my_project:
                if(!UserManager.getInstance(getContext()).isLogin()){
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }else if(hasGetState){
                    startActivity(HasAddCustom? new Intent(getContext(), WedProjectActivity.class).putExtra("state",state):
                            new Intent(getContext(), MakeProjectActivity.class));
                }else {
                    getCustomState(true);
                }
                break;
            case R.id.fragment_wedding_show_project:
                //TODO:方案商店
                startActivity(new Intent(getContext(), WedCaseActivity.class));
                break;
            case R.id.fragment_wedding_prize://免费办婚礼
                if (!UserManager.getInstance(getContext()).isLogin()) {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                } else {
//                    startActivity(new Intent(getContext(), InviteActivity1.class));
                    startActivity(new Intent(getContext(), FreeWedActivity.class));
                }
            break;
            case R.id.fragment_wedding_danbao:
                if (!UserManager.getInstance(getContext()).isLogin()) {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                } else {
                    startActivity(new Intent(getContext(), WeddingAssureActivity.class));
//                startActivity(new Intent(getContext(), GuaranteeActivity.class));
                }
                break;
            case R.id.fragment_wedding_fuli://邀请结婚
                if (!UserManager.getInstance(getContext()).isLogin()) {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                } else {
                    startActivity(new Intent(getContext(), InviteWedActivity.class));
//                    startActivity(new Intent(getContext(), SignBillWelfareActivity.class));
                }
                break;
            case R.id.fragment_wedding_xuzhi:
                if (!UserManager.getInstance(getContext()).isLogin()) {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                } else {
                    startActivity(new Intent(getContext(), WeddingKnowActivity.class));
                }
                break;
            default:
                break;
        }
    }

    private void getCustomState(final boolean isRetry) {
        if(UserManager.getInstance(getContext()).isLogin()){
            UserInfo userInfo = UserManager.getInstance(getContext()).getUserInfo();
            NetWorkUtil.setCallback("User/IsNewPeopleAddCustom",
                    new BeanUserInfo(userInfo.getUserID()),
                    new NetWorkUtil.CallBackListener() {
                        @Override
                        public void onFinish(final String respose) {
                            Log.e("返回值",respose);
                            try {
                                ResultIsNewsAddCustom result = new Gson().fromJson(respose, ResultIsNewsAddCustom.class);
                                if ("200".equals(result.getMessage().getCode())) {
                                    state = result.getCustomState();
                                    hasGetState = true;
                                    if("0".equals(result.getNewPeopleCustomID())){
                                        //未编辑
                                        HasAddCustom = false;
                                        if(isRetry)
                                            startActivity(new Intent(getContext(), MakeProjectActivity.class));
                                    }else {
                                        //已编辑
                                        HasAddCustom = true;
                                        if(isRetry){
                                            startActivity(new Intent(getContext(), WedProjectActivity.class)
                                                    .putExtra("state",state));
                                        }
                                    }
                                } else {
                                    Toast.makeText(getContext(), "" + result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Log.e("json解析出错",e.toString());
                                //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(final String e) {
                            Log.e("网络出错",e.toString());
                        }
                    });
        }
    }

    private void getImage() {
        NetWorkUtil.setCallback("User/GetBHImg", null, new NetWorkUtil.CallBackListener() {
            @Override
            public void onFinish(String respose) {
                WeddingImageData data = new Gson().fromJson(respose, WeddingImageData.class);
                if (data.getMessage().getCode().equals("200")) {
                    Glide.with(getContext()).load(data.getImgURL()).into(ivCase);
                    data.getImgURL();
                }
            }

            @Override
            public void onError(String e) {

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
            public void run() {
               if(bean.getType() == Constants.USER_INFO_CHANGE ||
                       bean.getType() == Constants.WED_PUBLISH){
                    if(UserManager.getInstance(getContext()).isLogin()){
                        getCustomState(false);
                    }
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
