package com.dikai.chenghunjiclient.activity.store;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.store.SearchJiedanrenAdapter;
import com.dikai.chenghunjiclient.bean.BeanPhone;
import com.dikai.chenghunjiclient.entity.JiedanRenBean;
import com.dikai.chenghunjiclient.entity.ResultSearchJiedanren;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.SearchManager;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;

public class SearchJiedanrenActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mEditText;
    private ImageView mClose;
    private SearchJiedanrenAdapter mAdapter;
    private MyLoadRecyclerView mRecyclerView;
    private String phone = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_jiedanren);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
//                .keyboardEnable(true)  //解决软键盘与底部输入框冲突问题
//                .keyboardEnable(true, WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE
//                        | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)  //软键盘自动弹出
                .init();
        init();
    }

    private void init() {
        mEditText = (EditText) findViewById(R.id.activity_search_project_edit);
        mClose = (ImageView) findViewById(R.id.activity_search_project_close);
        mRecyclerView = (MyLoadRecyclerView) findViewById(R.id.activity_search_project_recycler);
        mAdapter = new SearchJiedanrenAdapter(this);
        mAdapter.setOnItemClickListener(new SearchJiedanrenAdapter.OnItemClickListener() {
            @Override
            public void onClick(JiedanRenBean bean) {
                EventBus.getDefault().post(new EventBusBean(Constants.ADD_JIEDANREN,bean));
                SearchManager.getInstance(SearchJiedanrenActivity.this).addRecord(bean);
                finish();
            }
        });
        mClose.setOnClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setNoData("未查询到用户信息，请输入正确的成婚纪用户手机号");
        mRecyclerView.setLoadChangeListener(new MyLoadRecyclerView.OnLoadChangeListener() {
            @Override
            public void onRefresh() {
                refresh();
            }

            @Override
            public void onLoadMore() {
            }
        });
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if("".equals(s.toString())){
                    phone = "";
                }else if(s.toString().length() >= 11){
                    phone = s.toString();
                    refresh();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        mAdapter.refresh(SearchManager.getInstance(SearchJiedanrenActivity.this).getRecord());
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
        getHomeSup();
    }

    /**
     * 获取供应商
     */
    private void getHomeSup(){
        NetWorkUtil.setCallback("HQOAApi/GetUserInfoByPhone",
                new BeanPhone(phone),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        mRecyclerView.stopLoad();
                        try {
                            ResultSearchJiedanren result = new Gson().fromJson(respose, ResultSearchJiedanren.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                if(result.getData().size() == 0){
                                    mRecyclerView.setHasData(false);
                                }else {
                                    mRecyclerView.setHasData(true);
                                }
                                mAdapter.refresh(result.getData());
                            } else {
                                Toast.makeText(SearchJiedanrenActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mRecyclerView.stopLoad();
                        Toast.makeText(SearchJiedanrenActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}

