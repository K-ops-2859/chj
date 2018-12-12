package com.dikai.chenghunjiclient.activity.me;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.OnAdapterViewClickListener;
import com.dikai.chenghunjiclient.adapter.me.MyVideoAdapter;
import com.dikai.chenghunjiclient.bean.BeanDeleteUploadedFile;
import com.dikai.chenghunjiclient.bean.BeanPhotoDetails;
import com.dikai.chenghunjiclient.bean.BeanPhotoDis;
import com.dikai.chenghunjiclient.entity.PhotoDetailsData;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;
import com.flyco.dialog.widget.base.BaseDialog;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.utils.CommonUtil;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.utils.GSYVideoHelper;
import com.shuyu.gsyvideoplayer.video.NormalGSYVideoPlayer;

import dmax.dialog.SpotsDialog;

public class MyVideoActivity extends AppCompatActivity implements View.OnClickListener {
    private GSYVideoHelper smallVideoHelper;
    private GSYVideoHelper.GSYVideoHelperBuilder gsySmallVideoHelperBuilder;
    private MyVideoAdapter mVideoAdapter;
    private MyLoadRecyclerView mVideoRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private int lastVisibleItem;
    private int firstVisibleItem;
    private int pageIndex = 1;
    private int itemCount = 20;
    private int itemPosition;
    private long itemID;
    private SpotsDialog mDialog;
    private long clientId;
    private boolean mFull;
    private MaterialDialog dialog2;
    private ServiceDialog ruleDialog;
    private boolean isInit = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_video);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        mDialog = new SpotsDialog(this,R.style.DialogCustom);
        clientId = getIntent().getLongExtra("id",0);
        System.out.println("视频客户id=============" + clientId);
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.rule).setOnClickListener(this);
        mVideoRecyclerView = (MyLoadRecyclerView) findViewById(R.id.video_recycler);
        mDialog = new SpotsDialog(this,R.style.DialogCustom);
//        boolean canRemove = getArguments().getBoolean("canremove",false);
//        VIDEO_TYPE = canRemove? 1:0;
        mVideoAdapter = new MyVideoAdapter(this);
//        mVideoAdapter.setCanRemove(canRemove);
        linearLayoutManager = new LinearLayoutManager(this);
        mVideoRecyclerView.setLinearLayout(linearLayoutManager);
        mVideoRecyclerView.setListScrollListener(new MyLoadRecyclerView.OnListScrollListener() {
            @Override
            public void onScroll(int y) {
                firstVisibleItem   = linearLayoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                Debuger.printfLog("firstVisibleItem " + firstVisibleItem +" lastVisibleItem " + lastVisibleItem);
                //大于0说明有播放,//对应的播放列表TAG
                if (smallVideoHelper.getPlayPosition() >= 0 && smallVideoHelper.getPlayTAG().equals(mVideoAdapter.TAG)) {
                    //当前播放的位置
                    int position = smallVideoHelper.getPlayPosition();
                    //不可视的是时候
                    if ((position < firstVisibleItem || position > lastVisibleItem)) {
                        //如果是小窗口就不需要处理
                        if (!smallVideoHelper.isSmall() && !smallVideoHelper.isFull()) {
                            //小窗口
                            int size = CommonUtil.dip2px(MyVideoActivity.this, 150);
                            //actionbar为true才不会掉下面去
                            smallVideoHelper.showSmallVideo(new Point(((int)(size*1.78)), size), true, true);
                        }
                    } else {
                        if (smallVideoHelper.isSmall()) {
                            smallVideoHelper.smallVideoToNormal();
                        }
                    }
                }
            }
        });

        mVideoRecyclerView.setLoadChangeListener(new MyLoadRecyclerView.OnLoadChangeListener() {
            @Override
            public void onRefresh() {
                refresh();
            }

            @Override
            public void onLoadMore() {
                pageIndex++;
                getList(pageIndex,itemCount,false);
            }
        });

        mVideoRecyclerView.setAdapter(mVideoAdapter);
        smallVideoHelper = new GSYVideoHelper(this, new NormalGSYVideoPlayer(this));
