package com.dikai.chenghunjiclient.fragment.ad;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dikai.chenghunjiclient.MainActivity;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.me.ShareAppActivity;
import com.dikai.chenghunjiclient.activity.register.LoginActivity;
import com.dikai.chenghunjiclient.activity.store.BoomActivity;
import com.dikai.chenghunjiclient.activity.store.CaigoujieActivity;
import com.dikai.chenghunjiclient.activity.store.HotelADActivity;
import com.dikai.chenghunjiclient.activity.store.WeddingStoreActivity;
import com.dikai.chenghunjiclient.activity.wedding.FreeWedActivity;
import com.dikai.chenghunjiclient.activity.wedding.InviteWedActivity;
import com.dikai.chenghunjiclient.activity.wedding.InviteWeddingActivity;
import com.dikai.chenghunjiclient.activity.wedding.MakeProjectActivity;
import com.dikai.chenghunjiclient.activity.wedding.WeddingAssureActivity;
import com.dikai.chenghunjiclient.adapter.ad.AdListAdapter;
import com.dikai.chenghunjiclient.bean.BeanNone;
import com.dikai.chenghunjiclient.entity.NewAdBean;
import com.dikai.chenghunjiclient.entity.ResultGetNewAd;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.google.gson.Gson;

/**
 * A simple {@link Fragment} subclass.
 */
public class ADFragment extends Fragment {

    private MyLoadRecyclerView mRecyclerView;
    private AdListAdapter mAdapter;

    public ADFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ad, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (MyLoadRecyclerView) view.findViewById(R.id.recycler);
        mRecyclerView.setLoadChangeListener(new MyLoadRecyclerView.OnLoadChangeListener() {
            @Override
            public void onRefresh() {
                refresh();
            }

            @Override
            public void onLoadMore() {

            }
        });
        mAdapter = new AdListAdapter(getContext());
        mAdapter.setOnItemClickListener(new AdListAdapter.OnItemClickListener() {
            @Override
            public void onClick(NewAdBean bean) {
                action(bean);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        refresh();
    }

    private void refresh(){
        getList();
    }
//    邀请好友办婚礼    —————YQBHL
//    免费办婚礼          —————FreeBHL
//    分享APP赚现金 —————ShareApp
//    免费领爆米花 —————-FreeBMH
//    婚礼担保————————HLDB
//    我要出方案 ——————ChuFA
//    共享方案 ———————GongxianFA
//    婚礼返还———————-HunLiFH
//    酒店活动———————-JDHD
//    婚嫁采购节———————-HJCGJ
    private void action(NewAdBean bean){
        if("YQBHL".equals(bean.getActivityCode())){
            startActivity(new Intent(getContext(), InviteWedActivity.class));
        }else if("FreeBHL".equals(bean.getActivityCode())){
            startActivity(new Intent(getContext(), FreeWedActivity.class));
        }else if("ShareApp".equals(bean.getActivityCode())){
            startActivity(new Intent(getContext(), ShareAppActivity.class));
        }else if("FreeBMH".equals(bean.getActivityCode())){
            if(!UserManager.getInstance(getContext()).isLogin()){
                startActivity(new Intent(getContext(), LoginActivity.class));
            }else{
                startActivity(new Intent(getContext(), BoomActivity.class));
            }
        }else if("HLDB".equals(bean.getActivityCode())){
            startActivity(new Intent(getContext(), WeddingAssureActivity.class));
        }else if("ChuFA".equals(bean.getActivityCode())){
            startActivity(new Intent(getContext(), MakeProjectActivity.class));
        }else if("JDHD".equals(bean.getActivityCode())){
            startActivity(new Intent(getContext(), HotelADActivity.class));
        }else if("HunLiFH".equals(bean.getActivityCode())){
            if(!UserManager.getInstance(getContext()).isLogin()){
                startActivity(new Intent(getContext(), LoginActivity.class));
            }else{
                startActivity(new Intent(getContext(), WeddingStoreActivity.class));
            }
        }else if("HJCGJ".equals(bean.getActivityCode())){
            if(!UserManager.getInstance(getContext()).isLogin()){
                startActivity(new Intent(getContext(), LoginActivity.class));
            }else{
                startActivity(new Intent(getContext(), CaigoujieActivity.class));
            }
        }else {
            Toast.makeText(getContext(), "参与该活动,请更新版本!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取福利列表
     */
    private void getList() {
        NetWorkUtil.setCallback("HQOAApi/GetWeChatActivityList",
                new BeanNone(),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(String respose) {
                        mRecyclerView.stopLoad();
                        Log.e("返回值",respose);
                        try {
                            ResultGetNewAd result = new Gson().fromJson(respose, ResultGetNewAd.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                mAdapter.refresh(result.getData());
                                if(result.getData().size() == 0){
                                    mRecyclerView.setHasData(false);
                                }else {
                                    mRecyclerView.setHasData(true);
                                }
                            } else {
                                Toast.makeText(getContext(), result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError(String e) {
                        mRecyclerView.stopLoad();
                    }
                });

    }
}
