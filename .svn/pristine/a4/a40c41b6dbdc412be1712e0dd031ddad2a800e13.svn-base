package com.dikai.chenghunjiclient.activity.wedding;


import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.OnItemClickListener;
import com.dikai.chenghunjiclient.adapter.wedding.RuleAdapter;
import com.dikai.chenghunjiclient.bean.RuleBean;
import com.dikai.chenghunjiclient.entity.ActivityRuleBean;
import com.dikai.chenghunjiclient.entity.DataTreeEntity;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.view.ViewWrapper;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cmk03 on 2017/11/15.
 */

public class RuleActivity extends AppCompatActivity implements View.OnClickListener {

    private String[] rules;
    private boolean marryExpand = false;
    private boolean downLoadExpand = false;
    private boolean isExpand = false;
    private int layoutHeight;
    private int expandHeight;
    private List<DataTreeEntity> mData = new ArrayList<>();
    private RuleAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rule);

        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        ImageView mBack = (ImageView) findViewById(R.id.activity_zone_back);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RuleAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        initData();

        mBack.setOnClickListener(this);
//        mAdapter.setOnItemClickListener(new OnItemClickListener<ActivityRuleBean.GradeDataList>() {
//            @Override
//            public void onItemClick(View view, int position, ActivityRuleBean.GradeDataList gradeDataList) {
//                System.out.println("====================================");
//                if (!isExpand) {
//                    mAdapter.addChild(position);
//
//                    Log.e("点击'", position + "");
//                    isExpand = !isExpand;
//                } else {
//                    mAdapter.remove(position);
//
//                    isExpand = !isExpand;
//                }
//            }
//        });
    }

    private void layoutStartAnimation(View target) {
        if (layoutHeight == 0) {
            layoutHeight = target.getLayoutParams().height;
        }
        System.out.println("===============高度" + expandHeight);
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(new ViewWrapper(target), "height", expandHeight);
        objectAnimator.setDuration(400);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.start();
    }

    private void layoutEndAnimation(View target) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(new ViewWrapper(target), "height", layoutHeight);
        objectAnimator.setDuration(400);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.start();
    }

    private void imageStartAnimation(View target) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(target, "rotation", 0, 180);
        objectAnimator.setDuration(400).start();
    }

    private void imageEndAnimation(View target) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(target, "rotation", 180, 360);
        objectAnimator.setDuration(400).start();
    }

    private void initData() {
        NetWorkUtil.setCallback("Corp/ByIdsGetActivityInfo", new RuleBean("1,3"), new NetWorkUtil.CallBackListener() {

            private String title;

            @Override
            public void onFinish(String respose) {
                ActivityRuleBean ruleBean = new Gson().fromJson(respose, ActivityRuleBean.class);
                if ("200".equals(ruleBean.getMessage().getCode())) {
                    List<ActivityRuleBean.GradeDataList> gradeData = ruleBean.getGradeData();
                    mAdapter.setList(gradeData);
                }
            }

            @Override
            public void onError(String e) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_zone_back:
                finish();
                break;
            default:
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
}
