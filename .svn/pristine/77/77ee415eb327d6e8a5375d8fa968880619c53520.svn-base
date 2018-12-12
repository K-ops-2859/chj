  package com.dikai.chenghunjiclient.activity.me;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.bean.BeanUpFiledType;
import com.dikai.chenghunjiclient.entity.CustomerInfoBySupplierData;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.entity.UserInfo;
import com.dikai.chenghunjiclient.entity.VideoPhotoData;
import com.dikai.chenghunjiclient.fragment.BaseFragmentAdapter;
import com.dikai.chenghunjiclient.fragment.me.AllFragment;
import com.dikai.chenghunjiclient.fragment.me.FailFragment;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.flyco.dialog.widget.base.BaseDialog;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

/**
 * Created by cmk03 on 2018/3/20.
 */

public class VideoPhotoDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private BaseFragmentAdapter fragmentAdapter;
    private int tabPage = 0;
    private CustomerInfoBySupplierData.DataList data;
    private FrameLayout flPass;
    private ServiceDialog ruleDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_photo_details);

        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.rule).setOnClickListener(this);
        ImageView ivSearch = (ImageView) findViewById(R.id.iv_search);
        flPass = (FrameLayout) findViewById(R.id.fl_pass);
        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        ViewPager mViewPager = (ViewPager) findViewById(R.id.view_pager);

        data = (CustomerInfoBySupplierData.DataList) getIntent().getSerializableExtra("data");

        final String[] tabs = {"全部照片", "不合格照片"};
        AllFragment allFragment = AllFragment.newInstance(String.valueOf(data.getCustomerid()));
        FailFragment failFragment = FailFragment.newInstance(String.valueOf(data.getCustomerid()));
        Fragment[] fragments = new Fragment[]{allFragment, failFragment};
        if (fragmentAdapter == null) {
            fragmentAdapter = new BaseFragmentAdapter(getSupportFragmentManager(), fragments, tabs);
        }
        fragmentAdapter.setOnTabIndex(new BaseFragmentAdapter.OnTabIndex() {
            @Override
            public void tabIndex(int index) {
                tabPage = index;
            }
        });
        mViewPager.setVisibility(View.VISIBLE);
        mViewPager.setAdapter(fragmentAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabTextColors(ContextCompat.getColor(this, R.color.black), ContextCompat.getColor(this, R.color.main));

        mViewPager.setAdapter(fragmentAdapter);
        flPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passState();
            }
        });
        ruleDialog = new ServiceDialog(this);
        ruleDialog.widthScale(1);
        ruleDialog.heightScale(1);
    }

    private void passState() {
        UserInfo userInfo = UserManager.getInstance(this).getUserInfo();
        NetWorkUtil.setCallback("Corp/UpFiledType", new BeanUpFiledType(data.getCustomerid(), userInfo.getCorpID(), "1", "1"), new NetWorkUtil.CallBackListener() {
            @Override
            public void onFinish(String respose) {
                ResultMessage message = new Gson().fromJson(respose, ResultMessage.class);
                if (message.getMessage().getCode().equals("200")) {
                    Toast.makeText(VideoPhotoDetailsActivity.this, "操作完成", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(VideoPhotoDetailsActivity.this, message.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String e) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.rule:
                ruleDialog.show();
                break;
        }
    }

    /**
     *rule
     */
    private class ServiceDialog extends BaseDialog<ServiceDialog> implements View.OnClickListener {
        private TextView close;
        public ServiceDialog(Context context) {
            super(context);
        }
        @Override
        public View onCreateView() {
            View view = View.inflate(mContext, R.layout.dialog_photo_video_know, null);
            close = (TextView) view.findViewById(R.id.close);
            close.setOnClickListener(this);
            return view;
        }
        @Override
        public void onViewCreated(View view) {
            super.onViewCreated(view);
        }
        @Override
        public void setUiBeforShow() {}
        @Override
        public void onClick(View v) {
            if(v == close){
                this.dismiss();
            }
        }
    }

}
