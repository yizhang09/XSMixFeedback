package com.xcmgxs.xsmixfeedback.ui;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.xcmgxs.xsmixfeedback.R;
import com.xcmgxs.xsmixfeedback.ui.baseactivity.BaseActionBarActivity;
import com.xcmgxs.xsmixfeedback.ui.fragments.NotificaiontViewPagerFragment;

public class NotificationActivity extends BaseActionBarActivity {

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        initView();
    }

    private void initView() {
        mTitle = "我的通知";
        mActionBar.setTitle(mTitle);
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.replace(R.id.notification_content, NotificaiontViewPagerFragment.newInstance()).commit();
    }
}
