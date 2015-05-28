package com.xcmgxs.xsmixfeedback.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.xcmgxs.xsmixfeedback.R;
import com.xcmgxs.xsmixfeedback.api.ApiClient;
import com.xcmgxs.xsmixfeedback.bean.Notification;
import com.xcmgxs.xsmixfeedback.broadcast.AlarmReceiver;
import com.xcmgxs.xsmixfeedback.common.UIHelper;
import com.xcmgxs.xsmixfeedback.ui.MainActivity;
import com.xcmgxs.xsmixfeedback.util.JsonUtils;
import com.xcmgxs.xsmixfeedback.util.TLog;

import org.apache.http.Header;

import java.lang.ref.WeakReference;
import java.util.List;

public class NotificationService extends Service {

    public static final String INTENT_ACTION_GET = "com.xcmgxs.feedback.service.GET_NOTICE";
    public static final String INTENT_ACTION_CLEAR = "com.xcmgxs.feedback.service.CLEAR_NOTICE";
    public static final String INTENT_ACTION_BROADCAST = "com.xcmgxs.feedback.service.BROADCAST_NOTICE";
    public static final String INTENT_ACTION_SHUTDOWN = "com.xcmgxs.feedback.service.SHUTDOWN_NOTICE";
    public static final String INTENT_ACTION_REQUEST = "com.xcmgxs.feedback.service.REQUEST_NOTICE";

    public static final String BUNDLE_KEY_TYPE = "bundle_key_type";

    private static final long INTERVAL = 1000*60;
    private AlarmManager mAlarmManager;

    private List<Notification> mNotification;

    //广播接收器，负责接收系统广播
    private final BroadcastReceiver mReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(INTENT_ACTION_BROADCAST.equals(action)){
                if(mNotification != null){
                    for(Notification n :mNotification) {
                        UIHelper.sendBroadcast(context, n);
                    }
                }
            }
            else if (INTENT_ACTION_REQUEST.equals((action))){
                requestNotification();
            }
            else if(INTENT_ACTION_SHUTDOWN.equals(action)){
                stopSelf();
            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAlarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        startRequestAlarm();
        //requestNotification();

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
        // 从1秒后开始，每隔2分钟执行getOperationIntent()
        mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1000, INTERVAL, getOperationIntent());
    }

    private void cancelRequestAlarm(){
        mAlarmManager.cancel(getOperationIntent());
    }

    /**
     * OSC采用轮询方式实现消息推送<br>
     * 每次被调用都去执行一次AlarmReceiver.onReceive()方法
     *
     * @return
     */
    private PendingIntent getOperationIntent(){
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent operation = PendingIntent.getBroadcast(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        Log.i("info","alarm");
        return operation;
    }

    private void clearNotification(int uid,int type){
        ApiClient.clearNotice(uid,type,mClearNotificationHandler);
    }

    private void requestNotification(){
        TLog.log("ApiClient ->requestNotification");
        ApiClient.getNotices(mGetNotificationHandler);
    }

    private int lastNotifyCount;

    private void notification(Notification notification){
        Resources res = getResources();
        String contentTitle = res.getString(R.string.you_have_news_messages, "1");
        String contentText;

        contentText = notification.getTitle();

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("NOTIFICATION", true);
        PendingIntent pi = PendingIntent.getActivity(this,1000,intent,PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setTicker(contentTitle)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setAutoCancel(true)
                .setContentIntent(pi)
                .setSmallIcon(R.drawable.xcmg);

        android.app.Notification appNotify = builder.build();
        NotificationManagerCompat.from(this).notify(R.string.you_have_news_messages,appNotify);

    }

    private final AsyncHttpResponseHandler mGetNotificationHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int i, Header[] headers, byte[] bytes) {
            try {
                List<Notification> notifications = JsonUtils.toList(Notification[].class, bytes);
                if(notifications != null && !notifications.isEmpty()){
                    for(Notification n : notifications){
                        UIHelper.sendBroadcast(NotificationService.this, n);
                        notification(n);
                    }
                    mNotification = notifications;
                }
            }
            catch (Exception e){
                e.printStackTrace();
                onFailure(i,headers,bytes,null);
            }
        }

        @Override
        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
            throwable.printStackTrace();
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
            mService.get().startRequestAlarm();
        }

        @Override
        public void requestNotice() throws RemoteException {
            mService.get().requestNotification();
        }

        @Override
        public void clearNotice(int uid, int type) throws RemoteException {
            mService.get().clearNotification(uid,type);
        }
    }

    private final IBinder mBinder = new ServiceStub(this);
}
