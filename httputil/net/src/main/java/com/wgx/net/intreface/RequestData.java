package com.wgx.net.intreface;



import com.wgx.net.config.HttpClientBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by WU on 2017/6/12.
 */

public class RequestData {
    HashMap<Object,Object> mParms = new HashMap<>();
    HashMap<Object,Object> mHeader= new HashMap<>();

    private List<Interceptor> mInterceptors = new ArrayList<>();
    private List<Interceptor> mNetInterceptors =  new ArrayList<>();

    private Object tag ;
    private long READ_TIME_OUT = 15;
    private long CONNECTION_TIME_OUT = 15;
    private long WRITE_TIME_OUT = 15;

    private String url;
    private String method = "GET";

    Response builder() {

        Request build = new Request.Builder().url(url).tag(tag).build();
        try {
           return HttpClientBuilder.getInstance().getNewHttpClien().build().newCall(build).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    void builder(Callback callback) {
//        Request build = new Request.Builder().build();
//        HttpClientBuilder.getInstance().getHttpClien().newCall(build).enqueue(callback);
    }
}
