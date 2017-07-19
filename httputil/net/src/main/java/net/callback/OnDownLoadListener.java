package net.callback;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by WU on 2017/6/17 0017.
 */

public abstract class OnDownLoadListener implements FinalCallBack {
    public void onstart(String path) {

    }

    public void onDLoadProgress(long total, long progress, String path) {

    }


    public void onDFaild(String path , Call call , IOException e) {
    }

    @Override
    public void onBefore(Call call) {

    }


    @Override
    public void onFaild(Call call, IOException e) {
    }

    @Override
    public void onAfter(Call call) {

    }

}
