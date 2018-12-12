package com.dikai.chenghunjiclient.popcorn;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.entity.LuckyBean;
import com.dikai.chenghunjiclient.view.MyRelativeLayout;

import java.util.List;

public class LuckyMonkeyPanelView extends MyRelativeLayout {


    private ImageView start;
    private ImageView bg_1;
    private ImageView bg_2;
    private long times = 500;

    private MyLuckyItem itemView1, itemView2, itemView3,
            itemView4, itemView5,itemView6, itemView7,
            itemView8, itemView9, itemView10;

    private MyLuckyItem[] itemViewArr = new MyLuckyItem[10];
    private int currentIndex = 0;
    private int currentTotal = 0;
    private int stayIndex = 0;

    private boolean isMarqueeRunning = false;
    private boolean isGameRunning = false;
    private boolean isTryToStop = false;

    private static final int DEFAULT_SPEED = 300;
    private static final int MIN_SPEED = 80;
    private int currentSpeed = DEFAULT_SPEED;

    LuckyMonkeyAnimationListener mListener;

    public LuckyMonkeyPanelView(@NonNull Context context) {
        this(context, null);
    }

    public LuckyMonkeyPanelView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LuckyMonkeyPanelView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.view_lucky_mokey_panel, this);
        setupView();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
//        startMarquee();
    }

    @Override
    protected void onDetachedFromWindow() {
        stopMarquee();
        super.onDetachedFromWindow();
    }

    private void setupView() {
        bg_1 = (ImageView) findViewById(R.id.bg_1);
        bg_2 = (ImageView) findViewById(R.id.bg_2);
        start = (ImageView) findViewById(R.id.start);
        itemView1 = (MyLuckyItem) findViewById(R.id.item1);
        itemView2 = (MyLuckyItem) findViewById(R.id.item2);
        itemView3 = (MyLuckyItem) findViewById(R.id.item3);
        itemView4 = (MyLuckyItem) findViewById(R.id.item4);
        itemView5 = (MyLuckyItem) findViewById(R.id.item5);
        itemView6 = (MyLuckyItem) findViewById(R.id.item6);
        itemView7 = (MyLuckyItem) findViewById(R.id.item7);
        itemView8 = (MyLuckyItem) findViewById(R.id.item8);
        itemView9 = (MyLuckyItem) findViewById(R.id.item9);
        itemView10 = (MyLuckyItem) findViewById(R.id.item10);
        itemViewArr[0] = itemView1;
        itemViewArr[1] = itemView2;
        itemViewArr[2] = itemView3;
        itemViewArr[3] = itemView4;
        itemViewArr[4] = itemView5;
        itemViewArr[5] = itemView6;
        itemViewArr[6] = itemView7;
        itemViewArr[7] = itemView8;
        itemViewArr[8] = itemView9;
        itemViewArr[9] = itemView10;
    }

    public void setData(List<LuckyBean> data){
        if(data.size() >= 10){
            for (int i = 0; i < 10; i++) {
                itemViewArr[i].setData(data.get(i).getImgurl(),data.get(i).getPrizeName());
            }
        }
    }

    private void stopMarquee() {
        isMarqueeRunning = false;
        isGameRunning = false;
        isTryToStop = false;
    }

    private void startMarquee() {
        isMarqueeRunning = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isMarqueeRunning) {
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    post(new Runnable() {
                        @Override
                        public void run() {
                            if (bg_1 != null && bg_2 != null) {
                                if (VISIBLE == bg_1.getVisibility()) {
                                    bg_1.setVisibility(GONE);
                                    bg_2.setVisibility(VISIBLE);
                                } else {
                                    bg_1.setVisibility(VISIBLE);
                                    bg_2.setVisibility(GONE);
                                }
                            }
                        }
                    });
                }
            }
        }).start();
    }

    private long getInterruptTime() {
        currentTotal++;
        if (isTryToStop) {
            currentSpeed += 20;
            if (currentSpeed > DEFAULT_SPEED) {
                currentSpeed = DEFAULT_SPEED;
            }
        } else {
            if (currentTotal / itemViewArr.length > 0) {
                currentSpeed -= 100;
            }
            if (currentSpeed < MIN_SPEED) {
                currentSpeed = MIN_SPEED;
            }
        }
        return currentSpeed;
    }

    public boolean isGameRunning() {
        return isGameRunning;
    }

    public void startGame() {
        reset();
        isGameRunning = true;
        isTryToStop = false;
        currentSpeed = DEFAULT_SPEED;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isGameRunning) {
                    try {
                        Thread.sleep(getInterruptTime());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    post(new Runnable() {
                        @Override
                        public void run() {
                            int preIndex = currentIndex;
                            currentIndex++;
                            if (currentIndex >= itemViewArr.length) {
                                currentIndex = 0;
                            }
                            if(isGameRunning){
                                itemViewArr[preIndex].setFocus(false);
                                itemViewArr[currentIndex].setFocus(true);
                                Log.e("设置位置",currentIndex+" preIndex:"+preIndex);
                            }
                            if (isTryToStop && currentSpeed == DEFAULT_SPEED && stayIndex == currentIndex) {
                                isGameRunning = false;
                                if (mListener != null) {
                                    mListener.onAnimationEnd();
                                }
                                Log.e("停止位置",currentIndex+"");
                            }
                        }
                    });
                }
            }
        }).start();
    }

    public void tryToStop(int position) {
        stayIndex = position;
        isTryToStop = true;
    }

    public void reset() {
        isGameRunning = false;
        isTryToStop = false;
        isMarqueeRunning = true;
        currentIndex = 0;
        currentTotal = 0;
        stayIndex = 0;
        currentSpeed = DEFAULT_SPEED;
        mListener = null;

        for (ItemView itemView : itemViewArr) {
            itemView.setFocus(false);
        }
    }

    public void setStartListener(View.OnClickListener onClickListener){
        start.setOnClickListener(onClickListener);
    }

    public void setGameListener(LuckyMonkeyAnimationListener listener) {
        mListener = listener;
    }

    public interface LuckyMonkeyAnimationListener {
        void onAnimationEnd();
    }
}
