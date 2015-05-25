package com.xcmgxs.xsmixfeedback.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.xcmgxs.xsmixfeedback.AppContext;

import java.util.HashMap;

/**
 * Created by zhangyi on 2015-05-25.
 */
public class NotificationUtils {

    public static INotificationService notificationService = null;
    private static HashMap<Context,ServiceBinder> mConnectionMap = new HashMap<Context,ServiceBinder>();

    public static boolean bindToService(Context context){
        return bindToService(context,null);
    }

    public static boolean bindToService(Context context,ServiceConnection callback){
        context.startService(new Intent(context, NotificationService.class));
        ServiceBinder serviceBinder = new ServiceBinder(callback);
        mConnectionMap.put(context,serviceBinder);
        return context.bindService((new Intent()).setClass(context, NotificationService.class), serviceBinder, 0);
    }

    public static void unBindFromService(Context context){
        ServiceBinder serviceBinder = mConnectionMap.remove(context);
        if(serviceBinder == null){
            Log.e("MusicUtils","Try to unbind from unknown context");
            return;
        }
        context.unbindService(serviceBinder);
        if(mConnectionMap.isEmpty()){
            notificationService = null;
        }
    }

    public static void clearNotification(int type){
        if(notificationService != null){
            try {
                notificationService.clearNotice(AppContext.getInstance().getLoginUid(),type);
            }
            catch (RemoteException e){
                e.printStackTrace();
            }
        }
    }

    public static void requestNotification(Context context){
        if(notificationService != null){
            try {
                notificationService.requestNotice();
            }catch (RemoteException e){
                e.printStackTrace();
            }
        } else {
            context.sendBroadcast(new Intent(NotificationService.INTENT_ACTION_REQUEST));
        }
    }

    public static void scheduleNotification(){
        if(notificationService != null){
            try {
                notificationService.scheduleNotice();
            }catch (RemoteException e){
                e.printStackTrace();
            }
        }
    }

    public static void tryToShutDown(Context context){

    }

    public static void startNotificationService(Context context){
        Intent intent = new Intent(context,NotificationService.class);
        context.startService(intent);
    }

    private static class ServiceBinder implements ServiceConnection {

        ServiceConnection mCallback;

        ServiceBinder(ServiceConnection serviceConnection){
            mCallback = serviceConnection;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            notificationService = INotificationService.Stub.asInterface(service);
            if(mCallback != null){
                mCallback.onServiceConnected(name,service);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            if(mCallback != null){
                mCallback.onServiceDisconnected(name);
            }
            notificationService = null;
        }
    }
}
