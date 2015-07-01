package com.xcmgxs.xsmixfeedback.adapter;

import android.content.Context;

import com.xcmgxs.xsmixfeedback.R;
import com.xcmgxs.xsmixfeedback.adapter.base.CommonAdapter;
import com.xcmgxs.xsmixfeedback.adapter.base.ViewHolder;
import com.xcmgxs.xsmixfeedback.bean.Notification;
import com.xcmgxs.xsmixfeedback.util.StringUtils;

/**
 * Created by zhangyi on 2015-06-05.
 */
public class NotificationAdapter extends CommonAdapter<Notification> {


    public NotificationAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void convert(ViewHolder vh, Notification notification) {
        vh.setText(R.id.notification_listitem_title, notification.getTitle());
        vh.setText(R.id.notification_listitem_content, notification.getContent());
        vh.setText(R.id.notification_listitem_date, notification.getCreatetime());
    }
}
