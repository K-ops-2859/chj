package com.dikai.chenghunjiclient.activity.store;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.wedding.WedRuleActivity;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.ImageUri;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.greenrobot.eventbus.EventBus;

public class ScanActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_IMAGE = 166;
    private RelativeLayout mRelativeLayout;
    private LinearLayout lightLayout;
    private LinearLayout picLayout;
    private ImageView lightPic;
    private boolean isLightOpen = false;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        type = getIntent().getIntExtra("type",0);
        setContentView(R.layout.activity_scan);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.activity_scan_return_layout);
        lightLayout = (LinearLayout) findViewById(R.id.activity_scan_light_layout);
        picLayout = (LinearLayout) findViewById(R.id.activity_scan_local_layout);
        lightPic = (ImageView) findViewById(R.id.activity_scan_light);
        mRelativeLayout.setOnClickListener(this);
        lightLayout.setOnClickListener(this);
        picLayout.setOnClickListener(this);
        initFragment();
    }

    private void setData(Bitmap mBitmap, String result){
        Log.e("扫描结果",result+"");
        if(type == 0){
            EventBus.getDefault().post(new EventBusBean(Constants.SCAN_RESULT,result));
            finish();
        }else {
            startActivity(new Intent(ScanActivity.this, WedRuleActivity.class)
                    .putExtra("url",result));
        }
    }

    private void initFragment(){
        /**
         * 执行扫面Fragment的初始化操作
         */
        CaptureFragment captureFragment = new CaptureFragment();
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.my_camera);

        captureFragment.setAnalyzeCallback(analyzeCallback);
        /**
         * 替换我们的扫描控件
         */
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();
    }

    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
           setData(mBitmap,result);
        }

        @Override
        public void onAnalyzeFailed() {
            Toast.makeText(ScanActivity.this, "解析失败", Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * 扫描本地图片
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (data != null) {
                Uri uri = data.getData();
                ContentResolver cr = getContentResolver();
                try {
                    Bitmap mBitmap = MediaStore.Images.Media.getBitmap(cr, uri);//显得到bitmap图片
                    CodeUtils.analyzeBitmap(ImageUri.getImageAbsolutePath(this, uri), new CodeUtils.AnalyzeCallback() {
                        @Override
                        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                            setData(mBitmap,result);
                        }

                        @Override
                        public void onAnalyzeFailed() {
                            Toast.makeText(ScanActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                        }
                    });
                    if (mBitmap != null) {
                        mBitmap.recycle();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v == mRelativeLayout){
            onBackPressed();
        }else if(v == lightLayout){
            if(isLightOpen){
                /**
                 * 关闭闪光灯
                 */
                CodeUtils.isLightEnable(false);
                isLightOpen = false;
                lightPic.setImageResource(R.mipmap.ic_app_no_light);
            }else {
                /**
                 * 打开闪光灯
                 */
                CodeUtils.isLightEnable(true);
                isLightOpen = true;
                lightPic.setImageResource(R.mipmap.ic_app_light);
            }
        }else if(v == picLayout){
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_IMAGE);
        }
    }
}
