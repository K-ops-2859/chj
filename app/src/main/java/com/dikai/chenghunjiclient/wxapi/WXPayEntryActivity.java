package com.dikai.chenghunjiclient.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.bean.BeanGetWXToken;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.PayUtil;
import com.gyf.barlibrary.ImmersionBar;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler, View.OnClickListener {

	private static final int RETURN_MSG_TYPE_LOGIN = 1;
	private static final int RETURN_MSG_TYPE_SHARE = 2;
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	private boolean paySucceed = false;
	private TextView payInfo;
	private ImageView payImg;
    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_success_pay);
		ImmersionBar.with(this)
				.statusBarView(R.id.top_view)
				.statusBarColor(R.color.white)
				.statusBarDarkFont(true, 0.2f)
				.init();
		api = WXAPIFactory.createWXAPI(this, Constants.WX_APP_ID);
		api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ImmersionBar.with(this).destroy();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {

	}

	@Override
	public void onResp(BaseResp resp) {
		findViewById(R.id.activity_wxpay_return_layout).setOnClickListener(this);
		payImg = (ImageView) findViewById(R.id.activity_wxpay_img);
		payInfo = (TextView) findViewById(R.id.activity_wxpay_info);

		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {//微信支付
			if(resp.errCode == 0){
				//支付成功
				EventBus.getDefault().post(new EventBusBean(PayUtil.getInstance(this).getType()));
				payImg.setImageResource(R.mipmap.ic_app_pay_success);
				payInfo.setText("支付成功");
			}else if(resp.errCode == -2){
				finish();
			}else {
				payImg.setImageResource(R.mipmap.ic_app_pay_failed);
				payInfo.setText("错误"+ resp.errCode + resp.errStr +" 支付失败,若已扣款请联系客服");
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.activity_wxpay_return_layout:
				onBackPressed();
				break;
		}
	}

}