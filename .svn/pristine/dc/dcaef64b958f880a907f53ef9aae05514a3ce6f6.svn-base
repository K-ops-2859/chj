package com.dikai.chenghunjiclient.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.dikai.chenghunjiclient.R;

/**
 * Created by Lucio on 2017/8/28.
 */

public class MyImageView extends ImageView {

    private float ratio = 1;

    public MyImageView(Context context) {
        this(context,null);
    }

    public MyImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.myImageView);
        ratio = ta.getFloat(R.styleable.myImageView_aspect_ratio,1);
        //记得此处要recycle();
        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取宽度的模式和尺寸
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        //宽确定，高不确定
        int heightSize = (int)(widthSize / ratio);//根据宽度和比例计算高度
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
        //必须调用下面的两个方法之一完成onMeasure方法的重写，否则会报错
//        setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
