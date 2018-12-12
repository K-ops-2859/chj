package com.dikai.chenghunjiclient.activity.wedding;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.dikai.chenghunjiclient.R;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;
import com.gyf.barlibrary.ImmersionBar;

/**
 * Created by cmk03 on 2017/12/18.
 */

public class WeddingAssureActivity extends AppCompatActivity implements View.OnClickListener{
    private MaterialDialog dialog1;
    private MaterialDialog dialog2;
    private MaterialDialog dialog3;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wedding_assure);

        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();

        ImageView mBack = (ImageView) findViewById(R.id.activity_add_car_back);
        ImageButton ibQuestion = (ImageButton) findViewById(R.id.ib_question);
        TextView tvNeedPay = (TextView) findViewById(R.id.tv_need_pay);
        CardView cbWeddingImage = (CardView) findViewById(R.id.cv_weddint_image);
        CardView cbPact = (CardView) findViewById(R.id.cb_pact);
        CardView cbMyAssure = (CardView) findViewById(R.id.cb_my_assure);
      //  CardView cbEvidence = (CardView) findViewById(R.id.cb_evidence);

        showDialog1();
        showDialog2();
        showDialog3();
        mBack.setOnClickListener(this);
        ibQuestion.setOnClickListener(this);
        tvNeedPay.setOnClickListener(this);
        cbWeddingImage.setOnClickListener(this);
        cbPact.setOnClickListener(this);
        cbMyAssure.setOnClickListener(this);
      //  cbEvidence.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_add_car_back:
                finish();
                break;
            case R.id.ib_question:
                dialog1.show();
                break;
            case R.id.tv_need_pay:
                break;
            case R.id.cv_weddint_image:
                dialog2.show();
                break;
            case R.id.cb_pact:
                startActivity(new Intent(this, GuaranteeActivity.class));
                break;
            case R.id.cb_my_assure:
                dialog3.show();
                break;
//            case R.id.cb_evidence:
//                break;
            default:
                break;
        }
    }

    private void showDialog1() {
        if (dialog1 == null) {
            dialog1 = new MaterialDialog(this);
        }
        dialog1.isTitleShow(false)
                .btnNum(2)
                .title("温馨提示")
                .content("婚礼担保金额由您和婚庆公司共同签署的担保合同产生，详情请咨询您的婚庆公司")//
                .btnText("确定")
                .setOnBtnClickL(new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog1.dismiss();
                    }
                });
    }

    private void showDialog2() {
        if (dialog2 == null) {
            dialog2 = new MaterialDialog(this);
        }
        dialog2.isTitleShow(false)
                .btnNum(2)
                .title("温馨提示")
                .content("请联系您的婚庆公司获取详细的婚礼设计图")//
                .btnText("确定")
                .setOnBtnClickL(new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog2.dismiss();
                    }
                });
    }

    private void showDialog3() {
        if (dialog3 == null) {
            dialog3 = new MaterialDialog(this);
        }
        dialog3.isTitleShow(false)
                .btnNum(2)
                .title("温馨提示")
                .content("请联系您的婚庆公司获取您的担保合同")//
                .btnText("确定")
                .setOnBtnClickL(new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog3.dismiss();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }

}
