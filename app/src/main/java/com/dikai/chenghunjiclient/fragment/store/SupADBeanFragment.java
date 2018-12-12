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
import com.dikai.chenghunjiclient.activity.ad.ADListActivity;
import com.dikai.chenghunjiclient.activity.ad.NewADInfoActivity;
import com.dikai.chenghunjiclient.adapter.ad.SupADAdapter;
import com.dikai.chenghunjiclient.bean.BeanGetAdList;
import com.dikai.chenghunjiclient.entity.ResultGetAdList;
import com.dikai.chenghunjiclient.entity.SupAdBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.google.gson.Gson;

/**
 * A simple {@link Fragment} subclass.
 */
public class SupADBeanFragment extends Fragment {

    private MyLoadRecyclerView mRecyclerView;
    private SupADAdapter mAdapter;
    private int pageIndex = 1;
    private int pageCount = 20;
    private String supID;

    public SupADBeanFragment() {
        // Required empty public constructor
    }

    public static SupADBeanFragment newInstance(String id) {
        Bundle args = new Bundle();
        args.putString("id",id);
        SupADBeanFragment fragment = new SupADBeanFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sup_adbean, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        supID = getArguments().getString("id");
        mRecyclerView = (MyLoadRecyclerView) view.findViewById(R.id.recycler);
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
        mAdapter = new SupADAdapter(getContext());
        mAdapter.setOnItemClickListener(new SupADAdapter.OnItemClickListener() {
            @Override
            public void onClick(SupAdBean bean) {
                startActivity(new Intent(getContext(), NewADInfoActivity.class).putExtra("id",bean.getId()));
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
        NetWorkUtil.setCallback("HQOAApi/GetFacilitatorActivityList",
                new BeanGetAdList(supID,PageIndex, PageCount),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mRecyclerView.stopLoad();
                        Log.e("返回值",respose);
                        try {
                            ResultGetAdList result = new Gson().fromJson(respose, ResultGetAdList.class);
                            if ("200".equals(result.getMessage().getCode())) {
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
                            //Toast.makeText(ADListActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mRecyclerView.stopLoad();
//                        Toast.makeText(ADListActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
