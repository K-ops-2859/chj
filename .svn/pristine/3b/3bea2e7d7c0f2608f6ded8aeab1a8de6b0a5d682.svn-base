package com.dikai.chenghunjiclient.activity.discover;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.me.PublishPhotoAdapter;
import com.dikai.chenghunjiclient.bean.PublishDynamicBean;
import com.dikai.chenghunjiclient.entity.ResultMessage;
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

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

/**
 * Created by cmk03 on 2018/1/23.
 */

public class PublishDynamicActivity extends AppCompatActivity {

    private String userID;
    private int type;
    private EditText etContent;
    private ImagePicker imagePicker;
    private int photoType;
    private List<ImageItem> mList;
    private ImageView facePic;
    private String faceUrl;
    private PublishPhotoAdapter mPhotoAdapter;
    private SpotsDialog mDialog;
    private ArrayList<ImageItem> images;
    //private ImageView ivCamera;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_dynamic);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();

        Toolbar mToolBar = (Toolbar) findViewById(R.id.toolbar);
        etContent = (EditText) findViewById(R.id.et_content);
       // ivCamera = (ImageView) findViewById(R.id.iv_camera);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayout llPublish = (LinearLayout) findViewById(R.id.ll_publish);
        TextView tvPublish = (TextView) findViewById(R.id.tv_publish);
        mDialog = new SpotsDialog(this, R.style.DialogCustom);
        mList = new ArrayList<>();
        mPhotoAdapter = new PublishPhotoAdapter(this, mList, 9);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        mRecyclerView.setAdapter(mPhotoAdapter);
        initImagePicker();
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mPhotoAdapter.setAddClickListener(new PublishPhotoAdapter.OnAddClickListener() {
            @Override
            public void onClick() {
                openPhoto();
            }
        });

//        llPublish.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                verify();
//            }
//        });

        tvPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verify();
            }
        });
    }

    private void verify() {
        if (userID == null) {
            userID = UserManager.getInstance(this).getNewUserInfo().getUserId();
        }
        String content = etContent.getText().toString();
        List<String> result = mPhotoAdapter.getResult();
        System.out.println("===============” " + result);
        if (result.isEmpty()) {
            Toast.makeText(this, "添加几张照片再发布吧", Toast.LENGTH_SHORT).show();
            return;
        } else {
            upLoadPic(content, result);
        }
        mDialog.show();
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
        imagePicker.setMultiMode(true); // 是否为多选模式
        imagePicker.setSelectLimit(9); // 多选模式下限制数量，默认为9
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
        mPhotoAdapter.setImagePicker(imagePicker);
    }

    public void upLoadPic(final String content, List<String> list) {
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
                        publish(content, pics);
                    }

                    @Override
                    public void onError(String e) {
                        mDialog.dismiss();
                        Toast.makeText(PublishDynamicActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == Constants.SET_LOGO) {
                images = (ArrayList) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
//                picUrl = images.get(0).path;
//                pic.setImageURI(Uri.fromFile(new File(images.get(0).path)));
                mPhotoAdapter.addAll(images);
            } else {
                Toast.makeText(this, "图片出了些问题...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void publish(String content, String pics) {
        NetWorkUtil.setCallback("HQOAApi/AddDynamic",
                new PublishDynamicBean(1, userID, "", content, pics, " "),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(String respose) {
                        mDialog.dismiss();
                        Log.e("返回值" , respose);
                        try {
                            ResultMessage resultMessage = new Gson().fromJson(respose, ResultMessage.class);
                            if (resultMessage.getMessage().getCode().equals("200")) {
                                Toast.makeText(PublishDynamicActivity.this, "上传完成", Toast.LENGTH_SHORT).show();
                                System.out.println("信息" + resultMessage.getMessage().getInform() + "状态码" + resultMessage.getMessage().getCode());
                                EventBus.getDefault().post(new EventBusBean(Constants.DYNAMIC_PUBLISHED));
                                PublishDynamicActivity.this.finish();
                            } else {
                                Toast.makeText(PublishDynamicActivity.this, resultMessage.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                                System.out.println("返回" + resultMessage.getMessage().getInform());
                            }
                        }catch (Exception e){
                            Log.e("网络请求出错",e.toString());
                        }
                    }

                    @Override
                    public void onError(String e) {
                        mDialog.dismiss();
                        Log.e("网络请求出错" , e);
                    }
        });
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}
