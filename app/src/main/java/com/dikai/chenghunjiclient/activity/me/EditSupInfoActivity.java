package com.dikai.chenghunjiclient.activity.me;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.me.AddImgAdapter;
import com.dikai.chenghunjiclient.adapter.me.SupcanbiaoAdapter;
import com.dikai.chenghunjiclient.bean.BeanID;
import com.dikai.chenghunjiclient.bean.BeanSupEditInfo;
import com.dikai.chenghunjiclient.citypicker.DBManager;
import com.dikai.chenghunjiclient.citypicker.SelectCityActivity;
import com.dikai.chenghunjiclient.entity.NewUserInfo;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.entity.ResultNewSupInfo;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UpLoadImgThread;
import com.dikai.chenghunjiclient.util.UserManager;
import com.flyco.dialog.widget.base.BaseDialog;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class EditSupInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private SpotsDialog mDialog;
    private RecyclerView canbiaoList;
    private SupcanbiaoAdapter mCanbiaoAdapter;
    private RecyclerView mRecyclerView;
    private AddImgAdapter mImgAdapter;
    private ImagePicker imagePicker;
    private LinearLayout hotelLayout;
    private EditText nickEdit;
    private EditText phoneEdit;
    private TextView areaText;
    private TextView addCanbiao;
    private EditText addressEdit;
    private EditText supInfoEdit;
    private EditText serviceEdit;
    private String areaID;
    private TextView discounts;
    private ServiceDialog mCanbiaoDialog;
    private String picUrl;
    private ImageView mLogo;
    private boolean isLogo;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sup_info);
        EventBus.getDefault().register(this);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        type = getIntent().getIntExtra("type",0);
        mDialog = new SpotsDialog(this,R.style.DialogCustom);
        findViewById(R.id.cancel).setOnClickListener(this);
        findViewById(R.id.save).setOnClickListener(this);
        mLogo = (ImageView) findViewById(R.id.user_info_logo);
        discounts = (TextView) findViewById(R.id.discounts);
        discounts.setOnClickListener(this);
        nickEdit = (EditText) findViewById(R.id.nick);
        phoneEdit = (EditText) findViewById(R.id.phone);
        areaText = (TextView) findViewById(R.id.area);
        addCanbiao = (TextView) findViewById(R.id.add_canbiao);
        addressEdit = (EditText) findViewById(R.id.address);
        supInfoEdit = (EditText) findViewById(R.id.sup_info);
        serviceEdit = (EditText) findViewById(R.id.service_price);
        findViewById(R.id.user_info_logo_layout).setOnClickListener(this);
        areaText.setOnClickListener(this);
        addCanbiao.setOnClickListener(this);
        hotelLayout = (LinearLayout) findViewById(R.id.hotel_info_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL));
        mRecyclerView.setVerticalScrollBarEnabled(true);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setNestedScrollingEnabled(false);
        mImgAdapter = new AddImgAdapter(this,50);
        mImgAdapter.setAddClickListener(new AddImgAdapter.OnAddClickListener() {
            @Override
            public void onClick() {
                isLogo = false;
                imagePicker.setMultiMode(true); // 是否为多选模式
                imagePicker.setCrop(false); // 允许裁剪（单选有效）
                imagePicker.setSelectLimit(mImgAdapter.getMaxPhotoNum());
                openPhoto();
            }
        });
        mRecyclerView.setAdapter(mImgAdapter);
        canbiaoList = (RecyclerView) findViewById(R.id.canbiao);
        canbiaoList.setLayoutManager(new LinearLayoutManager(this));
        canbiaoList.setNestedScrollingEnabled(false);
        mCanbiaoAdapter = new SupcanbiaoAdapter(this);
        mCanbiaoAdapter.setOnItemClickListener(new SupcanbiaoAdapter.OnItemClickListener() {
            @Override
            public void onClick(boolean isDelete, int position, String bean) {
                if(isDelete){
                    mCanbiaoAdapter.delete(position);
                }else {
                    mCanbiaoDialog.setAdd(false);
                    mCanbiaoDialog.setTitle("编辑餐标");
                    mCanbiaoDialog.setValue(bean);
                    mCanbiaoDialog.setPosition(position);
                    mCanbiaoDialog.show();
                    mCanbiaoDialog.setUiBeforShow();
                }
            }
        });
        canbiaoList.setAdapter(mCanbiaoAdapter);
        mCanbiaoDialog = new ServiceDialog(this);
        mCanbiaoDialog.widthScale(1);
        mCanbiaoDialog.heightScale(1);

        if(UserManager.getInstance(this).getNewUserInfo().getProfession().toUpperCase()
                .equals("99C06C5A-DDB8-46A1-B860-CD1227B4DB68")){//酒店
            hotelLayout.setVisibility(View.VISIBLE);
        }else {
            hotelLayout.setVisibility(View.GONE);
        }
        initImagePicker();
        getInfo();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                onBackPressed();
                break;
            case R.id.user_info_logo_layout:
                isLogo = true;
                imagePicker.setMultiMode(false); // 是否为多选模式
                imagePicker.setCrop(true); // 允许裁剪（单选有效）
                imagePicker.setSaveRectangle(true);
                imagePicker.setStyle(CropImageView.Style.RECTANGLE);  // 裁剪框的形状
                imagePicker.setFocusWidth(400);   // 裁剪框的宽度。单位像素（圆形自动取宽高最小值）
                imagePicker.setFocusHeight(400);  // 裁剪框的高度。单位像素（圆形自动取宽高最小值）
                imagePicker.setOutPutX(400);// 保存文件的宽度。单位像素
                imagePicker.setOutPutY(400);// 保存文件的高度。单位像素
                openPhoto();
                break;
            case R.id.area:
                startActivity(new Intent(this, SelectCityActivity.class));
                break;
            case R.id.discounts:
                startActivity(new Intent(this,DiscountsActivity.class));
                break;
            case R.id.add_canbiao:
                mCanbiaoDialog.setAdd(true);
                mCanbiaoDialog.setTitle("添加餐标");
                mCanbiaoDialog.setValue("");
                mCanbiaoDialog.show();
                mCanbiaoDialog.setUiBeforShow();
                break;
            case R.id.save:
                if(type == 1 && picUrl == null){
                    Toast.makeText(this, "请选择头像", Toast.LENGTH_SHORT).show();
                }else if(type == 1 && mImgAdapter.getNewPaths().size() <= 0){
                    Toast.makeText(this, "请选择至少一张封面图", Toast.LENGTH_SHORT).show();
                }else if(nickEdit.getText() == null || "".equals(nickEdit.getText().toString().trim())){
                    Toast.makeText(this, "请输入商家用户昵称", Toast.LENGTH_SHORT).show();
                }else if(phoneEdit.getText() == null || "".equals(phoneEdit.getText().toString().trim())){
                    Toast.makeText(this, "请输入商家联系人手机", Toast.LENGTH_SHORT).show();
                }else if(areaID == null || "".equals(areaID)){
                    Toast.makeText(this, "请选择商家所在地区", Toast.LENGTH_SHORT).show();
                }else if(addressEdit.getText() == null || "".equals(addressEdit.getText().toString().trim())){
                    Toast.makeText(this, "请输入商家详细地址", Toast.LENGTH_SHORT).show();
                }else if(supInfoEdit.getText() == null || "".equals(supInfoEdit.getText().toString().trim())){
                    Toast.makeText(this, "请添加店铺描述", Toast.LENGTH_SHORT).show();
//                }else if(UserManager.getInstance(this).getNewUserInfo().getProfession().toUpperCase()
//                        .equals("99C06C5A-DDB8-46A1-B860-CD1227B4DB68") && ("".equals(mCanbiaoAdapter.getAllCanbiao()))){
//                    Toast.makeText(this, "请输入最低餐标", Toast.LENGTH_SHORT).show();
//                }else if(UserManager.getInstance(this).getNewUserInfo().getProfession().toUpperCase()
//                        .equals("99C06C5A-DDB8-46A1-B860-CD1227B4DB68") &&
//                        (serviceEdit.getText() == null || "".equals(serviceEdit.getText().toString().trim()))){
//                    Toast.makeText(this, "请输入服务费标准", Toast.LENGTH_SHORT).show();
//                    return;
                }else {
                    mDialog.show();
                    if(picUrl != null){
                        List<String> list = new ArrayList<>();
                        list.add(picUrl);
                        uploadLogo(list);
                    }else if(mImgAdapter.getNewPaths().size() > 0){
                        upload(mImgAdapter.getNewPaths(),"");
                    }else {
                        String nick = nickEdit.getText().toString();
                        String pics = "";
                        String tablePri = mCanbiaoAdapter.getAllCanbiao();
                        String servicePri = "".equals(serviceEdit.getText().toString())?"":serviceEdit.getText().toString();
                        if(mImgAdapter.getOldIds().size() > 0){
                            for (int i = 0; i < mImgAdapter.getOldIds().size(); i++) {
                                if (i < mImgAdapter.getOldIds().size() - 1) {
                                    pics = pics + mImgAdapter.getOldIds().get(i) + ",";
                                } else {
                                    pics = pics + mImgAdapter.getOldIds().get(i);
                                }
                            }
                        }
                        editInfo(phoneEdit.getText().toString(),addressEdit.getText().toString(),supInfoEdit.getText().toString(),pics,tablePri,servicePri,nick,"");
                    }
                }
                break;
        }
    }
    /**
     * 打开相册
     */
    private void openPhoto() {
        Intent intent = new Intent(this, ImageGridActivity.class);
        startActivityForResult(intent, Constants.SET_LOGO);
    }

    private void initImagePicker() {
        imagePicker = ImagePicker.getInstance();
        imagePicker.setMultiMode(true); // 是否为多选模式
        imagePicker.setShowCamera(true); // 显示拍照按钮
        imagePicker.setSelectLimit(9);

        // 是否按矩形区域
        // 保存裁剪后的图片是按矩形区域保存还是裁剪框的形状，
        // 例如圆形裁剪的时候，该参数给true，那么保存的图片是矩形区域，
        // 如果该参数给false，保存的图片是圆形区域
        imagePicker.setSaveRectangle(true);
        imagePicker.setCrop(false);        // 允许裁剪（单选有效）
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  // 裁剪框的形状
        imagePicker.setFocusWidth(400);   // 裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(400);  // 裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(400);// 保存文件的宽度。单位像素
        imagePicker.setOutPutY(400);// 保存文件的高度。单位像素
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == Constants.SET_LOGO) {
                ArrayList<ImageItem> images = (ArrayList) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if(isLogo){
                    picUrl = images.get(0).path;
                    Glide.with(this)
                            .load(images.get(0).path)
                            .error(R.drawable.ic_default_image)
                            .into(mLogo);
                }else {
                    List<String> temp = new ArrayList<>();
                    for (ImageItem item : images) {
                        temp.add(item.path);
                    }
                    mImgAdapter.addNew(temp);
                }
            } else {
                Toast.makeText(this, "图片出了些问题...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadLogo(final List<String> list) {
        UpLoadImgThread.getInstance(this).upLoad(list, "http://121.42.156.151:93/FileStorage.aspx",
                UserManager.getInstance(this).getNewUserInfo().getUserId(), "2", new UpLoadImgThread.CallBackListener() {
                    @Override
                    public void onFinish(List<String> values) {
                        if(mImgAdapter.getNewPaths().size() > 0){
                            upload(mImgAdapter.getNewPaths(),values.get(0));
                        }else {
                            String nick = nickEdit.getText().toString();
                            String pics = "";
                            String tablePri = mCanbiaoAdapter.getAllCanbiao();
                            String servicePri = "".equals(serviceEdit.getText().toString())?"":serviceEdit.getText().toString();
                            if(mImgAdapter.getOldIds().size() > 0){
                                for (int i = 0; i < mImgAdapter.getOldIds().size(); i++) {
                                    if (i < mImgAdapter.getOldIds().size() - 1) {
                                        pics = pics + mImgAdapter.getOldIds().get(i) + ",";
                                    } else {
                                        pics = pics + mImgAdapter.getOldIds().get(i);
                                    }
                                }
                            }
                            editInfo(phoneEdit.getText().toString(),addressEdit.getText().toString(),
                                    supInfoEdit.getText().toString(),pics,tablePri,servicePri,nick,values.get(0));
                        }
                    }

                    @Override
                    public void onError(String e) {
                        mDialog.dismiss();
                        Toast.makeText(EditSupInfoActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void upload(final List<String> list, final String logo) {
        UpLoadImgThread.getInstance(this).upLoad(list, "http://121.42.156.151:93/FileStorage.aspx",
                UserManager.getInstance(this).getNewUserInfo().getUserId(), "2", new UpLoadImgThread.CallBackListener() {
                    @Override
                    public void onFinish(List<String> values) {
                        String nick = nickEdit.getText().toString();
                        String pics = "";
                        String tablePri = mCanbiaoAdapter.getAllCanbiao();
                        String servicePri = "".equals(serviceEdit.getText().toString())?"":serviceEdit.getText().toString();
                        if(mImgAdapter.getOldIds().size() > 0){
                            for (int i = 0; i < mImgAdapter.getOldIds().size(); i++) {
                                pics = pics + mImgAdapter.getOldIds().get(i) + ",";
                            }
                        }
                        for (int i = 0; i < values.size(); i++) {
                            if (i < values.size() - 1) {
                                pics = pics + values.get(i) + ",";
                            } else {
                                pics = pics + values.get(i);
                            }
                        }
                        editInfo(phoneEdit.getText().toString(),addressEdit.getText().toString(),supInfoEdit.getText().toString(),pics,tablePri,servicePri,nick,logo);
                    }

                    @Override
                    public void onError(String e) {
                        mDialog.dismiss();
                        Toast.makeText(EditSupInfoActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setData(ResultNewSupInfo result) {
        nickEdit.setText(result.getName());
        addressEdit.setText(result.getAddress());
        phoneEdit.setText(result.getPhone());
        supInfoEdit.setText(result.getAbstract());
        areaID = result.getRegion();
        if(areaID != null && !"".equals(areaID)){
            areaText.setText(DBManager.getInstance(this).getCityName(areaID));
        }
        if(UserManager.getInstance(this).getNewUserInfo().getProfession().toUpperCase()
                .equals("99C06C5A-DDB8-46A1-B860-CD1227B4DB68")){//酒店
            mCanbiaoAdapter.refresh(Arrays.asList(result.getMealMark().split(",")));
            serviceEdit.setText(result.getServiceCharge());
        }
        discounts.setText("当前"+result.getBasicPreferencesCount()+"条优惠");
        mImgAdapter.addOld(result.getData());
        Glide.with(this).load(result.getLogo()).into(mLogo);
    }

    /**
     * 更新信息
     */
    private void editInfo(final String phone, String address, String intro, String pics, String tablePri, String servicePri, String nick, String logo){
        NewUserInfo info = UserManager.getInstance(this).getNewUserInfo();
        NetWorkUtil.setCallback("HQOAApi/UpdateFacilitatorInfo",
                new BeanSupEditInfo(info.getFacilitatorId(),address,phone,intro,"",areaID,pics,tablePri,servicePri,logo,nick),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                Toast.makeText(EditSupInfoActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                EventBus.getDefault().post(new EventBusBean(Constants.USER_INFO_CHANGE));
                                finish();
                            } else {
                                Toast.makeText(EditSupInfoActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Toast.makeText(EditSupInfoActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    
    /**
     * 获取信息
     */
    private void getInfo(){
        NetWorkUtil.setCallback("HQOAApi/GetFacilitatorInfo",
                new BeanID(UserManager.getInstance(this).getNewUserInfo().getFacilitatorId()),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultNewSupInfo result = new Gson().fromJson(respose, ResultNewSupInfo.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                setData(result);
                            } else {
                                Toast.makeText(EditSupInfoActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(CorpInfoActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        Toast.makeText(EditSupInfoActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 餐标弹窗
     */
    private class ServiceDialog extends BaseDialog<ServiceDialog> implements View.OnClickListener {

        private TextView cancel;
        private TextView add;
        private TextView titleText;
        private EditText canbiao;
        private String title;
        private String value;
        private boolean isAdd;
        private int position;

        public void setPosition(int position) {this.position = position;}
        public void setAdd(boolean add) {isAdd = add;}
        public void setTitle(String title) {this.title = title;}
        public void setValue(String value) {this.value = value;}
        public ServiceDialog(Context context) {super(context);}

        @Override
        public View onCreateView() {
            View view = View.inflate(mContext, R.layout.dialog_add_canbiao_layout, null);
            cancel = (TextView) view.findViewById(R.id.btn_cancel);
            add = (TextView) view.findViewById(R.id.btn_add);
            titleText = (TextView) view.findViewById(R.id.title);
            canbiao = (EditText) view.findViewById(R.id.canbiao);
            cancel.setOnClickListener(this);
            add.setOnClickListener(this);
            return view;
        }

        @Override
        public void setUiBeforShow() {
            titleText.setText(title);
            canbiao.setText(value);
        }

        @Override
        public void onClick(View v) {
            if(v == cancel){
                this.dismiss();
            }else if(v == add){
                if(canbiao.getText() == null || "".equals(canbiao.getText().toString().trim())){
                    Toast.makeText(mContext, "请输入餐标", Toast.LENGTH_SHORT).show();
                }else {
                    this.dismiss();
                    if(isAdd){
                        List<String> temp = new ArrayList<>();
                        temp.add(canbiao.getText().toString().trim());
                        mCanbiaoAdapter.addAll(temp);
                    }else {
                        mCanbiaoAdapter.itemChange(canbiao.getText().toString().trim(),position);
                    }
                }
            }
        }
    }

    /**
     * 事件总线刷新
     * @param bean
     */
    @Subscribe
    public void onEventMainThread(final EventBusBean bean) {
        runOnUiThread(new Runnable() {
            public void run() {
                if(bean.getType() == Constants.SELECT_COUNTRY){
                    areaID = bean.getCountry().getRegionId();
                    areaText.setText(bean.getCountry().getRegionName());
                }else if(bean.getType() == Constants.ADD_DISCOUNT_NUM){
                    discounts.setText("当前" + bean.getProgress() + "条优惠");
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        ImmersionBar.with(this).destroy();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
