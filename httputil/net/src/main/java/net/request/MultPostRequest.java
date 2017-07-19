package net.request;

import android.text.TextUtils;

import java.io.File;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by WU on 2017/6/13.
 */

public class MultPostRequest extends BaseRequest<MultPostRequest> {


    private static MultPostRequest sRequest = null;

    public static MultPostRequest getInstance() {
            return new MultPostRequest();
    }

    public MultPostRequest setTag(Object tag) {
        this.tag = tag;
        return this;
    }


    public MultPostRequest url(String url) {
        this.url = url;
        return this;
    }

    public MultPostRequest addParam(Object key, Object value) {
        this.mPamer.put(key, value);
        return this;
    }

    public MultPostRequest addHeader(String key, Object value) {
        this.mHander.add(key, String.valueOf(value));
        return this;
    }


    @Override
    protected Request buildData(Map<Object, Object> mPamer, Headers.Builder mHander, Object tag) {
        MultipartBody.Builder build = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (mPamer!=null&&mPamer.size()>0) {
            StringBuffer sbf = new StringBuffer(url + ((TextUtils.equals(url.substring(url.length()-1),"/")) ? "" : "/")).append("?");
            for (Map.Entry<Object, Object> item : mPamer.entrySet()
                    ) {
                if (item.getValue() instanceof File){
                    build.addPart(Headers.of("Content-Disposition", "form-data; name=\""+ String.valueOf(item.getKey())+"\";filename=\""+((File)item.getValue()).getName()+"\""), RequestBody.create(MediaType.parse("multipart/form-data"),(File)item.getValue()));
                }else {
                    build.addFormDataPart(String.valueOf(item.getKey()), String.valueOf(item.getValue()));
                }
            }
        }
        Request.Builder builder = new Request.Builder().url(url).post(build.build());
        if (mHander!=null){
            builder.headers(mHander.build());
        }
        if (tag!=null){
            builder.tag(tag);
        }
        return builder.build();
    }
}
