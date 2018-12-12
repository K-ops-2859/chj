package com.dikai.chenghunjiclient.fragment;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.PhotoActivity;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PhotoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhotoFragment extends Fragment {


//    private TouchImageView mPhotoView;
    private SubsamplingScaleImageView mPhotoView;
    private String mImgUrl;
    private Dialog mExitDialog;


    public PhotoFragment() {
        // Required empty public constructor
    }

    public static PhotoFragment newInstance(String param1) {
        PhotoFragment fragment = new PhotoFragment();
        Bundle args = new Bundle();
        args.putString("url", param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_photo, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mImgUrl = getArguments().getString("url");
//        http://121.42.156.151:92/FileGain.aspx?fi=04db4a9a-b97f-4f0e-b8a6-609f18aedf60&it=0
        try {
            if (mImgUrl.contains("&it=")) {
                mImgUrl = mImgUrl.substring(0, mImgUrl.length() - 1) + "3";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mPhotoView = (SubsamplingScaleImageView) view.findViewById(R.id.fragment_image);
        mPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new EventBusBean(Constants.PHOTO_TITLE_BAR));
            }
        });
        loadImg();
    }
    private void loadImg(){
        Glide.with(this)
                .load(mImgUrl)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        if(resource.getHeight() < 4096 && resource.getWidth() < 4096){
                            mPhotoView.setImage(ImageSource.bitmap(resource));
                        }else {
                            String imageName = mImgUrl.substring(mImgUrl.lastIndexOf("/")+1,mImgUrl.length());
                            Log.e("图片名", imageName);
                            File file = new File(Environment.getExternalStorageDirectory() + "/ChengHunJi/cache");
                            File picFile = new File(file, imageName);
                            if (!file.exists()) {
                                file.mkdirs();
                            }
                            FileOutputStream fout = null;
                            if (!picFile.exists()) {
                                try {
                                    //保存图片
                                    fout = new FileOutputStream(picFile);
                                    resource.compress(Bitmap.CompressFormat.JPEG, 50, fout);
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
                            }
                            mPhotoView.setImage(ImageSource.uri(picFile.getAbsolutePath()));
                        }
                    }
                });
    }
}
