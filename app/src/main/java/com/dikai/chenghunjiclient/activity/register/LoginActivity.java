package com.dikai.chenghunjiclient.activity.register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.UserManager;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;

import dmax.dialog.SpotsDialog;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mForget;
    private TextView mRegister;
    private TextView mLogin;
    private EditText nameEdit;
    private EditText pwdEdit;
    private String name;
    private String pwd;
    private SpotsDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        mDialog = new SpotsDialog(this,R.style.DialogCustom);
        mForget = (TextView) findViewById(R.id.activity_login_forget);
        mRegister = (TextView) findViewById(R.id.activity_login_register);
        mLogin = (TextView) findViewById(R.id.activity_login_btn);
        nameEdit = (EditText) findViewById(R.id.activity_login_name);
        pwdEdit = (EditText) findViewById(R.id.activity_login_pwd);
        mForget.setOnClickListener(this);
        mRegister.setOnClickListener(this);
        mLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == mForget){
            startActivity(new Intent(this, SendCodeActivity.class).putExtra("type", Constants.USER_PHONE_RESET));
        }else if(v == mRegister){
            startActivity(new Intent(this, SelectIdentityActivity.class));
        }else if(v == mLogin){
            if(nameEdit.getText() != null && !"".equals(nameEdit.getText().toString().trim())){
                if(pwdEdit.getText() != null && !"".equals(pwdEdit.getText().toString().trim())){
                    name = nameEdit.getText().toString().trim();
                    pwd = pwdEdit.getText().toString().trim();
                    login();
                }else {
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy(); //必须调用该方法，防止内存泄漏
        super.onDestroy();
    }

    /**
     * 登录
     */
    private void login(){
        mDialog.show();
        UserManager.getInstance(this).login(name,pwd,new UserManager.OnLoginListener() {
            @Override
            public void onFinish() {
                mDialog.dismiss();
                Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
               // EventBus.getDefault().post(new EventBusBean(100));
                finish();
            }

            @Override
            public void onError(String e) {
                mDialog.dismiss();
                Toast.makeText(LoginActivity.this, e, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
