package com.dikai.chenghunjiclient.activity.store;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.store.CasePicAdapter;
import com.dikai.chenghunjiclient.bean.BeanGetNewsInfo;
import com.dikai.chenghunjiclient.entity.ResultGetZixunInfo;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.video.LandLayoutVideo;
import com.dikai.chenghunjiclient.view.MyRecyclerView;
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

import java.util.Arrays;
import java.util.HashMap;

import dmax.dialog.SpotsDialog;

public class NewsActivity extends AppCompatActivity implements View.OnClickListener {
    
    private ImageView mBack;
    private ImageView more;
    private TextView name;
    private TextView intro;
    private MyRecyclerView mRecycler;
    private CasePicAdapter mAdater;
    private String newsID;
    private String videoUrl;
    private LandLayoutVideo detailPlayer;
    private boolean isPlay;
    private boolean isPause;
    private OrientationUtils orientationUtils;
    private GSYVideoOptionBuilder gsyVideoOption;
    private SpotsDialog mDialog;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        mDialog = new SpotsDialog(this,R.style.DialogCustom);
        newsID = getIntent().getStringExtra("news");
        detailPlayer = (LandLayoutVideo) findViewById(R.id.activity_project_info_video_layout);
        mBack = (ImageView) findViewById(R.id.activity_project_info_back);
        more = (ImageView) findViewById(R.id.activity_project_info_more);
        name = (TextView) findViewById(R.id.activity_project_info_name);
        intro = (TextView) findViewById(R.id.activity_project_info_intro);
        mRecycler = (MyRecyclerView) findViewById(R.id.activity_project_info_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(manager);
        mAdater = new CasePicAdapter(this);
        mRecycler.setAdapter(mAdater);
        mBack.setOnClickListener(this);
        more.setOnClickListener(this);
//        final String[] stringItems = {"编辑", "删除"};
//        moreDialog = new ActionSheetDialog(this, stringItems,null);
//        moreDialog.isTitleShow(false)
//                .setOnOperItemClickL(new OnOperItemClickL() {
//                    @Override
//                    public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        moreDialog.dismiss();
//                        if (position == 0) {
//                            Bundle bundle = new Bundle();
//                            bundle.putSerializable("project",mProject);
//                            bundle.putInt("t",1);
//                            startActivity(new Intent(NewsActivity.this, NewProjectActivity.class).putExtras(bundle));
//                        } else if (position == 1) {
//                            delete();
//                        }
//                    }
//                });
        initVideo();
        getInfo();
    }

    @Override
    public void onClick(View v) {
        if(v == mBack){
            onBackPressed();
        }else if(v == more){
        }
    }

    private void setData(ResultGetZixunInfo result) {
        name.setText(result.getTitle());
        intro.setText(result.getContent());
        if(result.getVideos() == null || "".equals(result.getVideos().trim())){
            detailPlayer.setVisibility(View.GONE);
            mRecycler.setVisibility(View.VISIBLE);
            if(result.getImgs() != null){
                mAdater.refresh(Arrays.asList(result.getImgs().split(",")));
            }
        }else {
            detailPlayer.setVisibility(View.VISIBLE);
            mRecycler.setVisibility(View.GONE);
            videoUrl = result.getVideos();
//            initVideo(result);
        }
//        ImageView imageView = new ImageView(this);
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        imageView.setImageBitmap(createVideoThumbnail(videoUrl,600,300));
        gsyVideoOption
//                .setThumbImageView(imageView)
                .setUrl(videoUrl)
                .setVideoTitle(result.getTitle())
                .build(detailPlayer);
    }

    private void initVideo() {
        //增加封面
//        ImageView imageView = new ImageView(this);
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        imageView.setImageBitmap(createVideoThumbnail(videoUrl,600,300));
        resolveNormalVideoUI();
        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(this, detailPlayer);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);
        gsyVideoOption = new GSYVideoOptionBuilder();
        gsyVideoOption
//                .setThumbImageView(imageView)
//                .setUrl(videoUrl)
//                .setVideoTitle(result.getTitle())
                .setIsTouchWiget(true)
                .setRotateViewAuto(true)
                .setLockLand(true)
                .setShowFullAnimation(true)
                .setNeedLockFull(true)
                .setSeekRatio(1)
                .setCacheWithPlay(false)
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
                        Debuger.printfLog(" progress " + progress + " secProgress " +
                                secProgress + " currentPosition " + currentPosition + " duration " + duration);
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
                detailPlayer.startWindowFullscreen(NewsActivity.this, true, true);
            }
        });
    }

    /**
     * 获取案例
     */
    private void getInfo(){
//        mDialog.show();
        NetWorkUtil.setCallback("User/GetInformationArticleInfo",
                new BeanGetNewsInfo(newsID,0),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultGetZixunInfo result = new Gson().fromJson(respose, ResultGetZixunInfo.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                setData(result);
                            } else {
//                                mDialog.dismiss();
                                Toast.makeText(NewsActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
//                            mDialog.dismiss();
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
//                        mDialog.dismiss();
                        Toast.makeText(NewsActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private Bitmap createVideoThumbnail(String url, int width, int height) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        int kind = MediaStore.Video.Thumbnails.MINI_KIND;
        try {
            if (Build.VERSION.SDK_INT >= 14) {
                retriever.setDataSource(url, new HashMap<String, String>());
            } else {
                retriever.setDataSource(url);
            }
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
            }
        }
        if (kind == MediaStore.Images.Thumbnails.MICRO_KIND && bitmap != null) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }
        return bitmap;
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


    @Override
    protected void onPause() {
        getCurPlay().onVideoPause();
        super.onPause();
        isPause = true;
    }

    @Override
    protected void onResume() {
        getCurPlay().onVideoResume(false);
        super.onResume();
        isPause = false;
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
            return  detailPlayer.getFullWindowPlayer();
        }
        return detailPlayer;
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy(); //必须调用该方法，防止内存泄漏
        super.onDestroy();
        if (isPlay) {
            getCurPlay().release();
        }
        //GSYPreViewManager.instance().releaseMediaPlayer();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }
}
