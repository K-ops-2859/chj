package com.dikai.chenghunjiclient.activity.me;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.me.PublishPhotoAdapter;
import com.dikai.chenghunjiclient.bean.BeanAddCase;
import com.dikai.chenghunjiclient.bean.BeanEditCase;
import com.dikai.chenghunjiclient.entity.NewUserInfo;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.entity.ResultProject;
import com.dikai.chenghunjiclient.entity.UserInfo;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UpLoadImgThread;
import com.dikai.chenghunjiclient.util.UserManager;
import com.flyco.dialog.widget.base.BaseDialog;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class NewProjectActivity extends AppCompatActivity implements View.OnClickListener {

    private int type;
    private ImageView mBack;
    private TextView mPublish;
    private TextView mark;
    private EditText titleEdit;
    private EditText contentEdit;
    private RecyclerView mRecyclerView;
    private PublishPhotoAdapter mPhotoAdapter;
    private ImagePicker imagePicker;
    private List<ImageItem> mList;
    private SpotsDialog mDialog;
    private String videoPath;
    private ImageView addVideo;
//    private int photoType;
    private int editType;
    private ResultProject mProject;
    private ServiceDialog mServiceDialog;

    private static final String imageName = "cache_img.jpg";
    private File picFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project);
        EventBus.getDefault().register(this);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        editType = getIntent().getIntExtra("t",0);
        type = getIntent().getIntExtra("type",0);
        mDialog = new SpotsDialog(this,R.style.DialogCustom);
        mBack = (ImageView) findViewById(R.id.activity_new_project_back);
        addVideo = (ImageView) findViewById(R.id.activity_new_project_video);
//        facePic = (ImageView) findViewById(R.id.activity_new_project_face);
        mPublish = (TextView) findViewById(R.id.activity_new_project_publish);
        mark = (TextView) findViewById(R.id.activity_new_project_mark);
        titleEdit = (EditText) findViewById(R.id.activity_new_project_title);
        contentEdit = (EditText) findViewById(R.id.activity_new_project_content);
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_new_project_recycler);
        mList = new ArrayList<>();
        mPhotoAdapter = new PublishPhotoAdapter(this, mList, 9);
        mPhotoAdapter.setAddClickListener(new PublishPhotoAdapter.OnAddClickListener() {
            @Override
            public void onClick() {
//                photoType = 1;
                imagePicker.setMultiMode(true); // 是否为多选模式
                imagePicker.setSelectLimit(9);
                imagePicker.setCrop(false);
                openPhoto();
            }
        });

        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        mRecyclerView.setAdapter(mPhotoAdapter);
        initImagePicker();
        mServiceDialog = new ServiceDialog(this);
        mServiceDialog.widthScale(1);
        mServiceDialog.heightScale(1);
        mBack.setOnClickListener(this);
        mPublish.setOnClickListener(this);
        addVideo.setOnClickListener(this);
