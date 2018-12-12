package com.dikai.chenghunjiclient.activity.store;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.store.MyTicketAdapter;
import com.dikai.chenghunjiclient.bean.BeanGetMyTicket;
import com.dikai.chenghunjiclient.entity.MyTicketBean;
import com.dikai.chenghunjiclient.entity.ResultGetMyTicket;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyImageView;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;
import com.flyco.dialog.widget.base.BaseDialog;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.uuzuche.lib_zxing.activity.CodeUtils;

public class MyTicketActivity extends AppCompatActivity implements View.OnClickListener {

    private MyLoadRecyclerView mRecyclerView;
    private MyTicketAdapter mAdapter;
    private ServiceDialog mCodeDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ticket);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        findViewById(R.id.back).setOnClickListener(this);
        mRecyclerView = (MyLoadRecyclerView) findViewById(R.id.recycler);
        mRecyclerView.setLoadChangeListener(new MyLoadRecyclerView.OnLoadChangeListener() {
            @Override
            public void onRefresh() {
                refresh();
            }

            @Override
            public void onLoadMore() {

            }
        });

        mAdapter = new MyTicketAdapter(this);
        mAdapter.setOnItemClickListener(new MyTicketAdapter.OnItemClickListener() {
            @Override
            public void onClick(MyTicketBean bean) {
                mCodeDialog.setCode(bean.getATQR());
                mCodeDialog.show();
                mCodeDialog.setUiBeforShow();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mCodeDialog = new ServiceDialog(this);
        mCodeDialog.widthScale(1);
        mCodeDialog.heightScale(1);
        refresh();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
        }
    }

    private void refresh() {
        getTickets();
    }

    /**
     * 获取门票
     */
    private void getTickets(){
        NetWorkUtil.setCallback("HQOAApi/GetUserAdmissionTicket",
                new BeanGetMyTicket(UserManager.getInstance(this).getNewUserInfo().getUserId(),"2"),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mRecyclerView.stopLoad();
                        Log.e("返回值",respose);
                        try {
                            ResultGetMyTicket result = new Gson().fromJson(respose, ResultGetMyTicket.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                if(result.getData().size() == 0){
                                    mRecyclerView.setHasData(false);
                                }else {
                                    mRecyclerView.setHasData(true);
                                }
                                mAdapter.refresh(result.getData());
                            } else {
                                Toast.makeText(MyTicketActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mRecyclerView.stopLoad();
                        Toast.makeText(MyTicketActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public class ServiceDialog extends BaseDialog<ServiceDialog> implements View.OnClickListener {
        private MyImageView code;
        private String qrcode;
        public ServiceDialog(Context context) {
            super(context);
        }

        @Override
        public View onCreateView() {
            View view = View.inflate(mContext, R.layout.dialog_ticket_code_layout, null);
            return view;
        }

        @Override
        public void onViewCreated(View view) {
            super.onViewCreated(view);
            code = (MyImageView) view.findViewById(R.id.qrcode);
            view.findViewById(R.id.close).setOnClickListener(this);

        }

        public void setCode(String qrcode){
            this.qrcode = qrcode;
        }

        @Override
        public void setUiBeforShow() {
            code.setImageBitmap(CodeUtils.createImage(qrcode, 600, 600, null));
        }

        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.close){
                this.dismiss();
            }
        }
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}
