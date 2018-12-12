package com.dikai.chenghunjiclient.fragment.store;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.discover.MessageListActivity;
import com.dikai.chenghunjiclient.activity.me.ShareAppActivity;
import com.dikai.chenghunjiclient.activity.register.LoginActivity;
import com.dikai.chenghunjiclient.activity.store.BannerInfoActivity;
import com.dikai.chenghunjiclient.activity.store.BoomActivity;
import com.dikai.chenghunjiclient.activity.store.HomeFuliActivity;
import com.dikai.chenghunjiclient.activity.store.HomeSupActivity;
import com.dikai.chenghunjiclient.activity.store.NewArticleActivity;
import com.dikai.chenghunjiclient.activity.store.SearchSupActivity;
import com.dikai.chenghunjiclient.activity.store.StoreSupActivity;
import com.dikai.chenghunjiclient.activity.store.WebProListActivity;
import com.dikai.chenghunjiclient.activity.store.WeddingStoreActivity;
import com.dikai.chenghunjiclient.activity.wedding.FreeWedActivity;
import com.dikai.chenghunjiclient.activity.wedding.GiftListActivity;
import com.dikai.chenghunjiclient.activity.wedding.InviteWedActivity;
import com.dikai.chenghunjiclient.adapter.store.HotProjectAdapter;
import com.dikai.chenghunjiclient.adapter.store.MainHeadAdapter;
import com.dikai.chenghunjiclient.adapter.store.StoreSupAdapter;
import com.dikai.chenghunjiclient.adapter.store.ZixunAdapter;
import com.dikai.chenghunjiclient.bean.BeanEmpty;
import com.dikai.chenghunjiclient.bean.BeanGetCoupon;
import com.dikai.chenghunjiclient.bean.BeanGetHomePro;
import com.dikai.chenghunjiclient.bean.BeanGetHomeSup;
import com.dikai.chenghunjiclient.bean.BeanGetZixun;
import com.dikai.chenghunjiclient.bean.BeanNone;
import com.dikai.chenghunjiclient.bean.BeanNull;
import com.dikai.chenghunjiclient.citypicker.SelectCityActivity;
import com.dikai.chenghunjiclient.entity.BannerBean;
import com.dikai.chenghunjiclient.entity.CouponBean;
import com.dikai.chenghunjiclient.entity.HomeFuliBean;
import com.dikai.chenghunjiclient.entity.IdentityBean;
import com.dikai.chenghunjiclient.entity.PrizeData;
import com.dikai.chenghunjiclient.entity.ResultGetBanner;
import com.dikai.chenghunjiclient.entity.ResultGetCoupon;
import com.dikai.chenghunjiclient.entity.ResultGetHomeFuli;
import com.dikai.chenghunjiclient.entity.ResultGetHomePro;
import com.dikai.chenghunjiclient.entity.ResultGetHomeSup;
import com.dikai.chenghunjiclient.entity.ResultGetIdentity;
import com.dikai.chenghunjiclient.entity.ResultGetZixun;
import com.dikai.chenghunjiclient.entity.ResultZixunTag;
import com.dikai.chenghunjiclient.entity.ZixunTagBean;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyImageView;
import com.dikai.chenghunjiclient.view.MyRecyclerView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewStoreFragment extends Fragment implements View.OnClickListener {

    private String areaID = "0";
    private String identID = "SF_14001000";
    private LinearLayout locationLayout;
    private LinearLayout dateLayout;
    private RelativeLayout search;
    private TextView location;

    private NestedScrollView mScrollView;
    private TabLayout mTabLayout;
//    private ViewPager mViewPager;
//    private List<Fragment> mFragments;
//    private MainFragmentAdapter mPagerAdapter;
    private ConvenientBanner mProfessionBanner;
    private CBViewHolderCreator mProfessionView;
    private ConvenientBanner mAdBanner;
    private CBViewHolderCreator mAdView;
    private ConvenientBanner mFuliBanner;
    private CBViewHolderCreator mFuliView;
    private RecyclerView hotProject;
    private HotProjectAdapter mProjectAdapter;
    private List<BannerBean> banners;

    private RecyclerView zixunRecycler;
    private ZixunAdapter mZixunAdapter;

    private RecyclerView mRecyclerView;
    private StoreSupAdapter mSupAdapter;

    private TagContainerLayout mTagLayout;

//    private ImageView homeRed;

//    private MyImageView coupon1;
//    private MyImageView coupon2;
//    private MyImageView coupon3;
//    private MyImageView coupon4;
    private MyImageView fanhuan;

    private MyImageView inviteImg;
    private LinearLayout moreFuliLayout;
    private LinearLayout webProLayout;
    private LinearLayout zixunLayout;

    private int supPosition = 0;
    private List<ZixunTagBean> tags;
    private List<IdentityBean> professions;
    private TextView placeText;

    private LinearLayout moreSup;
    private List<PrizeData.PrizeDataList> prize;
    private List<CouponBean> coupons;
    private List<HomeFuliBean> mHomeFulis;

    public NewStoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        EventBus.getDefault().register(this);
        return inflater.inflate(R.layout.fragment_new_store, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView title = (TextView) view.findViewById(R.id.fragment_store_title);
        placeText = (TextView) view.findViewById(R.id.place_text);
        mScrollView = (NestedScrollView) view.findViewById(R.id.new_store_scroll);
        locationLayout = (LinearLayout) view.findViewById(R.id.fragment_store_location_layout);
        search = (RelativeLayout) view.findViewById(R.id.fragment_store_search);
        dateLayout = (LinearLayout) view.findViewById(R.id.fragment_store_date);
        moreSup = (LinearLayout) view.findViewById(R.id.more_sup);
        location = (TextView) view.findViewById(R.id.fragment_store_location);

        mTabLayout = (TabLayout) view.findViewById(R.id.new_store_tabs);
//        mViewPager = (ViewPager) view.findViewById(R.id.new_store_paper);
        hotProject = (RecyclerView) view.findViewById(R.id.hot_project_recycler);
        zixunRecycler = (RecyclerView) view.findViewById(R.id.zixun_list);
        mTagLayout = (TagContainerLayout) view.findViewById(R.id.zixun_tag);
//        homeRed = (ImageView) view.findViewById(R.id.home_red_packet);
//        coupon1 = (MyImageView) view.findViewById(R.id.coupon_1);
//        coupon2 = (MyImageView) view.findViewById(R.id.coupon_2);
//        coupon3 = (MyImageView) view.findViewById(R.id.coupon_3);
//        coupon4 = (MyImageView) view.findViewById(R.id.coupon_4);
        fanhuan = (MyImageView) view.findViewById(R.id.fanhuan);

        inviteImg = (MyImageView) view.findViewById(R.id.invite_img);
        moreFuliLayout = (LinearLayout) view.findViewById(R.id.invite_more);
        webProLayout = (LinearLayout) view.findViewById(R.id.webpro_more);
        zixunLayout = (LinearLayout) view.findViewById(R.id.zixun_more);

        locationLayout.setOnClickListener(this);
        dateLayout.setOnClickListener(this);
        search.setOnClickListener(this);
//        prize5.setOnClickListener(this);
//        homeRed.setOnClickListener(this);
//        coupon1.setOnClickListener(this);
//        coupon2.setOnClickListener(this);
//        coupon3.setOnClickListener(this);
//        coupon4.setOnClickListener(this);
        fanhuan.setOnClickListener(this);
        moreSup.setOnClickListener(this);
        inviteImg.setOnClickListener(this);
        moreFuliLayout.setOnClickListener(this);
        webProLayout.setOnClickListener(this);
        zixunLayout.setOnClickListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        hotProject.setLayoutManager(linearLayoutManager);
        mProjectAdapter = new HotProjectAdapter(getContext());
        hotProject.setAdapter(mProjectAdapter);
        hotProject.setNestedScrollingEnabled(false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        zixunRecycler.setLayoutManager(layoutManager);
        mZixunAdapter = new ZixunAdapter(getContext());
        zixunRecycler.setAdapter(mZixunAdapter);
        zixunRecycler.setNestedScrollingEnabled(false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.store_sup_recycler);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(manager);
        mSupAdapter = new StoreSupAdapter(getContext());
        mRecyclerView.setAdapter(mSupAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);

        mProfessionBanner = (ConvenientBanner) view.findViewById(R.id.fragment_head_convenientBanner);
        mProfessionView = new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        };

        mAdBanner = (ConvenientBanner) view.findViewById(R.id.fragment_head_ad);
        mAdView = new CBViewHolderCreator<AdBannerHolderView>() {
            @Override
            public AdBannerHolderView createHolder() {
                return new AdBannerHolderView();
            }
        };

        mFuliBanner = (ConvenientBanner) view.findViewById(R.id.fragment_head_fuli);
        mFuliView = new CBViewHolderCreator<FuliBannerHolderView>() {
            @Override
            public FuliBannerHolderView createHolder() {
                return new FuliBannerHolderView();
            }
        };

        mAdBanner.setPointViewVisible(false)//设置指示器是否可见
                .startTurning(3000)//设置自动切换（同时设置了切换时间间隔）
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        try {
                            if(banners != null && position < banners.size()){
                                if(banners.get(position).getHTMLURL() != null &&
                                        !"".equals(banners.get(position).getHTMLURL())){
                                    if(position == 1){
                                        if (!UserManager.getInstance(getContext()).isLogin()) {
                                            startActivity(new Intent(getContext(), LoginActivity.class));
                                        } else {
                                            startActivity(new Intent(getContext(), FreeWedActivity.class));
                                        }
                                    }else if(position == 6){
                                        if (!UserManager.getInstance(getContext()).isLogin()) {
                                            startActivity(new Intent(getContext(), LoginActivity.class));
                                        } else {
                                            startActivity(new Intent(getContext(), InviteWedActivity.class));
                                        }
                                    }else {
                                        startActivity(new Intent(getContext(), BannerInfoActivity.class)
                                                .putExtra("url",banners.get(position).getHTMLURL()));
                                    }
                                }
                            }
                        }catch (Exception e){
                            Log.e("错误: ",e.toString());
                        }
                    }
                })//设置点击监听事件
                .setManualPageable(true);//设置手动影响（设置可否手动切换）

        mFuliBanner.setPointViewVisible(true)//设置指示器是否可见
                .setPageIndicator(new int[]{R.drawable.circle_lead_unslected, R.drawable.circle_lead_slected})   //设置指示器圆点
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL) //设置指示器位置（左、中、右)
                .startTurning(3000)//设置自动切换（同时设置了切换时间间隔）
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        if (UserManager.getInstance(getContext()).isLogin()) {
                            if(position == 0){
                                startActivity(new Intent(getContext(), InviteWedActivity.class));
                            }else if(position == 1){
                                startActivity(new Intent(getContext(), FreeWedActivity.class));
                            }else if(position == 2){
                                startActivity(new Intent(getContext(), ShareAppActivity.class));
                            }else if(position == 3){
                                startActivity(new Intent(getContext(), BoomActivity.class));
                            }
                        } else {
                            startActivity(new Intent(getContext(), LoginActivity.class));
                        }
                    }
                })//设置点击监听事件
                .setManualPageable(true);//设置手动影响（设置可否手动切换）


        mTagLayout.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
                getZixun(tags.get(position).getWeddingInformationID());
            }

            @Override
            public void onTagLongClick(int position, String text) {}
        });
        title.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis())));
        if(UserManager.getInstance(getContext()).isLogin()){
            identID = UserManager.getInstance(getContext()).getUserInfo().getProfession();
            String loca = UserManager.getInstance(getContext()).getLocation();
            if(loca != null && !"".equals(loca)){
                String[] info = loca.split(",");
                areaID = info[0];
                location.setText(info[1]);
                Log.e("数据：=========== ",loca);
            }
        }else{
            location.setText("青岛");
        }
    }

    @Override
    public void onClick(View v) {
        if(v == locationLayout){
            startActivity(new Intent(getContext(), SelectCityActivity.class).putExtra("type",Constants.HOME_SELECT_CITY));
        }else if(v == dateLayout){
            startActivity(new Intent(getContext(), StoreSupActivity.class));
        }else if(v == search){
            if (UserManager.getInstance(getContext()).checkLogin())
                startActivity(new Intent(getContext(), SearchSupActivity.class));
        }else if(v == moreSup){
            Bundle bundle = new Bundle();
            bundle.putString("areaID",areaID);
            bundle.putSerializable("ident",professions.get(supPosition));
            startActivity(new Intent(getContext(), HomeSupActivity.class).putExtras(bundle));
//        }else if(v == coupon1){
//            if(coupons.get(0).getDiscountURL() != null && !"".equals(coupons.get(0).getDiscountURL())){
//                startActivity(new Intent(getContext(), BannerInfoActivity.class)
//                        .putExtra("url",coupons.get(0).getDiscountURL()));
//            }
//        }else if(v == coupon2){
//            if(coupons.get(1).getDiscountURL() != null && !"".equals(coupons.get(1).getDiscountURL())){
//                startActivity(new Intent(getContext(), BannerInfoActivity.class)
//                        .putExtra("url",coupons.get(1).getDiscountURL()));
//            }
//        }else if(v == coupon3){
//            if(coupons.get(2).getDiscountURL() != null && !"".equals(coupons.get(2).getDiscountURL())){
//                startActivity(new Intent(getContext(), BannerInfoActivity.class)
//                        .putExtra("url",coupons.get(2).getDiscountURL()));
//            }
//        }else if(v == coupon4){
//            if(coupons.get(3).getDiscountURL() != null && !"".equals(coupons.get(3).getDiscountURL())){
//                startActivity(new Intent(getContext(), BannerInfoActivity.class)
//                        .putExtra("url",coupons.get(3).getDiscountURL()));
//            }
        }else if(v == fanhuan){
            if (!UserManager.getInstance(getContext()).isLogin()) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            } else {
                startActivity(new Intent(getContext(), WeddingStoreActivity.class));
            }
        }else if(v == inviteImg){
            if (!UserManager.getInstance(getContext()).isLogin()) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            } else {
//                startActivity(new Intent(getContext(), InviteActivity1.class));
                startActivity(new Intent(getContext(), FreeWedActivity.class));
            }
        }else if(v == moreFuliLayout){//
            if (!UserManager.getInstance(getContext()).isLogin()) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            } else {
                //startActivity(new Intent(getContext(), InviteActivity1.class));
                startActivity(new Intent(getContext(), HomeFuliActivity.class));
            }
        }else if(v == webProLayout){
            startActivity(new Intent(getContext(), WebProListActivity.class));
        }else if(v == zixunLayout){
            startActivity(new Intent(getContext(), MessageListActivity.class));
//        }else if(v == homeRed){
//            EventBus.getDefault().post(new EventBusBean(Constants.SHOW_RED_PACKET));
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Glide.with(getContext()).load(R.drawable.ic_app_home_fanhuan).into(fanhuan);
        getIdentity();
        getBanner();
//        getPrize();
        getFuli();
        getProject();
        getZixunTag();
//        getCoupon();
    }

    private void initFragment(List<IdentityBean> data) {
        professions = data;
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        for (IdentityBean bean : professions) {
            mTabLayout.addTab(mTabLayout.newTab().setText(bean.getOccupationName()));
        }
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                supPosition = tab.getPosition();
                getHomeSup(professions.get(supPosition).getOccupationCode(),areaID);
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
        getHomeSup(professions.get(0).getOccupationCode(),areaID);
    }

    private void setData(List<IdentityBean> data) {
        data.subList(0,10);
        List<List<IdentityBean>> lists = new ArrayList<>();
        Collections.addAll(lists, data.subList(0,10), data.subList(10,data.size()));
        mProfessionBanner.setPages    (mProfessionView, lists);
        mProfessionBanner.setPointViewVisible(true)//设置指示器是否可见
                .setPageIndicator(new int[]{R.drawable.cbview_selector_2, R.drawable.cbview_selector})   //设置指示器圆点
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL) //设置指示器位置（左、中、右)
                .setManualPageable(true);//设置手动影响（设置可否手动切换）
    }

    private void setBanner(ResultGetBanner result){
        banners = result.getPhoneBanner();
        mAdBanner.setPages(mAdView, banners);
    }

