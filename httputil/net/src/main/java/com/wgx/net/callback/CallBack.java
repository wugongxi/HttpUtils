package com.wgx.net.callback;

import com.wgx.net.cache.CacheType;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by WU on 2017/6/16.
 */

public class CallBack<T> implements okhttp3.Callback {

    @Override
    public void onFailure(Call call, IOException e) {
//        CacheControl cacheControl = call.request().cacheControl();
//        if (cacheControl.mustRevalidate()== CacheType.NETWORK_ELSE_CACHED)
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {

    }


}
