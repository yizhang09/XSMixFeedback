package com.xcmgxs.xsmixfeedback.ui.baseactivity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.ViewConfiguration;

import com.xcmgxs.xsmixfeedback.AppContext;
import com.xcmgxs.xsmixfeedback.AppManager;
import com.xcmgxs.xsmixfeedback.R;
import com.xcmgxs.xsmixfeedback.interfaces.ActivityHelperInterface;
import com.xcmgxs.xsmixfeedback.ui.ActivityHelper;
import com.xcmgxs.xsmixfeedback.util.StringUtils;

import java.lang.reflect.Field;

/**
 * Created by zhangyi on 2015-4-8.
 */
public class BaseActionBarActivity extends ActionBarActivity implements ActivityHelperInterface {

    ActivityHelper helper = new ActivityHelper(this);

    protected static boolean isCanBack;

    protected ActionBar mActionBar;

    protected String mTitle;

    protected String mSubTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper.onCreate(savedInstanceState);
        initActionBar();
        //将activity加入堆栈
        AppManager.getAppManager().addActivity(this);

    }

    @Override
    public boolean onSupportNavigateUp() {
        AppManager.getAppManager().finishActivity(getActivity());
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setActionBarTitle();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        helper.onAttachedToWindow();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    public AppContext getXSApplication() {
        return helper.getXSApplication();
    }

    @Override
    public Activity getActivity() {
        return helper.getActivity();
    }


    private void initActionBar(){
        mActionBar = getSupportActionBar();
        int flags = ActionBar.DISPLAY_HOME_AS_UP;
        int change = mActionBar.getDisplayOptions() ^ flags;

        mActionBar.setHomeAsUpIndicator(R.drawable.ic_navigation_back);
        mActionBar.setDisplayOptions(change,flags);

    }

    private void setActionBarTitle(){
        if(mTitle != null && StringUtils.isEmpty(mTitle)){
            mActionBar.setTitle(mTitle);
        }
        if(mSubTitle != null && StringUtils.isEmpty(mSubTitle)){
            mActionBar.setSubtitle(mSubTitle);
        }
    }

    //将菜单显示在ActionBar上，而不是在底部
    protected void requestActionBarMenu(){
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if(menuKeyField != null){
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config,false);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
