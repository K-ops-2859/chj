package com.dikai.chenghunjiclient.activity.discover;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.fragment.BaseFragmentAdapter;
import com.dikai.chenghunjiclient.fragment.discover.DynamicFragment;
import com.dikai.chenghunjiclient.fragment.discover.MessageFragment;
import com.gyf.barlibrary.ImmersionBar;

/**
 * Created by cmk03 on 2018/1/4.
 */

public class DiscoverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        ImageView ivNote = (ImageView) findViewById(R.id.iv_note);
        ViewPager mViewPager = (ViewPager) findViewById(R.id.view_pager);

        String[] tabs = {"动态", "资讯"};
        DynamicFragment dynamicFragment = new DynamicFragment();
       // AttentionFragment attentionFragment = new AttentionFragment();
        MessageFragment messageFragment = new MessageFragment();
        Fragment[] fragments = {dynamicFragment, messageFragment};

        BaseFragmentAdapter fragmentAdapter = new BaseFragmentAdapter(getSupportFragmentManager(), fragments, tabs);

        mViewPager.setAdapter(fragmentAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        ivNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DiscoverActivity.this, PublishDynamicActivity.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}
