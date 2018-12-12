package com.dikai.chenghunjiclient.activity.store;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.store.RoomPhotoAdapter;
import com.dikai.chenghunjiclient.bean.BeanGetDocument;
import com.dikai.chenghunjiclient.bean.BeanID;
import com.dikai.chenghunjiclient.entity.ResultGetDocument;
import com.dikai.chenghunjiclient.entity.ResultGetRoomInfo;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import java.util.Arrays;
import java.util.List;

public class RoomPhotoActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton mBack;
    private TextView mTextView;
    private MyLoadRecyclerView mRecyclerView;
    private RoomPhotoAdapter mAdapter;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_photo);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        id = getIntent().getStringExtra("id");
        mBack = (ImageButton) findViewById(R.id.activity_photo_back);
        mTextView = (TextView) findViewById(R.id.activity_photo_title);
        mRecyclerView = (MyLoadRecyclerView) findViewById(R.id.activity_photo_recycler);
        mRecyclerView.setGridLayout(2);
        mAdapter = new RoomPhotoAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLoadChangeListener(new MyLoadRecyclerView.OnLoadChangeListener() {
            @Override
            public void onRefresh() {
                refresh();
            }

            @Override
            public void onLoadMore() {
            }
        });
        mBack.setOnClickListener(this);
        refresh();
    }

    @Override
    public void onClick(View v) {
        if(v == mBack){
            onBackPressed();
        }
    }

    private void refresh() {
        getList();
//        String s = "http://121.42.156.151:96/2/0/0/2/0/9fcbf38f-4832-48e7-af69-a94b62a61b58.png,http://121.42.156.151:96/2/0/0/2/0/195a2adb-7d40-4b2f-99af-226da0645c08.png,http://121.42.156.151:96/2/0/0/2/0/87899c73-5e1b-4fc5-8448-55b9d8d4cd54.png";
//        List<String> list = Arrays.asList(s.split(","));
//        Log.e("list",list.toString()+list.size());
//        mAdapter.refresh(list);
//        mRecyclerView.stopLoad();
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy(); //必须调用该方法，防止内存泄漏
        super.onDestroy();
    }

    /**
     * 获取信息
     */
    private void getList(){
        NetWorkUtil.setCallback("HQOAApi/GetHotelBanquetlInfo ",
                new BeanID(id),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mRecyclerView.stopLoad();
                        Log.e("返回值",respose);
                        try {
                            ResultGetRoomInfo result = new Gson().fromJson(respose, ResultGetRoomInfo.class);
                            if ("200".equals(result.getMessage().getCode())) {
//                                mAdapter.refresh(Arrays.asList(result.getTypeQuestion().split(",")));
                            } else {
                                Toast.makeText(RoomPhotoActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mRecyclerView.stopLoad();
                        Toast.makeText(RoomPhotoActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
