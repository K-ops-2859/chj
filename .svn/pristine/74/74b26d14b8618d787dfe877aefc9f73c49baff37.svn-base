package com.dikai.chenghunjiclient.activity.me;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.wedding.GetPrizeActivity;
import com.dikai.chenghunjiclient.activity.wedding.PrizeDetailsActivity;
import com.dikai.chenghunjiclient.activity.wedding.RuleActivity;
import com.dikai.chenghunjiclient.adapter.OnItemClickListener;
import com.dikai.chenghunjiclient.adapter.wedding.InviteAdapter;
import com.dikai.chenghunjiclient.bean.BeanInvite;
import com.dikai.chenghunjiclient.bean.BeanPrizeInfo;
import com.dikai.chenghunjiclient.bean.GetPrizeBean;
import com.dikai.chenghunjiclient.entity.ActivityDetailsData;
import com.dikai.chenghunjiclient.entity.GetInviteCountData;
import com.dikai.chenghunjiclient.entity.GetPrizeData;
import com.dikai.chenghunjiclient.entity.UserInfo;
import com.dikai.chenghunjiclient.entity.WrapperPrizeData;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.sharesdk.onekeyshare.OnekeyShare;


/**
 * Created by cmk03 on 2017/11/15.
 */

public class InviteActivity extends AppCompatActivity  {

    private InviteAdapter mAdapter;
    private TextView tvInviteCode;
    private int inviteCount;
    private int currPage = 2;
    private RecyclerView mRecyclerView;
    private LinearLayout llFooter;
    private TextView tvActivityId;
    private TextView tvActivityContent;
    private TextView tvTime;
    private TextView tvRule;
    private TextView tvGetPriz;
    private GetInviteCountData inviteCountData;
    private TextView tvInviteSuccess;
    private FrameLayout flVerify;
    private Timer mOffTime;
    private int num;
    private Toast toast;
    private List<WrapperPrizeData> wrapperData = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_invite);

        Toolbar mToolBar = (Toolbar) findViewById(R.id.toolbar);
        tvRule = (TextView) findViewById(R.id.tv_rule);
        tvInviteCode = (TextView) findViewById(R.id.tv_invite_code);
        tvInviteSuccess = (TextView) findViewById(R.id.tv_invite_success);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        tvGetPriz = (TextView) findViewById(R.id.tv_get_prize);
        tvActivityId = (TextView) findViewById(R.id.tv_activity_id);
        tvActivityContent = (TextView) findViewById(R.id.tv_activity_content);
        tvTime = (TextView) findViewById(R.id.tv_time);
        TextView tvShare = (TextView) findViewById(R.id.tv_share);
        flVerify = (FrameLayout) findViewById(R.id.fl_verify);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setNestedScrollingEnabled(false);
//        View footerView = LayoutInflater.from(this).inflate(R.layout.layout_invite_load_footer, null);
//        llFooter = (LinearLayout) footerView.findViewById(R.id.ll_footer);
//        mRecyclerView.addFooterView(footerView);

