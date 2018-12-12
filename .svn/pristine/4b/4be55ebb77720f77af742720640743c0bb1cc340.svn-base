package com.dikai.chenghunjiclient.fragment.wedding;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.register.LoginActivity;
import com.dikai.chenghunjiclient.activity.wedding.FreeWedActivity;
import com.dikai.chenghunjiclient.activity.wedding.MakeProjectActivity;
import com.dikai.chenghunjiclient.activity.wedding.WedCaseActivity;
import com.dikai.chenghunjiclient.activity.wedding.WedProjectActivity;
import com.dikai.chenghunjiclient.activity.wedding.WeddingAssureActivity;
import com.dikai.chenghunjiclient.bean.BeanBanner;
import com.dikai.chenghunjiclient.bean.BeanNone;
import com.dikai.chenghunjiclient.bean.BeanPager;
import com.dikai.chenghunjiclient.bean.BeanUserInfo;
import com.dikai.chenghunjiclient.entity.GetWeddingData;
import com.dikai.chenghunjiclient.entity.NewUserInfo;
import com.dikai.chenghunjiclient.entity.ResultBanner;
import com.dikai.chenghunjiclient.entity.ResultIsNewsAddCustom;
import com.dikai.chenghunjiclient.entity.UserInfo;
import com.dikai.chenghunjiclient.entity.WeddingImageData;
import com.dikai.chenghunjiclient.fragment.BaseFragmentPagerAdapter;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.OldNetWorkUtil;
import com.dikai.chenghunjiclient.util.ScreenUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.JudgeNestedScrollView;
import com.dikai.chenghunjiclient.view.MyImageView;
import com.dikai.chenghunjiclient.view.MyViewPager;
import com.dikai.chenghunjiclient.view.SwipeLayout;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cmk03 on 2018/5/16.
 */

public class NewWeddingFragment2 extends Fragment implements View.OnClickListener, OnRefreshListener, OnLoadMoreListener, AppBarLayout.OnOffsetChangedListener {

