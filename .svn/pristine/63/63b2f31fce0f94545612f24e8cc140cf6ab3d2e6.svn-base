package com.dikai.chenghunjiclient.activity.ad;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.PhotoActivity;
import com.dikai.chenghunjiclient.bean.BeanID;
import com.dikai.chenghunjiclient.entity.ResultGetAdInfo;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.flyco.dialog.widget.base.BaseDialog;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.callback.OnImageClickListener;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;

public class NewADInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;
//    private TextView title;
    private String adID;
    private Intent intent;
    private int CALL_REQUEST_CODE = 101;
    private ServiceDialog mCallDialog;
    private ResultGetAdInfo result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_adinfo);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.call).setOnClickListener(this);
        findViewById(R.id.share).setOnClickListener(this);
        adID = getIntent().getStringExtra("id");
        textView = (TextView) findViewById(R.id.article);
//        title = (TextView) findViewById(R.id.title);
        mCallDialog = new ServiceDialog(this);
        mCallDialog.widthScale(1);
        mCallDialog.heightScale(1);
        getInfo();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.share:
                showShare();
                break;
            case R.id.call:
                mCallDialog.show();
                break;
        }
    }


    /**
     * 获取案例
     */
    private void getInfo(){
        NetWorkUtil.setCallback("HQOAApi/GetFacilitatorActivityInfo",
                new BeanID(adID),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            result = new Gson().fromJson(respose, ResultGetAdInfo.class);
                            if ("200".equals(result.getMessage().getCode())) {
//                                title.setText(result.getTitle());
                                if(result.getDetails() != null){
                                    RichText.fromHtml(result.getDetails())
                                            .clickable(true) //是否可点击，默认只有设置了点击监听才可点击
                                            .imageClick(new OnImageClickListener() {
                                                @Override
                                                public void imageClicked(List<String> imageUrls, int position) {
                                                    Intent intent = new Intent(NewADInfoActivity.this, PhotoActivity.class);
                                                    intent.putExtra("now", position);
                                                    intent.putStringArrayListExtra("imgs", new ArrayList<>(imageUrls));
                                                    startActivity(intent);
                                                }
                                            }) // 设置图片点击回调
                                            .into(textView);
                                }
                            } else {
//                                mDialog.dismiss();
                                Toast.makeText(NewADInfoActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
//                            mDialog.dismiss();
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
//                        mDialog.dismiss();
                        Toast.makeText(NewADInfoActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showShare() {
        String url = "http://www.chenghunji.com/Capital/FacilitatorActivityInfo?Id="+result.getId();
        OnekeyShare oks = new OnekeyShare();
//        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(result.getShareTitle());
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(result.getShareDescribe());
//        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("");//确保SDcard下面存在此张图片
        oks.setImageUrl("http://www.chenghunji.com/img/index/B_LOGO_01.png");
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("成婚纪——婚礼原来如此简单");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("成婚纪");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://www.chenghunji.com/");
        // 启动分享GUI
        oks.show(this);
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy(); //必须调用该方法，防止内存泄漏
        super.onDestroy();
    }

    public class ServiceDialog extends BaseDialog<ServiceDialog> implements View.OnClickListener {
        private RelativeLayout cancel;
        private RelativeLayout sure;
        public ServiceDialog(Context context) {
            super(context);
        }

        @Override
        public View onCreateView() {
            View view = View.inflate(mContext, R.layout.dialog_call_service, null);
            return view;
        }

        @Override
        public void onViewCreated(View view) {
            super.onViewCreated(view);
            cancel = (RelativeLayout) view.findViewById(R.id.button_cancel);
            sure = (RelativeLayout) view.findViewById(R.id.button_sure);
            cancel.setOnClickListener(this);
            sure.setOnClickListener(this);
        }

        @Override
        public void setUiBeforShow() {

        }

        @Override
        public void onClick(View v) {
            if(v == cancel){
                this.dismiss();
            }else if(v == sure){
                this.dismiss();
                intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "15192055999"));
                request();
            }
        }
    }

    private void request() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请CALL_PHONE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, CALL_REQUEST_CODE);
        } else {
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode, grantResults);
    }

    private void doNext(int requestCode, int[] grantResults) {
        if (requestCode == CALL_REQUEST_CODE) {
            if (grantResults != null && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivity(intent);
            } else {
                Toast.makeText(this, "已禁止", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
