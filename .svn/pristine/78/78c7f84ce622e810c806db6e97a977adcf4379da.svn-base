package com.dikai.chenghunjiclient.util;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.lzy.imagepicker.loader.ImageLoader;

import java.io.File;

/**
 * Created by Lucio on 2017/9/6.
 */

public class GlideImageLoader implements ImageLoader {

    private static GlideImageLoader mGlideImageLoader;

    private GlideImageLoader() {
    }

    public static GlideImageLoader getInstance(){
        if(mGlideImageLoader == null){
            mGlideImageLoader = new GlideImageLoader();
        }
        return mGlideImageLoader;
    }

    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        Glide.with(activity)//
                .load(Uri.fromFile(new File(path)))//
                .placeholder(R.drawable.ic_default_image)//
                .error(R.drawable.ic_default_image)//
                .into(imageView);
    }

    @Override
    public void displayImagePreview(Activity activity, String path, ImageView imageView, int width, int height) {
        Glide.with(activity)//
                .load(Uri.fromFile(new File(path)))//
                .placeholder(R.drawable.ic_default_image)//
                .error(R.drawable.ic_default_image)//
                .into(imageView);
    }

    @Override
    public void clearMemoryCache() {

    }
}