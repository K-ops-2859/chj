package com.dikai.chenghunjiclient.fragment.wedding;


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
import com.dikai.chenghunjiclient.activity.wedding.ProQuestionActivity;
import com.dikai.chenghunjiclient.activity.wedding.WedManInfoActivity;
import com.dikai.chenghunjiclient.adapter.wedding.WedProjectAdapter;
import com.dikai.chenghunjiclient.bean.BeanGetCustom;
import com.dikai.chenghunjiclient.bean.BeanUserInfo;
import com.dikai.chenghunjiclient.entity.NewUserInfo;
import com.dikai.chenghunjiclient.entity.NewsProBean;
import com.dikai.chenghunjiclient.entity.ResultGetNewsCustom;
import com.dikai.chenghunjiclient.entity.ResultGetNewsInfo;
import com.dikai.chenghunjiclient.entity.UserInfo;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyProFragment extends Fragment {

    private MyLoadRecyclerView mRecyclerView;
    private WedProjectAdapter mAdapter;
    private int state;


    public MyProFragment() {
        // Required empty public constructor
    }

    public static MyProFragment newInstance(int state) {
        Bundle args = new Bundle();
        args.putInt("state",state);
        MyProFragment fragment = new MyProFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
        state = getArguments().getInt("state");
        Log.e("MyProFragment---","state: " + state);
        mRecyclerView = (MyLoadRecyclerView) view.findViewById(R.id.fragment_project_recycler);
        mRecyclerView.setLoadChangeListener(new MyLoadRecyclerView.OnLoadChangeListener() {
            @Override
            public void onRefresh() {
                getNewsInfo();
            }

            @Override
            public void onLoadMore() {}
        });

        mAdapter = new WedProjectAdapter(getContext());
        mAdapter.setOnAddClickListener(new WedProjectAdapter.OnAddClickListener() {
            @Override
            public void onClick(int position, NewsProBean bean) {
                if(state == 1){
                    Toast.makeText(getContext(), "已提交，不可修改", Toast.LENGTH_SHORT).show();
                }else {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("bean",bean);
                    startActivity(new Intent(getContext(), ProQuestionActivity.class).putExtras(bundle));
                }
            }
        });
        mAdapter.setEditClickListener(new WedProjectAdapter.OnEditClickListener() {
            @Override
            public void onClick(int position, Object bean) {
                if(state == 1){
                    Toast.makeText(getContext(), "已提交，不可修改", Toast.LENGTH_SHORT).show();
                }else {
                    if(position == 0){
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("info",(ResultGetNewsInfo)bean);
                        startActivity(new Intent(getContext(), WedManInfoActivity.class).putExtras(bundle));
                    }else {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("bean",(NewsProBean)bean);
                        startActivity(new Intent(getContext(), ProQuestionActivity.class).putExtras(bundle));
                    }
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getNewsInfo();
    }

    private void getNewsInfo() {
        NewUserInfo userInfo = UserManager.getInstance(getContext()).getNewUserInfo();
        NetWorkUtil.setCallback("HQOAApi/GetNewPeopleInfo",
                new BeanUserInfo(userInfo.getUserId()),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultGetNewsInfo result = new Gson().fromJson(respose, ResultGetNewsInfo.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                getCustomQuestion(result);
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

    private void getCustomQuestion(final ResultGetNewsInfo info) {
        NewUserInfo userInfo = UserManager.getInstance(getContext()).getNewUserInfo();
        NetWorkUtil.setCallback("HQOAApi/GetNewPeopleQuestionList",
                new BeanGetCustom(userInfo.getUserId(),"1"),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mRecyclerView.stopLoad();
                        Log.e("返回值",respose);
                        try {
                            ResultGetNewsCustom result = new Gson().fromJson(respose, ResultGetNewsCustom.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                List<Object> list = new ArrayList<>();
                                list.add(info);
                                list.addAll(result.getDataXR());
                                mAdapter.refresh(list);
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

    /**
     * 事件总线刷新
     * @param bean
     */
    @Subscribe
    public void onEventMainThread(final EventBusBean bean) {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                if(bean.getType() == Constants.NEWS_INFO_CHANGED){
                    getNewsInfo();
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
