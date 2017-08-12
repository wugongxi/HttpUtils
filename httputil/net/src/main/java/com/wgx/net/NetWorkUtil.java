package com.wgx.net;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.wgx.net.config.HttpClientBuilder;
import com.wgx.net.config.Method;
import com.wgx.net.config.ThreadManager;
import com.wgx.net.request.BaseRequest;
import com.wgx.net.request.GetRequest;
import com.wgx.net.request.MultPostRequest;
import com.wgx.net.request.PostRequest;

/**
 * Created by WU on 2017/6/12.
 */

public class NetWorkUtil {
    private Context context;
    private NetWorkUtil() {
        ThreadManager.start();
    }

    private static NetWorkUtil netWorkUtil;

    public static NetWorkUtil getInstance() {
        if (netWorkUtil == null) {
            netWorkUtil = new NetWorkUtil();
        }
        initDefault();
        return netWorkUtil;
    }

    Handler mhandler = new Handler(Looper.getMainLooper());

    public Handler getHandler() {
        return mhandler;
    }

    private static HttpClientBuilder initDefault() {
        return HttpClientBuilder.getInstance();
    }

    public static GetRequest get(String url) {
        return GetRequest.getInstance().url(url);
    }
    public static BaseRequest url(String url) {
        switch (HttpClientBuilder.getInstance().getDefaultMethod()){
            case Method.get:
                return get(url);
            case Method.post:
                return post(url);
            default:
                return get(url);
        }
    }
    public static BaseRequest multPost(String url) { return MultPostRequest.getInstance().url(url);
    }
    public static PostRequest post(String url) {
        return PostRequest.getInstance().url(url);
    }

    public void cancle(Object tag) {
        HttpClientBuilder.getInstance().cancelCall(tag);
    }

    static int x = 0;
    public static HttpClientBuilder init(Context app) {
//        DBManage.getInstance().init(app);
   /**
        if (x!=0){
            NetWorkUtil.post("").post(MediaType.parse(""),"").builder(new JCallBack<String>() {
                @Override
                public void onSucceed(String s, Call call) {

                }
            });

        NetWorkUtil.post("").param("","").addHeader("","").setTag(this);
        NetWorkUtil.get("").param("","").addHeader("","").setTag(this);
        NetWorkUtil.multPost("").param("","").addHeader("","").setTag(this).builder("", new OnDownLoadListener() {
            @Override
            public void onDLoadProgress(long total, long progress, String path) {
                super.onDLoadProgress(total, progress, path);
            }

            @Override
            public void onSucceed(File o, Call call) {

            }

            @Override
            public void onDFaild(String path, Call call, IOException e) {
                super.onDFaild(path, call, e);
            }

            @Override
            public void onAfter(Call call) {
                super.onAfter(call);
            }
        });
        }
    */
        return HttpClientBuilder.init(app);
    }
}
