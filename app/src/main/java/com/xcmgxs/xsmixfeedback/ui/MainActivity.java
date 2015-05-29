package com.xcmgxs.xsmixfeedback.ui;


import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import com.xcmgxs.xsmixfeedback.AppContext;
import com.xcmgxs.xsmixfeedback.AppManager;
import com.xcmgxs.xsmixfeedback.R;
import com.xcmgxs.xsmixfeedback.common.DoubleClickExitHelper;
import com.xcmgxs.xsmixfeedback.common.UIHelper;
import com.xcmgxs.xsmixfeedback.interfaces.DrawerMenuCallBack;
import com.xcmgxs.xsmixfeedback.service.NotificationUtils;
import com.xcmgxs.xsmixfeedback.ui.fragments.DrawerNavigationMenu;
import com.xcmgxs.xsmixfeedback.ui.fragments.ExploreViewPagerFragment;
import com.xcmgxs.xsmixfeedback.ui.fragments.MySelfViewPagerFragment;
import com.xcmgxs.xsmixfeedback.widget.BadgeView;

import org.apache.http.Header;

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
            ExploreViewPagerFragment.class.getName(),
            MySelfViewPagerFragment.class.getName()
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = (AppContext)getApplicationContext();
        initView(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
//        if(mContext.isReceiveNotice()){
//            foreachUserNotice();
//        }

        //绑定Service
        NotificationUtils.bindToService(this);
    }

    private void initView(Bundle savedInstanceState) {

        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeButtonEnabled(true);

        mDoubleClickExitHelper = new DoubleClickExitHelper(this);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerListener(new DrawerMenuListener());

        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.drawable.ic_drawer,0,0);
        mFragmentManager = getSupportFragmentManager();
        if(null == savedInstanceState){
            setExploreView();
        }

    }

    private void setExploreView(){
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        mMenu = DrawerNavigationMenu.newInstance();
        fragmentTransaction.replace(R.id.main_slidingmenu_frame,mMenu , DRAWER_MENU_TAG)
                .replace(R.id.main_content, ExploreViewPagerFragment.newInstance(), DRAWER_CONTENT_TAG).commit();

        mTitle = "发现";
        mActionBar.setTitle(mTitle);
        mCurrentContentTag = CONTENT_TAG_EXPLORE;

    }

    //轮询消息
    private void foreachUserNotice(){
        final boolean isLogin = mContext.isLogin();
        final Handler handler = new Handler(){
            @SuppressWarnings("unchecked")
            public void handleMessage(Message msg){
                if(msg.what == 1){
                    //CommonList<Project>
                }
                foreachUserNotice();
            }
        };

        new Thread(){
            public void run(){
                Message msg = new Message();
                try{
                    sleep(60 * 1000);
                    if(isLogin){
                        msg.obj = null;
                        msg.what = 1;
                    }
                    else {
                        msg.what = 0;
                    }
                }
//                catch (AppException e){
//                    e.printStackTrace();
//                    msg.what = -1;
//                }
                catch (Exception e){
                    e.printStackTrace();
                    msg.what = -1;
                }
                handler.sendMessage(msg);
            }
        }.start();
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

        if (mCurrentContentTag != null && mContext !=null && mMenu != null) {
            //setExploreView();
            mMenu.highlightProjects();
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.main_actionbar_menu_search:
                //UIHelper.showSearch(mContext);
                return true;
            case R.id.main_actionbar_menu_notification:
                //onClickNotice();
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
    public void onClickProjects() {
        setExploreView();
        //showMainContent(0);
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

    }


    @Override
    public void onClickExit() {

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
}
