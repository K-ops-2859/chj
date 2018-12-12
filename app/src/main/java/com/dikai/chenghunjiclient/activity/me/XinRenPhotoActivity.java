package com.dikai.chenghunjiclient.activity.me;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.awen.photo.photopick.controller.PhotoPagerConfig;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.PhotoPickActivity;
import com.dikai.chenghunjiclient.adapter.OnItemClickListener;
import com.dikai.chenghunjiclient.adapter.me.FailAdapter;
import com.dikai.chenghunjiclient.bean.BeanPhotoDetails;
import com.dikai.chenghunjiclient.bean.PhotoStatusBean;
import com.dikai.chenghunjiclient.entity.CustomerInfoData;
import com.dikai.chenghunjiclient.entity.PhotoDetailsData;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.dikai.chenghunjiclient.view.SpaceItemDecoration;
import com.flyco.dialog.widget.base.BaseDialog;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cmk03 on 2018/3/27.
 */

public class XinRenPhotoActivity extends AppCompatActivity implements View.OnClickListener {

    private MyLoadRecyclerView mRecyclerView;
    private FailAdapter mAdapter;
    private int pagerIndex = 1;
    private int pagerCount = 20;
    private long clientId;
    private Bundle bundle;
    private PhotoDetailsData photoDetailsData;
    private PhotoPagerConfig.Builder photoBuilder;
    private ArrayList<PhotoStatusBean> photoStatusBeen;
    private ArrayList<PhotoDetailsData.DataList> photoLists;
    private ArrayList<String> images = new ArrayList<>();
    private CustomerInfoData.DataList data;
    private LinearLayout llDataFailure;
    private ServiceDialog ruleDialog;
    private boolean isInit = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_xinren_photo);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        mRecyclerView = (MyLoadRecyclerView) findViewById(R.id.recycler_view);
        llDataFailure = (LinearLayout) findViewById(R.id.ll_data_failure);
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.rule).setOnClickListener(this);
        mRecyclerView.setGridLayout(3);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(10));
        mAdapter = new FailAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        photoBuilder = new PhotoPagerConfig.Builder(this, PhotoPickActivity.class);
        photoStatusBeen = new ArrayList<>();

        mRecyclerView.setLoadChangeListener(new MyLoadRecyclerView.OnLoadChangeListener() {
            @Override
            public void onRefresh() {
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
                ArrayList<PhotoDetailsData.DataList> datalist = mAdapter.getData();
                bundle.putInt("position", position);
                bundle.putInt("photoCount", datalist.size());
                bundle.putParcelableArrayList("dataList", datalist);
                for (PhotoDetailsData.DataList list : datalist) {
                    String fileUrl = list.getFileUrl();
                    images.add(fileUrl);
                }
                photoBuilder
                        .setBigImageUrls(images)
                        .setSavaImage(true)
                        .setPosition(position)
                        .setBundle(bundle)
                        .setOpenDownAnimate(true)
                        .build();
            }
        });
        clientId = getIntent().getLongExtra("id", 0);
        ruleDialog = new ServiceDialog(this);
        ruleDialog.widthScale(1);
        ruleDialog.heightScale(1);
        if (clientId == -1) {
            ruleDialog.show();
        } else {
            initData();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.rule:
                ruleDialog.show();
                break;
        }
    }

    private void initData() {
        pagerIndex = 1;
        NetWorkUtil.setCallback("User/GetFileSupplier", new BeanPhotoDetails("0", clientId + "", "4", "1", "", "", pagerIndex + "", pagerCount + ""), new NetWorkUtil.CallBackListener() {
            @Override
            public void onFinish(String respose) {
                photoDetailsData = new Gson().fromJson(respose, PhotoDetailsData.class);
                if (photoDetailsData.getMessage().getCode().equals("200")) {
                    mRecyclerView.setTotalCount(photoDetailsData.getTotalCount());
                    if (photoDetailsData.getData().size() == 0) {
                        llDataFailure.setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.GONE);
                        if (isInit) {
                            ruleDialog.show();
                            isInit = false;
                        }
                    } else {
                        llDataFailure.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mAdapter.setList(photoDetailsData.getData());
                        photoLists = photoDetailsData.getData();
                        images.clear();
                    }
                } else {
                    Toast.makeText(XinRenPhotoActivity.this, photoDetailsData.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                }
                mRecyclerView.stopLoad();
            }

            @Override
            public void onError(String e) {

            }
        });
    }

    private void loadMore() {
        pagerIndex++;
        clientId = getIntent().getLongExtra("id", 0);
        NetWorkUtil.setCallback("User/GetFileSupplier", new BeanPhotoDetails("0", clientId + "", "4", "1", "", "", pagerIndex + "", pagerCount + ""), new NetWorkUtil.CallBackListener() {
            @Override
            public void onFinish(String respose) {
                photoDetailsData = new Gson().fromJson(respose, PhotoDetailsData.class);
                if (photoDetailsData.getMessage().getCode().equals("200")) {
                    mRecyclerView.setTotalCount(photoDetailsData.getTotalCount());
                    if (photoDetailsData.getData().size() == 0) {
                        llDataFailure.setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.GONE);
                    } else {
                        llDataFailure.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mAdapter.append(photoDetailsData.getData());
                        photoLists = photoDetailsData.getData();
                    }

                } else {
                    Toast.makeText(XinRenPhotoActivity.this, photoDetailsData.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                }
                mRecyclerView.stopLoad();
            }

            @Override
            public void onError(String e) {

            }
        });
    }

    /**
     * rule
     */
    private class ServiceDialog extends BaseDialog<ServiceDialog> implements View.OnClickListener {
        private TextView close;

        public ServiceDialog(Context context) {
            super(context);
        }

        @Override
        public View onCreateView() {
            View view = View.inflate(mContext, R.layout.dialog_photo_video_know, null);
            close = (TextView) view.findViewById(R.id.close);
            close.setOnClickListener(this);
            return view;
        }

        @Override
        public void onViewCreated(View view) {
            super.onViewCreated(view);
        }

        @Override
        public void setUiBeforShow() {
        }

        @Override
        public void onClick(View v) {
            if (v == close) {
                this.dismiss();
            }
        }
    }


    @Subscribe
    public void onEventMainThread(EventBusBean event) {
        if (event.getType() == 200) {
            initData();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
                EventBus.getDefault().unregister(this);
                ImmersionBar.with(this).destroy();
                }
                }