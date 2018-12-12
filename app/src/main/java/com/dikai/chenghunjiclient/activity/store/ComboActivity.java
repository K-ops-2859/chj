package com.dikai.chenghunjiclient.activity.store;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.PhotoActivity;
import com.dikai.chenghunjiclient.activity.discover.DynamicActivity;
import com.dikai.chenghunjiclient.activity.wedding.TaocanDescActivity;
import com.dikai.chenghunjiclient.activity.wedding.TaocanPhotoActivity;
import com.dikai.chenghunjiclient.adapter.store.ComboAreaAdapter;
import com.dikai.chenghunjiclient.adapter.store.ComboCommentAdapter;
import com.dikai.chenghunjiclient.bean.BeanBookCombo;
import com.dikai.chenghunjiclient.bean.BeanCancelCollect;
import com.dikai.chenghunjiclient.bean.BeanCollect;
import com.dikai.chenghunjiclient.bean.BeanGetComboArea;
import com.dikai.chenghunjiclient.bean.BeanGetComboInfo;
import com.dikai.chenghunjiclient.bean.BeanID;
import com.dikai.chenghunjiclient.entity.AreaImgBean;
import com.dikai.chenghunjiclient.entity.ComboAreaBean;
import com.dikai.chenghunjiclient.entity.ComboCommentBean;
import com.dikai.chenghunjiclient.entity.ComboVideoBean;
import com.dikai.chenghunjiclient.entity.ResultGetComboArea;
import com.dikai.chenghunjiclient.entity.ResultGetComboComment;
import com.dikai.chenghunjiclient.entity.ResultGetComboInfo;
import com.dikai.chenghunjiclient.entity.ResultGetComboVideo;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.DensityUtil;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.video.LandLayoutVideo;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;
import com.flyco.dialog.widget.base.BaseDialog;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.GSYVideoProgressListener;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.lujun.androidtagview.TagContainerLayout;
import dmax.dialog.SpotsDialog;

public class ComboActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean isPlay;
    private boolean isPause;
    private OrientationUtils orientationUtils;
    private GSYVideoOptionBuilder gsyVideoOption;
    private LandLayoutVideo detailPlayer;
    private ImageView coverImageView;
    private SwipeRefreshLayout fresh;
    private NestedScrollView mScrollView;
