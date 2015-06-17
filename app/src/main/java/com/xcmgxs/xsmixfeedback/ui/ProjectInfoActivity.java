package com.xcmgxs.xsmixfeedback.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xcmgxs.xsmixfeedback.AppContext;
import com.xcmgxs.xsmixfeedback.R;
import com.xcmgxs.xsmixfeedback.bean.Project;
import com.xcmgxs.xsmixfeedback.common.Contanst;
import com.xcmgxs.xsmixfeedback.ui.baseactivity.BaseActionBarActivity;
import com.xcmgxs.xsmixfeedback.widget.CustomerScrollView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ProjectInfoActivity extends BaseActionBarActivity {

    private static final int ACTION_LOAD_PROJECT = 0;

    Project mProject;

    String projectid;

    private AppContext appContext;

    @InjectView(R.id.project_detail_loading2)
    ProgressBar mProjectDetailLoading2;
    @InjectView(R.id.project_info_install)
    TextView mProjectInfoInstall;
    @InjectView(R.id.project_install)
    LinearLayout mProjectInstall;
    @InjectView(R.id.project_info_contacter)
    TextView mProjectInfoContacter;
    @InjectView(R.id.project_contacter)
    LinearLayout mProjectContacter;
    @InjectView(R.id.project_info_address)
    TextView mProjectInfoAddress;
    @InjectView(R.id.project_address)
    LinearLayout mProjectAddress;
    @InjectView(R.id.project_info_hetong)
    TextView mProjectInfoHetong;
    @InjectView(R.id.project_hetong)
    LinearLayout mProjectHetong;
    @InjectView(R.id.project_info_anzhuangz)
    TextView mProjectInfoAnzhuangz;
    @InjectView(R.id.project_anzhuangz)
    LinearLayout mProjectAnzhuangz;
    @InjectView(R.id.project_info_fache)
    TextView mProjectInfoFache;
    @InjectView(R.id.project_fache)
    LinearLayout mProjectFache;
    @InjectView(R.id.project_info_tongcang)
    TextView mProjectInfoTongcang;
    @InjectView(R.id.project_tongcang)
    LinearLayout mProjectTongcang;
    @InjectView(R.id.project_info_base)
    TextView mProjectInfoBase;
    @InjectView(R.id.project_base)
    LinearLayout mProjectBase;
    @InjectView(R.id.project_info_anzhuang)
    TextView mProjectInfoAnzhuang;
    @InjectView(R.id.project_anzhuang)
    LinearLayout mProjectAnzhuang;
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
    @InjectView(R.id.project_info_rclz)
    TextView mProjectInfoRclz;
    @InjectView(R.id.project_rclz)
    LinearLayout mProjectRclz;
    @InjectView(R.id.project_info_rcly)
    TextView mProjectInfoRcly;
    @InjectView(R.id.project_rcly)
    LinearLayout mProjectRcly;
    @InjectView(R.id.project_info_zclz)
    TextView mProjectInfoZclz;
    @InjectView(R.id.project_zclz)
    LinearLayout mProjectZclz;
    @InjectView(R.id.project_info_zcly)
    TextView mProjectInfoZcly;
    @InjectView(R.id.project_zcly)
    LinearLayout mProjectZcly;
    @InjectView(R.id.project_content)
    CustomerScrollView mProjectContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_info);
        ButterKnife.inject(this);
        initView();

    }


    private void initView() {
        Intent intent = getIntent();
        mProject = (Project) intent.getSerializableExtra(Contanst.PROJECT);
        projectid = intent.getStringExtra(Contanst.PROJECTID);

        mActionBar = getSupportActionBar();


        if (null == mProject) {
            loadProject(ACTION_LOAD_PROJECT, projectid);
        } else {
            initData();
        }


    }

    private void initData() {
        mActionBar.setTitle("项目明细");
        mActionBar.setSubtitle(mProject.getName());
        mProjectInfoAddress.setText(mProject.getAddress());
        mProjectInfoTongcang.setText(mProject.getPackagedate());
        mProjectInfoBase.setText(mProject.getBasedate());
        mProjectInfoAnzhuang.setText(mProject.getInstalldate());
        mProjectInfoAnzhuangz.setText(mProject.getInstalloverdate());
        mProjectInfoAnzhuangy.setText(mProject.getInstalloverdate2());
        mProjectInfoChuliaoz.setText(mProject.getDischargdate1());
        mProjectInfoChuliaoy.setText(mProject.getDischargdate2());
        mProjectInfoContacter.setText(mProject.getCustomer());
        mProjectInfoFache.setText(mProject.getSenddate());
        mProjectInfoQianziz.setText(mProject.getCheckdate1());
        mProjectInfoQianziy.setText(mProject.getCheckdate2());
        mProjectInfoRclz.setText(mProject.getAmount1());
        mProjectInfoRcly.setText(mProject.getAmount2());
        mProjectInfoZclz.setText(mProject.getAllamount1());
        mProjectInfoZcly.setText(mProject.getAllamount2());
        mProjectInfoTiaoshiz.setText(mProject.getDebugoverdate1());
        mProjectInfoTiaoshiy.setText(mProject.getDebugoverdate2());
        mProjectInfoFengzhuang.setText(mProject.getEnterdate1());
        mProjectInfoHetong.setText(mProject.getContactno());
        mProjectInfoInstall.setText(mProject.getPersons());

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
                    mProjectDetailLoading2.setVisibility(View.GONE);
                    msg.what = -1;
                    msg.obj = ex;
                    ex.printStackTrace();
                }
                return msg;
            }
        };
    }
}
