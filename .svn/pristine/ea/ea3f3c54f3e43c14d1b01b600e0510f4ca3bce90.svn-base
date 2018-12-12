package com.dikai.chenghunjiclient.fragment.discover;


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
import com.dikai.chenghunjiclient.activity.discover.DynamicActivity;
import com.dikai.chenghunjiclient.adapter.discover.TrendsAdapter;
import com.dikai.chenghunjiclient.bean.BeanMyFocusDynamic;
import com.dikai.chenghunjiclient.bean.DiscoverLikeBean;
import com.dikai.chenghunjiclient.bean.DynamicBean;
import com.dikai.chenghunjiclient.entity.DynamicData;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.popcorn.MyLuckyItem;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllTrendsFragment extends Fragment {

    private int pageIndex = 1;
    private int pageCount = 20;
    private MyLoadRecyclerView mRecyclerView;
    private TrendsAdapter mAdapter;
    private LinearLayout placeHolder;
    private SpotsDialog mDialog;
    private int type = 2;

    public AllTrendsFragment() {
        // Required empty public constructor
    }

    public static AllTrendsFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type",type);
        AllTrendsFragment fragment = new AllTrendsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        EventBus.getDefault().register(this);
        return inflater.inflate(R.layout.fragment_all_trends, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        type = getArguments().getInt("type");
        mDialog = new SpotsDialog(getContext(),R.style.DialogCustom);
        placeHolder = (LinearLayout) view.findViewById(R.id.un_login_layout);
        mRecyclerView = (MyLoadRecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setGridLayout(2);
        mRecyclerView.setLoadChangeListener(new MyLoadRecyclerView.OnLoadChangeListener() {
            @Override
            public void onRefresh() {
                refresh();
            }

            @Override
            public void onLoadMore() {
                pageIndex++;
                if(type ==2){
                    if(UserManager.getInstance(getContext()).isLogin()){
                        getList(UserManager.getInstance(getContext()).getNewUserInfo().getUserId(),pageIndex,pageCount,false);
                    }else {
                        getList("00000000-0000-0000-0000-000000000000",pageIndex,pageCount,false);
                    }
                }else {
                    if(UserManager.getInstance(getContext()).isLogin()){
                        getFocusList(UserManager.getInstance(getContext()).getNewUserInfo().getUserId(),pageIndex,pageCount,false);
                    }else {
                        getFocusList("00000000-0000-0000-0000-000000000000",pageIndex,pageCount,false);
                    }
                }
            }
        });
        mAdapter = new TrendsAdapter(getContext());
        mAdapter.setOnItemClickListener(new TrendsAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position, int type, DynamicData.DataList bean) {
                if(type == 0){
                    Intent intent = new Intent(getContext(), DynamicActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("type",bean.getFileType());
                    bundle.putSerializable("data", bean);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else if(type == 1){
                    if(UserManager.getInstance(getContext()).checkLogin()){
                        int state = bean.getState();
                        int num = bean.getGivethumbCount();
                        addAndRemoveLikeData(state == 0 ? 1 : 0, state == 0 ? num+1:num-1,position,bean.getDynamicID()+"");
                    }
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        if(UserManager.getInstance(getContext()).isLogin()){
            mRecyclerView.startRefresh();
        }
        refresh();
    }

    private void refresh(){
        placeHolder.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        pageIndex = 1;
        pageCount = 20;
        if(type ==2){
            if(UserManager.getInstance(getContext()).isLogin()){
                getList(UserManager.getInstance(getContext()).getNewUserInfo().getUserId(),pageIndex,pageCount,true);
            }else {
                getList("00000000-0000-0000-0000-000000000000",pageIndex,pageCount,true);
            }
        }else {
            if(UserManager.getInstance(getContext()).isLogin()){
                getFocusList(UserManager.getInstance(getContext()).getNewUserInfo().getUserId(),pageIndex,pageCount,true);
            }else {
                getFocusList("00000000-0000-0000-0000-000000000000",pageIndex,pageCount,true);
            }
        }
    }

    /**
     * 获取所有动态
     */
    private void getList(String userid, int PageIndex, int PageCount, final boolean isRefresh){
        NetWorkUtil.setCallback("HQOAApi/GetDynamicList",
                new DynamicBean(userid,"00000000-0000-0000-0000-000000000000", 0,PageIndex, PageCount),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mRecyclerView.stopLoad();
                        Log.e("返回值",respose);
                        try {
                            DynamicData result = new Gson().fromJson(respose, DynamicData.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                mRecyclerView.setTotalCount(result.getTotalCount());
                                if(isRefresh){
                                    mAdapter.refresh(result.getData());
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

    /**
     * 获取关注动态
     */
    private void getFocusList(String userid, int PageIndex, int PageCount, final boolean isRefresh){
        NetWorkUtil.setCallback("HQOAApi/GetFollowDynamicList",
                new BeanMyFocusDynamic(userid, 0, PageIndex, PageCount),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mRecyclerView.stopLoad();
                        Log.e("返回值",respose);
                        try {
                            DynamicData result = new Gson().fromJson(respose, DynamicData.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                mRecyclerView.setTotalCount(result.getTotalCount());
                                if(isRefresh){
                                    mAdapter.refresh(result.getData());
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

    private void addAndRemoveLikeData(final int state, final int num, final int position, String objectId) {
        mDialog.show();
        NetWorkUtil.setCallback("HQOAApi/AddDelGivethumb",
                new DiscoverLikeBean(objectId, UserManager.getInstance(getContext()).getNewUserInfo().getUserId()),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(String respose) {
                        try {
                            mDialog.dismiss();
                            Log.e("返回结果", respose);
                            ResultMessage resultMessage = new Gson().fromJson(respose, ResultMessage.class);
                            if (resultMessage.getMessage().getCode().equals("200")) {
                                Log.e("第三步", "=======" + state);
                                mAdapter.itemChange(position, state,num);
                            } else {
                                Toast.makeText(getContext(), resultMessage.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                        }
                    }

                    @Override
                    public void onError(String e) {
                        mDialog.dismiss();
                    }
                });
    }

    @Subscribe
    public void onEvent(final EventBusBean event) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (event.getType() == Constants.USER_INFO_CHANGE) {
                    refresh();
                }else if(event.getType() == Constants.DYNAMIC_PUBLISHED){
                    refresh();
                }else if(event.getType() == Constants.FOCUS_REFRESH_HOME_LIST && type == 3){
                    refresh();
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
