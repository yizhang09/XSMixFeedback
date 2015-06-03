package com.xcmgxs.xsmixfeedback.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.xcmgxs.xsmixfeedback.AppConfig;
import com.xcmgxs.xsmixfeedback.AppContext;
import com.xcmgxs.xsmixfeedback.R;
import com.xcmgxs.xsmixfeedback.api.XsFeedbackApi;
import com.xcmgxs.xsmixfeedback.bean.Project;
import com.xcmgxs.xsmixfeedback.bean.ProjectIssue;
import com.xcmgxs.xsmixfeedback.common.Contanst;
import com.xcmgxs.xsmixfeedback.common.UIHelper;
import com.xcmgxs.xsmixfeedback.ui.baseactivity.BaseActionBarActivity;
import com.xcmgxs.xsmixfeedback.ui.dialog.LightProgressDialog;
import com.xcmgxs.xsmixfeedback.util.JsonUtils;

import org.apache.http.Header;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class IssueEditActivity extends BaseActionBarActivity {

    @InjectView(R.id.issue_edit_loading)
    ProgressBar mIssueEditLoading;
    @InjectView(R.id.issue_edit_title)
    EditText mIssueEditTitle;
    @InjectView(R.id.issue_edit_type)
    Spinner mIssueEditType;
    @InjectView(R.id.issue_edit_desc)
    EditText mIssueEditDesc;

    Project mProject;

    private static final String[] TYPES = {"缺件漏件", "其他反馈"};

    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_edit);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        mProject = (Project) getIntent().getSerializableExtra(Contanst.PROJECT);

        mActionBar.setTitle("新建问题反馈");
        mActionBar.setSubtitle(mProject.getName() + "/" + mProject.getCustomer());

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, TYPES);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mIssueEditType.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_issue_edit, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.issue_actionbar_menu_save) {
            pubIssue();
        }
        return super.onOptionsItemSelected(item);
    }


    private void pubIssue() {
        String title = mIssueEditTitle.getText().toString();
        String desc = mIssueEditDesc.getText().toString();
        String type = mIssueEditType.getSelectedItem().toString();
        final AlertDialog pubing = LightProgressDialog.create(this, "提交中...");
        XsFeedbackApi.pubCreateIssue(mProject.getId(), title, desc, type, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                ProjectIssue issue = JsonUtils.toBean(ProjectIssue.class,responseBody);
                if(issue != null){
                    UIHelper.ToastMessage(AppContext.getInstance(),"创建成功");
                    IssueEditActivity.this.finish();
                } else {
                    UIHelper.ToastMessage(AppContext.getInstance(),"创建失败");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                UIHelper.ToastMessage(AppContext.getInstance(),"创建失败");
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
}
