package com.dikai.chenghunjiclient.activity.register;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.bean.BeanRegFirm;
import com.dikai.chenghunjiclient.entity.BeanFirmInfo;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.MD5Util;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UpLoadImgThread;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class FirmRegeditActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private ImageView mBack;
    private EditText nameEdit;
    private EditText emailEdit;
    private EditText pwdEdit;
    private EditText pwd2Edit;
    private CheckBox mCheckBox;
    private TextView mPaper;
    private TextView mRegBtn;
    private List<String> mNameList;
    private List<String> mPathList;
    private SpotsDialog mDialog;
    private BeanFirmInfo mFirmInfo;
    private String email;
    private String picID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firm_regedit);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        initView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }

    private void initView() {
        mFirmInfo = (BeanFirmInfo) getIntent().getSerializableExtra("info");
        mDialog = new SpotsDialog(this, R.style.DialogCustomtwo);
        mBack = (ImageView) findViewById(R.id.back);
        nameEdit = (EditText) findViewById(R.id.activity_reg_finish_name);
        emailEdit = (EditText) findViewById(R.id.activity_reg_finish_email);
        pwdEdit = (EditText) findViewById(R.id.activity_reg_finish_pwd);
        pwd2Edit = (EditText) findViewById(R.id.activity_reg_finish_pwd2);
        mRegBtn = (TextView) findViewById(R.id.activity_reg_finish_btn);
        mPaper = (TextView) findViewById(R.id.activity_reg_finish_paper);
        mCheckBox = (CheckBox) findViewById(R.id.activity_reg_finish_check);
        mCheckBox.setOnCheckedChangeListener(this);
        mRegBtn.setOnClickListener(this);
        mPaper.setOnClickListener(this);
        mBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mBack) {
            onBackPressed();
        } else if (v == mPaper) {
            //TODO:注册协议;
        } else if (v == mRegBtn) {
            if (valid(nameEdit, "昵称") && valid(pwdEdit, "登录密码")) {
                if (pwdEdit.getText().toString().equals(pwd2Edit.getText().toString())) {
                    if (emailEdit.getText() != null && !"".equals(emailEdit.getText().toString())) {
                        email = emailEdit.getText().toString();
                    } else {
                        email = "";
                    }
                    mPathList = new ArrayList<>();
                    mPathList.add(mFirmInfo.getLicenseImg());
                    mDialog.show();
                    upLoadPic(mPathList);
                } else {
                    Toast.makeText(this, "两次输入密码不一致！", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            setBtnAble();
        } else {
            setBtnEnable();
        }
    }
//
//    public void upLoadImg(List<String> pathList, List<String> nameList) {
//        final String uri = "http://121.42.156.151:93/FileStorage.aspx";
//        UpLoadImgThread.getInstance(this).setCallBackListener(new UpLoadImgThread.CallBackListener() {
//
//            @Override
//            public void onFinish(String repose) {
//                register();
//            }
//
//            @Override
//            public void onError(String e) {
//                mDialog.dismiss();
//                Toast.makeText(FirmRegeditActivity.this, e, Toast.LENGTH_SHORT).show();
//            }
//        }).startUpLoadLogo(pathList, uri, nameList, "0", "1");
//    }

    public void upLoadPic(List<String> list) {
        UpLoadImgThread.getInstance(this).upLoad(list, "http://121.42.156.151:93/FileStorage.aspx",
                "0", "1", new UpLoadImgThread.CallBackListener() {
                    @Override
                    public void onFinish(List<String> values) {
                        register(values.get(0));
                    }

                    @Override
                    public void onError(String e) {
                        mDialog.dismiss();
                        Toast.makeText(FirmRegeditActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    /**
     * 注册
     */
    private void register(String picID) {
        mDialog.show();
        NetWorkUtil.setCallback("Corp/RegisterCorp",
                new BeanRegFirm(mFirmInfo.getOrgcode(), mFirmInfo.getCorpName(), mFirmInfo.getCorpAlias(), picID, "",
                        mFirmInfo.getManagerName(), mFirmInfo.getUserTel(), mFirmInfo.getAreaID(), mFirmInfo.getAddress(), mFirmInfo.getAuthCodeID(),
                        mFirmInfo.getUserName(), MD5Util.md5(pwdEdit.getText().toString()), nameEdit.getText().toString(), email, mFirmInfo.getPhone()
                        , "", "", ""),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值", respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                Toast.makeText(FirmRegeditActivity.this, "注册成功", Toast.LENGTH_LONG).show();
                                EventBus.getDefault().post(new EventBusBean(Constants.CORP_REGISTER));
                                finish();
                            } else {
                                Toast.makeText(FirmRegeditActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错", e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Toast.makeText(FirmRegeditActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private boolean valid(EditText editText, String name) {
        if (editText.getText() != null && !"".equals(editText.getText().toString())) {
            return true;
        } else {
            Toast.makeText(this, name + "不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    //设置按键不可用
    public void setBtnEnable() {
        mRegBtn.setClickable(false);
        mRegBtn.setBackgroundResource(R.drawable.bg_btn_gray_solid);
        mRegBtn.setEnabled(false);
    }

    //恢复按键可用
    public void setBtnAble() {
        mRegBtn.setClickable(true);
        mRegBtn.setBackgroundResource(R.drawable.select_btn_main);
        mRegBtn.setEnabled(true);
    }
}
