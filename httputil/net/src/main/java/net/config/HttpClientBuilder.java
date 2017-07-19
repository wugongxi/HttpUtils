package net.config;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * Created by BM-WGX on 2017/2/16.
 *
 * @serial
 * <p>
 * HttpClientBuilder.getInstance()
 * getHttpClienBuilder()
 * OkHttpClient.Builder mBuilder;
 */

public class HttpClientBuilder {
    private Map<String, List<String>> mheader = null;
    private List<Interceptor> mInterceptors = null;
    private List<Interceptor> mNetInterceptors = null;
    private Map<Object, Object> mParms = null;
    private String start = "{";
    private String end = "}";
    private SSLSocketFactory sSLSocketFactory;
    private X509TrustManager trustManager;

    private long READ_TIME_OUT = 15*1000;
    private long CONNECTION_TIME_OUT =  15*1000;
    private long WRITE_TIME_OUT =  15*1000;
    private OkHttpClient.Builder mBuilder;
    private static HttpClientBuilder sBuilder;
    private File cachedir = null;
    private int cacheSize = 10 * 1024 * 1024;
    private boolean isDebug = false;
    private String defaultMethod = Method.get;

    public HttpClientBuilder(Map<String, List<String>> mheader, List<Interceptor> interceptors, Map<Object, Object> mParms, long READ_TIME_OUT, long CONNECTION_TIME_OUT, long WRITE_TIME_OUT) {
        this.mheader = mheader;
        this.mInterceptors = interceptors;
        this.mParms = mParms;
        this.READ_TIME_OUT = READ_TIME_OUT;
        this.CONNECTION_TIME_OUT = CONNECTION_TIME_OUT;
        this.WRITE_TIME_OUT = WRITE_TIME_OUT;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public static HttpClientBuilder getInstance() {
        if (sBuilder == null) {
            synchronized (HttpClientBuilder.class) {
                if (sBuilder == null) {
                    sBuilder = new HttpClientBuilder();
                }
            }
        }
        return sBuilder;
    }

    public void setsSLSocketFactory(SSLSocketFactory sSLSocketFactory) {
        this.sSLSocketFactory = sSLSocketFactory;
    }

    public void setTrustManager(X509TrustManager trustManager) {
        this.trustManager = trustManager;
    }

    public OkHttpClient getHttpClien() {
        if (mBuilder == null) {
            getHttpClienBuilder();
        }
        return mBuilder.build().newBuilder().build();
    }
    public OkHttpClient.Builder getNewHttpClien() {
        return new OkHttpClient.Builder();
    }

    private OkHttpClient.Builder getHttpClienBuilder() {
        if (mBuilder == null) {
            synchronized (HttpClientBuilder.class) {
                if (mBuilder == null) {
                    mBuilder = new OkHttpClient.Builder();
                }
            }

        }
        return mBuilder;
    }

    /**
     * set default request type @{@link Method}
     * @param method default Method.get;
     * @return
     */
    public HttpClientBuilder setDefaultMethod(String method){
        defaultMethod = method;
        return this;
    }


    public HttpClientBuilder() {
        mheader = new HashMap<>();
        mCancels = new HashMap<>();
        mInterceptors = new ArrayList<>();
        mNetInterceptors = new ArrayList<>();
        mParms = new HashMap<>();
        getHttpClienBuilder();
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

    public Map<Object, Object> getParms() {
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

    public int getCacheSize() {
        return cacheSize;
    }

    public List<Interceptor> getNetInterceptors() {
        return mNetInterceptors;
    }

    public void builder() {
        for (Interceptor i : mInterceptors
                ) {
            getHttpClienBuilder().addInterceptor(i);
        }
        for (Interceptor i : mNetInterceptors
                ) {
            getHttpClienBuilder().addInterceptor(i);
        }
        getHttpClienBuilder()
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
            getHttpClienBuilder().cache(new Cache(cachedir, cacheSize));
        }

        getHttpClienBuilder().cookieJar(new CookieJar() {
            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {

            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                return new ArrayList<>();
            }
        });
        if (sSLSocketFactory != null && trustManager != null) {
            mBuilder.sslSocketFactory(sSLSocketFactory, trustManager);
        }

    }
    public HttpClientBuilder setDebug(boolean isdebug){
        this.isDebug = isdebug;
        return this;
    };

    public boolean isDebug() {
        return isDebug;
    }

    public String getDefaultMethod() {
        return defaultMethod;
    }


    HashMap<Object, Integer> mCancels;

    public synchronized void ModifyCancels(Object o, Call call) {
        if (call==null) {
            cancelCall(o);
        } else {
            removeCall(o,call);
        }
    }

    private void cancelCall(Object tag) {
        Integer integer = mCancels.get(tag);
        mCancels.put(tag, integer != null ? integer++ : 1);
    }

    private void removeCall(Object tag, Call call) {
        Integer integer = mCancels.get(tag);
        if (integer != null) {
            if (integer >= 1) {
                mCancels.put(tag, integer--);
                return;
            }
            mCancels.remove(tag);
        }else {
            call.cancel();
        }
    }
}