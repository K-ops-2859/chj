package com.dikai.chenghunjiclient.activity.me;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.me.PublishPhotoAdapter;
import com.dikai.chenghunjiclient.bean.BeanID;
import com.dikai.chenghunjiclient.bean.BeanSupEditInfo;
import com.dikai.chenghunjiclient.citypicker.DBManager;
import com.dikai.chenghunjiclient.citypicker.SelectCityActivity;
import com.dikai.chenghunjiclient.entity.NewUserInfo;
import com.dikai.chenghunjiclient.entity.ResultGetNewUserInfo;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.entity.ResultNewPhoneCode;
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

public class SupEditInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mPhone;
    private TextView region;
    private EditText mIntro;
    private EditText address;
    private String areaID;
    private SpotsDialog mDialog;

    private RecyclerView mRecyclerView;
    private PublishPhotoAdapter mPhotoAdapter;
    private List<ImageItem> mList;
    private ImagePicker imagePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sup_edit_info);
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
        mIntro = (EditText) findViewById(R.id.user_info_intro);
        mPhone = (EditText) findViewById(R.id.user_info_phone);
        address = (EditText) findViewById(R.id.user_info_address);
        region = (TextView) findViewById(R.id.user_info_region);
        region.setOnClickListener(this);
        findViewById(R.id.user_info_back).setOnClickListener(this);
        findViewById(R.id.user_info_save).setOnClickListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.add_room_recycler);
        mList = new ArrayList<>();
        mPhotoAdapter = new PublishPhotoAdapter(this, mList, 9);
        mPhotoAdapter.setAddClickListener(new PublishPhotoAdapter.OnAddClickListener() {
            @Override
            public void onClick() {
                imagePicker.setSelectLimit(mPhotoAdapter.getMaxPhotoNum());
                openPhoto();
            }
        });
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        mRecyclerView.setAdapter(mPhotoAdapter);
        initImagePicker();
        getInfo();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_info_back:
                onBackPressed();
                break;
            case R.id.user_info_region:
                startActivity(new Intent(this, SelectCityActivity.class));
                break;
            case R.id.user_info_save:
                if(mPhone.getText() == null || "".equals(mPhone.getText().toString().trim())){
                    Toast.makeText(this, "请输入联系人手机号！", Toast.LENGTH_SHORT).show();
                }else if(areaID == null || "".equals(areaID)){
                    Toast.makeText(this, "请选择地区！", Toast.LENGTH_SHORT).show();
                }else if(address.getText() == null || "".equals(address.getText().toString().trim())){
                    Toast.makeText(this, "请输入地址！", Toast.LENGTH_SHORT).show();
                }else if(mIntro.getText() == null || "".equals(mIntro.getText().toString().trim())){
                    Toast.makeText(this, "请输入简介！", Toast.LENGTH_SHORT).show();
                }else {
                    mDialog.show();
                    if(mPhotoAdapter.getResult().size() > 0){
                        upload(mPhotoAdapter.getResult());
                    }else {
                        editInfo(mPhone.getText().toString(),address.getText().toString(),mIntro.getText().toString(),"");
                    }
                }
                break;
        }
    }

    private void setData(ResultGetNewUserInfo result) {
        address.setText(result.getAddress());
        mPhone.setText(result.getPhone());
        mIntro.setText(result.getAbstract());
        areaID = result.getRegion();
        if(areaID != null && !"".equals(areaID)){
            region.setText(DBManager.getInstance(this).getCityName(areaID));
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
        imagePicker = ImagePicker.getInstance();
        imagePicker.setMultiMode(true); // 是否为多选模式
        imagePicker.setShowCamera(true); // 显示拍照按钮
        imagePicker.setSelectLimit(9);

        // 是否按矩形区域
        // 保存裁剪后的图片是按矩形区域保存还是裁剪框的形状，
        // 例如圆形裁剪的时候，该参数给true，那么保存的图片是矩形区域，
        // 如果该参数给false，保存的图片是圆形区域
        imagePicker.setSaveRectangle(true);
        imagePicker.setCrop(false);        // 允许裁剪（单选有效）
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
                mPhotoAdapter.addAll(images);
            } else {
                Toast.makeText(this, "图片出了些问题...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void upload(final List<String> list) {
        UpLoadImgThread.getInstance(this).upLoad(list, "http://121.42.156.151:93/FileStorage.aspx",
                UserManager.getInstance(this).getNewUserInfo().getUserId(), "2", new UpLoadImgThread.CallBackListener() {
                    @Override
                    public void onFinish(List<String> values) {
                        String pics = "";
                        for (int i = 0; i < values.size(); i++) {
                            if (i < values.size() - 1) {
                                pics = pics + values.get(i) + ",";
                            } else {
                                pics = pics + values.get(i);
                            }
                        }
                        editInfo(mPhone.getText().toString(),address.getText().toString(),mIntro.getText().toString(),pics);
                    }

                    @Override
                    public void onError(String e) {
                        mDialog.dismiss();
                        Toast.makeText(SupEditInfoActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    /**
     * 更新信息
     */
    private void editInfo(final String phone, String address, String intro, String pics){
//        final NewUserInfo info = UserManager.getInstance(this).getNewUserInfo();
//        NetWorkUtil.setCallback("HQOAApi/UpdateFacilitatorInfo",
//                new BeanSupEditInfo(info.getFacilitatorId(),address,phone,intro,"",areaID,pics,"",""),
//                new NetWorkUtil.CallBackListener() {
//                    @Override
//                    public void onFinish(final String respose) {
//                        mDialog.dismiss();
//                        Log.e("返回值",respose);
//                        try {
//                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
//                            if ("200".equals(result.getMessage().getCode())) {
//                                Toast.makeText(SupEditInfoActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
//                                EventBus.getDefault().post(new EventBusBean(Constants.USER_INFO_CHANGE));
//                                finish();
//                            } else {
//                                Toast.makeText(SupEditInfoActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (Exception e) {
//                            Log.e("json解析出错",e.toString());
//                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onError(final String e) {
//                        mDialog.dismiss();
//                        Toast.makeText(SupEditInfoActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
//                    }
//                });
    }

    /**
     * 获取信息
     */
    private void getInfo(){
        NewUserInfo info = UserManager.getInstance(this).getNewUserInfo();
        NetWorkUtil.setCallback("HQOAApi/GetUserFacilitatorInfo",
                new BeanID(info.getUserId()),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultGetNewUserInfo result = new Gson().fromJson(respose, ResultGetNewUserInfo.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                setData(result);
                            } else {
                                Toast.makeText(SupEditInfoActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        Toast.makeText(SupEditInfoActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
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
