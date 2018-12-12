package com.dikai.chenghunjiclient.activity.me;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.me.PublishPhotoAdapter;
import com.dikai.chenghunjiclient.bean.BeanFeedBack;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UpLoadImgThread;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class FeedBackActivity extends AppCompatActivity {

    private ImagePicker imagePicker;
    private List<ImageItem> mList;
    private PublishPhotoAdapter mPhotoAdapter;
    private EditText etContent;
    private EditText etTitle;
    private TextView tvWordNumber;
    private SpotsDialog mDialog;
    private EditText etPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        ImageView mBack = (ImageView) findViewById(R.id.back);
        etPhone = (EditText) findViewById(R.id.et_phone);
        etTitle = (EditText) findViewById(R.id.et_title);
        etContent = (EditText) findViewById(R.id.et_content);
        tvWordNumber = (TextView) findViewById(R.id.tv_word_number);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        TextView tvSubmit = (TextView) findViewById(R.id.tv_submit);
        mDialog = new SpotsDialog(this, R.style.DialogCustom);
        mList = new ArrayList<>();
        mPhotoAdapter = new PublishPhotoAdapter(this, mList, 9);
        mPhotoAdapter.setAddClickListener(new PublishPhotoAdapter.OnAddClickListener() {
            @Override
            public void onClick() {
                imagePicker.setMultiMode(true); // 是否为多选模式
                imagePicker.setSelectLimit(9);
                imagePicker.setCrop(false);
                openPhoto();
            }
        });
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        mRecyclerView.setAdapter(mPhotoAdapter);
        initImagePicker();
        etTitle.addTextChangedListener(titleWatcher);
        etContent.addTextChangedListener(mContentWatcher);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editVerify();
            }
        });
    }

    private void editVerify() {
        String strPhone = etPhone.getText().toString();
        String strTitle = etTitle.getText().toString();
        String strContent = etContent.getText().toString();
        if (strPhone.equals("")) {
            Toast.makeText(this, "请填写手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (strTitle.equals("")) {
            Toast.makeText(this, "亲填写标题", Toast.LENGTH_SHORT).show();
        } else if (etTitle.getText().length() > 20) {
            Toast.makeText(this, "标题字数过长", Toast.LENGTH_SHORT).show();
            return;
        }
        if (strContent.equals("")) {
            Toast.makeText(this, "请填写反馈内容", Toast.LENGTH_SHORT).show();
            return;
        }
        List<String> result = mPhotoAdapter.getResult();
        mDialog.show();
        upLoadPic(strTitle, strContent, result, strPhone);

    }

    private void initImagePicker() {
        imagePicker = ImagePicker.getInstance();
//      imagePicker = ImagePicker.getInstance();
//        imagePicker.setImageLoader(GlideImageLoader.getInstance()); //设置图片加载器
        imagePicker.setMultiMode(true); // 是否为多选模式
        imagePicker.setSelectLimit(9); // 多选模式下限制数量，默认为9
        imagePicker.setShowCamera(true); // 显示拍照按钮
        // 是否按矩形区域
        // 保存裁剪后的图片是按矩形区域保存还是裁剪框的形状，
        // 例如圆形裁剪的时候，该参数给true，那么保存的图片是矩形区域，
        // 如果该参数给false，保存的图片是圆形区域
        imagePicker.setSaveRectangle(false);
        imagePicker.setCrop(false);        // 允许裁剪（单选有效）
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  // 裁剪框的形状
//        imagePicker.setFocusWidth(400);   // 裁剪框的宽度。单位像素（圆形自动取宽高最小值）
//        imagePicker.setFocusHeight(400);  // 裁剪框的高度。单位像素（圆形自动取宽高最小值）
//        imagePicker.setOutPutX(400);// 保存文件的宽度。单位像素
//        imagePicker.setOutPutY(400);// 保存文件的高度。单位像素
        mPhotoAdapter.setImagePicker(imagePicker);
    }

    private void pubFeed(String title, String content, String images, String phone) {
        NetWorkUtil.setCallback("HQOAApi/AddFeedbackInfo",
                new BeanFeedBack(title, content, images, "", phone),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(String respose) {
                        try {
                            Log.e("返回值",respose);
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if (result.getMessage().getCode().equals("200")) {
                                mDialog.dismiss();
                                Toast.makeText(FeedBackActivity.this, "上传成功，感谢您的反馈", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(FeedBackActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错", e.toString());
                        }
                    }

                    @Override
                    public void onError(String e) {
                        Log.e("出错", e);
                    }
        });
    }

    /**
     * 打开相册
     */
    private void openPhoto() {
        Intent intent = new Intent(this, ImageGridActivity.class);
        startActivityForResult(intent, Constants.SET_LOGO);
    }

//    /**
//     * 上传
//     */
//    public void upLoadImg(List<String> pathList, List<String> nameList) {
//        final String uri = "http://121.42.156.151:93/FileStorage.aspx";
//        UpLoadImgThread.getInstance(this).setCallBackListener(new UpLoadImgThread.CallBackListener() {
//
//            @Override
//            public void onFinish(String repose) {
//                publish(title, content, imgs, "", "1", empIDs, "0");
//            }
//
//            @Override
//            public void onError(String e) {
//                mDialog.dismiss();
////                Toast.makeText(ActiveAccountActivity.this, e, Toast.LENGTH_SHORT).show();
//            }
//        }).startUpLoadLogo(pathList, uri, nameList, "0", "1");
//    }

    public void upLoadPic(final String title, final String content, List<String> list, final String phone) {
        UpLoadImgThread.getInstance(this).upLoad(list, "http://121.42.156.151:93/FileStorage.aspx",
                "0", "1", new UpLoadImgThread.CallBackListener() {
                    @Override
                    public void onFinish(List<String> values) {
                        String pics = "";
                        for (int i = 0; i < values.size(); i++) {
                            if (i < values.size() - 1) {
                                pics = pics + values.get(i) + ",";
                            } else {
                                pics = pics + values.get(i);
                            }
                        }
                        pubFeed(title, content, pics, phone);
                    }

                    @Override
                    public void onError(String e) {
                        //  mDialog.dismiss();
                        Toast.makeText(FeedBackActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == Constants.SET_LOGO) {
                ArrayList<ImageItem> images = (ArrayList) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
//                picUrl = images.get(0).path;
//                pic.setImageURI(Uri.fromFile(new File(images.get(0).path)));
                mPhotoAdapter.addAll(images);
            } else {
                Toast.makeText(this, "图片出了些问题...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private TextWatcher mContentWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            int count = etContent.getText().length();
            if (count <= 800) {
                tvWordNumber.setText("" + (s.toString().length()));
            } else {
                tvWordNumber.setText("" + (s.toString().length()));
            }
        }
    };

    private int titlecount = 0;
    private TextWatcher titleWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            //在文本改变前执行
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            //当文本正在改变时执行
        }

        @Override
        public void afterTextChanged(Editable s) {
//          统计字母数字个数
            titlecount = 0;
            for (int i = 0; i < s.length(); i++) {
                char cs = s.charAt(i);
                if ((cs >= 'a' && cs <= 'z') || ((cs >= 'A' && cs <= 'Z')) || ((cs >= '0' && cs <= '9'))) {
                    titlecount++;
                }
            }
            //统计汉字的个数
            String Reg = "^[\u4e00-\u9fa5]{1}$";  //汉字的正规表达式
            for (int i = 0; i < s.length(); i++) {
                String b = Character.toString(s.charAt(i));
                if (b.matches(Reg))
                    titlecount = titlecount + 4;
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }

}
