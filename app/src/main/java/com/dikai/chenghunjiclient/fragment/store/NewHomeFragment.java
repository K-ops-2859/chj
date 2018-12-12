package com.dikai.chenghunjiclient.fragment.store;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.ad.KeYuanActivity;
import com.dikai.chenghunjiclient.activity.invitation.ComInviteMainActivity;
import com.dikai.chenghunjiclient.activity.invitation.ComInviteSuccActivity;
import com.dikai.chenghunjiclient.activity.invitation.VipInviteMainActivity;
import com.dikai.chenghunjiclient.activity.me.ShareAppActivity;
import com.dikai.chenghunjiclient.activity.newregister.NewLoginActivity;
import com.dikai.chenghunjiclient.activity.store.AddCollectionActivity;
import com.dikai.chenghunjiclient.activity.store.AddGiftActivity;
import com.dikai.chenghunjiclient.activity.store.BannerInfoActivity;
import com.dikai.chenghunjiclient.activity.store.BoomActivity;
import com.dikai.chenghunjiclient.activity.store.CaigoujieActivity;
import com.dikai.chenghunjiclient.activity.store.ComboActivity;
import com.dikai.chenghunjiclient.activity.store.HomeSupActivity;
import com.dikai.chenghunjiclient.activity.store.HotelADActivity;
import com.dikai.chenghunjiclient.activity.store.NewArticleActivity;
import com.dikai.chenghunjiclient.activity.store.NewHomeADActivity;
import com.dikai.chenghunjiclient.activity.store.ScanActivity;
import com.dikai.chenghunjiclient.activity.store.SearchSupActivity;
import com.dikai.chenghunjiclient.activity.store.WeChatPayH5Activity;
import com.dikai.chenghunjiclient.activity.store.WeddingStoreActivity;
import com.dikai.chenghunjiclient.activity.wedding.FreeWedActivity;
import com.dikai.chenghunjiclient.activity.wedding.InviteWedActivity;
import com.dikai.chenghunjiclient.activity.wedding.MakeProjectActivity;
import com.dikai.chenghunjiclient.activity.wedding.WedCaseActivity;
import com.dikai.chenghunjiclient.activity.wedding.WedProjectActivity;
import com.dikai.chenghunjiclient.activity.wedding.WeddingAssureActivity;
import com.dikai.chenghunjiclient.activity.wedding.WeddingTaocan;
import com.dikai.chenghunjiclient.adapter.store.HomeComboAdapter;
import com.dikai.chenghunjiclient.adapter.store.HomeZixunAdapter;
import com.dikai.chenghunjiclient.adapter.store.MainHeadAdapter;
import com.dikai.chenghunjiclient.adapter.store.ZixunTabAdapter;
import com.dikai.chenghunjiclient.bean.BeanBanner;
import com.dikai.chenghunjiclient.bean.BeanGetCombos;
import com.dikai.chenghunjiclient.bean.BeanGetZixun;
import com.dikai.chenghunjiclient.bean.BeanNone;
import com.dikai.chenghunjiclient.bean.BeanUserId;
import com.dikai.chenghunjiclient.bean.BeanUserInfo;
import com.dikai.chenghunjiclient.citypicker.SelectCityActivity;
import com.dikai.chenghunjiclient.entity.CombosBean;
import com.dikai.chenghunjiclient.entity.NewAdBean;
import com.dikai.chenghunjiclient.entity.NewBannerBean;
import com.dikai.chenghunjiclient.entity.NewIdentityBean;
import com.dikai.chenghunjiclient.entity.NewUserInfo;
import com.dikai.chenghunjiclient.entity.ResultBanner;
import com.dikai.chenghunjiclient.entity.ResultGetCombos;
import com.dikai.chenghunjiclient.entity.ResultGetInvitationProfit;
import com.dikai.chenghunjiclient.entity.ResultGetNewAd;
import com.dikai.chenghunjiclient.entity.ResultGetZixun;
import com.dikai.chenghunjiclient.entity.ResultIsNewsAddCustom;
import com.dikai.chenghunjiclient.entity.ResultNewIdentity;
import com.dikai.chenghunjiclient.entity.ResultZixunTag;
import com.dikai.chenghunjiclient.entity.ZixunBean;
import com.dikai.chenghunjiclient.entity.ZixunTagBean;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.DensityUtil;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyRecyclerView;
import com.flyco.dialog.widget.popup.base.BasePopup;
import com.google.gson.Gson;
import com.joooonho.SelectableRoundedImageView;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dmax.dialog.SpotsDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewHomeFragment extends Fragment implements View.OnClickListener {

    private String areaID = "1740";
    private String identID = "7DC8EDF8-A068-400F-AFD0-417B19DB3C7C";
    private LinearLayout locationLayout;
    private LinearLayout search;
    private TextView location;

    private ConvenientBanner mProfessionBanner;
    private CBViewHolderCreator mProfessionView;
    private MZBannerView mAdBanner;
    private List<NewBannerBean> banners = new ArrayList<>();

    private RecyclerView hotProject;
    private ImageView img1;
    private ImageView img5;
    private ImageView img6;
    private ImageView img7;
    private RelativeLayout homeAdd;
    private SelectableRoundedImageView img4;
    private TextView allProject;
    private TextView allAD;
    private HomeComboAdapter mHomeComboAdapter;
    private NestedScrollView mScrollView;
    private SwipeRefreshLayout fresh;
    private int totalNum;
    private int pageIndex = 1;
    private int itemCount = 20;
    private int CALL_REQUEST_CODE = 190;
    private SimpleCustomPop mSimpleCustomPop;
    private TranslateAnimation mShowAction;
    private TranslateAnimation mHiddenAction;

    private LinearLayout freeProject;
    private LinearLayout freeWedding;
    private LinearLayout weddingDanbao;
    private LinearLayout shareProject;

    private String zixunTabId;
    private RecyclerView mTabRecycler;
    private RecyclerView mTabRecycler2;
    private ZixunTabAdapter mTabAdapter;
    private RecyclerView mZixunRecycler;
    private HomeZixunAdapter mZixunAdapter;

    private LinearLayout titleLayout;
    private boolean isTabShow = false;
    private List<NewAdBean> homeADList;
    private SpotsDialog mDialog;
    
    public NewHomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        EventBus.getDefault().register(this);
        return inflater.inflate(R.layout.fragment_new_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDialog = new SpotsDialog(getContext(),R.style.DialogCustom);
        mScrollView = (NestedScrollView) view.findViewById(R.id.new_store_scroll);
        fresh = (SwipeRefreshLayout) view.findViewById(R.id.my_load_recycler_fresh);
        locationLayout = (LinearLayout) view.findViewById(R.id.fragment_store_location_layout);
        search = (LinearLayout) view.findViewById(R.id.fragment_store_search);
        titleLayout = (LinearLayout) view.findViewById(R.id.title_layout);
        location = (TextView) view.findViewById(R.id.fragment_store_location);
        homeAdd = (RelativeLayout) view.findViewById(R.id.home_scan);
        img1 = (ImageView) view.findViewById(R.id.img_info_1);
        img5 = (ImageView) view.findViewById(R.id.ad_img_1);
        img6 = (ImageView) view.findViewById(R.id.ad_img_2);
        img7 = (ImageView) view.findViewById(R.id.ad_img_3);
        img4 = (SelectableRoundedImageView) view.findViewById(R.id.img_info_4);
        homeAdd.setOnClickListener(this);
        locationLayout.setOnClickListener(this);
        search.setOnClickListener(this);
        img1.setOnClickListener(this);
        img4.setOnClickListener(this);
        img4.setOnClickListener(this);
        img5.setOnClickListener(this);
        img6.setOnClickListener(this);
        img7.setOnClickListener(this);

        freeProject = (LinearLayout) view.findViewById(R.id.free_project);
        freeWedding = (LinearLayout) view.findViewById(R.id.free_wedding);
        weddingDanbao = (LinearLayout) view.findViewById(R.id.wedding_danbao);
        shareProject = (LinearLayout) view.findViewById(R.id.share_project);

        freeProject.setOnClickListener(this);
        freeWedding.setOnClickListener(this);
        weddingDanbao.setOnClickListener(this);
        shareProject.setOnClickListener(this);

        allProject = (TextView) view.findViewById(R.id.all_project);
        allProject.setOnClickListener(this);
        allAD = (TextView) view.findViewById(R.id.all_ad);
        allAD.setOnClickListener(this);

        hotProject = (RecyclerView) view.findViewById(R.id.hot_project_recycler);
        LinearLayoutManager hotlayout = new LinearLayoutManager(getContext());
        hotlayout.setOrientation(LinearLayoutManager.HORIZONTAL);
        hotProject.setLayoutManager(hotlayout);
        mHomeComboAdapter = new HomeComboAdapter(getContext());
        mHomeComboAdapter.setOnItemClickListener(new HomeComboAdapter.OnItemClickListener() {
            @Override
            public void onClick(CombosBean bean) {
                if(UserManager.getInstance(getContext()).checkLogin())
                startActivity(new Intent(getContext(),ComboActivity.class).putExtra("id",bean.getId()));
            }
        });
        hotProject.setAdapter(mHomeComboAdapter);
        
        mProfessionBanner = (ConvenientBanner) view.findViewById(R.id.fragment_head_convenientBanner);
        mProfessionView = new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        };

        mAdBanner = (MZBannerView) view.findViewById(R.id.fragment_head_ad);
        mAdBanner.setDelayedTime(3000);
        mAdBanner.setIndicatorVisible(true);
        mAdBanner.setIndicatorAlign(MZBannerView.IndicatorAlign.CENTER);
//        mAdBanner.setIndicatorPadding(0,0,0, DensityUtil.dip2px(getContext(),8));
        mAdBanner.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int position) {
                try {
                    if(banners != null && position < banners.size()){
                        if(banners.get(position).getHTMLURL() != null &&
                                !"".equals(banners.get(position).getHTMLURL())){
                            startActivity(new Intent(getContext(), BannerInfoActivity.class)
                                    .putExtra("url",banners.get(position).getHTMLURL()));
                        }else if("MFBHL".equals(banners.get(position).getBannerCode())){
                            if (!UserManager.getInstance(getContext()).isLogin()) {
                                startActivity(new Intent(getContext(), NewLoginActivity.class));
                            } else {
                                startActivity(new Intent(getContext(), FreeWedActivity.class));
                            }
                        }else if("DLKYFP".equals(banners.get(position).getBannerCode())){
                            if (!UserManager.getInstance(getContext()).isLogin()) {
                                startActivity(new Intent(getContext(), NewLoginActivity.class));
                            } else {
                                startActivity(new Intent(getContext(), KeYuanActivity.class));
                            }
                        }
                    }
                }catch (Exception e){
                    Log.e("错误: ",e.toString());
                }
            }
        });

        mTabRecycler = (RecyclerView) view.findViewById(R.id.zixun_tab);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mTabRecycler.setLayoutManager(layoutManager);

        mTabRecycler2 = (RecyclerView) view.findViewById(R.id.zixun_tab_title);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext());
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        mTabRecycler2.setLayoutManager(layoutManager2);

        mTabAdapter = new ZixunTabAdapter(getContext());
        mTabAdapter.setOnItemClickListener(new ZixunTabAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position, ZixunTagBean bean) {
                pageIndex = 1;
                itemCount = 20;
                zixunTabId = bean.getWeddingInformationID();
                getZixun(pageIndex,itemCount,true);
            }
        });
        mTabRecycler.setAdapter(mTabAdapter);
        mTabRecycler2.setAdapter(mTabAdapter);

        mZixunRecycler = (RecyclerView) view.findViewById(R.id.zixun_list);
        LinearLayoutManager zixunlayout = new LinearLayoutManager(getContext());
        mZixunRecycler.setLayoutManager(zixunlayout);
        mZixunRecycler.setNestedScrollingEnabled(false);

        mZixunAdapter = new HomeZixunAdapter(getContext());
        mZixunAdapter.setOnItemClickListener(new HomeZixunAdapter.OnItemClickListener() {
            @Override
            public void onClick(ZixunBean bean) {
                Intent intent = new Intent(getContext(), NewArticleActivity.class);
                intent.putExtra("news", bean.getInformationArticleID());
                startActivity(intent);
            }
        });

        mZixunRecycler.setAdapter(mZixunAdapter);

        mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                Log.e("scrollY:", scrollY + " ===oldScrollY:" + oldScrollY + " ===== now:" + (v.getChildAt(0).getHeight() - v.getHeight()));
                if(scrollY > mTabRecycler.getBottom() && !isTabShow){
                    isTabShow = true;
                    mTabRecycler2.setVisibility(View.VISIBLE);
                    titleLayout.setVisibility(View.GONE);
                }else if(scrollY < mTabRecycler.getBottom() && isTabShow){
                    isTabShow = false;
                    mTabRecycler2.setVisibility(View.GONE);
                    titleLayout.setVisibility(View.VISIBLE);
                }
                if(mZixunAdapter.getItemCount() < totalNum && scrollY == (v.getChildAt(0).getHeight() - v.getHeight())){
                    Log.e("totalNum:", totalNum + " ===== mZixunAdapter.getItemCount():" + mZixunAdapter.getItemCount());
                    pageIndex++;
                    getZixun(pageIndex,itemCount,false);
                }
            }
        });

        //设置刷新时动画的颜色，可以设置4个
        fresh.setColorSchemeResources(R.color.main);
        fresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fresh.post(new Runnable() {
                    @Override
                    public void run() {
                        fresh.setRefreshing(true);
                    }
                });
                refresh();
            }
        });

        if(UserManager.getInstance(getContext()).isLogin()){
            identID = UserManager.getInstance(getContext()).getNewUserInfo().getProfession();
        }else{
            location.setText("青岛");
        }
