package com.xcmgxs.xsmixfeedback.common;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.xcmgxs.xsmixfeedback.AppContext;

/**
 * @author zhangyi
 * Created by zhangyi on 2015-3-18.
 */
public class BroadcastController {

    public static final String ACTION_USERCHANGE = "com.xcmgxs.feedback.ACTION_USERCHANGE";

    //发送用户变化的广播
    public static void sendUserChangeBroadcast(Context context) {
        context.sendBroadcast(new Intent(ACTION_USERCHANGE));
    }

    //注册一个监听用户变化的广播
    public static void registerUserChangeReceiver(Context context, BroadcastReceiver receiver) {
        IntentFilter filter = new IntentFilter(ACTION_USERCHANGE);
        context.registerReceiver(receiver,filter);
    }

    public static void unregisterReceiver(Context context, BroadcastReceiver receiver) {
        try {
            context.unregisterReceiver(receiver);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
