package com.dikai.chenghunjiclient.activity.me;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.me.AddImgAdapter;
import com.dikai.chenghunjiclient.bean.BeanAddRoom;
import com.dikai.chenghunjiclient.bean.BeanEditRoom;
import com.dikai.chenghunjiclient.bean.BeanID;
import com.dikai.chenghunjiclient.entity.NewUserInfo;
import com.dikai.chenghunjiclient.entity.ResultGetRoomInfo;
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
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class AddNewRoomActivity extends AppCompatActivity implements View.OnClickListener {
    private SpotsDialog mDialog;
    private RecyclerView mRecyclerView;
    private AddImgAdapter mImgAdapter;
    private ImagePicker imagePicker;
    private EditText nameEdit;
    private EditText areaEdit;
    private EditText lengthEdit;
    private EditText widthEdit;
    private EditText heightEdit;
    private EditText minNumEdit;
    private EditText maxNumEdit;
//    private String roomID;
//    private int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_room);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
//        type = getIntent().getIntExtra("type",0);//0 添加 1 修改
//        roomID = getIntent().getStringExtra("roomid");
        mDialog = new SpotsDialog(this,R.style.DialogCustom);
        findViewById(R.id.cancel).setOnClickListener(this);
        findViewById(R.id.save).setOnClickListener(this);
        nameEdit = (EditText) findViewById(R.id.name);
        areaEdit = (EditText) findViewById(R.id.area);
        lengthEdit = (EditText) findViewById(R.id.length);
        widthEdit = (EditText) findViewById(R.id.width);
        heightEdit = (EditText) findViewById(R.id.height);
        minNumEdit = (EditText) findViewById(R.id.min_num);
        maxNumEdit = (EditText) findViewById(R.id.max_num);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL));
        mRecyclerView.setVerticalScrollBarEnabled(true);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setNestedScrollingEnabled(false);
        mImgAdapter = new AddImgAdapter(this,50);
        mImgAdapter.setAddClickListener(new AddImgAdapter.OnAddClickListener() {
            @Override
            public void onClick() {
                imagePicker.setSelectLimit(mImgAdapter.getMaxPhotoNum());
                openPhoto();
            }
        });
        mRecyclerView.setAdapter(mImgAdapter);
        initImagePicker();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                onBackPressed();
                break;
            case R.id.save:
                if(nameEdit.getText() == null || "".equals(nameEdit.getText().toString().trim())){
                    Toast.makeText(this, "请输入宴会厅名称", Toast.LENGTH_SHORT).show();
                }else if(areaEdit.getText() == null || "".equals(areaEdit.getText().toString().trim())){
                    Toast.makeText(this, "请输入宴会厅面积", Toast.LENGTH_SHORT).show();
                }else if(lengthEdit.getText() == null || "".equals(lengthEdit.getText().toString().trim())){
                    Toast.makeText(this, "请输入宴会厅长度", Toast.LENGTH_SHORT).show();
                }else if(widthEdit.getText() == null || "".equals(widthEdit.getText().toString().trim())){
                    Toast.makeText(this, "请输入宴会厅宽度", Toast.LENGTH_SHORT).show();
                }else if(heightEdit.getText() == null || "".equals(heightEdit.getText().toString().trim())){
                    Toast.makeText(this, "请输入宴会厅层高", Toast.LENGTH_SHORT).show();
                }else if(minNumEdit.getText() == null || "".equals(minNumEdit.getText().toString().trim())){
                    Toast.makeText(this, "请输入可容纳最小桌数", Toast.LENGTH_SHORT).show();
                }else if(maxNumEdit.getText() == null || "".equals(maxNumEdit.getText().toString().trim())){
                    Toast.makeText(this, "请输入可容纳最大桌数", Toast.LENGTH_SHORT).show();
                }else {
                    mDialog.show();
                    if(mImgAdapter.getNewPaths().size() > 0){
                        upload(mImgAdapter.getNewPaths());
                    }else {
                        String pics = "";
                        if(mImgAdapter.getOldIds().size() > 0){
                            for (int i = 0; i < mImgAdapter.getOldIds().size(); i++) {
                                if (i < mImgAdapter.getOldIds().size() - 1) {
                                    pics = pics + mImgAdapter.getOldIds().get(i) + ",";
                                } else {
                                    pics = pics + mImgAdapter.getOldIds().get(i);
                                }
                            }
                            addRoom(nameEdit.getText().toString(),maxNumEdit.getText().toString(),minNumEdit.getText().toString(), pics,
                                    areaEdit.getText().toString(),lengthEdit.getText().toString(),widthEdit.getText().toString(),heightEdit.getText().toString());
//                            if(type == 0){
//                            }else {
//                                editRoom(nameEdit.getText().toString(),maxNumEdit.getText().toString(),minNumEdit.getText().toString(), pics,
//                                        areaEdit.getText().toString(),lengthEdit.getText().toString(),widthEdit.getText().toString(),heightEdit.getText().toString());
//                            }
                        }else {
                            mDialog.dismiss();
                            Toast.makeText(this, "请至少上传一张图片", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
        }
    }
    private void setData(ResultGetRoomInfo result) {
        nameEdit.setText(result.getBanquetHallName());
        areaEdit.setText(result.getAcreage());
        lengthEdit.setText(result.getLength());
        widthEdit.setText(result.getWidth());
        heightEdit.setText(result.getHeight());
        minNumEdit.setText(result.getMinTableCount());
        maxNumEdit.setText(result.getMaxTableCount());
        mImgAdapter.addOld(result.getData());
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
                List<String> temp = new ArrayList<>();
                for (ImageItem item : images) {
                    temp.add(item.path);
                }
                mImgAdapter.addNew(temp);
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
                        if(mImgAdapter.getOldIds().size() > 0){
                            for (int i = 0; i < mImgAdapter.getOldIds().size(); i++) {
                                pics = pics + mImgAdapter.getOldIds().get(i) + ",";
                            }
                        }
                        for (int i = 0; i < values.size(); i++) {
                            if (i < values.size() - 1) {
                                pics = pics + values.get(i) + ",";
                            } else {
                                pics = pics + values.get(i);
                            }
                        }
                        addRoom(nameEdit.getText().toString(),maxNumEdit.getText().toString(),minNumEdit.getText().toString(), pics,
                                areaEdit.getText().toString(),lengthEdit.getText().toString(),widthEdit.getText().toString(),heightEdit.getText().toString());
//                        if(type == 0){
//                        }else {
//                            editRoom(nameEdit.getText().toString(),maxNumEdit.getText().toString(),minNumEdit.getText().toString(), pics,
//                                    areaEdit.getText().toString(),lengthEdit.getText().toString(),widthEdit.getText().toString(),heightEdit.getText().toString());
//                        }
                    }

                    @Override
                    public void onError(String e) {
                        mDialog.dismiss();
                        Toast.makeText(AddNewRoomActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    /**
     * 添加宴会厅
     */
    private void addRoom(String name, String max, String min, String pics, String area, String length,String width,String height){
        NewUserInfo info = UserManager.getInstance(this).getNewUserInfo();
        NetWorkUtil.setCallback("HQOAApi/AddBanquetHallInfo",
                new BeanAddRoom(name,"0",max,min,pics,info.getFacilitatorId(),area,length,width,height),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                Toast.makeText(AddNewRoomActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                                EventBus.getDefault().post(new EventBusBean(Constants.ADD_NEW_ROOM));
                                finish();
                            } else {
                                Toast.makeText(AddNewRoomActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Toast.makeText(AddNewRoomActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

//    /**
//     * 修改宴会厅
//     */
//    private void editRoom(String name, String max, String min, String pics, String area, String length,String width,String height){
//        NewUserInfo info = UserManager.getInstance(this).getNewUserInfo();
//        NetWorkUtil.setCallback("HQOAApi/UpBanquetHallInfo",
//                new BeanEditRoom(name,"0",max,min,pics,info.getFacilitatorId(),roomID,area,length,width,height),
//                new NetWorkUtil.CallBackListener() {
//                    @Override
//                    public void onFinish(final String respose) {
//                        mDialog.dismiss();
//                        Log.e("返回值",respose);
//                        try {
//                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
//                            if ("200".equals(result.getMessage().getCode())) {
//                                Toast.makeText(AddNewRoomActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
//                                EventBus.getDefault().post(new EventBusBean(Constants.ADD_NEW_ROOM));
//                                finish();
//                            } else {
//                                Toast.makeText(AddNewRoomActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
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
//                        Toast.makeText(AddNewRoomActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }

//    /**
//     * 获取信息
//     */
//    private void getInfo(String id){
//        NetWorkUtil.setCallback("HQOAApi/GetHotelBanquetlInfo",
//                new BeanID(id),
//                new NetWorkUtil.CallBackListener() {
//                    @Override
//                    public void onFinish(final String respose) {
//                        Log.e("返回值",respose);
//                        try {
//                            ResultGetRoomInfo result = new Gson().fromJson(respose, ResultGetRoomInfo.class);
//                            if ("200".equals(result.getMessage().getCode())) {
//                                setData(result);
//                            } else {
//                                Toast.makeText(AddNewRoomActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (Exception e) {
//                            Log.e("json解析出错",e.toString());
//                            //Toast.makeText(CorpInfoActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onError(final String e) {
//                        Toast.makeText(AddNewRoomActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }

    @Override
    public void onDestroy() {
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}
