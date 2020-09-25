package com.wgx.net.config;


import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.wgx.net.cache.CacheType;
import com.wgx.net.intercept.LoggerInterceptor;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Dispatcher;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;

/**
 * Created by BM-WGX on 2017/2/16.
 *
 * @serial <p>
 * HttpClientBuilder.getInstance()
 * getHttpClienBuilder()
 * OkHttpClient.Builder mBuilder;
 */

public class HttpClientBuilder {
    private Map<String, List<String>> mheader = null;
    private List<Interceptor> mInterceptors = null;
    private List<Interceptor> mNetInterceptors = null;
    private Map<String, Object> mParms = null;
    private String start = "{";
    private String end = "}";
    private Application app;
    private SSLSocketFactory sSLSocketFactory;
    private X509TrustManager trustManager;

    private long READ_TIME_OUT = 15 * 1000;
    private long CONNECTION_TIME_OUT = 15 * 1000;
    private long WRITE_TIME_OUT = 15 * 1000;
    private static HttpClientBuilder sBuilder = null;
    private File cachedir = null;
    private int cacheSize = 10 * 1024 * 1024;
    private boolean isDebug = false;
    private String defaultMethod = Method.get;

    private boolean DEBUG = true;
    private OkHttpClient client = null;
    private Gson gson;
    private MediaType mediaType = MultipartBody.FORM;

    private int maxCachedSize = 5 * 1024 * 1024;
    private File cachedDir;
    private Context context;
    private List<Interceptor> networkInterceptors;
    private List<Interceptor> interceptors;
    private int cacheType = CacheType.NETWORK_ELSE_CACHED;
    private boolean isGzip = false;
    private long timeOut = 5000;
    private boolean debug = false;


    public HttpClientBuilder(Map<String, List<String>> mheader, List<Interceptor> interceptors, Map<String, Object> mParms, long READ_TIME_OUT, long CONNECTION_TIME_OUT, long WRITE_TIME_OUT) {
        this.mheader = mheader;
        this.mInterceptors = interceptors;
        this.mParms = mParms;
        this.READ_TIME_OUT = READ_TIME_OUT;
        this.CONNECTION_TIME_OUT = CONNECTION_TIME_OUT;
        this.WRITE_TIME_OUT = WRITE_TIME_OUT;
    }

