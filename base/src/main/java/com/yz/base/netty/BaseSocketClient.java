package com.yz.base.netty;

import com.blankj.utilcode.util.NetworkUtils;
import com.yz.base.utils.MyLogger;
import com.yz.base.utils.MyMainHandler;
import com.yz.base.utils.MyTimer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.nio.NioEventLoopGroup;

public abstract class BaseSocketClient {

    protected final static int SO_TIMEOUT = 5 * 1000;
    protected final static int RETRY_TIME = 3 * 1000;
    protected final static int HEARTBEAT_TIME = 3 * 1000;
    protected MyTimer mHeartbeatTimer;
    protected NioEventLoopGroup mGroup;
    protected Channel mChannel;
    protected boolean isStart=false;
    protected boolean isDestroy=false;

    protected ExecutorService mConnectThreadPool = Executors.newSingleThreadExecutor();
    protected ExecutorService mSendThreadPool = Executors.newSingleThreadExecutor();
    protected ExecutorService mHandleThreadPool = Executors.newSingleThreadExecutor();

    private SendListener mSendListener;

    public void setmSendListener(SendListener mSendListener) {
        this.mSendListener = mSendListener;
    }

    public void init() {
        isDestroy=false;
        start();
    }

    public void destroy() {
        isDestroy=true;
        stop();
    }

    protected void start() {
        if(isDestroy()){
            return;
        }
        if (mConnectThreadPool.isShutdown()) {
            mConnectThreadPool = Executors.newSingleThreadExecutor();
        }
        mConnectThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                connect();
            }
        });
    }

    protected void stop() {
        mConnectThreadPool.shutdownNow();
        mSendThreadPool.shutdownNow();
        mHandleThreadPool.shutdownNow();
        if(mHeartbeatTimer!=null){
            mHeartbeatTimer.destroy();
            mHeartbeatTimer=null;
        }
        if (mChannel!=null) {
            mChannel.close();
            mChannel=null;
        }
        isStart=false;
    }

    protected void retry(){
        stop();
        MyMainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                start();
            }
        },RETRY_TIME);
    }

    protected boolean isNetError(){
        boolean isNetError=!NetworkUtils.isConnected();
        if (isNetError) {
            MyLogger.w("=====BaseSocketClient net error");
        }
        return isNetError;
    }

    protected boolean isDestroy(){
        if (isDestroy) {
            MyLogger.w("=====BaseSocketClient destroy");
        }
        return isDestroy;
    }

    protected void startHeartbeatTimer() {
        mHeartbeatTimer = new MyTimer
                .Builder()
                .setPeriod(HEARTBEAT_TIME)
                .setListener(new MyTimer.TimerListener() {
                    @Override
                    public void onStart() {
                    }
                    @Override
                    public void onPause() {
                    }
                    @Override
                    public void onKeep(long t) {
                        heartbeat();
                    }
                }).build();
        mHeartbeatTimer.start();
    }

    protected void send(Object object) {
        if(isDestroy()||isNetError()){
            if(mSendListener!=null){
                mSendListener.onFailure();
            }
            return;
        }
        if (mSendThreadPool.isShutdown()) {
            mSendThreadPool = Executors.newSingleThreadExecutor();
        }
        mSendThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                if(mChannel!=null&&object!=null){
                    mChannel.writeAndFlush(object).addListener(new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture future) throws Exception {
                            if(future.isSuccess()){
                                if(mSendListener!=null){
                                    mSendListener.onSuccess();
                                }
                            }else{
                                if(mSendListener!=null){
                                    mSendListener.onFailure();
                                }
                            }
                        }
                    });
                }else{
                    if(mSendListener!=null){
                        mSendListener.onFailure();
                    }
                }
            }
        });
    }

    protected void handle(Object object){
        if(isDestroy()){
            return;
        }
        if (mHandleThreadPool.isShutdown()) {
            mHandleThreadPool = Executors.newSingleThreadExecutor();
        }
        mHandleThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                handleMessage(object);
            }
        });
    }

    protected abstract void connect();

    protected abstract void heartbeat();

    protected abstract void register();

    protected abstract void handleMessage(Object object);

    public interface SendListener {
        void onSuccess();
        void onFailure();
    }
}
