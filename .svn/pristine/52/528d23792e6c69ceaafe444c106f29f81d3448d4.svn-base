package com.dikai.chenghunjiclient.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.LayoutRes;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.awen.photo.photopick.ui.PhotoPagerActivity;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.bean.BeanDeleteUploadedFile;
import com.dikai.chenghunjiclient.bean.BeanPhotoDis;
import com.dikai.chenghunjiclient.dialog.PhotoDialog;
import com.dikai.chenghunjiclient.entity.PhotoDetailsData;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by cmk03 on 2018/3/23.
 */

public class PhotoPickActivity extends PhotoPagerActivity implements View.OnClickListener {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};
    private String id;
    private PhotoDialog photoDialog;
    private LinearLayout llFail;
    private LinearLayout llDis;
    private TextView tvFailDesc;
    private TextView tvTitle;
    private int pagerCount;
    private ArrayList<PhotoDetailsData.DataList> photoList;
    private String fileUrl;
    private Handler handler;

    @Override
    protected void setCustomView(@LayoutRes int layoutId) {
        verifyStoragePermissions(this);
        super.setCustomView(R.layout.activity_lookphoto);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.black_1)
                .statusBarDarkFont(true, 0.2f)
                .init();
        Toolbar mToolBar = (Toolbar) findViewById(R.id.toolbar);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        llFail = (LinearLayout) findViewById(R.id.ll_fail);
        LinearLayout llBottom = (LinearLayout) findViewById(R.id.ll_bottom);
        tvFailDesc = (TextView) findViewById(R.id.tv_fail_desc);
        LinearLayout llButton = (LinearLayout) findViewById(R.id.ll_button);
        LinearLayout llDel = (LinearLayout) findViewById(R.id.ll_del);
        llDis = (LinearLayout) findViewById(R.id.ll_dis);
        LinearLayout llDow = (LinearLayout) findViewById(R.id.ll_dow);
        photoDialog = new PhotoDialog(this);
        Bundle bundle = getBundle();
        photoList = bundle.getParcelableArrayList("dataList");
        int position = bundle.getInt("position");
        pagerCount = bundle.getInt("photoCount");
        tvTitle.setText((position + 1) + "/" + pagerCount);
        id = String.valueOf(photoList.get(position).getId());
        fileUrl = photoList.get(position).getFileUrl();
        if (photoList.get(position).getStatus() == 2) {
            llDis.setVisibility(View.GONE);
            llFail.setVisibility(View.VISIBLE);
            tvFailDesc.setVisibility(View.VISIBLE);
            tvFailDesc.setText(photoList.get(position).getReason());
        } else {
            llDis.setVisibility(View.VISIBLE);
            llFail.setVisibility(View.GONE);
            tvFailDesc.setVisibility(View.GONE);
        }
        llDel.setOnClickListener(this);
        llDis.setOnClickListener(this);
        llDow.setOnClickListener(this);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    Bitmap bitmap = (Bitmap) msg.getData().getParcelable("bmp");
                    saveBmp2Gallery(bitmap, id);
                }
            }
        };
    }

    @Override
    public void onPageSelected(int position) {
        super.onPageSelected(position);
        tvTitle.setText((position + 1) + "/" + pagerCount);
        if (photoList.get(position).getStatus() == 2) {
            llDis.setVisibility(View.GONE);
            llFail.setVisibility(View.VISIBLE);
            tvFailDesc.setVisibility(View.VISIBLE);
            tvFailDesc.setText(photoList.get(position).getReason());
        } else {
            llDis.setVisibility(View.VISIBLE);
            llFail.setVisibility(View.GONE);
            tvFailDesc.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_del:
                showNormalDialog(id);
                break;
            case R.id.ll_dis:
                photoDialog.show();
                photoDialog.setNoOnclickListener(new PhotoDialog.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        photoDialog.dismiss();
                    }
                });
                photoDialog.setYesOnclickListener(new PhotoDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick(String desc) {
                        dis(id, desc);
                    }
                });
                break;
            case R.id.ll_dow:
                returnBitMap(fileUrl);
                break;
            default:
                break;
        }
    }

    private void showNormalDialog(final String id){
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(this);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("是否删除该图片");
        normalDialog.setPositiveButton("是",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       remove(id);
                       dialog.dismiss();
                    }
                });
        normalDialog.setNegativeButton("否",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        // 显示
        normalDialog.show();
    }

    private void remove(String id) {
        System.out.println("id================“ " + id);
        NetWorkUtil.setCallback("User/DeleteUploadedFile", new BeanDeleteUploadedFile(id), new NetWorkUtil.CallBackListener() {
            @Override
            public void onFinish(String respose) {
                ResultMessage data = new Gson().fromJson(respose, ResultMessage.class);
                if (data.getMessage().getCode().equals("200")) {
                    EventBus.getDefault().post(new EventBusBean(200));
                    finish();
                }
            }

            @Override
            public void onError(String e) {
                llDis.setVisibility(View.GONE);
            }
        });
    }

    private void dis(String id, final String editDesc) {
        NetWorkUtil.setCallback("Corp/RejectFile", new BeanPhotoDis(id, editDesc), new NetWorkUtil.CallBackListener() {
            @Override
            public void onFinish(String respose) {
                ResultMessage message = new Gson().fromJson(respose, ResultMessage.class);
                if (message.getMessage().getCode().equals("200")) {
                    llFail.setVisibility(View.VISIBLE);
                    tvFailDesc.setText(editDesc);
                    tvFailDesc.setVisibility(View.VISIBLE);
                    EventBus.getDefault().post(new EventBusBean(200));
                    photoDialog.dismiss();
                }
            }

            @Override
            public void onError(String e) {

            }
        });
    }

    public static void verifyStoragePermissions(Activity activity) {

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void returnBitMap(final String url){

        new Thread(new Runnable() {

            private Bundle bundle;
            private Message message;

            @Override
            public void run() {
                URL imageurl = null;

                try {
                    imageurl = new URL(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    HttpURLConnection conn = (HttpURLConnection)imageurl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    message = new Message();
                    bundle = new Bundle();
                    bundle.putParcelable("bmp", bitmap);
                    message.setData(bundle);
                    message.what = 1;
                    handler.sendMessage(message);
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public  void saveBmp2Gallery(Bitmap bmp, String picName) {

        String fileName = null;
        //系统相册目录
        String galleryPath= Environment.getExternalStorageDirectory()
                + File.separator + Environment.DIRECTORY_DCIM
                +File.separator+"Camera"+File.separator;


        // 声明文件对象
        File file = null;
        // 声明输出流
        FileOutputStream outStream = null;

        try {
            // 如果有目标文件，直接获得文件对象，否则创建一个以filename为名称的文件
            file = new File(galleryPath, picName+ ".jpg");

            // 获得文件相对路径
            fileName = file.toString();
            // 获得输出流，如果文件中有内容，追加内容
            outStream = new FileOutputStream(fileName);
            if (null != outStream) {
                bmp.compress(Bitmap.CompressFormat.JPEG, 90, outStream);
            }

        } catch (Exception e) {
            e.getStackTrace();
        }finally {
            try {
                if (outStream != null) {
                    outStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//通知相册更新
    MediaStore.Images.Media.insertImage(getContentResolver(),
                bmp, fileName, null);
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        PhotoPickActivity.this.sendBroadcast(intent);

        Toast.makeText(this, "图片保存成功", Toast.LENGTH_SHORT).show();

    }

}
