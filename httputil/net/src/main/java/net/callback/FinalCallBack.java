package net.callback;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by WU on 2017/6/16.
 */

public interface FinalCallBack <T>{
    /**
     * execute before
     * @param call
     */
    void onBefore(Call call);//UI


    /**
     * UI Thread
     * @param t parse Bean form natData
     * @param call request Data
     */
    void onSucceed(T t, Call call);

    /**
     *  backGround Thread
     * @param call request Data
     * @param e exception
     */
    void onFaild(Call call, IOException e);
    /**
     *  backGround Thread
     * @param call request Data
     * @serialData  Absolute execute
     */
    void onAfter(Call call);//

}


