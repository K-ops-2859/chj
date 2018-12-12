package com.dikai.chenghunjiclient;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.util.UserDBManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeadActivity extends AppCompatActivity implements View.OnClickListener {

    private ConvenientBanner mAdBanner;
    private CBViewHolderCreator mAdView;
    private TextView start;
    private AlphaAnimation mShowAnimation;
    private AlphaAnimation mHideAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //全屏
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lead);
        start = (TextView) findViewById(R.id.start);
        mAdBanner = (ConvenientBanner) findViewById(R.id.fragment_head_ad);
        mAdView = new CBViewHolderCreator<AdBannerHolderView>() {
            @Override
            public AdBannerHolderView createHolder() {
                return new AdBannerHolderView();
            }
        };
        start.setOnClickListener(this);
        mAdBanner.setPointViewVisible(true)//设置指示器是否可见
                .setPageIndicator(new int[]{R.drawable.circle_lead_unslected, R.drawable.circle_lead_slected})   //设置指示器圆点
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL) //设置指示器位置（左、中、右)
                .setManualPageable(true);//设置手动影响（设置可否手动切换）
        mAdBanner.setCanLoop(false);
        mAdBanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageSelected(int position) {
                if(position == 4 && start.getVisibility() == View.INVISIBLE){
                    see();
                }else if(start.getVisibility() == View.VISIBLE){
                    unsee();
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });
        List<Integer> pics = new ArrayList<>();
        Collections.addAll(pics,R.drawable.new_lead_1,R.drawable.new_lead_2,
                R.drawable.new_lead_3,R.drawable.new_lead_5,R.drawable.new_lead_4);
        mAdBanner.setPages(mAdView, pics);
        mShowAnimation = new AlphaAnimation(0.0f, 1.0f);
        mShowAnimation.setDuration(1000);
        mShowAnimation.setFillAfter(true);
        mHideAnimation = new AlphaAnimation(1.0f, 0.0f);
        mHideAnimation.setDuration(1000);
        mHideAnimation.setFillAfter(true);
        start.setVisibility(View.INVISIBLE);
        start.setClickable(false);
    }

    @Override
    public void onClick(View v) {
        if(v == start){
            UserDBManager.getInstance(this).updateFirstUse();
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }

    }

    private void see(){
        start.setClickable(true);
        start.startAnimation(mShowAnimation);
        start.setVisibility(View.VISIBLE);
    }

    private void unsee(){
        start.setClickable(false);
        start.setVisibility(View.INVISIBLE);
        start.startAnimation(mHideAnimation);
    }

    /**
     * 广告HolderView
     */
    public class AdBannerHolderView implements Holder<Integer> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, Integer data) {
            Glide.with(context).load(data).
                    placeholder(R.color.gray_background).into(imageView);
        }
    }

}