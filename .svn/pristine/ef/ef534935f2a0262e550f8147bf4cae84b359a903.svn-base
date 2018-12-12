package com.dikai.chenghunjiclient.activity.wedding;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.store.CasePicAdapter;
import com.dikai.chenghunjiclient.entity.GetProjectBean;
import com.dikai.chenghunjiclient.view.MyRecyclerView;
import com.gyf.barlibrary.ImmersionBar;

import java.util.Arrays;

public class WedCaseInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView title;
    private TextView info;
    private TextView keyWord;
    private RecyclerView mRecycler;
    private CasePicAdapter mAdater;
    private GetProjectBean mProjectBean;
    private NestedScrollView mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wed_case_info);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        mProjectBean = (GetProjectBean) getIntent().getSerializableExtra("bean");
        title = (TextView) findViewById(R.id.title);
        mScrollView = (NestedScrollView) findViewById(R.id.scroll);
        info = (TextView) findViewById(R.id.info);
        keyWord = (TextView) findViewById(R.id.key_word);
        findViewById(R.id.back).setOnClickListener(this);
        mRecycler = (RecyclerView) findViewById(R.id.recycler);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(manager);
        mAdater = new CasePicAdapter(this);
        mRecycler.setAdapter(mAdater);
        mRecycler.setNestedScrollingEnabled(false);
        setData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                onBackPressed();
                break;
            default:
                break;
        }
    }

    private void setData() {
        title.setText(mProjectBean.getPlanTitle());
        info.setText(mProjectBean.getPlanContent());
        keyWord.setText(mProjectBean.getPlanKeyWord());
        mAdater.refresh(Arrays.asList(mProjectBean.getImgs().split(",")));
        mScrollView.smoothScrollTo(0,0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
}
