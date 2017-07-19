package net.request;


import net.callback.AbsCall;
import net.callback.DownloadHelper;
import net.callback.JsonCallback;
import net.callback.OnDownLoadListener;
import net.config.HttpClientBuilder;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by WU on 2017/6/17 0017.
 */

public abstract class BaseRequest <T extends BaseRequest> {


    protected String url = "";
    protected Object tag = null;
    public String method = HttpClientBuilder.getInstance().getDefaultMethod();
    protected Map<Object, Object> mPamer = new HashMap<>();
    protected static Headers.Builder mHander = new Headers.Builder();


    public void builder(AbsCall callback) {
        Call call = getHttpClien().newCall(buildData(this.mPamer, mHander, tag));
        callback.onBefore(call);
        call.enqueue(new JsonCallback(callback));
//        HttpClientBuilder.getInstance().ModifyCancels(tag, call);
    }

    private OkHttpClient getHttpClien() {
        return HttpClientBuilder.getInstance().getHttpClien();
    }

    public abstract T setTag(Object tag) ;


    public abstract T url(String url);

    public abstract T addParam(Object key, Object value) ;

    public abstract T addHeader(String key, Object value) ;


    public void builder(String path, OnDownLoadListener onDownLoadListener) {
        Call call = getHttpClien().newCall(buildData(this.mPamer, mHander, tag));
        onDownLoadListener.onBefore(call);
        call.enqueue(new DownloadHelper(onDownLoadListener, path));
        HttpClientBuilder.getInstance().ModifyCancels(tag, call);

    }
    public void builder(Callback callback) {
        Call call = getHttpClien().newCall(buildData(this.mPamer, mHander, tag));
        call.enqueue(callback);
        HttpClientBuilder.getInstance().ModifyCancels(tag, call);
    }

    protected abstract Request buildData(Map<Object, Object> mPamer, Headers.Builder mHander, Object tag);






}
