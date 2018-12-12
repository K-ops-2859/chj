package com.dikai.chenghunjiclient.activity.wedding;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.dikai.chenghunjiclient.R;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;
import com.gyf.barlibrary.ImmersionBar;

public class GuaranteeActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout llService;
    private LinearLayout llPay;
    private LinearLayout llDuty;
    private LinearLayout llWeiyue;
    private LinearLayout llRelieve;
    private ImageView ImgService;
    private ImageView ImgPay;
    private ImageView ImgDuty;
    private ImageView ImgRelieve;
    private ImageView ImgWeiyue;
    private MaterialDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guarantee);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        llService = (LinearLayout) findViewById(R.id.guarantee_item_service);
        llPay = (LinearLayout) findViewById(R.id.guarantee_item_pay);
        llDuty = (LinearLayout) findViewById(R.id.guarantee_item_duty);
        llRelieve = (LinearLayout) findViewById(R.id.guarantee_item_relieve);
        llWeiyue = (LinearLayout) findViewById(R.id.guarantee_item_weiyue);
        ImgPay = (ImageView) findViewById(R.id.img_pay);
        ImgDuty = (ImageView) findViewById(R.id.img_duty);
        ImgService = (ImageView) findViewById(R.id.img_service);
        ImgRelieve = (ImageView) findViewById(R.id.img_relieve);
        ImgWeiyue = (ImageView) findViewById(R.id.img_weiyue);
        findViewById(R.id.next).setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.ll_service).setOnClickListener(this);
        findViewById(R.id.ll_pay).setOnClickListener(this);
        findViewById(R.id.ll_duty).setOnClickListener(this);
        findViewById(R.id.ll_relieve).setOnClickListener(this);
        findViewById(R.id.ll_weiyue).setOnClickListener(this);
        dialog = new MaterialDialog(this);
        dialog.isTitleShow(false)//
                .btnNum(2)
                .content("为了保证给您提供优质的的婚礼担保服务，请联系您的婚庆公司制定您的专属担保合同")//
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
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.next:
                dialog.show();
                break;
            case R.id.ll_service:
                if(llService.getVisibility() == View.GONE){
                    ImgService.setImageResource(R.mipmap.ic_main_up);
                    llService.setVisibility(View.VISIBLE);
                }else {
                    ImgService.setImageResource(R.mipmap.ic_main_down);
                    llService.setVisibility(View.GONE);
                }
                break;
            case R.id.ll_pay:
                if(llPay.getVisibility() == View.GONE){
                    ImgPay.setImageResource(R.mipmap.ic_main_up);
                    llPay.setVisibility(View.VISIBLE);
                }else {
                    ImgPay.setImageResource(R.mipmap.ic_main_down);
                    llPay.setVisibility(View.GONE);
                }
                break;
            case R.id.ll_duty:
                if(llDuty.getVisibility() == View.GONE){
                    ImgDuty.setImageResource(R.mipmap.ic_main_up);
                    llDuty.setVisibility(View.VISIBLE);
                }else {
                    ImgDuty.setImageResource(R.mipmap.ic_main_down);
                    llDuty.setVisibility(View.GONE);
                }
                break;
            case R.id.ll_weiyue:
                if(llWeiyue.getVisibility() == View.GONE){
                    ImgWeiyue.setImageResource(R.mipmap.ic_main_up);
                    llWeiyue.setVisibility(View.VISIBLE);
                }else {
                    ImgWeiyue.setImageResource(R.mipmap.ic_main_down);
                    llWeiyue.setVisibility(View.GONE);
                }
                break;
            case R.id.ll_relieve:
                if(llRelieve.getVisibility() == View.GONE){
                    ImgRelieve.setImageResource(R.mipmap.ic_main_up);
                    llRelieve.setVisibility(View.VISIBLE);
                }else {
                    ImgRelieve.setImageResource(R.mipmap.ic_main_down);
                    llRelieve.setVisibility(View.GONE);
                }
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
}
