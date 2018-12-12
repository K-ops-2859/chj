package com.dikai.chenghunjiclient.activity.me;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.bean.BeanAddWithDraw;
import com.dikai.chenghunjiclient.entity.NewUserInfo;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.entity.WalletAccountBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import dmax.dialog.SpotsDialog;

public class AliPayWithDrawActivity extends AppCompatActivity implements View.OnClickListener {

    private String money;
    private WalletAccountBean mAccountBean;
    private SpotsDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ali_pay_with_draw);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        mDialog = new SpotsDialog(this,R.style.DialogCustom);
        money = getIntent().getStringExtra("money");
        mAccountBean = (WalletAccountBean) getIntent().getSerializableExtra("bean");
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.next).setOnClickListener(this);
        findViewById(R.id.change).setOnClickListener(this);
        TextView info = (TextView) findViewById(R.id.info);
        TextView number = (TextView) findViewById(R.id.number);
        TextView change = (TextView) findViewById(R.id.change);
        if(mAccountBean == null){
            Toast.makeText(this, "无效的账户信息！", Toast.LENGTH_SHORT).show();
        }else {
            number.setText(mAccountBean.getNumber());
            if(mAccountBean.getSourceType() == 0){
                change.setText("更改银行卡");
                info.setText("确定要将 ￥"+ money +" 提现至改银行卡吗？");
            }else {
                change.setText("更改账户");
                info.setText("确定要将 ￥"+money+" 提现至该支付宝账户吗？");
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.change:
                if(mAccountBean.getSourceType() == 0){
                    startActivity(new Intent(this, WalletAddCardActivity.class));
                }else {
                    startActivity(new Intent(this, AddAliPayActivity.class));
                }
                finish();
                break;
            case R.id.next:
                withdraw();
                break;
        }
    }

    /**
     * 提现
     */
    private void withdraw(){
        mDialog.show();
        NewUserInfo userInfo = UserManager.getInstance(this).getNewUserInfo();
        String supId;
        int type;
        if("70CD854E-D943-4607-B993-91912329C61E".equals(userInfo.getProfession().toUpperCase())){
            supId = userInfo.getUserId();
            type = 1;
        }else {
            supId = userInfo.getFacilitatorId();
            type = 2;
        }
        NetWorkUtil.setCallback("HQOAApi/CreateFacilitatorApplicationCash",
                new BeanAddWithDraw(supId, mAccountBean.getId(),money,type),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                Toast.makeText(AliPayWithDrawActivity.this, "提现申请已提交，请耐心等待", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(AliPayWithDrawActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Toast.makeText(AliPayWithDrawActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}
