package com.dikai.chenghunjiclient.activity.register;

import android.content.Intent;
import android.net.Uri;
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
import com.dikai.chenghunjiclient.bean.BeanRegister;
import com.dikai.chenghunjiclient.citypicker.SelectCityActivity;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.UserManager;
import com.gyf.barlibrary.ImmersionBar;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.util.ArrayList;

public class CreateIdentityActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout areaLayout;
    private ImageView logo;
    private EditText nameText;
    private EditText areaText;
    private TextView mIdentText;
    private TextView logoName;
    private TextView nameMark;
    private TextView mlocationText;

    private String areaID;
    private String picUrl;
    private BeanRegister mBeanRegister;
    private int userType = Constants.USER_TYPE_USUAL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_identity);
        EventBus.getDefault().register(this);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        initView();
    }

    private void initView() {
        mBeanRegister = (BeanRegister) getIntent().getSerializableExtra("register");
        logo = (ImageView) findViewById(R.id.activity_create_logo);
        nameText = (EditText) findViewById(R.id.activity_create_name);
        areaText = (EditText) findViewById(R.id.activity_create_area);
        mIdentText = (TextView) findViewById(R.id.activity_create_identity);
        logoName = (TextView) findViewById(R.id.activity_create_logo_name);
        nameMark = (TextView) findViewById(R.id.activity_create_name_mark);
        mlocationText = (TextView) findViewById(R.id.activity_create_location);
        areaLayout = (LinearLayout) findViewById(R.id.activity_create_area_layout);
        findViewById(R.id.activity_create_back).setOnClickListener(this);
        findViewById(R.id.activity_create_logo_layout).setOnClickListener(this);
        findViewById(R.id.activity_create_location_layout).setOnClickListener(this);
        findViewById(R.id.activity_create_next).setOnClickListener(this);
        initImagePicker();
        mIdentText.setText(UserManager.getInstance(this).getAllIdentMap().get(mBeanRegister.getProfession()));
        if(mBeanRegister.getProfession().equals("SF_1001000")){//酒店
            areaLayout.setVisibility(View.VISIBLE);
            userType = Constants.USER_TYPE_HOTEL;
            logoName.setText("酒店logo");
            nameMark.setText("酒店名称");
            nameText.setHint("请填写酒店名称");
        }else {
            logoName.setText("用户logo");
            nameMark.setText("用户名称");
            nameText.setHint("请填写用户名称");
            areaLayout.setVisibility(View.GONE);
            if(mBeanRegister.getProfession().equals("SF_13001000")){//车手
                userType = Constants.USER_TYPE_DRIVER;
            }else {
                userType = Constants.USER_TYPE_USUAL;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_create_back:
                onBackPressed();
                break;
//            case R.id.activity_create_identity_layout:
//                startActivity(new Intent(this, SelectIdentActivity.class));
//                break;
            case R.id.activity_create_logo_layout:
                openPhoto();
                break;
            case R.id.activity_create_location_layout:
                startActivity(new Intent(this, SelectCityActivity.class));
                break;
            case R.id.activity_create_next:
                next();
                break;
        }
    }

    private void next() {
        if(picUrl == null || "".equals(picUrl.trim())){
            Toast.makeText(this, "请选择logo！", Toast.LENGTH_SHORT).show();
        }else if(areaID == null){
            Toast.makeText(this, "请选择所在地区！", Toast.LENGTH_SHORT).show();
        }else if(nameText.getText() == null || "".equals(nameText.getText().toString().trim())){
            Toast.makeText(this, "请填写店铺名称！", Toast.LENGTH_SHORT).show();
        }else if(userType == Constants.USER_TYPE_HOTEL &&
                (mlocationText.getText() == null || "".equals(mlocationText.getText().toString().trim()))){
            Toast.makeText(this, "请填写具体地址！", Toast.LENGTH_SHORT).show();
        }else {
            mBeanRegister.setLogo(picUrl);
            mBeanRegister.setTrueName(nameText.getText().toString().trim());
            mBeanRegister.setLogo(picUrl);
            mBeanRegister.setRegion(areaID);
            mBeanRegister.setCorpID("0");
            mBeanRegister.setAge("0");
            mBeanRegister.setOwnedCompany("");
            if(userType == Constants.USER_TYPE_HOTEL){
                mBeanRegister.setAdress(areaText.getText().toString().trim());
            }else {
                mBeanRegister.setAdress("");
                mBeanRegister.setBusinesslicense("");
            }
            if(userType != Constants.USER_TYPE_DRIVER){
                mBeanRegister.setDrivinglicense("");
                mBeanRegister.setModelID("0");
            }

            Bundle bundle = new Bundle();
            bundle.putSerializable("register",mBeanRegister);
            if(userType == Constants.USER_TYPE_DRIVER){
                startActivity(new Intent(this, RegisterCarActivity.class)
                        .putExtras(bundle).putExtra("type",userType));
            }else {
                startActivity(new Intent(this, VerifyActivity.class)
                        .putExtras(bundle).putExtra("type",userType));
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
                        .into(logo);
            } else {
                Toast.makeText(this, "图片出了些问题...", Toast.LENGTH_SHORT).show();
            }
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
                    mlocationText.setText(bean.getCountry().getRegionName());
                }else if(bean.getType() == Constants.USER_REGISTER_SUCCESS){
                    finish();
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
