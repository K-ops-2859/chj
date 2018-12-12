package com.dikai.chenghunjiclient.activity.wedding;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.me.PublishPhotoAdapter;
import com.dikai.chenghunjiclient.bean.BeanEditWeInfo;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.entity.WeProBean;
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

public class WedHotelActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText hotelName;
    private EditText hotelAddress;
    private EditText hotelTable;
    private EditText roomName;
    private EditText roomLength;
    private EditText roomWidth;
    private EditText roomHeight;
    private SpotsDialog mDialog;

    private RecyclerView mRecyclerView;
    private PublishPhotoAdapter mPhotoAdapter;
    private ImagePicker imagePicker;
    private List<ImageItem> mList;
    private WeProBean mWeProBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wed_hotel);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        mWeProBean = (WeProBean) getIntent().getSerializableExtra("info");
        mDialog = new SpotsDialog(this,R.style.DialogCustom);
        hotelName = (EditText) findViewById(R.id.edit_name);
        hotelAddress = (EditText) findViewById(R.id.edit_address);
        hotelTable = (EditText) findViewById(R.id.edit_table);
        roomName = (EditText) findViewById(R.id.edit_room);
        roomLength = (EditText) findViewById(R.id.edit_length);
        roomWidth = (EditText) findViewById(R.id.edit_width);
        roomHeight = (EditText) findViewById(R.id.edit_height);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.save).setOnClickListener(this);
        mList = new ArrayList<>();
        mPhotoAdapter = new PublishPhotoAdapter(this, mList, 9);
        mPhotoAdapter.setAddClickListener(new PublishPhotoAdapter.OnAddClickListener() {
            @Override
            public void onClick() {
                imagePicker.setMultiMode(true); // 是否为多选模式
                imagePicker.setSelectLimit(9);
                imagePicker.setCrop(false);
                openPhoto();
            }
        });
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        mRecyclerView.setAdapter(mPhotoAdapter);
        initImagePicker();
        setData();
    }

    private void setData() {
        if(mWeProBean.getHotelName() != null && !"".equals(mWeProBean.getHotelName())){
            String[] xl = mWeProBean.getRummeryXls().split(",");
            hotelName.setText(mWeProBean.getHotelName());
            hotelAddress.setText(mWeProBean.getHotelAddress());
            hotelTable.setText(mWeProBean.getTableCount());
            roomName.setText(mWeProBean.getHallName());
            roomLength.setText(xl[0]);
            roomWidth.setText(xl[1]);
            roomHeight.setText(xl[2]);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.save:
                if(hotelName.getText() == null || "".equals(hotelName.getText().toString().trim())){
                    Toast.makeText(this, "酒店名称不能为空！", Toast.LENGTH_SHORT).show();
                }else if(hotelAddress.getText() == null || "".equals(hotelAddress.getText().toString().trim())){
                    Toast.makeText(this, "酒店地址不能为空！", Toast.LENGTH_SHORT).show();
                }else if(hotelTable.getText() == null || "".equals(hotelTable.getText().toString().trim())){
                    Toast.makeText(this, "酒店桌数不能为空！", Toast.LENGTH_SHORT).show();
                }else if(roomName.getText() == null || "".equals(roomName.getText().toString().trim())){
                    Toast.makeText(this, "宴会厅名称不能为空！", Toast.LENGTH_SHORT).show();
                }else if(roomLength.getText() == null || "".equals(roomLength.getText().toString().trim())){
                    Toast.makeText(this, "宴会厅长不能为空！", Toast.LENGTH_SHORT).show();
                }else if(roomWidth.getText() == null || "".equals(roomWidth.getText().toString().trim())){
                    Toast.makeText(this, "宴会厅宽不能为空！", Toast.LENGTH_SHORT).show();
                }else if(roomHeight.getText() == null || "".equals(roomHeight.getText().toString().trim())){
                    Toast.makeText(this, "宴会厅高不能为空！", Toast.LENGTH_SHORT).show();
                }else if(mPhotoAdapter.getResult().size() == 0){
                    Toast.makeText(this, "请选择至少一张宴会厅图片！", Toast.LENGTH_SHORT).show();
                }else {
                    mDialog.show();
                        List<String> list = new ArrayList<>();
                        list.addAll(mPhotoAdapter.getResult());
                        uploadPic(list);
                }
                break;
        }
    }

    private void uploadPic(final List<String> list){
        UpLoadImgThread.getInstance(this).upLoad(list, "http://121.42.156.151:93/FileStorage.aspx",
                UserManager.getInstance(this).getNewUserInfo().getUserId(), "2", new UpLoadImgThread.CallBackListener() {
                    @Override
                    public void onFinish(List<String> values) {
                        String pics = "";
                        for (int i = 0; i < values.size(); i++) {
                            if(i < values.size() - 1){
                                pics = pics + values.get(i) + ",";
                            }else{
                                pics = pics + values.get(i);
                            }
                        }
                        edit(hotelName.getText().toString().trim(),hotelAddress.getText().toString().trim(),
                                roomName.getText().toString().trim(),hotelTable.getText().toString().trim(),pics,
                                roomLength.getText().toString().trim()+","+roomWidth.getText().toString().trim()+","+
                                        roomHeight.getText().toString().trim());
                    }
                    @Override
                    public void onError(String e) {
                        mDialog.dismiss();
                        Toast.makeText(WedHotelActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void edit(String name,String address, String room, String table, String imgs, String xl) {
        NetWorkUtil.setCallback("HQOAApi/UpNewPeoplePublic",
                new BeanEditWeInfo(mWeProBean.getNewPeopleCustomID(),"1","0","",name,address,room,table,imgs,xl,"2","",""),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                EventBus.getDefault().post(new EventBusBean(Constants.WE_INFO_CHANGED));
                                finish();
                            } else {
                                Toast.makeText(WedHotelActivity.this, "" + result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Log.e("网络出错",e.toString());
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == Constants.SET_LOGO) {
                ArrayList<ImageItem> images = (ArrayList) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                mPhotoAdapter.refresh(images);
            } else {
                Toast.makeText(this, "图片出了些问题...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
}
