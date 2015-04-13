package com.xcmgxs.xsmixfeedback.ui;

import android.app.Activity;
import android.os.Bundle;

import com.xcmgxs.xsmixfeedback.AppContext;
import com.xcmgxs.xsmixfeedback.interfaces.ActivityHelperInterface;

/**
 * Created by zhangyi on 2015-4-8.
 */
public class ActivityHelper implements ActivityHelperInterface {

    Activity mActivity;

    public ActivityHelper(Activity activity){
        this.mActivity = activity;
    }

    public void onCreate(Bundle savedInstanceState){

    }

    public void onAttachedToWindow(){

    }

    @Override
    public AppContext getXSApplication() {
        return (AppContext)mActivity.getApplication();
    }

    @Override
    public Activity getActivity() {
        return mActivity;
    }
}
