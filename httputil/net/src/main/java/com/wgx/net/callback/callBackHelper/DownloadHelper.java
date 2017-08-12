package com.wgx.net.callback.callBackHelper;

import android.os.Handler;
import android.util.Log;


import com.wgx.net.NetWorkUtil;
import com.wgx.net.callback.OnDownLoadListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by BM-WGX on 2017/3/15.
 */

public class DownloadHelper implements Callback {
    /**
     *
     * void onstart(String path);
     * void onDLoadProgress(long total, long progress, String path);
     * void onSucceed(File file);
     * void onDFaild(String path);
     */

    private OnDownLoadListener onDownLoadListener = null;
    private String path = null;

    public DownloadHelper(OnDownLoadListener onDownLoadListener, String path) {
        this.onDownLoadListener = onDownLoadListener;
        this.path = path;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        onDownLoadListener.onDFaild(path,call,e);
    }

    @Override
    public void onResponse(final Call call, Response response) throws IOException {
        final File file = new File(path);
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;
        Handler handler = NetWorkUtil.getInstance().getHandler();
        try {
            final long total = response.body().contentLength();
//            Log.e(TAG, "total------>" + total);
            long current = 0;
            is = response.body().byteStream();
            fos = new FileOutputStream(file);
            if (is != null && fos != null) {
                onDownLoadListener.onstart(path);
            }
            while ((len = is.read(buf)) != -1) {
                current += len;
                fos.write(buf, 0, len);
//                Log.e(TAG, "current------>" + current);
                fos.flush();
                final long finalCurrent = current;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onDownLoadListener.onDLoadProgress(total, finalCurrent, path);
                    }
                });
            }
            fos.flush();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    onDownLoadListener.onSucceed(file,call);
                }
            });
        } catch (final IOException e) {
            Log.e(TAG, e.toString());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    onDownLoadListener.onFaild(call,e);
                    onDownLoadListener.onDFaild(path,call,e);
                }
            });
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                Log.e(TAG, e.toString());
            }
        }
    }


}
