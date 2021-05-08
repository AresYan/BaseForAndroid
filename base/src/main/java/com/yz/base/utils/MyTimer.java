package com.yz.base.utils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yz on 2018/2/1.
 */

public class MyTimer {

    private Timer mTimer;
    private TimerTask mTimerTask;
    private boolean isPause = true;

    private long index = 0;
    private long delay = 1000;//1s
    private long period = 1000;//1s
    private boolean isDownTimer;
    private boolean isMainTheard;
    private TimerListener listener;

    public void init(){
        mTimer=new Timer();
        mTimerTask= new TimerTask() {
            @Override
            public void run() {
                do {
                    try {
                        Thread.sleep(period);
                    } catch (InterruptedException e) {
                        MyLogger.e(e.getMessage(),e);
                    }
                } while (isPause);
                if(isDownTimer){
                    index--;
                }else{
                    index++;
                }
                if(!isMainTheard){
                    if(listener!=null){
                        listener.onKeep(index);
                    }
                    return;
                }
                MyMainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(listener!=null){
                            listener.onKeep(index);
                        }
                    }
                });
            }
        };
        mTimer.schedule(mTimerTask, delay, period);
    }

    public boolean isPause() {
        return isPause;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public void setPeriod(long period) {
        this.period = period;
    }

    public void setDownTimer(boolean downTimer) {
        isDownTimer = downTimer;
    }

    public void setMainTheard(boolean mainTheard) {
        isMainTheard = mainTheard;
    }

    public void setListener(TimerListener listener) {
        this.listener = listener;
    }

    public void start(){
        isPause=false;
        if(!isMainTheard){
            if(listener!=null){
                listener.onStart();
            }
            return;
        }
        MyMainHandler.post(new Runnable() {
            @Override
            public void run() {
                if(listener!=null){
                    listener.onStart();
                }
            }
        });
    }

    public void pause(){
        isPause=true;
        if(!isMainTheard){
            if(listener!=null){
                listener.onPause();
            }
            return;
        }
        MyMainHandler.post(new Runnable() {
            @Override
            public void run() {
                if(listener!=null){
                    listener.onPause();
                }
            }
        });
    }

    public void destroy() {
        if(mTimer!=null){
            mTimer.cancel();
            mTimer=null;
        }
        if(mTimerTask!=null){
            mTimerTask.cancel();
            mTimerTask=null;
        }
    }

    public interface TimerListener {
        void onStart();
        void onPause();
        void onKeep(long t);
    }

    public static class Builder {

        private long index = 0;
        private long delay = 1000;  //1s
        private long period = 1000;  //1s
        private boolean isDownTimer = false;
        private boolean isMainTheard = false;
        private TimerListener listener;

        public MyTimer.Builder setIndex(long index) {
            this.index = index;
            return this;
        }

        public MyTimer.Builder setDelay(long delay) {
            this.delay = delay;
            return this;
        }

        public MyTimer.Builder setPeriod(long period) {
            this.period = period;
            return this;
        }

        public MyTimer.Builder setDownTimer(boolean downTimer) {
            isDownTimer = downTimer;
            return this;
        }

        public MyTimer.Builder setMainTheard(boolean mainTheard) {
            isMainTheard = mainTheard;
            return this;
        }

        public MyTimer.Builder setListener(TimerListener listener) {
            this.listener = listener;
            return this;
        }

        public MyTimer build() {
            MyTimer timer = new MyTimer();
            timer.setIndex(index);
            timer.setDelay(delay);
            timer.setPeriod(period);
            timer.setDownTimer(isDownTimer);
            timer.setMainTheard(isMainTheard);
            timer.setListener(listener);
            timer.init();
            return timer;
        }
    }
}
