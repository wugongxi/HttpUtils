package com.wgx.net.callback.callBackHelper;

/**
 * Created by WU on 2017/6/16.
 */


import android.os.Handler;

import com.google.gson.Gson;

import com.wgx.net.NetWorkUtil;
import com.wgx.net.callback.CallBack;
import com.wgx.net.callback.JCallBack;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by wangjitao on 15/10/16.
 */

public class JsonCallbackHelper<T> extends CallBack<T> {
    JCallBack<T> callback;

    public JsonCallbackHelper(JCallBack<T> JCallBack) {
        this.callback = JCallBack;
    }


    Handler handler = NetWorkUtil.getInstance().getHandler();
    Gson gson = new Gson();
    private String string = null;

    /**
     * 解析json 结果
     *
     * @param response
     * @return
     * @throws IOException
     */
    protected T convertSuccess(Response response) throws IOException {
        string = response.body().string();
        T data = parseData();
        response.close();
        return data;
    }

    private T parseData() {
        Type type = getType();
        if (type==String.class){
            return (T) string;
        }
        return gson.fromJson(string, type);
    }

    private Type getType() {
        Type genType = callback.getClass().getGenericSuperclass();
        if (genType instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        return params[0];
    }

    @Override
    public void onFailure(Call call, IOException e) {
        callback.onFaild(call, e);
        callback.onAfter(call);
    }


    @Override
    public void onResponse(final Call call, Response response) throws IOException {
        try {
            final T t = convertSuccess(response);
            if (t != null) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSucceed(t, call);
                    }
                });
            } else {
                callback.onFaild(call, new IOException("type is null"));
            }
        } catch (final IOException e) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    callback.onFaild(call, e);
                }
            });
//            e.printStackTrace();
        } finally {
            handler.post(new Runnable() {
                @Override
                public void run() {

                    callback.onAfter(call);
                }
            });
        }
    }

}