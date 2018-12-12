package com.dikai.chenghunjiclient.util;

import android.os.AsyncTask;
import android.util.Log;
import com.google.gson.Gson;
import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/10/25.
 */

public class NetWorkUtil {

    public static void setCallback(String method, Object object, CallBackListener mCallBackListener) {
        HttpCallback callback = new HttpCallback();
        callback.getResult(mCallBackListener);
        String jsonPara = new Gson().toJson(object);
       // String url = "http://121.42.156.151:6780/api/" + method;//正式
        String url = "http://121.42.156.151:52373/api/" + method;//测试
        Log.e("json参数： ","" + jsonPara + "  head:" + method);
        callback.execute(url,jsonPara);
    }

    /**
     * 网络请求异步任务
     */
    public static class HttpCallback extends AsyncTask<String,Integer,String>{

        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        private CallBackListener mCallBackListener;

        public String getRemoteInfo(String url,String jsonPara) {
            String jsonData = null;
            try {
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = RequestBody.create(JSON, jsonPara);
                //TODO:1 AppKey=PrivateKey:JsonParas
                String temp1 = "hqoaapp001=(*$=7i3XwXWz)=$*):" + jsonPara;
                //TODO:2 MD5(AppKey=PrivateKey:JsonParas)
                String temp2 = MD5Util.md5(temp1);
                String credential = Credentials.basic("hqoaapp001", temp2);
                Log.e("步骤结果：","  " + "\n" + temp1 + "\n" + temp2+ "\n" + credential);
                Request request = new Request.Builder()
                        .header("Authorization",credential)
                        .url(url)
                        .post(requestBody)
                        .build();

                Response response = client.newCall(request).execute();
                jsonData = response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("错误：","   " + e.toString());
            }
            return jsonData;
        }

        @Override
        protected String doInBackground(String... params) {
            String ret = null;
            if(params != null){
                ret = getRemoteInfo(params[0],params[1]);
            }
            return ret;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null) {
                mCallBackListener.onFinish(s);
            } else {
                mCallBackListener.onError("结果为空");
            }
        }

        /**
         * 接口回调
         * @param callBackListener 回调接口
         */
        public void getResult(CallBackListener callBackListener) {
            mCallBackListener = callBackListener;
        }
    }

    /**
     * 回调接口
     */
    public interface CallBackListener {
        void onFinish(String respose);
        void onError(String e);
    }

    //=======================================微信相关==============================================

    /**
     * 微信登录请求access_token
     * @param baseUrl 请求网址
     * @param mCallBackListener 回调
     */
    public static void wxLogin(String baseUrl, CallBackListener mCallBackListener){
        WXHttpCallback callback = new WXHttpCallback();
        callback.getResult(mCallBackListener);
//        String jsonPara = new Gson().toJson(object);
        Log.e("json参数： ","  baseUrl:" + baseUrl);
        callback.execute(baseUrl,"");
    }

    /**
     * 微信登录请求access_token
     */
    public static class WXHttpCallback extends AsyncTask<String,Integer,String>{

        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        private CallBackListener mCallBackListener;

        public String getRemoteInfo(String url,String jsonPara) {
            String jsonData = null;
            try {
                OkHttpClient client = new OkHttpClient();
//                RequestBody requestBody = RequestBody.create(JSON, jsonPara);
//                String temp1 = "hqoaapp001=(*$=7i3XwXWz)=$*):" + jsonPara;
//                String temp2 = MD5Util.md5(temp1);
//                String credential = Credentials.basic("hqoaapp001", temp2);
//                Log.e("步骤结果：","  " + "\n" + temp1 + "\n" + temp2+ "\n" + credential);
                Request request = new Request.Builder()
//                        .header("Authorization",credential)
                        .url(url)
//                        .post(requestBody)
                        .build();
                Response response = client.newCall(request).execute();
                jsonData = response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("错误：","   " + e.toString());
            }
            return jsonData;
        }

        @Override
        protected String doInBackground(String... params) {
            String ret = null;
            if(params != null){
                ret = getRemoteInfo(params[0],params[1]);
            }
            return ret;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null) {
                mCallBackListener.onFinish(s);
            } else {
                mCallBackListener.onError("结果为空");
            }
        }

        /**
         * 接口回调
         * @param callBackListener 回调接口
         */
        public void getResult(CallBackListener callBackListener) {
            mCallBackListener = callBackListener;
        }
    }

}
