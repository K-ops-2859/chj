package com.dikai.chenghunjiclient.fragment.ad;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.store.MainFragmentAdapter;
import com.dikai.chenghunjiclient.bean.BeanNone;
import com.dikai.chenghunjiclient.entity.ADIdentBean;
import com.dikai.chenghunjiclient.entity.ResultGetADIdent;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewADFragment extends Fragment {

    private ViewPager mViewPager;
    private List<Fragment> mFragments;
    private TabLayout mTabLayout;
    private MainFragmentAdapter adapter;

    public NewADFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_ad, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewPager = (ViewPager) view.findViewById(R.id.fragment_add_viewpager);
        mTabLayout = (TabLayout) view.findViewById(R.id.fragment_add_tabs);
        mFragments = new ArrayList<>();
        adapter = new MainFragmentAdapter(getChildFragmentManager(), mFragments);

        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        getList();
    }

    private void setData(ResultGetADIdent result) {
        //排序
        List<String> names = new ArrayList<>();
        for (ADIdentBean bean : result.getData()) {
            if("99C06C5A-DDB8-46A1-B860-CD1227B4DB68".equalsIgnoreCase(bean.getIdentityId())) {//酒店
                mFragments.add(ADBeanFragment.newInstance(bean.getIdentityId(), bean.getTopImg()));
                names.add(bean.getName());
                break;
            }
        }
        for (ADIdentBean bean : result.getData()) {
            if("7DC8EDF8-A068-400F-AFD0-417B19DB3C7C".equalsIgnoreCase(bean.getIdentityId())) {//婚庆
                mFragments.add(ADBeanFragment.newInstance(bean.getIdentityId(), bean.getTopImg()));
                names.add(bean.getName());
                break;
            }
        }
        for (ADIdentBean bean : result.getData()) {
            if("ADF7BAAC-AD51-4605-99EE-C59A40BD165D".equalsIgnoreCase(bean.getIdentityId())) {//婚纱
                mFragments.add(ADBeanFragment.newInstance(bean.getIdentityId(), bean.getTopImg()));
                names.add(bean.getName());
                break;
            }
        }
        for (ADIdentBean bean : result.getData()) {
            if(!"ADF7BAAC-AD51-4605-99EE-C59A40BD165D".equalsIgnoreCase(bean.getIdentityId())
                    && !"7DC8EDF8-A068-400F-AFD0-417B19DB3C7C".equalsIgnoreCase(bean.getIdentityId())
                    && !"99C06C5A-DDB8-46A1-B860-CD1227B4DB68".equalsIgnoreCase(bean.getIdentityId())) {
                mFragments.add(ADBeanFragment.newInstance(bean.getIdentityId(), bean.getTopImg()));
                names.add(bean.getName());
            }
        }
        adapter.setTitleList(names);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    /**
     * 获取身份
     */
    private void getList(){
        NetWorkUtil.setCallback("HQOAApi/GetFacilitatorActivityIdentityList",
                new BeanNone(),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultGetADIdent result = new Gson().fromJson(respose, ResultGetADIdent.class);
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
//                        Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
