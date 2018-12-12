package com.dikai.chenghunjiclient.activity.discover;

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
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.me.NewProjectActivity;
import com.dikai.chenghunjiclient.bean.PublishDynamicBean;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UpLoadImgThread;
import com.dikai.chenghunjiclient.util.UserManager;
import com.flyco.dialog.widget.base.BaseDialog;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UpLoadVideoActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEditText;
    private ImageView addVideo;
    private ServiceDialog mServiceDialog;
    private String videoPath;
    private String imageName;
    private File picFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_load_video);
        EventBus.getDefault().register(this);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        mEditText = (EditText) findViewById(R.id.content);
        addVideo = (ImageView) findViewById(R.id.video_img);
        mServiceDialog = new ServiceDialog(this);
        mServiceDialog.widthScale(1);
        mServiceDialog.heightScale(1);
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.publish).setOnClickListener(this);
        addVideo.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.publish:
                if(videoPath == null || "".equals(videoPath)){
                    Toast.makeText(this, "添加几张照片再发布吧", Toast.LENGTH_SHORT).show();
                }else {
                    List<String> path = new ArrayList<>();
                    path.add(picFile.getAbsolutePath());
                    uploadPic(path);
                }
                break;
            case R.id.video_img:
                openVideo();
                break;
        }
    }

    private void uploadPic(final List<String> list){
        UpLoadImgThread.getInstance(this).upLoad(list, "http://121.42.156.151:93/FileStorage.aspx",
                UserManager.getInstance(this).getNewUserInfo().getUserId(), "2", new UpLoadImgThread.CallBackListener() {
                    @Override
                    public void onFinish(List<String> values) {
                        List<String> list = new ArrayList<>();
                        list.add(videoPath);
                        uploadVideo(list, values.get(0));
                    }

                    @Override
                    public void onError(String e) {
                        Toast.makeText(UpLoadVideoActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void uploadVideo(final List<String> list, final String faceID){
        UpLoadImgThread.getInstance(this).upLoad(list, "http://121.42.156.151:93/FileStorage.aspx",
                UserManager.getInstance(this).getNewUserInfo().getUserId(), "1342", new UpLoadImgThread.CallBackListener() {
                    @Override
                    public void onFinish(List<String> values) {
                        String content = (mEditText.getText() == null ||
                                "".equals(mEditText.getText().toString().trim()))?"":mEditText.getText().toString();
                        publish(content,faceID,values.get(0));
                    }
                    @Override
                    public void onError(String e) {
                        Toast.makeText(UpLoadVideoActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void publish(String content, String picId, String videoID) {
        NetWorkUtil.setCallback("HQOAApi/AddDynamic",
                new PublishDynamicBean(2, UserManager.getInstance(this).getNewUserInfo().getUserId(), "", content, videoID, picId),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(String respose) {
                        ResultMessage resultMessage = new Gson().fromJson(respose, ResultMessage.class);
                        if (resultMessage.getMessage().getCode().equals("200")) {
                            EventBus.getDefault().post(new EventBusBean(Constants.DYNAMIC_PUBLISHED));
                            finish();
                        } else {
                            Toast.makeText(UpLoadVideoActivity.this, resultMessage.getMessage().getInform(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError(String e) {
                        Log.e("错误===", e);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
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
//            Glide.with(this).load(picFile).into(addVideo);
        }catch (Exception e){
            Log.e("获取视频缩略图出错：",e.toString());
        }
    }

    private void saveCode(Bitmap resource){
        imageName = "cache_img.jpg";
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

    public class ServiceDialog extends BaseDialog<NewProjectActivity.ServiceDialog> {

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
}