    public Cache provideCache() {
        return new Cache(app.getCacheDir(), 10240 * 1024);
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public HttpClientBuilder setStart(String start) {
        this.start = start;
        return this;
    }

    public HttpClientBuilder setEnd(String end) {
        this.end = end;
        return this;
    }


    public static HttpClientBuilder init(Context context) {
        if (sBuilder == null) {
            synchronized (HttpClientBuilder.class) {
                if (sBuilder == null) {
                    sBuilder = new HttpClientBuilder(context);
                }
            }
        }
        return sBuilder;
    }


    public static HttpClientBuilder getInstance() {
        return sBuilder;
    }

    public HttpClientBuilder setsSLSocketFactory(SSLSocketFactory sSLSocketFactory) {
        this.sSLSocketFactory = sSLSocketFactory;
        return this;
    }

    public HttpClientBuilder setTrustManager(X509TrustManager trustManager) {
        this.trustManager = trustManager;
        return this;
    }

    public OkHttpClient getHttpClien() {
        if (client == null) {
            client = getHttpClienBuilder().build();
        }
        return client;
    }

    public OkHttpClient setHttpClien(OkHttpClient okHttpClient) {
        client = getHttpClienBuilder(okHttpClient).build();
        return client;
    }

    public OkHttpClient.Builder getNewHttpClien() {
        return new OkHttpClient.Builder();
    }

    private OkHttpClient.Builder getHttpClienBuilder() {
        return new OkHttpClient.Builder();
    }

    private OkHttpClient.Builder getHttpClienBuilder(OkHttpClient client) {
        return client != null ? client.newBuilder() : new OkHttpClient.Builder();
    }

    /**
     * set default request type @{@link Method}
     *
     * @param method default Method.get;
     * @return
     */
    public HttpClientBuilder setDefaultMethod(String method) {
        defaultMethod = method;
        return this;
    }


    public HttpClientBuilder(Context context) {
        this.context = context;
        mheader = new HashMap<>();
        mCancels = new ArrayList<>();
        mInterceptors = new ArrayList<>();
        mNetInterceptors = new ArrayList<>();
        mParms = new HashMap<>();
    }

    public Map<String, List<String>> getheader() {
        return mheader;
    }

    public HttpClientBuilder addheader(String k, String v) {
        List<String> vs = mheader.get(k);
        if (vs == null) {
            vs = new ArrayList<>();
        }
        vs.add(v);
        mheader.put(k, vs);
        return this;
    }

    public List<Interceptor> getInterceptors() {
        return mInterceptors;
    }

    public HttpClientBuilder addInterceptors(Interceptor interceptors) {
        if (!mInterceptors.contains(interceptors)) {
            mInterceptors.add(interceptors);
        }
        return this;
    }

    public HttpClientBuilder addNetInterceptors(Interceptor interceptors) {
        if (!mNetInterceptors.contains(interceptors)) {
            mNetInterceptors.add(interceptors);
        }
        return this;
    }


    public HttpClientBuilder setREAD_TIME_OUT(long READ_TIME_OUT) {
        this.READ_TIME_OUT = READ_TIME_OUT;
        return this;
    }


    public HttpClientBuilder setCONNECTION_TIME_OUT(long CONNECTION_TIME_OUT) {
        this.CONNECTION_TIME_OUT = CONNECTION_TIME_OUT;
        return this;
    }

    public HttpClientBuilder setWRITE_TIME_OUT(long WRITE_TIME_OUT) {
        this.WRITE_TIME_OUT = WRITE_TIME_OUT;
        return this;
    }

    public Map<String, Object> getParms() {
        return mParms;
    }

    public HttpClientBuilder addParms(String k, String v) {
        mParms.put(k, v);
        return this;
    }

    public File getCachedir() {
        return cachedir;
    }

    public HttpClientBuilder setCachedir(File cachedir, int cacheSize) {
        this.cachedir = cachedir;
        this.cacheSize = cacheSize;
        return this;
    }

    public HttpClientBuilder setenableCache(String cachedir, int cacheSize) {
        this.cacheSize = cacheSize;
        return this;
    }

    public int getCacheSize() {
        return cacheSize;
    }

    public List<Interceptor> getNetInterceptors() {
        return mNetInterceptors;
    }

    public void builder() {
        OkHttpClient.Builder httpClienBuilder = getHttpClienBuilder();
        for (Interceptor i : mInterceptors
        ) {
            httpClienBuilder.addInterceptor(i);
        }
        if (isDebug) {
            httpClienBuilder.addNetworkInterceptor(new LoggerInterceptor());
        }
        for (Interceptor i : mNetInterceptors
        ) {
            httpClienBuilder.addInterceptor(i);
        }
        httpClienBuilder
                .readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS)
                .writeTimeout(WRITE_TIME_OUT, TimeUnit.MILLISECONDS)
                .connectTimeout(CONNECTION_TIME_OUT, TimeUnit.MILLISECONDS)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                }).followRedirects(true);
        if (cachedir != null) {
            httpClienBuilder.cache(new Cache(cachedir, cacheSize));
        }

        httpClienBuilder.cookieJar(new CookieJar() {
            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {

            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                return new ArrayList<>();
            }
        });
        if (sSLSocketFactory != null && trustManager != null) {
            httpClienBuilder.sslSocketFactory(sSLSocketFactory, trustManager);
        }
        client = httpClienBuilder.build();
    }

    public HttpClientBuilder setDebug(boolean isdebug) {
        this.isDebug = isdebug;
        return this;
    }

    ;

    public boolean isDebug() {
        return isDebug;
    }

    public String getDefaultMethod() {
        return defaultMethod;
    }


    private List<Object> mCancels;

    public void cancelCall(Object tag) {
        Log.e("---cancelCall -------", mCancels.size() + "--" + mCancels.toString());
        if (!mCancels.contains(tag)) {
            mCancels.add(tag);
        }
    }

    public void checkCall(Call call) {
        Log.e("---checkCall -------", mCancels.size() + "--" + mCancels.toString());
        if (mCancels.contains(call.request().tag())) {
            call.cancel();
        }
        if (client.dispatcher().queuedCallsCount() < 1) {
            mCancels.clear();
        }
    }
}