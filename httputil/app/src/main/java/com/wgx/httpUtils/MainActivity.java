package com.wgx.httpUtils;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.wgx.net.NetWorkUtil;
import com.wgx.net.callback.BitmapCallBack;
import com.wgx.net.callback.JCallBack;
import com.wgx.net.callback.OnDownLoadListener;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;

public class MainActivity extends AppCompatActivity {
    ImageView id_http;
    private TextView tv, tv_progress;
    private VideoView videoView;
    private Button close_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        tv_progress = (TextView) findViewById(R.id.tv_progress);
        close_btn = (Button) findViewById(R.id.close_btn);
        videoView = (VideoView) findViewById(R.id.videoView);
        id_http = (ImageView) findViewById(R.id.id_http);
        NetWorkUtil.init(this).addParms("bkk","bvvv").addheader("hkk","hvv").setDebug(true).builder();
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (x == 1)
                    return;
                if (videoView.isPlaying()) {
                    videoView.pause();
                } else {
                    videoView.start();
                }

            }
        });

    }

    int x = 1;

    public void Onload(View view) {

     /*           Log.e("----","点击了阿啊啊啊啊 啊");
        NetWorkUtil.get("http://www.baidu.com/").addHeader("abc","aaa").param("key1",1).builder(new JsonCallBack<String>() {
            @Override
            public void onSucceed(String s, Call call) {
                Log.e("----",s);

            }

        });
*/
        tv.setText("onClicked");
//        HttpClientBuilder.getInstance().addheader("","").addParms("","").addInterceptors(new LoggerInterceptor())
//                .addNetInterceptors(new LoggerInterceptor()).setCONNECTION_TIME_OUT(15000).setDefaultMethod(Method.get)
//                .setDebug(true).setStart("(").setEnd(")").setWRITE_TIME_OUT(15000).setREAD_TIME_OUT(15000)
//        .setsSLSocketFactory(new SSLCertificateSocketFactory(1)).setTrustManager(new X509TrustManager() {
//            @Override
//            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

//            }
//
//            @Override
//            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
//
//            }
//
//            @Override
//            public X509Certificate[] getAcceptedIssuers() {
//                return new X509Certificate[0];
//            }
//        }).setenableCache("",1).builder();
//        NetWorkUtil.getInstance().init(this);
        tv.setText("onClicked");
//        HttpClientBuilder.getInstance().getHttpClien();
        NetWorkUtil.url("http://www.angame.top/res/mv.jpg").param("jpgkey","jpgvalue").addHeader("jpg_heander", "jpg_heander-value").builder(new BitmapCallBack() {
            @Override
            public void onSucceed(Bitmap t, Call call) {
                id_http.setImageBitmap(t);
            }

            @Override
            public void onAfter(Call call) {
                super.onAfter(call);
//                Log.e("----", "--call==null --" + (call == null));

            }
        });
//        NetWorkUtil.getInstance().cancle("aaaa");
        NetWorkUtil.url("http://www.angame.top/res/bean.txt").param("beankey","beanvalue").addHeader("h-bean-key","h-bean-value").setTag("aaaa").builder(new JCallBack<String>() {
            @Override
            public void onSucceed(String s, Call call) {
                tv.setText(s);
            }

            @Override
            public void onFaild(Call call, IOException e) {
                super.onFaild(call, e);

                Log.e("----onFaild", "jinud "+(call.isCanceled())+(call.isExecuted()) +e.getMessage());
            }
        });
        NetWorkUtil.url("http://www.angame.top/res/videos.avi").param("down--a","down--v").addHeader("down-aab", "down--abc").builder(this.getCacheDir() + "/abc.mp4", new OnDownLoadListener() {
            @Override
            public void onSucceed(File file, Call call) {
                super.onSucceed(file, call);
                x = 0;
                Toast.makeText(MainActivity.this, "下载成功" + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                Uri uri = Uri.parse(MainActivity.this.getCacheDir() + "/abc.mp4");
//                videoView.setMediaController(this));
                videoView.setVideoURI(uri);
                videoView.start();
                videoView.requestFocus();
//                videoView.pause();
            }

            @Override
            public void onDFaild(String path, Call call, IOException e) {
                super.onDFaild(path, call, e);
                tv_progress.setText("----失败 " + path);
            }

            @Override
            public void onstart(final String path) {
                super.onstart(path);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        tv_progress.setText("开始下载" + path);
                    }
                });
            }

            @Override
            public void onDLoadProgress(long total, long progress, String path) {
                super.onDLoadProgress(total, progress, path);
                tv_progress.setText("进度： " + total + " / " + progress);
//                Log.e("----down", path + "jinud " + total + "--" + progress);
            }
        });
        NetWorkUtil.getInstance().cancle("aaaa");
    }

    Handler mHandler = new Handler(Looper.getMainLooper());


}
