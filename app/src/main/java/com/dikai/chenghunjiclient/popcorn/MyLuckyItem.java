package com.dikai.chenghunjiclient.popcorn;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;

/**
 * Created by Lucio on 2018/3/29.
 */

public class MyLuckyItem extends FrameLayout implements ItemView{

    private View itemBg;
    private TextView tvName;
    private ImageView imagePic;
    private float ratio;

    public MyLuckyItem(Context context) {
        this(context, null);
    }

    public MyLuckyItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyLuckyItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.view_panel_item, this);
        itemBg = findViewById(R.id.layout_bg);
        tvName = (TextView) findViewById(R.id.item_name);
        imagePic = (ImageView) findViewById(R.id.item_image);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyLuckyItem);
        Drawable drawable = ta.getDrawable(R.styleable.MyLuckyItem_prizeImg);
        if (drawable != null) {
            imagePic.setImageDrawable(drawable);
        }

        String name = ta.getString(R.styleable.MyLuckyItem_prizeName);
        if (name != null) {
            tvName.setText(name);
        }
        ratio = ta.getFloat(R.styleable.MyLuckyItem_item_ratio,0);
        //记得此处要recycle();
        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(ratio != 0){
            //获取宽度的模式和尺寸
            int widthSize = MeasureSpec.getSize(widthMeasureSpec);
            //宽确定，高不确定
            int heightSize = (int)(widthSize / ratio);//根据宽度和比例计算高度
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
            //必须调用下面的两个方法之一完成onMeasure方法的重写，否则会报错
//        setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void setFocus(boolean isFocused) {
        if (itemBg != null) {
            itemBg.setBackgroundResource(isFocused ? R.color.transparent_black2:R.color.transparent);
        }
    }

    public void setData(String imgurl,String name){
        Glide.with(getContext()).load(imgurl).into(imagePic);
        tvName.setText("");
        tvName.append(name);
    }
}
