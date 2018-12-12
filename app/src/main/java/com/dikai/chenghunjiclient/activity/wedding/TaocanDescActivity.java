package com.dikai.chenghunjiclient.activity.wedding;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.awen.photo.controller.ToolBarHelper;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.bean.TaocanBean;
import com.dikai.chenghunjiclient.entity.TaocanData;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

/**
 * Created by cmk03 on 2018/6/8.
 */

public class TaocanDescActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taocan_desc);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        Toolbar mToolBar = (Toolbar) findViewById(R.id.toolbar);
        TextView tvDesc = (TextView) findViewById(R.id.tv_desc);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvDesc.setText(getIntent().getStringExtra("info"));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
}
