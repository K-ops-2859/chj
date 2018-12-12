package com.dikai.chenghunjiclient.activity.wedding;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.wedding.PrizeDetailsActivity;
import com.dikai.chenghunjiclient.adapter.OnItemClickListener;
import com.dikai.chenghunjiclient.adapter.wedding.InvitePrizeAdapter;
import com.dikai.chenghunjiclient.bean.BeanPager;
import com.dikai.chenghunjiclient.bean.GetPrizeBean;
import com.dikai.chenghunjiclient.entity.ActivityInfoData;
import com.dikai.chenghunjiclient.entity.PrizeData;
import com.dikai.chenghunjiclient.entity.UserInfo;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by cmk03 on 2017/12/6.
 */

public class InviteActivity1 extends AppCompatActivity implements View.OnClickListener {

    private int pagerIndex = 1;
    private int pagerCount = 20;
    private TextView tvInvitedPerson;
    private TextView tvInvitedDownLoad;
    private TextView tvInviteCode;
    private InvitePrizeAdapter mAdapter;
    private TextView tvWeddingState;
    private TextView tvDownLoadState;
    private RecyclerView mRecyclerView;
    private ActivityInfoData infoData;
    private FrameLayout flVerify;
    private Timer timer;
    private int num;
    private Toast toast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_invite1);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.pink_deep)
                .statusBarDarkFont(true, 0.2f)
                .init();
        Toolbar mToolBar = (Toolbar) findViewById(R.id.toolbar);
        flVerify = (FrameLayout) findViewById(R.id.fl_verify);
        NestedScrollView nestedScrollView = (NestedScrollView) findViewById(R.id.nested_scroll_view);
        tvInviteCode = (TextView) findViewById(R.id.tv_invite_code);
        tvInvitedPerson = (TextView) findViewById(R.id.tv_invited_person);
        tvWeddingState = (TextView) findViewById(R.id.tv_wedding_state);
        tvInvitedDownLoad = (TextView) findViewById(R.id.tv_invited_download);
        tvDownLoadState = (TextView) findViewById(R.id.tv_download_state);
        CardView cvConvert = (CardView) findViewById(R.id.cv_convert);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setNestedScrollingEnabled(false);
        mAdapter = new InvitePrizeAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        findViewById(R.id.ll_rule).setOnClickListener(this);
        findViewById(R.id.ll_invite_phone).setOnClickListener(this);
        findViewById(R.id.ll_invite_share).setOnClickListener(this);
        findViewById(R.id.ll_invite_download).setOnClickListener(this);
        findViewById(R.id.ll_invite_wedding).setOnClickListener(this);

//        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                if(scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
//                    loadMore();
//                }
//            }
//        });

        mAdapter.setOnItemClickListener(new OnItemClickListener<PrizeData.PrizeDataList>() {
            @Override
            public void onItemClick(View view, int position, PrizeData.PrizeDataList prizeDataList) {
                Intent intent = new Intent(InviteActivity1.this, PrizeDetailsActivity.class);
                int activityPrizesID = prizeDataList.getActivityPrizesID();
                intent.putExtra("prizeId", activityPrizesID);
                startActivity(intent);
            }
        });


        cvConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InviteActivity1.this, ConvertGiftActivity.class));
            }
        });

//        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
//            @Override
//            public void onRefresh() {
//
//            }
//
//            @Override
//            public void onLoadMore() {
//                loadMore();
//            }
//        });

        getInfo();

    }

    private void setData(ActivityInfoData data) {
        getListData();
        if ("".equals(data.getYQCode())) {
            tvInviteCode.setText("无邀请码");
        } else {
            tvInviteCode.setText(data.getYQCode() + "");
        }
        tvInvitedPerson.setText("已邀请 : " + data.getWeddingNumber());
        tvInvitedDownLoad.setText("已邀请 : " + data.getInvitedNumber());
    }

    private void getInfo() {
        UserInfo info = UserManager.getInstance(this).getUserInfo();
        NetWorkUtil.setCallback("Corp/GetActivityInfo", new GetPrizeBean("1", info.getUserID()), new NetWorkUtil.CallBackListener() {
            @Override
            public void onFinish(String respose) {
                infoData = new Gson().fromJson(respose, ActivityInfoData.class);
                if ("200".equals(infoData.getMessage().getCode())) {
                    flVerify.setVisibility(View.GONE);
                    setData(infoData);
                } else if ("201".equals(infoData.getMessage().getCode())) {
                    flVerify.setVisibility(View.VISIBLE);
                    initToast();
                }
            }

            @Override
            public void onError(String e) {

            }
        });
    }

    private void getListData() {
        pagerIndex = 1;
        pagerCount = 999;
        NetWorkUtil.setCallback("Corp/GetActivityPrizesList", new BeanPager(pagerIndex + "", pagerCount + ""), new NetWorkUtil.CallBackListener() {
            @Override
            public void onFinish(String respose) {
                PrizeData prizeData = new Gson().fromJson(respose, PrizeData.class);
                if ("200".equals(prizeData.getMessage().getCode())) {
                    List<PrizeData.PrizeDataList> data = prizeData.getData();
                    mAdapter.setList(data);
                }
            }

            @Override
            public void onError(String e) {

            }
        });
    }

    private void loadMore() {
        pagerIndex = 2;
        NetWorkUtil.setCallback("Corp/GetActivityPrizesList", new BeanPager(pagerIndex + "", pagerCount + ""), new NetWorkUtil.CallBackListener() {
            @Override
            public void onFinish(String respose) {
                PrizeData prizeData = new Gson().fromJson(respose, PrizeData.class);
                if ("200".equals(prizeData.getMessage().getCode())) {
                    List<PrizeData.PrizeDataList> data = prizeData.getData();
                    mAdapter.append(data);
                    pagerIndex++;
                }
            }

            @Override
            public void onError(String e) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_invite_phone:
                startActivity(new Intent(this, PhoneInviteActivity.class));
                break;
            case R.id.ll_rule:
                startActivity(new Intent(this, RuleActivity.class));
                break;
            case R.id.ll_invite_share:
                showShare();
                // TODO 分享
                break;
            case R.id.ll_invite_download:
                if (infoData != null) {
                    Intent intent = new Intent(this, InviteDownLoadActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("info_data", infoData);
//                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                break;
            case R.id.ll_invite_wedding:
                if (infoData != null) {
                    Intent intent = new Intent(this, InviteWeddingActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("info_data", infoData);
//                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            Log.v("num=", String.valueOf(num));
            num++;
            if (num < 4) {
                toast.setText(String.valueOf(3 - num) + "秒后退出!");
            } else {
                timer.cancel();
                finish();
            }
        }

    };

    private void initToast() {
        toast = Toast.makeText(this, "活动已结束", Toast.LENGTH_LONG);
        toast.show();
        num = 0;
        TimerTask task = new TimerTask() {
            public void run() {
                Message message = new Message();
                handler.sendMessage(message);
            }
        };

        timer = new Timer(true);
        timer.schedule(task, 1000, 1000);
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
//        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("成婚纪——婚礼原来如此简单");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://www.chenghunji.com/Download/Index");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("邀请码" + infoData.getYQCode());
//        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("");//确保SDcard下面存在此张图片
        oks.setImageUrl("http://www.chenghunji.com/img/index/B_LOGO_01.png");
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://www.chenghunji.com/Download/Index");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("成婚纪——婚礼原来如此简单");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("成婚纪");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://www.chenghunji.com/Download/Index");
        // 启动分享GUI
        oks.show(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
}
