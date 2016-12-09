package com.example.lenovo.myapplication.network;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2016/12/9.
 *
 * 网络请求类：注意GET和DELETE不能传递请求参数，因为其请求的性质所致，用户可以将参数构建到url后传递进来到Reqeust中
 *
 */

public abstract class Request<T> implements Comparator<Request<T>> {

    /*
    * Http请求方法枚举，这里我们只有GET,POST,PUT,DELETE四种
    *
    * */
    public static enum HttpMethod{

        GET("GET"),
        POST("POST"),
        PUT("PUT"),
        DELETE("DELETE");

        private String mHttpMethod = "";
        private HttpMethod(String method){
            this.mHttpMethod = method;
        }

        @Override
        public String toString() {
            return mHttpMethod;
        }
    }

    public static enum Priority{

        LOW,
        NORMAL,
        HIGH,
        IMMEDITATE
    }

    private static final String DEFAULT_PARAMS_ENCODING = "UTF-8";

    protected int mSerialNum = 0;

    //优先级默认设置为 Normal
    protected Priority mPriority = Priority.NORMAL;

    //是否取消该需求
    protected boolean isCancel = false;

    //该请求是否应该缓存
    private boolean mShouldCache = true;

    //请求listener
    protected RequestListener<T> mRequestListener;

    //请求的url
    private String mUrl = "";

    //请求的方法
    HttpMethod mHttMthod = HttpMethod.GET;

    //请求的headers
    private Map<String,String> mHeaders = new HashMap<String,String>();

    //请求参数
    private Map<String,String> mBodyParams = new HashMap<>();

    public Request(HttpMethod method,String url,RequestListener listener){
        mHttMthod = method;
        mUrl = url;
        mRequestListener = listener;
    }

    /*
    * 从原生网络请求中解析结果，子类覆写
    *
    * */
    public abstract T parseResponse();

    public static interface RequestListener<T>{

        /*
        * 请求完成的回调
        * */
        public void onComplete(int stCode,T response,String errMsg);
    }
}
