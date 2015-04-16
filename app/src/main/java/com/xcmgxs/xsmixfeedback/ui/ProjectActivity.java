package com.xcmgxs.xsmixfeedback.ui;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.xcmgxs.xsmixfeedback.AppContext;
import com.xcmgxs.xsmixfeedback.R;
import com.xcmgxs.xsmixfeedback.bean.Project;
import com.xcmgxs.xsmixfeedback.common.Contanst;
import com.xcmgxs.xsmixfeedback.common.UIHelper;
import com.xcmgxs.xsmixfeedback.ui.baseactivity.BaseActionBarActivity;

/**
 * 项目详情界面
 *
 * @created 2015-04-08
 * @author zhangyi
 *
 * @最后更新：
 * @更新内容：
 * @更新者：
 */
@SuppressWarnings("deprecation")
public class ProjectActivity extends BaseActionBarActivity implements View.OnClickListener {

    private static final int ACTION_LOAD_PROJECT = 0;

    private static final int ACTION_LOAD_PARENT_PROJECT = 1;

    private Project mProject;

    private String projectid;

    private ProgressBar mLoading;

    private ScrollView mContent;

    private TextView mProjectName;

    private TextView mDescription;

    private TextView mUpdateTime;

    private ImageView mFlag;

    private TextView mCreated;

    private TextView mStationNum;

    private TextView mProjectState;

    private TextView mStationType;

    private TextView mProjectManager;

    private AppContext appContext;

    private LinearLayout mLLprojectmanager;

    private LinearLayout mLLprojectinfo;

    private LinearLayout mLLprojectlogs;

    private LinearLayout mLLprojectfiles;

    private LinearLayout mLLprojectissues;



    private String url_Link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        appContext = getXSApplication();
        initView();
    }

    private void initView(){
        Intent intent = getIntent();
        mProject = (Project)intent.getSerializableExtra(Contanst.PROJECT);
        projectid = intent.getStringExtra(Contanst.PROJECTID);

        mActionBar = getSupportActionBar();
        mContent = (ScrollView)this.findViewById(R.id.project_content);
        mCreated = (TextView)this.findViewById(R.id.project_created);
        mProjectManager = (TextView)this.findViewById(R.id.project_managername);
        mProjectName = (TextView)this.findViewById(R.id.project_name);
        mProjectState = (TextView)this.findViewById(R.id.project_state);
        mStationNum = (TextView)this.findViewById(R.id.project_stationnum);
        mStationType = (TextView)this.findViewById(R.id.project_stationtype);
        mUpdateTime = (TextView)this.findViewById(R.id.project_update);
        mDescription = (TextView)this.findViewById(R.id.project_description);

        mLLprojectfiles = (LinearLayout)this.findViewById(R.id.project_files);
        mLLprojectinfo = (LinearLayout)this.findViewById(R.id.project_info);
        mLLprojectissues = (LinearLayout)this.findViewById(R.id.project_issues);
        mLLprojectlogs = (LinearLayout)this.findViewById(R.id.project_logs);
        mLLprojectmanager = (LinearLayout)this.findViewById(R.id.project_manager);

        mLLprojectmanager.setOnClickListener(this);
        mLLprojectinfo.setOnClickListener(this);
        mLLprojectissues.setOnClickListener(this);
        mLLprojectlogs.setOnClickListener(this);
        mLLprojectfiles.setOnClickListener(this);


        if(null == mProject){
            loadProject(ACTION_LOAD_PROJECT,projectid);
        } else {
            initData();
        }


    }

    private void initData(){
        mActionBar.setTitle(mProject.getName());
        mActionBar.setSubtitle(mProject.getPersons().split("-")[0]);

        mProjectName.setText(mProject.getName());
        mUpdateTime.setText(mProject.getCreateon() == null?"":mProject.getCreateon().toString());
        mDescription.setText(mProject.getEmState());
        mCreated.setText(mProject.getCreateon() == null?"":mProject.getCreateon().toString());
        mStationNum.setText(mProject.getNum().toString());
        mProjectManager.setText(mProject.getPersons().split("-")[0]);
        mProjectState.setText(mProject.getEmState());
        mStationType.setText(mProject.getType());



    }

    private void loadProject(final int action, final String projectId) {
        new AsyncTask<Void,Void,Message>(){

            @Override
            protected Message doInBackground(Void... params) {
                Message msg = new Message();
                try {
                    msg.obj = appContext.getProject(projectId);
                    msg.what = 1;
                }
                catch (Exception ex){
                    mLoading.setVisibility(View.GONE);
                    msg.what = -1;
                    msg.obj = ex;
                    ex.printStackTrace();
                }
                return msg;
            }
        };
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_project, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if(mProject == null){
            return false;
        }
        int id = item.getItemId();

        switch (id){
            case R.id.project_menu_more:
                break;
            default:break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (mProject == null ) {
            return;
        }
        int id = v.getId();
        switch (id) {
            case R.id.project_manager:
                if (mProject.getPersons() != null) {
                    //UIHelper.showUserInfoDetail(ProjectActivity.this, mProject.getOwner(), mProject.getOwner().getId());
                }
                break;
            case R.id.project_description:
                //UIHelper.showProjectReadMeActivity(ProjectActivity.this, mProject);
                break;
            case R.id.project_logs:
                //UIHelper.showProjectCodeActivity(ProjectActivity.this, mProject);
                break;
            case R.id.project_files:
                UIHelper.showProjectListActivity(ProjectActivity.this, mProject, ProjectSomeInfoListActivity.PROJECT_LIST_TYPE_LOGS);
                break;
            case R.id.project_issues:
                UIHelper.showProjectListActivity(ProjectActivity.this, mProject, ProjectSomeInfoListActivity.PROJECT_LIST_TYPE_ISSUES);
                break;
        }
    }
}
