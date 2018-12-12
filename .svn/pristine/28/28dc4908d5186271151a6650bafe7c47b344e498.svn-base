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
import com.dikai.chenghunjiclient.bean.BeanGetCase;
import com.dikai.chenghunjiclient.bean.BeanGetSupList;
import com.dikai.chenghunjiclient.entity.ResultGetCase;
import com.dikai.chenghunjiclient.entity.ResultGetSupplierInfo;
import com.dikai.chenghunjiclient.entity.ResultNewSupInfo;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.dikai.chenghunjiclient.view.MyRecyclerView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * A simple {@link Fragment} subclass.
 */
public class CorpInfoFragment extends Fragment {

    private String supId;
    private MyLoadRecyclerView mRecycler;
    private HotelPicAdapter mAdater;

    public static CorpInfoFragment newInstance(String supId) {
        Bundle args = new Bundle();
        args.putString("id",supId);
        CorpInfoFragment fragment = new CorpInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    public CorpInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_corp_info, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        supId = getArguments().getString("id");
//        intro = (TextView) view.findViewById(R.id.activity_info_intro);
//        address = (TextView) view.findViewById(R.id.activity_info_address);
//        num = (TextView) view.findViewById(R.id.activity_info_num);
        mRecycler = (MyLoadRecyclerView) view.findViewById(R.id.activity_info_recycler);
        mRecycler.setLoadChangeListener(new MyLoadRecyclerView.OnLoadChangeListener() {
            @Override
            public void onRefresh() {
                getCase();
            }

            @Override
            public void onLoadMore() {}
        });
        mAdater = new HotelPicAdapter(getContext());
        mAdater.setSupId(supId);
        mRecycler.setAdapter(mAdater);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getCase();
    }

    /**
     * 获取案例
     */
    private void getCase(){
        NetWorkUtil.setCallback("HQOAApi/CaseInfoInfoList",
                new BeanGetCase(supId),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mRecycler.stopLoad();
                        Log.e("返回值",respose);
                        try {
                            ResultGetCase result = new Gson().fromJson(respose, ResultGetCase.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                mAdater.refresh(result.getData());
                                if(result.getData().size()>0){
                                    mRecycler.setHasData(true);
                                }else {
                                    mRecycler.setHasData(false);
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
                        mRecycler.stopLoad();
                        Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
