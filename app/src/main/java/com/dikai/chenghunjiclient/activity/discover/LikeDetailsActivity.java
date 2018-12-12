package com.dikai.chenghunjiclient.activity.discover;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.OnAdapterViewClickListener;
import com.dikai.chenghunjiclient.adapter.OnItemClickListener;
import com.dikai.chenghunjiclient.adapter.discover.DiscoverVideoAdapter;
import com.dikai.chenghunjiclient.adapter.discover.DynamicAdapter;
import com.dikai.chenghunjiclient.adapter.discover.TrendsAdapter;
import com.dikai.chenghunjiclient.bean.DiscoverLikeBean;
import com.dikai.chenghunjiclient.bean.DynamicBean;
import com.dikai.chenghunjiclient.entity.DynamicData;
import com.dikai.chenghunjiclient.entity.LikePersonData;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.util.DensityUtil;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.utils.CommonUtil;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.utils.GSYVideoHelper;
import com.shuyu.gsyvideoplayer.video.NormalGSYVideoPlayer;

import cn.sharesdk.onekeyshare.OnekeyShare;
import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;

/**
 * Created by cmk03 on 2018/1/26. 改动
 */

public class LikeDetailsActivity extends AppCompatActivity {

    private TrendsAdapter mAdapter;
    private TextView tvDynamicNumber;
    private LikePersonData.DataList data;
    private RecyclerView mRecyclerView;
    private Toolbar mToolBar;
    private CircleImageView civLogo;
    private TextView tvUserName;
    private TextView tvIdentity;

//    private GSYVideoHelper smallVideoHelper;
//    private GSYVideoHelper.GSYVideoHelperBuilder gsySmallVideoHelperBuilder;
//    private DiscoverVideoAdapter mVideoAdapter;
//    private LinearLayoutManager mLinearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private int lastVisibleItem;
    private int firstVisibleItem;
    private int pageIndex = 1;
    private int itemCount = 20;
    private int type;
    private boolean mFull;
    private SpotsDialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like_details);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        /////////////
        type = getIntent().getIntExtra("type",0);
        mDialog = new SpotsDialog(this,R.style.DialogCustom);
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        civLogo = (CircleImageView) findViewById(R.id.civ_logo);
        tvUserName = (TextView) findViewById(R.id.tv_user_name);
        tvIdentity = (TextView) findViewById(R.id.tv_identity);
        tvDynamicNumber = (TextView) findViewById(R.id.tv_dynamic_number);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        gridLayoutManager = new GridLayoutManager(LikeDetailsActivity.this,2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
//        if(type ==0){
//        }else {
//            mLinearLayoutManager = new LinearLayoutManager(this);
//            mRecyclerView.setLayoutManager(mLinearLayoutManager);
//        }
        mRecyclerView.setNestedScrollingEnabled(false);
        data = (LikePersonData.DataList) getIntent().getSerializableExtra("data");
        Glide.with(this).load(data.getGivethumbHeadportrait()).error(R.color.white)
                .into(civLogo);
        String identity = data.getOccupationCode();
        float nameLength = getTextViewLength(tvUserName, data.getGivethumbName());
        float identityLength = getTextViewLength(tvIdentity, " · " + identity);
        int screenWidth = DensityUtil.getScreenWidth(this);
        int margin = DensityUtil.dip2px(this, 12);
        if (nameLength + identityLength > screenWidth - margin *2) {
            tvUserName.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
        }
        tvIdentity.setText(" · " + identity);
        tvUserName.setText(data.getGivethumbName());
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initPic();
    }

    private void initPic() {
        mAdapter = new TrendsAdapter(LikeDetailsActivity.this);
        mAdapter.setOnItemClickListener(new TrendsAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position, int type, DynamicData.DataList bean) {
                if(type == 0){
                    Intent intent = new Intent(LikeDetailsActivity.this, DynamicActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("type",bean.getFileType());
                    bundle.putSerializable("data", bean);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else if(type == 1){
                    int state = bean.getState();
                    int num = bean.getGivethumbCount();
                    addAndRemoveLikeData(state == 0 ? 1 : 0, state == 0 ? num+1:num-1,position,bean.getDynamicID()+"");
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
//        mPicAdapter.setOnAdapterViewClickListener(new OnAdapterViewClickListener<DynamicData.DataList>() {
//            @Override
//            public void onAdapterClick(View view, int position, DynamicData.DataList dataList) {
//                String content = dataList.getContent();
//                String dynamicerName = dataList.getDynamicerName();
//                long dynamicID = dataList.getDynamicID();
//                showShare(content, dynamicerName, dynamicID);
//            }
//        });
    }

//    private void initVideo() {
//        mVideoAdapter = new DiscoverVideoAdapter(LikeDetailsActivity.this);
//        mVideoAdapter.setCanRemove(false);
//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                firstVisibleItem   = mLinearLayoutManager.findFirstVisibleItemPosition();
//                lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
//                Debuger.printfLog("firstVisibleItem " + firstVisibleItem +" lastVisibleItem " + lastVisibleItem);
//                //大于0说明有播放,//对应的播放列表TAG
//                if (smallVideoHelper.getPlayPosition() >= 0 && smallVideoHelper.getPlayTAG().equals(mVideoAdapter.TAG)) {
//                    //当前播放的位置
//                    int position = smallVideoHelper.getPlayPosition();
//                    //不可视的是时候
//                    if ((position < firstVisibleItem || position > lastVisibleItem)) {
//                        //如果是小窗口就不需要处理
//                        if (!smallVideoHelper.isSmall() && !smallVideoHelper.isFull()) {
//                            //小窗口
//                            int size = CommonUtil.dip2px(LikeDetailsActivity.this, 150);
//                            //actionbar为true才不会掉下面去
//                            smallVideoHelper.showSmallVideo(new Point(((int)(size*1.78)), size), true, true);
//                        }
//                    } else {
//                        if (smallVideoHelper.isSmall()) {
//                            smallVideoHelper.smallVideoToNormal();
//                        }
//                    }
//                }
//            }
//        });
//        mRecyclerView.setAdapter(mVideoAdapter);
//        smallVideoHelper = new GSYVideoHelper(LikeDetailsActivity.this, new NormalGSYVideoPlayer(LikeDetailsActivity.this));
////        smallVideoHelper.setFullViewContainer(((MainActivity)getActivity()).getMvideoFrame());
//        //配置
//        gsySmallVideoHelperBuilder = new GSYVideoHelper.GSYVideoHelperBuilder();
//        gsySmallVideoHelperBuilder
//                .setHideActionBar(true)
//                .setHideStatusBar(true)
//                .setNeedLockFull(true)
//                .setCacheWithPlay(true)
//                .setShowFullAnimation(true)
//                .setLockLand(true).setVideoAllCallBack(new GSYSampleCallBack() {
//            @Override
//            public void onPrepared(String url, Object... objects) {
//                super.onPrepared(url, objects);
//            }
//
//            @Override
//            public void onQuitSmallWidget(String url, Object... objects) {
//                super.onQuitSmallWidget(url, objects);
//                //大于0说明有播放,//对应的播放列表TAG
//                if (smallVideoHelper.getPlayPosition() >= 0 &&
//                        smallVideoHelper.getPlayTAG().equals(mVideoAdapter.TAG)) {
//                    //当前播放的位置
//                    int position = smallVideoHelper.getPlayPosition();
//                    //不可视的是时候
//                    if ((position < firstVisibleItem || position > lastVisibleItem)) {
//                        //释放掉视频
//                        smallVideoHelper.releaseVideoPlayer();
//                        mVideoAdapter.notifyDataSetChanged();
//                    }
//                }
//
//            }
//        });
//
//        smallVideoHelper.setGsyVideoOptionBuilder(gsySmallVideoHelperBuilder);
//        mVideoAdapter.setVideoHelper(smallVideoHelper, gsySmallVideoHelperBuilder);
//        mVideoAdapter.setOnItemClickListener(new OnItemClickListener<DynamicData.DataList>() {
//            @Override
//            public void onItemClick(View view, int position, DynamicData.DataList dataList) {
//                Intent intent = new Intent(LikeDetailsActivity.this, DynamicActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("data", dataList);
//                bundle.putInt("type",2);//video类型
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        });
//
//        mVideoAdapter.setLikeClickListener(new OnAdapterViewClickListener<DynamicData.DataList>() {
//            @Override
//            public void onAdapterClick(View view, int position, DynamicData.DataList dataList) {
//                Log.e("第二步", "=======" + dataList.getState());
//                addAndRemoveLikeData(dataList.getState() == 0 ? 1 : 0, dataList.getDynamicID() + "", position);
//            }
//        });
//
//        mVideoAdapter.setOnAdapterViewClickListener(new OnAdapterViewClickListener<DynamicData.DataList>() {
//            @Override
//            public void onAdapterClick(View view, int position, DynamicData.DataList dataList) {
//                String content = dataList.getContent();
//                String dynamicerName = dataList.getDynamicerName();
//                long dynamicID = dataList.getDynamicID();
//                showShare(content, dynamicerName, dynamicID);
//            }
//        });
//    }

    private void refresh() {
        pageIndex = 1;
        itemCount = 999;
        getList(pageIndex,itemCount,true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        refresh();
    }

    private void getList(int pageIndex, int pageCount, final boolean isRefresh) {
        if (UserManager.getInstance(LikeDetailsActivity.this).isLogin()) {
            NetWorkUtil.setCallback("HQOAApi/GetDynamicList",
                    new DynamicBean(UserManager.getInstance(this).getNewUserInfo().getUserId(),data.getGivethumberId(),0,pageIndex,pageCount),
                    new NetWorkUtil.CallBackListener() {
                        @Override
                        public void onFinish(final String respose) {
                            Log.e("返回值",respose);
//                            mRecyclerView.stopLoad();
                            try {
                                DynamicData result = new Gson().fromJson(respose,  DynamicData.class);
                                if ("200".equals(result.getMessage().getCode())) {
//                                    mRecyclerView.setTotalCount(result.getTotalCount());
                                    tvDynamicNumber.setText("TA的动态  " + result.getTotalCount());
                                    mAdapter.refresh(result.getData());
                                } else {
                                    Toast.makeText(LikeDetailsActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Log.e("json解析出错",e.toString());
                                //Toast.makeText(LikeDetailsActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(final String e) {
//                            mRecyclerView.stopLoad();
                            Toast.makeText(LikeDetailsActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void addAndRemoveLikeData(final int state, final int num, final int position, String objectId) {
        mDialog.show();
        NetWorkUtil.setCallback("HQOAApi/AddDelGivethumb",
                new DiscoverLikeBean(objectId,UserManager.getInstance(this).getNewUserInfo().getUserId()),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(String respose) {
                        try {
                            mDialog.dismiss();
                            Log.e("返回结果", respose);
                            ResultMessage resultMessage = new Gson().fromJson(respose, ResultMessage.class);
                            if (resultMessage.getMessage().getCode().equals("200")) {
                                Log.e("第三步", "=======" + state);
                                mAdapter.itemChange(position, state,num);
                            } else {
                                Toast.makeText(LikeDetailsActivity.this, resultMessage.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                        }
                    }

                    @Override
                    public void onError(String e) {
                        mDialog.dismiss();
                    }
                });
    }

    // 计算出该TextView中文字的长度(像素)
    public static float getTextViewLength(TextView textView, String text) {
        TextPaint paint = textView.getPaint();
        // 得到使用该paint写上text的时候,像素为多少
        float textLength = paint.measureText(text);
        return textLength;
    }

    private void showShare(String content, String name, long id) {
        String url = "http://www.chenghunji.com/fenxiang/Index?id=" + id;
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(content);
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText("来自 " + name + " 的成婚记动态");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("");//确保SDcard下面存在此张图片
        oks.setImageUrl("http://www.chenghunji.com/img/index/B_LOGO_01.png");
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        //oks.setComment("成婚纪——婚礼原来如此简单");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("成婚纪");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(url);
        // 启动分享GUI
        oks.show(this);
    }

//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        //如果旋转了就全屏
//        if (newConfig.orientation != ActivityInfo.SCREEN_ORIENTATION_USER) {
//            mFull = false;
//        } else {
//            mFull = true;
//        }
//        Log.e("mFull",""+mFull);
//    }

//    @Override
//    public void onBackPressed() {
//        if (mFull) {
//            smallVideoHelper.backFromFull();
//        }else {
//            super.onBackPressed();
//        }
//    }

//    @Override
//    public void onPause() {
//        super.onPause();
//        if(type != 0){
//            GSYVideoManager.onPause();
//        }
//    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        if(type != 0){
//            GSYVideoManager.onResume();
//        }
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
//        if(type != 0){
//            smallVideoHelper.releaseVideoPlayer();
//            GSYVideoManager.releaseAllVideos();
//        }
    }
}
