package com.xcmgxs.xsmixfeedback.ui;

import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.xcmgxs.xsmixfeedback.AppContext;
import com.xcmgxs.xsmixfeedback.R;
import com.xcmgxs.xsmixfeedback.bean.Project;
import com.xcmgxs.xsmixfeedback.common.Contanst;
import com.xcmgxs.xsmixfeedback.common.UIHelper;
import com.xcmgxs.xsmixfeedback.ui.baseactivity.BaseActionBarActivity;
import com.xcmgxs.xsmixfeedback.ui.fragments.ProjectDocListFragment;
import com.xcmgxs.xsmixfeedback.ui.fragments.ProjectFileListFragment;
import com.xcmgxs.xsmixfeedback.ui.fragments.ProjectIssueListFragment;
import com.xcmgxs.xsmixfeedback.ui.fragments.ProjectLogListFragment;
import com.xcmgxs.xsmixfeedback.ui.fragments.ProjectSendIssueListFragment;

/**
 * Created by zhangyi on 2015-4-16.
 */
public class ProjectSomeInfoListActivity extends BaseActionBarActivity {

    public final static int PROJECT_LIST_TYPE_ISSUES = 0;

    public final static int PROJECT_LIST_TYPE_LOGS = 1;

    public final static int PROJECT_LIST_TYPE_FILES = 2;

    public final static int ALL_LIST_TYPE_ISSUES = 3;

    public final static int ALL_LIST_TYPE_LOGS = 4;

    public final static int ALL_LIST_TYPE_FILES = 5;

    public final static int PROJECT_LIST_TYPE_DOCS = 6;

    public final static int PROJECT_LIST_TYPE_SENDISSUES = 7;

    public final static int ALL_LIST_TYPE_SENDISSUES = 8;

    private final int MENU_CREATE_LOG_ID = 0;

    private final int MENU_CREATE_ISSUE_ID = 1;

    private final int MENU_CREATE_DOC_ID = 2;

    private final int MENU_CREATE_SENDISSUE_ID = 3;

    private FragmentManager fragmentManager;

    private Bundle mSavedInstanceState;

    private Project mProject;

    private ProgressBar mProgressBar;

    private AppContext mAppContext;

    private int mListType;

