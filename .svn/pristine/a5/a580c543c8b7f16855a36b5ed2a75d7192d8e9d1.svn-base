package com.dikai.chenghunjiclient.fragment.discover;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.discover.DynamicActivity;
import com.dikai.chenghunjiclient.adapter.OnAdapterViewClickListener;
import com.dikai.chenghunjiclient.adapter.OnItemClickListener;
import com.dikai.chenghunjiclient.adapter.discover.DynamicAdapter;
import com.dikai.chenghunjiclient.bean.DiscoverLikeBean;
import com.dikai.chenghunjiclient.bean.DynamicBean;
import com.dikai.chenghunjiclient.entity.DynamicData;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.fragment.BaseLazyFragment;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;
import dmax.dialog.SpotsDialog;

/**
 * Created by cmk03 on 2018/1/4.
 */

public class DynamicFragment extends BaseLazyFragment {

    private int pageIndex = 1;
    private int pageCount = 20;
    private DynamicAdapter mAdapter;
    private Context mContext;
    private MyLoadRecyclerView mRecyclerView;
    private String userID;
    private List<DynamicData.DataList> data;
    private SpotsDialog mDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_dynamic;
    }

    @Override
    protected void initViewsAndEvents(View view) {
        mDialog = new SpotsDialog(getContext(),R.style.DialogCustom);
        mContext = getContext();
        mRecyclerView = (MyLoadRecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setGridLayout(2);
        mRecyclerView.setLoadChangeListener(new MyLoadRecyclerView.OnLoadChangeListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
                pageCount = 20;
                initList();
            }

            @Override
            public void onLoadMore() {
//                loadMore();
            }
        });

        mAdapter = new DynamicAdapter(mContext);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener<DynamicData.DataList>() {
            @Override
            public void onItemClick(View view, int position, DynamicData.DataList dataList) {
                Intent intent = new Intent(mContext, DynamicActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", dataList);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        mAdapter.setLikeClickListener(new OnAdapterViewClickListener<DynamicData.DataList>() {
            @Override
            public void onAdapterClick(View view, int position, DynamicData.DataList dataList) {
                Log.e("第二步", "=======" + dataList.getState());
//                addAndRemoveLikeData(dataList.getState() == 0 ? 1 : 0, dataList.getDynamicID() + "", position);
            }
        });

//        mAdapter.setOnAdapterViewClickListener(new OnAdapterViewClickListener<DynamicData.DataList>() {
//            @Override
//            public void onAdapterClick(View view, int position, DynamicData.DataList dataList) {
//                String content = dataList.getContent();
//                String dynamicerName = dataList.getDynamicerName();
//                long dynamicID = dataList.getDynamicID();
//                showShare(content, dynamicerName, dynamicID);
//            }
//        });
    }

    @Override
    protected void DetoryViewAndEvents() {

    }

    @Override
    public void onFirstUserVisible() {
        initList();
    }

    @Override
    public void onUserVisible() {
        initList();
    }

    @Override
    public void onUserInvisible() {
    }

    private void initList() {
//        if (UserManager.getInstance(getContext()).isLogin()) {
//            userID = UserManager.getInstance(mContext).getUserInfo().getUserID();
//            NetWorkUtil.setCallback("User/GetDynamicList",
//                    new DynamicBean(userID,"0", 0, 0, pageIndex, pageCount),
//                    new NetWorkUtil.CallBackListener() {
//                        @Override
//                        public void onFinish(String respose) {
//                            mRecyclerView.stopLoad();
//                            DynamicData dynamicData = new Gson().fromJson(respose, DynamicData.class);
//                            if (dynamicData.getMessage().getCode().equals("200")) {
//                                data = dynamicData.getData();
//                                mAdapter.refresh(data);
//                            } else {
//                                Toast.makeText(mContext, dynamicData.getMessage().getInform(), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//                        @Override
//                        public void onError(String e) {
//                            mRecyclerView.stopLoad();
//                        }
//                    });
//        }
//    }
//
//    private void addAndRemoveLikeData(final int state, String objectId, final int position) {
//        mDialog.show();
//        NetWorkUtil.setCallback("User/AddDelGivethumb",
//                new DiscoverLikeBean(0, objectId, UserManager.getInstance(getContext()).getUserInfo().getUserID()),
//                new NetWorkUtil.CallBackListener() {
//                    @Override
//                    public void onFinish(String respose) {
//                        mDialog.dismiss();
//                        Log.e("返回结果", respose);
//                        ResultMessage resultMessage = new Gson().fromJson(respose, ResultMessage.class);
//                        if (resultMessage.getMessage().getCode().equals("200")) {
//                            Log.e("第三步", "=======" + state);
//                            mAdapter.itemChange(position, state);
//                        } else {
//                            Toast.makeText(mContext, resultMessage.getMessage().getInform(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onError(String e) {
//                        mDialog.dismiss();
//                    }
//                });
//    }
//
//    private void loadMore() {
//        if (UserManager.getInstance(getContext()).isLogin()) {
//            pageIndex++;
//            NetWorkUtil.setCallback("User/GetDynamicList",
//                    new DynamicBean(userID,"0", 0, 0, pageIndex, pageCount),
//                    new NetWorkUtil.CallBackListener() {
//                        @Override
//                        public void onFinish(String respose) {
//                            DynamicData dynamicData = new Gson().fromJson(respose, DynamicData.class);
//                            if (dynamicData.getMessage().getCode().equals("200")) {
//                                data = dynamicData.getData();
//                                mAdapter.add(data);
//                            } else {
//                                Toast.makeText(mContext, dynamicData.getMessage().getInform(), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//                        @Override
//                        public void onError(String e) {
//
//                        }
//                    });
//        }
    }

    @Subscribe
    public void onEvent(EventBusBean event) {
        if (event.getType() == Constants.USER_INFO_CHANGE) {
            initList();
        } else if (event.getType() == 1001) {
            initList();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void showShare(String content, String name, long id) {
        String url = "http://www.chenghunji.com/fenxiang/Index?id=" + id;
        OnekeyShare oks = new OnekeyShare();
//        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(content);
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText("来自 " + name + " 的成婚记动态");
//        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("");//确保SDcard下面存在此张图片
        oks.setImageUrl("http://www.chenghunji.com/img/index/B_LOGO_01.png");
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("成婚纪——婚礼原来如此简单");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("成婚纪");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(url);
        // 启动分享GUI
        oks.show(getContext());
    }
}
