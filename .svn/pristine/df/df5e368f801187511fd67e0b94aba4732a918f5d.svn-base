package com.dikai.chenghunjiclient.activity.invitation;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.awen.photo.controller.ToolBarHelper;
import com.awen.photo.photopick.controller.PhotoPagerConfig;
import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.PhotoPickActivity;
import com.dikai.chenghunjiclient.activity.discover.PublishDynamicActivity;
import com.dikai.chenghunjiclient.adapter.OnItemClickListener;
import com.dikai.chenghunjiclient.adapter.invitation.VipInviteBusinessAdapter;
import com.dikai.chenghunjiclient.bean.BeanVipUpImage;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UpLoadImgThread;
import com.dikai.chenghunjiclient.util.UserManager;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

/**
 * Created by cmk03 on 2018/10/16.
 */

public class VipInviteBusinessActivity extends AppCompatActivity implements View.OnClickListener {

    private PhotoPagerConfig.Builder photoBuilder;
    private ArrayList<ImageItem> images;
    private ImagePicker imagePicker;
    private ImageView ivUpLoad1;
    private ImageView ivUpLoad2;
    private ImageView ivUpLoad3;
    private SpotsDialog mDialog;
    private int TAG = -1;
    private ArrayList<String> imageList = new ArrayList<>();
    private TextView tvSub;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vipinvite_business);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();

        initView();
        initImagePicker();
    }

    private void initView() {
        Toolbar mToolBar = (Toolbar) findViewById(R.id.toolbar);
        mDialog = new SpotsDialog(this, R.style.DialogCustom);
        ivUpLoad1 = (ImageView) findViewById(R.id.iv_upload1);
        ivUpLoad2 = (ImageView) findViewById(R.id.iv_upload2);
        ivUpLoad3 = (ImageView) findViewById(R.id.iv_upload3);
        tvSub = (TextView) findViewById(R.id.tv_submit);

        ivUpLoad1.setOnClickListener(this);
        ivUpLoad2.setOnClickListener(this);
        ivUpLoad3.setOnClickListener(this);

        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageList.size() == 0) {
                    Toast.makeText(VipInviteBusinessActivity.this, "请上传至少一张照片", Toast.LENGTH_SHORT).show();
                } else {
                    upLoadPic(imageList);
                }
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_upload1:
                TAG = 1;
                openPhoto();
                break;
            case R.id.iv_upload2:
                TAG = 2;
                openPhoto();
                break;
            case R.id.iv_upload3:
                TAG = 3;
                openPhoto();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == Constants.SET_LOGO) {
                images = (ArrayList) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
//                picUrl = images.get(0).path;
//                pic.setImageURI(Uri.fromFile(new File(images.get(0).path)));
                if (TAG == 1) {
                    imageList.clear();
                    Glide.with(VipInviteBusinessActivity.this).load(images.get(0).path).centerCrop().into(ivUpLoad1);
                    imageList.add(images.get(0).path);
                    tvSub.setBackgroundResource(R.drawable.jianbian_golden);
                    tvSub.setTextColor(ContextCompat.getColor(VipInviteBusinessActivity.this, R.color.white));
                    TAG = -1;
                } else if (TAG == 2) {
                    imageList.clear();
                    Glide.with(VipInviteBusinessActivity.this).load(images.get(0).path).centerCrop().into(ivUpLoad2);
                    imageList.add(images.get(0).path);
                    tvSub.setBackgroundResource(R.drawable.jianbian_golden);
                    tvSub.setTextColor(ContextCompat.getColor(VipInviteBusinessActivity.this, R.color.white));
                    TAG = -1;
                } else if (TAG == 3) {
                    imageList.clear();
                    Glide.with(VipInviteBusinessActivity.this).load(images.get(0).path).centerCrop().into(ivUpLoad3);
                    imageList.add(images.get(0).path);
                    tvSub.setBackgroundResource(R.drawable.jianbian_golden);
                    tvSub.setTextColor(ContextCompat.getColor(VipInviteBusinessActivity.this, R.color.white));
                    TAG = -1;
                }
            } else {
                Toast.makeText(this, "图片出了些问题...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void upLoad(String images) {
        String userId = UserManager.getInstance(this).getNewUserInfo().getUserId();
        Log.e("userId====", userId);
        NetWorkUtil.setCallback("HQOAApi/ApplyInvitationVIP",
                new BeanVipUpImage(userId, images, 1), new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(String respose) {
                        mDialog.dismiss();
                        try {
                            ResultMessage message = new Gson().fromJson(respose, ResultMessage.class);
                            if (message.getMessage().getCode().equals("200")) {
                                startActivity(new Intent(VipInviteBusinessActivity.this, VipInviteBusinessWaitActivity.class));
//                                Toast.makeText(VipInviteBusinessActivity.this, "已上传", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(VipInviteBusinessActivity.this, message.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ex) {
                            Log.e("错误==", ex.toString());
                        }
                    }

                    @Override
                    public void onError(String e) {
                        mDialog.dismiss();
                    }
                });
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
//      imagePicker = ImagePicker.getInstance();
//        imagePicker.setImageLoader(GlideImageLoader.getInstance()); //设置图片加载器
        imagePicker.setMultiMode(false); // 是否为多选模式
        imagePicker.setSelectLimit(1); // 多选模式下限制数量，默认为9
        imagePicker.setShowCamera(true); // 显示拍照按钮
        // 是否按矩形区域
        // 保存裁剪后的图片是按矩形区域保存还是裁剪框的形状，
        // 例如圆形裁剪的时候，该参数给true，那么保存的图片是矩形区域，
        // 如果该参数给false，保存的图片是圆形区域
        imagePicker.setSaveRectangle(false);
        imagePicker.setCrop(false);        // 允许裁剪（单选有效）
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  // 裁剪框的形状
//        imagePicker.setFocusWidth(400);   // 裁剪框的宽度。单位像素（圆形自动取宽高最小值）
//        imagePicker.setFocusHeight(400);  // 裁剪框的高度。单位像素（圆形自动取宽高最小值）
//        imagePicker.setOutPutX(400);// 保存文件的宽度。单位像素
//        imagePicker.setOutPutY(400);// 保存文件的高度。单位像素
        //      mPhotoAdapter.setImagePicker(imagePicker);
    }

    public void upLoadPic(List<String> list) {
        mDialog.show();
        UpLoadImgThread.getInstance(this).upLoad(list, "http://121.42.156.151:93/FileStorage.aspx",
                "0", "1", new UpLoadImgThread.CallBackListener() {
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
                        upLoad(pics);
                    }

                    @Override
                    public void onError(String e) {
                        mDialog.dismiss();
                        Toast.makeText(VipInviteBusinessActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
}
