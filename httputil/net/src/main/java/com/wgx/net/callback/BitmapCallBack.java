package com.wgx.net.callback;

import android.graphics.Bitmap;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by WU on 2017/6/17 0017.
 */

public abstract class BitmapCallBack  extends JCallBack<Bitmap>{
    public void onBefore(Call call) {    }
    public abstract void onSucceed(Bitmap t, Call call);

    public void onFaild(Call call, IOException e) {

    }

    public void onAfter(Call call) {

    }


}
