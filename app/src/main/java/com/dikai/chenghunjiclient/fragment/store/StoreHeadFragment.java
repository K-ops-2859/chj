package com.dikai.chenghunjiclient.fragment.store;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.register.SelectIdentActivity;
import com.dikai.chenghunjiclient.adapter.store.MainHeadAdapter;
import com.dikai.chenghunjiclient.bean.BeanNull;
import com.dikai.chenghunjiclient.citypicker.DBManager;
import com.dikai.chenghunjiclient.citypicker.SelectCityActivity;
import com.dikai.chenghunjiclient.entity.IdentityBean;
import com.dikai.chenghunjiclient.entity.ResultGetIdentity;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyRecyclerView;
import com.flyco.dialog.entity.DialogMenuItem;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalListDialog;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class StoreHeadFragment extends Fragment {

    private TextView mIdentText;
    private ConvenientBanner mHeaderImageView;
    private CBViewHolderCreator mCBView;
    private NormalListDialog dialog;
    private ArrayList<DialogMenuItem> mMenuItems;
    private List<String> mStringItems;
    private List<IdentityBean> mIdentityBeanList;
    private String ident = "婚庆";

    public StoreHeadFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        EventBus.getDefault().register(this);
        return inflater.inflate(R.layout.fragment_store_head, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        mIdentLayout = (LinearLayout) view.findViewById(R.id.fragment_head_identity);
//        mLocationLayout = (LinearLayout) view.findViewById(R.id.fragment_head_location);
        mIdentText = (TextView) view.findViewById(R.id.fragment_head_identity_name);
//        mLocationText = (TextView) view.findViewById(R.id.fragment_head_location_name);
        mHeaderImageView = (ConvenientBanner) view.findViewById(R.id.fragment_head_convenientBanner);
        mCBView = new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        };

        if(UserManager.getInstance(getContext()).isLogin()){
            String identID = UserManager.getInstance(getContext()).getUserInfo().getProfession();
            String mark = "当天"+ UserManager.getInstance(getContext()).getAllIdentMap().get(identID) +"有空余档期";
            mIdentText.setText(mark);
        }else {
            mIdentText.setText("登录后可查看档期");
        }
        getIdentity();
    }

    private void initDialog(List<IdentityBean> data){
        mIdentityBeanList = data;
        mMenuItems = new ArrayList<>();
        mStringItems = new ArrayList<>();
        for (IdentityBean bean : data) {
            mStringItems.add(bean.getOccupationName());
            mMenuItems.add(new DialogMenuItem(bean.getOccupationName(), R.mipmap.ic_app_identtity));
        }
        dialog = new NormalListDialog(getContext(), mMenuItems);
        dialog.title("供应商身份")//
                .titleTextSize_SP(18)//
                .titleBgColor(ContextCompat.getColor(getContext(),R.color.vice))//
                .itemPressColor(ContextCompat.getColor(getContext(),R.color.gray_background))//
                .itemTextColor(ContextCompat.getColor(getContext(),R.color.main_text))//
                .itemTextSize(16)//
                .cornerRadius(4)//
                .widthScale(0.7f);

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog.dismiss();
                EventBus.getDefault().post(new EventBusBean(Constants.HOME_SELECT_IDENTITY,mIdentityBeanList.get(position)));
                mIdentText.setText(mIdentityBeanList.get(position).getOccupationName());
            }
        });
    }


    /**
     * 获取职业
     */
    private void getIdentity(){
        NetWorkUtil.setCallback("User/GetAllOccupationList",
                new BeanNull("2"),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultGetIdentity result = new Gson().fromJson(respose, ResultGetIdentity.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                setData(result.getData());
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
                        Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setData(List<IdentityBean> data) {
        data.subList(0,10);
        List<List<IdentityBean>> lists = new ArrayList<>();
        Collections.addAll(lists, data.subList(0,10), data.subList(10,data.size()));
        mHeaderImageView.setPages(mCBView, lists);
        mHeaderImageView.setPointViewVisible(true)    //设置指示器是否可见
                .setPageIndicator(new int[]{R.drawable.cbview_selector_2, R.drawable.cbview_selector})   //设置指示器圆点
//                .startTurning(3000)//设置自动切换（同时设置了切换时间间隔）
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL) //设置指示器位置（左、中、右)
//                .setOnItemClickListener(this)//设置点击监听事件
                .setManualPageable(true);//设置手动影响（设置可否手动切换）
        initDialog(data);
    }

    /**
     * CBView的HolderView
     */
    public class NetworkImageHolderView implements Holder<List<IdentityBean>> {
        private MyRecyclerView mRecyclerView;

        @Override
        public View createView(Context context) {
            //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
            View view = LayoutInflater.from(context).inflate(R.layout.item_cbview_layout, null);
            mRecyclerView = (MyRecyclerView) view.findViewById(R.id.my_cbview_recycler);
            return view;
        }

        @Override
        public void UpdateUI(Context context,int position, List<IdentityBean> data) {
//            GridLayoutManager gridLayoutManager = new GridLayoutManager(context,5);
//            mRecyclerView.setLayoutManager(gridLayoutManager);
//            MainHeadAdapter adapter = new MainHeadAdapter(context);
//            adapter.setOnItemClickListener(new MainHeadAdapter.OnItemClickListener() {
//                @Override
//                public void onClick(IdentityBean bean) {
//                    ident = bean.getOccupationName();
//                    EventBus.getDefault().post(new EventBusBean(Constants.HOME_SELECT_IDENTITY,bean));
//                }
//            });
//            mRecyclerView.setAdapter(adapter);
//            adapter.refresh(data);
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
                if(bean.getType() == Constants.USER_INFO_CHANGE){
                    if(!UserManager.getInstance(getContext()).isLogin()){
                        mIdentText.setText("登录后可查看档期");
                    }
                }else if(bean.getType() == Constants.HOME_HAS_DATA){
                    String mark = "当天"+ ident +"有空余档期";
                    mIdentText.setText(mark);
                }else if(bean.getType() == Constants.HOME_NO_DATA){
                    String mark = "当天"+ ident +"没有有空余档期";
                    mIdentText.setText(mark);
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
