package com.dikai.chenghunjiclient.fragment.me;


import android.Manifest;
import android.content.Context;
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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.me.SearchCarActivity;
import com.dikai.chenghunjiclient.adapter.me.DriverAdapter;
import com.dikai.chenghunjiclient.bean.BeanFire;
import com.dikai.chenghunjiclient.bean.BeanGetMember;
import com.dikai.chenghunjiclient.entity.MemberBean;
import com.dikai.chenghunjiclient.entity.ResultGetMember;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;
import com.flyco.dialog.widget.popup.base.BasePopup;
import com.google.gson.Gson;

/**
 * A simple {@link Fragment} subclass.
 */
public class DriverFragment extends Fragment implements View.OnClickListener {

    private static final int CALL_REQUEST_CODE = 120;
    private LinearLayout mInviteLayout;
    private MyLoadRecyclerView mRecyclerView;
    private DriverAdapter mAdapter;
    private String id;
    private int type;
    private SimpleCustomPop mSimpleCustomPop;
    private int mPosition;
    private MemberBean mBean;
    private Intent intent;
    private MaterialDialog dialog;

    public DriverFragment() {
        // Required empty public constructor
    }

    public static DriverFragment newInstance(String id, int type) {
        Bundle args = new Bundle();
        args.putString("id",id);
        args.putInt("type",type);
        DriverFragment fragment = new DriverFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_driver, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        id = getArguments().getString("id");
        type = getArguments().getInt("type",0);
        mInviteLayout = (LinearLayout)view.findViewById(R.id.fragment_driver_invite);
        mRecyclerView = (MyLoadRecyclerView)view.findViewById(R.id.fragment_driver_recycler);
        mSimpleCustomPop = new SimpleCustomPop(getContext());
        mSimpleCustomPop.setOnMoreListener(new OnMoreListener() {
            @Override
            public void onClick(int position) {
                mSimpleCustomPop.dismiss();
                if(position == 0){
                    dialog.show();
                }else {
                    intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mBean.getPhone()));
                    request();
                }
            }
        });

        mAdapter = new DriverAdapter(getContext());
        mAdapter.setMoreClickListener(new DriverAdapter.OnMoreClickListener() {
            @Override
            public void onClick(View view, int position, MemberBean bean) {
                mPosition = position;
                mBean = bean;
                mSimpleCustomPop.anchorView(view).gravity(Gravity.BOTTOM).show();
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

        dialog = new MaterialDialog(getContext());
        dialog.isTitleShow(false)//
                .btnNum(2)
                .content("确定开除该成员吗？")//
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
                        fire();
                    }
                });
        if(type == 0){
            mInviteLayout.setVisibility(View.GONE);
        }else {
            mInviteLayout.setVisibility(View.VISIBLE);
            mInviteLayout.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        if(v == mInviteLayout){
            startActivity(new Intent(getContext(), SearchCarActivity.class));
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refresh();
    }

    private void refresh() {
        getList();
    }


    /**
     * 开除
     */
    private void fire(){
        NetWorkUtil.setCallback("User/FiredAndAgreedTeam",
                new BeanFire(mBean.getRelaID(), "1", "", UserManager.getInstance(getContext()).getUserInfo().getUserID()),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                mAdapter.remove(mPosition);
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
                        Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    /**
     * 获取信息
     */
    private void getList(){
        NetWorkUtil.setCallback("User/GetTeamInfoList",
                new BeanGetMember(id, "", "0", "0", "2"),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mRecyclerView.stopLoad();
                        Log.e("返回值",respose);
                        try {
                            ResultGetMember result = new Gson().fromJson(respose, ResultGetMember.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                if(result.getData() == null || result.getData().size() == 0){
                                    String mark = "暂时没有车队成员\n" + "先去通知他们下载APP\n" + "再邀请他们加入车队吧";
                                    mRecyclerView.setNoData(mark);
                                    mRecyclerView.setHasData(false);
                                }else {
                                    mRecyclerView.setHasData(true);
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

    /**
     * 右上角弹窗
     */
    public static class SimpleCustomPop extends BasePopup<SimpleCustomPop> implements View.OnClickListener {

        private View mDialogView;
        private OnMoreListener mOnMoreListener;
        private TextView oneText;
        private TextView twoText;

        public void setOnMoreListener(OnMoreListener onMoreListener) {
            mOnMoreListener = onMoreListener;
        }

        public SimpleCustomPop(Context context) {
            super(context);
        }

        @Override
        public View onCreatePopupView() {
            mDialogView = View.inflate(getContext(), R.layout.item_dialog_msg_layout, null);
            oneText = (TextView) mDialogView.findViewById(R.id.item_first_text);
            twoText = (TextView) mDialogView.findViewById(R.id.item_second_text);
            oneText.setOnClickListener(this);
            twoText.setOnClickListener(this);
            return  mDialogView;
        }

        @Override
        public void setUiBeforShow() {
            oneText.setText("开除");
            twoText.setText("联系");
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.item_first_text:
                    mOnMoreListener.onClick(0);
                    break;
                case R.id.item_second_text:
                    mOnMoreListener.onClick(1);
                    break;
            }
        }
    }

    private interface OnMoreListener{
        void onClick(int position);
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
}
