package com.dikai.chenghunjiclient.fragment.wedding;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.wedding.ProjectAdapter;
import com.dikai.chenghunjiclient.bean.BeanGetProject;
import com.dikai.chenghunjiclient.bean.BeanNewPeople;
import com.dikai.chenghunjiclient.entity.GetColorProjectData;
import com.dikai.chenghunjiclient.entity.UserInfo;
import com.dikai.chenghunjiclient.fragment.BaseLazyFragment;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectBeanFragment extends BaseLazyFragment {

    private ProjectAdapter mAdapter;
    private MyLoadRecyclerView mRecyclerView;
    private String color;
    private int type;
    private int pageIndex = 1;
    private int itemCount = 20;

    public ProjectBeanFragment() {
        // Required empty public constructor
    }

    public static ProjectBeanFragment newInstance(String color, int type) {
        Bundle args = new Bundle();
        args.putString("color", color);
        args.putInt("type", type);
        ProjectBeanFragment fragment = new ProjectBeanFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_project_bean;
    }

    @Override
    protected void initViewsAndEvents(View view) {
        mRecyclerView = (MyLoadRecyclerView) view.findViewById(R.id.fragment_project_recycler);
        mAdapter = new ProjectAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        color = getArguments().getString("color");
        if (color.equals("全部")) {
            color = "";
        }
        type = getArguments().getInt("type");
        mRecyclerView.setLoadChangeListener(new MyLoadRecyclerView.OnLoadChangeListener() {
            @Override
            public void onRefresh() {
                refresh();
            }

            @Override
            public void onLoadMore() {
                pageIndex++;
                getProject(false, "", "", pageIndex, itemCount);
            }
        });
    }

    @Override
    protected void DetoryViewAndEvents() {
    }

    @Override
    public void onFirstUserVisible() {
        refresh();
    }

    @Override
    public void onUserVisible() {
        refresh();
    }

    @Override
    public void onUserInvisible() {

    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_project_bean, container, false);
//    }

//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        mRecyclerView = (MyLoadRecyclerView) view.findViewById(R.id.fragment_project_recycler);
//        mAdapter = new ProjectAdapter(getContext());
//        mRecyclerView.setAdapter(mAdapter);
//        color = getArguments().getString("color");
//        if (color.equals("全部")) {
//            color = "";
//        }
//        type = getArguments().getInt("type");
//        mRecyclerView.setLoadChangeListener(new MyLoadRecyclerView.OnLoadChangeListener() {
//            @Override
//            public void onRefresh() {
//                refresh(0, 0);
//            }
//
//            @Override
//            public void onLoadMore() {
//                pageIndex++;
//                getProject(false, pageIndex, itemCount, 0, 0);
//            }
//        });
//
//    }

    public void refresh() {
        pageIndex = 1;
        itemCount = 20;
        getProject(true, "", "", pageIndex, itemCount);
    }

    public void getProject(final boolean isRefresh, String title, String keyWord, final int pageIndex, final int itemCount) {
        NetWorkUtil.setCallback("HQOAApi/GetNewPeoplePlanList",
                new BeanNewPeople(title, keyWord, color, pageIndex + "", itemCount + "", "1"),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mRecyclerView.stopLoad();
                        Log.e("返回值", respose);
                        try {
                            GetColorProjectData result = new Gson().fromJson(respose, GetColorProjectData.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                mRecyclerView.setTotalCount(result.getTotalCount());
                                if (isRefresh) {
                                    if (result.getData().size() == 0) {
                                        mRecyclerView.setHasData(false);
                                    } else {
                                        mRecyclerView.setHasData(true);
                                        mAdapter.refresh(result.getData());
                                    }
                                } else {
                                    mAdapter.addAll(result.getData());
                                }
                            } else {
                                Toast.makeText(getContext(), "" + result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错", e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mRecyclerView.stopLoad();
                        Log.e("网络出错", e.toString());
                    }
                });
    }
}
