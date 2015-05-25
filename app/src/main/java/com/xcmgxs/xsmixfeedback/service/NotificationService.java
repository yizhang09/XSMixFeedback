package com.xcmgxs.xsmixfeedback.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.RemoteException;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.xcmgxs.xsmixfeedback.bean.Notification;
import com.xcmgxs.xsmixfeedback.broadcast.AlarmReceiver;

import org.apache.http.Header;

import java.lang.ref.WeakReference;

public class NotificationService extends Service {

    public static final String INTENT_ACTION_GET = "com.xcmgxs.feedback.service.GET_NOTICE";
    public static final String INTENT_ACTION_CLEAR = "com.xcmgxs.feedback.service.CLEAR_NOTICE";
    public static final String INTENT_ACTION_BROADCAST = "com.xcmgxs.feedback.service.BROADCAST_NOTICE";
    public static final String INTENT_ACTION_SHUTDOWN = "com.xcmgxs.feedback.service.SHUTDOWN_NOTICE";
    public static final String INTENT_ACTION_REQUEST = "com.xcmgxs.feedback.service.REQUEST_NOTICE";

    public static final String BUNDLE_KEY_TYPE = "bundle_key_type";

    private static final long INTERVAL = 1000*120;
    private AlarmManager mAlarmManager;

    private Notification mNotification;



    //广播接收器，负责接收系统广播
    private final BroadcastReceiver mReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(INTENT_ACTION_BROADCAST.equals(action)){

            }
            else if (INTENT_ACTION_REQUEST.equals((action))){

            }
            else if(INTENT_ACTION_SHUTDOWN.equals(action)){

            }
        }
    };

    public NotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAlarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        startRequestAlarm();
        requestNotification();

        IntentFilter filter = new IntentFilter(INTENT_ACTION_BROADCAST);

        filter.addAction(INTENT_ACTION_SHUTDOWN);
        filter.addAction(INTENT_ACTION_REQUEST);
        registerReceiver(mReceiver,filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        cancelRequestAlarm();
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    private void startRequestAlarm(){
        cancelRequestAlarm();
        mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+1000,INTERVAL,getOperationIntent());
    }

    private void cancelRequestAlarm(){
        mAlarmManager.cancel(getOperationIntent());
    }

    private PendingIntent getOperationIntent(){
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent operation = PendingIntent.getBroadcast(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        return operation;
    }

    private void clearNotification(){

    }

    private void notification(Notification notification){

    }

    private void requestNotification(){

    }

    private final AsyncHttpResponseHandler mGetNotificationHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int i, Header[] headers, byte[] bytes) {

        }

        @Override
        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

        }
    };

    private final AsyncHttpResponseHandler mClearNotificationHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int i, Header[] headers, byte[] bytes) {

        }

        @Override
        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

        }
    };

    private static class ServiceStub extends INotificationService.Stub {
        WeakReference<NotificationService> mService;

        ServiceStub(NotificationService service){
            mService = new WeakReference<NotificationService>(service);
        }

        @Override
        public void scheduleNotice() throws RemoteException {

        }

        @Override
        public void requestNotice() throws RemoteException {

        }

        @Override
        public void clearNotice(int uid, int type) throws RemoteException {

        }
    }

    private final IBinder mBinder = new ServiceStub(this);
}
