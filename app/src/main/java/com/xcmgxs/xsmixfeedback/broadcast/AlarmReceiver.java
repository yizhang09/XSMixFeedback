package com.xcmgxs.xsmixfeedback.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.xcmgxs.xsmixfeedback.service.NotificationUtils;

/**
 * Created by zhangyi on 2015-05-25.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationUtils.requestNotification(context);
    }
}