//    private View titleBg;
//    private ImageView mBack;
//    private ImageView mShare;
    private LinearLayout videoLayout;
    private ImageView headImg;
    private ImageView hotelImg;
    private TextView headNumber;
    private TextView commentNum;
    private TextView headName;
    private TextView price1;
    private TextView price2;
    private TextView headInfo;
    private TagContainerLayout tags;
    private RecyclerView areaRecycler;
    private RecyclerView commentRecycler;
    private ComboAreaAdapter mAreaAdapter;
    private ComboCommentAdapter mCommentAdapter;
    private Intent intent;
    private String comboID;
    private int CALL_REQUEST_CODE = 101;
    private ServiceDialog mCallDialog;
    private SpotsDialog mDialog;
    private int nowPosition;
    private String commentID;
    private MaterialDialog deleteDialog;
    private ResultGetComboInfo mComboInfo;
    private Toolbar toolbar;
    private int picNum;
    private int videoNum;
    private ResultGetComboVideo mComboVideo;
    private ImageView collectImg;
    private boolean isCollect;
    private TextView moreInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combo);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setTitle("");
        EventBus.getDefault().register(this);
        ImmersionBar.with(this).titleBar(toolbar).init();
        init();
    }

    private void init() {
        comboID = getIntent().getStringExtra("id");
        mDialog = new SpotsDialog(this,R.style.DialogCustom);
        detailPlayer = (LandLayoutVideo) findViewById(R.id.video_layout);
        fresh = (SwipeRefreshLayout) findViewById(R.id.my_load_recycler_fresh);
        mScrollView = (NestedScrollView) findViewById(R.id.my_scroll);
//        titleBg = findViewById(R.id.title_bg);
//        mBack = (ImageView) findViewById(R.id.ic_app_back);
//        mShare = (ImageView) findViewById(R.id.share);
        moreInfo = (TextView) findViewById(R.id.more_info);
        videoLayout = (LinearLayout) findViewById(R.id.video_parent_layout);
        headImg = (ImageView) findViewById(R.id.head_img);
        collectImg = (ImageView) findViewById(R.id.collect_img);
        hotelImg = (ImageView) findViewById(R.id.hotel_info);
        headNumber = (TextView) findViewById(R.id.head_number);
        headName = (TextView) findViewById(R.id.name);
        commentNum = (TextView) findViewById(R.id.comment_num);
        price1 = (TextView) findViewById(R.id.price1);
        price2 = (TextView) findViewById(R.id.price2);
        headInfo = (TextView) findViewById(R.id.head_info);
        tags = (TagContainerLayout) findViewById(R.id.tag_layout);
        areaRecycler = (RecyclerView) findViewById(R.id.area_recycler);
        commentRecycler = (RecyclerView) findViewById(R.id.comment_recycler);
//        mBack.setOnClickListener(this);
//        mShare.setOnClickListener(this);
        moreInfo.setOnClickListener(this);
        findViewById(R.id.head_videos).setOnClickListener(this);
        findViewById(R.id.head_photos).setOnClickListener(this);
        findViewById(R.id.wuliao).setOnClickListener(this);
        findViewById(R.id.all_videos).setOnClickListener(this);
        findViewById(R.id.all_pics).setOnClickListener(this);
        findViewById(R.id.comment_layout).setOnClickListener(this);
        findViewById(R.id.call_layout).setOnClickListener(this);
        findViewById(R.id.book).setOnClickListener(this);
        collectImg.setOnClickListener(this);
        mAreaAdapter = new ComboAreaAdapter(this);
        mAreaAdapter.setOnItemClickListener(new ComboAreaAdapter.OnItemClickListener() {
            @Override
            public void onClick(int type, int position, ComboAreaBean bean) {
                ArrayList<String> temp = new ArrayList<>();
                for (AreaImgBean imgBeam:bean.getImageData()) {
                    temp.add(imgBeam.getImage());
                }
                Intent intent = new Intent(ComboActivity.this, PhotoActivity.class);
                if(type == 0){
                    intent.putExtra("now", 0);
                }else if(type == 1){
                    intent.putExtra("now", 1);
                }else if(type == 2){
                    intent.putExtra("now", 2);
                }else if(type == 3){
                    intent.putExtra("now", 0);
                }
                intent.putStringArrayListExtra("imgs", temp);
                startActivity(intent);
            }
        });
        areaRecycler.setAdapter(mAreaAdapter);
        mCommentAdapter = new ComboCommentAdapter(this);
        mCommentAdapter.setOnItemClickListener(new ComboCommentAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position, ComboCommentBean bean) {
                nowPosition = position;
                commentID = bean.getId();
                deleteDialog.show();
            }
        });
        commentRecycler.setAdapter(mCommentAdapter);
        commentRecycler.setNestedScrollingEnabled(false);
        areaRecycler.setNestedScrollingEnabled(false);
        headInfo.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Layout layout = headInfo.getLayout();
                if (layout != null) {
                    int lines = layout.getLineCount();
                    if (layout.getEllipsisCount(lines - 1) > 0) { //有省略
                        moreInfo.setVisibility(View.VISIBLE);
                    }else {
                        moreInfo.setVisibility(View.GONE);
                    }
                }
            }
        });
        initDialog();
        initfresh();
        initScroll();
        initVideo();
        refresh();
    }

    private void refresh() {
        getInfo(comboID);
        getArea(comboID);
        getVideo(comboID);
        getComment(comboID);
        stopLoad();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.head_videos:
                //视频：
                if(mComboVideo!=null){
                    Bundle bundle1 = new Bundle();
                    bundle1.putSerializable("bean",mComboVideo);
                    startActivity(new Intent(this, ComboVideoActivity.class).putExtras(bundle1));
                }
                break;
            case R.id.head_photos:
                //照片：
                if(mComboInfo!=null){
                    Intent intent = new Intent(this, TaocanPhotoActivity.class);
                    intent.putExtra("id", comboID);
                    startActivity(intent);
                }
                break;
            case R.id.wuliao:
                startActivity(new Intent(this,PropActivity.class).putExtra("info",mComboInfo.getDetailedList()));
                break;
            case R.id.all_videos:
                //视频：
                if(mComboVideo!=null){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("bean",mComboVideo);
                    startActivity(new Intent(this, ComboVideoActivity.class).putExtras(bundle));
                }
                break;
            case R.id.all_pics:
                //照片：
                if(mComboInfo!=null){
                    startActivity(new Intent(this, TaocanPhotoActivity.class).putExtra("id", comboID));
                }
                break;
            case R.id.comment_layout:
                if(mComboInfo!=null){
                    startActivity(new Intent(this,ComboCommentActivity.class).putExtra("id",comboID));
                }
                break;
            case R.id.collect_img:
                collect(isCollect?"1":"0");
                break;
            case R.id.call_layout:
                mCallDialog.show();
                break;
            case R.id.book:
                book(comboID);
                break;
            case R.id.more_info:
                startActivity(new Intent(this,TaocanDescActivity.class).putExtra("info",mComboInfo.getBriefIntroduction()));
                break;
        }
    }

    private void setHeadDate(ResultGetComboInfo result){
        mComboInfo = result;
        headName.setText(result.getName());
        price1.setText(result.getPresentPrice());
        price2.setText(result.getOriginalPrice());
        price2.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
        headInfo.setText(result.getBriefIntroduction());
        tags.setTags(Arrays.asList(result.getLabel().split(",")));
        Glide.with(ComboActivity.this).load(result.getCoverMap()).into(headImg);
        Glide.with(ComboActivity.this).load(result.getHotelImage()).into(hotelImg);
    }

    //================================

    private void getInfo(String comboId) {
        //  dynamicID = String.valueOf(data.getDynamicID());
        NetWorkUtil.setCallback("HQOAApi/GetWeddingPackageInfo",
                new BeanGetComboInfo(0,comboId), new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(String respose) {
                        try {
                            Log.e("返回值", respose);
                            ResultGetComboInfo result = new Gson().fromJson(respose, ResultGetComboInfo.class);
                            if (result.getMessage().getCode().equals("200")) {
                                setHeadDate(result);
                            }else {
                                Toast.makeText(ComboActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("", e.toString());
                        }
                    }

                    @Override
                    public void onError(String e) {

                    }
                });
    }

    private void getArea(String comboId) {
        //  dynamicID = String.valueOf(data.getDynamicID());
        NetWorkUtil.setCallback("HQOAApi/GetWeddingPackageAreaImg",
                new BeanGetComboArea(comboId,"0"), new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(String respose) {
                        try {
                            Log.e("返回值", respose);
                            ResultGetComboArea result = new Gson().fromJson(respose, ResultGetComboArea.class);
                            if (result.getMessage().getCode().equals("200")) {
                                mAreaAdapter.refresh(result.getData());
                                picNum = result.getImgCount();
                                headNumber.setText(picNum + "图片 · "+ videoNum +"视频");
                            }else {
                                Toast.makeText(ComboActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("", e.toString());
                        }
                    }

                    @Override
                    public void onError(String e) {

                    }
                });
    }

    private void getComment(String comboId) {
        //  dynamicID = String.valueOf(data.getDynamicID());
        NetWorkUtil.setCallback("HQOAApi/GetWeddingPackageEvaluateList",
                new BeanID(comboId), new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(String respose) {
                        try {
                            Log.e("返回值", respose);
                            ResultGetComboComment result = new Gson().fromJson(respose, ResultGetComboComment.class);
                            if (result.getMessage().getCode().equals("200")) {
                                commentNum.setText("("+result.getData().size()+")");
                                mCommentAdapter.refresh(result.getData());
                            }else {
                                Toast.makeText(ComboActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("", e.toString());
                        }
                    }

                    @Override
                    public void onError(String e) {

                    }
                });
    }

    private void getVideo(String comboId) {
        //  dynamicID = String.valueOf(data.getDynamicID());
        NetWorkUtil.setCallback("HQOAApi/GetWeddingPackageVideoCaseList",
                new BeanID(comboId), new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(String respose) {
                        try {
                            Log.e("返回值", respose);
                            ResultGetComboVideo result = new Gson().fromJson(respose, ResultGetComboVideo.class);
                            if (result.getMessage().getCode().equals("200")) {
                                if(result.getData().size() > 0){
                                    mComboVideo = result;
                                    videoNum = result.getData().size();
                                    headNumber.setText(picNum + "图片 · "+ videoNum +"视频");
                                    if(result.getData().size() == 0){
                                        videoLayout.setVisibility(View.GONE);
                                    }else {
                                        videoLayout.setVisibility(View.VISIBLE);
                                        ComboVideoBean bean = result.getData().get(0);
                                        Glide.with(ComboActivity.this).load(bean.getCoverMap()).into(coverImageView);
                                        gsyVideoOption
                                                .setThumbImageView(coverImageView)
                                                .setUrl(bean.getVideoId())
                                                .setVideoTitle("")
                                                .build(detailPlayer);
                                    }
                                }
                            }else {
                                Toast.makeText(ComboActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("", e.toString());
                        }
                    }

                    @Override
                    public void onError(String e) {

                    }
                });
    }

    /**
     * 预定套餐
     */
    private void book(String comboId) {
        mDialog.show();
        String userID = UserManager.getInstance(this).getNewUserInfo().getUserId();
        NetWorkUtil.setCallback("HQOAApi/CreateWeddingPackageReserve",
                new BeanBookCombo(userID,comboId), new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(String respose) {
                        try {
                            mDialog.dismiss();
                            Log.e("返回值", respose);
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if (result.getMessage().getCode().equals("200")) {
                                Toast.makeText(ComboActivity.this, "预订成功！", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(ComboActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("", e.toString());
                        }
                    }

                    @Override
                    public void onError(String e) {
                        mDialog.dismiss();
                    }
                });
    }

    /**
     * 删除评论
     */
    private void deleteCommet(final int positon,String commentID) {
        mDialog.show();
        NetWorkUtil.setCallback("HQOAApi/DeleteWeddingPackageEvaluate",
                new BeanID(commentID), new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(String respose) {
                        try {
                            mDialog.dismiss();
                            Log.e("返回值", respose);
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if (result.getMessage().getCode().equals("200")) {
                                mCommentAdapter.delete(positon);
                            }else {
                                Toast.makeText(ComboActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("", e.toString());
                        }
                    }

                    @Override
                    public void onError(String e) {
                        mDialog.dismiss();
                    }
                });
    }

    /**
     * 收藏
     * type 0：添加 1：删除
     */
    private void collect(final String type){
        mDialog.show();
        if("0".equals(type)){
            NetWorkUtil.setCallback("HQOAApi/CreateUserCollection",
                    new BeanCollect(UserManager.getInstance(this).getNewUserInfo().getUserId(), comboID, "3"),
                    new NetWorkUtil.CallBackListener() {
                        @Override
                        public void onFinish(final String respose) {
                            Log.e("返回值",respose);
                            mDialog.dismiss();
                            try {
                                ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                                if ("200".equals(result.getMessage().getCode())) {
                                    isCollect = true;
                                    collectImg.setImageResource(R.mipmap.ic_app_white_collect_1);
                                } else {
                                    Toast.makeText(ComboActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Log.e("json解析出错",e.toString());
                                //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(final String e) {
                            mDialog.dismiss();
                            Toast.makeText(ComboActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    });
        }else {
            NetWorkUtil.setCallback("HQOAApi/DeleteUserCollection",
                    new BeanCancelCollect(UserManager.getInstance(this).getNewUserInfo().getUserId(), comboID),
                    new NetWorkUtil.CallBackListener() {
                        @Override
                        public void onFinish(final String respose) {
                            Log.e("返回值",respose);
                            mDialog.dismiss();
                            try {
                                ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                                if ("200".equals(result.getMessage().getCode())) {
                                    isCollect = false;
                                    collectImg.setImageResource(R.mipmap.ic_app_white_collect_2);
                                } else {
                                    Toast.makeText(ComboActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Log.e("json解析出错",e.toString());
                                //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(final String e) {
                            mDialog.dismiss();
                            Toast.makeText(ComboActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    //================================

    private void initDialog() {
        mCallDialog = new ServiceDialog(this);
        mCallDialog.widthScale(1);
        mCallDialog.heightScale(1);

        deleteDialog = new MaterialDialog(this);
        deleteDialog.isTitleShow(false)//
                .btnNum(2)
                .content("是否删除此条评论？")//
                .btnText("取消", "确认")
                .setOnBtnClickL(new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        deleteDialog.dismiss();
                    }
                }, new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        deleteDialog.dismiss();
                        deleteCommet(nowPosition,commentID);
                    }
                });
    }

    private void initScroll() {
        mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Log.e("scrollY:", scrollY + " ===== ChildAt:" + v.getChildAt(0).getHeight() + " ====getHeight:" + v.getHeight());
                setAlpha((float) DensityUtil.px2dip(ComboActivity.this,scrollY)/200);
            }
        });
    }

    private void initfresh() {
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
    }


    private void initVideo() {
//        GSYVideoManager.clearAllDefaultCache(this);
        //增加封面
        coverImageView = new ImageView(this);
//        coverImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        resolveNormalVideoUI();
        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(this, detailPlayer);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);
        gsyVideoOption = new GSYVideoOptionBuilder();
        gsyVideoOption
//                .setThumbImageView(imageView)
//                .setUrl(videoUrl)
//                .setVideoTitle(result.getLogTitle())
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setSeekRatio(1)
                .setCacheWithPlay(true)
                .setVideoAllCallBack(new GSYSampleCallBack() {
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        Debuger.printfError("***** onPrepared **** " + objects[0]);
                        Debuger.printfError("***** onPrepared **** " + objects[1]);
                        super.onPrepared(url, objects);
                        //开始播放了才能旋转和全屏
                        orientationUtils.setEnable(true);
                        isPlay = true;
                    }

                    @Override
                    public void onEnterFullscreen(String url, Object... objects) {
                        super.onEnterFullscreen(url, objects);
                        Debuger.printfError("***** onEnterFullscreen **** " + objects[0]);//title
                        Debuger.printfError("***** onEnterFullscreen **** " + objects[1]);//当前全屏player
                    }

                    @Override
                    public void onAutoComplete(String url, Object... objects) {
                        super.onAutoComplete(url, objects);
                    }

                    @Override
                    public void onClickStartError(String url, Object... objects) {
                        super.onClickStartError(url, objects);
                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        Debuger.printfError("***** onQuitFullscreen **** " + objects[0]);//title
                        Debuger.printfError("***** onQuitFullscreen **** " + objects[1]);//当前非全屏player
                        if (orientationUtils != null) {
                            orientationUtils.backToProtVideo();
                        }
                    }
                })
                .setGSYVideoProgressListener(new GSYVideoProgressListener() {
                    @Override
                    public void onProgress(int progress, int secProgress, int currentPosition, int duration) {
                        Debuger.printfLog(" progress " + progress + " secProgress " + secProgress + " currentPosition " + currentPosition + " duration " + duration);
                    }
                })
                .setLockClickListener(new LockClickListener() {
                    @Override
                    public void onClick(View view, boolean lock) {
                        if (orientationUtils != null) {
                            //配合下方的onConfigurationChanged
                            orientationUtils.setEnable(!lock);
                        }
                    }
                });
        detailPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接横屏
                orientationUtils.resolveByClick();

                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                detailPlayer.startWindowFullscreen(ComboActivity.this, true, true);
            }
        });
    }

    @Override
    protected void onPause() {
        getCurPlay().onVideoPause();
        super.onPause();
        isPause = true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            detailPlayer.onConfigurationChanged(this, newConfig, orientationUtils, true, true);
        }
    }

    private void resolveNormalVideoUI() {
        //增加title
        detailPlayer.getTitleTextView().setVisibility(View.GONE);
        detailPlayer.getBackButton().setVisibility(View.GONE);
    }

    private GSYVideoPlayer getCurPlay() {
        if (detailPlayer.getFullWindowPlayer() != null) {
            return detailPlayer.getFullWindowPlayer();
        }
        return detailPlayer;
    }


    @Override
    public void onBackPressed() {

        if (orientationUtils != null) {
            orientationUtils.backToProtVideo();
        }

        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }

    /**
     * 事件总线刷新
     * @param bean
     */
    @Subscribe
    public void onEventMainThread(final EventBusBean bean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(bean.getType() == Constants.ADD_COMBO_COMMENT){
                    getComment(comboID);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        ImmersionBar.with(this).destroy(); //必须调用该方法，防止内存泄漏
        super.onDestroy();
        if (isPlay) {
            getCurPlay().release();
        }
        //GSYPreViewManager.instance().releaseMediaPlayer();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
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

    private void setAlpha(float alpha){
        float a = alpha >= 1?1:alpha;
        ImmersionBar.with(ComboActivity.this)
                .addViewSupportTransformColor(toolbar, R.color.red_new)
//                .navigationBarColorTransform(R.color.red_new)
                .barAlpha(a)
                .init();
    }

    public class ServiceDialog extends BaseDialog<ServiceDialog> implements View.OnClickListener {
        private RelativeLayout cancel;
        private RelativeLayout sure;
        public ServiceDialog(Context context) {
            super(context);
        }

        @Override
        public View onCreateView() {
            View view = View.inflate(mContext, R.layout.dialog_call_service, null);
            return view;
        }

        @Override
        public void onViewCreated(View view) {
            super.onViewCreated(view);
            cancel = (RelativeLayout) view.findViewById(R.id.button_cancel);
            sure = (RelativeLayout) view.findViewById(R.id.button_sure);
            cancel.setOnClickListener(this);
            sure.setOnClickListener(this);
        }

        @Override
        public void setUiBeforShow() {

        }

        @Override
        public void onClick(View v) {
            if(v == cancel){
                this.dismiss();
            }else if(v == sure){
                this.dismiss();
                intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "15192055999"));
                request();
            }
        }
    }

    private void request() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请CALL_PHONE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, CALL_REQUEST_CODE);
        } else {
            startActivity(intent);
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
                startActivity(intent);
            } else {
                Toast.makeText(this, "已禁止", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
