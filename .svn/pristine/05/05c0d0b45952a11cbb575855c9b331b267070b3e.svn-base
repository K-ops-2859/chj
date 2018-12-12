package com.dikai.chenghunjiclient.fragment.discover;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.discover.DynamicActivity;
import com.dikai.chenghunjiclient.adapter.OnAdapterViewClickListener;
import com.dikai.chenghunjiclient.adapter.OnItemClickListener;
import com.dikai.chenghunjiclient.adapter.discover.DiscoverVideoAdapter;
import com.dikai.chenghunjiclient.bean.DiscoverLikeBean;
import com.dikai.chenghunjiclient.bean.DynamicBean;
import com.dikai.chenghunjiclient.bean.RemoveDynamicBean;
import com.dikai.chenghunjiclient.entity.DynamicData;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.google.gson.Gson;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.utils.CommonUtil;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.utils.GSYVideoHelper;
import com.shuyu.gsyvideoplayer.video.NormalGSYVideoPlayer;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import cn.sharesdk.onekeyshare.OnekeyShare;
import dmax.dialog.SpotsDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFragment extends Fragment {

    private GSYVideoHelper smallVideoHelper;
    private GSYVideoHelper.GSYVideoHelperBuilder gsySmallVideoHelperBuilder;
    private DiscoverVideoAdapter mVideoAdapter;
    private MyLoadRecyclerView mVideoRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private int lastVisibleItem;
    private int firstVisibleItem;
    private int pageIndex = 1;
    private int itemCount = 20;
    private AlertDialog.Builder normalDialog;
    private int VIDEO_TYPE;

    private int itemPosition;
    private long itemID;
    private SpotsDialog mDialog;

    public static VideoFragment newInstance(boolean canRemove) {
        Bundle args = new Bundle();
        args.putBoolean("canremove",canRemove);
        VideoFragment fragment = new VideoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public VideoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        EventBus.getDefault().register(this);
        return inflater.inflate(R.layout.fragment_video, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mVideoRecyclerView = (MyLoadRecyclerView) view.findViewById(R.id.video_recycler);
        try {
            initView();
        }catch (Exception e){
            Log.e("",e.toString());
        }
        refresh();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        refresh();
    }

    private void refresh() {
        pageIndex = 1;
        itemCount = 20;
        getList(pageIndex,itemCount,true);
    }

    private void initView() {
        mDialog = new SpotsDialog(getContext(),R.style.DialogCustom);
        boolean canRemove = getArguments().getBoolean("canremove",false);
        VIDEO_TYPE = canRemove? 1:0;
        mVideoAdapter = new DiscoverVideoAdapter(getContext());
        mVideoAdapter.setCanRemove(canRemove);
        linearLayoutManager = new LinearLayoutManager(getContext());
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
                            int size = CommonUtil.dip2px(getContext(), 150);
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
        smallVideoHelper = new GSYVideoHelper(getContext(), new NormalGSYVideoPlayer(getContext()));
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
        mVideoAdapter.setOnItemClickListener(new OnItemClickListener<DynamicData.DataList>() {
            @Override
            public void onItemClick(View view, int position, DynamicData.DataList dataList) {
                Intent intent = new Intent(getContext(), DynamicActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", dataList);
                bundle.putInt("type",1);//video类型
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        mVideoAdapter.setLikeClickListener(new OnAdapterViewClickListener<DynamicData.DataList>() {
            @Override
            public void onAdapterClick(View view, int position, DynamicData.DataList dataList) {
                Log.e("第二步", "=======" + dataList.getState());
                addAndRemoveLikeData(dataList.getState() == 0 ? 1 : 0, dataList.getDynamicID() + "", position);
            }
        });

        mVideoAdapter.setOnAdapterViewClickListener(new OnAdapterViewClickListener<DynamicData.DataList>() {
            @Override
            public void onAdapterClick(View view, int position, DynamicData.DataList dataList) {
//                String content = dataList.getContent();
//                String dynamicerName = dataList.getDynamicerName();
//                long dynamicID = dataList.getDynamicID();
//                showShare(content, dynamicerName, dynamicID);
            }
        });
        if(canRemove){
            mVideoAdapter.setRemoveClickListener(new OnAdapterViewClickListener<DynamicData.DataList>() {
                @Override
                public void onAdapterClick(View view, int position, DynamicData.DataList dataList) {
//                    Log.e("DynamicID","" + dataList.getDynamicID());
//                    itemID = dataList.getDynamicID();
//                    itemPosition = position;
//                    showNormalDialog();
                }
            });
        }
    }


    private void getList(int pageIndex, int pageCount, final boolean isRefresh) {
        if (UserManager.getInstance(getContext()).isLogin()) {
            String userID = UserManager.getInstance(getContext()).getUserInfo().getUserID();
//            NetWorkUtil.setCallback("User/GetDynamicList",
//                    new DynamicBean(userID,VIDEO_TYPE == 1?userID:"0", VIDEO_TYPE, 0, 1, pageIndex, pageCount),
//                    new NetWorkUtil.CallBackListener() {
//                        @Override
//                        public void onFinish(final String respose) {
//                            Log.e("返回值",respose);
//                            mVideoRecyclerView.stopLoad();
//                            try {
//                                DynamicData result = new Gson().fromJson(respose,  DynamicData.class);
//                                if ("200".equals(result.getMessage().getCode())) {
//                                    Log.d("有数据====================", respose);
//                                    mVideoRecyclerView.setTotalCount(result.getTotalCount());
//                                    if(isRefresh){
//                                        mVideoAdapter.refresh(result.getData());
//                                        if(result.getData().size() > 0){
//                                            mVideoRecyclerView.setHasData(true);
//                                        }else {
//                                            mVideoRecyclerView.setHasData(false);
//                                        }
//                                    }else {
//                                        mVideoAdapter.addAll(result.getData());
//                                    }
//                                } else {
//                                    Toast.makeText(getContext(), result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
//                                }
//                            } catch (Exception e) {
//                                Log.e("json解析出错",e.toString());
//                                //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//                        @Override
//                        public void onError(final String e) {
//                            mVideoRecyclerView.stopLoad();
////                            Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
//                        }
//                    });
        }
    }

    private void addAndRemoveLikeData(final int state, String objectId, final int position) {
        mDialog.show();
        NetWorkUtil.setCallback("User/AddDelGivethumb",
                new DiscoverLikeBean(objectId, UserManager.getInstance(getContext()).getNewUserInfo().getUserId()),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(String respose) {
                        mDialog.dismiss();
                        Log.e("返回结果", respose);
                        ResultMessage resultMessage = new Gson().fromJson(respose, ResultMessage.class);
                        if (resultMessage.getMessage().getCode().equals("200")) {
                            Log.e("第三步", "=======" + state);
                            mVideoAdapter.itemChange(position, state);
                        } else {
                            Toast.makeText(getContext(), resultMessage.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(String e) {
                        mDialog.dismiss();
                    }
                });
    }


    private void removeDyanmic(final int position,long id) {
        mDialog.show();
        NetWorkUtil.setCallback("User/DelDynamic",
                new RemoveDynamicBean(id+""),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(String respose) {
                        mDialog.dismiss();
                        ResultMessage resultMessage = new Gson().fromJson(respose, ResultMessage.class);
                        if (resultMessage.getMessage().getCode().equals("200")) {
                            mVideoAdapter.remove(position);
                        }else {
                            Toast.makeText(getContext(), resultMessage.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(String e) {
                        mDialog.dismiss();
        //                Toast.makeText(getContext(), "请重试！", Toast.LENGTH_SHORT).show();
                    }
        });
    }


    private void showNormalDialog() {
        if(normalDialog == null){
            normalDialog = new AlertDialog.Builder(getContext());
            normalDialog.setTitle("确定要删除该动态么");
            //  normalDialog.setIcon(R.drawable.icon_dialog);
            // normalDialog.setMessage("你要点击哪一个按钮呢?");
            normalDialog.setPositiveButton("确定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //...To-do
                            removeDyanmic(itemPosition,itemID);
                            dialog.dismiss();
                        }
                    });
            normalDialog.setNegativeButton("关闭",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
        }
        normalDialog.show();
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
        EventBus.getDefault().unregister(this);
        smallVideoHelper.releaseVideoPlayer();
        GSYVideoManager.releaseAllVideos();
    }

    /**
     * 事件总线刷新
     * @param bean
     */
    @Subscribe
    public void onEventMainThread(final EventBusBean bean) {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                if(bean.getType() == Constants.VIDEO_FULL){
                    smallVideoHelper.backFromFull();
                }else if(bean.getType() == Constants.DYNAMIC_PUBLISHED){
                    smallVideoHelper.releaseVideoPlayer();
                    GSYVideoManager.releaseAllVideos();
                    refresh();
                }else if(bean.getType() == Constants.DYNAMIC_PUBLISHED){
                    refresh();
                }
            }
        });
    }

}
