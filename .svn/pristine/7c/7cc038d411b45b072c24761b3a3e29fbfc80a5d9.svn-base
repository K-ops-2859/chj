package com.dikai.chenghunjiclient.activity.wedding;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.PhotoActivity;
import com.dikai.chenghunjiclient.adapter.OnItemClickListener;
import com.dikai.chenghunjiclient.adapter.wedding.TaocanPhotoAdapter;
import com.dikai.chenghunjiclient.bean.BeanGetComboArea;
import com.dikai.chenghunjiclient.bean.TaocanPhotoBean;
import com.dikai.chenghunjiclient.entity.CusTaocanPhoto;
import com.dikai.chenghunjiclient.entity.TaocanPhotoData;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cmk03 on 2018/6/8.
 */

public class TaocanPhotoActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<CusTaocanPhoto> cusData = new ArrayList<>();
    private TaocanPhotoAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<String> imageList = new ArrayList<>();
    private boolean isBig = true;
    private int scrollPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taocanphoto);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();

        final ImageView ivBack = (ImageView) findViewById(R.id.iv_back);
        final ImageView ivSwitch = (ImageView) findViewById(R.id.iv_switch);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view1);
        mAdapter = new TaocanPhotoAdapter(this);
        setManager(0);
        mRecyclerView.setAdapter(mAdapter);

        String id = getIntent().getStringExtra("id");
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ivSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBig) {
                    setManager(1);
                    isBig = false;
                    ivSwitch.setImageResource(R.mipmap.ic_switch_small);
                } else {
                    setManager(0);
                    isBig = true;
                    ivSwitch.setImageResource(R.mipmap.ic_switch_big);
                }
            }
        });

        mAdapter.setOnItemClickListener(new OnItemClickListener<List<String>>() {
            @Override
            public void onItemClick(View view, int position, List<String> strings) {
                Intent intent = new Intent(TaocanPhotoActivity.this, PhotoActivity.class);
                List<String> subList = strings.subList(position - 1, strings.size());
                ArrayList<String> list = new ArrayList<>(subList);
                intent.putStringArrayListExtra("imgs", list);
                startActivity(intent);

//                imageList.clear();
//                Intent intent = new Intent(TaocanPhotoActivity.this, PhotoActivity.class);
//                System.out.println("position=======" + position + "====list.size--" + imageList.size());
//                imageList.add(cusTaocanPhoto.getImage());
//                //List<String> strings = imageList.subList(position, imageList.size());
//                ArrayList<String> list = new ArrayList<>(imageList);
//                intent.putStringArrayListExtra("imgs", list);
//                startActivity(intent);

            }
        });

        initData(id);
    }

    private void setManager(int type) {
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }
        if (type == 0) {
            mLayoutManager = new LinearLayoutManager(this);
        } else {
            mLayoutManager = new GridLayoutManager(this, 3);
            if (mLayoutManager instanceof GridLayoutManager) {
//            final GridLayoutManager.SpanSizeLookup originalSpanSizeLookup =
//                    ((GridLayoutManager) layoutManager).getSpanSizeLookup();
                ((GridLayoutManager) mLayoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return cusData.get(position).isTitle() ? 3 : 1;
                    }
                });
            }
        }
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
        mAdapter.setList(cusData);
    }


    private void initData(String schemeId) {
        imageList.clear();
        NetWorkUtil.setCallback("HQOAApi/GetWeddingPackageAreaImg",
                new BeanGetComboArea(""+schemeId,"0"), new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(String respose) {
                try {
                    final TaocanPhotoData data = new Gson().fromJson(respose, TaocanPhotoData.class);
                    if (data.getMessage().getCode().equals("200")) {
                        List<TaocanPhotoData.DataList> data1 = data.getData();

                        for (int i = 0; i < data1.size(); i++) {
                            TaocanPhotoData.DataList dataList = data1.get(i);
                            if (!TextUtils.isEmpty(dataList.getAreaName())) {
                                cusData.add(new CusTaocanPhoto(true, dataList.getAreaId(), dataList.getAreaName(), i));
                            } else {
                                cusData.add(new CusTaocanPhoto(false, dataList.getAreaId(), dataList.getAreaName(), i));
                            }
                            for (TaocanPhotoData.ImageList imags : dataList.getImageData()) {
                                String image = imags.getImage();
                                if (image == null) {
                                    System.out.println("图片返回有空值=============");
                                }
                                if (image != null) {
                                    cusData.add(new CusTaocanPhoto(image));
                                }
                            }

                        }
                        mAdapter.setList(cusData);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String e) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
}
