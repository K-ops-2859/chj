package com.dikai.chenghunjiclient.activity.me;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.me.NewRoomAdapter;
import com.dikai.chenghunjiclient.bean.BeanID;
import com.dikai.chenghunjiclient.entity.ResultGetRooms;
import com.dikai.chenghunjiclient.entity.RoomBean;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import dmax.dialog.SpotsDialog;

public class NewRoomActivity extends AppCompatActivity implements View.OnClickListener {

    private SpotsDialog mDialog;
    private ImageView place;
    private MyLoadRecyclerView mRecyclerView;
    private NewRoomAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_room);
        EventBus.getDefault().register(this);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        mDialog = new SpotsDialog(this,R.style.DialogCustom);
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.add).setOnClickListener(this);
        place = (ImageView) findViewById(R.id.place);
        mRecyclerView = (MyLoadRecyclerView) findViewById(R.id.recycler);
        mRecyclerView.setLoadChangeListener(new MyLoadRecyclerView.OnLoadChangeListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
            @Override
            public void onLoadMore() {}
        });
        mAdapter = new NewRoomAdapter(this);
        mAdapter.setOnItemClickListener(new NewRoomAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position, RoomBean bean) {
                startActivity(new Intent(NewRoomActivity.this,EditNewRoomActivity.class).putExtra("roomid",bean.getBanquetID()));
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        refresh();
    }

    private void refresh() {
        getList(UserManager.getInstance(this).getNewUserInfo().getFacilitatorId());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.add:
                startActivity(new Intent(this,AddNewRoomActivity.class));
                break;
        }
    }


    /**
     * 获取信息
     */
    private void getList(String supId){
        NetWorkUtil.setCallback("HQOAApi/GetBanquetHallList",
                new BeanID(supId),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mRecyclerView.stopLoad();
                        Log.e("返回值",respose);
                        try {
                            ResultGetRooms result = new Gson().fromJson(respose, ResultGetRooms.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                mAdapter.refresh(result.getData());
                                if(result.getData().size() == 0){
                                    place.setVisibility(View.VISIBLE);
                                }else {
                                    place.setVisibility(View.GONE);
                                }
                            } else {
                                Toast.makeText(NewRoomActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mRecyclerView.stopLoad();
                        Toast.makeText(NewRoomActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 事件总线刷新
     * @param bean
     */
    @Subscribe
    public void onEventMainThread(final EventBusBean bean) {
        runOnUiThread(new Runnable() {
            public void run() {
                if(bean.getType() == Constants.ADD_NEW_ROOM){
                    refresh();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}
