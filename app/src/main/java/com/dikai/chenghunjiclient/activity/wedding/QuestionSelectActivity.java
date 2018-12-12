package com.dikai.chenghunjiclient.activity.wedding;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.wedding.QuestionSelectAdapter;
import com.dikai.chenghunjiclient.adapter.wedding.SelectBean;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionSelectActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView title;
    private MyLoadRecyclerView mRecyclerView;
    private QuestionSelectAdapter mAdapter;
    private List<SelectBean> selectedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_select);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        title = (TextView) findViewById(R.id.title);
        mRecyclerView = (MyLoadRecyclerView) findViewById(R.id.recycler);
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.finish).setOnClickListener(this);
        mAdapter = new QuestionSelectAdapter(this);
        mRecyclerView.setLoadChangeListener(new MyLoadRecyclerView.OnLoadChangeListener() {
            @Override
            public void onRefresh() {
                mRecyclerView.stopLoad();
                mAdapter.refresh(selectedList);
            }

            @Override
            public void onLoadMore() {

            }
        });
        mRecyclerView.setAdapter(mAdapter);
        title.setText(getIntent().getStringExtra("title"));
        String[] names = getIntent().getStringExtra("list").split(",");
        selectedList = new ArrayList<>();
        for (String name : names) {
            selectedList.add(new SelectBean(name));
        }
        mAdapter.refresh(selectedList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.finish:
                String seleced = "";
                for (SelectBean bean : mAdapter.getList()) {
                    if(bean.isSelected()){
                        if("".equals(seleced)){
                            seleced = seleced + bean.getName();
                        }else {
                            seleced = seleced + "," + bean.getName();
                        }
                    }
                }
                EventBus.getDefault().post(new EventBusBean(Constants.QUESTION_SLELECED,seleced));
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
}
