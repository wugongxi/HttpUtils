package com.wgx.net.callback;


import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;

/**
 * Created by WU on 2017/6/16.
 */

public abstract class JCallBack<T> {
    /**
     * execute before
     *
     * @param call
     */
    public void onBefore(Call call)//UI
    {

    }


    /**
     * UI Thread
     * @param t parse Bean form natData
     * @param call request Data
     */
    public abstract void onSucceed(T t, Call call);

    /**
     * backGround Thread
     *
     * @param call request Data
     * @param e    exception
     */
    public void onFaild(Call call, IOException e) {

    }

    /**
     * backGround Thread
     *
     * @param call request Data
     * @serialData Absolute execute
     */
    public void onAfter(Call call)//
    {

    }
    Type getType(){
        Type type = ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        if(type instanceof Class){
            return type;
        }else{
            return new TypeToken<T>(){}.getType();
        }
    }

}


