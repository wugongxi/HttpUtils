package com.wgx.net.request;


import java.io.File;
import java.util.Map;

import okhttp3.CacheControl;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.ByteString;

/**
 * Created by WU on 2017/6/13.
 */

public class PostRequest extends BaseRequest<PostRequest> {

    public static PostRequest getInstance() {
        return new PostRequest();
    }

    public PostRequest() {
        super();
    }

    public PostRequest setTag(Object tag) {
        this.tag = tag;
        return this;
    }


    public PostRequest url(String url) {
        this.url = url;
        return this;
    }

    public PostRequest param(Object key, Object value) {
        this.mPamer.put(key, value);
        return this;
    }

    @Override
    public PostRequest rmParam(Object key) {
        mPamer.remove(key);
        return this;
    }

    @Override
    public PostRequest rmHeader(String key) {
        mHander.removeAll(key);
        return null;
    }

    public PostRequest addHeader(String key, Object value) {
        this.mHander.add(key, String.valueOf(value));
        return this;
    }


    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    public PostRequest postJson(String json) {
        post(MEDIA_TYPE_JSON,json,-1,0);
        return this;
    }
    private static final MediaType MEDIA_TYPE_FILE = MediaType.parse("Content-Disposition: form-data; name=\"file\"");
    public PostRequest postFile(File file) {
        post(MEDIA_TYPE_FILE,file,-1,0);
        return this;
    }
    private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
    public PostRequest postString(String str) {
        post(MEDIA_TYPE_MARKDOWN,str,-1,0);
        return this;
    }

    public PostRequest post(MediaType mediaType,Object object){
        post(mediaType,object,-1,0);
        return this;
    }

    protected RequestBody mRequestBody = null;

    protected RequestBody post(MediaType mediaType, Object object, int offset, int byteCount) {
        if (object != null) {
            if (object instanceof File) {
                mRequestBody = RequestBody.create(mediaType, (File) object);
            } else if (object instanceof String) {
                mRequestBody = RequestBody.create(mediaType, (String) object);
            } else if (object instanceof ByteString) {
                mRequestBody = RequestBody.create(mediaType, (ByteString) object);
            } else if (object instanceof byte[] && offset >= 0) {
                mRequestBody = RequestBody.create(mediaType, (byte[]) object, offset, byteCount);
            } else if (object instanceof byte[]) {
                mRequestBody = RequestBody.create(mediaType, (byte[]) object);
            }else {
                mRequestBody = RequestBody.create(mediaType,String.valueOf(object));
            }
            return mRequestBody;
        }
        return null;
    }



    @Override
    protected Request buildData(Map<Object, Object> mPamer, Headers.Builder mHander, CacheControl control, Object tag) {
        if (mRequestBody == null) {
            FormBody.Builder formBody = new FormBody.Builder();
            if (mPamer != null && mPamer.size() > 0) {
                for (Map.Entry<Object, Object> item : mPamer.entrySet()
                        ) {
                    formBody.add(String.valueOf(item.getKey()), String.valueOf(item.getValue()));
                }
            }
            mRequestBody = formBody.build();
        }
        Request.Builder builder = new Request.Builder().url(url).post(mRequestBody);
        builder.cacheControl(CacheControl.FORCE_NETWORK);
        if (mHander != null) {
            builder.headers(mHander.build());
        }
        if (control!=null){
            builder.cacheControl(control);
        }
        if (tag != null) {
            builder.tag(tag);
        }
        return builder.build();
    }
}
