package com.dikai.chenghunjiclient.activity.store;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.bean.BeanAddFanhuan;
import com.dikai.chenghunjiclient.bean.BeanOnlyFacilitatorId;
import com.dikai.chenghunjiclient.entity.ResultGetRatio;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dmax.dialog.SpotsDialog;

public class AddFanhuanActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int READ_CONTACTS_CODE = 111;
    private EditText etName;
    private EditText etPhone;
    private EditText etMoney;
    private TextView kouchu;
    private TextView fanhuan;
    private SpotsDialog mDialog;
    private MaterialDialog ratioDialog;
    private String mRatio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fanhuan);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        mDialog = new SpotsDialog(this,R.style.DialogCustom);
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.publish).setOnClickListener(this);
        findViewById(R.id.iv_question).setOnClickListener(this);
        findViewById(R.id.phone_contacts).setOnClickListener(this);
        etName = (EditText) findViewById(R.id.et_name);
        etPhone = (EditText) findViewById(R.id.et_phone);
        etMoney = (EditText) findViewById(R.id.et_money);
        kouchu = (TextView) findViewById(R.id.tv_back);
        fanhuan = (TextView) findViewById(R.id.tv_back2);
        etMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.e("etMoney",charSequence.toString());
                if(!"".equals(charSequence.toString())){
                    setBackMoney(charSequence.toString());
                    fanhuan.setText(charSequence.toString()+"￥");
                }else {
                    kouchu.setText("0.0￥");
                    fanhuan.setText("0.0￥");
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        getRatio();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.phone_contacts:
                request();
                break;
            case R.id.iv_question:
                if(ratioDialog == null){
                    getRatio();
                }else {
                    ratioDialog.show();
                }
                break;
            case R.id.publish:
                String money = etMoney.getText().toString();
                String name = etName.getText().toString();
                String phone = etPhone.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(this, "请输入客户姓名", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!isMobile(phone)){
                    Toast.makeText(this, "请输入不包含空格、\"-\"、\"+86\"等字符的11位手机号", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(money)) {
                    Toast.makeText(this, "请输入金额", Toast.LENGTH_SHORT).show();
                    return;
                }
                add(name,phone,money);
                break;
        }
    }

    private void setBackMoney(String money){
        if(ratioDialog == null){
            getRatio();
        } else {
            try {
                BigDecimal nowMoney = new BigDecimal(money);
                nowMoney = nowMoney.multiply(new BigDecimal(1).subtract(new BigDecimal(mRatio)));
                kouchu.setText(nowMoney.setScale(1,BigDecimal.ROUND_HALF_UP).toString()+"￥");
            }catch (Exception e){
                Log.e("",e.toString());
            }
        }
    }

    /**
     * 初始化Dialog
     */
    private void initDialog(ResultGetRatio ratio){
        if(ratioDialog == null){
            mRatio = ratio.getProportion();
            ratioDialog = new MaterialDialog(this);
            ratioDialog.isTitleShow(false)//
                    .btnNum(2)
                    .content(ratio.getDescribe())//
                    .btnText("取消", "确定")
                    .setOnBtnClickL(new OnBtnClickL() {
                        @Override
                        public void onBtnClick() {
                            ratioDialog.dismiss();
                        }
                    }, new OnBtnClickL() {
                        @Override
                        public void onBtnClick() {
                            ratioDialog.dismiss();
                        }
                    });
        }
    }

    /**
     * 提交
     */
    private void add(String name, String phone, String money){
        mDialog.show();
        NetWorkUtil.setCallback("HQOAApi/CreateFacilitatorWeddingReturn",
                new BeanAddFanhuan(name,phone, UserManager.getInstance(this).getNewUserInfo().getFacilitatorId(),money),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                EventBus.getDefault().post(new EventBusBean(Constants.SUPPIPELINE_ADD));
                                Toast.makeText(AddFanhuanActivity.this, "提交成功！", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(AddFanhuanActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Toast.makeText(AddFanhuanActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 获取比例
     */
    private void getRatio(){
        NetWorkUtil.setCallback("HQOAApi/GetFacilitatorIdWeddingReturnProportion",
                new BeanOnlyFacilitatorId(UserManager.getInstance(this).getNewUserInfo().getFacilitatorId()),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultGetRatio result = new Gson().fromJson(respose, ResultGetRatio.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                initDialog(result);
                            } else {
                                Toast.makeText(AddFanhuanActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        Toast.makeText(AddFanhuanActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void request() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            //申请READ_CONTACTS权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, READ_CONTACTS_CODE);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent, 123);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode, grantResults);
    }

    private void doNext(int requestCode, int[] grantResults) {
        if (requestCode == READ_CONTACTS_CODE) {
            if (grantResults != null && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, 123);
            } else {
                Toast.makeText(this, "已禁止", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 123){
            getContacts(data);
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void getContacts(Intent data) {
        if (data == null) {
            return;
        }

        Uri contactData = data.getData();
        if (contactData == null) {
            return;
        }
        String name = "";
        String phoneNumber = "";

        Uri contactUri = data.getData();
        Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
        if (cursor.moveToFirst()) {
            name = cursor
                    .getString(cursor
                            .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String hasPhone = cursor
                    .getString(cursor
                            .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
            String id = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.Contacts._ID));
            if (hasPhone.equalsIgnoreCase("1")) {
                hasPhone = "true";
            } else {
                hasPhone = "false";
            }
            if (Boolean.parseBoolean(hasPhone)) {
                Cursor phones = getContentResolver()
                        .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                        + " = " + id, null, null);
                while (phones.moveToNext()) {
                    phoneNumber = phones
                            .getString(phones
                                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                }
                phones.close();
            }
            cursor.close();
            etName.setText(name);
            etPhone.setText(phoneNumber.replaceAll(" ","").replaceAll("-","").replaceAll("\\+86",""));
        }
    }

    //校验手机号
    public boolean isMobile(String mobile) {
        String regex = "^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8}$";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(mobile);
        return m.matches();
    }


    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}
