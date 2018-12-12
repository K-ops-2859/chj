package com.dikai.chenghunjiclient.activity.invitation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.SimplePicAdapter;
import com.dikai.chenghunjiclient.bean.BeanPublishInvitation;
import com.dikai.chenghunjiclient.citypicker.SelectCityActivity;
import com.dikai.chenghunjiclient.entity.NewUserInfo;
import com.dikai.chenghunjiclient.entity.ResultGetInvitationProfit;
import com.dikai.chenghunjiclient.entity.ResultPublishInvitation;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.DensityUtil;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.wechat.friends.Wechat;
import dmax.dialog.SpotsDialog;

public class VipInviteMainActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, OnDateSetListener {

    private static final int READ_CONTACTS_CODE = 111;
    private EditText etName;
    private EditText etPhone;
    private TextView date;
    private TextView area;
    private TimePickerDialog mPickerDialog;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private String areaID;
    private SpotsDialog mDialog;
    
    private LinearLayout weddingLayout;
    private LinearLayout hunYanayout;
    private LinearLayout otherLayout;
    private EditText budget;
    private EditText table;
    private EditText tableMoney;
    private EditText project;
    private EditText projectBudget;
    private int budgetType = 1;
    private ResultGetInvitationProfit profit;
    private NestedScrollView mScrollView;
    private boolean hasScroll;
    private LinearLayout dateLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_invite_main);
        EventBus.getDefault().register(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .fullScreen(true)
                .statusBarColorTransform(R.color.transparent)
                .navigationBarColorTransform(R.color.transparent)
                .addViewSupportTransformColor(toolbar)
                .init();
        init();
    }

    private void init() {
        mDialog = new SpotsDialog(this,R.style.DialogCustom);
        profit = (ResultGetInvitationProfit) getIntent().getSerializableExtra("profit");
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.phone_contacts).setOnClickListener(this);
        findViewById(R.id.record).setOnClickListener(this);
        findViewById(R.id.publish).setOnClickListener(this);
        findViewById(R.id.show).setOnClickListener(this);
        mScrollView = (NestedScrollView) findViewById(R.id.scrollview);
        dateLayout = (LinearLayout) findViewById(R.id.date_layout);
        weddingLayout = (LinearLayout) findViewById(R.id.wedding_layout);
        hunYanayout = (LinearLayout) findViewById(R.id.hunyan_layout);
        otherLayout = (LinearLayout) findViewById(R.id.other_layout);
        budget = (EditText) findViewById(R.id.budget);
        table = (EditText) findViewById(R.id.table);
        tableMoney = (EditText) findViewById(R.id.table_money);
        project = (EditText) findViewById(R.id.project);
        projectBudget = (EditText) findViewById(R.id.other_budget);

        area = (TextView) findViewById(R.id.area);
        date = (TextView) findViewById(R.id.date);
        etName = (EditText) findViewById(R.id.name);
        etPhone = (EditText) findViewById(R.id.phone);
        area.setOnClickListener(this);
        date.setOnClickListener(this);
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
                .setThemeColor(ContextCompat.getColor(this, R.color.main))
                .setType(Type.YEAR_MONTH_DAY)
                .setWheelItemTextNormalColor(ContextCompat.getColor(this, R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(ContextCompat.getColor(this, R.color.main))
                .build();
        
        TextView mText = (TextView) findViewById(R.id.money);
        LinearGradient mLinearGradient = new LinearGradient(0, 0, 0,DensityUtil.dip2px(this,34),
                new int[] {0xffFFA359, 0xffFF5D76},
                null, Shader.TileMode.REPEAT);
        mText.getPaint().setShader(mLinearGradient);

        int iconW = DensityUtil.dip2px(this, 20);
        RadioGroup mGroup = (RadioGroup) findViewById(R.id.select_group);
        Drawable drawable1 = ContextCompat.getDrawable(this,R.drawable.select_vip_btn);
        drawable1.setBounds(0,0,iconW,iconW);
        Drawable drawable2 = ContextCompat.getDrawable(this,R.drawable.select_vip_btn);
        drawable2.setBounds(0,0,iconW,iconW);
        Drawable drawable3 = ContextCompat.getDrawable(this,R.drawable.select_vip_btn);
        drawable3.setBounds(0,0,iconW,iconW);
        List<Drawable> draw_list = new ArrayList<>();
        Collections.addAll(draw_list, drawable1, drawable2, drawable3);
        for (int i = 0; i < 3; i++) {
            RadioButton button = (RadioButton) mGroup.getChildAt(i);
            button.setCompoundDrawables(draw_list.get(i),null, null, null);
            button.setCompoundDrawablePadding(DensityUtil.dip2px(this, 8));
        }
        mGroup.setOnCheckedChangeListener(this);
        ImageView topImg = (ImageView) findViewById(R.id.top_img);
        RecyclerView endpics = (RecyclerView) findViewById(R.id.end_img_recycler);
        SimplePicAdapter picAdapter = new SimplePicAdapter(this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        endpics.setLayoutManager(mLayoutManager);
        endpics.setAdapter(picAdapter);
        endpics.setNestedScrollingEnabled(false);
        picAdapter.refresh(Arrays.asList(profit.getEndBanner().split(",")));

        String loca = UserManager.getInstance(this).getLocation();
        if(loca != null && !"".equals(loca)){
            String[] info = loca.split(",");
            areaID = info[0];
            area.setText(info[1]);
            Log.e("数据：=========== ",loca);
        }
        setScroll();
        mText.setText(profit.getMoney());
        Glide.with(this).load(profit.getTopBanner()).into(topImg);
    }

    private void setScroll(){
        View.OnFocusChangeListener listener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus && !hasScroll){
                    Log.e("mScrollView",dateLayout.getTop()+"");
                    hasScroll = true;
                    mScrollView.fling(0);
                    mScrollView.smoothScrollTo(0, dateLayout.getTop());
                }
            }
        };
        budget.setOnFocusChangeListener(listener);
        table.setOnFocusChangeListener(listener);
        tableMoney.setOnFocusChangeListener(listener);
        project.setOnFocusChangeListener(listener);
        projectBudget.setOnFocusChangeListener(listener);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.select_1:
                budgetType = 0;//预算
                hunYanayout.setVisibility(View.GONE);
                otherLayout.setVisibility(View.GONE);
                weddingLayout.setVisibility(View.VISIBLE);
                budget.requestFocus();
                break;
            case R.id.select_2:
                budgetType = 1;//桌数 餐标
                hunYanayout.setVisibility(View.VISIBLE);
                otherLayout.setVisibility(View.GONE);
                weddingLayout.setVisibility(View.GONE);
                table.requestFocus();
                break;
            case R.id.select_3:
                budgetType = 2;//婚礼项目 预算
                otherLayout.setVisibility(View.VISIBLE);
                hunYanayout.setVisibility(View.GONE);
                weddingLayout.setVisibility(View.GONE);
                project.requestFocus();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.phone_contacts:
                request();
                break;
            case R.id.record:
                startActivity(new Intent(this,VipInviteRecordActivity.class));
                break;
            case R.id.show:
                break;
            case R.id.area:
                startActivity(new Intent(this, SelectCityActivity.class));
                break;
            case R.id.date:
                mPickerDialog.show(getSupportFragmentManager(), "year_month_day");
                break;
            case R.id.publish:
                if(!checkInput(etName)){
                    Toast.makeText(this, "请输入新人姓名！", Toast.LENGTH_SHORT).show();
                }else if(!checkInput(etPhone)){
                    Toast.makeText(this, "请输入新人手机！", Toast.LENGTH_SHORT).show();
                }else if(!isMobile(etPhone.getText().toString())){
                    Toast.makeText(this, "请输入不包含空格、\"-\"、\"+86\"等字符的11位手机号", Toast.LENGTH_LONG).show();
                }else if(areaID == null){
                    Toast.makeText(this, "请选择所在区域！", Toast.LENGTH_SHORT).show();
                }else if(!checkInput(date)){
                    Toast.makeText(this, "请选择婚期！", Toast.LENGTH_SHORT).show();
                }else {
                    if(budgetType == 0){
                        if(!checkInput(budget)){
                            Toast.makeText(this, "请输入婚礼预算！", Toast.LENGTH_SHORT).show();
                        }else {
                            publish(etName.getText().toString(), etPhone.getText().toString(),date.getText().toString(),0,"","",budget.getText().toString(),"");
                        }
                    }else if(budgetType == 1){
                        String canbiao = checkInput(tableMoney)?tableMoney.getText().toString():"";
                        if(!checkInput(table)){
                            Toast.makeText(this, "请输入餐桌数！", Toast.LENGTH_SHORT).show();
                        }else {
                            publish(etName.getText().toString(), etPhone.getText().toString(),date.getText().toString(),1,canbiao,table.getText().toString(),"","");
                        }
                    }else {
                        if(!checkInput(project)){
                            Toast.makeText(this, "请输入婚礼项目！", Toast.LENGTH_SHORT).show();
                        }else if(!checkInput(projectBudget)){
                            Toast.makeText(this, "请输入项目预算！", Toast.LENGTH_SHORT).show();
                        }else {
                            publish(etName.getText().toString(), etPhone.getText().toString(),date.getText().toString(),1,"","",projectBudget.getText().toString(),project.getText().toString());
                        }
                    }
                }
                break;
        }
    }


    private void setNull(){
        areaID = null;
        area.setText("");
        etName.setText("");
        etPhone.setText("");
        date.setText("");
        budget.setText("");
        table.setText("");
        tableMoney.setText("");
        project.setText("");
        projectBudget.setText("");
        budgetType = 1;//桌数 餐标
        hunYanayout.setVisibility(View.VISIBLE);
        otherLayout.setVisibility(View.GONE);
        weddingLayout.setVisibility(View.GONE);
    }

    /**
     * 获取记录
     */
    private void publish(String name, String phone, String date, int type, String mealmark, String tables, String budget, String project){
        mDialog.show();
        NewUserInfo userInfo = UserManager.getInstance(this).getNewUserInfo();
        NetWorkUtil.setCallback("HQOAApi/CeaterInvitationRecord",
                new BeanPublishInvitation(name,phone,date,areaID,type,1,userInfo.getUserId(),mealmark,tables,budget,project),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        hasScroll = false;
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultPublishInvitation result = new Gson().fromJson(respose, ResultPublishInvitation.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                startActivity(new Intent(VipInviteMainActivity.this,VipInviteSuccessActivity.class));
                                setNull();
                            } else {
                                Toast.makeText(VipInviteMainActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(ADListActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        hasScroll = false;
                        mDialog.dismiss();
//                        Toast.makeText(ADListActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //炫耀一下
    private void show() {
        String url = "http://www.chenghunji.com/Capital/WeddInvitation?UserId=" +
                UserManager.getInstance(this).getNewUserInfo().getUserId();
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("赚钱也太快了，我已经赚了"+profit.getMoney()+"元！你也来试试?");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText("邀新人用成婚纪，赚80-10000元现金，可提现!");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("");//确保SDcard下面存在此张图片
        oks.setImageUrl("http://www.chenghunji.com/Download/User/wechat_red_packet.png");
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        // oks.setComment("成婚纪——婚礼原来如此简单");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("成婚纪");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://www.chenghunji.com/");
        oks.setPlatform(Wechat.NAME);//微信好友
        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Toast.makeText(VipInviteMainActivity.this, "分享成功！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Toast.makeText(VipInviteMainActivity.this, "分享失败！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(Platform platform, int i) {
                Toast.makeText(VipInviteMainActivity.this, "取消分享！", Toast.LENGTH_SHORT).show();
            }
        });
        // 启动分享GUI
        oks.show(VipInviteMainActivity.this);
//        shareRedPacket(isToFriends);
    }

    private boolean checkInput(TextView textView){
        if(textView.getText() == null|| "".equals(textView.getText().toString().trim())){
            return false;
        }else {
            return true;
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
