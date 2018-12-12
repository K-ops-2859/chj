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
import com.dikai.chenghunjiclient.adapter.store.SearchSupAdapter;
import com.dikai.chenghunjiclient.bean.BeanGetSups;
import com.dikai.chenghunjiclient.entity.NewSupsBean;
import com.dikai.chenghunjiclient.entity.ResultNewSups;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;

public class SearchSupActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEditText;
    private ImageView mClose;
    private SearchSupAdapter mAdapter;
    private MyLoadRecyclerView mRecyclerView;
    private int pageIndex = 1;
    private int itemCount = 20;
    private String title = "";
    private int type;
    private String areaID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_sup);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
//                .keyboardEnable(true)  //解决软键盘与底部输入框冲突问题
//                .keyboardEnable(true, WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE
//                                        | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)  //软键盘自动弹出
                .init();
        init();
    }

    private void init() {
        type = getIntent().getIntExtra("type",0);
        mEditText = (EditText) findViewById(R.id.activity_search_project_edit);
        mClose = (ImageView) findViewById(R.id.activity_search_project_close);
        mRecyclerView = (MyLoadRecyclerView) findViewById(R.id.activity_search_project_recycler);
        mAdapter = new SearchSupAdapter(this);

        if(type == 1){
            mAdapter.setSelect(true);
            mAdapter.setSelectedClickListener(new SearchSupAdapter.OnSelectedClickListener() {
                @Override
                public void onClick(int position, NewSupsBean bean) {
                    EventBus.getDefault().post(new EventBusBean(Constants.SUPPIPELINE_SELECT_SUP,bean));
                    finish();
                }
            });
        }

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
                getHomeSup(false, pageIndex, itemCount);
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

        String loca = UserManager.getInstance(this).getLocation();
        if(loca != null && !"".equals(loca)){
            String[] info = loca.split(",");
            areaID = info[0];
        }else {
            areaID = "1740";//默认黄岛
        }
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
        getHomeSup(true, pageIndex, itemCount);
    }

    /**
     * 获取供应商
     */
    private void getHomeSup(final boolean isRefresh, int pageIndex, int itemCount){
        NetWorkUtil.setCallback("HQOAApi/GetFacilitatorList",
                new BeanGetSups(areaID, "",pageIndex+"",itemCount+"",title),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        mRecyclerView.stopLoad();
                        try {
                            ResultNewSups result = new Gson().fromJson(respose, ResultNewSups.class);
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
                                Toast.makeText(SearchSupActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mRecyclerView.stopLoad();
                        Toast.makeText(SearchSupActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

//    private void getList(final boolean isRefresh, int pageIndex, int itemCount) {
//        UserInfo userInfo = UserManager.getInstance(this).getUserInfo();
//        NetWorkUtil.setCallback("User/GetSupplierInfoList",
//                new BeanGetSupplier(userInfo.getUserID(), "", "", "", title, pageIndex + "",itemCount + ""),
//                new NetWorkUtil.CallBackListener() {
//                    @Override
//                    public void onFinish(final String respose) {
//                        mRecyclerView.stopLoad();
//                        Log.e("返回值",respose);
//                        try {
//                            ResultGetSupList result = new Gson().fromJson(respose, ResultGetSupList.class);
//                            if ("200".equals(result.getMessage().getCode())) {
//                                mRecyclerView.setTotalCount(result.getTotalCount());
//                                if(isRefresh){
//                                    if(result.getData().size() == 0){
//                                        mRecyclerView.setHasData(false);
//                                    }else {
//                                        mRecyclerView.setHasData(true);
//                                    }
//                                    mAdapter.refresh(result.getData());
//                                }else {
//                                    mAdapter.addAll(result.getData());
//                                }
//                            } else {
//                                Toast.makeText(SearchSupActivity.this, "" + result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (Exception e) {
//                            Log.e("json解析出错",e.toString());
//                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onError(final String e) {
//                        mRecyclerView.stopLoad();
//                        Log.e("网络出错",e.toString());
//                    }
//                });
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
}
