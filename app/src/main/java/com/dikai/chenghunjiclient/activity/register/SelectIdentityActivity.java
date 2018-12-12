package com.dikai.chenghunjiclient.activity.register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.store.CorpInfoActivity;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.UserManager;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class SelectIdentityActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout mIdentity1;
    private LinearLayout mIdentity2;
    private LinearLayout mIdentity3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_identity);
        EventBus.getDefault().register(this);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarAlpha(0.0f)
                .fullScreen(true)
                .init();
        init();
    }

    private void init() {
        mIdentity1 = (LinearLayout) findViewById(R.id.activity_select_identity_1);
        mIdentity2 = (LinearLayout) findViewById(R.id.activity_select_identity_2);
        mIdentity3 = (LinearLayout) findViewById(R.id.activity_select_identity_3);

        mIdentity1.setOnClickListener(this);
        mIdentity2.setOnClickListener(this);
        mIdentity3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == mIdentity1){
            startActivity(new Intent(this, SendCodeActivity.class).putExtra("type",Constants.USER_NEWS_REGISTER));
        }else if(v == mIdentity2){
            startActivity(new Intent(this, SendCodeActivity.class).putExtra("type", Constants.USER_PHONE_REGISTER));
        }else if(v == mIdentity3){
            startActivity(new Intent(this, RegisterCorpActivity.class).putExtra("type", Constants.USER_PHONE_REGISTER));
        }
    }

    /**
     * 事件总线刷新
     * @param bean
     */
    @Subscribe
    public void onEventMainThread(final EventBusBean bean) {
        runOnUiThread(new Runnable() {
            public void run() {
                if(bean.getType() == Constants.USER_REGISTER_SUCCESS){
                    finish();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
