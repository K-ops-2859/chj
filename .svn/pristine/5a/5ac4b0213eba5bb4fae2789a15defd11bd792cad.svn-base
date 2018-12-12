package com.dikai.chenghunjiclient.util;

import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Lucio on 2018/5/28.
 */

public class TextLUtil {
    public static void setLength(Context context, TextView leftText,TextView rightText,
                                 String leftValue, String rightValue, float margin, float padding){
        float leftLength = getTextViewLength(leftText,leftValue);
        float rightLength = getTextViewLength(rightText,rightValue);
        int allWidth = DensityUtil.getScreenWidth(context) - dp2px(context,margin);
        if((leftLength + rightLength + dp2px(context,padding)) > allWidth){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
            leftText.setLayoutParams(params);
        }else {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            leftText.setLayoutParams(params);
        }
        Log.e("宽度","leftLength:"+leftLength+"  rightLength"+rightLength +"   allWidth:"+allWidth);
        leftText.setText(leftValue);
        rightText.setText(rightValue);
    }

    private static int dp2px(Context context, float dpValue){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue*scale + 0.5f);
    }

    // 计算出该TextView中文字的长度(像素)
    private static float getTextViewLength(TextView textView, String text) {
        // 得到使用该paint写上text的时候,像素为多少
        if(textView == null || text == null){
            return 0;
        }else {
            return textView.getPaint().measureText(text);
        }
    }
}
