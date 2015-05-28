package com.xcmgxs.xsmixfeedback.common;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.widget.Toast;

import com.xcmgxs.xsmixfeedback.AppManager;
import com.xcmgxs.xsmixfeedback.R;

/**
 * Created by zhangyi on 2015-3-19.
 */
public class DoubleClickExitHelper {

    private final Activity mActivity;

    private boolean isOnKeyBacking;
    private Handler mHandler;
    private Toast mBackToast;

    public DoubleClickExitHelper(Activity activity){
        this.mActivity = activity;
        mHandler = new Handler(Looper.getMainLooper());
    }

    public boolean onKeyDown(int keyCode,KeyEvent keyEvent){
        if(keyCode != keyEvent.KEYCODE_BACK){
            return false;
        }

        if(isOnKeyBacking){
            mHandler.removeCallbacks(onBackTimeRunnable);
            if(mBackToast != null){
                mBackToast.cancel();
            }
            mActivity.finish();
            AppManager.getAppManager().finishAllActivity();
            return true;
        }
        else {
            isOnKeyBacking = true;
            if(mBackToast == null){
                mBackToast = Toast.makeText(mActivity, R.string.back_exit_tips ,Toast.LENGTH_LONG);
            }
            mBackToast.show();
            mHandler.postDelayed(onBackTimeRunnable, 2000);
            return true;
        }
    }

    private Runnable onBackTimeRunnable = new Runnable() {
        @Override
        public void run() {
            isOnKeyBacking = false;
            if(mBackToast != null){
                mBackToast.cancel();
            }
        }
    };

}
