package net;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;


import net.callback.AbsCall;
import net.callback.OnDownLoadListener;
import net.config.HttpClientBuilder;
import net.config.Method;
import net.config.ThreadManager;
import net.request.BaseRequest;
import net.request.GetRequest;
import net.request.MultPostRequest;
import net.request.PostRequest;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;

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

    private static Map<Object, Object> mParms = null;
    private static Map<Object, Object> mHeader = null;

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
        HttpClientBuilder.getInstance().ModifyCancels(tag, null);
    }



    public HttpClientBuilder init(Context app) {
//        DBManage.getInstance().init(app);
        if (app==null){
            NetWorkUtil.post("").post(MediaType.parse(""),"").builder(new AbsCall<String>() {
                @Override
                public void onSucceed(String s, Call call) {

                }
            });

        NetWorkUtil.post("").addParam("","").addHeader("","").setTag(this);
        NetWorkUtil.get("").addParam("","").addHeader("","").setTag(this);
        NetWorkUtil.multPost("").addParam("","").addHeader("","").setTag(this).builder("", new OnDownLoadListener() {
            @Override
            public void onDLoadProgress(long total, long progress, String path) {
                super.onDLoadProgress(total, progress, path);
            }

            @Override
            public void onSucceed(Object o, Call call) {

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
        this.context = app;
        return HttpClientBuilder.getInstance();
    }
}
