package com.xcmgxs.xsmixfeedback.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.xcmgxs.xsmixfeedback.AppContext;
import com.xcmgxs.xsmixfeedback.R;
import com.xcmgxs.xsmixfeedback.api.XsFeedbackApi;
import com.xcmgxs.xsmixfeedback.bean.Notification;
import com.xcmgxs.xsmixfeedback.common.Contanst;
import com.xcmgxs.xsmixfeedback.ui.baseactivity.BaseActionBarActivity;

import org.apache.http.Header;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NotificationDetailActivity extends BaseActionBarActivity {

    @InjectView(R.id.notification_detail_title)
    TextView mNotificationDetailTitle;
    @InjectView(R.id.notification_detail_content)
    TextView mNotificationDetailContent;
    @InjectView(R.id.notification_detail_creator)
    TextView mNotificationDetailCreator;
    @InjectView(R.id.notification_detail_createdate)
    TextView mNotificationDetailCreatedate;

    private AppContext mContext;

    private Notification mNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_detail);
        ButterKnife.inject(this);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView() {
        Intent intent = getIntent();
        mNotification = (Notification) intent.getSerializableExtra(Contanst.NOTIFICATOIN);
        initData();
    }

    private void initData() {
        mNotificationDetailTitle.setText(mNotification.getTitle());
        mNotificationDetailContent.setText(mNotification.getContent());
        mNotificationDetailCreator.setText(mNotification.getCreator().getName());
        mNotificationDetailCreatedate.setText(mNotification.getCreatetime());
        setReaded(mNotification);
    }

    private void setReaded(Notification notification) {
        if (notification != null) {
            // 设置未读通知为已读
            if (!notification.isRead()) {
                XsFeedbackApi.setNotificationReaded(notification.getId(), new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });
            }
        }
    }
}
