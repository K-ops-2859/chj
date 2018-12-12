package com.dikai.chenghunjiclient.activity.me;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.register.RegisterCarActivity;
import com.dikai.chenghunjiclient.activity.register.ResetPwdActivity;
import com.dikai.chenghunjiclient.bean.BeanEditUserInfo;
import com.dikai.chenghunjiclient.bean.BeanGetUserInfo;
import com.dikai.chenghunjiclient.bean.BeanUserInfo;
import com.dikai.chenghunjiclient.entity.NewUserInfo;
import com.dikai.chenghunjiclient.entity.ResultGetUserInfo;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.entity.ResultNewPhoneCode;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UpLoadImgThread;
import com.dikai.chenghunjiclient.util.UserManager;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener, OnDateSetListener {

    private ImageView mLogo;
    private EditText mName;
    private String picUrl;
    private SpotsDialog mDialog;
    private LinearLayout dateLayout;
    private TextView date;
    private String weddingDate = "";
    private TimePickerDialog mPickerDialog;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        mDialog = new SpotsDialog(this, R.style.DialogCustom);
        mLogo = (ImageView) findViewById(R.id.user_info_logo);
        mName = (EditText) findViewById(R.id.user_info_name);
        dateLayout = (LinearLayout) findViewById(R.id.date_layout);
        date = (TextView) findViewById(R.id.wedding_date);
        date.setOnClickListener(this);
        findViewById(R.id.user_info_back).setOnClickListener(this);
        findViewById(R.id.user_info_save).setOnClickListener(this);
        findViewById(R.id.user_info_logo_layout).setOnClickListener(this);
        initImagePicker();

        long Years = 10L * 365 * 1000 * 60 * 60 * 24L;
        mPickerDialog = new TimePickerDialog.Builder()
                .setCallBack(this)
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("选择您的预计婚期时间")
                .setYearText("年")
                .setMonthText("月")
                .setDayText("日")
                .setMinMillseconds(System.currentTimeMillis() - Years)
                .setMaxMillseconds(System.currentTimeMillis() + Years)
                .setThemeColor(ContextCompat.getColor(this, R.color.main))
                .setType(Type.YEAR_MONTH_DAY)
                .setWheelItemTextNormalColor(ContextCompat.getColor(this, R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(ContextCompat.getColor(this, R.color.main))
                .build();

        NewUserInfo info = UserManager.getInstance(this).getNewUserInfo();
        String code = info.getProfession().toUpperCase();
        Log.e("zhiye", code);

        weddingDate = info.getWedding();
        date.setText(info.getWedding());
        if("70CD854E-D943-4607-B993-91912329C61E".equals(code)){//用户（新人）
            dateLayout.setVisibility(View.VISIBLE);
        }else {
            dateLayout.setVisibility(View.GONE);
        }
        mName.setText(info.getName());
        Glide.with(this).load(info.getHeadportrait()).into(mLogo);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_info_back:
                onBackPressed();
                break;
            case R.id.user_info_save:
                if(mName.getText() == null || "".equals(mName.getText().toString().trim())){
                    Toast.makeText(this, "请输入昵称！", Toast.LENGTH_SHORT).show();
                }else if(picUrl == null || "".equals(picUrl.trim())){
                    editInfo(mName.getText().toString().trim(),"");
                }else {
                    List<String> list = new ArrayList<>();
                    list.add(picUrl);
                    upload(list);
                }
                break;
            case R.id.user_info_logo_layout:
                openPhoto();
                break;
            case R.id.wedding_date:
                mPickerDialog.show(getSupportFragmentManager(), "year_month_day");
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }

    /**
     * 打开相册
     */
    private void openPhoto() {
        Intent intent = new Intent(this, ImageGridActivity.class);
        startActivityForResult(intent, Constants.SET_LOGO);
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setMultiMode(false); // 是否为多选模式
//        imagePicker.setSelectLimit(1); // 多选模式下限制数量，默认为9
        imagePicker.setShowCamera(true); // 显示拍照按钮
        // 是否按矩形区域
        // 保存裁剪后的图片是按矩形区域保存还是裁剪框的形状，
        // 例如圆形裁剪的时候，该参数给true，那么保存的图片是矩形区域，
        // 如果该参数给false，保存的图片是圆形区域
        imagePicker.setSaveRectangle(true);
        imagePicker.setCrop(true);        // 允许裁剪（单选有效）
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  // 裁剪框的形状
        imagePicker.setFocusWidth(400);   // 裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(400);  // 裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(400);// 保存文件的宽度。单位像素
        imagePicker.setOutPutY(400);// 保存文件的高度。单位像素
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == Constants.SET_LOGO) {
                ArrayList<ImageItem> images = (ArrayList) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                picUrl = images.get(0).path;
                Glide.with(this)
                        .load(images.get(0).path)
                        .error(R.drawable.ic_default_image)
                        .centerCrop()
                        .into(mLogo);
            } else {
                Toast.makeText(this, "图片出了些问题...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void upload(List<String> list){
        UpLoadImgThread.getInstance(this).upLoad(list, "http://121.42.156.151:93/FileStorage.aspx",
                UserManager.getInstance(this).getNewUserInfo().getUserId(), "2", new UpLoadImgThread.CallBackListener() {
                    @Override
                    public void onFinish(List<String> values) {
                        editInfo(mName.getText().toString().trim(), values.get(0));
                    }

                    @Override
                    public void onError(String e) {
                        mDialog.dismiss();
                        Toast.makeText(UserInfoActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 更新信息
     */
    private void editInfo(String name, String logo){
        final NewUserInfo info = UserManager.getInstance(this).getNewUserInfo();
        NetWorkUtil.setCallback("HQOAApi/UpdateUserInfo",
                new BeanEditUserInfo(info.getUserId(),name,logo,weddingDate),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                Toast.makeText(UserInfoActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                EventBus.getDefault().post(new EventBusBean(Constants.USER_INFO_CHANGE));
                                finish();
                            } else {
                                Toast.makeText(UserInfoActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Toast.makeText(UserInfoActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        weddingDate = format.format(new Date(millseconds));
        date.setText(weddingDate);
    }
}
