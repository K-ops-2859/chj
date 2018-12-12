package com.dikai.chenghunjiclient.fragment.store;


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
import com.dikai.chenghunjiclient.activity.me.NewRoomInfoActivity;
import com.dikai.chenghunjiclient.adapter.me.NewRoomAdapter;
import com.dikai.chenghunjiclient.adapter.store.HotelRoomAdapter;
import com.dikai.chenghunjiclient.adapter.store.MainStoreAdapter;
import com.dikai.chenghunjiclient.bean.BeanGetHotelInfo;
import com.dikai.chenghunjiclient.bean.BeanID;
import com.dikai.chenghunjiclient.entity.NewUserInfo;
import com.dikai.chenghunjiclient.entity.ResultGetRooms;
import com.dikai.chenghunjiclient.entity.RoomBean;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HotelRoomFragment extends Fragment {
    private String supID;
    private MyLoadRecyclerView mRecyclerView;
    private NewRoomAdapter mAdapter;

    public HotelRoomFragment() {
        // Required empty public constructor
    }

    public static HotelRoomFragment newInstance(String id) {
        Bundle args = new Bundle();
        args.putString("id",id);
        HotelRoomFragment fragment = new HotelRoomFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hotel_room, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        supID = getArguments().getString("id");
        mRecyclerView = (MyLoadRecyclerView)view.findViewById(R.id.fragment_room_recycler);
        mAdapter = new NewRoomAdapter(getContext());
        mAdapter.setOnItemClickListener(new NewRoomAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position, RoomBean bean) {

                startActivity(new Intent(getContext(),NewRoomInfoActivity.class).putExtra("roomid",bean.getBanquetID()));
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLoadChangeListener(new MyLoadRecyclerView.OnLoadChangeListener() {
            @Override
            public void onRefresh() {
                refresh();
            }

            @Override
            public void onLoadMore() {
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refresh();
    }

    public void refresh() {
        getList(supID);
    }

    /**
     * 获取信息
     */
    private void getList(String supId){
        NetWorkUtil.setCallback("HQOAApi/GetBanquetHallList",
                new BeanID(supId),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mRecyclerView.stopLoad();
                        Log.e("返回值",respose);
                        try {
                            ResultGetRooms result = new Gson().fromJson(respose, ResultGetRooms.class);
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
                    public void onError(final String e) {
                        mRecyclerView.stopLoad();
                        Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
