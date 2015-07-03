package com.xcmgxs.xsmixfeedback.ui;


import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.ActionBarDrawerToggle;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.xcmgxs.xsmixfeedback.AppContext;
import com.xcmgxs.xsmixfeedback.AppManager;
import com.xcmgxs.xsmixfeedback.R;
import com.xcmgxs.xsmixfeedback.api.XsFeedbackApi;
import com.xcmgxs.xsmixfeedback.bean.Notification;
import com.xcmgxs.xsmixfeedback.common.Contanst;
import com.xcmgxs.xsmixfeedback.common.DoubleClickExitHelper;
import com.xcmgxs.xsmixfeedback.common.UIHelper;
import com.xcmgxs.xsmixfeedback.common.UpdateManager;
import com.xcmgxs.xsmixfeedback.interfaces.DrawerMenuCallBack;
import com.xcmgxs.xsmixfeedback.service.NotificationUtils;
import com.xcmgxs.xsmixfeedback.ui.fragments.DrawerNavigationMenu;
import com.xcmgxs.xsmixfeedback.ui.fragments.ExploreViewPagerFragment;
import com.xcmgxs.xsmixfeedback.util.JsonUtils;
import com.xcmgxs.xsmixfeedback.widget.BadgeView;

import org.apache.http.Header;

import java.util.List;

/**
 * Created by zhangyi on 2015-3-18.
 * @author zhangyi
 */
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class MainActivity extends ActionBarActivity implements DrawerMenuCallBack {

    static final String DRAWER_MENU_TAG = "drawer_menu";
    static final String DRAWER_CONTENT_TAG = "drawer_content";

    static final String CONTENT_TAG_EXPLORE = "content_explore";
    static final String CONTENT_TAG_MYSELF = "content_myself";

    static final String[] CONTENTS = {
            CONTENT_TAG_EXPLORE,
            CONTENT_TAG_MYSELF
    };

    static final String[] FRAGMENTS = {
            ExploreViewPagerFragment.class.getName()
    };

    final String[] TITLES = {
            "发现",
            "我的"
    };

    private static DrawerNavigationMenu mMenu;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private FragmentManager mFragmentManager;
    private DoubleClickExitHelper mDoubleClickExitHelper;

    private String mCurrentContentTag;
    private ActionBar mActionBar;
    private AppContext mContext;

    private static String mTitle;
    public static BadgeView mNotificationBadgeView;
    private MenuItem mItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = (AppContext)getApplicationContext();
        initView();
        AppManager.getAppManager().addActivity(this);
//        if(mContext.isReceiveNotice()){
//            foreachUserNotice();
//        }

        //绑定Service
        NotificationUtils.bindToService(this);
    }

    private void initView() {

        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeButtonEnabled(true);

        mDoubleClickExitHelper = new DoubleClickExitHelper(this);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerListener(new DrawerMenuListener());

        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.drawable.ic_drawer,0,0);
        mFragmentManager = getSupportFragmentManager();
