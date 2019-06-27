package com.yz.base.http;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.google.gson.Gson;
import com.yz.base.R;
import com.yz.base.api.APIBase;
import com.yz.base.entity.BaseEntity;
import com.yz.base.utils.MyLogger;
import com.yz.base.utils.MyMainHandler;
import com.yz.base.utils.MyStrHelper;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBack;
import com.zhouyou.http.callback.DownloadProgressCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpHeaders;
import com.zhouyou.http.request.*;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Map;


/**
 * @author :yanzheng
 * @describe ：Http网络请求封装
 */
public abstract class BaseEaseHttp {

    public static final int GET=1;
    public static final int POST=2;
    public static final int PUT=3;
    public static final int DEL=4;

    public Context mContext;
    public int mSuccess = 0;
    public int mInvalid = -1;

    public void init(Application application, int success, int invalid){
        mContext=application.getApplicationContext();
        mSuccess=success;
        mInvalid=invalid;
        EasyHttp.init(application);//默认初始化,必须调用
        EasyHttp.getInstance()
                .setConnectTimeout(5*1000)
                .setWriteTimeOut(10*1000)
                .setReadTimeOut(15*1000)
                .setRetryCount(0);
        APIBase.init(this);
    }

    public InvalidListener mInvalidListener;

    public interface InvalidListener{
        void invalid();
    }

    public void setInvalidListener(InvalidListener listener){
        mInvalidListener=listener;
    }

    public void addHeaders(HttpHeaders headers){
        EasyHttp.getInstance().addCommonHeaders(headers);
    }

    public void baseUrl(BaseRequest request, String baseUrl){
        if (request!=null&&!TextUtils.isEmpty(baseUrl)) {
            request.baseUrl(baseUrl);
        }
    }

    public <T> Disposable request(int mode, String baseUrl, String url, Object params, Type type, MyResultListener<T> listener) {
        if(params!=null){
            if(params instanceof BaseEntity){
                 return requestForJson(mode,baseUrl,url,(BaseEntity)params,type,listener);
            }
            else if(params instanceof Map){
                return requestForParams(mode,baseUrl,url,(Map)params,type,listener);
            }
        }
        return request(mode,baseUrl,url,type,listener);
    }

    public <T> Disposable request(int mode, String baseUrl, String url, Type type, MyResultListener<T> listener) {
        if(!isConnected(listener)){
            return null;
        }
        BaseRequest request = null;
        switch (mode){
            case GET:
                request = EasyHttp.get(url);
                break;
            case POST:
                request = EasyHttp.post(url);
                break;
            case PUT:
                request = EasyHttp.put(url);
                break;
            case DEL:
                request = EasyHttp.delete(url);
                break;
        }
        baseUrl(request,baseUrl);
        return execute(request,baseUrl+url,type,listener);
    }

    public <T> Disposable requestForJson(int mode, String baseUrl, String url, BaseEntity params, Type type, MyResultListener<T> listener) {
        if(!isConnected(listener)){
            return null;
        }
        BaseRequest request = null;
        switch (mode){
            case GET:
                request = EasyHttp.get(url);
                break;
            case POST:
                request = EasyHttp.post(url);
                break;
            case PUT:
                request = EasyHttp.put(url);
                break;
            case DEL:
                request = EasyHttp.delete(url);
                break;
        }
        baseUrl(request,baseUrl);
        if(params!=null && request instanceof BaseBodyRequest){
            RequestBody body= RequestBody.create(MediaType.parse("application/json; charset=utf-8"),params.toString());
            ((BaseBodyRequest)request).requestBody(body);
        }
        return execute(request,baseUrl+url,type,listener);
    }

