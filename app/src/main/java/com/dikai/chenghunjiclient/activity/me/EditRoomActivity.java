package com.dikai.chenghunjiclient.activity.me;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.store.RoomPhotoActivity;
import com.dikai.chenghunjiclient.adapter.me.EditRoomAdapter;
import com.dikai.chenghunjiclient.adapter.store.RoomPhotoAdapter;
import com.dikai.chenghunjiclient.bean.BeanDeleteRoom;
import com.dikai.chenghunjiclient.bean.BeanGetDocument;
import com.dikai.chenghunjiclient.bean.BeanGetHotelInfo;
import com.dikai.chenghunjiclient.bean.BeanID;
import com.dikai.chenghunjiclient.entity.NewUserInfo;
import com.dikai.chenghunjiclient.entity.ResultGetDocument;
import com.dikai.chenghunjiclient.entity.ResultGetRooms;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.entity.RoomBean;
import com.dikai.chenghunjiclient.entity.UserInfo;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;
import com.flyco.dialog.widget.popup.base.BasePopup;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import dmax.dialog.SpotsDialog;

public class EditRoomActivity extends AppCompatActivity implements View.OnClickListener {

    private MyLoadRecyclerView mRecyclerView;
    private EditRoomAdapter mAdapter;
    private SimpleCustomPop mSimpleCustomPop;
    private int mPosition;
    private RoomBean mRoomBean;
    private MaterialDialog dialog;
    private SpotsDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_edit_room);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        mDialog = new SpotsDialog(this, R.style.DialogCustom);
        mRecyclerView = (MyLoadRecyclerView) findViewById(R.id.activity_photo_recycler);
        findViewById(R.id.activity_my_team_back).setOnClickListener(this);
        findViewById(R.id.activity_my_team_edit).setOnClickListener(this);
        mAdapter = new EditRoomAdapter(this);
        mAdapter.setMoreClickListener(new EditRoomAdapter.OnMoreClickListener() {
            @Override
            public void onClick(View view, int position, RoomBean bean) {
                mPosition = position;
                mRoomBean = bean;
                mSimpleCustomPop.anchorView(view).gravity(Gravity.BOTTOM).show();
            }
        });
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
        initDialog();
        refresh();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_my_team_back:
                onBackPressed();
                break;
            case R.id.activity_my_team_edit:
                startActivity(new Intent(this, AddRoomActivity.class));
                break;
        }
    }

    private void refresh() {
        getList();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        ImmersionBar.with(this).destroy(); //必须调用该方法，防止内存泄漏
        super.onDestroy();
    }

    /**
     * 获取信息
     */
    private void getList(){
        NewUserInfo info = UserManager.getInstance(this).getNewUserInfo();
        NetWorkUtil.setCallback("HQOAApi/GetBanquetHallList",
                new BeanGetHotelInfo(info.getFacilitatorId()),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mRecyclerView.stopLoad();
                        Log.e("返回值",respose);
                        try {
                            ResultGetRooms result = new Gson().fromJson(respose, ResultGetRooms.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                mAdapter.refresh(result.getData());
                            } else {
                                Toast.makeText(EditRoomActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mRecyclerView.stopLoad();
                        Toast.makeText(EditRoomActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 删除宴会厅
     */
    private void deleteRoom(){
        mDialog.show();
        NetWorkUtil.setCallback("HQOAApi/DeleteHotelBanquetl",
                new BeanID(mRoomBean.getBanquetID()),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                mAdapter.remove(mPosition);
                                Toast.makeText(EditRoomActivity.this, "删除成功！", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(EditRoomActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Toast.makeText(EditRoomActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initDialog() {
        dialog = new MaterialDialog(this);
        dialog.isTitleShow(false)//
                .btnNum(2)
                .content("是否删除此宴会厅？")//
                .btnText("取消", "确定")
                .setOnBtnClickL(new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                    }
                }, new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                        deleteRoom();
                    }
                });
        mSimpleCustomPop = new SimpleCustomPop(this);
        mSimpleCustomPop.setOnMoreListener(new OnMoreListener() {
            @Override
            public void onClick(int position) {
                mSimpleCustomPop.dismiss();
                if(position == 0){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("bean",mRoomBean);
                    bundle.putInt("type",1);
                    startActivity(new Intent(EditRoomActivity.this, AddRoomActivity.class).putExtras(bundle));
                }else {
                    dialog.show();
                }
            }
        });
    }

    /**
     * 右上角弹窗
     */
    public static class SimpleCustomPop extends BasePopup<SimpleCustomPop> implements View.OnClickListener {

        private View mDialogView;
        private OnMoreListener mOnMoreListener;

        public void setOnMoreListener(OnMoreListener onMoreListener) {
            mOnMoreListener = onMoreListener;
        }

        public SimpleCustomPop(Context context) {
            super(context);
        }

        @Override
        public View onCreatePopupView() {
            mDialogView = View.inflate(getContext(), R.layout.dialog_float_black_layout, null);
            mDialogView.findViewById(R.id.item_first_text).setOnClickListener(this);
            mDialogView.findViewById(R.id.item_second_text).setOnClickListener(this);
            return  mDialogView;
        }

        @Override
        public void setUiBeforShow() {

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.item_first_text:
                    mOnMoreListener.onClick(0);
                    break;
                case R.id.item_second_text:
                    mOnMoreListener.onClick(1);
                    break;
            }
        }
    }

    private interface OnMoreListener{
        void onClick(int position);
    }

    /**
     * 事件总线刷新
     * @param bean
     */
    @Subscribe
    public void onEventMainThread(final EventBusBean bean) {
        runOnUiThread(new Runnable() {
            public void run() {
                if(bean.getType() == Constants.ADD_ROOM_SUCCESS){
                    refresh();
                }
            }
        });
    }
}
