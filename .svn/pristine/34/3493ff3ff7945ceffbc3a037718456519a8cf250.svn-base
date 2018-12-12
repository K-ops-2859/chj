package com.dikai.chenghunjiclient.fragment.plan;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.plan.ManPlanAdapter;
import com.dikai.chenghunjiclient.entity.ManPlanBean;
import com.dikai.chenghunjiclient.entity.PlanCarBean;
import com.dikai.chenghunjiclient.entity.ResultGetFlow;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManPlanFragment extends Fragment {

    private static final int CALL_REQUEST_CODE = 120;
    private MyLoadRecyclerView mRecyclerView;
    private ManPlanAdapter mAdapter;
    private ResultGetFlow result;
    private Intent intent;

    public ManPlanFragment() {
        // Required empty public constructor
    }

    public static ManPlanFragment newInstance(ResultGetFlow result) {
        Bundle args = new Bundle();
        args.putSerializable("bean",result);
        ManPlanFragment fragment = new ManPlanFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_man_plan, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {

            result = (ResultGetFlow) getArguments().getSerializable("bean");
            mRecyclerView = (MyLoadRecyclerView)view.findViewById(R.id.fragment_man_plan_recycler);
            mAdapter = new ManPlanAdapter(getContext());
            mAdapter.setCallListener(new ManPlanAdapter.OnCallListener() {
                @Override
                public void onClick(ManPlanBean bean) {
                    intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + bean.getPhone()));
                    request();
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
        }catch (Exception e){
            Log.e("",e.toString());
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refresh();
    }

    private void refresh() {
//        String logo = "http://121.42.156.151:93/FileGain.aspx?fi=0ed1c989-b2b3-43e2-9ca6-0d24009d4f4c&it=2";
//        List<ManPlanBean> list = new ArrayList<>();
//        list.add(new ManPlanBean("小花",logo,"小小小","15764226604","花艺师","11111"));
//        list.add(new ManPlanBean("小花1",logo,"阿斯达多","15764226604","阿萨德","11111"));
//        list.add(new ManPlanBean("小花2",logo,"阿斯达所多","15764226604","分的","11111"));
//        list.add(new ManPlanBean("小花3",logo,"润发发","15764226604","法国人","11111"));
//        list.add(new ManPlanBean("小花4",logo,"方位服务","15764226604","多舒服","11111"));
//        list.add(new ManPlanBean("小花5",logo,"去安抚","15764226604","额额","11111"));
//        list.addAll(result.getSuppOrderData());
//
//        List<PlanCarBean> list2 = new ArrayList<>();
//        list2.add(new PlanCarBean("宝马","钻石黑","2"));
//        list2.add(new PlanCarBean("保时捷","冰川白","4"));
//        list2.add(new PlanCarBean("玛莎拉蒂","胭脂红","4"));
//        list2.add(new PlanCarBean("法拉利","宝石蓝","4"));
//        list2.addAll(result.getDriverData());
//        mAdapter.setCarList(list2);
//        mAdapter.refresh(list);
        mAdapter.setCarList(result.getDriverData());
        mAdapter.refresh(result.getSuppOrderData());
        mRecyclerView.stopLoad();
    }

    private void request() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请CALL_PHONE权限
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, CALL_REQUEST_CODE);
        } else {
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode, grantResults);
    }

    private void doNext(int requestCode, int[] grantResults) {
        if (requestCode == CALL_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivity(intent);
            } else {
                Toast.makeText(getContext(), "已禁止", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
