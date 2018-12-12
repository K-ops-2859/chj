package com.dikai.chenghunjiclient.fragment.discover;


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
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.discover.DiscoverActivity;
import com.dikai.chenghunjiclient.activity.discover.PublishDynamicActivity;
import com.dikai.chenghunjiclient.activity.discover.PublishVideoActivity;
import com.dikai.chenghunjiclient.fragment.BaseFragmentAdapter;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.UserManager;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoverFragment extends Fragment {


    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private String[] tabs;
    private BaseFragmentAdapter fragmentAdapter;
//    private AllTrendsFragment dynamicFragment;
//    private MessageFragment messageFragment;
//    private VideoFragment mVideoFragment;
    private Fragment[] fragments;

    public DiscoverFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_discover, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        mTabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        ImageView ivNote = (ImageView) view.findViewById(R.id.iv_note);
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);

        tabs = new String[]{"全部","关注"};
//        dynamicFragment = new AllTrendsFragment();
//        mVideoFragment = VideoFragment.newInstance(false);
//        AttentionFragment attentionFragment = new AttentionFragment();
//        messageFragment = new MessageFragment();
        fragments = new Fragment[]{AllTrendsFragment.newInstance(2), AllTrendsFragment.newInstance(3)};
        if (fragmentAdapter == null) {
            fragmentAdapter = new BaseFragmentAdapter(getChildFragmentManager(), fragments, tabs);
        }
        mViewPager.setVisibility(View.VISIBLE);
        mViewPager.setAdapter(fragmentAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        ivNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(UserManager.getInstance(getContext()).checkLogin())
                getActivity().startActivity(new Intent(getContext(), PublishVideoActivity.class));
            }
        });
    }
}
