package com.dikai.chenghunjiclient.fragment.me;


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
import com.dikai.chenghunjiclient.adapter.me.ClearAdapter;
import com.dikai.chenghunjiclient.bean.BeanClearOrder;
import com.dikai.chenghunjiclient.bean.BeanGetOrder;
import com.dikai.chenghunjiclient.bean.BeanGetSupplier;
import com.dikai.chenghunjiclient.entity.OrderBean;
import com.dikai.chenghunjiclient.entity.ResultGetOrder;
import com.dikai.chenghunjiclient.entity.ResultGetSupList;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.entity.UserInfo;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dmax.dialog.SpotsDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClearFragment extends Fragment {

    private static final int CALL_REQUEST_CODE = 121;
    private int pageIndex = 1;
    private int itemCount = 20;
    private MyLoadRecyclerView mRecyclerView;
    private ClearAdapter mAdapter;
    private int type;
    private SpotsDialog mDialog;
    private MaterialDialog dialog;
    private MaterialDialog dialogCall;
    private int mPosition;
    private OrderBean mOrderBean;
    private Intent intent;

    public ClearFragment() {
        // Required empty public constructor
    }

    public static ClearFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type",type);
        ClearFragment fragment = new ClearFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_clear, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        type = getArguments().getInt("type");
        mDialog = new SpotsDialog(getContext(), R.style.DialogCustom);
        mRecyclerView = (MyLoadRecyclerView)view.findViewById(R.id.fragment_clear_recycler);
        mAdapter = new ClearAdapter(getContext());
        mAdapter.setType(type);
        mAdapter.setOnCarClickListener(new ClearAdapter.OnCarClickListener() {
            @Override
            public void onClick(int position, OrderBean bean) {
                mPosition = position;
                mOrderBean = bean;
                dialog.show();
            }
        });

        mAdapter.setCallClickListener(new ClearAdapter.OnCallClickListener() {
            @Override
            public void onClick(OrderBean bean) {
                intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + bean.getCorpNamePhone()));
                dialogCall.show();
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
                pageIndex++;
                getList(false, pageIndex, itemCount);
            }
        });

        dialog = new MaterialDialog(getContext());
        dialog.isTitleShow(false)//
                .btnNum(2)
                .content("是否结算此订单？")//
                .btnText("取消", "确定")
                .setOnBtnClickL(new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                    }
                }, new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                        clear(mPosition, mOrderBean.getSupplierOrderID());
                    }
                });

        dialogCall = new MaterialDialog(getContext());
        dialogCall.isTitleShow(false)//
                .btnNum(2)
                .content("是否拨打此联系人电话？")//
                .btnText("取消", "确定")
                .setOnBtnClickL(new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                    }
                }, new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                        request();
                    }
                });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refresh();
    }

    private void refresh() {
        pageIndex = 1;
        itemCount = 20;
        getList(true, pageIndex, itemCount);
    }

    private void getList(final boolean isRefresh, int pageIndex, int itemCount) {
        UserInfo userInfo = UserManager.getInstance(getContext()).getUserInfo();
        NetWorkUtil.setCallback("User/GetSupplierrOrderList",
                new BeanGetOrder(userInfo.getSupplierID(), "0","" + type, pageIndex + "",itemCount + ""),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mRecyclerView.stopLoad();
                        Log.e("返回值",respose);
                        try {
                            ResultGetOrder result = new Gson().fromJson(respose, ResultGetOrder.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                mRecyclerView.setTotalCount(result.getTotalCount());
                                if(isRefresh){
                                    if(result.getData().size() == 0){
                                        mRecyclerView.setHasData(false);
                                    }else {
                                        mRecyclerView.setHasData(true);
                                    }
                                    mAdapter.refresh(result.getData());
                                }else {
                                    mAdapter.addAll(result.getData());
                                }
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
                        mRecyclerView.stopLoad();
                        Log.e("网络出错",e.toString());
                    }
                });
    }


    private void clear(final int position, String orderID) {
        mDialog.show();
        UserInfo userInfo = UserManager.getInstance(getContext()).getUserInfo();
        NetWorkUtil.setCallback("User/ScheduleSettlement",
                new BeanClearOrder(userInfo.getSupplierID(), orderID),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                mAdapter.delete(position);
                                EventBus.getDefault().post(new EventBusBean(Constants.ORDER_CLEARED));
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
                        mDialog.dismiss();
                        Log.e("网络出错",e.toString());
                    }
                });
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

    /**
     * 事件总线刷新
     * @param bean
     */
    @Subscribe
    public void onEventMainThread(final EventBusBean bean) {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                if(bean.getType() == Constants.ORDER_CLEARED){
                    if(type == 2){
                        refresh();
                    }
                }
            }
        });
    }

}
