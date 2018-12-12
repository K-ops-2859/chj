package com.dikai.chenghunjiclient.activity.me;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.bean.BeanAddWalletAccount;
import com.dikai.chenghunjiclient.entity.NewUserInfo;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;

import dmax.dialog.SpotsDialog;

public class AddAliPayActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEditText;
    private EditText mEditName;
    private SpotsDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ali_pay);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        mDialog = new SpotsDialog(this,R.style.DialogCustom);
        mEditText = (EditText) findViewById(R.id.number);
        mEditName = (EditText) findViewById(R.id.name);
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.next).setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.next:
                if(mEditText.getText() == null||"".equals(mEditText.getText().toString().trim())){
                    Toast.makeText(this, "请输入支付宝账号！", Toast.LENGTH_SHORT).show();
                }else if(mEditName.getText() == null||"".equals(mEditName.getText().toString().trim())){
                    Toast.makeText(this, "请输入支付宝账号真实姓名！", Toast.LENGTH_SHORT).show();
                }else {
                    addAccount(mEditText.getText().toString().trim(),mEditName.getText().toString().trim());
                }
                break;
        }
    }


    /**
     * 添加支付宝账号
     */
    private void addAccount(String number,String name){
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
        NetWorkUtil.setCallback("HQOAApi/CreateFacilitatorAccountNumberInfo",
                new BeanAddWalletAccount(supId,
                        1,number, name,"","","",type),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                Toast.makeText(AddAliPayActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                                EventBus.getDefault().post(new EventBusBean(Constants.ADD_ALIPAY_ACCOUNT));
                                finish();
                            } else {
                                Toast.makeText(AddAliPayActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Toast.makeText(AddAliPayActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}
