package com.dikai.chenghunjiclient.adapter;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.activity.PhotoActivity;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lucio on 2017/6/30.
 */

public class NineGridAdapter extends NineGridImageViewAdapter<String> {

    @Override
    protected void onDisplayImage(Context context, ImageView imageView, String s) {
        Glide.with(context)
                .load(s)
                .override(250,250)
                .into(imageView);
    }

    @Override
    protected ImageView generateImageView(Context context) {
//                return super.generateImageView(context);
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }

    @Override
    protected void onItemImageClick(Context context, int index, List<String> list) {
        Intent intent = new Intent(context, PhotoActivity.class);
        intent.putExtra("now", index);
        intent.putStringArrayListExtra("imgs", new ArrayList<>(list));
        context.startActivity(intent);
    }
}
