package com.dikai.chenghunjiclient.util;

import android.content.Context;
import android.util.Log;

import com.dikai.chenghunjiclient.bean.BeanWXPay;
import com.dikai.chenghunjiclient.entity.ResultWXPay;
import com.dikai.chenghunjiclient.wxapi.MD5;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by Lucio on 2016/8/27.
 */
public class PayUtil {
    private static PayUtil mPayUtil;
    private static IWXAPI api;
    private String orderNum;
    private int type = -1;
    private String payCode;
    private PayUtil(){

    }

    public static PayUtil getInstance(Context context){
        if(mPayUtil == null){
            mPayUtil = new PayUtil();
        }
        api = WXAPIFactory.createWXAPI(context.getApplicationContext(), null);
        api.registerApp(Constants.WX_APP_ID);
        return mPayUtil;
    }

    public void wxPay(String TransNumber, final OnPayListener payListener){
        this.orderNum = TransNumber;
        NetWorkUtil.setCallback("HQOAApi/WeChatAppPay",
                new BeanWXPay(TransNumber),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultWXPay result = new Gson().fromJson(respose, ResultWXPay.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
                                PayReq req = new PayReq();
                                parameters.put("appid", result.getAppid());
                                parameters.put("noncestr", result.getNoncestr());
                                parameters.put("package", result.getPackageValue());
                                parameters.put("partnerid", result.getPartnerid());
                                parameters.put("prepayid", result.getPrepayid());
                                parameters.put("timestamp", result.getTimestamp());
                                String characterEncoding = "UTF-8";
                                String mySign = createSign(characterEncoding, parameters);

                                req.appId = result.getAppid();
                                req.partnerId = result.getPartnerid();
                                req.prepayId = result.getPrepayid();
                                req.nonceStr = result.getNoncestr();
                                req.timeStamp = result.getTimestamp();
                                req.packageValue = result.getPackageValue();
                                req.sign = mySign;
//                                Toast.makeText(sContext, respose, Toast.LENGTH_LONG).show();
                                api.sendReq(req);
                                payListener.onFinish("请求成功");
                            } else {
                                payListener.onError(result.getMessage().getInform());
                            }
                        } catch (Exception e) {
                            payListener.onError("json解析出错:" + e.toString());
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        payListener.onError("支付网络错误：" + e.toString());
                    }
                });
    }

    public interface OnPayListener{
        void onFinish(String info);
        void onError(String e);
    }

    /**
     * MD5加密算法加密sign
     * @param characterEncoding
     * @param parameters
     * @return
     */
    public String createSign(String characterEncoding, SortedMap<Object, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();// 所有参与传参的参数按照accsii排序（升序）
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=");
        sb.append("69c7b9898d9b4fb9a5428f6d960faed8");//商户密钥
        String sign = MD5.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
        return sign;
    }

    public String double2Str(double num){
        String string = new java.text.DecimalFormat("#########0.00").format(num);
        return string;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }
}
