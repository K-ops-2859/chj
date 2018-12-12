package com.dikai.chenghunjiclient.activity.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.util.ActivityManager;
import com.gyf.barlibrary.ImmersionBar;

/**
 * Created by cmk03 on 2018/3/2.
 */

public class TiXianCompleteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tixian_commed);

        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView tvTiXian = (TextView) findViewById(R.id.tv_tixian);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvTiXian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityManager.getInstance().addActivity(TiXianCompleteActivity.this);
                startActivity(new Intent(TiXianCompleteActivity.this, TiXianListActivity.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}
