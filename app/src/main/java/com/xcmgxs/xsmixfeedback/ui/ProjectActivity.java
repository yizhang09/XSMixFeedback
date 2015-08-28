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

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.xcmgxs.xsmixfeedback.AppContext;
import com.xcmgxs.xsmixfeedback.R;
import com.xcmgxs.xsmixfeedback.api.XsFeedbackApi;
import com.xcmgxs.xsmixfeedback.bean.Project;
import com.xcmgxs.xsmixfeedback.bean.Stat;
import com.xcmgxs.xsmixfeedback.common.Contanst;
import com.xcmgxs.xsmixfeedback.common.UIHelper;
import com.xcmgxs.xsmixfeedback.ui.baseactivity.BaseActionBarActivity;
import com.xcmgxs.xsmixfeedback.util.JsonUtils;
import com.xcmgxs.xsmixfeedback.util.StringUtils;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

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

    private LinearLayout mLLprojectsendissues;

    private LinearLayout mLLprojectdocs;

    private ImageView mProjectStep1;
    private ImageView mProjectStep2;
    private ImageView mProjectStep3;
    private ImageView mProjectStep4;
    private ImageView mProjectStep5;
    private ImageView mProjectStep6;
    private ImageView mProjectStep7;

    private ImageView mProjectStop;

    private LinearLayout mLLProjectStopReason;

    private TextView mProjectStopReason;

    private HorizontalBarChart mBarchart;

    private PieChart mPiechart;

    private String url_Link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        appContext = getXSApplication();
        initView();
    }

    @Override
    protected void onResume() {
        loadProject(ACTION_LOAD_PROJECT,projectid);
        super.onResume();
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
        mLLprojectsendissues = (LinearLayout)this.findViewById(R.id.project_sendissues);
        mLLprojectlogs = (LinearLayout)this.findViewById(R.id.project_logs);
        mLLprojectmanager = (LinearLayout)this.findViewById(R.id.project_manager);
        mLLprojectdocs  =  (LinearLayout)this.findViewById(R.id.project_docs);
        mProjectStep1  =  (ImageView)this.findViewById(R.id.project_step1);
        mProjectStep2  =  (ImageView)this.findViewById(R.id.project_step2);
        mProjectStep3  =  (ImageView)this.findViewById(R.id.project_step3);
        mProjectStep4  =  (ImageView)this.findViewById(R.id.project_step4);
        mProjectStep5  =  (ImageView)this.findViewById(R.id.project_step5);
        mProjectStep6  =  (ImageView)this.findViewById(R.id.project_step6);
        mProjectStep7  =  (ImageView)this.findViewById(R.id.project_step7);
        mPiechart = (PieChart)this.findViewById(R.id.project_issue_piechart);
        mBarchart = (HorizontalBarChart)this.findViewById(R.id.project_issue_barchart);

        mProjectStop  =  (ImageView)this.findViewById(R.id.project_stop);
        mLLProjectStopReason  =  (LinearLayout)this.findViewById(R.id.project_layout_stopreason);
        mProjectStopReason  =  (TextView)this.findViewById(R.id.project_stopreason);

        mLLprojectmanager.setOnClickListener(this);
        mLLprojectinfo.setOnClickListener(this);
        mLLprojectissues.setOnClickListener(this);
        mLLprojectlogs.setOnClickListener(this);
        mLLprojectfiles.setOnClickListener(this);
        mLLprojectdocs.setOnClickListener(this);
        mLLprojectsendissues.setOnClickListener(this);
        //mProjectStep1.setOnClickListener(this);
        mProjectStep2.setOnClickListener(this);
        mProjectStep3.setOnClickListener(this);
        mProjectStep4.setOnClickListener(this);
        mProjectStep5.setOnClickListener(this);
        mProjectStep6.setOnClickListener(this);
        mProjectStep7.setOnClickListener(this);

        if(null == mProject){
            loadProject(ACTION_LOAD_PROJECT,projectid);
        } else {
            initData();
        }


    }

    private void initData(){
        mActionBar.setTitle(mProject.getName());
        mActionBar.setSubtitle(mProject.getManager() != null ? mProject.getManager().getName() : "");

        mProjectName.setText(mProject.getName());
        mUpdateTime.setText(mProject.getUpdatetime() == null ? "" : StringUtils.friendly_time(mProject.getUpdatetime()));
        mDescription.setText(mProject.getAddress() + mProject.getEmState());
        mCreated.setText(mProject.getCreateon() == null?"":StringUtils.getString_date(mProject.getCreateon()));
        mStationNum.setText(mProject.getNum().toString());
        mProjectManager.setText(mProject.getManager() != null ? mProject.getManager().getName() : "");
        mProjectState.setText(mProject.getState());
        mStationType.setText(mProject.getType());

        if(mProject.isStop()){
            mProjectStop.setVisibility(View.VISIBLE);
            mLLProjectStopReason.setVisibility(View.VISIBLE);
            mProjectStopReason.setText(mProject.getStopReason());
        }
        else {
            mProjectStop.setVisibility(View.GONE);
            mLLProjectStopReason.setVisibility(View.GONE);
        }


        switch (mProject.getState()) {
            case "基础未做":
                mProjectStep1.setImageResource(R.drawable.step1);
                mProjectStep2.setImageResource(R.drawable.blank);
                mProjectStep3.setImageResource(R.drawable.blank);
                mProjectStep4.setImageResource(R.drawable.blank);
                mProjectStep5.setImageResource(R.drawable.blank);
                mProjectStep6.setImageResource(R.drawable.blank);
                mProjectStep7.setImageResource(R.drawable.blank);
                break;
            case "基础制作":
                mProjectStep1.setImageResource(R.drawable.step1);
                mProjectStep2.setImageResource(R.drawable.step2);
                mProjectStep3.setImageResource(R.drawable.blank);
                mProjectStep4.setImageResource(R.drawable.blank);
                mProjectStep5.setImageResource(R.drawable.blank);
                mProjectStep6.setImageResource(R.drawable.blank);
                mProjectStep7.setImageResource(R.drawable.blank);
                break;
            case "筒仓施工":
                mProjectStep1.setImageResource(R.drawable.step1);
                mProjectStep2.setImageResource(R.drawable.step2);
                mProjectStep3.setImageResource(R.drawable.step3);
                mProjectStep4.setImageResource(R.drawable.blank);
                mProjectStep5.setImageResource(R.drawable.blank);
                mProjectStep6.setImageResource(R.drawable.blank);
                mProjectStep7.setImageResource(R.drawable.blank);
                break;
            case "正在安装":
                mProjectStep1.setImageResource(R.drawable.step1);
                mProjectStep2.setImageResource(R.drawable.step2);
                mProjectStep3.setImageResource(R.drawable.step3);
                mProjectStep4.setImageResource(R.drawable.step4);
                mProjectStep5.setImageResource(R.drawable.blank);
                mProjectStep6.setImageResource(R.drawable.blank);
                mProjectStep7.setImageResource(R.drawable.blank);
                break;
            case "安装完毕":
                mProjectStep1.setImageResource(R.drawable.step1);
                mProjectStep2.setImageResource(R.drawable.step2);
                mProjectStep3.setImageResource(R.drawable.step3);
                mProjectStep4.setImageResource(R.drawable.step4);
                mProjectStep5.setImageResource(R.drawable.step5);
                mProjectStep6.setImageResource(R.drawable.blank);
                mProjectStep7.setImageResource(R.drawable.blank);
                break;
            case "调试出料":
                mProjectStep1.setImageResource(R.drawable.step1);
                mProjectStep2.setImageResource(R.drawable.step2);
                mProjectStep3.setImageResource(R.drawable.step3);
                mProjectStep4.setImageResource(R.drawable.step4);
                mProjectStep5.setImageResource(R.drawable.step5);
                mProjectStep6.setImageResource(R.drawable.step6);
                mProjectStep7.setImageResource(R.drawable.blank);
                break;
            case "签字验收":
                mProjectStep1.setImageResource(R.drawable.step1);
                mProjectStep2.setImageResource(R.drawable.step2);
                mProjectStep3.setImageResource(R.drawable.step3);
                mProjectStep4.setImageResource(R.drawable.step4);
                mProjectStep5.setImageResource(R.drawable.step5);
                mProjectStep6.setImageResource(R.drawable.step6);
                mProjectStep7.setImageResource(R.drawable.step7);
                break;
        }
        loadPieChartData();
        loadBarChartData();

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
                    //mLoading.setVisibility(View.GONE);
                    msg.what = -1;
                    msg.obj = ex;
                    ex.printStackTrace();
                }
                return msg;
            }

            @Override
            protected void onPostExecute(Message message) {
                mProject = (Project)message.obj;
                initData();
                super.onPostExecute(message);
            }
        }.execute();
    }


    private void loadPieChartData(){
        String projectid = mProject.getId();
        XsFeedbackApi.getStatData(projectid, "response", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                List<Stat> data = JsonUtils.getList(Stat[].class, responseBody);
                if (data.size() != 0) {
                    renderPieChart(data);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void loadBarChartData(){
        String projectid = mProject.getId();
        XsFeedbackApi.getStatData(projectid, "type", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                List<Stat> data = JsonUtils.getList(Stat[].class, responseBody);
                if (data.size() != 0) {
                    renderBarChart(data);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void renderBarChart(List<Stat> data) {
        mBarchart.setDescription("");
        mBarchart.setNoDataTextDescription("无数据");
        mBarchart.setNoDataText("暂时无数据");
        mBarchart.setPinchZoom(false);
        mBarchart.animateY(2500);
        mBarchart.setScaleEnabled(false);
        mBarchart.setDoubleTapToZoomEnabled(false);
        mBarchart.setDrawGridBackground(false);
        ArrayList<BarEntry> arrayList = new ArrayList<BarEntry>();
        ArrayList<String> xVals = new ArrayList<String>();

        int amount = 0;
        for (int i=0;i<data.size();i++) {
            BarEntry entry = new BarEntry(data.get(i).getCount(), i);
            arrayList.add(entry);
            xVals.add(data.get(i).getLabel());
            amount +=data.get(i).getCount();
        }
        BarDataSet bards = new BarDataSet(arrayList,  "合计：" + amount);
        bards.setAxisDependency(YAxis.AxisDependency.LEFT);
        bards.setColors(ColorTemplate.VORDIPLOM_COLORS);
        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(bards);

        BarData bardata = new BarData(xVals, dataSets);
        mBarchart.setData(bardata);
        mBarchart.invalidate(); // refresh
    }

    private void renderPieChart(List<Stat> data) {
        mPiechart.setNoDataTextDescription("无数据");
        mPiechart.setNoDataText("暂时无数据");
        mPiechart.setDescription("");
        mPiechart.setDrawHoleEnabled(true);
        mPiechart.setHoleColorTransparent(true);
        mPiechart.setDrawCenterText(true);
        mPiechart.animateY(1500, Easing.EasingOption.EaseInOutQuad);
        ArrayList<Entry> arrayList = new ArrayList<Entry>();
        ArrayList<String> xVals = new ArrayList<String>();
        int amount = 0;
        for (int i=0;i<data.size();i++) {
            Entry entry = new Entry(data.get(i).getCount(), i);
            arrayList.add(entry);
            xVals.add(data.get(i).getLabel());
            amount +=data.get(i).getCount();
        }

        mPiechart.setCenterText("合计：" + amount);
        PieDataSet dataSetsPie = new PieDataSet(arrayList, "");
        dataSetsPie.setColors(ColorTemplate.VORDIPLOM_COLORS);
        PieData pieData = new PieData(xVals, dataSetsPie);
        mPiechart.setData(pieData);
        mPiechart.invalidate();
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
            case R.id.project_menu_create_log:
                UIHelper.showLogEditOrCreate(getXSApplication(),mProject,null);
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
                    UIHelper.showUserInfoDetail(ProjectActivity.this, mProject.getManager(), mProject.getManager().getId());
                }
                break;
            case R.id.project_info:
                UIHelper.showProjectInfo(ProjectActivity.this, mProject);
                break;
            case R.id.project_logs:
                UIHelper.showProjectListActivity(ProjectActivity.this, mProject, ProjectSomeInfoListActivity.PROJECT_LIST_TYPE_LOGS);
                break;
            case R.id.project_files:
                UIHelper.showProjectListActivity(ProjectActivity.this, mProject, ProjectSomeInfoListActivity.PROJECT_LIST_TYPE_FILES);
                break;
            case R.id.project_issues:
                UIHelper.showProjectListActivity(ProjectActivity.this, mProject, ProjectSomeInfoListActivity.PROJECT_LIST_TYPE_ISSUES);
                break;
            case R.id.project_sendissues:
                UIHelper.showProjectListActivity(ProjectActivity.this, mProject, ProjectSomeInfoListActivity.PROJECT_LIST_TYPE_SENDISSUES);
                break;
            case R.id.project_docs:
                UIHelper.showProjectListActivity(ProjectActivity.this, mProject, ProjectSomeInfoListActivity.PROJECT_LIST_TYPE_DOCS);
                break;
            case R.id.project_step1:
                UIHelper.showProjectListActivity(ProjectActivity.this, mProject, ProjectSomeInfoListActivity.PROJECT_LIST_TYPE_LOGS,1);
                break;
            case R.id.project_step2:
                UIHelper.showProjectListActivity(ProjectActivity.this, mProject, ProjectSomeInfoListActivity.PROJECT_LIST_TYPE_LOGS,2);
                break;
            case R.id.project_step3:
                UIHelper.showProjectListActivity(ProjectActivity.this, mProject, ProjectSomeInfoListActivity.PROJECT_LIST_TYPE_LOGS,3);
                break;
            case R.id.project_step4:
                UIHelper.showProjectListActivity(ProjectActivity.this, mProject, ProjectSomeInfoListActivity.PROJECT_LIST_TYPE_LOGS,4);
                break;
            case R.id.project_step5:
                UIHelper.showProjectListActivity(ProjectActivity.this, mProject, ProjectSomeInfoListActivity.PROJECT_LIST_TYPE_LOGS,5);
                break;
            case R.id.project_step6:
                UIHelper.showProjectListActivity(ProjectActivity.this, mProject, ProjectSomeInfoListActivity.PROJECT_LIST_TYPE_LOGS,6);
                break;
            case R.id.project_step7:
                UIHelper.showProjectListActivity(ProjectActivity.this, mProject, ProjectSomeInfoListActivity.PROJECT_LIST_TYPE_LOGS,7);
                break;
        }
    }
}
