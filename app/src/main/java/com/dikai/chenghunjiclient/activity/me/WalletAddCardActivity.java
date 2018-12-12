package com.dikai.chenghunjiclient.activity.me;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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

public class WalletAddCardActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView next;
    private EditText blank;
    private EditText name;
    private EditText number;
    private EditText cardBlank;
    private EditText phone;
    private SpotsDialog mDialog;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        type = getIntent().getIntExtra("type",0);
        mDialog = new SpotsDialog(this,R.style.DialogCustom);
        findViewById(R.id.back).setOnClickListener(this);
        next = (TextView) findViewById(R.id.next);
        blank = (EditText) findViewById(R.id.blank);
        name = (EditText) findViewById(R.id.name);
        number = (EditText) findViewById(R.id.number);
        cardBlank = (EditText) findViewById(R.id.card_blank);
        phone = (EditText) findViewById(R.id.phone);
        next.setOnClickListener(this);
        next.setText(type == 0?"确定":"下一步");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.next:
                if(blank.getText() == null || "".equals(blank.getText().toString().trim())){
                    Toast.makeText(this, "所属银行不能为空！", Toast.LENGTH_SHORT).show();
                }else if(name.getText() == null || "".equals(name.getText().toString().trim())){
                    Toast.makeText(this, "账户名称不能为空！", Toast.LENGTH_SHORT).show();
                }else if(number.getText() == null || "".equals(number.getText().toString().trim())){
                    Toast.makeText(this, "开户账号不能为空！", Toast.LENGTH_SHORT).show();
                }else if(cardBlank.getText() == null || "".equals(cardBlank.getText().toString().trim())){
                    Toast.makeText(this, "开户行不能为空！", Toast.LENGTH_SHORT).show();
                }else if(phone.getText() == null || "".equals(phone.getText().toString().trim())){
                    Toast.makeText(this, "手机号不能为空！", Toast.LENGTH_SHORT).show();
                }else {
                    addAccount(number.getText().toString().trim(),name.getText().toString().trim(),cardBlank.getText().toString().trim(),
                            phone.getText().toString().trim(),blank.getText().toString().trim());
                }
                break;
        }
    }


    /**
     * 添加银行卡
     */
    private void addAccount(String number,String accountName,String cardBlank,String phone,String blank){
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
                new BeanAddWalletAccount(supId, 0,number,accountName,cardBlank,phone,blank,type),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {

                                Toast.makeText(WalletAddCardActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                                EventBus.getDefault().post(new EventBusBean(Constants.ADD_BLANK_ACCOUNT));
                                finish();
                            } else {
                                Toast.makeText(WalletAddCardActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Toast.makeText(WalletAddCardActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //设置按键不可用
    public void setGetCodeUnUse(){
        next.setClickable(false);
        next.setBackgroundResource(R.drawable.text_bg_gray_new);
        next.setTextColor(ContextCompat.getColor(this,R.color.gray_text));
        next.setEnabled(false);
    }

    //恢复按键可用
    public void setGetCodeUse(){
        next.setClickable(true);
        next.setBackgroundResource(R.drawable.text_bg_black_all);
        next.setTextColor(ContextCompat.getColor(this,R.color.gold));
        next.setEnabled(true);
    }
    
    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}
