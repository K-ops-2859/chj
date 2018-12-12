package com.dikai.chenghunjiclient.fragment.store;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.store.CarsAdapter;
import com.dikai.chenghunjiclient.adapter.store.HotelRoomAdapter;
import com.dikai.chenghunjiclient.bean.BeanGetCars;
import com.dikai.chenghunjiclient.bean.BeanGetHotelInfo;
import com.dikai.chenghunjiclient.entity.ResultCar;
import com.dikai.chenghunjiclient.entity.ResultGetAllCar;
import com.dikai.chenghunjiclient.entity.ResultGetRooms;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.google.gson.Gson;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarsFragment extends Fragment {

    private String hotelID;
    private MyLoadRecyclerView mRecyclerView;
    private CarsAdapter mAdapter;

    public CarsFragment() {
        // Required empty public constructor
    }

    public static CarsFragment newInstance(String id) {
        Bundle args = new Bundle();
        args.putString("id",id);
        CarsFragment fragment = new CarsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cars, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hotelID = getArguments().getString("id");
        mRecyclerView = (MyLoadRecyclerView)view.findViewById(R.id.fragment_room_recycler);
        mAdapter = new CarsAdapter(getContext());
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

    private void refresh() {
        getList();
    }

    /**
     * 获取信息
     */
    private void getList(){
        NetWorkUtil.setCallback("User/GetSupperTeamInfo",
                new BeanGetCars(hotelID),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mRecyclerView.stopLoad();
                        Log.e("返回值",respose);
                        try {
                            ResultCar result = new Gson().fromJson(respose, ResultCar.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                if(result.getData() == null || result.getData().size() == 0){
                                    mRecyclerView.setHasData(false);
                                }else {
                                    mRecyclerView.setHasData(true);
                                }
                                mAdapter.refresh(result.getData());
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
