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
import com.dikai.chenghunjiclient.adapter.store.PresentAdapter;
import com.dikai.chenghunjiclient.bean.BeanBuyCaigoujie;
import com.dikai.chenghunjiclient.bean.BeanGetPipeline;
import com.dikai.chenghunjiclient.bean.BeanReject;
import com.dikai.chenghunjiclient.entity.PipelineBean;
import com.dikai.chenghunjiclient.entity.ResultBuyCaigoujie;
import com.dikai.chenghunjiclient.entity.ResultGetPipeline;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.PayUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import dmax.dialog.SpotsDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class PipelineFragment extends Fragment {

    private int type;
    private int pageIndex = 1;
    private int pageCount = 20;
    private MyLoadRecyclerView mRecyclerView;
    private PresentAdapter mAdapter;
    private SpotsDialog mDialog;

    public PipelineFragment() {
        // Required empty public constructor
    }

    public static PipelineFragment newInstance(int type) {

        Bundle args = new Bundle();
        args.putInt("type",type);
        PipelineFragment fragment = new PipelineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        EventBus.getDefault().register(this);
        return inflater.inflate(R.layout.fragment_pipeline, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDialog = new SpotsDialog(getContext(),R.style.DialogCustom);
        type = getArguments().getInt("type");
        mRecyclerView = (MyLoadRecyclerView) view.findViewById(R.id.recycler);
        mAdapter = new PresentAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
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
        mAdapter.setOnItemClickListener(new PresentAdapter.OnItemClickListener() {
            @Override
            public void onClick(PipelineBean bean, int position, int type) {
                if(type == 0){
                    reject(bean.getId(),1,0);
                }else if(type == 1){
                    if(bean.getOrderType() == 0){//伴手礼
                        getTrans(bean.getId(),2);
                    }else if(bean.getOrderType() == 1){//婚礼返还
                        getTrans(bean.getId(),3);
                    }else if(bean.getOrderType() == 2){//代收
                        getTrans(bean.getId(),4);
                    }
                }else if(type == 2){
                    reject(bean.getId(),0,1);
                }
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
        NetWorkUtil.setCallback("HQOAApi/GetUserFlowRecord",
                new BeanGetPipeline(UserManager.getInstance(getContext()).getNewUserInfo().getUserId(),0,type,PageIndex,PageCount),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mRecyclerView.stopLoad();
                        Log.e("返回值",respose);
                        try {
                            ResultGetPipeline result = new Gson().fromJson(respose, ResultGetPipeline.class);
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
     * 拒单
     */
    private void reject(String id,int reject,int delete){
        mDialog.show();
        NetWorkUtil.setCallback("HQOAApi/UserRefusalDelete",
                new BeanReject(id,reject,delete),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                refresh();
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
                        mDialog.dismiss();
                        Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    /**
     * 获取中转号
     */
    private void getTrans(String id, int orderType){
        mDialog.show();
        NetWorkUtil.setCallback("HQOAApi/Payment",
                new BeanBuyCaigoujie(UserManager.getInstance(getContext()).getNewUserInfo().getUserId(), "0",orderType+"",id),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultBuyCaigoujie result = new Gson().fromJson(respose, ResultBuyCaigoujie.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                pay(result.getTransNumber());
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
                        mDialog.dismiss();
                        Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void pay(String orderNum){
        PayUtil.getInstance(getContext()).setType(Constants.PIPELINE_PAY);
        PayUtil.getInstance(getContext()).wxPay(orderNum, new PayUtil.OnPayListener() {
            @Override
            public void onFinish(String info) {

            }

            @Override
            public void onError(String e) {
                Toast.makeText(getContext(), e, Toast.LENGTH_SHORT).show();
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
                if(bean.getType() == Constants.PIPELINE_PAY){
                    refresh();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}

