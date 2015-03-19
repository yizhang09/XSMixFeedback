package com.xcmgxs.xsmixfeedback.ui;

import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.xcmgxs.xsmixfeedback.AppContext;
import com.xcmgxs.xsmixfeedback.R;
import com.xcmgxs.xsmixfeedback.common.DoubleClickExitHelper;
import com.xcmgxs.xsmixfeedback.interfaces.DrawerMenuCallBack;
import com.xcmgxs.xsmixfeedback.ui.fragments.ExploreViewPagerFragment;
import com.xcmgxs.xsmixfeedback.ui.fragments.MySelfViewPagerFragment;
import com.xcmgxs.xsmixfeedback.widget.BadgeView;

/**
 * Created by zhangyi on 2015-3-18.
 * @author zhangyi
 */
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

    private static DrawerNavigationMenu mMenu = DrawerNavigationMenu.newInstance();
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private FragmentManager mFramgmentManager;
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickLogin() {

    }

    @Override
    public void onClickSetting() {

    }

    @Override
    public void onClickExplore() {

    }

    @Override
    public void onClickMySelf() {

    }

    @Override
    public void onClickLanguage() {

    }

    @Override
    public void onClickShake() {

    }

    @Override
    public void onClickExit() {

    }
}
