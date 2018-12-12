package com.dikai.chenghunjiclient.fragment.me;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.citypicker.SelectCityActivity;
import com.dikai.chenghunjiclient.tongxunlu.CarBean;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.gyf.barlibrary.ImmersionBar;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateTeamFragment extends Fragment implements View.OnClickListener {

    private String picUrl;
    private ImageView logo;
    private TextView regionText;
    private EditText nameEdit;
    private EditText introEdit;
    private String areaID;

    public CreateTeamFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_team, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logo = (ImageView) view.findViewById(R.id.fragment_team_logo);
        regionText = (TextView) view.findViewById(R.id.fragment_team_region);
        nameEdit = (EditText) view.findViewById(R.id.fragment_team_name);
        introEdit = (EditText) view.findViewById(R.id.fragment_team_intro);
        logo.setOnClickListener(this);
        regionText.setOnClickListener(this);
        initImagePicker();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        if(v == logo){
            openPhoto();
        }else if(v == regionText){
            startActivity(new Intent(getContext(), SelectCityActivity.class));
        }
    }

    /**
     * 打开相册
     */
    private void openPhoto() {
        Intent intent = new Intent(getContext(), ImageGridActivity.class);
        startActivityForResult(intent, Constants.SET_LOGO);
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setMultiMode(false); // 是否为多选模式
//        imagePicker.setSelectLimit(1); // 多选模式下限制数量，默认为9
        imagePicker.setShowCamera(true); // 显示拍照按钮
        // 是否按矩形区域
        // 保存裁剪后的图片是按矩形区域保存还是裁剪框的形状，
        // 例如圆形裁剪的时候，该参数给true，那么保存的图片是矩形区域，
        // 如果该参数给false，保存的图片是圆形区域
        imagePicker.setSaveRectangle(true);
        imagePicker.setCrop(true);        // 允许裁剪（单选有效）
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  // 裁剪框的形状
        imagePicker.setFocusWidth(400);   // 裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(400);  // 裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(400);// 保存文件的宽度。单位像素
        imagePicker.setOutPutY(400);// 保存文件的高度。单位像素
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == Constants.SET_LOGO) {
                ArrayList<ImageItem> images = (ArrayList) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                picUrl = images.get(0).path;
                Glide.with(this)
                        .load(images.get(0).path)
                        .error(R.drawable.ic_default_image)
                        .centerCrop()
                        .into(logo);
//                logo.setImageURI(Uri.fromFile(new File(images.get(0).path)));
            } else {
                Toast.makeText(getContext(), "图片出了些问题...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 事件总线刷新
     * @param bean
     */
    @Subscribe
    public void onEventMainThread(final EventBusBean bean) {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                if(bean.getType() == Constants.SELECT_COUNTRY){
                    areaID = bean.getCountry().getRegionId();
                    regionText.setText(bean.getCountry().getRegionName());
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
