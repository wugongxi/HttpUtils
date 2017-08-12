package com.wgx.net.cache;

import android.support.annotation.IntDef;

/**
 * Created by dzc on 15/12/5.
 */
public interface CacheType {
    int ONLY_NETWORK = 0;
    int ONLY_CACHED = 1;
    int CACHED_ELSE_NETWORK =2;
    int NETWORK_ELSE_CACHED = 3;


}
