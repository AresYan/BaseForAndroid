package com.yz.base.api;

import com.yz.base.http.BaseEaseHttp;
import com.yz.base.http.MyResultDownloadListener;
import com.yz.base.http.MyResultListener;
import io.reactivex.disposables.Disposable;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author :yanzheng
 * @describe ：基础模块API
 */
public abstract class APIBase {

    private static BaseEaseHttp mBaseEaseHttp;

    public static void init(BaseEaseHttp baseEaseHttp){
        mBaseEaseHttp=baseEaseHttp;
    }

    /**
     * @author :yanzheng
     * @describe ：取消
     */
    public static void cancel(Object object){
        if(object!=null&&object instanceof Disposable){
            mBaseEaseHttp.cancel((Disposable)object);
        }
    }

    public static Object get(String baseUrl, String url, Type type, MyResultListener listener){
        return get(baseUrl,url,null,type,listener);
    }

    public static Object get(String baseUrl, String url, Map<String,String> params, Type type, MyResultListener listener){
        return mBaseEaseHttp.request(BaseEaseHttp.GET,baseUrl,url,params,type,listener);
    }

    public static Object post(String baseUrl, String url, Object params, Type type, MyResultListener listener){
        return mBaseEaseHttp.request(BaseEaseHttp.POST,baseUrl,url,params,type,listener);
    }

    public static Object post(String baseUrl, String url, Map<String,String> params, Type type, MyResultListener listener){
        return mBaseEaseHttp.request(BaseEaseHttp.POST,baseUrl,url,params,type,listener);
    }

    public static Object put(String baseUrl, String url, Object params, Type type, MyResultListener listener){
        return mBaseEaseHttp.request(BaseEaseHttp.PUT,baseUrl,url,params,type,listener);
    }

    public static Object put(String baseUrl, String url, Map<String,String> params, Type type, MyResultListener listener){
        return mBaseEaseHttp.request(BaseEaseHttp.PUT,baseUrl,url,params,type,listener);
    }

    public static Object del(String baseUrl, String url, Object params, Type type, MyResultListener listener){
        return mBaseEaseHttp.request(BaseEaseHttp.DEL,baseUrl,url,params,type,listener);
    }

    public static Object del(String baseUrl, String url, Map<String,String> params, Type type, MyResultListener listener){
        return mBaseEaseHttp.request(BaseEaseHttp.DEL,baseUrl,url,params,type,listener);
    }

    public static Object postFile(String baseUrl, String url, File file,Type type, MyResultListener listener){
        return mBaseEaseHttp.postFile(baseUrl,url,file,type,listener);
    }

    public static Object postFiles(String baseUrl, String url, Map<String, File> fileParams,Type type, MyResultListener listener){
        return mBaseEaseHttp.postFiles(baseUrl,url,fileParams,type,listener);
    }

    public static Object postFiles(String baseUrl, String url, Map<String,String> params, Map<String, File> fileParams,Type type, MyResultListener listener){
        return mBaseEaseHttp.postFiles(baseUrl,url,params,fileParams,type,listener);
    }
    
    public static Object download(String baseUrl, String url, String dir, String name, MyResultDownloadListener listener){
        return mBaseEaseHttp.download(baseUrl,url,dir,name, listener);
    }
}