    private MyImageView caseImage;
    private boolean HasAddCustom = false;
    private boolean hasGetState = false;
    private int pageIndex = 1;
    private int pageCount = 20;
    private long weddingInformationID = 0;
    private String title = "";
    private List<GetWeddingData.DataList> weddingDataList;
    private int state;
    private TabLayout mTabLayout;
    private List<Fragment> fragments;
    private List<String> tab;
    private BaseFragmentPagerAdapter adapter;
    private Toolbar mToolBar;
    private TextView tvTitle;
    private WeddingNoteFragment noteFragment;
    private ViewPager mViewPager;
    //private SwipeLayout mSwipeLayout;
    private AppBarLayout appbarLayout;
    private String areaID;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_wedding3, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mToolBar = (Toolbar) view.findViewById(R.id.toolbar);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        //mSwipeLayout = (SwipeLayout) view.findViewById(R.id.swipe_layout);
        //CoordinatorLayout swipeTarget = (CoordinatorLayout) view.findViewById(R.id.swipe_target);
        appbarLayout = (AppBarLayout) view.findViewById(R.id.appbar_layout);
        mTabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        caseImage = (MyImageView) view.findViewById(R.id.item_case_img);
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);

       // mSwipeLayout.setRefreshEnabled(false);
       // mSwipeLayout.setOnRefreshListener(this);
       // mSwipeLayout.setOnLoadMoreListener(this);
        appbarLayout.addOnOffsetChangedListener(this);
        view.findViewById(R.id.ll_free_project).setOnClickListener(this);
        view.findViewById(R.id.ll_free_wedding).setOnClickListener(this);
        view.findViewById(R.id.ll_wedding_danbao).setOnClickListener(this);
        view.findViewById(R.id.ll_share_project).setOnClickListener(this);

        mTabLayout.setupWithViewPager(mViewPager);
        fragments = new ArrayList<>();
        tab = new ArrayList<>();
        adapter = new BaseFragmentPagerAdapter(getChildFragmentManager(), fragments, tab);
        mViewPager.setAdapter(adapter);

        String loca = UserManager.getInstance(getContext()).getLocation();
        if (loca != null && !"".equals(loca)) {
            String[] info = loca.split(",");
            areaID = info[0];
        } else {
            areaID = "1740";
        }

        initWeddingId();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getCustomState(false);
        getBanner(8);
    }
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onRefresh() {

    }


    public static boolean isVisBottom(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        //屏幕中最后一个可见子项的position
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        //当前屏幕所看到的子项个数
        int visibleItemCount = layoutManager.getChildCount();
        //当前RecyclerView的所有子项个数
        int totalItemCount = layoutManager.getItemCount();
        //RecyclerView的滑动状态
        int state = recyclerView.getScrollState();
        if (visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1 && state == recyclerView.SCROLL_STATE_IDLE) {
            return true;
        } else {
            return false;
        }
    }

    private void initWeddingId() {
//        NetWorkUtil.setCallback("HQOAApi/GetInformationArticleList", new BeanNone(), new NetWorkUtil.CallBackListener() {
//            @RequiresApi(api = Build.VERSION_CODES.M)
//            @Override
//            public void onFinish(String respose) {
//                try {
//                    GetWeddingData weddingData = new Gson().fromJson(respose, GetWeddingData.class);
//                    if (weddingData.getMessage().getCode().equals("200")) {
//                        weddingDataList = weddingData.getData();
//                        weddingDataList.add(0, new GetWeddingData.DataList(0, "婚礼", ""));
//                        for (GetWeddingData.DataList dataList : weddingDataList) {
//                            mTabLayout.addTab(mTabLayout.newTab().setTag(dataList.getTitle()));
//                            noteFragment = WeddingNoteFragment.newInstance(dataList.getWeddingInformationID());
//                            fragments.add(noteFragment);
//                            tab.add(dataList.getTitle());
//                            adapter.notifyDataSetChanged();
//                        }
//                        mViewPager.setOffscreenPageLimit(weddingDataList.size());
//                    }
//                } catch (Exception e) {
//                    Log.e("", e.toString());
//                }
//            }
//
//            @Override
//            public void onError(String e) {
//
//            }
//        });
    }


    /**
     * 获取Banner
     */
    private void getBanner(final int type) {
        NetWorkUtil.setCallback("HQOAApi/GetBannerList",
                new BeanBanner(type, areaID),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值", respose);
                        try {
                            ResultBanner result = new Gson().fromJson(respose, ResultBanner.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                Glide.with(getContext()).load(result.getData().get(0).getBannerURL()).into(caseImage);
                            } else {
                                Toast.makeText(getContext(), result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错", e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
//                        Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getCustomState(final boolean isRetry) {
        if (UserManager.getInstance(getContext()).isLogin()) {
            NewUserInfo newUserInfo = UserManager.getInstance(getContext()).getNewUserInfo();
            NetWorkUtil.setCallback("HQOAApi/IsNewPeopleAddCustom",
                    new BeanUserInfo(newUserInfo.getUserId()),
                    new NetWorkUtil.CallBackListener() {
                        @Override
                        public void onFinish(final String respose) {
                            Log.e("返回值", respose);
                            try {
                                ResultIsNewsAddCustom result = new Gson().fromJson(respose, ResultIsNewsAddCustom.class);
                                if ("200".equals(result.getMessage().getCode())) {
                                    state = result.getCustomState();
                                    hasGetState = true;
                                    if ("0".equals(result.getNewPeopleCustomID())) {
                                        //未编辑
                                        HasAddCustom = false;
                                        if (isRetry)
                                            startActivity(new Intent(getContext(), MakeProjectActivity.class));
                                    } else {
                                        //已编辑
                                        HasAddCustom = true;
                                        if (isRetry) {
                                            startActivity(new Intent(getContext(), WedProjectActivity.class)
                                                    .putExtra("state", state));
                                        }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_free_project: // 免费出方案
                if (!UserManager.getInstance(getContext()).isLogin()) {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                } else if (hasGetState) {
                    startActivity(HasAddCustom ? new Intent(getContext(), WedProjectActivity.class).putExtra("state", state) :
                            new Intent(getContext(), MakeProjectActivity.class));
                } else {
                    getCustomState(true);
                }
                break;
            case R.id.ll_free_wedding: // 免费办婚礼
                if (!UserManager.getInstance(getContext()).isLogin()) {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                } else {
//                    startActivity(new Intent(getContext(), InviteActivity1.class));
                    startActivity(new Intent(getContext(), FreeWedActivity.class));
                }
                break;
            case R.id.ll_wedding_danbao: // 婚礼担保
                if (!UserManager.getInstance(getContext()).isLogin()) {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                } else {
                    startActivity(new Intent(getContext(), WeddingAssureActivity.class));
//                startActivity(new Intent(getContext(), GuaranteeActivity.class));
                }
                break;
            case R.id.ll_share_project:
                //TODO:方案商店
                startActivity(new Intent(getContext(), WedCaseActivity.class));
                break;
            default:
                break;
        }
    }
}