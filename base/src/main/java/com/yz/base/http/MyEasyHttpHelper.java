package com.yz.base.http;

import android.text.TextUtils;
import com.afollestad.ason.Ason;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yz.base.R;
import com.yz.base.utils.MyStrHelper;
import com.yz.base.utils.MyLogger;
import com.yz.base.utils.MyMainHandler;

import java.lang.reflect.Type;

/**
 * @author :yanzheng
 * @describe ：Http网络请求封装
 */
public class MyEasyHttpHelper extends BaseEaseHttp{

    private int state=-999;
    private String message="数据解析失败";
    private String returnData;
    private int hasNext;

    private static class SingletonHolder{
        private static MyEasyHttpHelper instance = new MyEasyHttpHelper();
    }

    private MyEasyHttpHelper() {
    }

    public static MyEasyHttpHelper getInstance() {
        return SingletonHolder.instance;
    }

    public <T> void success(final String result, final Type type, final MyResultListener<T> listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(TextUtils.isEmpty(result)){
                    failure(listener,"result is null");
                    return;
                }
                try{
                    Ason ason=new Ason(result);
                    state=ason.get("state",-1);
                    message=ason.get("message");
                    Object object=ason.get("returnData");
                    returnData=object==null?"":object.toString();
                    hasNext=ason.get("hasNext",0);
                }catch(Exception e){
                    e.printStackTrace();
                }
                if(state!=mSuccess){
                    failure(listener,message);
                    if(state==mInvalid){
                        if(mInvalidListener!=null){
                            MyMainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    mInvalidListener.invalid();
                                }
                            });
                        }
                    }
                    return;
                }
                if(type.equals(new TypeToken<String>(){}.getType())){
                    MyMainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(!TextUtils.isEmpty(returnData)){
                                listener.onSuccess((T)returnData);
                            }else{
                                listener.onSuccess((T)message);
                            }
                        }
                    });
                }else{
                    try {
                        final T t = new Gson().fromJson(returnData, type);
                        MyMainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onSuccess(t);
                            }
                        });
                    } catch (Exception e) {
                        MyLogger.e(e.getMessage(),e);
                        failure(listener, MyStrHelper.getString(mContext,R.string.yz_base_data_error));
                    }
                }
                if(listener instanceof MyResultHasMoreListener){
                    hasMore((MyResultHasMoreListener)listener,hasNext!=0);
                }
            }
        }).start();
    }

}
