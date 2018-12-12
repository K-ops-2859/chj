package com.dikai.chenghunjiclient.activity.wedding;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.bean.BeanActivityInfo;
import com.dikai.chenghunjiclient.entity.PrizeDetailsData;
import com.dikai.chenghunjiclient.util.DensityUtil;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;

/**
 * Created by cmk03 on 2017/11/15.
 */

public class PrizeDetailsActivity extends AppCompatActivity implements OnItemClickListener {

    private CBViewHolderCreator mCBView;
    private TextView tvCommodityName;
    private TextView tvPrice;
    private TextView tvWord;
    private TextView tvContent;
    private CollapsingToolbarLayout toolbarLayout;
    private ConvenientBanner mBanner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prize);

        toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        mBanner = (ConvenientBanner) findViewById(R.id.info_convenientBanner);
        Toolbar mToolBar = (Toolbar) findViewById(R.id.toolbar);
        ImmersionBar.with(this).titleBar(mToolBar).init();
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AppBarLayout mAppBar = (AppBarLayout) findViewById(R.id.appbar);
        int screenWidth = DensityUtil.getScreenWidth(this);
        ViewGroup.LayoutParams layoutParams = mAppBar.getLayoutParams();
        layoutParams.width = screenWidth;
        layoutParams.height = screenWidth;
        mAppBar.setLayoutParams(layoutParams);
        tvCommodityName = (TextView) findViewById(R.id.tv_commodity_name);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvWord = (TextView) findViewById(R.id.tv_word);
        tvContent = (TextView) findViewById(R.id.tv_content);

        toolbarLayout.setTitle("");
        //通过CollapsingToolbarLayout修改字体颜色

        mCBView = new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        };

        mBanner.setPointViewVisible(true)    //设置指示器是否可见
                .setPageIndicator(new int[]{R.drawable.circle_img_unselect_bg, R.drawable.circle_img_select_bg})   //设置指示器圆点
                .startTurning(3000)//设置自动切换（同时设置了切换时间间隔）
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT) //设置指示器位置（左、中、右)
                .setOnItemClickListener(this)//设置点击监听事件
                .setManualPageable(true);//设置手动影响（设置可否手动切换）

        initData();

        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                System.out.println("==================" + verticalOffset);
                if (verticalOffset < -320) {
                    toolbarLayout.setTitle("奖品详情");
                } else {
                    toolbarLayout.setTitle("");
                }
            }
        });
    }

    @Override
    public void onItemClick(int position) {

    }

    private void initData() {
        try {
            int prizeId = getIntent().getExtras().getInt("prizeId");
            System.out.println("================" + prizeId);
            NetWorkUtil.setCallback("Corp/GetActivityPrizesInfo", new BeanActivityInfo(prizeId), new NetWorkUtil.CallBackListener() {
                @Override
                public void onFinish(String respose) {
                    PrizeDetailsData data = new Gson().fromJson(respose, PrizeDetailsData.class);
                    if (data.getMessage().getCode().equals("200")) {
                        setData(data);
                    }
                }

                @Override
                public void onError(String e) {

                }
            });
        } catch (Exception e) {
            Log.e("异常：", e.toString());
        }
    }

    private void setData(PrizeDetailsData data) {
        tvCommodityName.setText(data.getCommodityName());
        tvWord.setText(data.getPrizesKeyWord());
        NumberFormat ddf1 = NumberFormat.getNumberInstance();

        ddf1.setMaximumFractionDigits(0);
        String price = ddf1.format(data.getMarketPrice());
        tvPrice.setText("￥" + price);
        tvContent.setText(data.getPrizesContent());

        String[] images = data.getImgs().split(",");
        List<String> imageList = Arrays.asList(images);
        mBanner.setPages(mCBView, imageList);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }

    /**
     * 相册HolderView
     */
    public static class NetworkImageHolderView implements Holder<String> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            Glide.with(context).load(data).placeholder(R.color.gray_background).error(R.color.gray_background).into(imageView);
        }
    }
}