    private int mProjectState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity_fragment);
        mAppContext = getXSApplication();
        this.mSavedInstanceState = savedInstanceState;
        initView();
    }

    private void initView(){
        fragmentManager = getSupportFragmentManager();

        Intent intent = getIntent();
        if(intent != null){
            mProject = (Project)intent.getSerializableExtra(Contanst.PROJECT);
            mListType = intent.getIntExtra("project_list_type", 0);
            mProjectState = intent.getIntExtra("project_list_state", 0);
            mTitle = getTitle(mListType);
            if(mProject != null) {
                mSubTitle = mProject.getName();
            }
            else {
                mSubTitle = "所有项目";
            }
        }

        mProgressBar = (ProgressBar)findViewById(R.id.content_loading);

        mActionBar.setTitle(mTitle);
        mActionBar.setSubtitle(mSubTitle);


        if(null == mSavedInstanceState){
            setFragment(mListType);
        }
    }

    private String getTitle(int type){
        String title = "";
        switch (type){
            case PROJECT_LIST_TYPE_LOGS:
                title = "日志列表";
                break;
            case  PROJECT_LIST_TYPE_ISSUES:
                title = "问题列表";
                break;
            case  PROJECT_LIST_TYPE_FILES:
                title = "文件列表";
                break;
            case  PROJECT_LIST_TYPE_SENDISSUES:
                title = "问题列表";
                break;
            case ALL_LIST_TYPE_LOGS:
                title = "最近日志";
                break;
            case  ALL_LIST_TYPE_ISSUES:
                title = "最近问题";
                break;
            case  ALL_LIST_TYPE_FILES:
                title = "最近文件";
                break;
            case  PROJECT_LIST_TYPE_DOCS:
                title = "单据列表";
                break;

        }
        return title;
    }

    private void setFragment(int type){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(type == PROJECT_LIST_TYPE_ISSUES){
            fragmentTransaction.replace(R.id.content, ProjectIssueListFragment.newInstance(mProject)).commit();
        }
        if(type == PROJECT_LIST_TYPE_LOGS){
            fragmentTransaction.replace(R.id.content, ProjectLogListFragment.newInstance(mProject,mProjectState)).commit();
        }
        if(type == PROJECT_LIST_TYPE_FILES){
            fragmentTransaction.replace(R.id.content, ProjectFileListFragment.newInstance(mProject)).commit();
        }
        if(type == ALL_LIST_TYPE_ISSUES){
            fragmentTransaction.replace(R.id.content, ProjectIssueListFragment.newInstance()).commit();
        }
        if(type == ALL_LIST_TYPE_LOGS){
            fragmentTransaction.replace(R.id.content, ProjectLogListFragment.newInstance()).commit();
        }
        if(type == ALL_LIST_TYPE_FILES){
            fragmentTransaction.replace(R.id.content, ProjectFileListFragment.newInstance()).commit();
        }
        if(type == PROJECT_LIST_TYPE_DOCS){
            fragmentTransaction.replace(R.id.content, ProjectDocListFragment.newInstance(mProject)).commit();
        }
        if(type == PROJECT_LIST_TYPE_SENDISSUES){
            fragmentTransaction.replace(R.id.content, ProjectSendIssueListFragment.newInstance(mProject)).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(mProject != null) {
            if (mListType == PROJECT_LIST_TYPE_LOGS) {
                MenuItem createOption = menu.add(0, MENU_CREATE_LOG_ID, MENU_CREATE_LOG_ID, "创建日志");
                createOption.setIcon(R.drawable.action_create);
                MenuItemCompat.setShowAsAction(createOption, MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
            }
            if (mListType == PROJECT_LIST_TYPE_ISSUES) {
                MenuItem createOption = menu.add(0, MENU_CREATE_ISSUE_ID, MENU_CREATE_ISSUE_ID, "创建质量反馈");
                createOption.setIcon(R.drawable.action_create);
                MenuItemCompat.setShowAsAction(createOption, MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
            }
            if (mListType == PROJECT_LIST_TYPE_DOCS) {
                MenuItem createOption = menu.add(0, MENU_CREATE_DOC_ID, MENU_CREATE_DOC_ID, "上传单据");
                createOption.setIcon(R.drawable.action_create);
                MenuItemCompat.setShowAsAction(createOption, MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
            }
            if (mListType == PROJECT_LIST_TYPE_SENDISSUES) {
                MenuItem createOption = menu.add(0, MENU_CREATE_SENDISSUE_ID, MENU_CREATE_SENDISSUE_ID, "创建发运问题");
                createOption.setIcon(R.drawable.action_create);
                MenuItemCompat.setShowAsAction(createOption, MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
            }
        }
//        if (mListType == PROJECT_LIST_TYPE_LOGS) {
//            MenuItem createOption = menu.add(0, MENU_CREATE_ID, MENU_CREATE_ID, "创建日志");
//            createOption.setIcon(R.drawable.action_create);
//            MenuItemCompat.setShowAsAction(createOption, MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
//        }

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case MENU_CREATE_LOG_ID:
                UIHelper.showLogEditOrCreate(getXSApplication(),mProject,null);
                break;
            case MENU_CREATE_ISSUE_ID:
                UIHelper.showIssueEditOrCreate(getXSApplication(), mProject, null);
                break;
            case MENU_CREATE_DOC_ID:
                UIHelper.showDocEditOrCreate(getXSApplication(), mProject, null);
                break;
            case MENU_CREATE_SENDISSUE_ID:
                UIHelper.showSendIssueEditOrCreate(getXSApplication(),mProject,null);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
