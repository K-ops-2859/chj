package com.dikai.chenghunjiclient.activity.store;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.dikai.chenghunjiclient.R;
import com.gyf.barlibrary.ImmersionBar;

/**
 * Created by cmk03 on 2018/10/9.
 */

public class RefuseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refuse);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();

        Toolbar mToolBar = (Toolbar) findViewById(R.id.toolbar);
        TextView tvYuanYin = (TextView) findViewById(R.id.tv_yuanyin);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvYuanYin.setText(getIntent().getStringExtra("reason"));
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}
