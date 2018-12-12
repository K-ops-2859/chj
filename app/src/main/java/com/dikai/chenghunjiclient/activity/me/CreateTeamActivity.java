package com.dikai.chenghunjiclient.activity.me;

import android.content.Intent;
import android.support.annotation.Nullable;
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
import com.dikai.chenghunjiclient.bean.BeanCreateTeam;
import com.dikai.chenghunjiclient.citypicker.SelectCityActivity;
import com.dikai.chenghunjiclient.entity.ResultCreateTeam;
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

public class CreateTeamActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mBack;
    private TextView mFinish;
    private String picUrl;
    private ImageView logo;
    private TextView regionText;
    private EditText nameEdit;
    private EditText introEdit;
    private EditText addressEdit;
    private String areaID;
    private SpotsDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_team);
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
        mBack = (ImageView) findViewById(R.id.create_team_back);
        mFinish = (TextView) findViewById(R.id.create_team_finish);
        logo = (ImageView) findViewById(R.id.create_team_logo);
        regionText = (TextView) findViewById(R.id.create_team_region);
        nameEdit = (EditText) findViewById(R.id.create_team_name);
        introEdit = (EditText) findViewById(R.id.create_team_intro);
        addressEdit = (EditText) findViewById(R.id.create_team_address);
        logo.setOnClickListener(this);
        regionText.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mFinish.setOnClickListener(this);
        initImagePicker();
    }

    @Override
    public void onClick(View v) {
        if(v == logo){
            openPhoto();
        }else if(v == mBack){
            onBackPressed();
        }else if(v == regionText){
            startActivity(new Intent(this, SelectCityActivity.class));
        }else if(v == mFinish){
            if(nameEdit.getText() == null || "".equals(nameEdit.getText().toString().trim())){
                Toast.makeText(this, "车队名称不能为空！", Toast.LENGTH_SHORT).show();
            }else if(introEdit.getText() == null || "".equals(introEdit.getText().toString().trim())){
                Toast.makeText(this, "车队介绍不能为空！", Toast.LENGTH_SHORT).show();
            }else if(addressEdit.getText() == null || "".equals(addressEdit.getText().toString().trim())){
                Toast.makeText(this, "车队地址不能为空！", Toast.LENGTH_SHORT).show();
            }else if(picUrl == null || "".equals(picUrl.trim())){
                Toast.makeText(this, "请选择logo！", Toast.LENGTH_SHORT).show();
            }else if(areaID == null || "".equals(areaID.trim())){
                Toast.makeText(this, "请选择地区！", Toast.LENGTH_SHORT).show();
            }else{
                mDialog.show();
                List<String> list = new ArrayList<>();
                list.add(picUrl);
                upload(list);
            }
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == Constants.SET_LOGO) {
                ArrayList<ImageItem> images = (ArrayList) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                picUrl = images.get(0).path;
                Glide.with(this)
                        .load(images.get(0).path)
                        .error(R.drawable.ic_default_image)
                        .centerCrop()
                        .into(logo);
//                logo.setImageURI(Uri.fromFile(new File(images.get(0).path)));
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
                        create(values.get(0));
                    }

                    @Override
                    public void onError(String e) {
                        mDialog.dismiss();
                        Toast.makeText(CreateTeamActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 创建车队
     */
    private void create(String logo){
        NetWorkUtil.setCallback("User/AddSupplierInfo",
                new BeanCreateTeam(UserManager.getInstance(this).getUserInfo().getUserID(),logo,areaID,
                        addressEdit.getText().toString().trim(),nameEdit.getText().toString().trim()
                        ,introEdit.getText().toString().trim(), ""),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultCreateTeam result = new Gson().fromJson(respose, ResultCreateTeam.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                Toast.makeText(CreateTeamActivity.this, "创建车队成功", Toast.LENGTH_SHORT).show();
                                upInfo();
                            } else {
                                Toast.makeText(CreateTeamActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Toast.makeText(CreateTeamActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void upInfo(){
        UserManager.getInstance(this).autoLogin(new UserManager.OnLoginListener() {
            @Override
            public void onFinish() {
                mDialog.dismiss();
                EventBus.getDefault().post(new EventBusBean(Constants.USER_INFO_CHANGE));
                finish();
            }

            @Override
            public void onError(String e) {
                mDialog.dismiss();
                Toast.makeText(CreateTeamActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
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
                    Log.e("name", bean.getCountry().getRegionName());
                    areaID = bean.getCountry().getRegionId();
                    regionText.setText(bean.getCountry().getRegionName());
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        ImmersionBar.with(this).destroy();
    }
}
