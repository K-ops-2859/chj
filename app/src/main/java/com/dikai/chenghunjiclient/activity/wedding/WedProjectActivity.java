package com.dikai.chenghunjiclient.activity.wedding;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.store.MainFragmentAdapter;
import com.dikai.chenghunjiclient.bean.BeanUserInfo;
import com.dikai.chenghunjiclient.entity.NewUserInfo;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.entity.UserInfo;
import com.dikai.chenghunjiclient.fragment.wedding.MyProFragment;
import com.dikai.chenghunjiclient.fragment.wedding.WeProFragment;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WedProjectActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private ViewPager mViewPager;
    private List<Fragment> mFragments;
    private MainFragmentAdapter mPagerAdapter;
    private RadioGroup mTitleRadioGroup;
    private MaterialDialog dialog;
    private int state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wed_project);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.colorPrimary)
                .init();
        init();
    }

    private void init() {
        state = getIntent().getIntExtra("state",0);
        Log.e("WedProjectActivity---","state: " + state);
        mTitleRadioGroup = (RadioGroup)findViewById(R.id.activity_bill_ridiogroup);
        mViewPager = (ViewPager)findViewById(R.id.activity_bill_pager);
        findViewById(R.id.publish).setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);
        mFragments = new ArrayList<>();
        Collections.addAll(mFragments, MyProFragment.newInstance(state), WeProFragment.newInstance(state));
        mPagerAdapter = new MainFragmentAdapter(getSupportFragmentManager(), mFragments);
        List<String> titles = new ArrayList<>();
        Collections.addAll(titles, "我的", "我们的");
        mPagerAdapter.setTitleList(titles);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.addOnPageChangeListener(this);
        mTitleRadioGroup.setOnCheckedChangeListener(this);
        dialog = new MaterialDialog(this);
        dialog.isTitleShow(true)//
                .btnNum(2)
                .title("您确定要提交吗")
                .titleTextSize(17)
                .content("提交之后，将无法再更改,请确保信息无误后提交")//
                .btnText("取消", "确定")
                .setOnBtnClickL(new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                    }
                }, new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                        publish();
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.publish:
                if(state == 1){
                    Toast.makeText(this, "已提交过", Toast.LENGTH_SHORT).show();
                }else {
                    dialog.show();
                }
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(checkedId == R.id.activity_bill_ridio_left){
            mViewPager.setCurrentItem(0);
        }else {
            mViewPager.setCurrentItem(1);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if(position == 0){
            mTitleRadioGroup.check(R.id.activity_bill_ridio_left);
        }else {
            mTitleRadioGroup.check(R.id.activity_bill_ridio_right);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void publish() {
        NewUserInfo userInfo = UserManager.getInstance(this).getNewUserInfo();
        NetWorkUtil.setCallback("HQOAApi/SubmitNewPeople",
                new BeanUserInfo(userInfo.getUserId()),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                EventBus.getDefault().post(new EventBusBean(Constants.WED_PUBLISH));
                                finish();
                            } else {
                                Toast.makeText(WedProjectActivity.this, "" + result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        Log.e("网络出错",e.toString());
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
}
