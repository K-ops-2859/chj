package com.dikai.chenghunjiclient.activity.store;

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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.bean.BeanFacilitatorReceivables;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.yanzhenjie.nohttp.NoHttp.getContext;

/**
 * Created by cmk03 on 2018/10/8.
 */

public class AddCollectionActivity extends AppCompatActivity implements View.OnClickListener, OnDateSetListener {

    private static final int READ_CONTACTS_CODE = 111;
    private TextView tvCHJ;
    private TextView tvStore;
    private LinearLayout llStore;
    private TimePickerDialog mPickerDialog;
    private ActionSheetDialog typeDialog;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private TextView tvDate;
    private TextView tvType;
    private TextView etOrderPhone;
    private String facilitatorId;
    private EditText etName;
    private EditText etPhone;
    private EditText etMoney;
    private String date;
    private String moneType;
    private TextView tvSelectStore;
    private String storeId = "00000000-0000-0000-0000-000000000000";
    private String jiedanrenID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_collection);
        EventBus.getDefault().register(this);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
        initDialog();
    }

    private void init() {
        findViewById(R.id.phone_contacts).setOnClickListener(this);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        etOrderPhone = (TextView) findViewById(R.id.et_order_phone);
        etName = (EditText) findViewById(R.id.et_name);
        etPhone = (EditText) findViewById(R.id.et_phone);
        etMoney = (EditText) findViewById(R.id.et_money);
        LinearLayout llSelectDate = (LinearLayout) findViewById(R.id.ll_select_date);
        tvDate = (TextView) findViewById(R.id.tv_date);
        tvType = (TextView) findViewById(R.id.tv_type);
        LinearLayout llMoneyType = (LinearLayout) findViewById(R.id.ll_select_money_type);
        tvCHJ = (TextView) findViewById(R.id.tv_chj);
        tvStore = (TextView) findViewById(R.id.tv_store);
        llStore = (LinearLayout) findViewById(R.id.ll_store);
        tvSelectStore = (TextView) findViewById(R.id.tv_select_store);
        TextView tvSub = (TextView) findViewById(R.id.tv_submit);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        llSelectDate.setOnClickListener(this);
        llMoneyType.setOnClickListener(this);
        tvCHJ.setOnClickListener(this);
        tvStore.setOnClickListener(this);
        llStore.setOnClickListener(this);
        etOrderPhone.setOnClickListener(this);
        tvSub.setOnClickListener(this);
    }

    private void subData(String facilitatorId, String storeId, int money, String phone, String userName, String date, String type, String orderPhone) {
        if (facilitatorId == null) {
            facilitatorId = UserManager.getInstance(this).getNewUserInfo().getFacilitatorId();
        }
        NetWorkUtil.setCallback("HQOAApi/CreateFacilitatorReceivables",
                new BeanFacilitatorReceivables(facilitatorId, storeId, money, phone, userName, date, type, orderPhone), new NetWorkUtil.CallBackListener() {
            @Override
            public void onFinish(String respose) {
                try {
                    ResultMessage message = new Gson().fromJson(respose, ResultMessage.class);
                    if (message.getMessage().getCode().equals("200")) {
                        EventBus.getDefault().post(new EventBusBean(Constants.SUPPIPELINE_ADD));
                        Toast.makeText(AddCollectionActivity.this, "提交完成", Toast.LENGTH_SHORT).show();
                        AddCollectionActivity.this.finish();
                    }
                } catch (Exception e) {
                    Log.e("错误", e.toString());
                }
            }

            @Override
            public void onError(String e) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.phone_contacts:
                request();
                break;
            case R.id.ll_select_date:
                mPickerDialog.show(getSupportFragmentManager(), "year_month_day");
                break;
            case R.id.ll_select_money_type:
                typeDialog.show();
                break;
            case R.id.tv_submit:
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
                if (TextUtils.isEmpty(date)) {
                    Toast.makeText(this, "请选择日期", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(moneType)) {
                    Toast.makeText(this, "请选择消费类型", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (llStore.getVisibility() == View.VISIBLE && "00000000-0000-0000-0000-000000000000".equals(storeId)) {
                    Toast.makeText(this, "请选择关联商家", Toast.LENGTH_SHORT).show();
                    return;
                }

                subData(facilitatorId, storeId, Integer.parseInt(money), phone,
                        name, date, moneType, jiedanrenID);
                break;
            case R.id.ll_store:
                startActivity(new Intent(AddCollectionActivity.this, SearchSupActivity.class).putExtra("type",1));
                break;
            case R.id.et_order_phone:
                startActivity(new Intent(this,SearchJiedanrenActivity.class));
                break;
            case R.id.tv_chj:
                tvCHJ.setBackgroundResource(R.drawable.bg_btn_rednew);
//                tvCHJ.setTextColor(getResources().getColor(R.color.white));
                tvCHJ.setTextColor(ContextCompat.getColor(this,R.color.white));
                tvStore.setBackgroundResource(R.drawable.bg_btn_gray_light3);
//                tvStore.setTextColor(getResources().getColor(R.color.gray_text));
                tvStore.setTextColor(ContextCompat.getColor(this,R.color.gray_text));
                llStore.setVisibility(View.GONE);
                break;
            case R.id.tv_store:
                tvStore.setBackgroundResource(R.drawable.bg_btn_rednew);
                tvStore.setTextColor(ContextCompat.getColor(this,R.color.white));
//                tvStore.setTextColor(getResources().getColor(R.color.white));
                tvCHJ.setBackgroundResource(R.drawable.bg_btn_gray_light3);
//                tvCHJ.setTextColor(getResources().getColor(R.color.gray_text));
                tvCHJ.setTextColor(ContextCompat.getColor(this,R.color.gray_text));
                llStore.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    private void initDialog() {
        long Years = 10L * 365 * 1000 * 60 * 60 * 24L;
        mPickerDialog = new TimePickerDialog.Builder()
                .setCallBack(this)
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("选择婚期")
                .setYearText("年")
                .setMonthText("月")
                .setDayText("日")
                .setMinMillseconds(System.currentTimeMillis())
                .setMaxMillseconds(System.currentTimeMillis() + Years)
                .setThemeColor(ContextCompat.getColor(getContext(), R.color.main))
                .setType(Type.YEAR_MONTH_DAY)
                .setWheelItemTextNormalColor(ContextCompat.getColor(getContext(), R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(ContextCompat.getColor(getContext(), R.color.main))
                .build();

        final String[] stringItems = {"首款", "再款", "尾款", "全款"};
        typeDialog = new ActionSheetDialog(this, stringItems, null);
        typeDialog.isTitleShow(true)
                .titleTextColor(ContextCompat.getColor(this, R.color.black_deep))
                .itemTextColor(ContextCompat.getColor(this, R.color.gray_text))
                .titleBgColor(ContextCompat.getColor(this, R.color.white))
                .lvBgColor(ContextCompat.getColor(this, R.color.white))
                .setOnOperItemClickL(new OnOperItemClickL() {
                    @Override
                    public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                        moneType = stringItems[position];
                        tvType.setText(moneType);
                        tvType.setTextColor(ContextCompat.getColor(AddCollectionActivity.this,R.color.black));
                        typeDialog.dismiss();
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
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        date = this.format.format(new Date(millseconds));
        tvDate.setText(date);
        tvDate.setTextColor(ContextCompat.getColor(this,R.color.black));
    }

    /**
     * 事件总线刷新
     * @param bean
     */
    @Subscribe
    public void onEventMainThread(final EventBusBean bean) {
        runOnUiThread(new Runnable() {
            public void run() {
                if(bean.getType() == Constants.SUPPIPELINE_SELECT_SUP){
                    storeId = bean.getSupsBean().getId();
                    tvSelectStore.setText(bean.getSupsBean().getName());
                }else if(bean.getType() == Constants.ADD_JIEDANREN){
                    etOrderPhone.setText(bean.getJiedanRenBean().getName()+"\n"+bean.getJiedanRenBean().getPhone());
                    jiedanrenID = bean.getJiedanRenBean().getUserId();
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
