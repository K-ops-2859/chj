package com.dikai.chenghunjiclient.activity.plan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dikai.chenghunjiclient.R;
import com.gyf.barlibrary.ImmersionBar;

public class RemarkActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mBack;
    private TextView mFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remark);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        mBack = (ImageView) findViewById(R.id.activity_remark_info_back);
        mFinish = (TextView) findViewById(R.id.activity_remark_info_finish);
        mBack.setOnClickListener(this);
        mFinish.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == mBack){
            onBackPressed();
        }else if(v == mFinish){

        }
    }
    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy(); //必须调用该方法，防止内存泄漏
        super.onDestroy();
    }
}
