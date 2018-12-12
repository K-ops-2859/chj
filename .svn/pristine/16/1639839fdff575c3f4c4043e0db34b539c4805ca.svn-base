package com.dikai.chenghunjiclient.activity.me;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.BitmapTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.me.PublishPhotoAdapter;
import com.dikai.chenghunjiclient.bean.BeanAddRoom;
import com.dikai.chenghunjiclient.bean.BeanEditRoom;
import com.dikai.chenghunjiclient.entity.NewUserInfo;
import com.dikai.chenghunjiclient.entity.ResultAddRoom;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.entity.RoomBean;
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

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import dmax.dialog.SpotsDialog;

public class AddRoomActivity extends AppCompatActivity implements View.OnClickListener {

    private int type;
    private RoomBean mRoomBean;
    private String faceUrl;
    private TextView mFinish;
    private EditText nameEdit;
    private EditText priceEdit;
    private EditText tableEdit;
    private ImageView facePic;
    private ImageView mBack;
    private RecyclerView mRecyclerView;
    private PublishPhotoAdapter mPhotoAdapter;
    private List<ImageItem> mList;
    private ImagePicker imagePicker;
    private int photoType;
    private SpotsDialog mDialog;
    private Map<Integer, Bitmap> bitmaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        mDialog = new SpotsDialog(this, R.style.DialogCustom);
        mBack = (ImageView) findViewById(R.id.add_room_back);
        mFinish = (TextView) findViewById(R.id.add_room_next);
        nameEdit = (EditText) findViewById(R.id.add_room_name);
        priceEdit = (EditText) findViewById(R.id.add_room_price);
        tableEdit = (EditText) findViewById(R.id.add_room_table);
        facePic = (ImageView) findViewById(R.id.add_room_face);
        mRecyclerView = (RecyclerView) findViewById(R.id.add_room_recycler);
        mList = new ArrayList<>();
        mPhotoAdapter = new PublishPhotoAdapter(this, mList, 9);
        mPhotoAdapter.setAddClickListener(new PublishPhotoAdapter.OnAddClickListener() {
            @Override
            public void onClick() {
                photoType = 1;
                imagePicker.setMultiMode(true); // 是否为多选模式
                imagePicker.setSelectLimit(9);
                imagePicker.setCrop(false);
                openPhoto();
            }
        });
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        mRecyclerView.setAdapter(mPhotoAdapter);
        mFinish.setOnClickListener(this);
        facePic.setOnClickListener(this);
        mBack.setOnClickListener(this);
        initImagePicker();
        type = getIntent().getIntExtra("type", 0);
        if (type == 1) {
            mRoomBean = (RoomBean) getIntent().getSerializableExtra("bean");
            nameEdit.setText(mRoomBean.getBanquetHallName());
//            priceEdit.setText(mRoomBean.getFloorPrice());
            tableEdit.setText(mRoomBean.getMaxTableCount());
        }
    }

    @Override
    public void onClick(View v) {
        if (v == mBack) {
            onBackPressed();
        } else if (v == facePic) {
            photoType = 0;
            imagePicker.setMultiMode(false); // 是否为多选模式
            imagePicker.setCrop(true);        // 允许裁剪（单选有效）
            imagePicker.setStyle(CropImageView.Style.RECTANGLE);  // 裁剪框的形状
            imagePicker.setFocusWidth(900);   // 裁剪框的宽度。单位像素（圆形自动取宽高最小值）
            imagePicker.setFocusHeight(700);  // 裁剪框的高度。单位像素（圆形自动取宽高最小值）
            imagePicker.setOutPutX(900);// 保存文件的宽度。单位像素
            imagePicker.setOutPutY(700);// 保存文件的高度。单位像素
            openPhoto();
        } else if (v == mFinish) {
            if (nameEdit.getText() == null || "".equals(nameEdit.getText().toString().trim())) {
                Toast.makeText(this, "宴会厅名不能为空！", Toast.LENGTH_SHORT).show();
            } else if (priceEdit.getText() == null || "".equals(priceEdit.getText().toString().trim())) {
                Toast.makeText(this, "最低价格不能为空！", Toast.LENGTH_SHORT).show();
            } else if (tableEdit.getText() == null || "".equals(tableEdit.getText().toString().trim())) {
                Toast.makeText(this, "容纳桌数不能为空！", Toast.LENGTH_SHORT).show();
            } else if (faceUrl == null || "".equals(faceUrl.trim())) {
                Toast.makeText(this, "请选择封面图！", Toast.LENGTH_SHORT).show();
            } else if (mPhotoAdapter.getResult().size() == 0) {
                Toast.makeText(this, "请选择宴会厅图片！", Toast.LENGTH_SHORT).show();
            } else {
                mDialog.show();
                List<String> list = new ArrayList<>();
                list.add(faceUrl);
                list.addAll(mPhotoAdapter.getResult());
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
        imagePicker = ImagePicker.getInstance();
        imagePicker.setMultiMode(true); // 是否为多选模式
        imagePicker.setSelectLimit(1); // 多选模式下限制数量，默认为9
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
                if (photoType == 0) {
                    faceUrl = images.get(0).path;
                    Glide.with(this)
                            .load(faceUrl)
                            .error(R.drawable.ic_default_image)
                            .centerCrop()
                            .into(facePic);
                } else {
                    mPhotoAdapter.refresh(images);
                }
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
                        String face = values.get(0);
                        String pics = "";
                        List<String> temp = values.subList(1, list.size());
                        for (int i = 0; i < temp.size(); i++) {
                            if (i < temp.size() - 1) {
                                pics = pics + temp.get(i) + ",";
                            } else {
                                pics = pics + temp.get(i);
                            }
                        }
                        if (type == 0) {
                            addRoom(face, pics);
                        } else {
                            editRoom(face, pics);
                        }
                    }

                    @Override
                    public void onError(String e) {
                        mDialog.dismiss();
                        Toast.makeText(AddRoomActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 添加信息
     */
    private void addRoom(String face, String pics) {
//        NewUserInfo info = UserManager.getInstance(this).getNewUserInfo();
//        NetWorkUtil.setCallback("HQOAApi/AddBanquetHallInfo",
//                new BeanAddRoom(nameEdit.getText().toString().trim(), priceEdit.getText().toString().trim(), tableEdit.getText().toString().trim(),
//                        face, pics, info.getFacilitatorId()),
//                new NetWorkUtil.CallBackListener() {
//                    @Override
//                    public void onFinish(final String respose) {
//                        mDialog.dismiss();
//                        Log.e("返回值", respose);
//                        try {
//                            ResultAddRoom result = new Gson().fromJson(respose, ResultAddRoom.class);
//                            if ("200".equals(result.getMessage().getCode())) {
//                                Toast.makeText(AddRoomActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
//                                EventBus.getDefault().post(new EventBusBean(Constants.ADD_ROOM_SUCCESS));
//                                finish();
//                            } else {
//                                Toast.makeText(AddRoomActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (Exception e) {
//                            Log.e("json解析出错", e.toString());
//                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onError(final String e) {
//                        mDialog.dismiss();
//                        Toast.makeText(AddRoomActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
//                    }
//                });
    }

    /**
     * 修改信息
     */
    private void editRoom(String face, String pics) {
//        NewUserInfo info = UserManager.getInstance(this).getNewUserInfo();
//        NetWorkUtil.setCallback("HQOAApi/UpBanquetHallInfo",
//                new BeanEditRoom(mRoomBean.getBanquetID(), info.getFacilitatorId(), nameEdit.getText().toString().trim(),
//                        priceEdit.getText().toString().trim(), tableEdit.getText().toString().trim(), face, pics),
//                new NetWorkUtil.CallBackListener() {
//                    @Override
//                    public void onFinish(final String respose) {
//                        mDialog.dismiss();
//                        Log.e("返回值", respose);
//                        try {
//                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
//                            if ("200".equals(result.getMessage().getCode())) {
//                                Toast.makeText(AddRoomActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
//                                EventBus.getDefault().post(new EventBusBean(Constants.ADD_ROOM_SUCCESS));
//                                finish();
//                            } else {
//                                Toast.makeText(AddRoomActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (Exception e) {
//                            Log.e("json解析出错", e.toString());
//                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onError(final String e) {
//                        mDialog.dismiss();
//                        Toast.makeText(AddRoomActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
//                    }
//                });
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}
