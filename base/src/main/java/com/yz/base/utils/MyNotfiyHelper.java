package com.yz.base.utils;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.blankj.utilcode.util.NotificationUtils;
import com.blankj.utilcode.util.Utils;
import com.yz.base.config.BaseContants;
import com.yz.base.entity.NotifyMsg;

/**
 * @ClassName: MyNotfiyHelper
 * @Description: java类作用描述
 * @Author: YZ-PC
 * @Date: 2021/1/26 18:14
 */
public class MyNotfiyHelper {

    public static void notfiy(Context context, Intent intent, NotifyMsg msg){
        if(NotificationUtils.areNotificationsEnabled()){
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra(BaseContants.IntentKey.NOTITF_MSG, msg);
            NotificationUtils.notify(msg.getId(),new Utils.Func1<Void, NotificationCompat.Builder>() {
                @Override
                public Void call(NotificationCompat.Builder param) {
                    param.setSmallIcon(msg.getIcon())
                            .setTicker(msg.getTitle())
                            .setContentTitle(msg.getTitle())
                            .setContentText(msg.getContent())
                            .setContentIntent(PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT))
                            .setAutoCancel(true);
                    return null;
                }
            });
        }
    }
}
