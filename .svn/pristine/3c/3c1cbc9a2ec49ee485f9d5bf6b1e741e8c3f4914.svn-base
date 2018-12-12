package com.dikai.chenghunjiclient.fragment.store;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.store.HotelPicAdapter;
import com.dikai.chenghunjiclient.bean.BeanGetSupList;
import com.dikai.chenghunjiclient.citypicker.DBManager;
import com.dikai.chenghunjiclient.entity.ResultGetSupplierInfo;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyRecyclerView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarInfoFragment extends Fragment {

    private TextView intro;
    private TextView address;
    private TextView num;
    private RecyclerView mRecycler;
    private HotelPicAdapter mAdater;

    public CarInfoFragment() {
        // Required empty public constructor
    }

    public static CarInfoFragment newInstance(String id) {
        Bundle args = new Bundle();
        args.putString("id",id);
        CarInfoFragment fragment = new CarInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_car, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        intro = (TextView) view.findViewById(R.id.fragment_car_info_intro);
        address = (TextView) view.findViewById(R.id.fragment_car_info_address);
        num = (TextView) view.findViewById(R.id.fragment_car_info_num);
        mRecycler = (RecyclerView) view.findViewById(R.id.fragment_car_info_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecycler.setLayoutManager(manager);
        mAdater = new HotelPicAdapter(getContext());
        mRecycler.setAdapter(mAdater);
        mRecycler.setNestedScrollingEnabled(false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void setData(ResultGetSupplierInfo result){
        intro.setText(result.getBriefinTroduction());
        if(result.getRegion() != null && !"".equals(result.getRegion())){
            address.setText(DBManager.getInstance(getContext()).getCityName(result.getRegion()));
        }else {
            address.setText("未知");
        }
        num.setText(result.getData().size() + "");
        mAdater.setSupId(result.getSupplierID());
        mAdater.refresh(result.getData());
    }

}