//        title.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis())));
        String loca = UserManager.getInstance(getContext()).getLocation();
        if(loca != null && !"".equals(loca)){
            String[] info = loca.split(",");
            areaID = info[0];
            location.setText(info[1]);
            Log.e("数据：=========== ",loca);
        }else {
            location.setText("青岛");
        }

        mSimpleCustomPop = new SimpleCustomPop(getContext());

        mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(260);

        mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f);
        mHiddenAction.setDuration(260);

    }

    private void refresh() {
        pageIndex = 1;
        itemCount = 20;
        getIdentity();
        getBanner(3);
        getBanner(5);
//        getBanner(9);
        getAdList();
        getCombos();
        getTabs();
        stopLoad();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refresh();
    }

    @Override
    public void onClick(View v) {
        if(v == locationLayout){//区域选择
            startActivity(new Intent(getContext(), SelectCityActivity.class).putExtra("type", Constants.HOME_SELECT_CITY));
        }else if(v == search){//搜索供应商
            if (UserManager.getInstance(getContext()).checkLogin())
                startActivity(new Intent(getContext(), SearchSupActivity.class));
        }else if(v == img1){//婚礼返还
            if (UserManager.getInstance(getContext()).checkLogin())
                startActivity(new Intent(getContext(), WeddingStoreActivity.class));
        }else if(v == img4){//订婚宴
            if (UserManager.getInstance(getContext()).checkLogin())
                action(homeADList.get(0));
        }else if(v == img5){//免费办婚礼
            if (UserManager.getInstance(getContext()).checkLogin())
                action(homeADList.get(1));
        }else if(v == img6){//婚嫁采购节
            if (UserManager.getInstance(getContext()).checkLogin())
                action(homeADList.get(2));
        }else if(v == img7){//邀请结婚
            if (UserManager.getInstance(getContext()).checkLogin())
                action(homeADList.get(3));
        }else if(v == allProject){//婚礼套餐
            if (UserManager.getInstance(getContext()).checkLogin())
                startActivity(new Intent(getContext(), WeddingTaocan.class));
        }else if(v == allAD){//活动列表
            startActivity(new Intent(getContext(), NewHomeADActivity.class));
        }else if(v == freeProject){//免费出方案
            getCustomState();
        }else if(v == freeWedding){//免费办婚礼
            if (UserManager.getInstance(getContext()).checkLogin())
                startActivity(new Intent(getContext(), FreeWedActivity.class));
        }else if(v == weddingDanbao){//婚礼担保
            if (UserManager.getInstance(getContext()).checkLogin())
                startActivity(new Intent(getContext(), WeddingAssureActivity.class));
        }else if(v == shareProject){//共享方案
            if (UserManager.getInstance(getContext()).checkLogin())
                startActivity(new Intent(getContext(), WedCaseActivity.class));
        }else if(v == homeAdd){//扫一扫、付款
            mSimpleCustomPop.anchorView(homeAdd).gravity(Gravity.BOTTOM).show();
        }
    }
    private void setData(List<NewIdentityBean> data) {
        data.subList(0,10);
        List<List<NewIdentityBean>> lists = new ArrayList<>();
        Collections.addAll(lists, data.subList(0,10), data.subList(10,data.size()));
        mProfessionBanner.setPages (mProfessionView, lists);
        mProfessionBanner.setPointViewVisible(true)//设置指示器是否可见
                .setPageIndicator(new int[]{R.drawable.cbview_selector_2, R.drawable.cbview_selector})   //设置指示器圆点
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL) //设置指示器位置（左、中、右)
                .setManualPageable(true);//设置手动影响（设置可否手动切换）
    }

    private void setBanner(int type, ResultBanner result){
        if(type == 3){
            banners = result.getData();
            mAdBanner.setPages(banners, new MZHolderCreator<BannerViewHolder>() {
                @Override
                public BannerViewHolder createViewHolder() {
                    return new BannerViewHolder();
                }
            });
            mAdBanner.start();
        }else if(type == 5){
            Glide.with(getContext()).load(result.getData().get(0).getBannerURL()).into(img1);
        }
//        else if(type == 9){
//            //HLDB     婚礼担保
//            //MFCFA    免费出方案
//            //MFBC     免费保存视频照片
//            //CHJJM    成婚纪加盟
//            //MFBHL    免费办婚礼(APP)
//            //YQHYJH   邀请好友结婚
//            //DHY      订婚宴
//            //HJCGJ    婚嫁采购节
//            //MFBHL_S  免费办婚礼(小图)
//            for (NewBannerBean bean: result.getData()) {
//                if("YQHYJH".equals(bean.getBannerCode())){
//                    Glide.with(getContext()).load(bean.getBannerURL()).into(img7);
//                }else if("MFBHL_S".equals(bean.getBannerCode())){
//                    Glide.with(getContext()).load(bean.getBannerURL()).into(img5);
//                }else if("HJCGJ".equals(bean.getBannerCode())){
//                    Glide.with(getContext()).load(bean.getBannerURL()).into(img6);
//                }else if("DHY".equals(bean.getBannerCode())){
//                    Glide.with(getContext()).load(bean.getBannerURL()).into(img4);
//                }
//            }
////            if(result.getData().size() > 3){
////                Glide.with(getContext()).load(result.getData().get(1).getBannerURL()).into(img5);
////                Glide.with(getContext()).load(result.getData().get(2).getBannerURL()).into(img6);
////                Glide.with(getContext()).load(result.getData().get(3).getBannerURL()).into(img7);
////            }
//        }
    }

    private void setADData(ResultGetNewAd result){
        if(result.getSortField() != null && !"".equals(result.getSortField())){
            String[] rank = result.getSortField().split(",");
            homeADList = new ArrayList<>();
            for (String code : rank) {
                for (NewAdBean bean:result.getData()) {
                    if(code.equals(bean.getActivityCode())){
                        homeADList.add(bean);
                        break;
                    }
                }
            }
            Glide.with(getContext()).load(homeADList.get(0).getSmallImg()).into(img4);
            Glide.with(getContext()).load(homeADList.get(1).getSmallImg()).into(img5);
            Glide.with(getContext()).load(homeADList.get(2).getSmallImg()).into(img6);
            Glide.with(getContext()).load(homeADList.get(3).getSmallImg()).into(img7);
        }
    }

    private void action(NewAdBean bean){
        if("YQBHL".equals(bean.getActivityCode())){
            if (UserManager.getInstance(getContext()).checkLogin()) {
                getProfit(UserManager.getInstance(getContext()).getNewUserInfo().getUserId());
            }
        }else if("FreeBHL".equals(bean.getActivityCode())){
            startActivity(new Intent(getContext(), FreeWedActivity.class));
        }else if("ShareApp".equals(bean.getActivityCode())){
            startActivity(new Intent(getContext(), ShareAppActivity.class));
        }else if("FreeBMH".equals(bean.getActivityCode())){
            if(!UserManager.getInstance(getContext()).isLogin()){
                startActivity(new Intent(getContext(), NewLoginActivity.class));
            }else{
                startActivity(new Intent(getContext(), BoomActivity.class));
            }
        }else if("HLDB".equals(bean.getActivityCode())){
            startActivity(new Intent(getContext(), WeddingAssureActivity.class));
        }else if("ChuFA".equals(bean.getActivityCode())){
            startActivity(new Intent(getContext(), MakeProjectActivity.class));
        }else if("JDHD".equals(bean.getActivityCode())){
            startActivity(new Intent(getContext(), HotelADActivity.class));
        }else if("HunLiFH".equals(bean.getActivityCode())){
            if(!UserManager.getInstance(getContext()).isLogin()){
                startActivity(new Intent(getContext(), NewLoginActivity.class));
            }else{
                startActivity(new Intent(getContext(), WeddingStoreActivity.class)
                        .putExtra("code",bean.getActivityCode()));
            }
        }else if("BSL".equals(bean.getActivityCode())){
            if(!UserManager.getInstance(getContext()).isLogin()){
                startActivity(new Intent(getContext(), NewLoginActivity.class));
            }else{
                startActivity(new Intent(getContext(), WeddingStoreActivity.class)
                        .putExtra("code",bean.getActivityCode()));
            }
        }else if("DS".equals(bean.getActivityCode())){
            if(!UserManager.getInstance(getContext()).isLogin()){
                startActivity(new Intent(getContext(), NewLoginActivity.class));
            }else{
                startActivity(new Intent(getContext(), WeddingStoreActivity.class)
                        .putExtra("code",bean.getActivityCode()));
            }
        }else if("HJCGJ".equals(bean.getActivityCode())){
            if(!UserManager.getInstance(getContext()).isLogin()){
                startActivity(new Intent(getContext(), NewLoginActivity.class));
            }else{
                startActivity(new Intent(getContext(), CaigoujieActivity.class));
            }
        }else {
            Toast.makeText(getContext(), "参与该活动,请更新版本!", Toast.LENGTH_SHORT).show();
        }
    }


    //==============================================================================================

    /**
     * 获取邀请结婚收益
     */
    public void getProfit(String userID) {
        mDialog.show();
        NetWorkUtil.setCallback("HQOAApi/GetInvitationProfit",
                new BeanUserId(userID), new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultGetInvitationProfit result = new Gson().fromJson(respose, ResultGetInvitationProfit.class);
                            if (result.getMessage().getCode().equals("200")) {
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("profit",result);
                                if (result.getRefereeStatus() == 0){//普通用户
                                    startActivity(new Intent(getContext(), ComInviteMainActivity.class).putExtras(bundle));
                                }else {//Vip
                                    startActivity(new Intent(getContext(), VipInviteMainActivity.class).putExtras(bundle));
                                }
                            }else {
                                Toast.makeText(getContext(), result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            Log.e("",e.toString());
                        }
                    }
                    @Override
                    public void onError(String e) {
                        mDialog.dismiss();
                        Log.e("网络请求错误",e);
                    }
                });
    }

    /**
     * 获取职业
     */
    private void getIdentity(){
        UserManager.getInstance(getContext()).getProfession(2, new UserManager.OnGetIdentListener() {
            @Override
            public void onFinish(ResultNewIdentity result) {
                setData(result.getData());
            }

            @Override
            public void onError(String e) {
                Log.e("出错",e);
            }
        });
    }

    /**
     * 获取Banner
     */
    private void getBanner(final int type){
        NetWorkUtil.setCallback("HQOAApi/GetBannerList",
                new BeanBanner(type,areaID),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultBanner result = new Gson().fromJson(respose, ResultBanner.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                setBanner(type,result);
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
     * 获取婚礼套餐
     */
    private void getCombos(){
        NetWorkUtil.setCallback("HQOAApi/GetWeddingPackageList",
                new BeanGetCombos(1+"",10+"",1),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultGetCombos result = new Gson().fromJson(respose, ResultGetCombos.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                mHomeComboAdapter.refresh(result.getData());
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
                        Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 获取资讯类别
     */
    private void getTabs(){
        NetWorkUtil.setCallback("HQOAApi/GetWeddingInformationList",
                new BeanNone(),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultZixunTag result = new Gson().fromJson(respose, ResultZixunTag.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                mTabAdapter.refresh(result.getData());
                                if(result.getData().size()>0){
                                    zixunTabId = result.getData().get(0).getWeddingInformationID();
                                    getZixun(pageIndex,itemCount,true);
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
                        Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 获取婚礼资讯
     */
    private void getZixun(int pageIndex, int pageCount, final boolean isRefresh){
        NetWorkUtil.setCallback("HQOAApi/GetInformationArticleList",
                new BeanGetZixun(pageIndex,pageCount,zixunTabId,""),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultGetZixun result = new Gson().fromJson(respose, ResultGetZixun.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                totalNum = result.getTotalCount();
                                if(isRefresh){
                                    mZixunAdapter.refresh(result.getData());
                                }else {
                                    mZixunAdapter.addAll(result.getData());
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
                        Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 获取免费出方案状态
     */
    private void getCustomState() {
        if (UserManager.getInstance(getContext()).checkLogin()) {
            NewUserInfo userInfo = UserManager.getInstance(getContext()).getNewUserInfo();
            NetWorkUtil.setCallback("HQOAApi/IsNewPeopleAddCustom",
                    new BeanUserInfo(userInfo.getUserId()),
                    new NetWorkUtil.CallBackListener() {
                        @Override
                        public void onFinish(final String respose) {
                            Log.e("返回值", respose);
                            try {
                                ResultIsNewsAddCustom result = new Gson().fromJson(respose, ResultIsNewsAddCustom.class);
                                if ("200".equals(result.getMessage().getCode())) {
                                    int state = result.getCustomState();
                                    if ("00000000-0000-0000-0000-000000000000".equals(result.getNewPeopleCustomID())) {
                                        //未编辑
                                        startActivity(new Intent(getContext(), MakeProjectActivity.class));
                                    } else {
                                        //已编辑
                                        startActivity(new Intent(getContext(), WedProjectActivity.class)
                                                .putExtra("state", state));
                                    }
                                } else {
                                    Toast.makeText(getContext(), "" + result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Log.e("json解析出错", e.toString());
                                //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(final String e) {
                            Log.e("网络出错", e.toString());
                        }
                    });
        }
    }


    /**
     * 获取活动列表
     */
    private void getAdList() {
        NetWorkUtil.setCallback("HQOAApi/GetWeChatActivityList",
                new BeanNone(),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultGetNewAd result = new Gson().fromJson(respose, ResultGetNewAd.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                setADData(result);
                            } else {
                                Toast.makeText(getContext(), result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(this, "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError(String e) {
                    }
                });

    }

    /**
     * CBView的HolderView
     */
    public class NetworkImageHolderView implements Holder<List<NewIdentityBean>> {
        private MyRecyclerView mRecyclerView;

        @Override
        public View createView(Context context) {
            //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
            View view = LayoutInflater.from(context).inflate(R.layout.item_cbview_layout, null);
            mRecyclerView = (MyRecyclerView) view.findViewById(R.id.my_cbview_recycler);
            return view;
        }

        @Override
        public void UpdateUI(Context context,int position, List<NewIdentityBean> data) {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(context,5);
            mRecyclerView.setLayoutManager(gridLayoutManager);
            MainHeadAdapter adapter = new MainHeadAdapter(context);
            adapter.setOnItemClickListener(new MainHeadAdapter.OnItemClickListener() {
                @Override
                public void onClick(NewIdentityBean bean) {
                    Bundle bundle = new Bundle();
                    bundle.putString("areaID",areaID);
                    bundle.putSerializable("ident",bean);
                    startActivity(new Intent(getContext(), HomeSupActivity.class).putExtras(bundle));
                }
            });
            mRecyclerView.setAdapter(adapter);
            adapter.refresh(data);
        }
    }

    public static class BannerViewHolder implements MZViewHolder<NewBannerBean> {
        private ImageView mImageView;
        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.item_banner_home_layout,null);
            mImageView = (ImageView) view.findViewById(R.id.banner_image);
            return view;
        }

        @Override
        public void onBind(Context context, int position, NewBannerBean data) {
            // 数据绑定
            Glide.with(context)
                    .load(data.getBannerURL())
                    .placeholder(R.color.gray_background)
                    .centerCrop()
                    .dontAnimate()
                    .into(mImageView);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mAdBanner.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(banners != null && banners.size() > 0){
            mAdBanner.start();
        }
    }

    public void stopLoad(){
        if(fresh.isRefreshing()){
            fresh.post(new Runnable() {
                @Override
                public void run() {
                    fresh.setRefreshing(false);
                }
            });
        }
    }

    private void request() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            //申请CALL_PHONE权限
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CALL_REQUEST_CODE);
        } else {
            startActivity(new Intent(getContext(), ScanActivity.class).putExtra("type",1));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode, grantResults);
    }

    private void doNext(int requestCode, int[] grantResults) {
        if (requestCode == CALL_REQUEST_CODE) {
            if (grantResults != null && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivity(new Intent(getContext(), ScanActivity.class).putExtra("type",1));
            } else {
                Toast.makeText(getContext(), "已禁止", Toast.LENGTH_SHORT).show();
            }
        }
    }


    /**
     * 右上角弹窗
     */
    public class SimpleCustomPop extends BasePopup<SimpleCustomPop> implements View.OnClickListener {
        public SimpleCustomPop(Context context) {
            super(context);
        }

        @Override
        public View onCreatePopupView() {
            View mDialogView = View.inflate(getContext(), R.layout.dialog_home_add_layout, null);
            mDialogView.findViewById(R.id.scan).setOnClickListener(this);
            mDialogView.findViewById(R.id.pay).setOnClickListener(this);
            return  mDialogView;
        }

        @Override
        public void setUiBeforShow() {

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.scan:
                    this.dismiss();
                    if (UserManager.getInstance(getContext()).checkLogin())
                        request();
                    break;
                case R.id.pay:
                    this.dismiss();
                    startActivity(new Intent(getContext(), WeChatPayH5Activity.class)
                            .putExtra("url","http://www.chenghunji.com/capital"));
                    break;

            }
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
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
