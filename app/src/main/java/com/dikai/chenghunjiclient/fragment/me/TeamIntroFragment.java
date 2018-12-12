package com.dikai.chenghunjiclient.fragment.me;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.store.CorpInfoActivity;
import com.dikai.chenghunjiclient.adapter.store.HotelPicAdapter;
import com.dikai.chenghunjiclient.bean.BeanGetSupList;
import com.dikai.chenghunjiclient.citypicker.DBManager;
import com.dikai.chenghunjiclient.entity.ResultGetSupplierInfo;
import com.dikai.chenghunjiclient.fragment.store.HotelInfoFragment;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeamIntroFragment extends Fragment {

    private String id;
    private ImageView logo;
    private TextView name;
    private TextView intro;
    private TextView region;
//    private TextView address;
    private TextView num;
    private RecyclerView mRecycler;
    private HotelPicAdapter mAdater;

    public TeamIntroFragment() {
        // Required empty public constructor
    }

    public static TeamIntroFragment newInstance(String id) {
        Bundle args = new Bundle();
        args.putString("id",id);
        TeamIntroFragment fragment = new TeamIntroFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team_intro, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        id = getArguments().getString("id");
        logo = (ImageView) view.findViewById(R.id.fragment_team_intro_pic);
        name = (TextView) view.findViewById(R.id.fragment_team_intro_name);
        intro = (TextView) view.findViewById(R.id.fragment_team_intro);
        region = (TextView) view.findViewById(R.id.fragment_team_intro_region);
//        address = (TextView) view.findViewById(R.id.fragment_team_intro_address);
        num = (TextView) view.findViewById(R.id.fragment_team_intro_num);
        mRecycler = (RecyclerView) view.findViewById(R.id.fragment_team_intro_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecycler.setLayoutManager(manager);
        mAdater = new HotelPicAdapter(getContext());
        mRecycler.setAdapter(mAdater);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getIdentity();
    }

    private void setData(ResultGetSupplierInfo result){
        name.setText(result.getName());
        intro.setText(result.getBriefinTroduction());
        if(result.getRegion() != null && !"".equals(result.getRegion())){
            region.setText(DBManager.getInstance(getContext()).getCityName(result.getRegion()));
        }else {
            region.setText("未知");
        }
        num.setText(result.getData().size() + "");
        mAdater.setSupId(result.getSupplierID());
        mAdater.refresh(result.getData());
        Glide.with(getContext()).load(result.getHeadportrait()).into(logo);
    }

    /**
     * 获取信息
     */
    private void getIdentity(){
        NetWorkUtil.setCallback("User/GetSupplierInfo",
                new BeanGetSupList(id, UserManager.getInstance(getContext()).getUserInfo().getUserID(),"0"),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultGetSupplierInfo result = new Gson().fromJson(respose, ResultGetSupplierInfo.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                setData(result);
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

//    /**
//     * 事件总线刷新
//     * @param bean
//     */
//    @Subscribe
//    public void onEventMainThread(final EventBusBean bean) {
//        getActivity().runOnUiThread(new Runnable() {
//            public void run() {
//                if(bean.getType() == Constants.DATA_HOTEL_INFO){
//                    setData(bean.getHotelInfo());
//                }
//            }
//        });
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        EventBus.getDefault().unregister(this);
//    }
}
