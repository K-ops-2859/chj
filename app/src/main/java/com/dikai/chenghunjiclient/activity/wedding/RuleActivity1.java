package com.dikai.chenghunjiclient.activity.wedding;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.wedding.ExpandRuleAdapter;
import com.dikai.chenghunjiclient.bean.RuleBean;
import com.dikai.chenghunjiclient.entity.ActivityRuleBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import java.util.List;

/**
 * Created by cmk03 on 2017/12/15.
 */

public class RuleActivity1 extends AppCompatActivity {

    private ExpandableListView expandListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rule1);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        ImageView mBack = (ImageView) findViewById(R.id.activity_zone_back);
        expandListView = (ExpandableListView) findViewById(R.id.expand_list_view);

        initData();

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void upData(List<ActivityRuleBean.GradeDataList> data) {
        ExpandRuleAdapter mAdapter = new ExpandRuleAdapter(this, data);
        expandListView.setAdapter(mAdapter);
        expandListView.setGroupIndicator(null);

        expandListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                Toast.makeText(RuleActivity1.this, "第" + i + "组被点击了", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

//        expandListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//            @Override
//            public void onGroupExpand(int i) {
//                Toast.makeText(RuleActivity1.this, "第" + i + "组展开", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        expandListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
//
//            @Override
//            public void onGroupCollapse(int groupPosition) {
//                Toast.makeText(RuleActivity1.this, "第" + groupPosition + "组合拢", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        expandListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v,
//                                        int groupPosition, int childPosition, long id) {
//                Toast.makeText(RuleActivity1.this, "第" + groupPosition + "组的第" + childPosition + "被点击了", Toast.LENGTH_SHORT).show();
//                return true;
//            }
//        });
    }

    private void initData() {
        NetWorkUtil.setCallback("Corp/ByIdsGetActivityInfo", new RuleBean("1,3"), new NetWorkUtil.CallBackListener() {

            private String title;

            @Override
            public void onFinish(String respose) {
                ActivityRuleBean ruleBean = new Gson().fromJson(respose, ActivityRuleBean.class);
                if ("200".equals(ruleBean.getMessage().getCode())) {
                    List<ActivityRuleBean.GradeDataList> gradeData = ruleBean.getGradeData();
                    upData(gradeData);
                }
            }

            @Override
            public void onError(String e) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
}
