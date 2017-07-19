package net.config;

/**
 * Created by BM-WGX on 2017/2/5.
 */

import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import okio.BufferedSource;


public class LoggerInterceptor implements Interceptor {

    public static final String TAG = "NetWorkLogger";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        printRequestMessage(request);
        Response response = chain.proceed(request);
        printResponseMessage(response);
        return response;
    }
    /**
     *      *
     * @param request
     */
    private void printRequestMessage(Request request) {
        if (request == null) {
            return;
        }
        Log.i(TAG, "Url   : " + request.url().url().toString());
        Log.i(TAG, "Method: " + request.method());
        Log.i(TAG, "Heads : " + request.headers());
        RequestBody requestBody = request.body();
        if (requestBody == null) {
            return;
        }
       /* try {
            final Buffer bufferedSink = new Buffer();
            requestBody.writeTo(bufferedSink);
            Charset charset = requestBody.contentType().charset();
            charset = charset == null ? Charset.forName("utf-8") : charset;
            Log.i(TAG, "Params: " + bufferedSink.readString(charset));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
    /**
     *
     *
     * @param response
     */
    private void printResponseMessage(Response response) {
        if (response == null || !response.isSuccessful()) {
            return;
        }
       ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();
        BufferedSource source = responseBody.source();
        try {
            source.request(Long.MAX_VALUE); // Buffer the entire body.
        } catch (IOException e) {
//            e.printStackTrace();
        }
        okio.Buffer buffer = source.buffer();
        Charset charset = Util.UTF_8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null&&contentType.charset()!=null) {
            charset = contentType.charset();
        }
        if (contentLength != 0) {
            String result = buffer.clone()
                    .readString(charset)
                    ;
            Log.i(TAG, "Response: " + result);
        }
    }
}