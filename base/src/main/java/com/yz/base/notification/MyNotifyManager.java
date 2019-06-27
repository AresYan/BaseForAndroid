package com.yz.base.notification;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import com.yz.base.config.BaseContants;

/**
 * Created by YZ on 2016/11/30.
 */

public class MyNotifyManager {

    public static void notify_normal_singLine(Context context,Class<?> cls,MyNotifyMsg msg) {
        Intent intent = new Intent(context, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(BaseContants.IntentKey.NOTITF_MSG, msg);
        PendingIntent pIntent = PendingIntent.getActivity(context,
                (int) SystemClock.uptimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotifyUtils notify = new NotifyUtils(context, 1);
        notify.notify_normal_singline(pIntent, msg.getIconRes(), msg.getTicker(), msg.getTitle(), msg.getContent(), true, true, false);
    }
}
