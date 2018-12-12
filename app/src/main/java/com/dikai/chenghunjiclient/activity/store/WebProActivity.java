package com.dikai.chenghunjiclient.activity.store;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.store.CasePicAdapter;
import com.dikai.chenghunjiclient.bean.BeanGetWebPro;
import com.dikai.chenghunjiclient.entity.ResultGetWebPro;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import java.util.Arrays;

public class WebProActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView title;
    private TextView info;
    private TextView keyWord;
    private RecyclerView mRecycler;
    private CasePicAdapter mAdater;
    //    private GetProjectBean mProjectBean;
    private String id;
    private NestedScrollView mScrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_pro);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
//        mProjectBean = (GetProjectBean) getIntent().getSerializableExtra("bean");
        id = getIntent().getStringExtra("id");
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
//        setData();
        getInfo();
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

    //    private void setData() {
//        title.setText(mProjectBean.getPlanTitle());
//        info.setText(mProjectBean.getPlanContent());
//        keyWord.setText(mProjectBean.getPlanKeyWord());
//        mAdater.refresh(Arrays.asList(mProjectBean.getImgs().split(",")));
//    }
    private void setData(ResultGetWebPro result) {
        title.setText(result.getPlanTitle());
        info.setText(result.getPlanContent());
        keyWord.setText(result.getPlanKeyWord());
        mAdater.refresh(Arrays.asList(result.getImgs().split(",")));
        mScrollView.scrollTo(0,0);
    }

    /**
     * 获取方案详情
     */
    private void getInfo(){
        NetWorkUtil.setCallback("HQOAApi/GetPlanInfo",
                new BeanGetWebPro(id),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultGetWebPro result = new Gson().fromJson(respose, ResultGetWebPro.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                setData(result);
                            } else {
                                Toast.makeText(WebProActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(WebProActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        Toast.makeText(WebProActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
}