//        facePic.setOnClickListener(this);
        if(editType == 0){
            if(type == 0){
                mark.setText("添加图片");
                addVideo.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
            }else {
                mark.setText("添加视频");
                addVideo.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
            }
        }else {
            mProject = (ResultProject) getIntent().getSerializableExtra("project");
            titleEdit.setText(mProject.getLogTitle());
            contentEdit.setText(mProject.getLogContent());
            if(mProject.getVIDeos() == null || "".equals(mProject.getVIDeos().trim())){
                type = 0;
                mark.setText("添加图片");
                addVideo.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
            }else {
                type = 1;
                mark.setText("添加视频");
                addVideo.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v == mBack){
            onBackPressed();
//        }else if(v == facePic){
//            photoType = 0;
//            imagePicker.setMultiMode(false); // 是否为多选模式
//            imagePicker.setCrop(true);        // 允许裁剪（单选有效）
//            imagePicker.setStyle(CropImageView.Style.RECTANGLE);  // 裁剪框的形状
//            imagePicker.setFocusWidth(750);   // 裁剪框的宽度。单位像素（圆形自动取宽高最小值）
//            imagePicker.setFocusHeight(500);  // 裁剪框的高度。单位像素（圆形自动取宽高最小值）
//            imagePicker.setOutPutX(750);// 保存文件的宽度。单位像素
//            imagePicker.setOutPutY(500);// 保存文件的高度。单位像素
//            openPhoto();
        }else if(v == addVideo){
            openVideo();
        }else if(v == mPublish){
            if(titleEdit.getText() == null || "".equals(titleEdit.getText().toString().trim())){
                Toast.makeText(this, "案例名称不能为空！", Toast.LENGTH_SHORT).show();
            }else if(contentEdit.getText() == null || "".equals(contentEdit.getText().toString().trim())){
                Toast.makeText(this, "案例介绍不能为空！", Toast.LENGTH_SHORT).show();
//            }else if(faceUrl == null || "".equals(faceUrl.trim())){
//                Toast.makeText(this, "请选择封面图！", Toast.LENGTH_SHORT).show();
            }else if(type == 0 && mPhotoAdapter.getResult().size() == 0){
                Toast.makeText(this, "请选择案例图片！", Toast.LENGTH_SHORT).show();
            }else if(type == 1 && (videoPath == null || "".equals(videoPath.trim()))){
                Toast.makeText(this, "请选择视频！", Toast.LENGTH_SHORT).show();
            }else {
                mDialog.show();
                if(type == 0){
                    List<String> list = new ArrayList<>();
//                    list.add(faceUrl);
                    list.addAll(mPhotoAdapter.getResult());
                    uploadPic(list);
                }else {
                    List<String> list = new ArrayList<>();
                    list.add(picFile.getAbsolutePath());
                    uploadPic(list);
                }
            }
        }
    }

    private void uploadPic(final List<String> list){
        UpLoadImgThread.getInstance(this).upLoad(list, "http://121.42.156.151:93/FileStorage.aspx",
                UserManager.getInstance(this).getNewUserInfo().getUserId(), "2", new UpLoadImgThread.CallBackListener() {
                    @Override
                    public void onFinish(List<String> values) {
                        try {
                            if(type == 0){
                                String face = values.get(0);
                                String pics = "";
                                for (int i = 0; i < values.size(); i++) {
                                    if(i < values.size() - 1){
                                        pics = pics + values.get(i) + ",";
                                    }else{
                                        pics = pics + values.get(i);
                                    }
                                }
                                if(editType == 0){
                                    addCase(face, pics, "");
                                }else {
                                    editCase(face, pics, "");
                                }
                            }else {
                                List<String> list = new ArrayList<>();
                                list.add(videoPath);
                                uploadVideo(list, values.get(0));
                            }
                        }catch (Exception e){
                            Log.e("上传出错",e.toString());
                        }
                    }

                    @Override
                    public void onError(String e) {
                        mDialog.dismiss();
                        Toast.makeText(NewProjectActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void uploadVideo(final List<String> list, final String faceID){
        UpLoadImgThread.getInstance(this).upLoad(list, "http://121.42.156.151:93/FileStorage.aspx",
                UserManager.getInstance(this).getNewUserInfo().getUserId(), "1342", new UpLoadImgThread.CallBackListener() {
                    @Override
                    public void onFinish(List<String> values) {
                        try {
                            if(editType == 0){
                                addCase(faceID, "", values.get(0));
                            }else {
                                editCase(faceID, "", values.get(0));
                            }
                        }catch (Exception e){
                            Log.e("上传出错",e.toString());
                        }
                    }
                    @Override
                    public void onError(String e) {
                        mDialog.dismiss();
                        Toast.makeText(NewProjectActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 添加案例
     */
    private void addCase(String face, String pics, String video){
        NewUserInfo info = UserManager.getInstance(this).getNewUserInfo();
        NetWorkUtil.setCallback("HQOAApi/AddCaseInfoInfo",
                new BeanAddCase(info.getFacilitatorId(),face, titleEdit.getText().toString().trim(),contentEdit.getText().toString().trim(),
                        pics, video),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                Toast.makeText(NewProjectActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                                EventBus.getDefault().post(new EventBusBean(Constants.ADD_CASE_SUCCESS));
                                finish();
                            } else {
                                Toast.makeText(NewProjectActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Toast.makeText(NewProjectActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 修改案例
     */
    private void editCase(String face, String pics, String video){
        NewUserInfo info = UserManager.getInstance(this).getNewUserInfo();
        NetWorkUtil.setCallback("HQOAApi/UpCaseInfoInfo",
                new BeanEditCase(info.getFacilitatorId(),mProject.getCaseID(),face, titleEdit.getText().toString().trim(),contentEdit.getText().toString().trim(),
                        pics, video),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                Toast.makeText(NewProjectActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                EventBus.getDefault().post(new EventBusBean(Constants.EDIT_CASE_SUCCESS));
                                finish();
                            } else {
                                Toast.makeText(NewProjectActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Toast.makeText(NewProjectActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 打开视频
     */
    private void openVideo() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, Constants.SET_VIDEO);
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
//                if(photoType == 0){
//                    faceUrl = images.get(0).path;
//                    Glide.with(this)
//                            .load(faceUrl)
//                            .error(R.drawable.ic_default_image)
//                            .centerCrop()
//                            .into(facePic);
//                }else {
//                }
                mPhotoAdapter.refresh(images);
            } else {
                Toast.makeText(this, "图片出了些问题...", Toast.LENGTH_SHORT).show();
            }
        }else if(resultCode == RESULT_OK){
            if (data != null && requestCode == Constants.SET_VIDEO) {
                Uri vPath = data.getData();
                Log.e("路径1--",vPath.toString());
                String[] filePathColumn = { MediaStore.Video.Media.DATA };
                Cursor cursor = getContentResolver().query(vPath , filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                videoPath = cursor.getString(columnIndex);
                cursor.close();
                Log.e("路径2--",videoPath);
                getVideoThumbnail(videoPath);
            }else {
                Toast.makeText(this, "视频出了些问题...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 获取视频缩略图
     * @return
     */
    public void getVideoThumbnail(String videoPath) {
        try{
            MediaMetadataRetriever media = new MediaMetadataRetriever();
            media.setDataSource(videoPath);
            Bitmap bitmap = media.getFrameAtTime();
            saveCode(bitmap);
            addVideo.setImageBitmap(bitmap);
        }catch (Exception e){
            Log.e("获取视频缩略图出错：",e.toString());
        }
    }

    public class ServiceDialog extends BaseDialog<ServiceDialog>{

        private NumberProgressBar mProgressBar;

        public ServiceDialog(Context context) {
            super(context);
        }

        @Override
        public View onCreateView() {
            View view = View.inflate(mContext, R.layout.dialog_progress_layout, null);
            return view;
        }

        @Override
        public void onViewCreated(View view) {
            super.onViewCreated(view);
            mProgressBar = (NumberProgressBar) view.findViewById(R.id.dialog_progress);
        }

        @Override
        public void setUiBeforShow() {
            mProgressBar.setProgress(0);
        }

        public void setProgress(int progress){
            mProgressBar.setProgress(progress);
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
                if(bean.getType() == Constants.UPLOAD_VIDEO_START){
                    mServiceDialog.show();
                }else if(bean.getType() == Constants.UPLOAD_VIDEO_PROGRESS){
                    Log.e("上传进度：","%" + bean.getProgress());
                    mServiceDialog.setProgress(bean.getProgress());
                }else if(bean.getType() == Constants.UPLOAD_VIDEO_END){
                    Log.e("上传完成：","==================");
                    mServiceDialog.dismiss();
                }else if(bean.getType() == Constants.UPLOAD_VIDEO_ERROR){
                    Log.e("上传失败：","==================");
                    mServiceDialog.dismiss();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
        EventBus.getDefault().unregister(this);
    }

    //=============================

    private void saveCode(Bitmap resource){
        File file = new File(Environment.getExternalStorageDirectory() + "/ChengHunJi/cache");
        picFile = new File(file, imageName);
        if (!file.exists()) {
            file.mkdirs();
        }
        if (picFile.exists()) {
            picFile.delete();
        }
        FileOutputStream fout = null;
        try {
            //保存图片
            fout = new FileOutputStream(picFile);
            resource.compress(Bitmap.CompressFormat.JPEG, 30, fout);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e("保存图片失败:", e.toString());
        } finally {
            try {
                if (fout != null) fout.close();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("关闭IO失败:", e.toString());
            }
        }
    }
}
