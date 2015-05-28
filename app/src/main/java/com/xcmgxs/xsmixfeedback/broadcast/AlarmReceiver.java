package com.xcmgxs.xsmixfeedback.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.xcmgxs.xsmixfeedback.service.NotificationUtils;
import com.xcmgxs.xsmixfeedback.util.TLog;

/**
 * Created by zhangyi on 2015-05-25.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        TLog.log("onReceive ->com.xcmgxs.feedback收到定时获取消息");
        NotificationUtils.requestNotification(context);
    }
}
