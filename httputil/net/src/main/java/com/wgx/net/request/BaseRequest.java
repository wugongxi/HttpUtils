package com.wgx.net.request;


import android.graphics.Bitmap;
import android.util.Log;

import com.wgx.net.LogUtil;
import com.wgx.net.callback.BitmapCallBack;
import com.wgx.net.callback.JCallBack;
import com.wgx.net.callback.callBackHelper.DownloadHelper;
import com.wgx.net.callback.callBackHelper.JsonCallbackHelper;
import com.wgx.net.callback.callBackHelper.OnBitmapHelper;
import com.wgx.net.callback.OnDownLoadListener;
import com.wgx.net.config.HttpClientBuilder;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by WU on 2017/6/17 0017.
 */

public abstract class BaseRequest<T extends BaseRequest> {

    protected CacheControl cacheControl = CacheControl.FORCE_NETWORK;
    protected String url = "";
    protected Object tag = null;
    public String method = HttpClientBuilder.getInstance().getDefaultMethod();
    protected Map<Object, Object> mPamer = new HashMap<>();
    protected static Headers.Builder mHander = new Headers.Builder();

    public BaseRequest() {
        Map<String, List<String>> baseHeader = getHttpClien().getheader();
        for (String s : baseHeader.keySet()) {
            List<String> strings = baseHeader.get(s);
            for (int i = 0; i < strings.size(); i++) {
                mHander.add(s,strings.get(i));
            }
        }
        Map<String, Object> baseParms = getHttpClien().getParms();
        for (String key : baseParms.keySet()) {
            mPamer.put(key,baseParms.get(key));
        }
    }

    public void builder(JCallBack callback) {
        Call call = getHttpClien().getHttpClien().newCall(build(this.mPamer, mHander));
        callback.onBefore(call);
        HttpClientBuilder.getInstance().checkCall(call);
        call.enqueue(new JsonCallbackHelper(callback));
        HttpClientBuilder.getInstance().checkCall(call);
    }

    private HttpClientBuilder getHttpClien() {
        return HttpClientBuilder.getInstance();
    }

    public abstract T setTag(Object tag);


    public abstract T url(String url);

    public abstract T param(Object key, Object value);
    public abstract T rmParam(Object key);
    public abstract T rmHeader(String key);

    public abstract T addHeader(String key, Object value);


    public void builder(String path, OnDownLoadListener onDownLoadListener) {
        Call call = getHttpClien().getHttpClien().newCall(build(this.mPamer, mHander));
        onDownLoadListener.onBefore(call);
        HttpClientBuilder.getInstance().checkCall(call);
        call.enqueue(new DownloadHelper(onDownLoadListener, path));
        HttpClientBuilder.getInstance().checkCall(call);
    }

    private Request build(Map<Object, Object> mPamer, Headers.Builder mHander) {

       return buildData(mPamer, mHander, cacheControl, tag);
    }

    public void builder(BitmapCallBack bitmapCallBack) {
        Call call = getHttpClien().getHttpClien().newCall(build(this.mPamer, mHander));
        bitmapCallBack.onBefore(call);
        HttpClientBuilder.getInstance().checkCall(call);
        call.enqueue(new OnBitmapHelper(bitmapCallBack));
        HttpClientBuilder.getInstance().checkCall(call);


    }

    public void builder(Callback callback) {
        Call call = getHttpClien().getHttpClien().newCall(build(this.mPamer, mHander));
        HttpClientBuilder.getInstance().checkCall(call);
        call.enqueue(callback);
        HttpClientBuilder.getInstance().checkCall(call);
    }

    protected abstract Request buildData(Map<Object, Object> mPamer, Headers.Builder mHander, CacheControl control, Object tag);


}
