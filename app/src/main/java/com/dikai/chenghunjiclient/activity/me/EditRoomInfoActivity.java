package com.dikai.chenghunjiclient.activity.me;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.bean.BeanEditRoom;
import com.dikai.chenghunjiclient.entity.NewUserInfo;
import com.dikai.chenghunjiclient.entity.ResultGetRoomInfo;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;

import dmax.dialog.SpotsDialog;

public class EditRoomInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private SpotsDialog mDialog;
    private EditText nameEdit;
    private EditText areaEdit;
    private EditText lengthEdit;
    private EditText widthEdit;
    private EditText heightEdit;
    private EditText minNumEdit;
    private EditText maxNumEdit;
    private ResultGetRoomInfo mRoomInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_room_info);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {

        mRoomInfo = (ResultGetRoomInfo) getIntent().getSerializableExtra("bean");
        mDialog = new SpotsDialog(this,R.style.DialogCustom);
        findViewById(R.id.cancel).setOnClickListener(this);
        findViewById(R.id.save).setOnClickListener(this);
        nameEdit = (EditText) findViewById(R.id.name);
        areaEdit = (EditText) findViewById(R.id.area);
        lengthEdit = (EditText) findViewById(R.id.length);
        widthEdit = (EditText) findViewById(R.id.width);
        heightEdit = (EditText) findViewById(R.id.height);
        minNumEdit = (EditText) findViewById(R.id.min_num);
        maxNumEdit = (EditText) findViewById(R.id.max_num);
        setData(mRoomInfo);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                onBackPressed();
                break;
            case R.id.save:
                if(nameEdit.getText() == null || "".equals(nameEdit.getText().toString().trim())){
                    Toast.makeText(this, "请输入宴会厅名称", Toast.LENGTH_SHORT).show();
                }else if(areaEdit.getText() == null || "".equals(areaEdit.getText().toString().trim())){
                    Toast.makeText(this, "请输入宴会厅面积", Toast.LENGTH_SHORT).show();
                }else if(lengthEdit.getText() == null || "".equals(lengthEdit.getText().toString().trim())){
                    Toast.makeText(this, "请输入宴会厅长度", Toast.LENGTH_SHORT).show();
                }else if(widthEdit.getText() == null || "".equals(widthEdit.getText().toString().trim())){
                    Toast.makeText(this, "请输入宴会厅宽度", Toast.LENGTH_SHORT).show();
                }else if(heightEdit.getText() == null || "".equals(heightEdit.getText().toString().trim())){
                    Toast.makeText(this, "请输入宴会厅层高", Toast.LENGTH_SHORT).show();
                }else if(minNumEdit.getText() == null || "".equals(minNumEdit.getText().toString().trim())){
                    Toast.makeText(this, "请输入可容纳最小桌数", Toast.LENGTH_SHORT).show();
                }else if(maxNumEdit.getText() == null || "".equals(maxNumEdit.getText().toString().trim())){
                    Toast.makeText(this, "请输入可容纳最大桌数", Toast.LENGTH_SHORT).show();
                }else {
                    mDialog.show();
                    String pics = "";
                    editRoom(nameEdit.getText().toString(),maxNumEdit.getText().toString(),minNumEdit.getText().toString(), pics,
                            areaEdit.getText().toString(),lengthEdit.getText().toString(),widthEdit.getText().toString(),heightEdit.getText().toString());
                }
                break;
        }
    }

    private void setData(ResultGetRoomInfo result) {
        nameEdit.setText(result.getBanquetHallName());
        areaEdit.setText(result.getAcreage());
        lengthEdit.setText(result.getLength());
        widthEdit.setText(result.getWidth());
        heightEdit.setText(result.getHeight());
        minNumEdit.setText(result.getMinTableCount());
        maxNumEdit.setText(result.getMaxTableCount());
    }

    /**
     * 修改宴会厅
     */
    private void editRoom(String name, String max, String min, String pics, String area, String length,String width,String height){
        NewUserInfo info = UserManager.getInstance(this).getNewUserInfo();
        NetWorkUtil.setCallback("HQOAApi/UpBanquetHallInfo",
                new BeanEditRoom(name,"0",max,min,pics,info.getFacilitatorId(),mRoomInfo.getBanquetID(),area,length,width,height),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                Toast.makeText(EditRoomInfoActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                EventBus.getDefault().post(new EventBusBean(Constants.ADD_NEW_ROOM));
                                finish();
                            } else {
                                Toast.makeText(EditRoomInfoActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Toast.makeText(EditRoomInfoActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onDestroy() {
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}
