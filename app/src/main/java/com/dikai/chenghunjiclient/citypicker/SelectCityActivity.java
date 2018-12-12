package com.dikai.chenghunjiclient.citypicker;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.gyf.barlibrary.ImmersionBar;
import org.greenrobot.eventbus.EventBus;

public class SelectCityActivity extends AppCompatActivity {

    private FragmentTransaction transaction;
    private FragmentManager manager;
    private boolean canBack = true;
    private int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_select_city);
            ImmersionBar.with(this)
                    .statusBarView(R.id.top_view)
                    .statusBarColor(R.color.white)
                    .statusBarDarkFont(true, 0.2f)
                    .init();
            findViewById(R.id.activity_city_return_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            type = getIntent().getIntExtra("type",Constants.SELECT_COUNTRY);
            setFragment("");
        }catch (Exception e){
            Log.e("city1:",e.toString());
        }
    }

    public void setFragment(Object object){
        Log.e("city:","there1");
        SelectCityFragment fragment = SelectCityFragment.newInstance(object);
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(R.id.city_select_frame, fragment);
        transaction.show(fragment);
//        if (manager.getBackStackEntryCount()>1){
        transaction.addToBackStack(null);
//        }
        transaction.commit();
        Log.e("city:","there2");
    }

    public void sendCity(Country country){
//        if(type == Constants.SELECT_CITY_FROM){
//            EventBus.getDefault().post(new EventBusClass.CityRefresh(Constants.SELECT_CITY_FROM,country));
//        }else if(type == Constants.SELECT_CITY_LOCATION){
//            EventBus.getDefault().post(new EventBusClass.CityRefresh(Constants.SELECT_CITY_LOCATION,country));
//        }else if(type == Constants.XUNXUN_SELECT_CITY){
//            EventBus.getDefault().post(new EventBusClass.XunXunBus(Constants.XUNXUN_SELECT_CITY,country));
//        }else{
//        }
        EventBus.getDefault().post(new EventBusBean(type,country));
//        if(type == Constants.HOME_SELECT_CITY){
//        }else if(type == Constants.SELECT_HOTEL_CITY){
//            EventBus.getDefault().post(new EventBusBean(Constants.SELECT_HOTEL_CITY,country));
//        }else {
//            EventBus.getDefault().post(new EventBusBean(Constants.SELECT_COUNTRY,country));
//        }
        finish();
    }

    @Override
    public void onBackPressed() {
        if(canBack){

            if (manager.getBackStackEntryCount() > 1){
                super.onBackPressed();
            }else {
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}
