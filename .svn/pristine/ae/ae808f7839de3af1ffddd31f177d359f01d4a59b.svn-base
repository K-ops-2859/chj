package com.dikai.chenghunjiclient.activity.wedding;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.bean.BeanApplyWed;
import com.dikai.chenghunjiclient.citypicker.SelectCityActivity;
import com.dikai.chenghunjiclient.entity.NewUserInfo;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.entity.UserInfo;
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

import static com.yanzhenjie.nohttp.NoHttp.getContext;

public class InviteApplyActivity extends AppCompatActivity implements View.OnClickListener, OnDateSetListener {

    private EditText name;
    private EditText phone;
    private EditText money;
    private TextView type;
    private TextView date;
    private TextView area;
    private TextView remark;

    private TextView hotelBook;
    private LinearLayout hasBook;
    private LinearLayout notBook;
    private EditText hotelName;
    private EditText hotelAddress;
    private EditText hotelTable;
    private EditText hotelNorm;
    private int bookType = 0;//0：未选择 1：未预定 2：已预订

    private String areaID;
    private boolean isGroom;
    private ActionSheetDialog bookDialog;
    private ActionSheetDialog typeDialog;
    private TimePickerDialog mPickerDialog;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    private AppCompatCheckBox mCheckBox;
    private TextView mCheckBoxText;
    private boolean agreeDeal = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_apply);
        EventBus.getDefault().register(this);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);
        money = (EditText) findViewById(R.id.money);

        hotelBook = (TextView) findViewById(R.id.hotel_book);
        hotelName = (EditText) findViewById(R.id.hotel_name);
        hotelAddress = (EditText) findViewById(R.id.hotel_address);
        hotelTable = (EditText) findViewById(R.id.hotel_table);
        hotelNorm = (EditText) findViewById(R.id.hotel_norm);
        hasBook = (LinearLayout) findViewById(R.id.has_book);
        notBook = (LinearLayout) findViewById(R.id.have_not_book);

        type = (TextView) findViewById(R.id.type);
        date = (TextView) findViewById(R.id.date);
        area = (TextView) findViewById(R.id.area);
        remark = (TextView) findViewById(R.id.remark);
        mCheckBox = (AppCompatCheckBox) findViewById(R.id.apply_checkbox);
        mCheckBoxText = (TextView) findViewById(R.id.apply_checkbox_text);
        mCheckBoxText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InviteApplyActivity.this, WedRuleActivity.class)
                        .putExtra("url","http://www.chenghunji.com/Redbag/xieyi"));
            }
        });

        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                agreeDeal = isChecked;
            }
        });

        findViewById(R.id.hotel_book).setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.apply).setOnClickListener(this);
        findViewById(R.id.edit).setOnClickListener(this);
        findViewById(R.id.type_layout).setOnClickListener(this);
        findViewById(R.id.date_layout).setOnClickListener(this);
        findViewById(R.id.area_layout).setOnClickListener(this);
        initDialog();
    }

    private void initDialog() {
        final String[] stringItems = {"新郎", "新娘"};
        typeDialog = new ActionSheetDialog(this, stringItems,null);
        typeDialog.isTitleShow(true)
                .titleTextColor(ContextCompat.getColor(this,R.color.black_deep))
                .itemTextColor(ContextCompat.getColor(this,R.color.pink))
                .titleBgColor(ContextCompat.getColor(this,R.color.white))
                .lvBgColor(ContextCompat.getColor(this,R.color.white))
                .setOnOperItemClickL(new OnOperItemClickL() {
                    @Override
                    public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                        typeDialog.dismiss();
                        if(position == 0){
                            isGroom = true;
                            type.setText("新郎");
                        }else {
                            isGroom = false;
                            type.setText("新娘");
                        }
                    }
                });

        final String[] items = {"未预定", "已预订"};
        bookDialog = new ActionSheetDialog(this, items,null);
        bookDialog.isTitleShow(true)
                .titleTextColor(ContextCompat.getColor(this,R.color.black_deep))
                .itemTextColor(ContextCompat.getColor(this,R.color.pink))
                .titleBgColor(ContextCompat.getColor(this,R.color.white))
                .lvBgColor(ContextCompat.getColor(this,R.color.white))
                .setOnOperItemClickL(new OnOperItemClickL() {
                    @Override
                    public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                        bookDialog.dismiss();
                        if(position == 0){
                            bookType = 1;
                            hotelBook.setText("未预定");
                            hasBook.setVisibility(View.GONE);
                            notBook.setVisibility(View.VISIBLE);
                        }else {
                            bookType = 2;
                            hotelBook.setText("已预订");
                            hasBook.setVisibility(View.VISIBLE);
                            notBook.setVisibility(View.GONE);
                        }
                    }
                });

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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.hotel_book:
                bookDialog.show();
                break;
            case R.id.apply:
                if(!agreeDeal){
                    Toast.makeText(this, "同意备婚协议后才可申请哦", Toast.LENGTH_SHORT).show();
                }else if(!checkInput(type)){
                    Toast.makeText(this, "请选择身份！", Toast.LENGTH_SHORT).show();
                }else if(!checkInput(name)){
                    Toast.makeText(this, "请输入姓名！", Toast.LENGTH_SHORT).show();
                }else if(!checkInput(phone)){
                    Toast.makeText(this, "请输入手机号！", Toast.LENGTH_SHORT).show();
//                }else if(!checkInput(date)){
//                    Toast.makeText(this, "请选择婚期！", Toast.LENGTH_SHORT).show();
//                }else if(!checkInput(money)){
//                    Toast.makeText(this, "请输入预算！", Toast.LENGTH_SHORT).show();
                }else if(!checkInput(area)){
                    Toast.makeText(this, "请选择地区！", Toast.LENGTH_SHORT).show();
//                }else if(!checkInput(hotel)){
//                    Toast.makeText(this, "请输入酒店！", Toast.LENGTH_SHORT).show();
//                }else if(!checkInput(address)){
//                    Toast.makeText(this, "请输入酒店地点！", Toast.LENGTH_SHORT).show();
                }else {
                    String wedHotel = "";
                    String wedAddress = "";
                    String wedtable = "0";
                    String wednorm = "0";
                    int hoteltype = 0;
                    if(bookType != 0){
                        hoteltype = bookType-1;
                        wedHotel = bookType == 1? "" : (checkInput(hotelName)? hotelName.getText().toString():"");
                        wedAddress = bookType == 1? "" : (checkInput(hotelAddress)? hotelAddress.getText().toString():"");
                        wedtable = bookType == 2? "0" : (checkInput(hotelTable)? hotelTable.getText().toString():"0");
                        wednorm = bookType == 2? "0" : (checkInput(hotelNorm)? hotelNorm.getText().toString():"0");
                    }
                    String wedDate = checkInput(date)? date.getText().toString():"";
                    String wedMoney = checkInput(money)? money.getText().toString():"0";
                    String mark = checkInput(remark)? remark.getText().toString():"";
                    if(isGroom){
                        apply("",name.getText().toString(),phone.getText().toString(),"","",
                                wedDate,wedMoney,wedHotel,wedAddress,wedtable,wednorm,hoteltype,mark);
                    }else {
                        apply("","","",name.getText().toString(),phone.getText().toString(),
                                wedDate,wedMoney, wedHotel,wedAddress,wedtable,wednorm,hoteltype,mark);
                    }
                }
                break;
            case R.id.edit:
                startActivity(new Intent(this,WedMarkActivity.class)
                        .putExtra("info",checkInput(remark)? remark.getText().toString():""));
                break;
            case R.id.type_layout:
                typeDialog.show();
                break;
            case R.id.date_layout:
                mPickerDialog.show(getSupportFragmentManager(), "year_month_day");
                break;
            case R.id.area_layout:
                startActivity(new Intent(this, SelectCityActivity.class));
                break;
        }
    }

    /**
     * 申请
     */
    private void apply(String inviteMan, String groomname,String groomphone,String bridename,String bridephone,
                       String weddingDate,String money,String hotel,String address,String table,String norm,int isbook, String remark){
        NewUserInfo info = UserManager.getInstance(this).getNewUserInfo();
        NetWorkUtil.setCallback("HQOAApi/AddNewInformation",
                new BeanApplyWed(info.getUserId(),"1","2",inviteMan,groomname,groomphone,bridename,bridephone,
                        areaID,"",weddingDate,money,hotel,address,table,norm,isbook,remark),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                Toast.makeText(InviteApplyActivity.this, "邀请成功！", Toast.LENGTH_LONG).show();
                                finish();
                            } else {
                                Toast.makeText(getContext(), result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private boolean checkInput(TextView textView){
        if(textView.getText() == null|| "".equals(textView.getText().toString().trim())){
            return false;
        }else {
            return true;
        }
    }

    /**
     * 事件总线刷新
     * @param bean
     */
    @Subscribe
    public void onEventMainThread(final EventBusBean bean) {
        runOnUiThread(new Runnable() {
            public void run() {
                if(bean.getType() == Constants.SELECT_COUNTRY){
                    areaID = bean.getCountry().getRegionId();
                    area.setText(bean.getCountry().getRegionName());
                }else if(bean.getType() == Constants.WEDDING_REMARK){
                    remark.setText(bean.getData());
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        date.setText(format.format(new Date(millseconds)));
    }
}
