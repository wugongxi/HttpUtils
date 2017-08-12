package com.wgx.net.request;


import com.wgx.net.LogUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import okhttp3.CacheControl;
import okhttp3.Headers;
import okhttp3.Request;

/**
 * Created by WU on 2017/6/13.
 */

public class GetRequest extends BaseRequest<GetRequest>{

    public static GetRequest getInstance() {
        return new GetRequest();
    }

    public GetRequest() {
        super();
    }

    public GetRequest setTag(Object tag) {
        this.tag = tag;
        return this;
    }


    public GetRequest url(String url) {
        this.url = url;
        return this;
    }

    public GetRequest param(Object key, Object value) {
        this.mPamer.put(key,value);
        return this;
    }

    @Override
    public GetRequest rmParam(Object key) {
        mPamer.remove(key);
        return this;
    }

    @Override
    public GetRequest rmHeader(String key) {
        mHander.removeAll(key);
        return this;
    }

    public GetRequest addHeader(String key, Object value) {
        this.mHander.add(key, String.valueOf(value));
        return this;
    }
/*
    public void builder(JsonCallBack callback){
        Call call = HttpClientBuilder.getInstance().getHttpClien().newCall(build(this.mPamer, mHander, tag));
        callback.onBefore(call);
        call.enqueue(new JsonCallbackHelper(callback));

    }

    public void builder(String path, OnDownLoadListener onDownLoadListener) {
        Call call = HttpClientBuilder.getInstance().getHttpClien().newCall(build(this.mPamer, mHander, tag));
        onDownLoadListener.onBefore(call);
        call.enqueue(new DownloadHelper(onDownLoadListener,path));
    }
*/

    @Override
    protected Request buildData(Map<Object, Object> mPamer, Headers.Builder mHander, CacheControl control, Object tag) {
        if (mPamer!=null&&mPamer.size()>0) {
            StringBuffer sbf = new StringBuffer(url + (url.contains("/") ? "" : "/")).append("?");
            for (Map.Entry<Object, Object> item : mPamer.entrySet()
                    ) {
                sbf.append(String.valueOf(item.getKey()) + "=" + getEncode(String.valueOf(item.getValue()))).append("&");
            }
            url = sbf.substring(0, sbf.length() - 1);
        }
        Request.Builder builder = new Request.Builder().url(url).get();
        if (control!=null){
            builder.cacheControl(control);
        }

        if (mHander!=null){
            builder.headers(mHander.build());
        }
        if (tag!=null){
            builder.tag(tag);
        }
        return builder.build();

    }

    private String getEncode(String s) {
        if (s != null) {
            try {
                return URLEncoder.encode(s, "utf-8");
            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
            }
        }
        return s;
    }

}
