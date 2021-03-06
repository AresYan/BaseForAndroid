package com.yz.base.http;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.google.gson.Gson;
import com.yz.base.R;
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
    public String mediaType = "application/json; charset=utf-8";

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    private boolean isPostParamsUrl=false;

    public boolean isPostParamsUrl() {
        return isPostParamsUrl;
    }

    public void setPostParamsUrl(boolean postParamsUrl) {
        isPostParamsUrl = postParamsUrl;
    }

    public void init(Application application){
        init(application,mSuccess,mInvalid);
    }

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
        if(!isConnected(listener)){
            return null;
        }
        StringBuffer buffer=new StringBuffer();
        if(params!=null && params instanceof Map && ! ((Map)params).isEmpty()){
            for (Object key : ((Map)params).keySet()) {
                buffer.append(key).append("=").append(((Map)params).get(key));
                buffer.append("&");
            }
        }
        if(isPostParamsUrl){
            url+="?"+buffer.toString();
        }
        BaseRequest request = null;
        String modeStr="";
        switch (mode){
            case GET:
                modeStr="GET";
                url+="?"+buffer.toString();
                request = EasyHttp.get(url);
                break;
            case POST:
                modeStr="POST";
                request = EasyHttp.post(url);
                break;
            case PUT:
                modeStr="PUT";
                request = EasyHttp.put(url);
                break;
            case DEL:
                modeStr="DEL";
                request = EasyHttp.delete(url);
                break;
        }
        baseUrl(request,baseUrl);
        String content="";
        if(request instanceof BaseBodyRequest){
            if(!TextUtils.isEmpty(mediaType)&&mediaType.startsWith("application/json")&&params!=null){
                content=new Gson().toJson(params);
                RequestBody body = RequestBody.create(MediaType.parse(mediaType),content);
                ((BaseBodyRequest)request).requestBody(body);
            }
            if(!TextUtils.isEmpty(mediaType)&&mediaType.startsWith("application/x-www-form-urlencoded")&&params!=null && params instanceof Map && ! ((Map)params).isEmpty()){
                content=buffer.toString();
                RequestBody body = RequestBody.create(MediaType.parse(mediaType),buffer.toString());
                ((BaseBodyRequest)request).requestBody(body);
            }
        }
        return execute(request,modeStr,baseUrl+url,content,type,listener);
    }

    public <T> Disposable request(int mode, String baseUrl, String url, Type type, MyResultListener<T> listener) {
        return request(mode,baseUrl,url,null,type,listener);
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
        String content="";
        if(request instanceof BaseBodyRequest){
            if(params!=null && ! params.isEmpty()){
                content+=params.toString();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    ((BaseBodyRequest)request).params(entry.getKey(),entry.getValue());
                }
            }
            if(fileParams!=null&&!fileParams.isEmpty()){
                content+=fileParams.toString();
                for (Map.Entry<String, File> entry : fileParams.entrySet()) {
                    ((BaseBodyRequest)request).params(entry.getKey(),entry.getValue(),entry.getValue().getName(),null);
                }
            }
        }
        return execute(request,"POST",baseUrl+url,content,type,listener);
    }

    public <T> Disposable postFile(String baseUrl, String url, File file, Type type, MyResultListener<T> listener) {
        if(!isConnected(listener)){
            return null;
        }
        BaseRequest request = EasyHttp.post(url);
        baseUrl(request,baseUrl);
        String content="";
        if(file!=null && request instanceof BaseBodyRequest){
            content=file.getAbsolutePath();
            RequestBody body= RequestBody.create(null,file);
            ((BaseBodyRequest)request).requestBody(body);
        }
        return execute(request,"POST",baseUrl+url,content,type,listener);
    }

    public <T> Disposable execute(BaseRequest request, String mode, String url, String params, Type type, MyResultListener<T> listener){
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
                MyLogger.e("EasyHttp "+mode+" , url : "+url+" , Params : "+params+" , onError : " + e.getMessage());
                failure(listener,e.getMessage());
            }
            @Override
            public void onSuccess(String result) {
                MyLogger.d("EasyHttp "+mode+" , url : "+url+" , Params : "+params+" , onSuccess : " + result);
                result(result,type,listener);
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

    public Disposable download(String baseUrl, String url, String dir, String name, MyResultProgressListener listener){
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
                progress(listener,progress);
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
            failure(listener, MyStrHelper.getString(mContext,R.string.yz_base_net_error));
            return false;
        }
        return true;
    }

    public abstract <T> void result(String result, Type type, MyResultListener<T> listener);;

    public <T> void failure(MyResultListener<T> listener, final String msg) {
        MyMainHandler.post(new Runnable() {
            @Override
            public void run() {
                listener.onFailure(msg);
                listener.onFinished();
                if(listener instanceof MyResultHasMoreListener){
                    ((MyResultHasMoreListener)listener).onHasMore(false);
                }
            }
        });
    }

    public <T> void success(MyResultListener<T> listener, T t, boolean hasMore) {
        MyMainHandler.post(new Runnable() {
            @Override
            public void run() {
                listener.onSuccess(t);
                listener.onFinished();
                if(listener instanceof MyResultHasMoreListener){
                    ((MyResultHasMoreListener)listener).onHasMore(hasMore);
                }
            }
        });
    }

    public <T> void start(MyResultListener<T> listener) {
        MyMainHandler.post(new Runnable() {
            @Override
            public void run() {
                listener.onStart();
            }
        });
    }

    public <T> void progress(MyResultProgressListener listener, int progress) {
        MyMainHandler.post(new Runnable() {
            @Override
            public void run() {
                listener.onProgress(progress);
            }
        });
    }

}
