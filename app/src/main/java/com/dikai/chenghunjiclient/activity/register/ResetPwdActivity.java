package com.dikai.chenghunjiclient.activity.register;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.me.EditRoomActivity;
import com.dikai.chenghunjiclient.bean.BeanRegister;
import com.dikai.chenghunjiclient.bean.BeanResetPwd;
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
import java.util.Collections;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class ResetPwdActivity extends AppCompatActivity implements View.OnClickListener {

    private String phone;
    private String codeID;

    //=======================
    private LinearLayout codeLayout;
    private EditText pwd1;
    private EditText pwd2;
    private EditText codeEdit;
    private int userType;
    private int picType;
    private BeanRegister mBeanRegister;
    private SpotsDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pwd);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        initView();
    }

    private void initView() {
        mDialog = new SpotsDialog(this, R.style.DialogCustom);
        picType = getIntent().getIntExtra("type",Constants.USER_TYPE_USUAL);
        userType = getIntent().getIntExtra("usertype",0);
        Log.e("picType:" + picType," === userType:" + userType);
        pwd1 = (EditText) findViewById(R.id.activity_reset_pwd_pwd1);
        pwd2 = (EditText) findViewById(R.id.activity_reset_pwd_pwd2);
        codeEdit = (EditText) findViewById(R.id.activity_reset_code);
        codeLayout = (LinearLayout) findViewById(R.id.activity_reset_code_layout);
        findViewById(R.id.activity_reset_pwd_back).setOnClickListener(this);
        findViewById(R.id.activity_reset_pwd_next).setOnClickListener(this);
        if(userType == 1){
            mBeanRegister = (BeanRegister) getIntent().getSerializableExtra("register");
            codeLayout.setVisibility(View.VISIBLE);
        }else if(userType == 0){
            codeLayout.setVisibility(View.GONE);
            phone = getIntent().getStringExtra("phone");
            codeID = getIntent().getStringExtra("code");
            mDialog = new SpotsDialog(this, R.style.DialogCustom);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_reset_pwd_back:
                onBackPressed();
                break;
            case R.id.activity_reset_pwd_next:
                next();
                break;
        }
    }

    private void next() {
        if(pwd1.getText() == null || "".equals(pwd1.getText().toString().trim())){
            Toast.makeText(this, "请输入密码！", Toast.LENGTH_SHORT).show();
        }else if(pwd2.getText() == null || "".equals(pwd2.getText().toString().trim())){
            Toast.makeText(this, "请确认密码！", Toast.LENGTH_SHORT).show();
        }else if(!pwd1.getText().toString().trim().equals(pwd2.getText().toString().trim())){
            Toast.makeText(this, "两次密码输入不一致！", Toast.LENGTH_SHORT).show();
        }else if(userType == 0){
            //重置密码
            reset();
        }else if(userType == 1){
            Log.e("picType:" + picType," === userType:执行至此" + userType);
            //注册
            mBeanRegister.setUserPwd(MD5Util.md5(pwd1.getText().toString().trim()));
            mBeanRegister.setYQCode((codeEdit.getText() == null || "".equals(codeEdit.getText().toString().trim()))?
                            "":codeEdit.getText().toString().trim());
            getPic();
        }
    }

    private void getPic() {
        mDialog.show();
        List<String> list = new ArrayList<>();
        if(picType == Constants.USER_TYPE_HOTEL){
            Collections.addAll(list, mBeanRegister.getLogo(), mBeanRegister.getFrontIDcard(), mBeanRegister.getNegativeIDcard(),
                    mBeanRegister.getHandheldIDcard(),mBeanRegister.getBusinesslicense());
        }else if(picType == Constants.USER_TYPE_DRIVER){
            Collections.addAll(list, mBeanRegister.getLogo(), mBeanRegister.getFrontIDcard(),mBeanRegister.getNegativeIDcard(),
                    mBeanRegister.getHandheldIDcard(),mBeanRegister.getDrivinglicense());
        }else {
            Collections.addAll(list, mBeanRegister.getLogo(), mBeanRegister.getFrontIDcard(),mBeanRegister.getNegativeIDcard(),
                    mBeanRegister.getHandheldIDcard());
        }
        upload(list);
    }

    private void upload(List<String> list){
        UpLoadImgThread.getInstance(this).upLoad(list, "http://121.42.156.151:93/FileStorage.aspx",
                "0", "1", new UpLoadImgThread.CallBackListener() {
                    @Override
                    public void onFinish(List<String> values) {
                        try {
                            Log.e("图片上传成功",""+ values.toString());
                            if(picType == Constants.USER_TYPE_HOTEL){
                                mBeanRegister.setLogo(values.get(0));
                                mBeanRegister.setFrontIDcard(values.get(1));
                                mBeanRegister.setNegativeIDcard(values.get(2));
                                mBeanRegister.setHandheldIDcard(values.get(3));
                                mBeanRegister.setBusinesslicense(values.get(4));
                            }else if(picType == Constants.USER_TYPE_DRIVER){
                                mBeanRegister.setLogo(values.get(0));
                                mBeanRegister.setFrontIDcard(values.get(1));
                                mBeanRegister.setNegativeIDcard(values.get(2));
                                mBeanRegister.setHandheldIDcard(values.get(3));
                                mBeanRegister.setDrivinglicense(values.get(4));
                            }else {
                                mBeanRegister.setLogo(values.get(1));
                                mBeanRegister.setFrontIDcard(values.get(2));
                                mBeanRegister.setNegativeIDcard(values.get(3));
                                mBeanRegister.setHandheldIDcard(values.get(4));
                            }
                        }catch (Exception e){
                            Toast.makeText(ResetPwdActivity.this, "图片返回错误", Toast.LENGTH_SHORT).show();
                        }
                        register();
                    }

                    @Override
                    public void onError(String e) {
                        mDialog.dismiss();
                        Toast.makeText(ResetPwdActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 注册
     */
    private void register(){
        NetWorkUtil.setCallback("User/RegisterSupplier", mBeanRegister,
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                EventBus.getDefault().post(new EventBusBean(Constants.USER_REGISTER_SUCCESS));
                                Toast.makeText(ResetPwdActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(ResetPwdActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Toast.makeText(ResetPwdActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 重置密码
     */
    private void reset(){
        mDialog.show();
        NetWorkUtil.setCallback("User/ResetPassword",
                new BeanResetPwd(phone,MD5Util.md5(pwd1.getText().toString().trim()),codeID,"1"),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                EventBus.getDefault().post(new EventBusBean(Constants.USER_REGISTER_SUCCESS));
                                Toast.makeText(ResetPwdActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(ResetPwdActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Toast.makeText(ResetPwdActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

//    /**
//     * 事件总线刷新
//     * @param bean
//     */
//    @Subscribe
//    public void onEventMainThread(final EventBusBean bean) {
//        runOnUiThread(new Runnable() {
//            public void run() {
//                if(bean.getType() == Constants.SELECT_CAR_BRAND){
//                    carBrand = bean.getCarBean();
//                    brandText.setText(carBrand.getName());
//                }else if(bean.getType() == Constants.SELECT_CAR_TYPE){
//                    carType = bean.getCarBean();
//                    typeText.setText(carType.getName());
//                }
//            }
//        });
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
//        EventBus.getDefault().unregister(this);
    }
}
