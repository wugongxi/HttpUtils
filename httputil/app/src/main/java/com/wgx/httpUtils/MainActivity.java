package com.wgx.httpUtils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import net.NetWorkUtil;
import net.callback.AbsCall;

import okhttp3.Call;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Onload(View view) {

                Log.e("----","点击了阿啊啊啊啊 啊");
        NetWorkUtil.get("http://www.baidu.com/").addHeader("abc","aaa").addParam("key1",1).builder(new AbsCall<String>() {
            @Override
            public void onSucceed(String s, Call call) {
                Log.e("----",s);
            }

        });
        NetWorkUtil.get("http://www.angame.top/").addHeader("h1","h2").builder();
    }
}
