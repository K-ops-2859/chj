package com.dikai.chenghunjiclient.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.dikai.chenghunjiclient.R;

/**
 * Created by Lucio on 2018/5/24.
 */

public class MyTextView extends LinearLayout{

    private TextView leftText;
    private TextView rightText;
    private String leftValue;
    private String rightValue;
    private int leftMarginl;
    private int leftMarginr;
    private int leftPaddingl;
    private int leftPaddingr;

    private int rightMarginl;
    private int rightMarginr;
    private int rightMargint;
    private int rightMarginb;

    private int rightPaddingl;
    private int rightPaddingr;
    private int rightPaddingt;
    private int rightPaddingb;
    private int allWidth;

    public MyTextView(Context context) {
        this(context,null);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.mytext_layout, this);
        leftText = (TextView) findViewById(R.id.my_text_left);
        rightText = (TextView) findViewById(R.id.my_text_right);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.myTextView);

        leftText.setTextSize(TypedValue.COMPLEX_UNIT_SP,ta.getInteger(R.styleable.myTextView_left_size,14));

        leftMarginl = dp2px(context,ta.getInteger(R.styleable.myTextView_left_margin_l,0));
        leftMarginr = dp2px(context,ta.getInteger(R.styleable.myTextView_left_margin_r,0));
        leftPaddingl = dp2px(context,ta.getInteger(R.styleable.myTextView_left_padding_l,0));
        leftPaddingr = dp2px(context,ta.getInteger(R.styleable.myTextView_left_padding_r,0));

        leftText.setPadding(dp2px(context,ta.getInteger(R.styleable.myTextView_left_padding_l,0)),
                dp2px(context,ta.getInteger(R.styleable.myTextView_left_padding_t,0)),
                dp2px(context,ta.getInteger(R.styleable.myTextView_left_padding_r,0)),
                dp2px(context,ta.getInteger(R.styleable.myTextView_left_padding_b,0)));
        setMargins(leftText,dp2px(context,ta.getInteger(R.styleable.myTextView_left_margin_l,0)),
                dp2px(context,ta.getInteger(R.styleable.myTextView_left_margin_t,0)),
                dp2px(context,ta.getInteger(R.styleable.myTextView_left_margin_r,0)),
                dp2px(context,ta.getInteger(R.styleable.myTextView_left_margin_b,0)));
        if (ta.getDrawable(R.styleable.myTextView_left_bg) != null) {
            leftText.setBackground(ta.getDrawable(R.styleable.myTextView_left_bg));
        }
        if (ta.getColor(R.styleable.myTextView_left_bg,-1) != -1) {
            leftText.setBackgroundColor(ta.getColor(R.styleable.myTextView_left_bg,-1));
        }
        if (ta.getColor(R.styleable.myTextView_left_color,-1) != -1) {
            leftText.setTextColor(ta.getColor(R.styleable.myTextView_left_color,-1));
        }
        if (ta.getResourceId(R.styleable.myTextView_left_color,-1) != -1) {
            leftText.setTextColor(ContextCompat.getColor(context,ta.getResourceId(R.styleable.myTextView_left_color,-1)));
        }

        rightText.setTextSize(TypedValue.COMPLEX_UNIT_SP,ta.getInteger(R.styleable.myTextView_right_size,14));

        rightMarginl = dp2px(context,ta.getInteger(R.styleable.myTextView_right_margin_l,0));
        rightMargint = dp2px(context,ta.getInteger(R.styleable.myTextView_right_margin_t,0));
        rightMarginr = dp2px(context,ta.getInteger(R.styleable.myTextView_right_margin_r,0));
        rightMarginb = dp2px(context,ta.getInteger(R.styleable.myTextView_right_margin_b,0));

        rightPaddingl = dp2px(context,ta.getInteger(R.styleable.myTextView_right_padding_l,0));
        rightPaddingt = dp2px(context,ta.getInteger(R.styleable.myTextView_right_padding_t,0));
        rightPaddingr = dp2px(context,ta.getInteger(R.styleable.myTextView_right_padding_r,0));
        rightPaddingb = dp2px(context,ta.getInteger(R.styleable.myTextView_right_padding_b,0));

        rightText.setPadding(rightPaddingl, rightPaddingt, rightPaddingr, rightPaddingb);
        setMargins(rightText,rightMarginl, rightMargint, rightMarginr, rightMarginb);
        if (ta.getDrawable(R.styleable.myTextView_right_bg) != null) {
            rightText.setBackground(ta.getDrawable(R.styleable.myTextView_right_bg));
        }
        if (ta.getColor(R.styleable.myTextView_right_bg,-1) != -1) {
            rightText.setBackgroundColor(ta.getColor(R.styleable.myTextView_right_bg,-1));
        }
        if (ta.getColor(R.styleable.myTextView_right_color,-1) != -1) {
            rightText.setTextColor(ta.getColor(R.styleable.myTextView_right_color,-1));
        }
        if (ta.getResourceId(R.styleable.myTextView_right_color,-1) != -1) {
            rightText.setTextColor(ContextCompat.getColor(context,ta.getResourceId(R.styleable.myTextView_right_color,-1)));
        }

        leftValue = ta.getString(R.styleable.myTextView_left_text) == null?"":ta.getString(R.styleable.myTextView_left_text);
        rightValue = ta.getString(R.styleable.myTextView_right_text) == null?"":ta.getString(R.styleable.myTextView_right_text);
        //记得此处要recycle();
        ta.recycle();
    }

    public void setText(String left,String right){
        setSize();
        leftText.setText(left);
        rightText.setText(right);
    }

    public void setRightTextBg(int resID){
        if(resID == 0){
            rightText.setBackground(null);
        }else {
            rightText.setBackgroundResource(resID);
        }
    }

    private void setSize(){
        float leftLength = getTextViewLength(leftText,leftValue);
        float rightLength = getTextViewLength(rightText,rightValue);
        if((leftLength + rightLength + leftMarginl + leftMarginr + leftPaddingl + leftPaddingr +
                rightMarginr + rightMarginl + rightPaddingl +rightPaddingr) > allWidth){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
            params.setMargins(rightMarginl, rightMargint, rightMarginr, rightMarginb);
            leftText.setLayoutParams(params);
        }
        Log.e("宽度","leftLength:"+leftLength+"  rightLength"+rightLength +"   allWidth:"+allWidth);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        Log.e("specMode:",""+specMode+"  specSize:"+specSize);
        if (specMode == MeasureSpec.EXACTLY) {//精确模式
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(result, specSize);
        }
        allWidth = result;
        setText(leftValue,rightValue);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private int dp2px(Context context,float dpValue){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue*scale + 0.5f);
    }

    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    // 计算出该TextView中文字的长度(像素)
    private float getTextViewLength(TextView textView, String text) {
        // 得到使用该paint写上text的时候,像素为多少
        return textView.getPaint().measureText(text);
    }
}
