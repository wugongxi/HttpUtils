package com.wgx.net;

import android.util.Log;

/**
 * Created by Administrator on 2017/8/11 0011.
 */

public class LogUtil {
    public static void show(String msg){
        Log.e("httputil--",msg!=null?msg:"");
    }
}
