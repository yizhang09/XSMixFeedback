package com.xcmgxs.xsmixfeedback.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xcmgxs.xsmixfeedback.R;
import com.xcmgxs.xsmixfeedback.ui.baseactivity.BaseActionBarActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ProjectDocActivity extends BaseActionBarActivity implements View.OnClickListener {

    @InjectView(R.id.project_doc_uploader_1)
    TextView mProjectDocUploader1;
    @InjectView(R.id.project_doc_1)
    LinearLayout mProjectDoc1;
    @InjectView(R.id.project_doc_uploader_2)
    TextView mProjectDocUploader2;
    @InjectView(R.id.project_doc_2)
    LinearLayout mProjectDoc2;
    @InjectView(R.id.project_doc_uploader_3)
    TextView mProjectDocUploader3;
    @InjectView(R.id.project_doc_3)
    LinearLayout mProjectDoc3;
    @InjectView(R.id.project_doc_uploader_4)
    TextView mProjectDocUploader4;
    @InjectView(R.id.project_doc_4)
    LinearLayout mProjectDoc4;
    @InjectView(R.id.project_doc_uploader_5)
    TextView mProjectDocUploader5;
    @InjectView(R.id.project_doc_5)
    LinearLayout mProjectDoc5;
    @InjectView(R.id.project_doc_uploader_6)
    TextView mProjectDocUploader6;
    @InjectView(R.id.project_doc_6)
    LinearLayout mProjectDoc6;
    @InjectView(R.id.project_doc_uploader_7)
    TextView mProjectDocUploader7;
    @InjectView(R.id.project_doc_7)
    LinearLayout mProjectDoc7;
    @InjectView(R.id.project_doc_uploader_8)
    TextView mProjectDocUploader8;
    @InjectView(R.id.project_doc_8)
    LinearLayout mProjectDoc8;
    @InjectView(R.id.project_doc_uploader_10)
    TextView mProjectDocUploader10;
    @InjectView(R.id.project_doc_10)
    LinearLayout mProjectDoc10;
    @InjectView(R.id.project_doc_uploader_9)
    TextView mProjectDocUploader9;
    @InjectView(R.id.project_doc_9)
    LinearLayout mProjectDoc9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_doc);
        ButterKnife.inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_project_doc, menu);
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
        
    }
}