//        StickyDecoration decoration = StickyDecoration.Builder
//                .init(this)
//                .setGroupBackground(Color.parseColor("#dedcdc"))    //背景色
//                .setGroupHeight(DensityUtil.dip2px(this, 35))       //高度
//                .setDivideColor(Color.parseColor("#dedcdc"))        //分割线颜色
//                .setDivideHeight(DensityUtil.dip2px(this, 1))       //分割线高度 (默认没有分割线)
////                .setGroupTextColor(Color.BLACK)                     //字体颜色
//                .setGroupTextSize(DensityUtil.sp2px(this, 15))      //字体大小
//                .setTextSideMargin(DensityUtil.dip2px(this, 16))
//                //靠右显示  （默认靠左）
//                .build();
//        mRecyclerView.addItemDecoration(decoration);
        mAdapter = new InviteAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        getInfo();
        getInvite();
        mAdapter.setOnItemClickListener(new OnItemClickListener<WrapperPrizeData>() {
            @Override
            public void onItemClick(View view, int position, WrapperPrizeData prizeList) {
                Intent intent = new Intent(InviteActivity.this, PrizeDetailsActivity.class);
                int activityPrizesID = prizeList.getActivityPrizesID();
                intent.putExtra("prizeId", activityPrizesID);
                startActivity(intent);
            }
        });

        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showShare();
            }
        });
    }

    private void upData(final ActivityDetailsData data) {
        tvActivityId.setText(data.getActivityID() + "");
        tvActivityContent.setText(data.getContent());
        tvTime.setText("活动时间 : " + data.getStartTime() + "至" + data.getEndTime());
        tvRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InviteActivity.this, RuleActivity.class);
                intent.putExtra("rule", data.getRule());
                startActivity(intent);
            }
        });

        tvGetPriz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InviteActivity.this, GetPrizeActivity.class);
                intent.putExtra("Grade", data.getGrade());
                intent.putExtra("end_time", data.getEndTime());
                intent.putExtra("count", inviteCountData.getCount());

                startActivity(intent);

            }
        });
    }

    private void getInvite() {
        String userId = UserManager.getInstance(this).getUserInfo().getUserID();
        NetWorkUtil.setCallback("User/GetYQCount", new BeanInvite(userId), new NetWorkUtil.CallBackListener() {
            @Override
            public void onFinish(String respose) {
                System.out.println(respose);
                inviteCountData = new Gson().fromJson(respose, GetInviteCountData.class);
                if (inviteCountData.getMessage().getCode().equals("200")) {
                    System.out.println("========" + inviteCountData.getYQCode());
                    if (inviteCountData.getYQCode().equals("")) {

                        tvInviteCode.setText("无邀请码");
                    } else {
                        tvInviteCode.setText(inviteCountData.getYQCode());
                    }
                    tvInviteSuccess.setText(inviteCountData.getCount() + " 人");
                }
            }

            @Override
            public void onError(String e) {

            }
        });
    }

    private void getInfo() {
        UserInfo info = UserManager.getInstance(this).getUserInfo();
        NetWorkUtil.setCallback("Corp/GetActivityInfo", new GetPrizeBean("1", info.getUserID()), new NetWorkUtil.CallBackListener() {
            @Override
            public void onFinish(String respose) {
                ActivityDetailsData data = new Gson().fromJson(respose, ActivityDetailsData.class);
                if (data.getMessage().getCode().equals("200")) {
                    flVerify.setVisibility(View.GONE);
                    upData(data);
                    initData(data);
                } else if (data.getMessage().getCode().equals("201")) {
                    flVerify.setVisibility(View.VISIBLE);
                    initToast();
                }
            }

            @Override
            public void onError(String e) {

            }
        });
    }

    private void initData(final ActivityDetailsData data) {

        NetWorkUtil.setCallback("Corp/ActivityPrizesList",new BeanPrizeInfo(data.getActivityID() + ""), new NetWorkUtil.CallBackListener() {
            @Override
            public void onFinish(String respose) {

                GetPrizeData dataList = new Gson().fromJson(respose, GetPrizeData.class);
                if (dataList.getMessage().getCode().equals("200")) {
                    List<GetPrizeData.GradeData> gradeData = dataList.getGradeData();
                    for (GetPrizeData.GradeData datalist : gradeData) {
                        wrapperData.add(new WrapperPrizeData(datalist.getGrade(), true));
                        List<GetPrizeData.GradeData.DagaList> data1 = datalist.getData();
                        for (GetPrizeData.GradeData.DagaList dagaList : data1) {
                            System.out.println("==================" + data1.size());
//                            wrapperData.add(new WrapperPrizeData(datalist.getGrade(), dagaList.getActivityPrizesID(), dagaList.getCommodityName(),
//                                    dagaList.getShowImg(), dagaList.getCountry(), dagaList.getCreateTime(), dagaList.getMarketPrice(), false));
                        }
                    }
                    mAdapter.setList(wrapperData);
                }
            }

            @Override
            public void onError(String e) {
                Log.e("错误", e);
            }
        });
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            Log.v("num=", String.valueOf(num));
            num++;
            if (num < 4) {
                toast.setText(String.valueOf(3 - num) + "秒后退出!");
            } else {
                //timer.cancel();
                InviteActivity.this.finish();
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
//
//        timer = new Timer(true);
//        timer.schedule(task, 1000, 1000);
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
        oks.setText("邀请码" + inviteCountData.getYQCode());
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

//    @Override
//    public String getGroupName(int position) {
//
//        if (wrapperData.size() > position) {
//            //获取城市对应的省份
//
//            return "达标" + wrapperData.get(position).getGrade() + " 即可领取好礼";
//        }
//        return null;
//    }
}
