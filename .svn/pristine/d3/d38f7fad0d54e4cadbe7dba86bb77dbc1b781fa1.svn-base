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

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.ad.ADListActivity;
import com.dikai.chenghunjiclient.activity.ad.CustomActivity;
import com.dikai.chenghunjiclient.activity.newregister.NewLoginActivity;
import com.dikai.chenghunjiclient.activity.store.CorpInfoActivity;
import com.dikai.chenghunjiclient.activity.store.HotelInfoActivity;
import com.dikai.chenghunjiclient.adapter.ad.ADItemAdapter;
import com.dikai.chenghunjiclient.adapter.ad.NewAdListAdapter;
import com.dikai.chenghunjiclient.bean.BeanGetNewAD;
import com.dikai.chenghunjiclient.entity.NewAdHomeList;
import com.dikai.chenghunjiclient.entity.ResultGetNewADList;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ADBeanFragment extends Fragment {

    private int pageIndex = 1;
    private int pageCount = 20;
    private String id;
    private String banner;
    private MyLoadRecyclerView mRecyclerView;
    private NewAdListAdapter mAdapter;

    public ADBeanFragment() {
        // Required empty public constructor
    }

    public static ADBeanFragment newInstance(String id,String banner) {
        Bundle args = new Bundle();
        args.putString("id",id);
        args.putString("banner",banner);
        ADBeanFragment fragment = new ADBeanFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_adbean, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        id = getArguments().getString("id");
        banner = getArguments().getString("banner");
        mRecyclerView = (MyLoadRecyclerView) view.findViewById(R.id.recycler);
        mAdapter = new NewAdListAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLoadChangeListener(new MyLoadRecyclerView.OnLoadChangeListener() {
            @Override
            public void onRefresh() {
                refresh();
            }

            @Override
            public void onLoadMore() {
                pageIndex++;
                getList(pageIndex,pageCount,false);
            }
        });

        mAdapter.setOnItemClickListener(new NewAdListAdapter.OnItemClickListener() {
            @Override
            public void onClick(int type, NewAdHomeList bean) {
                if(type == 0){
                    if(UserManager.getInstance(getContext()).isLogin()){
                        startActivity(new Intent(getContext(), CustomActivity.class).putExtra("code",id));
                    }else {
                        startActivity(new Intent(getContext(), NewLoginActivity.class));
                    }
                }else if(type == 1){
                    Log.e("code",id);
                    if(UserManager.getInstance(getContext()).isLogin()){
                        if("99C06C5A-DDB8-46A1-B860-CD1227B4DB68".equalsIgnoreCase(id)){//酒店
                            startActivity(new Intent(getContext(), HotelInfoActivity.class)
                                    .putExtra("id", bean.getFacilitatorId()).putExtra("userid",bean.getUserId()));
//                }else if("2526D327-B0AE-4D88-922E-1F7A91722422".equals(bean.getSupplierIdentity())){//婚车
//                    context.startActivity(new Intent(context, CarInfoActivity.class)
//                            .putExtra("id", bean.getId()).putExtra("userid",bean.getUserId()));
                        }else if("7DC8EDF8-A068-400F-AFD0-417B19DB3C7C".equalsIgnoreCase(id)){//婚庆
                            startActivity(new Intent(getContext(), CorpInfoActivity.class)
                                    .putExtra("id", bean.getFacilitatorId())
                                    .putExtra("type",1).putExtra("userid",bean.getUserId()));
                        }else {//其他
                            startActivity(new Intent(getContext(), CorpInfoActivity.class)
                                    .putExtra("id", bean.getFacilitatorId())
                                    .putExtra("type",0)
                                    .putExtra("userid",bean.getUserId()));
                        }
                    }else {
                        startActivity(new Intent(getContext(), NewLoginActivity.class));
                    }
                }else if(type == 2){
                    startActivity(new Intent(getContext(), ADListActivity.class)
                            .putExtra("id",bean.getFacilitatorId())
                            .putExtra("title",bean.getFacilitatorName()));
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        refresh();
    }

    private void refresh(){
        pageIndex = 1;
        pageCount = 20;
        getList(pageIndex,pageCount,true);
    }

    /**
     * 获取记录
     */
    private void getList(int PageIndex, int PageCount, final boolean isRefresh){
        NetWorkUtil.setCallback("HQOAApi/GetFacilitatorActivityCoverMapList",
                new BeanGetNewAD(id,PageIndex, PageCount),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mRecyclerView.stopLoad();
                        Log.e("返回值",respose);
                        try {
                            ResultGetNewADList result = new Gson().fromJson(respose, ResultGetNewADList.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                if(isRefresh){
                                    List<Object> list = new ArrayList<>();
                                    list.add(banner);
                                    list.addAll(result.getData());
                                    mAdapter.refresh(list);
                                    if(result.getData().size() == 0){
                                        mRecyclerView.setHasData(false);
                                    }else {
                                        mRecyclerView.setHasData(true);
                                    }
                                }else {
                                    mAdapter.addAll(result.getData());
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
                    public void onError(final String e) {
                        mRecyclerView.stopLoad();
//                        Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