    public <T> Disposable requestForParams(int mode, String baseUrl, String url, Map<String,String> params, Type type, MyResultListener<T> listener) {
        if(!isConnected(listener)){
            return null;
        }
        StringBuffer buffer=new StringBuffer();
        if(params!=null && ! params.isEmpty()){
            buffer.append("?");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                buffer.append(entry.getKey()).append("=").append(entry.getValue());
                buffer.append("&");
            }
            url+=buffer.toString();
        }
        BaseRequest request = null;
        switch (mode){
            case GET:
                request = EasyHttp.get(url);
                break;
            case POST:
                request = EasyHttp.post(url);
                break;
            case PUT:
                request = EasyHttp.put(url);
                break;
            case DEL:
                request = EasyHttp.delete(url);
                break;
        }
        baseUrl(request,baseUrl);
        if(request instanceof BaseBodyRequest){
            if(params!=null && ! params.isEmpty()){
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    ((BaseBodyRequest)request).params(entry.getKey(),entry.getValue());
                }
            }
        }
        return execute(request,baseUrl+url,type,listener);
    }

    public <T> Disposable postFiles(String baseUrl, String url, Map<String, File> fileParams, Type type, MyResultListener<T> listener) {
        return postFiles(baseUrl,url,null,fileParams,type,listener);
    }

    public <T> Disposable postFiles(String baseUrl, String url, Map<String,String> params, Map<String, File> fileParams, Type type, MyResultListener<T> listener) {
        if(!isConnected(listener)){
            return null;
        }
        StringBuffer buffer=new StringBuffer();
        if(params!=null && ! params.isEmpty()){
            buffer.append("?");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                buffer.append(entry.getKey()).append("=").append(entry.getValue());
                buffer.append("&");
            }
            url+=buffer.toString();
        }
        BaseRequest request = EasyHttp.post(url);
        baseUrl(request,baseUrl);
        if(request instanceof BaseBodyRequest){
            if(params!=null && ! params.isEmpty()){
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    ((BaseBodyRequest)request).params(entry.getKey(),entry.getValue());
                }
            }
            if(fileParams!=null&&!fileParams.isEmpty()){
                for (Map.Entry<String, File> entry : fileParams.entrySet()) {
                    ((BaseBodyRequest)request).params(entry.getKey(),entry.getValue(),entry.getValue().getName(),null);
                }
            }
        }
        return execute(request,baseUrl+url,type,listener);
    }

    public <T> Disposable postFile(String baseUrl, String url, File file, Type type, MyResultListener<T> listener) {
        if(!isConnected(listener)){
            return null;
        }
        BaseRequest request = EasyHttp.post(url);
        baseUrl(request,baseUrl);
        if(file!=null && request instanceof BaseBodyRequest){
            RequestBody body= RequestBody.create(null,file);
            ((BaseBodyRequest)request).requestBody(body);
        }
        return execute(request,baseUrl+url,type,listener);
    }

    public <T> Disposable execute(final BaseRequest request, final String url, final Type type, final MyResultListener<T> listener){
        CallBack callBack=new CallBack<String>() {
            @Override
            public void onStart() {
                start(listener);
            }
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(ApiException e) {
                MyLogger.e("MyEasyHttp url : "+url+" , onError : " + e.getMessage());
                finished(listener);
                failure(listener,e.getMessage());
            }
            @Override
            public void onSuccess(String result) {
                MyLogger.d("MyEasyHttp url : "+url+" , onSuccess : " + result);
                finished(listener);
                success(result,type,listener);
            }
        };
        if(request instanceof GetRequest){
            return ((GetRequest)request).execute(callBack);
        }
        else if(request instanceof PostRequest){
            return ((PostRequest)request).execute(callBack);
        }
        else if(request instanceof PutRequest){
            return ((PutRequest)request).execute(callBack);
        }
        else if(request instanceof DeleteRequest){
            return ((DeleteRequest)request).execute(callBack);
        }
        return null;
    }

    public Disposable download(final String baseUrl, final String url, final String dir, final String name, final MyResultDownloadListener listener){
        if(!isConnected(listener)){
            return null;
        }
        DownloadRequest request = EasyHttp.downLoad(url);
        baseUrl(request,baseUrl);
        request.savePath(dir);
        request.saveName(name);
        return request.execute(new DownloadProgressCallBack<String>() {
            @Override
            public void onStart() {
                start(listener);
            }
            @Override
            public void onError(ApiException e) {
                failure(listener,e.getMessage());
            }
            @Override
            public void update(long bytesRead, long contentLength, boolean done) {
                int progress = (int) (bytesRead * 100 / contentLength);
                downloading(listener,progress,done);
            }
            @Override
            public void onComplete(String path) {
            }
        });
    }

    public void cancel(Disposable disposable){
        EasyHttp.cancelSubscription(disposable);
    }

    public <T> boolean isConnected(MyResultListener<T> listener){
        if(!NetworkUtils.isConnected()){
            finished(listener);
            failure(listener, MyStrHelper.getString(mContext,R.string.yz_base_net_error));
            return false;
        }
        return true;
    }

    public abstract <T> void success(final String result, final Type type, final MyResultListener<T> listener);;

    public <T> void failure(final MyResultListener<T> listener, final String msg) {
        MyMainHandler.post(new Runnable() {
            @Override
            public void run() {
                listener.onFailure(msg);
            }
        });
    }

    public <T> void start(final MyResultListener<T> listener) {
        MyMainHandler.post(new Runnable() {
            @Override
            public void run() {
                listener.onStart();
            }
        });
    }

    public <T> void finished(final MyResultListener<T> listener) {
        MyMainHandler.post(new Runnable() {
            @Override
            public void run() {
                listener.onFinished();
            }
        });
    }

    public <T> void hasMore(final MyResultHasMoreListener listener, final boolean hasMore) {
        MyMainHandler.post(new Runnable() {
            @Override
            public void run() {
                listener.onHasMore(hasMore);
            }
        });
    }

    public <T> void downloading(final MyResultDownloadListener listener, final int progress, final boolean done) {
        MyMainHandler.post(new Runnable() {
            @Override
            public void run() {
                listener.onDownloading(progress,done);
            }
        });
    }

}
