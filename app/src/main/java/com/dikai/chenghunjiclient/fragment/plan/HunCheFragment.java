package com.dikai.chenghunjiclient.fragment.plan;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.plan.AddPlanActivity;
import com.dikai.chenghunjiclient.adapter.store.MainFragmentAdapter;
import com.dikai.chenghunjiclient.util.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HunCheFragment extends Fragment implements View.OnClickListener {

    private ImageView addPlan;
    private ViewPager mViewPager;
    private List<Fragment> mFragments;
    private TabLayout mTabLayout;
    private MainFragmentAdapter adapter;
    private CalendarFragment mCalendarFragment;
    private NewAddFragment mNewAddFragment;

    public HunCheFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hun_che, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewPager = (ViewPager) view.findViewById(R.id.fragment_add_viewpager);
        mTabLayout = (TabLayout) view.findViewById(R.id.fragment_add_tabs);
        addPlan = (ImageView) view.findViewById(R.id.fragment_add_add);
        addPlan.setOnClickListener(this);
        mFragments = new ArrayList<>();
        adapter = new MainFragmentAdapter(getChildFragmentManager(), mFragments);
        mCalendarFragment = CalendarFragment.newInstance(Constants.CALENDAR_USER_HUNCHE);
        mNewAddFragment = new NewAddFragment();
        Collections.addAll(mFragments, mCalendarFragment, mNewAddFragment);
        List<String> names = new ArrayList<>();
        Collections.addAll(names, "档期", "任务");
        adapter.setTitleList(names);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onClick(View v) {
        if(v == addPlan){
            startActivity(new Intent(getContext(), AddPlanActivity.class));
        }
    }
}
