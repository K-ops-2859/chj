package com.dikai.chenghunjiclient.fragment.me;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.me.CreateTeamActivity;
import com.dikai.chenghunjiclient.adapter.me.MyInviteAdapter;
import com.dikai.chenghunjiclient.bean.BeanFire;
import com.dikai.chenghunjiclient.bean.BeanGetInvite;
import com.dikai.chenghunjiclient.entity.GetInviteBean;
import com.dikai.chenghunjiclient.entity.ResultGetInvite;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.google.gson.Gson;

import dmax.dialog.SpotsDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyInviteFragment extends Fragment implements View.OnClickListener {

    private LinearLayout mCreate;
    private SpotsDialog mDialog;
    private MyLoadRecyclerView mRecyclerView;
    private MyInviteAdapter mAdapter;

    public MyInviteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_invite, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDialog = new SpotsDialog(getContext(), R.style.DialogCustom);
        mCreate = (LinearLayout) view.findViewById(R.id.fragment_invite_create);
        mRecyclerView = (MyLoadRecyclerView)view.findViewById(R.id.fragment_invite_recycler);
        mAdapter = new MyInviteAdapter(getContext());
        mAdapter.setOnCarClickListener(new MyInviteAdapter.OnCarClickListener() {
            @Override
            public void onAgree(GetInviteBean bean) {
                fire(bean.getRelaID(),"0",bean.getSupplierID());
            }

            @Override
            public void onRefuse(GetInviteBean bean) {
                fire(bean.getRelaID(),"3",bean.getSupplierID());
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
        mCreate.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refresh();
    }

    @Override
    public void onClick(View v) {
        if(v == mCreate){
            startActivity(new Intent(getContext(), CreateTeamActivity.class));
        }
    }

    private void refresh() {
        getInvite();
    }

    /**
     * 开除
     */
    private void fire(String id, String type, String supID){
        mDialog.show();
        NetWorkUtil.setCallback("User/FiredAndAgreedTeam",
                new BeanFire(id, type, "", supID),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                Toast.makeText(getContext(), "操作成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(),result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 获取邀请
     */
    private void getInvite(){
        NetWorkUtil.setCallback("User/GetUserInviteList",
                new BeanGetInvite(UserManager.getInstance(getContext()).getUserInfo().getUserID(),"1","1","200"),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mRecyclerView.stopLoad();
                        Log.e("返回值",respose);
                        try {
                            ResultGetInvite result = new Gson().fromJson(respose, ResultGetInvite.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                if(result.getData().size() > 0){
                                    mRecyclerView.setHasData(true);
                                }else {
                                    mRecyclerView.setHasData(false);
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
