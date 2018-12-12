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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.bean.BeanFacilitatorFlowRecord;
import com.dikai.chenghunjiclient.bean.BeanIdentityId;
import com.dikai.chenghunjiclient.entity.IntervalAmountParamData;
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

public class AddGiftActivity extends AppCompatActivity implements View.OnClickListener, OnDateSetListener {

    private static final int READ_CONTACTS_CODE = 111;
    private TextView tvDate;
    private TimePickerDialog mPickerDialog;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private TextView etOrderPhone;
    private String moneType;
    private ActionSheetDialog typeDialog;
    private TextView tvType;
    private EditText etName;
    private EditText etPhone;
    private EditText etMoney;
    private String date;
    private int selectPosition = 0;
    private TextView tvBack;
    private IntervalAmountParamData range;
    private ImageView ivQuestion;
    private String jiedanrenID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addgift);
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
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.phone_contacts).setOnClickListener(this);
        etOrderPhone = (TextView) findViewById(R.id.et_order_phone);
        etName = (EditText) findViewById(R.id.et_name);
        etPhone = (EditText) findViewById(R.id.et_phone);
        etMoney = (EditText) findViewById(R.id.et_money);
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvType = (TextView) findViewById(R.id.tv_type);
        ivQuestion = (ImageView) findViewById(R.id.iv_question);
        LinearLayout llSelectDate = (LinearLayout) findViewById(R.id.ll_select_date);
        LinearLayout llSelectMoney = (LinearLayout) findViewById(R.id.ll_select_money_type);
        tvDate = (TextView) findViewById(R.id.tv_date);
        TextView tvXianxia = (TextView) findViewById(R.id.tv_xianxia);
        TextView tvXianshang = (TextView) findViewById(R.id.tv_xianshang);
        etMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.e("etMoney",charSequence.toString());
                if(!"".equals(charSequence.toString())){
                    setBackMoney(charSequence.toString());
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });

        llSelectDate.setOnClickListener(this);
        tvXianxia.setOnClickListener(this);
        llSelectMoney.setOnClickListener(this);
        tvXianshang.setOnClickListener(this);
        ivQuestion.setOnClickListener(this);
        etOrderPhone.setOnClickListener(this);
        getIntervalAmountParam();
    }

    private void setBackMoney(String money){
        if(range == null){
            getIntervalAmountParam();
        }else {
            try {
                int nowMoney = Integer.parseInt(money);
                Log.e("nowMoney", nowMoney + "");
                for (IntervalAmountParamData.DataList bean : range.getData()) {
                    if(nowMoney >= bean.getStartingPrice() && nowMoney <= bean.getTerminationPrice()){
                        tvBack.setText(bean.getReversion() + "");
                        return;
                    }
                }
            }catch (Exception e){
                Log.e("",e.toString());
            }
        }
    }

    private void subData( int paymentType, int money, String phone, String userName, String date, String type) {
        String facilitatorId = UserManager.getInstance(this).getNewUserInfo().getFacilitatorId();
        NetWorkUtil.setCallback("HQOAApi/CreateFacilitatorFlowRecord",
                new BeanFacilitatorFlowRecord(facilitatorId, paymentType, money, phone, userName, date, type, jiedanrenID),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(String respose) {
                        try {
                            Log.e("返回值",respose);
                            ResultMessage message = new Gson().fromJson(respose, ResultMessage.class);
                            if (message.getMessage().getCode().equals("200")) {
                                EventBus.getDefault().post(new EventBusBean(Constants.SUPPIPELINE_ADD));
                                Toast.makeText(AddGiftActivity.this, "提交完成", Toast.LENGTH_SHORT).show();
                                AddGiftActivity.this.finish();
                            } else {
                                Toast.makeText(AddGiftActivity.this, message.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("错误", e.toString());
                        }
                    }
                    @Override
                    public void onError(String e) {}
        });
    }

    private void getIntervalAmountParam() {
        String identity = UserManager.getInstance(this).getNewUserInfo().getIdentity();
        NetWorkUtil.setCallback("HQOAApi/GetIntervalAmountParamList",
                new BeanIdentityId(identity), new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(String respose) {
                        try {
                            Log.e("返回值",respose);
                            IntervalAmountParamData data = new Gson().fromJson(respose, IntervalAmountParamData.class);
                            if ("200".equals(data.getMessage().getCode())) {
                                range = data;
                            }else {
                                Log.e("====", data.getMessage().getInform());
                                Toast.makeText(AddGiftActivity.this, data.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("错误", e.toString());
                        }
                    }
                    @Override
                    public void onError(String e) {}
        });
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
                        tvType.setTextColor(ContextCompat.getColor(AddGiftActivity.this,R.color.black));
                        typeDialog.dismiss();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.phone_contacts:
                request();
                break;
            case R.id.ll_select_date:
                mPickerDialog.show(getSupportFragmentManager(), "year_month_day");
                break;
            case R.id.tv_xianxia:
                String orderPhone = etOrderPhone.getText().toString();
                String money = etMoney.getText().toString();
                String name = etName.getText().toString();
                String phone = etPhone.getText().toString();
                if (TextUtils.isEmpty(orderPhone)) {
                    Toast.makeText(this, "请输入接单人手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
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
                if (TextUtils.isEmpty(date)) {
                    Toast.makeText(this, "请选择日期", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(moneType)) {
                    Toast.makeText(this, "请选择消费类型", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(money)) {
                    Toast.makeText(this, "请输入金额", Toast.LENGTH_SHORT).show();
                    return;
                }
                subData(1, Integer.parseInt(money), phone, name, date, moneType);
                break;
            case R.id.tv_xianshang:
                String orderPhone1 = etOrderPhone.getText().toString();
                String money1 = etMoney.getText().toString();
                String name1 = etName.getText().toString();
                String phone1 = etPhone.getText().toString();
                if (TextUtils.isEmpty(orderPhone1)) {
                    Toast.makeText(this, "请输入接单人手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(name1)) {
                    Toast.makeText(this, "请输入客户姓名", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phone1)) {
                    Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!isMobile(phone1)){
                    Toast.makeText(this, "请输入不包含空格、\"-\"、\"+86\"等字符的11位手机号", Toast.LENGTH_LONG).show();
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
                if (TextUtils.isEmpty(money1)) {
                    Toast.makeText(this, "请输入金额", Toast.LENGTH_SHORT).show();
                    return;
                }
                subData(0, Integer.parseInt(money1), phone1, name1, date, moneType);
                break;
            case R.id.ll_select_money_type:
                typeDialog.show();
                break;
            case R.id.iv_question:
                startActivity(new Intent(this,ProfitAreaActivity.class));
                break;
            case R.id.et_order_phone:
                startActivity(new Intent(this,SearchJiedanrenActivity.class));
                break;
            default:
                break;
        }
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

    //===========================================================================================

    /**
     * 事件总线刷新
     * @param bean
     */
    @Subscribe
    public void onEventMainThread(final EventBusBean bean) {
        runOnUiThread(new Runnable() {
            public void run() {
                if(bean.getType() == Constants.ADD_JIEDANREN){
                    etOrderPhone.setText(bean.getJiedanRenBean().getName()+"\n"+bean.getJiedanRenBean().getPhone());
                    jiedanrenID = bean.getJiedanRenBean().getUserId();
                }
            }
        });
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        date = this.format.format(new Date(millseconds));
        tvDate.setText(date);
        tvDate.setTextColor(ContextCompat.getColor(this,R.color.black));
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}
