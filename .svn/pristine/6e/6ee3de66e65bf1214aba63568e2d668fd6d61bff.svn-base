package com.dikai.chenghunjiclient.activity.wedding;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.PhotoActivity;
import com.dikai.chenghunjiclient.bean.BeanGetTotalInvite;
import com.dikai.chenghunjiclient.entity.ResultGetTotalInvite;
import com.dikai.chenghunjiclient.entity.UserInfo;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyImageView;
import com.flyco.dialog.widget.base.BaseDialog;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class WedCodeActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView todayInvite;
    private TextView totalInvite;
    private MyImageView QRCodeImg;
    private Animation rotate;
    private ImageView refresh;
    private boolean isRefreshing = false;
    private static final int CALL_REQUEST_CODE = 120;
    private Intent intent;
    private ServiceDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wed_code);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.pink_deep)
                .statusBarDarkFont(false)
                .init();
        init();
    }

    private void init() {
        todayInvite = (TextView) findViewById(R.id.today_invite);
        totalInvite = (TextView) findViewById(R.id.total_invite);
        QRCodeImg = (MyImageView) findViewById(R.id.qrcode);
        refresh = (ImageView) findViewById(R.id.refresh);
        findViewById(R.id.save).setOnClickListener(this);
        findViewById(R.id.rule).setOnClickListener(this);
        findViewById(R.id.service).setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.refresh).setOnClickListener(this);
        findViewById(R.id.invite_layout).setOnClickListener(this);
        String userID =  UserManager.getInstance(this).isLogin()? UserManager.getInstance(this).getNewUserInfo().getUserId():"";
        String url = "http://www.chenghunji.com/REDBAG/SHURUXINXI?id=1&TerminalTypes=1&UserID=";
        QRCodeImg.setImageBitmap(CodeUtils.createImage(url + userID, 600, 600,
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)));
        rotate = AnimationUtils.loadAnimation(this, R.anim.refresh_anim);
        mDialog = new ServiceDialog(this);
        mDialog.widthScale(1);
        mDialog.heightScale(1);
        refresh();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.refresh:
                refresh();
                break;
            case R.id.save:
                String userID =  UserManager.getInstance(this).isLogin()? UserManager.getInstance(this).getUserInfo().getUserID():"";
                String url = "http://www.chenghunji.com/REDBAG/SHURUXINXI?id=1&TerminalTypes=1&UserID=";
                saveCode(CodeUtils.createImage(url + userID, 600, 600,
                        BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)));
                break;
            case R.id.invite_layout:
                startActivity(new Intent(this,InviteListActivity.class));
                break;
            case R.id.rule:
                startActivity(new Intent(this,InviteRuleActivity.class));
                break;
            case R.id.service:
                mDialog.show();
                break;
        }
    }

    private void refresh(){
        if (!isRefreshing){
            isRefreshing = true;
            if (rotate != null) {
                refresh.startAnimation(rotate);
            } else {
                refresh.setAnimation(rotate);
                refresh.startAnimation(rotate);
            }
            getTotalInvite();
        }else {
            isRefreshing = false;
            refresh.clearAnimation();
        }
    }

    /**
     * 获取历史邀请
     */
    private void getTotalInvite(){
        NetWorkUtil.setCallback("HQOAApi/GetNewInformationCount",
                new BeanGetTotalInvite(UserManager.getInstance(this).getNewUserInfo().getUserId(),"1","1"),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultGetTotalInvite result = new Gson().fromJson(respose, ResultGetTotalInvite.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                todayInvite.setText("今日邀请   "+result.getNowCount()+"人 ");
                                totalInvite.setText("累计邀请   "+result.getAllCount()+"人 ");
                                isRefreshing = false;
                                refresh.clearAnimation();
                            } else {
                                Toast.makeText(WedCodeActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        Toast.makeText(WedCodeActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveCode(Bitmap resource){
        UserInfo info = UserManager.getInstance(this).getUserInfo();
        String imageName = info.getName() + info.getUserID() + "成婚纪_邀请码.jpg";
        File file = new File(Environment.getExternalStorageDirectory() + "/ChengHunJi/");
        File picFile = new File(file, imageName);
        if (!file.exists()) {
            file.mkdirs();
        }
        FileOutputStream fout = null;
        if (!picFile.exists()) {
            try {
                //保存图片
                fout = new FileOutputStream(picFile);
                resource.compress(Bitmap.CompressFormat.JPEG, 30, fout);
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(picFile)));
                Toast.makeText(WedCodeActivity.this, "图片已下载至" + picFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                // 将保存的地址给SubsamplingScaleImageView,这里注意设置ImageViewState
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Log.e("保存图片失败:", e.toString());
            } finally {
                try {
                    if (fout != null) fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("关闭IO失败:", e.toString());
                }
            }
        }else {
            Toast.makeText(WedCodeActivity.this, "图片已存在", Toast.LENGTH_SHORT).show();
        }
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


    @Override
    protected void onDestroy() {
        rotate.cancel();
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}
