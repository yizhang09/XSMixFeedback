package com.xcmgxs.xsmixfeedback.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.xcmgxs.xsmixfeedback.AppContext;
import com.xcmgxs.xsmixfeedback.R;
import com.xcmgxs.xsmixfeedback.bean.Project;
import com.xcmgxs.xsmixfeedback.common.Contanst;
import com.xcmgxs.xsmixfeedback.ui.baseactivity.BaseActionBarActivity;
import com.xcmgxs.xsmixfeedback.widget.CustomerScrollView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ProjectReportActivity extends BaseActionBarActivity {


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
    BarChart mBarchart;
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
        mProjectDescription.setText(mProject.getAddress() + mProject.getEmState());
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

        switch (mProject.getState()) {
            case "基础未做":
                mProjectStep.setImageResource(R.drawable.step1);
                break;
            case "基础制作":
                mProjectStep.setImageResource(R.drawable.step2);
                break;
            case "筒仓施工":
                mProjectStep.setImageResource(R.drawable.step3);
                break;
            case "正在发车":
                mProjectStep.setImageResource(R.drawable.step4);
                break;
            case "正在安装":
                mProjectStep.setImageResource(R.drawable.step5);
                break;
            case "安装完毕":
                mProjectStep.setImageResource(R.drawable.step6);
                break;
            case "签字验收":
                mProjectStep.setImageResource(R.drawable.step7);
                break;
        }

        mPiechart.setDescription("质量分类汇总");
        mPiechart.setNoDataTextDescription("无数据");
        mBarchart.setDescription("质量月报");
        mBarchart.setNoDataTextDescription("无数据");

        ArrayList<BarEntry> valsComp1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> valsComp2 = new ArrayList<BarEntry>();
        BarEntry c1e1 = new BarEntry(100.000f, 0); // 0 == quarter 1
        valsComp1.add(c1e1);
        BarEntry c1e2 = new BarEntry(50.000f, 1); // 1 == quarter 2 ...
        valsComp1.add(c1e2);
        BarEntry c1e3 = new BarEntry(100.000f, 2); // 2 == quarter 3
        valsComp1.add(c1e3);
        BarEntry c1e4 = new BarEntry(50.000f, 3); // 3 == quarter 4 ...
        valsComp1.add(c1e4);

        BarEntry c2e1 = new BarEntry(120.000f, 0); // 0 == quarter 1
        valsComp2.add(c2e1);
        BarEntry c2e2 = new BarEntry(110.000f, 1); // 1 == quarter 2 ...
        valsComp2.add(c2e2);
        BarEntry c2e3 = new BarEntry(120.000f, 2); // 2 == quarter 3
        valsComp2.add(c2e3);
        BarEntry c2e4 = new BarEntry(110.000f, 3); // 3 == quarter 4 ...
        valsComp2.add(c2e4);

        BarDataSet setComp1 = new BarDataSet(valsComp1, "Company 1");
        setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);
        setComp1.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        BarDataSet setComp2 = new BarDataSet(valsComp2, "Company 2");
        setComp2.setAxisDependency(YAxis.AxisDependency.LEFT);
        setComp2.setColor(ColorTemplate.VORDIPLOM_COLORS[1]);
        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(setComp1);
        dataSets.add(setComp2);



        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("1.Q");
        xVals.add("2.Q");
        xVals.add("3.Q");
        xVals.add("4.Q");

        BarData data = new BarData(xVals, dataSets);
        mBarchart.setData(data);
        mBarchart.invalidate(); // refresh

        ArrayList<Entry> arrayList = new ArrayList<Entry>();
        Entry entry1 = new Entry(100.000f, 0); // 0 == quarter 1
        arrayList.add(entry1);
        Entry entry2 = new Entry(50.000f, 1); // 1 == quarter 2 ...
        arrayList.add(entry2);
        Entry entry3 = new Entry(100.000f, 2); // 2 == quarter 3
        arrayList.add(entry3);
        Entry entry4 = new Entry(50.000f, 3); // 3 == quarter 4 ...
        arrayList.add(entry4);

        PieDataSet dataSetsPie = new PieDataSet(arrayList,"Company 1");
        dataSetsPie.setColors(ColorTemplate.VORDIPLOM_COLORS);
        //dataSetsPie;
        PieData pieData = new PieData(xVals,dataSetsPie);
        mPiechart.setData(pieData);
        mPiechart.invalidate();



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
}
