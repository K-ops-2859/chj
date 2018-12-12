package com.dikai.chenghunjiclient.fragment.wedding;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.discover.MessageListActivity;
import com.dikai.chenghunjiclient.activity.store.NewArticleActivity;
import com.dikai.chenghunjiclient.adapter.OnItemClickListener;
import com.dikai.chenghunjiclient.adapter.wedding.WeddingNoteAdapter;
import com.dikai.chenghunjiclient.adapter.wedding.WeddingNoteAdapter2;
import com.dikai.chenghunjiclient.bean.MessageBean;
import com.dikai.chenghunjiclient.entity.MessageData;
import com.dikai.chenghunjiclient.fragment.BaseLazyFragment;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.OldNetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MRecyclerView;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.dikai.chenghunjiclient.view.MyRecyclerView;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cmk03 on 2018/5/9.
 */

public class WeddingNoteFragment extends BaseLazyFragment {

    private WeddingNoteAdapter mAdapter;
    private XRecyclerView mRecyclerView;
    private int pageIndex = 1;
    private int pageCount = 10;
    private String weddingInformationID;
    private String title = "";
    private Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EventBus.getDefault().register(this);
    }

    public static WeddingNoteFragment newInstance(String weddingId) {
        WeddingNoteFragment fragment = new WeddingNoteFragment();
        Bundle bdl = new Bundle(1);
        bdl.putString("id", weddingId);
        fragment.setArguments(bdl);
        return fragment;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_wedding_note;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initViewsAndEvents(View view) {
        mContext = getContext();
        weddingInformationID = getArguments().getString("id");
        mRecyclerView = (XRecyclerView) view.findViewById(R.id.recycler_view);
        //mRecyclerView.setNestedScrollingEnabled(true);
        mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new WeddingNoteAdapter(mContext);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                pageIndex++;
                loadMore();
            }
        });


//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {、
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (isVisBottom(recyclerView)) {
//                    System.out.println("======" + "到底了");
//                }
//            }
//        });

        mAdapter.setOnItemClickListener(new OnItemClickListener<MessageData.DataList>() {
            @Override
                public void onItemClick(View view, int position, MessageData.DataList dataList) {
                    Intent intent = new Intent(getContext(), NewArticleActivity.class);
                    if (dataList != null) {
                        intent.putExtra("news", dataList.getInformationArticleID());
                        startActivity(intent);
                    }
            }
        });

    }

    @Override
    public void onFirstUserVisible() {
//        initList(weddingInformationID, "");
    }

    @Override
    public void onUserVisible() {
        //  initList(weddingInformationID, "");
    }

    @Override
    public void onUserInvisible() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initList(weddingInformationID, "");
    }

    @Override
    protected void DetoryViewAndEvents() {

    }

    private void initList(String weddingInformationID, String likeTitle) {
        pageIndex = 1;
        pageCount = 10;
        NetWorkUtil.setCallback("HQOAApi/GetInformationArticleList",
                new MessageBean(pageIndex, pageCount, weddingInformationID, likeTitle),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(String respose) {
                        try {
                            Log.e("返回值",respose);
                            MessageData messageData = new Gson().fromJson(respose, MessageData.class);
                            if (messageData.getMessage().getCode().equals("200")) {
                                //Toast.makeText(mContext, "成功", Toast.LENGTH_SHORT).show();
                                List<MessageData.DataList> data = messageData.getData();
                                mAdapter.setList(data);
                            } else {
                                Toast.makeText(getContext(), messageData.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            Log.e("",e.toString());
                        }
                    }

                    @Override
                    public void onError(String e) {

                    }
        });
    }

    private void loadMore() {
        NetWorkUtil.setCallback("HQOAApi/GetInformationArticleList",
                new MessageBean(pageIndex, pageCount, weddingInformationID, ""), new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(String respose) {
                        try {
                            MessageData messageData = new Gson().fromJson(respose, MessageData.class);
                            if (messageData.getMessage().getCode().equals("200")) {
                                //Toast.makeText(mContext, "成功", Toast.LENGTH_SHORT).show();
                                List<MessageData.DataList> data = messageData.getData();
                                mAdapter.append(data);
                            } else {
                                Toast.makeText(getContext(), messageData.getMessage().getInform(), Toast.LENGTH_SHORT).show();

                            }
                            mRecyclerView.refreshComplete();
                        }catch (Exception e){
                            Log.e("",e.toString());
                        }
                    }

                    @Override
                    public void onError(String e) {

                    }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //EventBus.getDefault().unregister(this);
    }
}
