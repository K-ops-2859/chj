package com.dikai.chenghunjiclient.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.GuangGaoAdapter;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class PhotoActivity extends AppCompatActivity {
    private ArrayList<String> mImgs = new ArrayList<>();
    private ViewPager pager;
    private TextView nowPhoto;
    private TextView totalPhoto;
    private int nowPosition;

    private RelativeLayout returnLayout;
    private RelativeLayout downLoadLayout;
    private LinearLayout titleLayout;
    private boolean isShow = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        EventBus.getDefault().register(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏

        Intent intent = getIntent();
        mImgs = intent.getStringArrayListExtra("imgs");
        final int nowNum = intent.getIntExtra("now", 1);
        nowPosition = nowNum;
        String img = intent.getStringExtra("img");
        if (img != null) {
            mImgs = new ArrayList<>();
            mImgs.add(img);
        }
        Log.d("iimages=======" , mImgs.size() + "");

        pager = (ViewPager) findViewById(R.id.photo_pager);
        nowPhoto = (TextView) findViewById(R.id.now_photo);
        totalPhoto = (TextView) findViewById(R.id.total_photo);
        titleLayout = (LinearLayout) findViewById(R.id.photo_activity_title_layout);
        titleLayout.setVisibility(View.VISIBLE);
        returnLayout = (RelativeLayout) findViewById(R.id.photo_activity_return_layout);
        returnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        downLoadLayout = (RelativeLayout) findViewById(R.id.photo_activity_download_layout);
        downLoadLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downLoadPic(mImgs.get(nowPosition));
            }
        });


        totalPhoto.setText(""+mImgs.size());
        nowPhoto.setText(nowNum+1+"");
        System.out.println("photoActivity是否空====" +mImgs);
        pager.setAdapter(new GuangGaoAdapter(getSupportFragmentManager(), mImgs));
        pager.setCurrentItem(nowNum);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                nowPhoto.setText(position+1+"");
                nowPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void setTitleBar(){
        if(isShow){
            titleLayout.setVisibility(View.GONE);
            isShow = false;
        }else {
            titleLayout.setVisibility(View.VISIBLE);
            isShow = true;
        }
    }

    private void downLoadPic(final String mImgUrl){
        Glide.with(this)
                .load(mImgUrl)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        String imageName = mImgUrl.substring(mImgUrl.lastIndexOf("/")+1,mImgUrl.length())+".jpg";
                        Log.e("图片名", imageName);
                        File file = new File(Environment.getExternalStorageDirectory() + "/ChengHunJi/images");
                        File picFile = new File(file, imageName);
                        if (!file.exists()) {
                            file.mkdirs();
                        }
                        FileOutputStream fout = null;
                        if (!picFile.exists()) {
                            try {
                                //保存图片
                                fout = new FileOutputStream(picFile);
                                resource.compress(Bitmap.CompressFormat.JPEG, 30, fout);
                                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(picFile)));
                                Toast.makeText(PhotoActivity.this, "图片已下载至" + picFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                                // 将保存的地址给SubsamplingScaleImageView,这里注意设置ImageViewState
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
                        }else {
                            Toast.makeText(PhotoActivity.this, "图片已存在", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * 事件总线刷新
     * @param bean
     */
    @Subscribe
    public void onEventMainThread(final EventBusBean bean) {
        runOnUiThread(new Runnable() {
            public void run() {
                if(bean.getType() == Constants.PHOTO_TITLE_BAR){
                    setTitleBar();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
