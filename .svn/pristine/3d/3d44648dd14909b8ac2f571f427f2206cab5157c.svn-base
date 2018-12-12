/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.dikai.chenghunjiclient.wxapi;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.dikai.chenghunjiclient.BaseApplication;
import com.dikai.chenghunjiclient.bean.BeanGetWXToken;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import org.greenrobot.eventbus.EventBus;

import cn.sharesdk.wechat.utils.WechatHandlerActivity;

/** 微信客户端回调activity示例 */
public class WXEntryActivity extends WechatHandlerActivity implements IWXAPIEventHandler {
	private static final String TAG = "微信回调";
	private static final int RETURN_MSG_TYPE_LOGIN = 1; //登录
	private static final int RETURN_MSG_TYPE_SHARE = 2; //分享
	private WXEntryActivity mContext;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		//这句没有写,是不能执行回调的方法的
		((BaseApplication)getApplication()).getWxApi().handleIntent(getIntent(), this);
	}

//	/**
//	 * 处理微信发出的向第三方应用请求app message
//	 * <p>
//	 * 在微信客户端中的聊天页面有“添加工具”，可以将本应用的图标添加到其中
//	 * 此后点击图标，下面的代码会被执行。Demo仅仅只是打开自己而已，但你可
//	 * 做点其他的事情，包括根本不打开任何页面
//	 */
//	public void onGetMessageFromWXReq(WXMediaMessage msg) {
//		if (msg != null) {
//			Intent iLaunchMyself = getPackageManager().getLaunchIntentForPackage(getPackageName());
//			startActivity(iLaunchMyself);
//		}
//	}
//
//	/**
//	 * 处理微信向第三方应用发起的消息
//	 * <p>
//	 * 此处用来接收从微信发送过来的消息，比方说本demo在wechatpage里面分享
//	 * 应用时可以不分享应用文件，而分享一段应用的自定义信息。接受方的微信
//	 * 客户端会通过这个方法，将这个信息发送回接收方手机上的本demo中，当作
//	 * 回调。
//	 * <p>
//	 * 本Demo只是将信息展示出来，但你可做点其他的事情，而不仅仅只是Toast
//	 */
//	public void onShowMessageFromWXReq(WXMediaMessage msg) {
//		if (msg != null && msg.mediaObject != null
//				&& (msg.mediaObject instanceof WXAppExtendObject)) {
//			WXAppExtendObject obj = (WXAppExtendObject) msg.mediaObject;
//			Toast.makeText(this, obj.extInfo, Toast.LENGTH_SHORT).show();
//		}
//	}

	// 微信发送请求到第三方应用时，会回调到该方法
	@Override
	public void onReq(BaseReq baseReq) {

	}

	@Override
	public void onResp(BaseResp baseResp) {
		Log.e(TAG, "onResp:------>");
		Log.e(TAG, "error_code:---->" + baseResp.errCode);
		int type = baseResp.getType(); //类型：分享还是登录
		switch (baseResp.errCode) {
			case BaseResp.ErrCode.ERR_AUTH_DENIED:
				//用户拒绝授权
				Toast.makeText(this, "拒绝授权微信登录", Toast.LENGTH_SHORT).show();
			case BaseResp.ErrCode.ERR_USER_CANCEL:
				//用户取消
				String message = "";
				if (type == RETURN_MSG_TYPE_LOGIN) {
					message = "取消了微信登录";
				} else if (type == RETURN_MSG_TYPE_SHARE) {
					message = "取消了微信分享";
				}
				Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
				break;
			case BaseResp.ErrCode.ERR_OK:
				//用户同意
				if (type == RETURN_MSG_TYPE_LOGIN) {
					//用户换取access_token的code，仅在ErrCode为0时有效
					String code = ((SendAuth.Resp) baseResp).code;
					Log.i(TAG, "code:------>" + code);
					//这里拿到了这个code，去做2次网络请求获取access_token和用户个人信息
					EventBus.getDefault().post(new EventBusBean(Constants.WX_LOGIN_TOKEN,code));
				} else if (type == RETURN_MSG_TYPE_SHARE) {
					Toast.makeText(this, "微信分享成功", Toast.LENGTH_SHORT).show();
				}
				break;
		}
	}


}
