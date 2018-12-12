package com.dikai.chenghunjiclient.activity.invitation;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import com.dikai.chenghunjiclient.R;
import com.gyf.barlibrary.ImmersionBar;

/**
 * Created by cmk03 on 2018/10/15.
 */

public class ComInviteSuccActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cominvite_succ);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();

        initView();
    }

    private void initView() {
        int rank = getIntent().getIntExtra("rank",1);
        Toolbar mToolBar = (Toolbar) findViewById(R.id.toolbar);
//        ImageView ivLogo = (ImageView) findViewById(R.id.iv_logo);
        TextView tvNumber = (TextView) findViewById(R.id.tv_number);
        TextView tvGoOn = (TextView) findViewById(R.id.tv_go_on);

        LinearGradient mLinearGradient = new LinearGradient(0, 0, 0, tvNumber.getPaint().getTextSize(),
                Color.parseColor("#FFA359"), Color.parseColor("#FF5D76"), Shader.TileMode.CLAMP);
        tvNumber.getPaint().setShader(mLinearGradient);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvGoOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvNumber.setText("" + rank);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }

}
