package com.dikai.chenghunjiclient.fragment.keyuan;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.newregister.NewLoginActivity;
import com.dikai.chenghunjiclient.adapter.SimplePicAdapter;
import com.dikai.chenghunjiclient.adapter.ad.KeYuanAdapter;
import com.dikai.chenghunjiclient.adapter.ad.TypeSelectAdapter;
import com.dikai.chenghunjiclient.adapter.me.KeyuanStateAdapter;
import com.dikai.chenghunjiclient.bean.BeanGetKeyuan;
import com.dikai.chenghunjiclient.bean.BeanPublishKeYuan;
import com.dikai.chenghunjiclient.citypicker.SelectCityActivity;
import com.dikai.chenghunjiclient.entity.NewIdentityBean;
import com.dikai.chenghunjiclient.entity.ResultGetKeyuan;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.entity.ResultNewIdentity;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.google.gson.Gson;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import dmax.dialog.SpotsDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class KeYuanFragment extends Fragment implements View.OnClickListener{
    
    private RecyclerView mSelectRecycler;
    private TypeSelectAdapter mSelectAdapter;
    private RecyclerView mStateRecycler;
    private KeyuanStateAdapter mStateAdapter;
    private RelativeLayout typeLayout;
    private TranslateAnimation mShowAction;
    private TranslateAnimation mHiddenAction;
    private TextView typeText;
    private TextView typeText2;
    private TextView areaText;
    private TextView areaText2;
    private TextView stateText;
    private TextView stateText2;
    private int statePosition;
    private boolean isShowIden = false;
    private NewIdentityBean nowIdentity;
    private LinearLayout topLayout;

    private int pageIndex = 1;
    private int itemCount = 20;
    private RecyclerView sourceRecycler;
    private KeYuanAdapter mKeYuanAdapter;

    private SimplePicAdapter picAdapter;
    private RecyclerView pics;
    private NestedScrollView mScrollView;
    private LinearLayout titleLayout;
    private AppBarLayout titleLayout2;
    private LinearLayout barLayout;
    private boolean isTabShow = false;
    private int totalNum;
    private ResultNewIdentity mIdentityData;
    private SwipeRefreshLayout fresh;
    private SpotsDialog mDialog;
    private String areaID = "1740";
    private int sortField;
    private int sort;

    public KeYuanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        EventBus.getDefault().register(this);
        return inflater.inflate(R.layout.fragment_ke_yuan, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        initDialog();
    }

    private void init(View view) {
        mDialog = new SpotsDialog(getContext(),R.style.DialogCustom);
        nowIdentity = new NewIdentityBean("00000000-0000-0000-0000-000000000000","全部需求");
        view.findViewById(R.id.back).setOnClickListener(this);
        view.findViewById(R.id.bg_view).setOnClickListener(this);
        view.findViewById(R.id.close).setOnClickListener(this);
        view.findViewById(R.id.publish).setOnClickListener(this);
        typeLayout = (RelativeLayout) view.findViewById(R.id.type_layout);
        typeText = (TextView)view.findViewById(R.id.type_text);
        typeText2 = (TextView)view.findViewById(R.id.type_text2);
        areaText = (TextView)view.findViewById(R.id.area_text);
        areaText2 = (TextView)view.findViewById(R.id.area_text2);
        stateText = (TextView)view.findViewById(R.id.state_text);
        stateText2 = (TextView)view.findViewById(R.id.state_text2);
        fresh = (SwipeRefreshLayout) view.findViewById(R.id.my_load_recycler_fresh);

        pics = (RecyclerView) view.findViewById(R.id.pics);
        picAdapter = new SimplePicAdapter(getContext());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        pics.setNestedScrollingEnabled(false);
        pics.setLayoutManager(mLayoutManager);
        pics.setAdapter(picAdapter);

        topLayout = (LinearLayout) view.findViewById(R.id.top_layout);
        mSelectRecycler = (RecyclerView) view.findViewById(R.id.type_recycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        mSelectRecycler.setLayoutManager(gridLayoutManager);
        mSelectAdapter = new TypeSelectAdapter(getContext());
        mSelectAdapter.setOnItemClickListener(new TypeSelectAdapter.OnItemClickListener() {
            @Override
            public void onClick(NewIdentityBean bean) {
                hidden();
                if(!nowIdentity.getOccupationID().equals(bean.getOccupationID())){
                    typeText.setText(bean.getOccupationName());
                    typeText2.setText(bean.getOccupationName());
                    nowIdentity = bean;
                    refresh();
                }
            }
        });
        mSelectRecycler.setAdapter(mSelectAdapter);
        mStateRecycler = (RecyclerView) view.findViewById(R.id.state_recycler);
        mStateRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mStateAdapter = new KeyuanStateAdapter(getContext());
        mStateAdapter.setOnItemClickListener(new KeyuanStateAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position, String bean) {
                if(statePosition != position){
                    stateText.setText(bean);
                    stateText2.setText(bean);
                    statePosition = position;
                    if(position == 0){
                        sortField = 0;
                        sort = 0;
                    }else if(position == 1){
                        sortField = 0;
                        sort = 1;
                    }else if(position == 2){
                        sortField = 0;
                        sort = 0;
                    }else if(position == 3){
                        sortField = 1;
                        sort = 0;
                    }else if(position == 4){
                        sortField = 1;
                        sort = 1;
                    }
                    refresh();
                }
                hidden();
            }
        });

        mStateRecycler.setAdapter(mStateAdapter);
        List<String> temp = new ArrayList<>();
        Collections.addAll(temp,"综合排序","发布时间 远→近","发布时间 近→远","结婚日期 远→近","结婚日期 近→远");
        mStateAdapter.refresh(temp);

        sourceRecycler = (RecyclerView) view.findViewById(R.id.source);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        sourceRecycler.setLayoutManager(linearLayoutManager);
        sourceRecycler.setNestedScrollingEnabled(false);
        mKeYuanAdapter = new KeYuanAdapter(getContext());
        sourceRecycler.setAdapter(mKeYuanAdapter);

        barLayout = (LinearLayout) view.findViewById(R.id.bar_layout);
        titleLayout = (LinearLayout) view.findViewById(R.id.title_layout);
        titleLayout2 = (AppBarLayout) view.findViewById(R.id.title_layout2);
        mScrollView = (NestedScrollView) view.findViewById(R.id.scrollview);
        mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                Log.e("scrollY:", scrollY + " ===oldScrollY:" + oldScrollY + " ===== now:" + (v.getChildAt(0).getHeight() - v.getHeight()));
                if(scrollY > titleLayout.getBottom() && !isTabShow){
                    isTabShow = true;
                    titleLayout2.setVisibility(View.VISIBLE);
                    barLayout.setVisibility(View.GONE);
                }else if(scrollY < titleLayout.getBottom() && isTabShow){
                    isTabShow = false;
                    titleLayout2.setVisibility(View.GONE);
                    barLayout.setVisibility(View.VISIBLE);
                }
                if(mKeYuanAdapter.getItemCount() < totalNum && scrollY == (v.getChildAt(0).getHeight() - v.getHeight())){
                    Log.e("totalNum:", totalNum + " ===== mZixunAdapter.getItemCount():" + mKeYuanAdapter.getItemCount());
                    pageIndex++;
                    getKeyuan(pageIndex,itemCount,false);
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
        areaText.setOnClickListener(this);
        areaText2.setOnClickListener(this);
        typeText.setOnClickListener(this);
        typeText2.setOnClickListener(this);
        stateText.setOnClickListener(this);
        stateText2.setOnClickListener(this);
    }

    private void initDialog() {
        mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(260);

        mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f);
        mHiddenAction.setDuration(260);
        mHiddenAction.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                typeLayout.setVisibility(View.GONE);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        String loca = UserManager.getInstance(getContext()).getLocation();
        if(loca != null && !"".equals(loca)){
            String[] info = loca.split(",");
            areaID = info[0];
            areaText.setText(info[1]);
            areaText2.setText(info[1]);
            Log.e("数据：=========== ",loca);
        }
        refresh();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refresh();
    }

    private void refresh() {
        if(mIdentityData == null){
            getIdentity();
        }
        pageIndex = 1;
        itemCount = 20;
        getKeyuan(pageIndex,itemCount,true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.area_text:
            case R.id.area_text2:
                startActivity(new Intent(getContext(), SelectCityActivity.class).putExtra("type", Constants.SELECT_KEYUAN_AREA));
                break;
            case R.id.type_text:
            case R.id.type_text2:
                if(isShowIden){
                    if(mSelectRecycler.getVisibility() == View.VISIBLE){
                        hidden();
                    }else {
                        mSelectRecycler.setVisibility(View.VISIBLE);
                        mStateRecycler.setVisibility(View.GONE);
                    }
                }else {
                    mSelectRecycler.setVisibility(View.VISIBLE);
                    mStateRecycler.setVisibility(View.GONE);
                    show();
                }
                break;
            case R.id.state_text:
            case R.id.state_text2:
                if(isShowIden){
                    if(mStateRecycler.getVisibility() == View.VISIBLE){
                        hidden();
                    }else {
                        mStateRecycler.setVisibility(View.VISIBLE);
                        mSelectRecycler.setVisibility(View.GONE);
                    }
                }else {
                    mStateRecycler.setVisibility(View.VISIBLE);
                    mSelectRecycler.setVisibility(View.GONE);
                    show();
                }
                break;
            case R.id.close:
            case R.id.bg_view:
                hidden();
                break;
            case R.id.publish:
                if(UserManager.getInstance(getContext()).isLogin()){
                    publish();
                }else {
                    startActivity(new Intent(getContext(), NewLoginActivity.class));
                }
                break;
        }
    }

    private void show(){
        isShowIden = true;
        typeLayout.setVisibility(View.VISIBLE);
        topLayout.clearAnimation();
        topLayout.setVisibility(View.VISIBLE);
        topLayout.startAnimation(mShowAction);
    }

    private void hidden(){
        isShowIden = false;
        topLayout.clearAnimation();
        topLayout.setVisibility(View.GONE);
        topLayout.startAnimation(mHiddenAction);
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

    /**
     * 获取职业
     */
    private void getIdentity(){
        UserManager.getInstance(getContext()).getProfession(4, new UserManager.OnGetIdentListener() {
            @Override
            public void onFinish(ResultNewIdentity result) {
                List<NewIdentityBean> list = new ArrayList<>();
                list.add(new NewIdentityBean("00000000-0000-0000-0000-000000000000","全部需求"));
                list.addAll(result.getData());
                mIdentityData = result;
                mSelectAdapter.refresh(list);
            }

            @Override
            public void onError(String e) {
                Log.e("出错",e);
            }
        });
    }

    /**
     * 获取客源
     */
    private void getKeyuan(int pageIndex, int pageCount, final boolean isRefresh){
        NetWorkUtil.setCallback("HQOAApi/GetJSJTableList",
                new BeanGetKeyuan(nowIdentity.getOccupationID(),sortField,sort,pageIndex,pageCount,0,1,areaID),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        stopLoad();
                        try {
                            ResultGetKeyuan result = new Gson().fromJson(respose, ResultGetKeyuan.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                totalNum = result.getTotalCount();
                                if(isRefresh){
                                    picAdapter.refresh(Arrays.asList(result.getImg().split(",")));
                                    mKeYuanAdapter.refresh(result.getData());
                                }else {
                                    mKeYuanAdapter.addAll(result.getData());
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
                        stopLoad();
                        Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 提交
     */
    private void publish(){
        mDialog.show();
        NetWorkUtil.setCallback("HQOAApi/CreateAgentApplyTable",
                new BeanPublishKeYuan(UserManager.getInstance(getContext()).getNewUserInfo().getUserId(),""),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                Toast.makeText(getContext(), "提交成功！", Toast.LENGTH_SHORT).show();
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
                        mDialog.dismiss();
                        Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 事件总线刷新
     * @param bean
     */
    @Subscribe
    public void onEventMainThread(final EventBusBean bean) {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                if(bean.getType() == Constants.SELECT_KEYUAN_AREA){
                    areaID = bean.getCountry().getRegionId();
                    areaText.setText(bean.getCountry().getRegionName());
                    areaText2.setText(bean.getCountry().getRegionName());
                    refresh();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
