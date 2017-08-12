package com.wgx.net.intreface;



import com.wgx.net.config.HttpClientBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by WU on 2017/6/12.
 */

public class BuildData <T>{
    protected String url = "";
    protected Object tag = null;
    protected String method = HttpClientBuilder.getInstance().getDefaultMethod();
    protected Map<String,Object> mPamer= new HashMap<>();
    protected Map<Object ,Object> mHeader= new HashMap<>();
    public void builder(){
        HttpClientBuilder.getInstance().builder();
    }


}

