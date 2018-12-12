package com.dikai.chenghunjiclient.activity.wedding;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;

public class WedMarkActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText remark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wed_mark);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        String mark = getIntent().getStringExtra("info");
        remark = (EditText) findViewById(R.id.remark);
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.finish).setOnClickListener(this);
        if(mark!=null) remark.setText(mark);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.finish:
                if(remark.getText()!= null && !"".equals(remark.getText().toString().trim())){
                    EventBus.getDefault().post(new EventBusBean(Constants.WEDDING_REMARK,remark.getText().toString()));
                    finish();
                }else {
                    EventBus.getDefault().post(new EventBusBean(Constants.WEDDING_REMARK,""));
                    finish();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}
