package com.dikai.chenghunjiclient.activity.register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import dmax.dialog.SpotsDialog;

public class CreateNewsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mLogo;
    private EditText mName;
    private String picUrl;
    private BeanRegister mBeanRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_news);
        EventBus.getDefault().register(this);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        mBeanRegister = (BeanRegister) getIntent().getSerializableExtra("register");
//        mDialog = new SpotsDialog(this, R.style.DialogCustom);
        mLogo = (ImageView) findViewById(R.id.user_info_logo);
        mName = (EditText) findViewById(R.id.user_info_name);
        findViewById(R.id.user_info_back).setOnClickListener(this);
        findViewById(R.id.user_info_save).setOnClickListener(this);
        findViewById(R.id.user_info_logo_layout).setOnClickListener(this);
        initImagePicker();

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
                    Toast.makeText(this, "请选择头像！", Toast.LENGTH_SHORT).show();
                }else {
                    mBeanRegister.setTrueName(mName.getText().toString().trim());
                    mBeanRegister.setLogo(picUrl);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("register",mBeanRegister);
                    startActivity(new Intent(this, SetNewsPwdActivity.class).putExtras(bundle));
                }
                break;
            case R.id.user_info_logo_layout:
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
                        .load(images.get(0).path)
                        .error(R.drawable.ic_default_image)
                        .centerCrop()
                        .into(mLogo);
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
                if(bean.getType() == Constants.USER_REGISTER_SUCCESS){
                    finish();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }

}
