package com.dikai.chenghunjiclient.activity.wedding;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.register.LoginActivity;
import com.dikai.chenghunjiclient.activity.store.WedPrizeInfoActivity;
import com.dikai.chenghunjiclient.activity.store.WedPrizeListActivity;
import com.dikai.chenghunjiclient.adapter.store.NewWedStoreAdapter;
import com.dikai.chenghunjiclient.adapter.wedding.InviteWedAdapter;
import com.dikai.chenghunjiclient.adapter.wedding.NewInviteStoreAdapter;
import com.dikai.chenghunjiclient.bean.BeanGetFanhuanList;
import com.dikai.chenghunjiclient.bean.BeanPager;
import com.dikai.chenghunjiclient.bean.BeanType;
import com.dikai.chenghunjiclient.entity.GoodsItemBean;
import com.dikai.chenghunjiclient.entity.GoodsTypeBean;
import com.dikai.chenghunjiclient.entity.PrizeData;
import com.dikai.chenghunjiclient.entity.ResultGoodsTypeList;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

public class InviteWedActivity extends AppCompatActivity implements View.OnClickListener {

    private MyLoadRecyclerView mRecyclerView;
    private NewInviteStoreAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_wed);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        findViewById(R.id.back).setOnClickListener(this);
        mRecyclerView = (MyLoadRecyclerView) findViewById(R.id.recycler);
        mRecyclerView.setLoadChangeListener(new MyLoadRecyclerView.OnLoadChangeListener() {
            @Override
            public void onRefresh() {
                refresh();
            }

            @Override
            public void onLoadMore() {

            }
        });
        mAdapter = new NewInviteStoreAdapter(this);
        mAdapter.setOnItemClickListener(new NewInviteStoreAdapter.OnItemClickListener() {
            @Override
            public void onClick(int Type, Object bean) {
                if(UserManager.getInstance(InviteWedActivity.this).isLogin()){
                    if(Type == 0){//邀请
                        startActivity(new Intent(InviteWedActivity.this, InviteApplyActivity.class));
                    }else if(Type == 1){//记录
                        startActivity(new Intent(InviteWedActivity.this, RecordActivity.class));
                    }else if(Type == 2){//规则
                        startActivity(new Intent(InviteWedActivity.this, WedRuleActivity.class)
                                .putExtra("url","http://www.chenghunji.com/Download/Rules/OtherFreeWedding.html"));
                    }else if(Type == 3){//邀请
                        showShare();
                    }else if(Type == 4){//二维码邀请
                        startActivity(new Intent(InviteWedActivity.this, WedCodeActivity.class));
                    }else if(Type == 5){//礼品列表
                        startActivity(new Intent(InviteWedActivity.this,WedPrizeListActivity.class)
                                .putExtra("title",((GoodsTypeBean)bean).getTypeName())
                                .putExtra("typeID",((GoodsTypeBean)bean).getTypeId()));
                    }else if(Type == 6){
                        startActivity(new Intent(InviteWedActivity.this,WedPrizeListActivity.class)
                                .putExtra("title",((GoodsItemBean)bean).getTypeName())
                                .putExtra("typeID",((GoodsItemBean)bean).getTypeId()));
                    }
                }else {
                    startActivity(new Intent(InviteWedActivity.this, LoginActivity.class));
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        getList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
        }
    }

    private void refresh() {
        getList();
    }

    /**
     * 获取商品列表
     */
    private void getList(){
        NetWorkUtil.setCallback("HQOAApi/GetCommodityTypeTableList",
                new BeanGetFanhuanList(1,"51F48013-9ADA-4342-96BF-259C345832AE"),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mRecyclerView.stopLoad();
                        Log.e("返回值",respose);
                        try {
                            ResultGoodsTypeList result = new Gson().fromJson(respose, ResultGoodsTypeList.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                List<Object> mGoods = new ArrayList<>();
                                mGoods.add("head");
                                for (GoodsTypeBean bean: result.getData()) {
                                    if(bean.getData()!=null && bean.getData().size()>0){
                                        mGoods.add(bean);
                                        for (int i = 0; i < bean.getData().size(); i++) {
                                            if(i <= 0){
                                                GoodsItemBean itemBean = bean.getData().get(i);
                                                itemBean.setTypeName(bean.getTypeName());
                                                mGoods.add(itemBean);
                                            }
                                        }
                                    }
                                }
                                mAdapter.refresh(mGoods);
                            } else {
                                Toast.makeText(InviteWedActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mRecyclerView.stopLoad();
//                        Toast.makeText(InviteWedActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

//    private void getListData() {
//        NetWorkUtil.setCallback("Corp/GetActivityPrizesList",
//                new BeanPager("1", "1000"),
//                new NetWorkUtil.CallBackListener() {
//                    @Override
//                    public void onFinish(String respose) {
//                        PrizeData prizeData = new Gson().fromJson(respose, PrizeData.class);
//                        if ("200".equals(prizeData.getMessage().getCode())) {
//                            List<Object> data = new ArrayList<>();
//                            data.add("");
//                            data.addAll(prizeData.getData());
//                            mAdapter.refresh(data);
//                        }
//                    }
//
//                    @Override
//                    public void onError(String e) {
//                    }
//                });
//    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
//        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("邀好友来成婚纪办婚礼，可赢取100元现金，可提现！");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://www.chenghunji.com/Redbag/yqoqingjiehun");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("与好友一起起航，登上“小成梦想号”，赴免费盛宴，掌握赚钱秘籍，赢高额奖励金哦！");
//        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("");//确保SDcard下面存在此张图片
        oks.setImageUrl("http://www.chenghunji.com/img/index/B_LOGO_01.png");
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://www.chenghunji.com/Redbag/yqoqingjiehun");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("成婚纪——婚礼原来如此简单");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("成婚纪");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://www.chenghunji.com/");
        // 启动分享GUI
        oks.show(InviteWedActivity.this);
    }


    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}
