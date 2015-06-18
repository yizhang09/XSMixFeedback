package com.xcmgxs.xsmixfeedback.ui;

import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.xcmgxs.xsmixfeedback.AppContext;
import com.xcmgxs.xsmixfeedback.R;
import com.xcmgxs.xsmixfeedback.common.DoubleClickExitHelper;
import com.xcmgxs.xsmixfeedback.ui.fragments.DrawerNavigationMenu;
import com.xcmgxs.xsmixfeedback.ui.fragments.ExploreViewPagerFragment;
import com.xcmgxs.xsmixfeedback.ui.fragments.ReportListProjectFragment;

public class ProjectReportListActivity extends ActionBarActivity {

    private AppContext mContext;
    private FragmentManager mFragmentManager;
    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_report_list);
        mContext = (AppContext)getApplicationContext();
        initView();
    }


    private void initView() {
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.projectreport_list, ReportListProjectFragment.newInstance(), null).commit();
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setTitle("项目进度");

    }
}
