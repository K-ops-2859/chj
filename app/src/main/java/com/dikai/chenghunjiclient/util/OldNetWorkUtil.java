package com.dikai.chenghunjiclient.util;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by cmk03 on 2018/8/11.
 */

public class OldNetWorkUtil {

    String oldUrl = "http://121.42.156.151:76/api/";
    // 正式   "http://121.42.156.151:6780/api/"
    // 内部   "http://121.42.156.151:6779/api/"
    private static final String baseUrl = "http://121.42.156.151:6779/api/";

    public static void setCallback(String method, Object object, CallBackListener mCallBackListener) {
        HttpCallback callback = new HttpCallback();
        callback.getResult(mCallBackListener);
        String jsonPara = new Gson().toJson(object);
        Log.e("json参数： ", "" + jsonPara);
        String url = baseUrl + method;
        callback.execute(url, jsonPara);
    }

    /**
     * 网络请求异步任务
     */
    public static class HttpCallback extends AsyncTask<String, Integer, String> {

        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        private CallBackListener mCallBackListener;

        public String getRemoteInfo(String url, String jsonPara) {
            String jsonData = null;
            try {
                OkHttpClient client = new OkHttpClient();

                RequestBody requestBody = RequestBody.create(JSON, jsonPara);
                //TODO:1 AppKey=PrivateKey:JsonParas
                String temp1 = "hqoaapp001=(*$=7643001)=$*):" + jsonPara;
                //TODO:2 MD5(AppKey=PrivateKey:JsonParas)
                String temp2 = MD5Util.md5(temp1);
                String credential = Credentials.basic("hqoaapp001", temp2);
                Log.e("步骤结果：", "  " + "\n" + temp1 + "\n" + temp2 + "\n" + credential);
                Request request = new Request.Builder()
                        .header("Authorization", credential)
                        .url(url)
                        .post(requestBody)
                        .build();

                Response response = client.newCall(request).execute();
                jsonData = response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("错误：", "   " + e.toString());
            }
            return jsonData;
        }

        @Override
        protected String doInBackground(String... params) {
            String ret = null;
            if (params != null) {
                ret = getRemoteInfo(params[0], params[1]);
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
         *
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

    public static String getMD5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