//        if(null == savedInstanceState){
//            setExploreView();
//        }
        setExploreView();

        Intent intent = getIntent();
        boolean isNotify = intent.getBooleanExtra(Contanst.NOTIFICATOIN,false);
        if(isNotify){
            UIHelper.showNotification(this);
        }

    }

    private void setExploreView(){
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        mMenu = DrawerNavigationMenu.newInstance();
        fragmentTransaction.replace(R.id.main_slidingmenu_frame,mMenu , DRAWER_MENU_TAG)
                .replace(R.id.main_content, ExploreViewPagerFragment.newInstance(), DRAWER_CONTENT_TAG).commit();

        mTitle = "项目";
        mActionBar.setTitle(mTitle);
        mCurrentContentTag = CONTENT_TAG_EXPLORE;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NotificationUtils.unBindFromService(this);
        //unregisterReceiver(mReceiver);
        //mReceiver = null;
        NotificationUtils.tryToShutDown(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mTitle != null) {
            mActionBar.setTitle(mTitle);
        }
        setNotificationIcon();

        if (mCurrentContentTag != null && mContext !=null && mMenu != null) {
            //setExploreView();
            //mMenu.highlightProjects();
//            if (mCurrentContentTag.equalsIgnoreCase(CONTENTS[1])) {
//                if (!mContext.isLogin()) {
//                    onClickProjects();
//                    mMenu.highlightProjects();
//                }
//            }
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
        View mainContent = findViewById(R.id.main_content);
        if (mainContent != null) {
            mainContent.setAlpha(0);
            mainContent.animate().alpha(1).setDuration(200);
        } else {

        }

        // 检查新版本
        if (mContext.isCheckUp()) {
            UpdateManager.getUpdateManager().checkAppUpdate(this, false);
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(mDrawerLayout.isDrawerOpen(Gravity.START)){
                mDrawerLayout.closeDrawers();
                return true;
            }
            return mDoubleClickExitHelper.onKeyDown(keyCode,event);
        }

        if(keyCode == KeyEvent.KEYCODE_MENU){
            if(mDrawerLayout.isDrawerOpen(Gravity.START)){
                mDrawerLayout.closeDrawers();
                return true;
            }
            else {
                mDrawerLayout.openDrawer(Gravity.START);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    /** 显示内容*/
    private void showMainContent(int pos) {
        mDrawerLayout.closeDrawers();
        String tag = CONTENTS[pos];
        if (tag.equalsIgnoreCase(mCurrentContentTag)) return;

        FragmentTransaction ft = mFragmentManager.beginTransaction();
        if(mCurrentContentTag != null) {
            Fragment fragment = mFragmentManager.findFragmentByTag(mCurrentContentTag);
            if(fragment != null) {
                ft.remove(fragment);
            }
        }
        ft.replace(R.id.main_content, Fragment.instantiate(this, FRAGMENTS[pos]), tag);
        ft.commit();

        mActionBar.setTitle(TITLES[pos]);
        mTitle = mActionBar.getTitle().toString();//记录主界面的标题
        mCurrentContentTag = tag;
    }

    private void showLoginActivity() {
        if (!mContext.isLogin()) {
            Intent intent = new Intent(mContext, LoginActivity.class);
            startActivity(intent);
        } else {
            UIHelper.showMySelfInfoDetail(MainActivity.this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mItem = menu.findItem(R.id.main_actionbar_menu_notification);
        setNotificationIcon();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.main_actionbar_menu_search:
                UIHelper.showSearch(MainActivity.this);
                return true;
            case R.id.main_actionbar_menu_notification:
                UIHelper.showNotification(MainActivity.this);
                return true;
            default:
                break;
        }
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickLogin() {
        showLoginActivity();
    }

    @Override
    public void onClickSchedule() {
        UIHelper.showProjectReportListActivity(MainActivity.this);
    }

    @Override
    public void onClickProjects() {
        UIHelper.showProjectStatActivity(MainActivity.this);
    }

    @Override
    public void onClickLogs() {
        UIHelper.showProjectListActivity(MainActivity.this, null, ProjectSomeInfoListActivity.ALL_LIST_TYPE_LOGS);
    }


    @Override
    public void onClickIssues() {
        UIHelper.showProjectListActivity(MainActivity.this, null, ProjectSomeInfoListActivity.ALL_LIST_TYPE_ISSUES);
    }

    @Override
    public void onClickFiles() {
        UIHelper.showProjectListActivity(MainActivity.this, null, ProjectSomeInfoListActivity.ALL_LIST_TYPE_FILES);
    }

    @Override
    public void onClickSetting() {
        UIHelper.showSetting(MainActivity.this);
    }


    @Override
    public void onClickAbout() {
        UIHelper.showAbout(MainActivity.this);
    }

    private class DrawerMenuListener implements DrawerLayout.DrawerListener {

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            mDrawerToggle.onDrawerSlide(drawerView,slideOffset);
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            mDrawerToggle.onDrawerOpened(drawerView);
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            mDrawerToggle.onDrawerClosed(drawerView);
        }

        @Override
        public void onDrawerStateChanged(int newState) {
            mDrawerToggle.onDrawerStateChanged(newState);
        }
    }

    private void setNotificationIcon(){
        XsFeedbackApi.getNotification(false, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                List<Notification> notificationArrays = JsonUtils.getList(Notification[].class, responseBody);
                if (notificationArrays.size() != 0) {
                    mItem.setIcon(R.drawable.menu_icon_notice_unread);
                }
                else{
                    mItem.setIcon(R.drawable.menu_icon_notice);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
}
