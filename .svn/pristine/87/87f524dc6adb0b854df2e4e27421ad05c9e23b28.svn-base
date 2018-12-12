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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.plan.AddMarkActivity;
import com.dikai.chenghunjiclient.adapter.plan.PlanFlowAdapter;
import com.dikai.chenghunjiclient.bean.BeanAddMark;
import com.dikai.chenghunjiclient.bean.BeanGetPlanInfo;
import com.dikai.chenghunjiclient.entity.DayPlanBean;
import com.dikai.chenghunjiclient.entity.ResultGetPlanInfo;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.entity.UserInfo;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import dmax.dialog.SpotsDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlanInfoFragment extends Fragment implements View.OnClickListener {

    private static final int CALL_REQUEST_CODE = 120;
    private RecyclerView mRecyclerView;
    private PlanFlowAdapter mAdapter;
    private TextView room;
    private TextView hotel;
    private TextView address;
    private TextView bride;
    private TextView groom;
    private TextView add;
    private TextView edit;
    private TextView name;
    private TextView remark;
    private LinearLayout remarkLayout;
    private TextView phone;
    private ImageView call;
    private ImageView logo;
    private SpotsDialog mDialog;
    private DayPlanBean mBean;
    private int markType;
    private Intent intent;
    private MaterialDialog dialog;

    public PlanInfoFragment() {
        // Required empty public constructor
    }

    public static PlanInfoFragment newInstance(DayPlanBean bean) {
        Bundle args = new Bundle();
        args.putSerializable("bean",bean);
        PlanInfoFragment fragment = new PlanInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plan_info, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
        init(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void init(View view) {
        mBean = (DayPlanBean) getArguments().getSerializable("bean");
        mDialog = new SpotsDialog(getContext(), R.style.DialogCustom);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.activity_plan_info_recycler);
        room = (TextView) view.findViewById(R.id.activity_plan_info_room);
        hotel = (TextView) view.findViewById(R.id.activity_plan_info_hotel);
        address = (TextView) view.findViewById(R.id.activity_plan_info_address);
        remarkLayout = (LinearLayout) view.findViewById(R.id.activity_plan_info_remark_layout);
        remark = (TextView) view.findViewById(R.id.activity_plan_info_remark);
        bride = (TextView) view.findViewById(R.id.activity_plan_info_bride);
        groom = (TextView) view.findViewById(R.id.activity_plan_info_groom);
        name = (TextView) view.findViewById(R.id.activity_plan_info_name);
        phone = (TextView) view.findViewById(R.id.activity_plan_info_phone);
        add = (TextView) view.findViewById(R.id.activity_plan_info_agree);
        edit = (TextView) view.findViewById(R.id.activity_plan_info_refuse);
        logo = ((ImageView) view.findViewById(R.id.activity_plan_info_logo));
        call = ((ImageView) view.findViewById(R.id.activity_plan_info_call));
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new PlanFlowAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        call.setOnClickListener(this);
        edit.setOnClickListener(this);
        add.setOnClickListener(this);

        dialog = new MaterialDialog(getContext());
        dialog.isTitleShow(false)//
                .btnNum(2)
                .content("确定删除备注吗")//
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
                        deleteRemark();
                    }
                });

        getList();
    }

    @Override
    public void onClick(View v) {
        if(v == add){
            if(markType == 0){
                Bundle bundle = new Bundle();
                bundle.putSerializable("bean",mBean);
                bundle.putInt("type",0);
                startActivity(new Intent(getContext(), AddMarkActivity.class).putExtras(bundle));
            }else {
                dialog.show();
            }
        }else if(v == edit){
            Bundle bundle = new Bundle();
            bundle.putSerializable("bean",mBean);
            bundle.putInt("type",1);
            startActivity(new Intent(getContext(), AddMarkActivity.class).putExtras(bundle));
        }else if(v == call){
            intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mBean.getCorpNamePhone()));
            request();
        }
    }

    private void setData(ResultGetPlanInfo result) {
        name.setText(mBean.getCorpName());
        phone.setText(mBean.getCorpNamePhone());
        groom.setText(result.getGroom());
        bride.setText(result.getBride());
        hotel.setText(result.getRummeryName());
        address.setText(result.getRummeryAddress());
        room.setText(result.getBanquetHallName());
        mAdapter.refresh(result.getData());
        if(mBean.getRemark() == null || "".equals(mBean.getRemark().trim())){
            remarkLayout.setVisibility(View.GONE);
            markType = 0;
            edit.setVisibility(View.GONE);
            add.setText("添加备注");
        }else {
            remarkLayout.setVisibility(View.VISIBLE);
            remark.setText(mBean.getRemark());
            edit.setVisibility(View.VISIBLE);
            add.setText("删除备注");
            markType = 1;
        }
        Glide.with(this).load(mBean.getCorpLogo()).into(logo);
    }

    /**
     * 获取信息
     */
    private void getList() {
        mDialog.show();
        UserInfo userInfo = UserManager.getInstance(getContext()).getUserInfo();
        NetWorkUtil.setCallback("User/GetSupplierrOrderInfo",
                new BeanGetPlanInfo("1",userInfo.getSupplierID(),"1","200"),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultGetPlanInfo result = new Gson().fromJson(respose, ResultGetPlanInfo.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                setData(result);
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

    /**
     * 删除备注
     */
    private void deleteRemark() {
        mDialog.show();
        UserInfo userInfo = UserManager.getInstance(getContext()).getUserInfo();
        NetWorkUtil.setCallback("User/AddScheduleRemark",
                new BeanAddMark(userInfo.getSupplierID(), mBean.getScheduleID(), ""),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                EventBus.getDefault().post(new EventBusBean(Constants.DEL_REMARK_SUCCESS));
                                remarkLayout.setVisibility(View.GONE);
                                markType = 0;
                                edit.setVisibility(View.GONE);
                                add.setText("添加备注");
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
                if(bean.getType() == Constants.ADD_REMARK_SUCCESS){
                    remarkLayout.setVisibility(View.VISIBLE);
                    remark.setText(bean.getData());
                    markType = 1;
                    edit.setVisibility(View.VISIBLE);
                    add.setText("删除备注");
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
