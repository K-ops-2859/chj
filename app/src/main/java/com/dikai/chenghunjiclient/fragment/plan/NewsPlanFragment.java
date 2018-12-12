package com.dikai.chenghunjiclient.fragment.plan;

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
import com.dikai.chenghunjiclient.adapter.plan.NewsAdapter;
import com.dikai.chenghunjiclient.bean.BeanUserInfo;
import com.dikai.chenghunjiclient.entity.ResultGetNewsPlan;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsPlanFragment extends Fragment{

    private LinearLayout mPlace;
    private MyLoadRecyclerView mRecyclerView;
    private NewsAdapter mAdapter;

    public NewsPlanFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_plan, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPlace = (LinearLayout) view.findViewById(R.id.news_plan_place);
        mRecyclerView = (MyLoadRecyclerView)view.findViewById(R.id.news_plan_recycler);
        mAdapter = new NewsAdapter(getContext());
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
        mPlace.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refresh();
    }

    public void refresh() {
        getList();
    }

    /**
     * 获取信息
     */
    private void getList(){
        NetWorkUtil.setCallback("User/GetCustomerInfoList",
                new BeanUserInfo(UserManager.getInstance(getContext()).getUserInfo().getUserID()),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mRecyclerView.stopLoad();
                        Log.e("返回值",respose);
                        try {
                            ResultGetNewsPlan result = new Gson().fromJson(respose, ResultGetNewsPlan.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                if(result.getData().size() == 0){
                                    mPlace.setVisibility(View.VISIBLE);
                                    mRecyclerView.setVisibility(View.GONE);
                                }else {
                                    mPlace.setVisibility(View.INVISIBLE);
                                    mRecyclerView.setVisibility(View.VISIBLE);
                                    List<Object> list = new ArrayList<>();
                                    list.add("");
                                    list.addAll(result.getData());
                                    mAdapter.refresh(list);
                                }
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
                        mRecyclerView.stopLoad();
                        Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
