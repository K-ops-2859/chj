package com.dikai.chenghunjiclient.activity.me;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.PhotoActivity;
import com.dikai.chenghunjiclient.adapter.me.RoomPicsAdapter;
import com.dikai.chenghunjiclient.bean.BeanID;
import com.dikai.chenghunjiclient.entity.ImgBean;
import com.dikai.chenghunjiclient.entity.ResultGetRoomInfo;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.flyco.dialog.widget.MaterialDialog;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;

public class EditNewRoomActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView name;
    private TextView area;
    private TextView height;
    private TextView table;
    private TextView photoNum;
    private SpotsDialog mDialog;
    private RoomPicsAdapter mAdapter;
    private ActionSheetDialog moreDialog;
    private MaterialDialog dialog2;
    private String id;
    private ResultGetRoomInfo mRoomInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_new_room);
        EventBus.getDefault().register(this);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        id = getIntent().getStringExtra("roomid");
        mDialog = new SpotsDialog(this,R.style.DialogCustom);
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.more).setOnClickListener(this);
        findViewById(R.id.info_edit).setOnClickListener(this);
        findViewById(R.id.photo_edit).setOnClickListener(this);
        name = (TextView) findViewById(R.id.name);
        area = (TextView) findViewById(R.id.area);
        height = (TextView) findViewById(R.id.height);
        table = (TextView) findViewById(R.id.table);
        photoNum = (TextView) findViewById(R.id.photo_num);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL));
        mRecyclerView.setVerticalScrollBarEnabled(true);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setNestedScrollingEnabled(false);
        mAdapter = new RoomPicsAdapter(this);
        mAdapter.setOnItemClickListener(new RoomPicsAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position, ImgBean bean) {
                Intent intent = new Intent(EditNewRoomActivity.this, PhotoActivity.class);
                intent.putExtra("now", position);
                intent.putStringArrayListExtra("imgs", mAdapter.getAllPhoto());
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        dialog2 = new MaterialDialog(this);
        dialog2.isTitleShow(false)//
                .btnNum(2)
                .content("确定删除此宴会厅吗")//
                .btnText("取消", "确定")
                .setOnBtnClickL(new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog2.dismiss();
                    }
                }, new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog2.dismiss();
                        delete(id);
                    }
                });
        final String[] stringItems = {"删除宴会厅"};
        moreDialog = new ActionSheetDialog(this, stringItems,null);
        moreDialog.isTitleShow(false)
                .setOnOperItemClickL(new OnOperItemClickL() {
                    @Override
                    public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                        moreDialog.dismiss();
                        if (position == 0) {
                            dialog2.show();
                        }
                    }
                });
        getInfo(id);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.more:
                moreDialog.show();
                break;
            case R.id.info_edit:
                Bundle bundle = new Bundle();
                bundle.putSerializable("bean",mRoomInfo);
                startActivity(new Intent(this,EditRoomInfoActivity.class).putExtras(bundle));
                break;
            case R.id.photo_edit:
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("bean",mRoomInfo);
                startActivity(new Intent(this,EditRoomPhotoActivity.class).putExtras(bundle2));
                break;
        }
    }

    private void setData(ResultGetRoomInfo result) {
        mRoomInfo = result;
        name.setText(result.getBanquetHallName());
        area.setText("面积  "+result.getAcreage()+"㎡");
        height.setText("层高  "+result.getHeight()+"m");
        table.setText("最多容纳"+result.getMaxTableCount()+"桌");
        photoNum.setText("相册   ("+result.getData().size()+")");
        mAdapter.refresh(result.getData());
    }

    /**
     * 获取信息
     */
    private void getInfo(String id){
        NetWorkUtil.setCallback("HQOAApi/GetHotelBanquetlInfo",
                new BeanID(id),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultGetRoomInfo result = new Gson().fromJson(respose, ResultGetRoomInfo.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                setData(result);
                            } else {
                                Toast.makeText(EditNewRoomActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(CorpInfoActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        Toast.makeText(EditNewRoomActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 获取信息
     */
    private void delete(String id){
        mDialog.show();
        NetWorkUtil.setCallback("HQOAApi/DeleteHotelBanquetl",
                new BeanID(id),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                Toast.makeText(EditNewRoomActivity.this, "删除成功！", Toast.LENGTH_SHORT).show();
                                EventBus.getDefault().post(new EventBusBean(Constants.ADD_NEW_ROOM));
                                finish();
                            } else {
                                Toast.makeText(EditNewRoomActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(CorpInfoActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Toast.makeText(EditNewRoomActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
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
                    getInfo(id);
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
