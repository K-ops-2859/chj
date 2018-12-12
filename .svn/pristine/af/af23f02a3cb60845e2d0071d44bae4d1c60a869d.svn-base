package com.dikai.chenghunjiclient.fragment.me;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.discover.LikeDetailsActivity;
import com.dikai.chenghunjiclient.activity.store.CarInfoActivity;
import com.dikai.chenghunjiclient.activity.store.CorpInfoActivity;
import com.dikai.chenghunjiclient.activity.store.HotelInfoActivity;
import com.dikai.chenghunjiclient.adapter.me.FocusAdapter;
import com.dikai.chenghunjiclient.bean.BeanFocus;
import com.dikai.chenghunjiclient.bean.BeanGetFans;
import com.dikai.chenghunjiclient.bean.BeanID;
import com.dikai.chenghunjiclient.bean.BeanUserId;
import com.dikai.chenghunjiclient.entity.CasesBean;
import com.dikai.chenghunjiclient.entity.FansBean;
import com.dikai.chenghunjiclient.entity.LikePersonData;
import com.dikai.chenghunjiclient.entity.ResultGetFans;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import dmax.dialog.SpotsDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class FocusFragment extends Fragment {

    private int type;
    private FocusAdapter mAdapter;
    private MyLoadRecyclerView mRecyclerView;
    private MaterialDialog dialog;
    private SpotsDialog mDialog;
    private int nowPoition;
    private String nowID;

    public FocusFragment() {
        // Required empty public constructor
    }

    public static FocusFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type",type);
        FocusFragment fragment = new FocusFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_focus, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDialog = new SpotsDialog(getContext(),R.style.DialogCustom);
        type = getArguments().getInt("type");
        mRecyclerView = (MyLoadRecyclerView) view.findViewById(R.id.recycler);
        mRecyclerView.setLoadChangeListener(new MyLoadRecyclerView.OnLoadChangeListener() {
            @Override
            public void onRefresh() {
                refresh();
            }

            @Override
            public void onLoadMore() {

            }
        });
        mAdapter = new FocusAdapter(getContext());
        if(type == 1){
            mAdapter.setFans(true);
        }
        mAdapter.setOnItemClickListener(new FocusAdapter.OnItemClickListener() {
            @Override
            public void onClick(int type, int position,FansBean bean) {
                if(type == 0){
                    String code = bean.getProfession();
                    if("F209497C-2F2E-4394-AF20-312ED665F67A".equalsIgnoreCase(code)||"70CD854E-D943-4607-B993-91912329C61E".equalsIgnoreCase(code)){//车手、用户
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("data", new LikePersonData.DataList(bean.getHeadportrait(),
                                bean.getName(), bean.getUserId(), UserManager.getInstance(getContext()).getProfession(bean.getProfession())));
                        Intent intent = new Intent(getContext(), LikeDetailsActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }else if("99C06C5A-DDB8-46A1-B860-CD1227B4DB68".equalsIgnoreCase(code)){//酒店
                        startActivity(new Intent(getContext(), HotelInfoActivity.class)
                                .putExtra("id", bean.getFollowSupplierId()).putExtra("userid",bean.getUserId()));
//                    }else if("2526D327-B0AE-4D88-922E-1F7A91722422".equals(code)){//婚车
//                        startActivity(new Intent(getContext(), CarInfoActivity.class)
//                                .putExtra("id", bean.getFollowSupplierId()).putExtra("userid",bean.getUserId()));
                    }else if("7DC8EDF8-A068-400F-AFD0-417B19DB3C7C".equalsIgnoreCase(code)){//婚庆
                        startActivity(new Intent(getContext(), CorpInfoActivity.class)
                                .putExtra("id", bean.getFollowSupplierId())
                                .putExtra("type",1).putExtra("userid",bean.getUserId()));
                    }else {//其他
                        startActivity(new Intent(getContext(), CorpInfoActivity.class)
                                .putExtra("id", bean.getFollowSupplierId())
                                .putExtra("type",0)
                                .putExtra("userid",bean.getUserId()));
                    }
                }else if(type == 1){
                    nowID = bean.getUserId();
                    nowPoition = position;
                    dialog.show();
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        dialog = new MaterialDialog(getContext());
        dialog.isTitleShow(false)//
                .btnNum(2)
                .content("确定要取消关注吗？")//
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
                        unfocus(nowID,nowPoition);
                    }
                });

        refresh();
    }

    private void refresh(){
        getInfo();
    }

    /**
     * 获取info
     */
    private void getInfo(){
        NetWorkUtil.setCallback("HQOAApi/UserFollowInfoList",
                new BeanGetFans(UserManager.getInstance(getContext()).getNewUserInfo().getUserId(),type),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mRecyclerView.stopLoad();
                        Log.e("返回值",respose);
                        try {
                            ResultGetFans result = new Gson().fromJson(respose, ResultGetFans.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                mAdapter.refresh(result.getData());
                                if(result.getData().size() == 0){
                                    mRecyclerView.setHasData(false);
                                }else {
                                    mRecyclerView.setHasData(true);
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
                    }
                });
    }

    private void unfocus(String objectID, final int position) {
        mDialog.show();
        NetWorkUtil.setCallback("HQOAApi/DeleteUserFollow",
                new BeanFocus(UserManager.getInstance(getContext()).getNewUserInfo().getUserId(), objectID),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(String respose) {
                        mDialog.dismiss();
                        try {
                            Log.e("返回值", respose);
                            ResultMessage detailsData = new Gson().fromJson(respose, ResultMessage.class);
                            if (detailsData.getMessage().getCode().equals("200")) {
                                mAdapter.delete(position);
                                EventBus.getDefault().post(new EventBusBean(Constants.FOCUS_CHANGE));
                            }else {
                                Toast.makeText(getContext(), detailsData.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("", e.toString());
                        }
                    }

                    @Override
                    public void onError(String e) {
                        mDialog.dismiss();
                    }
                });
    }
}
