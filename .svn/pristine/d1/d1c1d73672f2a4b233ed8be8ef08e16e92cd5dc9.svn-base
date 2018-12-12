package com.dikai.chenghunjiclient.activity.wedding;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.register.LoginActivity;
import com.dikai.chenghunjiclient.bean.BeanAddCustom;
import com.dikai.chenghunjiclient.entity.NewUserInfo;
import com.dikai.chenghunjiclient.entity.ResultAddCustom;
import com.dikai.chenghunjiclient.entity.UserInfo;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.joooonho.SelectableRoundedImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import dmax.dialog.SpotsDialog;

public class MakeProjectActivity extends AppCompatActivity implements View.OnClickListener {

    private int type;
    private ImageView groomLogo;
    private ImageView brideLogo;
    private TextView groomText;
    private TextView brideText;
    private EditText nameEdit;
    private EditText phoneEdit;
//    private EditText codeEdit;
    private EditText otherPhoneEdit;
    private SpotsDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_project);
        EventBus.getDefault().register(this);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.gray_background)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        mDialog = new SpotsDialog(this,R.style.DialogCustom);
        groomLogo = (ImageView) findViewById(R.id.groom_logo);
        groomText = (TextView) findViewById(R.id.groom_text);
        brideLogo = (ImageView) findViewById(R.id.bride_logo);
        brideText = (TextView) findViewById(R.id.bride_text);
        nameEdit = (EditText) findViewById(R.id.ev_name);
        phoneEdit = (EditText) findViewById(R.id.ev_phone);
//        codeEdit = (EditText) findViewById(R.id.ev_code);
        otherPhoneEdit = (EditText) findViewById(R.id.ev_other_phone);
        findViewById(R.id.activity_wedding_back).setOnClickListener(this);
        findViewById(R.id.tv_finish).setOnClickListener(this);
        groomLogo.setOnClickListener(this);
        brideLogo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_wedding_back:
                onBackPressed();
                break;
            case R.id.tv_finish:
                if(!UserManager.getInstance(MakeProjectActivity.this).isLogin()){
                    startActivity(new Intent(MakeProjectActivity.this, LoginActivity.class));
                }else if(nameEdit.getText() == null || "".equals(nameEdit.getText().toString().trim())){
                    Toast.makeText(this, "姓名还没填哦", Toast.LENGTH_SHORT).show();
                }else if(phoneEdit.getText() == null || "".equals(phoneEdit.getText().toString().trim())){
                    Toast.makeText(this, "手机还没填哦！", Toast.LENGTH_SHORT).show();
                }else if(otherPhoneEdit.getText() == null || "".equals(otherPhoneEdit.getText().toString().trim())){
                    Toast.makeText(this, "TA的手机还没填哦", Toast.LENGTH_SHORT).show();
                }else {
                    String code = "";
//                    if(codeEdit.getText() != null && "".equals(codeEdit.getText().toString().trim())){
//                        code = codeEdit.getText().toString().trim();
//                    }
                    if(type == 0){
                        addCustom(nameEdit.getText().toString().trim(),phoneEdit.getText().toString().trim(),
                                "",otherPhoneEdit.getText().toString().trim(),type,code);
                    }else {
                        addCustom("",otherPhoneEdit.getText().toString().trim(),
                                nameEdit.getText().toString().trim(),phoneEdit.getText().toString().trim(),type,code);
                    }
                }
                break;
            case R.id.groom_logo:
                setGroom();
                break;
            case R.id.bride_logo:
                setBride();
                break;
        }
    }

    private void setGroom(){
        type = 0;
        groomLogo.setImageResource(R.mipmap.man_02);
        groomText.setTextColor(ContextCompat.getColor(this,R.color.black));
        brideLogo.setImageResource(R.mipmap.women_01);
        brideText.setTextColor(ContextCompat.getColor(this,R.color.gray_text));
    }
    private void setBride(){
        type = 1;
        groomLogo.setImageResource(R.mipmap.man_01);
        groomText.setTextColor(ContextCompat.getColor(this,R.color.gray_text));
        brideLogo.setImageResource(R.mipmap.women_02);
        brideText.setTextColor(ContextCompat.getColor(this,R.color.black));
    }


    private void addCustom(String groomName,String groomPhone,String brideName,String bridePhone, int type, String code) {
        NewUserInfo userInfo = UserManager.getInstance(this).getNewUserInfo();
        NetWorkUtil.setCallback("HQOAApi/AddNewPeopleCustom",
                new BeanAddCustom(userInfo.getUserId(),groomName,groomPhone,brideName,bridePhone,type,code),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultAddCustom result = new Gson().fromJson(respose, ResultAddCustom.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                startActivity(new Intent(MakeProjectActivity.this,WedProjectActivity.class)
                                        .putExtra("id",result.getNewPeopleCustomID()));
                            } else {
                                Toast.makeText(MakeProjectActivity.this, "" + result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        Log.e("网络出错",e.toString());
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
                if(bean.getType() == Constants.WED_PUBLISH){
                    finish();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}
