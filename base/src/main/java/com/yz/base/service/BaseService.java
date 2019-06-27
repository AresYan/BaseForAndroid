package com.yz.base.service;

import android.app.Service;
import com.yz.base.event.BaseEvent;
import com.yz.base.utils.MyEventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by YZ on 2016/11/3.
 */
public abstract class BaseService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        MyEventBus.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyEventBus.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(BaseEvent event) {
    }

}
