package com.dikai.chenghunjiclient.fragment.me;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.awen.photo.photopick.controller.PhotoPagerConfig;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.PhotoPickActivity;
import com.dikai.chenghunjiclient.adapter.OnItemClickListener;
import com.dikai.chenghunjiclient.adapter.me.FailAdapter;
import com.dikai.chenghunjiclient.bean.BeanPhotoDetails;
import com.dikai.chenghunjiclient.bean.PhotoStatusBean;
import com.dikai.chenghunjiclient.entity.PhotoDetailsData;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.dikai.chenghunjiclient.view.SpaceItemDecoration;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

/**
 * Created by cmk03 on 2018/3/20.
 */

public class AllFragment extends Fragment {

    private Context mContext;
    private int pagerIndex = 1;
    private int pagerCount = 20;
    private FailAdapter mAdapter;
    private MyLoadRecyclerView mRecyclerView;
    // private VideoPhotoData.DataList data;
    private String clientId;
    private int photoSize;
    private PhotoDetailsData photoDetailsData;
    private PhotoPagerConfig.Builder photoBuilder;
    private Bundle bundle;
    private ArrayList<PhotoStatusBean> photoStatusBeen;
    private int photoCount;
    private ArrayList<PhotoDetailsData.DataList> photoLists;
    private ArrayList<String> images = new ArrayList<>();
    private LinearLayout llDataFailure;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    public static AllFragment newInstance(String clientId) {
        AllFragment allFragment = new AllFragment();
        Bundle bundle = new Bundle();
        bundle.putString("clientId", clientId);
        //bundle.putSerializable("clientId", clientId);
        allFragment.setArguments(bundle);
        return allFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_all, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getContext();
        mRecyclerView = (MyLoadRecyclerView) view.findViewById(R.id.recycler_view);
        llDataFailure = (LinearLayout) view.findViewById(R.id.ll_data_failure);
        mRecyclerView.setGridLayout(3);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(10));
        mAdapter = new FailAdapter(mContext);
        mRecyclerView.setAdapter(mAdapter);
        photoBuilder = new PhotoPagerConfig.Builder((Activity) mContext, PhotoPickActivity.class);
        photoStatusBeen = new ArrayList<>();
        initData();

        mRecyclerView.setLoadChangeListener(new MyLoadRecyclerView.OnLoadChangeListener() {
            @Override
            public void onRefresh() {
                pagerIndex = 1;
                initData();
            }

            @Override
            public void onLoadMore() {
                loadMore();
            }
        });

        mAdapter.setOnItemClickListener(new OnItemClickListener<PhotoDetailsData.DataList>() {
            @Override
            public void onItemClick(View v, int position, PhotoDetailsData.DataList dataList) {
                if (bundle == null) {
                    bundle = new Bundle();
                }
                bundle.putInt("position", position);
                bundle.putInt("photoCount", photoLists.size());
                bundle.putParcelableArrayList("dataList", photoLists);
                photoBuilder
                        .setBigImageUrls(images)
                        .setSavaImage(true)
                        .setPosition(position)
                        .setBundle(bundle) //传递自己的数据，如果数据中包含java bean，必须实现Parcelable接口
                        .setOpenDownAnimate(true)
                        .build();

            }
        });
    }

    private void initData() {
        if (clientId == null) {
            Bundle bundle = getArguments();
            clientId = bundle.getString("clientId");
        }
        NetWorkUtil.setCallback("User/GetFileSupplier", new BeanPhotoDetails("0", clientId + "", "0", "1", "", "", pagerIndex + "", pagerCount + ""), new NetWorkUtil.CallBackListener() {
            @Override
            public void onFinish(String respose) {
                 photoDetailsData = new Gson().fromJson(respose, PhotoDetailsData.class);
                if (photoDetailsData.getMessage().getCode().equals("200")) {
                    if (photoDetailsData.getData().size() == 0) {
                        llDataFailure.setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.GONE);
                    } else {
                        llDataFailure.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mAdapter.setList(photoDetailsData.getData());
                        photoLists = photoDetailsData.getData();
                        images.clear();
                        for (PhotoDetailsData.DataList dataList1 : AllFragment.this.photoLists) {
                            images.add(dataList1.getFileUrl());
                        }
                    }
                } else {
                    Toast.makeText(mContext, photoDetailsData.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                }
                mRecyclerView.stopLoad();
            }

            @Override
            public void onError(String e) {

            }
        });
    }

    private void loadMore() {
        pagerCount = 2;
        if (clientId == null) {
            Bundle bundle = getArguments();
            clientId = bundle.getString("clientId");
        }
        NetWorkUtil.setCallback("User/GetFileSupplier", new BeanPhotoDetails("0", clientId + "", "0", "1", "", "", pagerIndex + "", pagerCount + ""), new NetWorkUtil.CallBackListener() {
            @Override
            public void onFinish(String respose) {
                photoDetailsData = new Gson().fromJson(respose, PhotoDetailsData.class);
                if (photoDetailsData.getMessage().getCode().equals("200")) {
                    mAdapter.append(photoDetailsData.getData());
                    photoLists = photoDetailsData.getData();
                    for (PhotoDetailsData.DataList dataList1 : AllFragment.this.photoLists) {
                        images.add(dataList1.getFileUrl());
                    }
                    pagerCount++;
                } else {
                    Toast.makeText(mContext, photoDetailsData.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                }
                mRecyclerView.stopLoad();
            }

            @Override
            public void onError(String e) {

            }
        });
    }


    @Subscribe
    public void onEventMainThread(EventBusBean event) {
        if (event.getType() == 200) {
            System.out.println("=============" + event.getType());
            initData();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
