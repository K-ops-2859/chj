package com.dikai.chenghunjiclient.activity.invitation;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.dikai.chenghunjiclient.R;
import com.gyf.barlibrary.ImmersionBar;
import com.uuzuche.lib_zxing.activity.CodeUtils;

public class FaceInviteActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView codeImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_invite);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        String url = getIntent().getStringExtra("url");
        findViewById(R.id.back).setOnClickListener(this);
        codeImg = (ImageView) findViewById(R.id.code);
        codeImg.setImageBitmap(CodeUtils.createImage(url, 600, 600,
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }

}
