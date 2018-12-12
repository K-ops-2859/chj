package com.dikai.chenghunjiclient.activity.invitation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.wechat.friends.Wechat;
import dmax.dialog.SpotsDialog;

public class ComInviteMainActivity extends AppCompatActivity implements View.OnClickListener, OnDateSetListener {

    private static final int READ_CONTACTS_CODE = 111;
    private EditText etName;
    private EditText etPhone;
    private TextView date;
    private TextView area;
    private TimePickerDialog mPickerDialog;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private String areaID;
    private SpotsDialog mDialog;
    private ResultGetInvitationProfit profit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com_invite_main);
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
        findViewById(R.id.share_layout).setOnClickListener(this);
        findViewById(R.id.face_layout).setOnClickListener(this);
        findViewById(R.id.vip_layout).setOnClickListener(this);
        findViewById(R.id.show).setOnClickListener(this);

        TextView money = (TextView) findViewById(R.id.money);
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
        LinearGradient mLinearGradient = new LinearGradient(0, 0, 0, DensityUtil.dip2px(this,34),
                new int[] {0xffFFA359, 0xffFF5D76},
                null, Shader.TileMode.REPEAT);
        money.getPaint().setShader(mLinearGradient);
        money.setText(profit.getMoney());
        Glide.with(this).load(profit.getTopBanner()).into(topImg);
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
                startActivity(new Intent(this, ComInviteRecordActivity.class));
                break;
            case R.id.share_layout:
                showShare();
                break;
            case R.id.face_layout:
                String url = "http://www.chenghunji.com/Capital/BeiHun?UserId=" +
                        UserManager.getInstance(this).getNewUserInfo().getUserId();
                startActivity(new Intent(this,FaceInviteActivity.class).putExtra("url",url));
                break;
            case R.id.vip_layout:
                startActivity(new Intent(this, VipInviteBusinessActivity.class));
                break;
            case R.id.show:
                show();
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
                    publish(etName.getText().toString(), etPhone.getText().toString(),date.getText().toString(),0,"","","","");
                }
                break;
        }
    }

    /**
     * 获取记录
     */
    private void publish(String name, String phone, String date, int type, String mealmark, String tables, String budget, String project){
        mDialog.show();
        NewUserInfo userInfo = UserManager.getInstance(this).getNewUserInfo();
        NetWorkUtil.setCallback("HQOAApi/CeaterInvitationRecord",
                new BeanPublishInvitation(name,phone,date,areaID,type,0,userInfo.getUserId(),mealmark,tables,budget,project),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultPublishInvitation result = new Gson().fromJson(respose, ResultPublishInvitation.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                startActivity(new Intent(ComInviteMainActivity.this, ComInviteSuccActivity.class).putExtra("rank",result.getRanking()));
                                setNull();
                            } else {
                                Toast.makeText(ComInviteMainActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(ADListActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
//                        Toast.makeText(ADListActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setNull(){
        etName.setText("");
        etPhone.setText("");
        date.setText("");
    }

    private void showShare() {
        String url = "http://www.chenghunji.com/Capital/BeiHun?UserId=" +
                UserManager.getInstance(this).getNewUserInfo().getUserId();
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("送您一份新婚礼，快去成婚纪APP领取!");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText("婚庆、婚纱，全部花多少返多少！婚礼对戒等更多豪礼速来领!");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("");//确保SDcard下面存在此张图片
        oks.setImageUrl("http://www.chenghunji.com/img/index/B_LOGO_01.png");
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
                Toast.makeText(ComInviteMainActivity.this, "分享成功！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Toast.makeText(ComInviteMainActivity.this, "分享失败！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(Platform platform, int i) {
                Toast.makeText(ComInviteMainActivity.this, "取消分享！", Toast.LENGTH_SHORT).show();
            }
        });
        // 启动分享GUI
        oks.show(ComInviteMainActivity.this);
//        shareRedPacket(isToFriends);
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
        oks.setImageUrl("http://www.chenghunji.com/img/index/B_LOGO_01.png");
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
                Toast.makeText(ComInviteMainActivity.this, "分享成功！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Toast.makeText(ComInviteMainActivity.this, "分享失败！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(Platform platform, int i) {
                Toast.makeText(ComInviteMainActivity.this, "取消分享！", Toast.LENGTH_SHORT).show();
            }
        });
        // 启动分享GUI
        oks.show(ComInviteMainActivity.this);
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