//    private void setPrize( PrizeData result) {
//        prize = result.getData();
//        Glide.with(getContext()).load(prize.get(0).getShowImg()).placeholder(R.color.gray_background).into(prize1);
//        Glide.with(getContext()).load(prize.get(1).getShowImg()).placeholder(R.color.gray_background).into(prize2);
//        Glide.with(getContext()).load(prize.get(2).getShowImg()).placeholder(R.color.gray_background).into(prize3);
//        Glide.with(getContext()).load(prize.get(3).getShowImg()).placeholder(R.color.gray_background).into(prize4);
//        Glide.with(getContext()).load(prize.get(4).getShowImg()).placeholder(R.color.gray_background).into(prize5);
//    }
    private void setFuli(ResultGetHomeFuli result) {
        mHomeFulis = result.getData();
        mFuliBanner.setPages(mFuliView, mHomeFulis);
    }

    private void setZixunTag(ResultZixunTag result) {
        tags = result.getData();
        for (ZixunTagBean bean : tags) {
            mTagLayout.addTag(bean.getTitle());
        }
        getZixun(tags.get(0).getWeddingInformationID());
    }

    private void setCoupon(ResultGetCoupon result) {
//        coupons = result.getData();
//        Glide.with(getContext()).load(coupons.get(0).getImgUrl()).placeholder(R.color.gray_background).into(coupon1);
//        Glide.with(getContext()).load(coupons.get(1).getImgUrl()).placeholder(R.color.gray_background).into(coupon2);
//        Glide.with(getContext()).load(coupons.get(2).getImgUrl()).placeholder(R.color.gray_background).into(coupon3);
//        Glide.with(getContext()).load(coupons.get(3).getImgUrl()).placeholder(R.color.gray_background).into(coupon4);
    }

    /**
     * 获取职业
     */
    private void getIdentity(){
        NetWorkUtil.setCallback("User/GetAllOccupationList",
                new BeanNull("2"),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultGetIdentity result = new Gson().fromJson(respose, ResultGetIdentity.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                setData(result.getData());
                                initFragment(result.getData());
                            } else {
                                Toast.makeText(getContext(), result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
//                        Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 获取Banner
     */
    private void getBanner(){
        NetWorkUtil.setCallback("User/GetWebBannerUrl",
                new BeanEmpty(),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultGetBanner result = new Gson().fromJson(respose, ResultGetBanner.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                setBanner(result);
                            } else {
                                Toast.makeText(getContext(), result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
//                        Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    /**
     * 获取福利列表
     */
    private void getFuli() {
        NetWorkUtil.setCallback("User/GetWeChatActivityList",
                new BeanNone(),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultGetHomeFuli result = new Gson().fromJson(respose, ResultGetHomeFuli.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                setFuli(result);
                            } else {
                                Toast.makeText(getContext(), result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError(String e) {}
                });

    }

//    /**
//     * 获取奖品列表
//     */
//    private void getPrize() {
//        NetWorkUtil.setCallback("Corp/GetActivityPrizesList",
//                new BeanPager("1", "5"),
//                new NetWorkUtil.CallBackListener() {
//                    @Override
//                    public void onFinish(String respose) {
//                        Log.e("返回值",respose);
//                        try {
//                            PrizeData result = new Gson().fromJson(respose, PrizeData.class);
//                            if ("200".equals(result.getMessage().getCode())) {
//                                setPrize(result);
//                            } else {
//                                Toast.makeText(getContext(), result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (Exception e) {
//                            Log.e("json解析出错",e.toString());
//                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onError(String e) {
//
//                    }
//        });
//
//    }

    /**
     * 获取策划方案
     */
    private void getProject(){
        NetWorkUtil.setCallback("User/GetWebPlanList",
                new BeanGetHomePro(1,6),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultGetHomePro result = new Gson().fromJson(respose, ResultGetHomePro.class);
                            if ("200".equals(result.getMessage().getCode())) {
//                                mProjectAdapter.refresh(result.getData());
                            } else {
                                Toast.makeText(getContext(), result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
//                        Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 获取资讯Tag
     */
    private void getZixunTag(){
        NetWorkUtil.setCallback("User/GetWeddingInformationList",
                new BeanGetHomePro(1,10),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultZixunTag result = new Gson().fromJson(respose, ResultZixunTag.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                setZixunTag(result);
                            } else {
                                Toast.makeText(getContext(), result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
//                        Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 获取资讯文章
     */
    private void getZixun(String id){
        NetWorkUtil.setCallback("User/GetInformationArticleList",
                new BeanGetZixun(1,3,id),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultGetZixun result = new Gson().fromJson(respose, ResultGetZixun.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                mZixunAdapter.refresh(result.getData());
                            } else {
                                Toast.makeText(getContext(), result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
//                        Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 获取优惠信息
     */
    private void getCoupon(){
        NetWorkUtil.setCallback("User/GetWebDiscountList",
                new BeanGetCoupon(1,4,1),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultGetCoupon result = new Gson().fromJson(respose, ResultGetCoupon.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                setCoupon(result);
                            } else {
                                Toast.makeText(getContext(), result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
//                        Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 获取供应商
     */
    private void getHomeSup(String profession,String areaID){
        NetWorkUtil.setCallback("User/GetWebSupplierList",
                new BeanGetHomeSup(profession,"1","6","0",areaID,"",""),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultGetHomeSup result = new Gson().fromJson(respose, ResultGetHomeSup.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                mSupAdapter.refresh(result.getData());
                                if(result.getData().size() > 0){
                                    placeText.setVisibility(View.GONE);
                                }else {
                                    placeText.setVisibility(View.VISIBLE);
                                }
                            } else {
                                Toast.makeText(getContext(), result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
//                        Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 广告HolderView
     */
    public class AdBannerHolderView implements Holder<BannerBean> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, BannerBean data) {
            Glide.with(context).load(data.getBannerURL()).
                    placeholder(R.color.gray_background).into(imageView);
        }
    }

    /**
     * 福利HolderView
     */
    public class FuliBannerHolderView implements Holder<HomeFuliBean> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, HomeFuliBean data) {
            Glide.with(context).load(data.getImg()).
                    placeholder(R.color.gray_background).into(imageView);
        }
    }

    /**
     * CBView的HolderView
     */
    public class NetworkImageHolderView implements Holder<List<IdentityBean>> {
        private MyRecyclerView mRecyclerView;

        @Override
        public View createView(Context context) {
            //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
            View view = LayoutInflater.from(context).inflate(R.layout.item_cbview_layout, null);
            mRecyclerView = (MyRecyclerView) view.findViewById(R.id.my_cbview_recycler);
            return view;
        }

        @Override
        public void UpdateUI(Context context,int position, List<IdentityBean> data) {
//            GridLayoutManager gridLayoutManager = new GridLayoutManager(context,5);
//            mRecyclerView.setLayoutManager(gridLayoutManager);
//            MainHeadAdapter adapter = new MainHeadAdapter(context);
//            adapter.setOnItemClickListener(new MainHeadAdapter.OnItemClickListener() {
//                @Override
//                public void onClick(IdentityBean bean) {
//                    Bundle bundle = new Bundle();
//                    bundle.putString("areaID",areaID);
//                    bundle.putSerializable("ident",bean);
//                    startActivity(new Intent(getContext(), HomeSupActivity.class).putExtras(bundle));
//                }
//            });
//            mRecyclerView.setAdapter(adapter);
//            adapter.refresh(data);
        }
    }

    /**
     * 事件总线刷新
     * @param bean
     */
    @Subscribe
    public void onEventMainThread(final EventBusBean bean) {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                if(bean.getType() == Constants.HOME_SELECT_CITY){
                    areaID = bean.getCountry().getRegionId();
                    location.setText(bean.getCountry().getRegionName());
                    UserManager.getInstance(getContext()).setLocation(bean.getCountry().getRegionId()+","
                            + bean.getCountry().getRegionName());
                    getHomeSup(professions.get(supPosition).getOccupationCode(),areaID);
                }
//                else if(bean.getType() == Constants.HOME_SELECT_IDENTITY){
//                    identID = bean.getIdentityBean().getOccupationCode();
//                    refresh();
//                }else if(bean.getType() == Constants.USER_INFO_CHANGE){
//                    if(UserManager.getInstance(getContext()).isLogin()){
//                        String loca = UserManager.getInstance(getContext()).getLocation();
//                        if(loca != null && !"".equals(loca)){
//                            String[] info = loca.split(",");
//                            areaID = info[0];
//                            location.setText(info[1]);
//                        }
//
//                    }else{
//                        refresh();
//                        location.setText("青岛");
//                    }
//                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
