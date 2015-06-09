package com.xcmgxs.xsmixfeedback.ui.fragments;

import android.os.Bundle;

import com.xcmgxs.xsmixfeedback.R;
import com.xcmgxs.xsmixfeedback.adapter.ViewPageFragmentAdapter;
import com.xcmgxs.xsmixfeedback.ui.basefragment.BaseViewPagerFragment;

/**
 * Created by zhangyi on 2015-06-05.
 */
public class NotificaiontViewPagerFragment extends BaseViewPagerFragment {

    public static NotificaiontViewPagerFragment newInstance() {
        return new NotificaiontViewPagerFragment();
    }

    @Override
    protected void onSetupTabAdapter(ViewPageFragmentAdapter adapter) {
        String[] title = getResources().getStringArray(R.array.notification_title_array);
        Bundle unRead = new Bundle();
        unRead.putInt(NotificationFragment.NOTIFICATION_ACTION_KEY, NotificationFragment.ACTION_UNREAD);
        adapter.addTab(title[0], "unread", NotificationFragment.class, unRead);
        Bundle readed = new Bundle();
        readed.putInt(NotificationFragment.NOTIFICATION_ACTION_KEY, NotificationFragment.ACTION_READED);
        adapter.addTab(title[1], "readed", NotificationFragment.class, readed);
    }
}