//        smallVideoHelper.setFullViewContainer(((MainActivity)getActivity()).getMvideoFrame());
        //配置
        gsySmallVideoHelperBuilder = new GSYVideoHelper.GSYVideoHelperBuilder();
        gsySmallVideoHelperBuilder
                .setHideActionBar(true)
                .setHideStatusBar(true)
                .setNeedLockFull(true)
                .setCacheWithPlay(true)
                .setShowFullAnimation(true)
                .setLockLand(true).setVideoAllCallBack(new GSYSampleCallBack() {
            @Override
            public void onPrepared(String url, Object... objects) {
                super.onPrepared(url, objects);
            }

            @Override
            public void onQuitSmallWidget(String url, Object... objects) {
                super.onQuitSmallWidget(url, objects);
                //大于0说明有播放,//对应的播放列表TAG
                if (smallVideoHelper.getPlayPosition() >= 0 &&
                        smallVideoHelper.getPlayTAG().equals(mVideoAdapter.TAG)) {
                    //当前播放的位置
                    int position = smallVideoHelper.getPlayPosition();
                    //不可视的是时候
                    if ((position < firstVisibleItem || position > lastVisibleItem)) {
                        //释放掉视频
                        smallVideoHelper.releaseVideoPlayer();
                        mVideoAdapter.notifyDataSetChanged();
                    }
                }

            }
        });

        smallVideoHelper.setGsyVideoOptionBuilder(gsySmallVideoHelperBuilder);
        mVideoAdapter.setVideoHelper(smallVideoHelper, gsySmallVideoHelperBuilder);
        mVideoAdapter.setOnDeleteListener(new OnAdapterViewClickListener<PhotoDetailsData.DataList>() {
            @Override
            public void onAdapterClick(View view, int position, PhotoDetailsData.DataList dataList) {
                itemID = dataList.getId();
                itemPosition = position;
                dialog2.show();
            }
        });

        dialog2 = new MaterialDialog(this);
        dialog2.isTitleShow(false)//
                .btnNum(2)
                .content("确定要删除此视频吗")//
                .btnText("取消", "确定")
                .setOnBtnClickL(new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog2.dismiss();
                    }
                }, new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog2.dismiss();
                        remove(itemID+"");
                    }
                });
        ruleDialog = new ServiceDialog(this);
        ruleDialog.widthScale(1);
        ruleDialog.heightScale(1);
        if(clientId == -1){
            ruleDialog.show();
        }else {
            refresh();
        }
    }

    private void refresh() {
        pageIndex = 1;
        itemCount = 20;
        getList(pageIndex,itemCount,true);
    }

    private void getList(int pageIndex, int pageCount, final boolean isRefresh) {
        if (UserManager.getInstance(this).isLogin()) {
            NetWorkUtil.setCallback("User/GetFileSupplier",
                    new BeanPhotoDetails("0", clientId + "", "0", "2", "", "", pageIndex + "", pageCount + ""),
                    new NetWorkUtil.CallBackListener() {
                        @Override
                        public void onFinish(final String respose) {
                            Log.e("返回值",respose);
                            mVideoRecyclerView.stopLoad();
                            try {
                                PhotoDetailsData result = new Gson().fromJson(respose,  PhotoDetailsData.class);
                                if ("200".equals(result.getMessage().getCode())) {
                                    mVideoRecyclerView.setTotalCount(result.getTotalCount());
                                    if(isRefresh){
                                        mVideoAdapter.refresh(result.getData());
                                        if(result.getData().size() > 0){
                                            mVideoRecyclerView.setHasData(true);
                                        }else {
                                            mVideoRecyclerView.setHasData(false);
                                            if (isInit){
                                                ruleDialog.show();
                                                isInit = false;
                                            }
                                        }
                                    }else {
                                        mVideoAdapter.addAll(result.getData());
                                    }
                                } else {
                                    Toast.makeText(MyVideoActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Log.e("json解析出错",e.toString());
                                //Toast.makeText(MyVideoActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(final String e) {
                            mVideoRecyclerView.stopLoad();
                            Toast.makeText(MyVideoActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void remove(String id) {
        smallVideoHelper.releaseVideoPlayer();
        GSYVideoManager.releaseAllVideos();
        NetWorkUtil.setCallback("User/DeleteUploadedFile",
                new BeanDeleteUploadedFile(id),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(String respose) {
                        ResultMessage data = new Gson().fromJson(respose, ResultMessage.class);
                        if (data.getMessage().getCode().equals("200")) {
                            mVideoAdapter.remove(itemPosition);
                        }else {
                            Toast.makeText(MyVideoActivity.this, data.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(String e) {

                    }
                });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (newConfig.orientation != ActivityInfo.SCREEN_ORIENTATION_USER) {
            mFull = false;
        } else {
            mFull = true;
        }
        Log.e("mFull","" + mFull);
    }

    @Override
    public void onBackPressed() {
        if (mFull) {
            smallVideoHelper.backFromFull();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        GSYVideoManager.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        smallVideoHelper.releaseVideoPlayer();
        GSYVideoManager.releaseAllVideos();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.rule:
                ruleDialog.show();
                break;
        }
    }

    /**
     *rule
     */
    private class ServiceDialog extends BaseDialog<ServiceDialog> implements View.OnClickListener {
        private TextView close;
        public ServiceDialog(Context context) {
            super(context);
        }
        @Override
        public View onCreateView() {
            View view = View.inflate(mContext, R.layout.dialog_photo_video_know, null);
            close = (TextView) view.findViewById(R.id.close);
            close.setOnClickListener(this);
            return view;
        }
        @Override
        public void onViewCreated(View view) {
            super.onViewCreated(view);
        }
        @Override
        public void setUiBeforShow() {}
        @Override
        public void onClick(View v) {
            if(v == close){
                this.dismiss();
            }
        }
    }

}

