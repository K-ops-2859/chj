package com.dikai.chenghunjiclient.activity.wedding;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.bean.BeanAddAddress;
import com.dikai.chenghunjiclient.bean.UpdateConsigneeInfo;
import com.dikai.chenghunjiclient.citypicker.SelectCityActivity;
import com.dikai.chenghunjiclient.entity.PersonAddressData;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ch.ielse.view.SwitchView;

/**
 * Created by cmk03 on 2018/5/2.
 */

public class AddAddressActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int READ_CONTACTS_CODE = 111;
    private EditText etName;
    private EditText etPhone;
    private TextView etArea;
    private EditText etAddress;
    private String name;
    private String phone;
    private String area;
    private String address;
    private String areaId;
    private String sheng;
    private String shi;
    private String qu;
    private TextView tvRemove;
    private String id;
    private SwitchView switchView;
    private Toolbar mToolBar;
    private String isDefault = "0";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addaddress);
        EventBus.getDefault().register(this);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();

        findViewById(R.id.phone_contacts).setOnClickListener(this);
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        tvRemove = (TextView) findViewById(R.id.tv_remove);
        etName = (EditText) findViewById(R.id.et_name);
        etPhone = (EditText) findViewById(R.id.et_phone);
        etArea = (TextView) findViewById(R.id.et_area);
        etAddress = (EditText) findViewById(R.id.et_details_address);
        switchView = (SwitchView) findViewById(R.id.switch_view);
        TextView tvSave = (TextView) findViewById(R.id.tv_save);

        Intent intent = getIntent();
        int isJump = intent.getIntExtra("isJump", -1);
        if (isJump == 1) {
            tvRemove.setVisibility(View.VISIBLE);
            PersonAddressData.DataList data = (PersonAddressData.DataList) intent.getSerializableExtra("data");
            etName.setText(data.getConsignee());
            etArea.setText(data.getArea());
            etPhone.setText(data.getConsigneePhone());
            etAddress.setText(data.getDetailedAddress());
            id = data.getId();
        } else {
            tvRemove.setVisibility(View.GONE);
        }

        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        etArea.setOnClickListener(this);
        switchView.setOnClickListener(this);
        tvSave.setOnClickListener(this);
        tvRemove.setOnClickListener(this);
    }

    private void verify() {
        name = etName.getText().toString();
        phone = etPhone.getText().toString();
        address = etAddress.getText().toString();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "请输入收货人姓名", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "请输入收货人手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!isMobile(phone)){
            Toast.makeText(this, "请输入不包含空格、\"-\"、\"+86\"等字符的11位手机号", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(areaId)) {
            Toast.makeText(this, "请选择地区", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(address)) {
            Toast.makeText(this, "请输入收货人详细地址", Toast.LENGTH_SHORT).show();
            return;
        }

        initData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.phone_contacts:
                request();
                break;
            case R.id.et_area:
                Intent intent = new Intent(AddAddressActivity.this, SelectCityActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.tv_save:
                verify();
                break;
            case R.id.switch_view:
                boolean opened = switchView.isOpened();
                if (opened) {
                    isDefault = "1";
                } else {
                    isDefault = "0";
                }
                System.out.println("开关========" + opened);
                break;
            default:
                break;
        }
    }

    private void initData() {
        String userId = UserManager.getInstance(this).getNewUserInfo().getUserId();
        NetWorkUtil.setCallback("HQOAApi/AddConsigneeInfo",
                new BeanAddAddress(userId, areaId + "", address, name, phone, "", isDefault), new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(String respose) {
                        try {
                            Log.e("返回值", respose);
                            ResultMessage data = new Gson().fromJson(respose, ResultMessage.class);
                            if (data.getMessage().getCode().equals("200")) {
                                Toast.makeText(AddAddressActivity.this, "新建地址完成", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(AddAddressActivity.this, data.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            Log.e("json解析错误", e.toString());
                        }
                    }

                    @Override
                    public void onError(String e) {
                        Log.e("网络错误", e);
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

    /**
     * 事件总线刷新
     *
     * @param bean
     */
    @Subscribe
    public void onEventMainThread(final EventBusBean bean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (bean.getType() == Constants.SELECT_PROVINCE) {//省
                    sheng = bean.getProvince().getRegionName();
                } else if (bean.getType() == Constants.SELECT_CITY) {//市
                    shi = bean.getCity().getRegionName();
                } else if (bean.getType() == Constants.SELECT_COUNTRY) {//区
                    areaId = bean.getCountry().getRegionId();
                    qu = bean.getCountry().getRegionName();
                }
                etArea.setText(sheng + "-" + shi + "-" + qu);
            }
        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == 1 && resultCode == 100) {
//            int addressID = data.getExtras().getInt("addressID");
//            System.out.println("=======================返回的id:::" + addressID);
//            etArea.setText(addressID);
//            if (addressID != -1) {
//            }
//        }
//    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }

}
