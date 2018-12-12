package com.dikai.chenghunjiclient.util;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.dikai.chenghunjiclient.entity.ResultCode;
import com.google.gson.Gson;
import com.yanzhenjie.nohttp.FileBinary;
import com.yanzhenjie.nohttp.OnUploadListener;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.rest.StringRequest;
import com.yanzhenjie.nohttp.rest.SyncRequestExecutor;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import id.zelory.compressor.Compressor;

/**
 * Created by Lucio on 2016/8/24.
 */
public class UpLoadImgThread {

    private static UpLoadImgThread sImgThread;
    private Context mContext;
    private UpLoadImgThread(Context context){
        mContext = context.getApplicationContext();
    }

    public static UpLoadImgThread getInstance(Context context){
        if(sImgThread == null){
            sImgThread = new UpLoadImgThread(context);
        }
        return sImgThread;
    }

    public void upLoad(List<String> pathList, String url, String id, String type, final CallBackListener mCallBackListener){
        MyHandler handler = new MyHandler();
        handler.setCallBackListener(mCallBackListener,pathList.size());
        ExecutorService singlePool = Executors.newSingleThreadExecutor();
        for (int i = 0; i < pathList.size(); i++) {
            singlePool.execute(new UploadTask(pathList.get(i),url,id,type,handler));
        }
    }

    public void upLoadNet(List<String> pathList, List<Boolean> isNet, String url, String id, String type, final CallBackListener mCallBackListener){
        MyHandler handler = new MyHandler();
        handler.setCallBackListener(mCallBackListener,pathList.size());
        ExecutorService singlePool = Executors.newSingleThreadExecutor();
        for (int i = 0; i < pathList.size(); i++) {
            singlePool.execute(new UploadNetTask(pathList.get(i),isNet.get(i),url,id,type,handler));
        }
    }

    /**
     * 图片上传线程
     */
    public class UploadTask implements Runnable {

        private String filePath;
        private String url;
        private String iduserid;
        private String type;
        private String[] values;
        private MyHandler mMyHandler;

        public UploadTask(String filepath, String url, String iduserid, String type, MyHandler handler){
            this.filePath = filepath;
            this.url = url;
            this.iduserid = iduserid;
            this.type = type;
            mMyHandler = handler;
        }

        //发送返回的数据
        @Override
        public void run() {
            String[] values = upload(filePath,url,iduserid,type);
            if("1".equals(values[0])){
                Message message = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putString("info",values[1]);
                message.setData(bundle);
                message.what = 0;
                mMyHandler.sendMessage(message);
            }else {
                Message message = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putString("info",values[1]);
                message.setData(bundle);
                message.what = 1;
                mMyHandler.sendMessage(message);
            }
        }

        //上传图片并返回服务器返回的数据
        public String[] upload(String filepath, String url, String id, String type) {
            try {
                Map<String,Object> params = new HashMap<>();
                params.put("os","2");
                params.put("ot","0");
                params.put("oi",id);
                params.put("t",type);
                Log.e("参数："," filepath: " + filepath + " url: " + url +  " id: " + id +  " type: " + type);
                final StringRequest request = new StringRequest(url, RequestMethod.POST);

                File tempPath;
                if("2".equals(type) || "1".equals(type)){
                    tempPath = new Compressor(mContext).compressToFile(new File(filepath));
                    request.add(params).add("file", tempPath);
                }else {
                    tempPath = new File(filepath);
                    FileBinary binary  = new FileBinary(tempPath);
                    //上传文件添加监听是在FileBinary中添加的。
                    binary.setUploadListener(1, new OnUploadListener() {
                        @Override
                        public void onStart(int what) {
                            EventBus.getDefault().post(new EventBusBean(Constants.UPLOAD_VIDEO_START));
                        }

                        @Override
                        public void onCancel(int what) {

                        }

                        @Override
                        public void onProgress(int what, int progress) {
                            EventBus.getDefault().post(new EventBusBean(Constants.UPLOAD_VIDEO_PROGRESS, progress));
                        }

                        @Override
                        public void onFinish(int what) {
                            EventBus.getDefault().post(new EventBusBean(Constants.UPLOAD_VIDEO_END));
                        }

                        @Override
                        public void onError(int what, Exception exception) {
                            EventBus.getDefault().post(new EventBusBean(Constants.UPLOAD_VIDEO_ERROR));
                        }
                    });
                    request.add(params).add("file",binary);
                }
                Response<String> response = SyncRequestExecutor.INSTANCE.execute(request);
                Log.e("返回值",response.toString());
                if (response.isSucceed()) {
                    Log.e("返回值",response.get());
                    ResultCode result = new Gson().fromJson(response.get(), ResultCode.class);
                    values = new String[]{"1",result.getInform()};
                } else {
                    // 请求失败，拿到错误：
                    Exception e = response.getException();
                    values = new String[]{"0","网络请求失败:" + e.toString()};
                }

            }catch (Exception e){
                Log.e("try：",e.toString());
                values = new String[]{"0","图片上传失败"};
            }
            return values;
        }
    }

