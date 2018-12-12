package com.dikai.chenghunjiclient.activity.me;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.store.ProjectInfoActivity;
import com.dikai.chenghunjiclient.adapter.me.MyCaseAdapter;
import com.dikai.chenghunjiclient.bean.BeanGetCase;
import com.dikai.chenghunjiclient.entity.CasesBean;
import com.dikai.chenghunjiclient.entity.ResultGetCase;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MyCaseActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mBack;
    private LinearLayout mAdd;
    private MyLoadRecyclerView mRecyclerView;
    private MyCaseAdapter mAdapter;
    private ActionSheetDialog moreDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_my_case);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        mBack = (ImageView) findViewById(R.id.activity_case_back);
        mAdd = (LinearLayout) findViewById(R.id.activity_case_add);
        mRecyclerView = (MyLoadRecyclerView) findViewById(R.id.activity_case_recycler);
        mAdapter = new MyCaseAdapter(this);
        mAdapter.setOnItemClickListener(new MyCaseAdapter.OnItemClickListener() {
            @Override
            public void onClick(CasesBean bean) {
                startActivity(new Intent(MyCaseActivity.this, ProjectInfoActivity.class)
                        .putExtra("sup",UserManager.getInstance(MyCaseActivity.this).getNewUserInfo().getFacilitatorId())
                        .putExtra("case",bean.getCaseID()).putExtra("type",1));
            }
        });

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLoadChangeListener(new MyLoadRecyclerView.OnLoadChangeListener() {
            @Override
            public void onRefresh() {
                refresh();
            }

            @Override
            public void onLoadMore() {
            }
        });
        mBack.setOnClickListener(this);
        mAdd.setOnClickListener(this);
        final String[] stringItems = {"图片案例", "视频案例"};
        moreDialog = new ActionSheetDialog(this, stringItems,null);
        moreDialog.isTitleShow(false)
                .setOnOperItemClickL(new OnOperItemClickL() {
                    @Override
                    public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                        moreDialog.dismiss();
                        if (position == 0) {
                            startActivity(new Intent(MyCaseActivity.this, NewProjectActivity.class).putExtra("type",0));
                        } else if (position == 1) {
                            startActivity(new Intent(MyCaseActivity.this, NewProjectActivity.class).putExtra("type",1));
                        }
                    }
                });
        refresh();
    }

    @Override
    public void onClick(View v) {
        if(v == mBack){
            onBackPressed();
        }else if(v == mAdd){
            moreDialog.show();
        }
    }

    private void refresh() {
        getCollection();
    }

    /**
     * 获取案例
     */
    private void getCollection(){
        NetWorkUtil.setCallback("HQOAApi/CaseInfoInfoList",
                new BeanGetCase(UserManager.getInstance(this).getNewUserInfo().getFacilitatorId()),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mRecyclerView.stopLoad();
                        Log.e("返回值",respose);
                        try {
                            ResultGetCase result = new Gson().fromJson(respose, ResultGetCase.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                mAdapter.refresh(result.getData());
                            } else {
                                Toast.makeText(MyCaseActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mRecyclerView.stopLoad();
                        Toast.makeText(MyCaseActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 事件总线刷新
     * @param bean
     */
    @Subscribe
    public void onEventMainThread(final EventBusBean bean) {
        runOnUiThread(new Runnable() {
            public void run() {
                if(bean.getType() == Constants.ADD_CASE_SUCCESS){
                    refresh();
                }else if(bean.getType() == Constants.EDIT_CASE_SUCCESS){
                    refresh();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        ImmersionBar.with(this).destroy(); //必须调用该方法，防止内存泄漏
        super.onDestroy();
    }
}
