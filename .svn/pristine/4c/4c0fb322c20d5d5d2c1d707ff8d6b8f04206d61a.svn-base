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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.me.SearchCarActivity;
import com.dikai.chenghunjiclient.activity.plan.TeamCarActivity;
import com.dikai.chenghunjiclient.adapter.plan.AssignDriverAdapter;
import com.dikai.chenghunjiclient.bean.BeanAssignDriver;
import com.dikai.chenghunjiclient.entity.AssignDriverBean;
import com.dikai.chenghunjiclient.entity.DayPlanBean;
import com.dikai.chenghunjiclient.entity.ResultAssignDriver;
import com.dikai.chenghunjiclient.fragment.me.DriverFragment;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.flyco.dialog.widget.MaterialDialog;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * A simple {@link Fragment} subclass.
 */
public class AssignDriverFragment extends Fragment implements View.OnClickListener {

    private static final int CALL_REQUEST_CODE = 120;
    private LinearLayout mInviteLayout;
    private MyLoadRecyclerView mRecyclerView;
    private AssignDriverAdapter mAdapter;
    private Intent intent;
    private DayPlanBean mBean;

    public AssignDriverFragment() {
        // Required empty public constructor
    }

    public static AssignDriverFragment newInstance(DayPlanBean bean) {
        Bundle args = new Bundle();
        args.putSerializable("bean",bean);
        AssignDriverFragment fragment = new AssignDriverFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_assign_driver, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
        mBean = (DayPlanBean) getArguments().getSerializable("bean");
        mInviteLayout = (LinearLayout)view.findViewById(R.id.fragment_driver_invite);
        mRecyclerView = (MyLoadRecyclerView)view.findViewById(R.id.fragment_driver_recycler);
        mAdapter = new AssignDriverAdapter(getContext());
        mAdapter.setMoreClickListener(new AssignDriverAdapter.OnMoreClickListener() {
            @Override
            public void onClick(View view, int position, AssignDriverBean bean) {
                intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + bean.getPhoneNo()));
                request();
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
        mInviteLayout.setOnClickListener(this);

//        dialog = new MaterialDialog(getContext());
//        dialog.isTitleShow(false)//
//                .btnNum(2)
//                .content("确定开除该成员吗？")//
//                .btnText("取消", "确定")
//                .setOnBtnClickL(new OnBtnClickL() {
//                    @Override
//                    public void onBtnClick() {
//                        dialog.dismiss();
//                    }
//                }, new OnBtnClickL() {
//                    @Override
//                    public void onBtnClick() {
//                        dialog.dismiss();
//                        fire();
//                    }
//                });

    }

    @Override
    public void onClick(View v) {
        if(v == mInviteLayout){
            startActivity(new Intent(getContext(), TeamCarActivity.class)
                    .putExtra("id",mBean.getOrderID())
                    .putExtra("date",mBean.getWeddingDate()));
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
     * 获取信息
     */
    private void getList(){
        NetWorkUtil.setCallback("User/CaptainGetResponseStatus",
                new BeanAssignDriver(UserManager.getInstance(getContext()).getUserInfo().getSupplierID(), mBean.getOrderID()),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mRecyclerView.stopLoad();
                        Log.e("返回值",respose);
                        try {
                            ResultAssignDriver result = new Gson().fromJson(respose, ResultAssignDriver.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                if(result.getData() == null || result.getData().size() == 0){
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
                if(bean.getType() == Constants.ASSIGN_CAR_SUCCESS){
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
