package com.dikai.chenghunjiclient.activity.store;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.store.ComboVideoAdapter;
import com.dikai.chenghunjiclient.entity.ResultGetComboVideo;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.gyf.barlibrary.ImmersionBar;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.utils.CommonUtil;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.utils.GSYVideoHelper;
import com.shuyu.gsyvideoplayer.video.NormalGSYVideoPlayer;

public class ComboVideoActivity extends AppCompatActivity implements View.OnClickListener {

    private GSYVideoHelper smallVideoHelper;
    private GSYVideoHelper.GSYVideoHelperBuilder gsySmallVideoHelperBuilder;
    private ComboVideoAdapter mVideoAdapter;
    private MyLoadRecyclerView mVideoRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private int lastVisibleItem;
    private int firstVisibleItem;
    private boolean mFull;
    private ResultGetComboVideo mComboVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combo_video);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        mComboVideo = (ResultGetComboVideo) getIntent().getSerializableExtra("bean");
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.rule).setOnClickListener(this);
        mVideoRecyclerView = (MyLoadRecyclerView) findViewById(R.id.video_recycler);
        mVideoAdapter = new ComboVideoAdapter(this);
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
                            int size = CommonUtil.dip2px(ComboVideoActivity.this, 150);
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
        refresh();
    }

    private void refresh() {
        mVideoRecyclerView.stopLoad();
        mVideoAdapter.refresh(mComboVideo.getData());
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
        }
    }

}
