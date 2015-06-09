package com.xcmgxs.xsmixfeedback.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xcmgxs.xsmixfeedback.AppContext;
import com.xcmgxs.xsmixfeedback.R;
import com.xcmgxs.xsmixfeedback.common.MethodsCompat;
import com.xcmgxs.xsmixfeedback.common.UIHelper;
import com.xcmgxs.xsmixfeedback.ui.baseactivity.BaseActionBarActivity;
import com.xcmgxs.xsmixfeedback.util.FileUtils;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SettingActivity extends BaseActionBarActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    @InjectView(R.id.cb_receive_notice)
    CheckBox mCbReceiveNotice;
    @InjectView(R.id.ll_receive_notice)
    LinearLayout mLlReceiveNotice;
    @InjectView(R.id.cb_notice_vioce)
    CheckBox mCbNoticeVioce;
    @InjectView(R.id.ll_notice_voice)
    LinearLayout mLlNoticeVoice;
    @InjectView(R.id.cb_check_update_start)
    CheckBox mCbCheckUpdateStart;
    @InjectView(R.id.ll_check_update_start)
    LinearLayout mLlCheckUpdateStart;
    @InjectView(R.id.tv_cache_size)
    TextView mTvCacheSize;
    @InjectView(R.id.ll_clear_cache)
    LinearLayout mLlClearCache;
    @InjectView(R.id.ll_feedback)
    LinearLayout mLlFeedback;
    @InjectView(R.id.ll_check_update)
    LinearLayout mLlCheckUpdate;
    @InjectView(R.id.ll_about)
    LinearLayout mLlAbout;


    private AppContext appContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.inject(this);
        initView();
    }


    private void initView() {
        appContext = AppContext.getInstance();
        mCbReceiveNotice.setChecked(appContext.isReceiveNotice());
        mCbNoticeVioce.setChecked(appContext.isVoice());
        mCbCheckUpdateStart.setChecked(appContext.isCheckUp());

        mTvCacheSize.setText(calCache());

        mCbReceiveNotice.setOnCheckedChangeListener(this);
    }

    @Override
    @OnClick({R.id.ll_receive_notice, R.id.ll_notice_voice, R.id.ll_check_update_start,
            R.id.ll_feedback, R.id.ll_clear_cache, R.id.ll_check_update, R.id.ll_about})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_receive_notice:
                updateReceiveNotice();
                break;
            case R.id.ll_notice_voice:

                updateNoticeVoice();
                break;
            case R.id.ll_check_update_start:
                updateCheckUpdateStart();
                break;
            case R.id.ll_feedback:
                onFeedBack();
                break;
            case R.id.ll_clear_cache:
                onCache();
                break;
            case R.id.ll_check_update:
                //UpdateManager.getUpdateManager().checkAppUpdate(this, true);
                break;
            case R.id.ll_about:
                showAbout();
                break;
            default:
                break;
        }
    }


    private void updateReceiveNotice() {
        if (mCbReceiveNotice.isChecked()) {
            mCbReceiveNotice.setChecked(false);
        } else {
            mCbReceiveNotice.setChecked(true);
        }
        appContext.setConfigReceiveNotice(mCbReceiveNotice.isChecked());
    }

    private void updateNoticeVoice() {
        if (mCbNoticeVioce.isChecked()) {
            mCbNoticeVioce.setChecked(false);
        } else {
            mCbNoticeVioce.setChecked(true);
        }
        appContext.setConfigReceiveNotice(mCbNoticeVioce.isChecked());
    }

    private void updateCheckUpdateStart() {
        if (mCbCheckUpdateStart.isChecked()) {
            mCbCheckUpdateStart.setChecked(false);
        } else {
            mCbCheckUpdateStart.setChecked(true);
        }
        appContext.setConfigCheckUp(mCbCheckUpdateStart.isChecked());
    }

    private void onCache() {
        UIHelper.clearAppCache(SettingActivity.this);
        mTvCacheSize.setText("OKB");
    }

    private String calCache() {
        long fileSize = 0;
        String cacheSize = "0KB";
        File filesDir = getFilesDir();
        File cacheDir = getCacheDir();

        fileSize += FileUtils.getDirSize(filesDir);
        fileSize += FileUtils.getDirSize(cacheDir);
        // 2.2版本才有将应用缓存转移到sd卡的功能
        if (AppContext.isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)) {
            File externalCacheDir = MethodsCompat.getExternalCacheDir(this);
            fileSize += FileUtils.getDirSize(externalCacheDir);
        }
        if (fileSize > 0)
            cacheSize = FileUtils.formatFileSize(fileSize);
        return cacheSize;
    }

    /**
     * 发送反馈意见到指定的邮箱
     */
    private void onFeedBack() {
        Intent i = new Intent(Intent.ACTION_SEND);
        //i.setType("text/plain"); //模拟器
        i.setType("message/rfc822"); //真机
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"zhangyi0037@qq.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "用户反馈-XsMixFeedback Android客户端");
        i.putExtra(Intent.EXTRA_TEXT, "");
        startActivity(Intent.createChooser(i, "send email to me..."));
    }

    private void showAbout() {
        Intent intent = new Intent(SettingActivity.this, AboutActivity.class);
        startActivity(intent);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.cb_receive_notice:
                appContext.setConfigReceiveNotice(isChecked);
                break;
            case R.id.cb_notice_vioce:
                appContext.setConfigVoice(isChecked);
                break;
            case R.id.cb_check_update_start:
                appContext.setConfigCheckUp(isChecked);
                break;
            default:
                break;
        }
    }

}
