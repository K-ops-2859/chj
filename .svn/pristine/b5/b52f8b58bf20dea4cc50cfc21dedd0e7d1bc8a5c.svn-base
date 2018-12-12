package com.dikai.chenghunjiclient.activity.discover;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dikai.chenghunjiclient.R;

public class PublishVideoActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout containLayout;
    private ImageView closeImg;
    private TranslateAnimation mShowAnimation;
    private TranslateAnimation mHideAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //全屏
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_publish_video);
        init();
    }

    private void init() {
        containLayout = (LinearLayout) findViewById(R.id.contain_layout);
        closeImg = (ImageView) findViewById(R.id.close);
        findViewById(R.id.video).setOnClickListener(this);
        findViewById(R.id.picture).setOnClickListener(this);
        closeImg.setOnClickListener(this);
        mShowAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAnimation.setDuration(300);
        mHideAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f);
        mHideAnimation.setDuration(300);
        mHideAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                finish();
                overridePendingTransition(0,0);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        see();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.video:
                startActivity(new Intent(this, UpLoadVideoActivity.class));
                finish();
                break;
            case R.id.picture:
                startActivity(new Intent(this, PublishDynamicActivity.class));
                finish();
                break;
            case R.id.close:
                unsee();
                break;
        }
    }

    private void see(){
        containLayout.clearAnimation();
        containLayout.setVisibility(View.VISIBLE);
        containLayout.startAnimation(mShowAnimation);
    }

    private void unsee(){
        containLayout.clearAnimation();
        containLayout.setVisibility(View.GONE);
        containLayout.startAnimation(mHideAnimation);
    }


    @Override
    public void onBackPressed() {
        unsee();
    }
}
