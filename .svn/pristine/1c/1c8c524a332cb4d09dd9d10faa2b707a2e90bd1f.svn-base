package com.dikai.chenghunjiclient.activity.me;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.me.SearchAdapter;
import com.dikai.chenghunjiclient.bean.BeanInviteCar;
import com.dikai.chenghunjiclient.bean.BeanSearchCar;
import com.dikai.chenghunjiclient.entity.ResultGetCase;
import com.dikai.chenghunjiclient.entity.ResultSearchCar;
import com.dikai.chenghunjiclient.entity.SearchCarBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

public class SearchCarActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEditText;
    private ImageView mClose;
    private SearchAdapter mAdapter;
    private MyLoadRecyclerView mRecyclerView;
    private int pageIndex = 1;
    private int itemCount = 20;
    private String title = "";
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_car);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        type = getIntent().getStringExtra("type");
        mEditText = (EditText) findViewById(R.id.activity_search_project_edit);
        mClose = (ImageView) findViewById(R.id.activity_search_project_close);
        mRecyclerView = (MyLoadRecyclerView) findViewById(R.id.activity_search_project_recycler);
        mAdapter = new SearchAdapter(this);
        mAdapter.setMoreClickListener(new SearchAdapter.OnMoreClickListener() {
            @Override
            public void onClick(int position, SearchCarBean bean) {
               invite(bean.getUserID());
            }
        });

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
                getProject(false, pageIndex, itemCount);
            }
        });
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString() == null || "".equals(s.toString())){
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
        getProject(true, pageIndex, itemCount);
    }

    /**
     * 获取案例
     */
    private void invite(String id){
        NetWorkUtil.setCallback("User/AddDriverSupplierRelationship",
                new BeanInviteCar(id, UserManager.getInstance(this).getUserInfo().getSupplierID(),"1"),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultGetCase result = new Gson().fromJson(respose, ResultGetCase.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                Toast.makeText(SearchCarActivity.this, "邀请成功！", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SearchCarActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        Toast.makeText(SearchCarActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public void getProject(final boolean isRefresh, final int pageIndex, final int itemCount){
        NetWorkUtil.setCallback("User/MotorCadeList",
                new BeanSearchCar(UserManager.getInstance(this).getUserInfo().getSupplierID(),title,pageIndex+"",itemCount + ""),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mRecyclerView.stopLoad();
                        Log.e("返回值",respose);
                        try {
                            ResultSearchCar result = new Gson().fromJson(respose, ResultSearchCar.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                mRecyclerView.setTotalCount(result.getTotalCount());
                                if(isRefresh){
                                    if(result.getData().size() == 0){
                                        mRecyclerView.setHasData(false);
                                    }else {
                                        mRecyclerView.setHasData(true);
                                    }
                                    mAdapter.refresh(result.getData());
                                }else {
                                    mAdapter.addAll(result.getData());
                                }
                            } else {
                                Toast.makeText(SearchCarActivity.this, "" + result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mRecyclerView.stopLoad();
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
