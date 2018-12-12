package com.dikai.chenghunjiclient.activity.wedding;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.bean.BeanNewPeople;
import com.dikai.chenghunjiclient.entity.GetColorProjectData;
import com.dikai.chenghunjiclient.entity.UserInfo;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

public class SearchProjectActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEditText;
    private ImageView mClose;
    private ProjectAdapter mAdapter;
    private MyLoadRecyclerView mRecyclerView;
    private int pageIndex = 1;
    private int itemCount = 20;
    private String title = "";
    private String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_project);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
    private void init() {
       // type = getIntent().getStringExtra("type");
        mEditText = (EditText) findViewById(R.id.activity_search_project_edit);
        mClose = (ImageView) findViewById(R.id.activity_search_project_close);
        mRecyclerView = (MyLoadRecyclerView) findViewById(R.id.activity_search_project_recycler);
        mAdapter = new ProjectAdapter(this);
        mClose.setOnClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLoadChangeListener(new MyLoadRecyclerView.OnLoadChangeListener() {
            @Override
            public void onRefresh() {
                refresh();
            }

            @Override
            public void onLoadMore() {
                pageIndex++;
                getProject(false,"", "", pageIndex, itemCount);
            }
        });
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(TextUtils.isEmpty(s)){
                    title = "";
                }else {
                    title = s.toString();
                    refresh();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v == mClose){
            if(mEditText.getText() == null || "".equals(mEditText.getText().toString())){
                onBackPressed();
            }else {
                mEditText.setText("");
            }
        }
    }

    public void refresh(){
        pageIndex = 1;
        itemCount = 20;
        getProject(true, title, "", pageIndex, itemCount);
    }

    public void getProject(final boolean isRefresh, String title, String keyWord, final int pageIndex, final int itemCount) {
        NetWorkUtil.setCallback("HQOAApi/GetNewPeoplePlanList",
                new BeanNewPeople(title, keyWord, "", pageIndex + "", itemCount + "", "1"),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mRecyclerView.stopLoad();
                        Log.e("返回值", respose);
                        try {
                            GetColorProjectData result = new Gson().fromJson(respose, GetColorProjectData.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                mRecyclerView.setTotalCount(result.getTotalCount());
                                if (isRefresh) {
                                    if (result.getData().size() == 0) {
                                        mRecyclerView.setHasData(false);
                                    } else {
                                        mRecyclerView.setHasData(true);
                                        mAdapter.refresh(result.getData());
                                    }
                                } else {
                                    mAdapter.addAll(result.getData());
                                }
                            } else {
                                Toast.makeText(SearchProjectActivity.this, "" + result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错", e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mRecyclerView.stopLoad();
                        Log.e("网络出错", e.toString());
                    }
                });
    }
}
