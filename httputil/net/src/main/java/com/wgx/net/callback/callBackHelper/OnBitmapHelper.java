package com.wgx.net.callback.callBackHelper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.wgx.net.NetWorkUtil;
import com.wgx.net.callback.BitmapCallBack;
import com.wgx.net.callback.CallBack;
import com.wgx.net.callback.JCallBack;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/8/4 0004.
 */


public class OnBitmapHelper extends CallBack {
    private BitmapCallBack bitmapCallBack;

    public void onFailure(Call call, IOException e) {
        super.onFailure(call,e);
    }

    public OnBitmapHelper(BitmapCallBack bitmapCallBack) {
        this.bitmapCallBack = bitmapCallBack;
    }


    @Override
    public void onResponse(final Call call, Response response) throws IOException {

        byte[] bytes = this.getBytes(response.body().byteStream());
        Log.e("00-------","---- calll");
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        options.inJustDecodeBounds = false;
        double oh = (double)(options.outHeight / 8192);
        double ow = (double)(options.outWidth / 8192);
        int x = 1;

        double s;
        for(s = oh > ow?oh:ow; s >= 1.0D; s /= 2.0D) {
            x *= 2;
        }

        int i = (new BigDecimal(s)).intValue();
        Log.e("---bitmap","----"+i);
        options.inSampleSize = x;
        final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        NetWorkUtil.getInstance().getHandler().post(new Runnable() {
            public void run() {
                OnBitmapHelper.this.bitmapCallBack.onSucceed(bitmap,call);
            }
        });
    }

    private byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int len1;
        while((len1 = is.read(b, 0, 1024)) != -1) {
            baos.write(b, 0, len1);
            Log.e("----bitmap--","--"+len1);
        }

        baos.flush();
        byte[] bytes = baos.toByteArray();
        return bytes;
    }
}