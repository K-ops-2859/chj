package com.dikai.chenghunjiclient.fragment.discover;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.discover.DynamicActivity;
import com.dikai.chenghunjiclient.adapter.discover.TrendsAdapter;
import com.dikai.chenghunjiclient.bean.DiscoverLikeBean;
import com.dikai.chenghunjiclient.bean.DynamicBean;
import com.dikai.chenghunjiclient.entity.DynamicData;
import com.dikai.chenghunjiclient.entity.ResultMessage;
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
 * A simple {@link Fragment} subclass.
 */
public class MyDynamicFragment extends Fragment {

    private TrendsAdapter mAdapter;
    private int pageIndex = 1;
    private int pageCount = 20;
    private MyLoadRecyclerView mRecyclerView;
    private List<DynamicData.DataList> data;
    private AlertDialog.Builder normalDialog;
    private int itemPosition;
    private SpotsDialog mDialog;
    private int type;
    private String userID;

    public MyDynamicFragment() {
        // Required empty public constructor
    }

    public static MyDynamicFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type",type);
        MyDynamicFragment fragment = new MyDynamicFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        EventBus.getDefault().register(this);
        return inflater.inflate(R.layout.fragment_my_dynamic, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        type = getArguments().getInt("type");
        mDialog = new SpotsDialog(getContext(), R.style.DialogCustom);
        userID = UserManager.getInstance(getContext()).getNewUserInfo().getUserId();
        mRecyclerView = (MyLoadRecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setGridLayout(2);
        mAdapter = new TrendsAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLoadChangeListener(new MyLoadRecyclerView.OnLoadChangeListener() {
            @Override
            public void onRefresh() {
                refresh();
            }

            @Override
            public void onLoadMore() {
                pageIndex++;
                getList(pageIndex,pageCount,false);
            }
        });
        mAdapter.setOnItemClickListener(new TrendsAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position, int type, DynamicData.DataList bean) {
                if(type == 0){
                    Intent intent = new Intent(getContext(), DynamicActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("who",1);
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
        refresh();
    }

    private void refresh(){
        pageIndex = 1;
        pageCount = 20;
        getList(pageIndex,pageCount,true);
    }

    /**
     * 获取记录
     */
    private void getList(int PageIndex, int PageCount, final boolean isRefresh){
        NetWorkUtil.setCallback("HQOAApi/GetDynamicList",
                new DynamicBean(userID,userID, type,PageIndex, PageCount),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mRecyclerView.stopLoad();
                        Log.e("返回值",respose);
                        try {
                            DynamicData result = new Gson().fromJson(respose, DynamicData.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                if(isRefresh){
                                    mAdapter.refresh(result.getData());
                                    if(result.getData().size() == 0){
                                        mRecyclerView.setHasData(false);
                                    }else {
                                        mRecyclerView.setHasData(true);
                                    }
                                }else {
                                    mAdapter.addAll(result.getData());
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
                        mRecyclerView.stopLoad();
//                        Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void addAndRemoveLikeData(final int state, final int num, final int position, String objectId) {
        mDialog.show();
        NetWorkUtil.setCallback("HQOAApi/AddDelGivethumb",
                new DiscoverLikeBean(objectId, UserManager.getInstance(getContext()).getNewUserInfo().getUserId()),
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
                                Toast.makeText(getContext(), resultMessage.getMessage().getInform(), Toast.LENGTH_SHORT).show();
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


    /**
     * 事件总线刷新
     * @param bean
     */
    @Subscribe
    public void onEventMainThread(final EventBusBean bean) {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                if(bean.getType() == Constants.DELETE_DYNAMIC && bean.getProgress() == type){
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
