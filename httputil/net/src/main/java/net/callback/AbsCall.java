package net.callback;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by WU on 2017/6/17 0017.
 */

public abstract class AbsCall<T>  implements FinalCallBack<T>{
    public void onBefore(Call call) {    }
    public abstract void onSucceed(T t, Call call);

    public void onFaild(Call call, IOException e) {

    }

    public void onAfter(Call call) {

    }


}
