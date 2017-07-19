package net.request;



import net.config.HttpClientBuilder;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.Request;

/**
 * Created by WU on 2017/6/13.
 */

public class GetRequest extends BaseRequest<GetRequest>{



    protected String url = "";
    protected Object tag = null;
    protected Map<Object,Object> mPamer= new HashMap<>();
    private static Headers.Builder mHander = new Headers.Builder();





    private static GetRequest sGetRequest  =null;

    public static GetRequest getInstance() {
        if (sGetRequest==null){
            sGetRequest = new GetRequest();
        }
        return sGetRequest;
    }


    public GetRequest setTag(Object tag) {
        this.tag = tag;
        return this;
    }


    public GetRequest url(String url) {
        this.url = url;
        return this;
    }

    public GetRequest addParam(Object key, Object value) {
        this.mPamer.put(key,value);
        return this;
    }

    public GetRequest addHeader(String key, Object value) {
        this.mHander.add(key, String.valueOf(value));
        return this;
    }
/*
    public void builder(AbsCall callback){
        Call call = HttpClientBuilder.getInstance().getHttpClien().newCall(build(this.mPamer, mHander, tag));
        callback.onBefore(call);
        call.enqueue(new JsonCallback(callback));

    }

    public void builder(String path, OnDownLoadListener onDownLoadListener) {
        Call call = HttpClientBuilder.getInstance().getHttpClien().newCall(build(this.mPamer, mHander, tag));
        onDownLoadListener.onBefore(call);
        call.enqueue(new DownloadHelper(onDownLoadListener,path));
    }
*/

    @Override
    protected Request buildData(Map<Object, Object> mPamer, Headers.Builder mHander, Object tag) {
        if (mPamer!=null&&mPamer.size()>0) {
            StringBuffer sbf = new StringBuffer(url + (url.contains("/") ? "" : "/")).append("?");
            for (Map.Entry<Object, Object> item : mPamer.entrySet()
                    ) {
                sbf.append(String.valueOf(item.getKey()) + "=" + String.valueOf(item.getValue())).append("&");
            }
            url = sbf.substring(0, sbf.length() - 1);
        }
        Request.Builder builder = new Request.Builder().url(url).get();
        if (mHander!=null){
            builder.headers(mHander.build());
        }
        if (tag!=null){
            builder.tag(tag);
        }
        return builder.build();

    }

}
