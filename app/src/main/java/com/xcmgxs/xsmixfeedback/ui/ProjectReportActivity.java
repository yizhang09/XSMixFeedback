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
import android.widget.TextView;


import com.github.mikephil.charting.animation.Easing;
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
import com.xcmgxs.xsmixfeedback.widget.CustomerScrollView;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ProjectReportActivity extends BaseActionBarActivity implements View.OnClickListener {


    private static final int ACTION_LOAD_PROJECT = 0;

    private static final int ACTION_LOAD_PARENT_PROJECT = 1;

    @InjectView(R.id.project_name)
    TextView mProjectName;
    @InjectView(R.id.project_flag)
    ImageView mProjectFlag;
    @InjectView(R.id.project_description)
    TextView mProjectDescription;
    @InjectView(R.id.project_step)
    ImageView mProjectStep;
    @InjectView(R.id.project_issue_piechart)
    PieChart mPiechart;
    @InjectView(R.id.project_issue_barchart)
    HorizontalBarChart mBarchart;
    @InjectView(R.id.project_info_tongcang)
    TextView mProjectInfoTongcang;
    @InjectView(R.id.project_tongcang)
    LinearLayout mProjectTongcang;
    @InjectView(R.id.project_info_base)
    TextView mProjectInfoBase;
    @InjectView(R.id.project_base)
    LinearLayout mProjectBase;
    @InjectView(R.id.project_info_fache)
    TextView mProjectInfoFache;
    @InjectView(R.id.project_fache)
    LinearLayout mProjectFache;
    @InjectView(R.id.project_info_anzhuang)
    TextView mProjectInfoAnzhuang;
    @InjectView(R.id.project_anzhuang)
    LinearLayout mProjectAnzhuang;
    @InjectView(R.id.project_info_anzhuangz)
    TextView mProjectInfoAnzhuangz;
    @InjectView(R.id.project_anzhuangz)
    LinearLayout mProjectAnzhuangz;
    @InjectView(R.id.project_info_anzhuangy)
    TextView mProjectInfoAnzhuangy;
    @InjectView(R.id.project_anzhuangy)
    LinearLayout mProjectAnzhuangy;
    @InjectView(R.id.project_info_tiaoshiz)
    TextView mProjectInfoTiaoshiz;
    @InjectView(R.id.project_tiaoshiz)
    LinearLayout mProjectTiaoshiz;
    @InjectView(R.id.project_info_tiaoshiy)
    TextView mProjectInfoTiaoshiy;
    @InjectView(R.id.project_tiaoshiy)
    LinearLayout mProjectTiaoshiy;
    @InjectView(R.id.project_info_fengzhuang)
    TextView mProjectInfoFengzhuang;
    @InjectView(R.id.project_fengzhuang)
    LinearLayout mProjectFengzhuang;
    @InjectView(R.id.project_info_chuliaoz)
    TextView mProjectInfoChuliaoz;
    @InjectView(R.id.project_chuliaoz)
    LinearLayout mProjectChuliaoz;
    @InjectView(R.id.project_info_chuliaoy)
    TextView mProjectInfoChuliaoy;
    @InjectView(R.id.project_chuliaoy)
    LinearLayout mProjectChuliaoy;
    @InjectView(R.id.project_info_qianziy)
    TextView mProjectInfoQianziy;
    @InjectView(R.id.project_qianziy)
    LinearLayout mProjectQianziy;
    @InjectView(R.id.project_info_qianziz)
    TextView mProjectInfoQianziz;
    @InjectView(R.id.project_qianziz)
    LinearLayout mProjectQianziz;
    @InjectView(R.id.project_content)
    CustomerScrollView mProjectContent;

    @InjectView(R.id.project_report_issuelayout)
    LinearLayout mProjectIssueLayout;

    private Project mProject;

    private String projectid;

    private AppContext appContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_report);
        ButterKnife.inject(this);
        appContext = getXSApplication();
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        mProject = (Project) intent.getSerializableExtra(Contanst.PROJECT);
        projectid = intent.getStringExtra(Contanst.PROJECTID);

        if (null == mProject) {
            loadProject(ACTION_LOAD_PROJECT, projectid);
        } else {
            initData();
        }

    }


    private void initData() {
        mActionBar.setTitle(mProject.getName());
        mActionBar.setSubtitle(mProject.getManager() != null ? mProject.getManager().getName() : "");
        mProjectName.setText(mProject.getName());
        if(!StringUtils.isEmpty(mProject.getAddress() + mProject.getEmState())) {
            mProjectDescription.setText(mProject.getAddress() + mProject.getEmState());
        } else {
            mProjectDescription.setText("暂无项目介绍");
        }
        mProjectInfoTongcang.setText(mProject.getPackagedate());
        mProjectInfoBase.setText(mProject.getBasedate());
        mProjectInfoAnzhuang.setText(mProject.getInstalldate());
        mProjectInfoAnzhuangz.setText(mProject.getInstalloverdate());
        mProjectInfoAnzhuangy.setText(mProject.getInstalloverdate2());
        mProjectInfoChuliaoz.setText(mProject.getDischargdate1());
        mProjectInfoChuliaoy.setText(mProject.getDischargdate2());
        mProjectInfoFache.setText(mProject.getSenddate());
        mProjectInfoQianziz.setText(mProject.getCheckdate1());
        mProjectInfoQianziy.setText(mProject.getCheckdate2());
        mProjectInfoTiaoshiz.setText(mProject.getDebugoverdate1());
        mProjectInfoTiaoshiy.setText(mProject.getDebugoverdate2());
        mProjectInfoFengzhuang.setText(mProject.getEnterdate1());
        mProjectStep.setOnClickListener(this);
        mProjectIssueLayout.setOnClickListener(this);

        switch (mProject.getState()) {
            case "基础未做":
                mProjectStep.setImageResource(R.drawable.s1);
                break;
            case "基础制作":
                mProjectStep.setImageResource(R.drawable.s2);
                break;
            case "筒仓施工":
                mProjectStep.setImageResource(R.drawable.s3);
                break;
            case "正在安装":
                mProjectStep.setImageResource(R.drawable.s4);
                break;
            case "安装完毕":
                mProjectStep.setImageResource(R.drawable.s5);
                break;
            case "调试出料":
                mProjectStep.setImageResource(R.drawable.s6);
                break;
            case "签字验收":
                mProjectStep.setImageResource(R.drawable.s7);
                break;
        }
        loadPieChartData();
        loadBarChartData();
    }


    private void loadProject(final int action, final String projectId) {
        new AsyncTask<Void, Void, Message>() {

            @Override
            protected Message doInBackground(Void... params) {
                Message msg = new Message();
                try {
                    msg.obj = appContext.getProject(projectId);
                    msg.what = 1;
                } catch (Exception ex) {
                    msg.what = -1;
                    msg.obj = ex;
                    ex.printStackTrace();
                }
                return msg;
            }

            @Override
            protected void onPostExecute(Message message) {
                mProject = (Project) message.obj;
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
        getMenuInflater().inflate(R.menu.menu_project_report, menu);
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
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.project_step:
                UIHelper.showProjectListActivity(ProjectReportActivity.this, mProject, ProjectSomeInfoListActivity.PROJECT_LIST_TYPE_LOGS);
                break;
            case R.id.project_report_issuelayout:
                UIHelper.showProjectListActivity(ProjectReportActivity.this, mProject, ProjectSomeInfoListActivity.PROJECT_LIST_TYPE_ISSUES);
                break;
        }
    }
}
