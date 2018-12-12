package com.dikai.chenghunjiclient.util;
import android.os.AsyncTask;
import android.util.Log;

import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.download.DownloadRequest;
import com.yanzhenjie.nohttp.download.SimpleDownloadListener;
import com.yanzhenjie.nohttp.download.SyncDownloadExecutor;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Lucio on 2017/12/22.
 */

public class DownloadUtil {

    public static void setCallback(String url, String fileFolder, CallBackListener mCallBackListener) {
        HttpCallback callback = new HttpCallback();
        callback.getResult(mCallBackListener);
        callback.execute(url,fileFolder);
    }

    /**
     * 网络请求异步任务
     */
    public static class HttpCallback extends AsyncTask<String,Integer,String> {
        private String filePath;
        private CallBackListener mCallBackListener;

        public String getRemoteInfo(String url,String fileFolder) {
            filePath = null;
            DownloadRequest request = new DownloadRequest(url, RequestMethod.GET, fileFolder, true, true);
            SyncDownloadExecutor.INSTANCE.execute(0, request, new SimpleDownloadListener() {
                @Override
                public void onStart(int what, boolean resume, long range, Headers headers, long size) {
                    // 开始下载，回调的时候说明文件开始下载了。
                    // 参数1：what。
                    // 参数2：是否是断点续传，从中间开始下载的。
                    // 参数3：如果是断点续传，这个参数非0，表示之前已经下载的文件大小。
                    // 参数4：服务器响应头。
                    // 参数5：文件总大小，可能为0，因为服务器可能不返回文件大小。
                }

                @Override
                public void onProgress(int what, int progress, long fileCount, long speed) {
                    // 进度发生变化，服务器不返回文件总大小时不回调，因为没法计算进度。
                    // 参数1：what。
                    // 参数2：进度，[0-100]。
                    // 参数3：文件总大小，可能为0，因为服务器可能不返回文件大小。
                    // 参数4：下载的速度，含义为1S下载的byte大小，计算下载速度时：
                    //        int xKB = (int) speed / 1024; // 单位：xKB/S
                    //        int xM = (int) speed / 1024 / 1024; // 单位：xM/S
                    publishProgress(progress);
//                    EventBus.getDefault().post(new EventBusBean(Constants.UPDATE_PROGRESS,progress));
                    Log.e("下载进度：","====" + progress);
                }


                @Override
                public void onFinish(int what, String file) {
                    // 下载完成，参数2为保存在本地的文件路径。
                    filePath = file;
                }
            });
            return filePath;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mCallBackListener.onProgress(values[0]);
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
        void onProgress(int progress);
        void onError(String e);
    }

}
