package com.xcmgxs.xsmixfeedback.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
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
import com.xcmgxs.xsmixfeedback.bean.Stat;
import com.xcmgxs.xsmixfeedback.common.UIHelper;
import com.xcmgxs.xsmixfeedback.ui.baseactivity.BaseActionBarActivity;
import com.xcmgxs.xsmixfeedback.ui.dialog.LightProgressDialog;
import com.xcmgxs.xsmixfeedback.util.JsonUtils;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ProjectStatActivity extends BaseActionBarActivity implements View.OnClickListener {


    @InjectView(R.id.projectstat_year)
    TextView mProjectstatTextyear;
    @InjectView(R.id.projectstat_amount)
    TextView mTextViewAmount;
    @InjectView(R.id.projectstat_chartstate)
    BarChart mStateChart;
    @InjectView(R.id.projectstat_state)
    LinearLayout mProjectstatState;
    @InjectView(R.id.projectstat_chartresp)
    PieChart mRespChart;
    @InjectView(R.id.projectstat_resp)
    LinearLayout mProjectstatResp;
    @InjectView(R.id.projectstat_charttype)
    HorizontalBarChart mTypeChart;
    @InjectView(R.id.projectstat_type)
    LinearLayout mProjectstatType;

    private AppContext mContext;

    private int currentYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_stat);
        ButterKnife.inject(this);
        mContext = getXSApplication();
        initView();
    }

    private void initView() {
        mProjectstatTextyear.setOnClickListener(this);
        mProjectstatResp.setOnClickListener(this);
        mProjectstatType.setOnClickListener(this);
        mProjectstatState.setOnClickListener(this);
        initData();
    }

    private void initData() {
        Calendar cal = Calendar.getInstance();
        currentYear = cal.get(Calendar.YEAR);
        mProjectstatTextyear.setText(String.valueOf(currentYear));
        loadRespChartData(currentYear);
        loadStateChartData(currentYear);
        loadTypeChartData(currentYear);

    }


    private void loadRespChartData(int year){
        XsFeedbackApi.getStatData(year, "response", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                List<Stat> data = JsonUtils.getList(Stat[].class, responseBody);
                if (data.size() != 0) {
                    renderRespChart(data);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void loadTypeChartData(int year){
        XsFeedbackApi.getStatData(year, "type", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                List<Stat> data = JsonUtils.getList(Stat[].class, responseBody);
                if (data.size() != 0) {
                    renderTypeChart(data);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void loadStateChartData(int year){
        final AlertDialog pubing = LightProgressDialog.create(this, "请稍候...");
        XsFeedbackApi.getStatData(year, "state", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                List<Stat> data = JsonUtils.getList(Stat[].class, responseBody);
                if (data.size() != 0) {
                    renderStateChart(data);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }

            @Override
            public void onStart() {
                super.onStart();
                pubing.show();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                pubing.dismiss();
            }
        });
    }

    private void renderTypeChart(List<Stat> data) {
        mTypeChart.setDescription("");
        mTypeChart.setNoDataTextDescription("无数据");
        mTypeChart.setNoDataText("暂时无数据");
        mTypeChart.setPinchZoom(false);
        mTypeChart.animateY(2500);
        mTypeChart.setScaleEnabled(false);
        mTypeChart.setDoubleTapToZoomEnabled(false);
        mTypeChart.setDrawGridBackground(false);
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
        mTypeChart.setData(bardata);
        mTypeChart.invalidate(); // refresh
    }

    private void renderStateChart(List<Stat> data) {
        mStateChart.setDescription("");
        mStateChart.setNoDataTextDescription("无数据");
        mStateChart.setNoDataText("暂时无数据");
        mStateChart.setPinchZoom(false);
        mStateChart.animateY(2500);
        mStateChart.setScaleEnabled(false);
        mStateChart.setDoubleTapToZoomEnabled(false);
        mStateChart.setDrawGridBackground(false);
        ArrayList<BarEntry> arrayList = new ArrayList<BarEntry>();
        ArrayList<String> xVals = new ArrayList<String>();
        int amount = 0;
        for (int i=0;i<data.size();i++) {
            BarEntry entry = new BarEntry(data.get(i).getCount(), i);
            arrayList.add(entry);
            xVals.add(data.get(i).getLabel());
            amount +=data.get(i).getCount();
        }
        BarDataSet bards = new BarDataSet(arrayList, "合计：" + amount);
        mTextViewAmount.setText(String.valueOf(amount));
        bards.setAxisDependency(YAxis.AxisDependency.LEFT);
        bards.setColors(ColorTemplate.VORDIPLOM_COLORS);
        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(bards);

        BarData bardata = new BarData(xVals, dataSets);
        mStateChart.setData(bardata);
        mStateChart.invalidate(); // refresh
    }

    private void renderRespChart(List<Stat> data) {
        mRespChart.setNoDataTextDescription("无数据");
        mRespChart.setNoDataText("暂时无数据");
        mRespChart.setDescription("");
        mRespChart.setDrawHoleEnabled(true);
        mRespChart.setHoleColorTransparent(true);
        mRespChart.setDrawCenterText(true);
        mRespChart.animateY(1500, Easing.EasingOption.EaseInOutQuad);
        ArrayList<Entry> arrayList = new ArrayList<Entry>();
        ArrayList<String> xVals = new ArrayList<String>();
        int amount = 0;
        for (int i=0;i<data.size();i++) {
            Entry entry = new Entry(data.get(i).getCount(), i);
            arrayList.add(entry);
            xVals.add(data.get(i).getLabel());
            amount +=data.get(i).getCount();
        }

        mRespChart.setCenterText("合计：" + amount);
        PieDataSet dataSetsPie = new PieDataSet(arrayList, "");
        dataSetsPie.setColors(ColorTemplate.VORDIPLOM_COLORS);
        PieData pieData = new PieData(xVals, dataSetsPie);
        mRespChart.setData(pieData);
        mRespChart.invalidate();
    }


    public void showYearPicker() {
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_pickyear, (ViewGroup) findViewById(R.id.dialog_yearpicker));
        final NumberPicker picker = (NumberPicker) v.findViewById(R.id.numberPicker1);
        final CheckBox checkAll = (CheckBox) v.findViewById(R.id.checkall);
        picker.setMaxValue(2050);
        picker.setMinValue(2013);
        picker.setValue(2015);
        picker.setWrapSelectorWheel(false);
        new AlertDialog.Builder(this).setTitle("选择年度").setView(v)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (checkAll.isChecked()) {
                            mProjectstatTextyear.setText("全部");
                            currentYear = -1;
                            loadRespChartData(currentYear);
                            loadStateChartData(currentYear);
                            loadTypeChartData(currentYear);
                        } else {
                            currentYear = picker.getValue();
                            mProjectstatTextyear.setText(String.valueOf(currentYear));
                            loadRespChartData(currentYear);
                            loadStateChartData(currentYear);
                            loadTypeChartData(currentYear);
                        }
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.projectstat_year:
                showYearPicker();
                break;
            case R.id.projectstat_state:
                UIHelper.showProjectStateActivity(ProjectStatActivity.this,currentYear);
            default:
                break;
        }
    }
}
