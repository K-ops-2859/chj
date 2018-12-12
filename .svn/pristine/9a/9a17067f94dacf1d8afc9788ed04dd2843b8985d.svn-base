package com.dikai.chenghunjiclient.activity.register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.bean.BeanRegister;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.gyf.barlibrary.ImmersionBar;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

public class VerifyActivity extends AppCompatActivity implements View.OnClickListener {

    private String picUrl1 = "";
    private String picUrl2 = "";
    private String picUrl3 = "";
    private String picUrl4 = "";
    private LinearLayout firstLayout;
    private LinearLayout secondLayout;
    private TextView secondText;
    private int nowPic;
    private ImageView ivIdentityFront;
    private ImageView ivIdentityBack;
    private ImageView ivIdentityHand;
    private ImageView ivBusinessFront;
    private BeanRegister mBeanRegister;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
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
        type = getIntent().getIntExtra("type", Constants.USER_TYPE_USUAL);
        ivIdentityFront = (ImageView) findViewById(R.id.iv_identity_front);
        ivIdentityBack = (ImageView) findViewById(R.id.iv_identity_back);
        ivIdentityHand = (ImageView) findViewById(R.id.iv_identity_hand);
        ivBusinessFront = (ImageView) findViewById(R.id.iv_business_front);
        firstLayout = (LinearLayout) findViewById(R.id.activity_verify_first_photo);
        secondLayout = (LinearLayout) findViewById(R.id.activity_verify_second_photo);
        secondText = (TextView) findViewById(R.id.activity_verify_second_text);
        findViewById(R.id.activity_verify_back).setOnClickListener(this);
        findViewById(R.id.activity_verify_next).setOnClickListener(this);
        ivIdentityFront.setOnClickListener(this);
        ivIdentityBack.setOnClickListener(this);
        ivIdentityHand.setOnClickListener(this);
        ivBusinessFront.setOnClickListener(this);
        initImagePicker();
        if (type == Constants.USER_TYPE_HOTEL) {
            firstLayout.setVisibility(View.VISIBLE);
            secondLayout.setVisibility(View.VISIBLE);
            secondText.setText("营业执照（必填）");
            ivBusinessFront.setImageResource(R.mipmap.ic_add_business);
        } else if (type == Constants.USER_TYPE_DRIVER) {
            firstLayout.setVisibility(View.VISIBLE);
            secondLayout.setVisibility(View.VISIBLE);
            secondText.setText("驾驶证照（必填）");
            ivBusinessFront.setImageResource(R.mipmap.ic_up_load);
        } else {
            firstLayout.setVisibility(View.VISIBLE);
            secondLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_verify_back:
                onBackPressed();
                break;
            case R.id.iv_identity_front:
                nowPic = 1;
                openPhoto();
                break;
            case R.id.iv_identity_back:
                nowPic = 2;
                openPhoto();
                break;
            case R.id.iv_identity_hand:
                nowPic = 3;
                openPhoto();
                break;
            case R.id.iv_business_front:
                nowPic = 4;
                openPhoto();
                break;
            case R.id.activity_verify_next:
                next();
                break;
        }
    }

    private void next() {
//        if(picUrl1 == null || "".equals(picUrl1.trim())){
//            Toast.makeText(this, "请上传身份证正面照！", Toast.LENGTH_SHORT).show();
//        }else if(picUrl2 == null || "".equals(picUrl2.trim())){
//            Toast.makeText(this, "请上传身份证反面照！", Toast.LENGTH_SHORT).show();
//        }else if(picUrl3 == null || "".equals(picUrl3.trim())){
//            Toast.makeText(this, "请上传手持身份证正面照！", Toast.LENGTH_SHORT).show();
//        }else{
        mBeanRegister.setFrontIDcard(picUrl1);
        mBeanRegister.setNegativeIDcard(picUrl2);
        mBeanRegister.setHandheldIDcard(picUrl3);
        if (type == Constants.USER_TYPE_DRIVER && ("".equals(picUrl4.trim()))) {
            Toast.makeText(this, "请上传驾驶证正面照！", Toast.LENGTH_SHORT).show();
        } else if (type == Constants.USER_TYPE_HOTEL && ("".equals(picUrl4.trim()))) {
            Toast.makeText(this, "请上传营业执照正面照！", Toast.LENGTH_SHORT).show();
        } else {
            if (type == Constants.USER_TYPE_DRIVER) {
                mBeanRegister.setDrivinglicense(picUrl4);
            } else if (type == Constants.USER_TYPE_HOTEL) {
                mBeanRegister.setBusinesslicense(picUrl4);
            }
            Bundle bundle = new Bundle();
            bundle.putSerializable("register", mBeanRegister);
            startActivity(new Intent(this, ResetPwdActivity.class)
                    .putExtras(bundle).putExtra("type", type).putExtra("usertype", 1));
        }
        // }
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
                setPhoto(images.get(0).path);
//                logo.setImageURI(Uri.fromFile(new File(images.get(0).path)));
            } else {
                Toast.makeText(this, "图片出了些问题...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setPhoto(String url) {
        switch (nowPic) {
            case 1:
                picUrl1 = url;
                System.out.println("picuUrl1===============" + picUrl1);
                Glide.with(this).load(url).centerCrop().into(ivIdentityFront);
                break;
            case 2:
                picUrl2 = url;
                Glide.with(this).load(url).centerCrop().into(ivIdentityBack);
                break;
            case 3:
                picUrl3 = url;
                Glide.with(this).load(url).centerCrop().into(ivIdentityHand);
                break;
            case 4:
                verify(url, picUrl4);

                picUrl4 = url;
                System.out.println("picuUrl4===============" + picUrl4);
                Glide.with(this).load(url).centerCrop().into(ivBusinessFront);
                break;
        }
    }

    private String verify(String url, String picUrl) {
        System.out.println("url========" +
                url);
        if (url.equals("")) {
            picUrl = "";
        } else {
            picUrl = url;
        }
        return picUrl;
    }

    /**
     * 事件总线刷新
     *
     * @param bean
     */
    @Subscribe
    public void onEventMainThread(final EventBusBean bean) {
        runOnUiThread(new Runnable() {
            public void run() {
                if (bean.getType() == Constants.USER_REGISTER_SUCCESS) {
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
