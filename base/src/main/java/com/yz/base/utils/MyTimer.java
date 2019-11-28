package com.yz.base.utils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yz on 2018/2/1.
 */

public class MyTimer {

    private Timer mTimer;
    private TimerTask mTimerTask;
    private long index = 0;
    private long delay = 1000;  //1s
    private long period = 1000;  //1s
    private boolean isKeep = false;
    private boolean isPause = false;
    private boolean isDownTimer = false;
    private boolean isMainTheard = false;
    private TimerListener listener;

    public MyTimer index(long index){
        this.index = index;
        return this;
    }

    public MyTimer delay(long delay){
        this.delay = delay;
        return this;
    }

    public MyTimer period(long period){
        this.period = period;
        return this;
    }

    public MyTimer isDownTimer(boolean isDownTimer){
        this.isDownTimer = isDownTimer;
        return this;
    }

    public MyTimer isMainTheard(boolean isMainTheard){
        this.isMainTheard = isMainTheard;
        return this;
    }

    public MyTimer listener(TimerListener listener){
        this.listener = listener;
        return this;
    }

    public MyTimer schedule() {
        isKeep=true;
        isPause=false;
        if(mTimer==null){
            mTimer=new Timer();
        }
        if(mTimerTask==null){
            mTimerTask= new TimerTask() {
                @Override
                public void run() {
                    if(isKeep){
                        do {
                            try {
                                Thread.sleep(period);
                            } catch (InterruptedException e) {
                                MyLogger.e(e.getMessage(),e);
                            }
                        } while (isPause);
                        if(isKeep){
                            if(isDownTimer){
                                index--;
                            }else{
                                index++;
                            }
                            keep(index);
                        }
                    }
                }
            };
        }
        mTimer.schedule(mTimerTask, delay, period);
        start();
        return this;
    }

    public void cancel() {
        end();
        index = 0;
        isKeep=false;
        isPause=false;
        if(mTimer!=null){
            mTimer.cancel();
            mTimer=null;
        }
        if(mTimerTask!=null){
            mTimerTask.cancel();
            mTimerTask=null;
        }
    }

    public void pause(){
        isPause=true;
        if(!isMainTheard){
            if(listener!=null){
                listener.pause(MyTimer.this);
            }
            return;
        }
        MyMainHandler.post(new Runnable() {
            @Override
            public void run() {
                if(listener!=null){
                    listener.pause(MyTimer.this);
                }
            }
        });
    }

    public void resume(){
        isPause=false;
        if(!isMainTheard){
            if(listener!=null){
                listener.resume(MyTimer.this);
            }
            return;
        }
        MyMainHandler.post(new Runnable() {
            @Override
            public void run() {
                if(listener!=null){
                    listener.resume(MyTimer.this);
                }
            }
        });
    }

    public boolean isKeep(){
        return isKeep;
    }

    public boolean isPause() {
        return isPause;
    }

    private void start(){
        if(!isMainTheard){
            if(listener!=null){
                listener.start(MyTimer.this);
            }
            return;
        }
        MyMainHandler.post(new Runnable() {
            @Override
            public void run() {
                if(listener!=null){
                    listener.start(MyTimer.this);
                }
            }
        });
    }

    private void end(){
        if(!isMainTheard){
            if(listener!=null){
                listener.end(MyTimer.this);
            }
            return;
        }
        MyMainHandler.post(new Runnable() {
            @Override
            public void run() {
                if(listener!=null){
                    listener.end(MyTimer.this);
                }
            }
        });
    }

    private void keep(long index){
        if(!isMainTheard){
            if(listener!=null){
                listener.keep(MyTimer.this,index);
            }
            return;
        }
        MyMainHandler.post(new Runnable() {
            @Override
            public void run() {
                if(listener!=null){
                    listener.keep(MyTimer.this,index);
                }
            }
        });
    }

    public interface TimerListener {
        void start(MyTimer timer);
        void end(MyTimer timer);
        void resume(MyTimer timer);
        void pause(MyTimer timer);
        void keep(MyTimer timer, long t);
    }
}