    /**
     * 网络图片上传线程
     */
    public class UploadNetTask implements Runnable {

        private String filePath;
        private String url;
        private String iduserid;
        private String type;
        private boolean isNetPic;
        private String[] values;
        private MyHandler mMyHandler;

        public UploadNetTask(String filepath, boolean isNetPic,String url, String iduserid, String type, MyHandler handler){
            this.filePath = filepath;
            this.isNetPic = isNetPic;
            this.url = url;
            this.iduserid = iduserid;
            this.type = type;
            mMyHandler = handler;
        }

        //发送返回的数据
        @Override
        public void run() {
            String[] values = upload(filePath,isNetPic,url,iduserid,type);
            if("1".equals(values[0])){
                Message message = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putString("info",values[1]);
                message.setData(bundle);
                message.what = 0;
                mMyHandler.sendMessage(message);
            }else {
                Message message = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putString("info",values[1]);
                message.setData(bundle);
                message.what = 1;
                mMyHandler.sendMessage(message);
            }
        }

        //上传图片并返回服务器返回的数据
        public String[] upload(String filepath, boolean isNetPic, String url, String id, String type) {
            try {
                Map<String,Object> params = new HashMap<>();
                params.put("os","2");
                params.put("ot","0");
                params.put("oi",id);
                params.put("t",type);
                Log.e("参数："," filepath: " + filepath + " url: " + url +  " id: " + id +  " type: " + type);
                final StringRequest request = new StringRequest(url, RequestMethod.POST);
                File glidePath = new File(filepath);
                if(isNetPic){
                    glidePath = Glide.with(mContext).load(filepath).downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
                }
                File tempPath = new Compressor(mContext).compressToFile(glidePath);
                request.add(params).add("file", tempPath);

                Response<String> response = SyncRequestExecutor.INSTANCE.execute(request);
                Log.e("返回值",response.toString());
                if (response.isSucceed()) {
                    Log.e("返回值",response.get());
                    ResultCode result = new Gson().fromJson(response.get(), ResultCode.class);
                    values = new String[]{"1",result.getInform()};
                } else {
                    // 请求失败，拿到错误：
                    Exception e = response.getException();
                    values = new String[]{"0","网络请求失败:" + e.toString()};
                }
            }catch (Exception e){
                Log.e("try：",e.toString());
                values = new String[]{"0","图片上传失败"};
            }
            return values;
        }
    }


    /**
     * 回调接口
     */
    public interface CallBackListener {
        void onFinish(List<String> values);
        void onError(String e);
    }

    /**
     * Handler传递消息
     */
    private class MyHandler extends Handler{
        private CallBackListener mCallBackListener;
        private int temp;
        private int total;
        private List<String> values;
        public void setCallBackListener(CallBackListener callBackListener, int size) {
            mCallBackListener = callBackListener;
            temp = 0;
            values = new ArrayList<>();
            total = size;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String info = msg.getData().getString("info");
            temp ++;
            switch (msg.what){
                case 0:
                    values.add(info);
                    if(temp == total){
                        mCallBackListener.onFinish(values);
                    }
                    break;
                case 1:
                    Log.e("图片上传失败","第" + temp + "张照片上传失败" + info);
                    mCallBackListener.onError(info);
                    break;
            }
        }
    }
}
