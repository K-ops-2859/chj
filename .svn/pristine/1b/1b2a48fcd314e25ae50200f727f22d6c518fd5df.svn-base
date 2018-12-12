package com.dikai.chenghunjiclient.activity.me;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.bean.BeanEditHotelInfo;
import com.dikai.chenghunjiclient.bean.BeanGetHotelInfo;
import com.dikai.chenghunjiclient.bean.BeanGetSupList;
import com.dikai.chenghunjiclient.bean.BeanSupEditInfo;
import com.dikai.chenghunjiclient.citypicker.DBManager;
import com.dikai.chenghunjiclient.citypicker.SelectCityActivity;
import com.dikai.chenghunjiclient.entity.ResultGetHotelInfo;
import com.dikai.chenghunjiclient.entity.ResultGetSupplierInfo;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.entity.UserInfo;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UpLoadImgThread;
import com.dikai.chenghunjiclient.util.UserManager;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class HotelEditInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mLogo;
    private EditText mName;
    private EditText mPhone;
    private EditText address;
    private TextView region;
    private EditText mIntro;
    private String picUrl;
    private String areaID;
    private SpotsDialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_edit_info);
        EventBus.getDefault().register(this);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        mDialog = new SpotsDialog(this, R.style.DialogCustom);
        mLogo = (ImageView) findViewById(R.id.hotel_info_logo);
        mName = (EditText) findViewById(R.id.hotel_info_name);
        mIntro = (EditText) findViewById(R.id.hotel_info_intro);
        mPhone = (EditText) findViewById(R.id.hotel_info_phone);
        address = (EditText) findViewById(R.id.hotel_info_address);
        region = (TextView) findViewById(R.id.hotel_info_region);
        region.setOnClickListener(this);
        findViewById(R.id.hotel_info_back).setOnClickListener(this);
        findViewById(R.id.hotel_info_save).setOnClickListener(this);
        findViewById(R.id.hotel_info_logo_layout).setOnClickListener(this);
        initImagePicker();
        getInfo();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.hotel_info_back:
                onBackPressed();
                break;
            case R.id.hotel_info_region:
                startActivity(new Intent(this, SelectCityActivity.class));
                break;
            case R.id.hotel_info_save:
                if(mName.getText() == null || "".equals(mName.getText().toString().trim())){
                    Toast.makeText(this, "请输入昵称！", Toast.LENGTH_SHORT).show();
                }else if(mPhone.getText() == null || "".equals(mPhone.getText().toString().trim())){
                    Toast.makeText(this, "请输入电话！", Toast.LENGTH_SHORT).show();
                }else if(mIntro.getText() == null || "".equals(mIntro.getText().toString().trim())){
                    Toast.makeText(this, "请输入简介！", Toast.LENGTH_SHORT).show();
                }else if(address.getText() == null || "".equals(address.getText().toString().trim())){
                    Toast.makeText(this, "请输入地址！", Toast.LENGTH_SHORT).show();
                }else if(picUrl == null || "".equals(picUrl.trim())){
                    mDialog.show();
                    editInfo(mName.getText().toString().trim(),"");
                }else {
                    mDialog.show();
                    List<String> list = new ArrayList<>();
                    list.add(picUrl);
                    upload(list);
                }
                break;
            case R.id.hotel_info_logo_layout:
                openPhoto();
                break;
        }
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
                        .load(picUrl)
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
                UserManager.getInstance(this).getUserInfo().getUserID(), "2", new UpLoadImgThread.CallBackListener() {
                    @Override
                    public void onFinish(List<String> values) {
                        editInfo(mName.getText().toString().trim(), values.get(0));
                    }

                    @Override
                    public void onError(String e) {
                        mDialog.dismiss();
                        Toast.makeText(HotelEditInfoActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setData(ResultGetHotelInfo result) {
        mName.setText(result.getRummeryName());
        mPhone.setText(result.getContactPhone());
        address.setText(result.getAddress());
        mIntro.setText(result.getBriefinTroduction());
        areaID = result.getRegion();
        if(areaID != null && !"".equals(areaID)){
            region.setText(DBManager.getInstance(this).getCityName(areaID));
        }
        Glide.with(this).load(result.getHotelLogo()).into(mLogo);
    }

    /**
     * 更新信息
     */
    private void editInfo(String name, String logo){
        UserInfo info = UserManager.getInstance(this).getUserInfo();
        NetWorkUtil.setCallback("User/UpRummeryInfo",
                new BeanEditHotelInfo(info.getRummeryID(),info.getSupplierID(),"",name,logo, "","",mPhone.getText().toString(),
                        address.getText().toString().trim(),"0", mIntro.getText().toString().trim(),areaID),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                Toast.makeText(HotelEditInfoActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                EventBus.getDefault().post(new EventBusBean(Constants.USER_RELOGIN));
                                finish();
                            } else {
                                Toast.makeText(HotelEditInfoActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Toast.makeText(HotelEditInfoActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 获取信息
     */
    private void getInfo(){
//        UserInfo info = UserManager.getInstance(this).getUserInfo();
//        NetWorkUtil.setCallback("User/GetRummeryInfo",
//                new BeanGetHotelInfo(info.getRummeryID(), info.getSupplierID()),
//                new NetWorkUtil.CallBackListener() {
//                    @Override
//                    public void onFinish(final String respose) {
//                        Log.e("返回值",respose);
//                        try {
//                            ResultGetHotelInfo result = new Gson().fromJson(respose, ResultGetHotelInfo.class);
//                            if ("200".equals(result.getMessage().getCode())) {
//                                setData(result);
//                            } else {
//                                Toast.makeText(HotelEditInfoActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (Exception e) {
//                            Log.e("json解析出错",e.toString());
//                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onError(final String e) {
//                        Toast.makeText(HotelEditInfoActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
//                    }
//                });
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
                    region.setText(bean.getCountry().getRegionName());
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
        EventBus.getDefault().unregister(this);
    }
}
