package com.dikai.chenghunjiclient.activity.store;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.bean.BeanAddToCart;
import com.dikai.chenghunjiclient.bean.BeanGetGoodModel;
import com.dikai.chenghunjiclient.bean.BeanID;
import com.dikai.chenghunjiclient.entity.GoodModelBean;
import com.dikai.chenghunjiclient.entity.ResultGetGoodInfo;
import com.dikai.chenghunjiclient.entity.ResultGetGoodModel;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.flyco.dialog.widget.base.BaseDialog;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.joooonho.SelectableRoundedImageView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import dmax.dialog.SpotsDialog;
import q.rorbin.badgeview.QBadgeView;

public class WedPrizeInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private ConvenientBanner mPrizeBanner;
    private CBViewHolderCreator<BannerHolderView> mPrizeView;
    private TextView name;
    private TextView price;
    private TextView source;
    private SubsamplingScaleImageView info;
    private ServiceDialog bottomDialog;
    private TextView addCart;
    private List<GoodModelBean> tags;
    private ResultGetGoodInfo mGoodInfo;
    private ImageView cartImg;
    private QBadgeView mQBadgeView;
    private SpotsDialog mDialog;
    private String activityid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wed_prize_info);
        EventBus.getDefault().register(this);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        activityid = getIntent().getStringExtra("activityid");
        if (activityid == null){
            activityid = "51F48013-9ADA-4342-96BF-259C345832AE";
        }
        mDialog = new SpotsDialog(this,R.style.DialogCustom);
        findViewById(R.id.back).setOnClickListener(this);
        cartImg = (ImageView) findViewById(R.id.cart);
        cartImg.setOnClickListener(this);
        name = (TextView) findViewById(R.id.name);
        price = (TextView) findViewById(R.id.price);
        source = (TextView) findViewById(R.id.source);
        info = (SubsamplingScaleImageView) findViewById(R.id.info);
        addCart = (TextView) findViewById(R.id.add_cart);
        mPrizeBanner = (ConvenientBanner) findViewById(R.id.imgs);
        mPrizeView = new CBViewHolderCreator<BannerHolderView>() {
            @Override
            public BannerHolderView createHolder() {
                return new BannerHolderView();
            }
        };
        mPrizeBanner.setPointViewVisible(true)//设置指示器是否可见
                .setPageIndicator(new int[]{R.drawable.circle_lead_unslected, R.drawable.circle_lead_slected})   //设置指示器圆点
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL) //设置指示器位置（左、中、右)
                .startTurning(3000)//设置自动切换（同时设置了切换时间间隔）
                .setManualPageable(true);//设置手动影响（设置可否手动切换）
        addCart.setOnClickListener(this);
        bottomDialog = new ServiceDialog(this);
        bottomDialog.widthScale(1);
        bottomDialog.heightScale(1);
        int num = UserManager.getInstance(WedPrizeInfoActivity.this).getMyCartNum();
        mQBadgeView = new QBadgeView(this);
        mQBadgeView.bindTarget(cartImg);
        mQBadgeView.setBadgeNumber(num);

        if(getIntent().getIntExtra("type",0) == 1){
            addCart.setVisibility(View.GONE);
            cartImg.setVisibility(View.GONE);
        }

        getInfo();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.add_cart:
                bottomDialog.show();
                bottomDialog.setUiBeforShow();
                break;
            case R.id.cart:
                startActivity(new Intent(this, CartActivity.class)
                        .putExtra("activityid",activityid));
                break;
        }
    }

    private void loadImg(final String mImgUrl){
        Glide.with(this)
                .load(mImgUrl)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        if(resource.getHeight() < 4096 && resource.getWidth() < 4096){
                            info.setImage(ImageSource.bitmap(resource));
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
                            info.setImage(ImageSource.uri(picFile.getAbsolutePath()));
                        }
                    }
                });
    }

    /**
     * 获取info
     */
    private void getInfo(){
        NetWorkUtil.setCallback("HQOAApi/GetCommodityInfo",
                new BeanID(getIntent().getStringExtra("prizeId")),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultGetGoodInfo result = new Gson().fromJson(respose, ResultGetGoodInfo.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                setData(result);
                            } else {
                                Toast.makeText(WedPrizeInfoActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        Toast.makeText(WedPrizeInfoActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 获取型号
     */
    private void getModel(String id){
        NetWorkUtil.setCallback("HQOAApi/GetSpecificationList",
                new BeanGetGoodModel(id),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultGetGoodModel result = new Gson().fromJson(respose, ResultGetGoodModel.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                tags = result.getData();
                            } else {
                                Toast.makeText(WedPrizeInfoActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        Toast.makeText(WedPrizeInfoActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 添加至购物车
     */
    private void addToCart(String id, int number){
        mDialog.show();
        NetWorkUtil.setCallback("HQOAApi/AddcommodityToShoppingCart",
                new BeanAddToCart(UserManager.getInstance(this).getNewUserInfo().getUserId(),mGoodInfo.getCommodityId(),id,number,activityid),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                Toast.makeText(WedPrizeInfoActivity.this, "已加入购物车！", Toast.LENGTH_SHORT).show();
                                EventBus.getDefault().post(new EventBusBean(Constants.CART_CHANGED));
                            } else {
                                Toast.makeText(WedPrizeInfoActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Toast.makeText(WedPrizeInfoActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setData(ResultGetGoodInfo data) {
        mGoodInfo = data;
        name.setText(data.getCommodityName());
        source.setText("产地：" + data.getPlaceOrigin());
        NumberFormat ddf1 = NumberFormat.getNumberInstance();
        ddf1.setMaximumFractionDigits(0);
        price.setText(data.getQuota()+"");
//        Glide.with(WedPrizeInfoActivity.this).load(data.getBriefIntroduction()).into(info);
        loadImg(data.getBriefIntroduction());
        String[] images = data.getCarouselFigure().split(",");
        List<String> imageList = Arrays.asList(images);
        mPrizeBanner.setPages(mPrizeView, imageList);
        getModel(data.getCommodityId());
    }

    /**
     *rule
     */
    private class ServiceDialog extends BaseDialog<ServiceDialog> implements View.OnClickListener {
        private View mView;
        private SelectableRoundedImageView img;
        private TextView price;
        private TextView info;
        private TextView stock;
        private ImageView close;
        private TagFlowLayout mflow;
        private ImageView subtract;
        private ImageView add;
        private TextView num;
        private TextView next;
        private TagAdapter mTagAdapter;
        private int selectNum = 1;
        private int position;
        private GoodModelBean nowTag;

        ServiceDialog(Context context) {
            super(context);
        }
        @Override
        public View onCreateView() {
            mView = View.inflate(mContext, R.layout.select_goods_layout, null);
            img = (SelectableRoundedImageView) mView.findViewById(R.id.img);
            price = (TextView) mView.findViewById(R.id.price);
            info = (TextView) mView.findViewById(R.id.select_info);
            stock = (TextView) mView.findViewById(R.id.stock);
            close = (ImageView) mView.findViewById(R.id.close);
            subtract = (ImageView) mView.findViewById(R.id.subtract);
            add = (ImageView) mView.findViewById(R.id.add);
            num = (TextView) mView.findViewById(R.id.select_num);
            next = (TextView) mView.findViewById(R.id.next);
            mflow = (TagFlowLayout) mView.findViewById(R.id.id_flowlayout);
            close.setOnClickListener(this);
            subtract.setOnClickListener(this);
            add.setOnClickListener(this);
            next.setOnClickListener(this);
            try {
                mTagAdapter = new TagAdapter<GoodModelBean>(tags) {
                    @Override
                    public View getView(FlowLayout parent, int position, GoodModelBean bean) {
                        TextView tv = (TextView) LayoutInflater.from(WedPrizeInfoActivity.this)
                                .inflate(R.layout.tag_item_layout, mflow, false);
                        tv.setText(bean.getName());
                        return tv;
                    }
                };
                mflow.setAdapter(mTagAdapter);
                mflow.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                    @Override
                    public void onSelected(Set<Integer> selectPosSet) {
                        selectNum = 1;
                        num.setText("1");
                        if(selectPosSet.isEmpty()){
                            nowTag = null;
                            info.setText("");
                            stock.setText("");
                        }else {
                            position = (Integer) selectPosSet.toArray()[0];
                            nowTag = tags.get(position);
                            stock.setText("库存" + nowTag.getNumber() + "件");
                            setInfo();
                        }
                    }
                });
            }catch (Exception e){
                Log.e("Exception",e.toString());
            }
            return mView;
        }
        @Override
        public void onViewCreated(View view) {
            super.onViewCreated(view);
        }
        @Override
        public void setUiBeforShow() {
            price.setText("￥" + mGoodInfo.getQuota());
            Glide.with(getContext()).load(mGoodInfo.getCoverMap()).into(img);
        }

        @Override
        public void onClick(View v) {
            if(v == close){
                this.dismiss();
            }else if(v == add){
                if(nowTag != null && selectNum < nowTag.getNumber()){
                    selectNum++;
                    num.setText(""+selectNum);
                    setInfo();
                }
            }else if(v == subtract){
                if(nowTag != null && selectNum > 1){
                    selectNum--;
                    num.setText(""+selectNum);
                    setInfo();
                }
            }else if(v == next){
                if(selectNum <= 0){
                    Toast.makeText(mContext, "至少选择一件！", Toast.LENGTH_SHORT).show();
                }else if(nowTag == null){
                    Toast.makeText(mContext, "请选择“商品类型”后，再添加商品数量", Toast.LENGTH_SHORT).show();
                }else{
                    addToCart(nowTag.getId(),selectNum);
                }
            }
        }

        private void setInfo(){
            info.setText("已选择 " + tags.get(position).getName() + " " + selectNum + "件");
        }
    }

    /**
     * 福利HolderView
     */
    public class BannerHolderView implements Holder<String> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            Glide.with(context).load(data).
                    placeholder(R.color.gray_background).into(imageView);
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
                if(bean.getType() == Constants.CART_NUM){
                    mQBadgeView.setBadgeNumber(UserManager.getInstance(WedPrizeInfoActivity.this).getMyCartNum());
                }else if(bean.getType() == Constants.CLEARCARTA_FINISH){
                    finish();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
