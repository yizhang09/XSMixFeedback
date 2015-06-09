package com.xcmgxs.xsmixfeedback.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.xcmgxs.xsmixfeedback.R;
import com.xcmgxs.xsmixfeedback.adapter.MyProjectListAdapter;
import com.xcmgxs.xsmixfeedback.adapter.ProjectAdapter;
import com.xcmgxs.xsmixfeedback.api.XsFeedbackApi;
import com.xcmgxs.xsmixfeedback.bean.Project;
import com.xcmgxs.xsmixfeedback.common.UIHelper;
import com.xcmgxs.xsmixfeedback.ui.baseactivity.BaseActionBarActivity;
import com.xcmgxs.xsmixfeedback.util.JsonUtils;
import com.xcmgxs.xsmixfeedback.widget.EnhanceListView;
import com.xcmgxs.xsmixfeedback.widget.TipInfoLayout;

import org.apache.http.Header;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SearchActivity extends BaseActionBarActivity implements SearchView.OnQueryTextListener,AdapterView.OnItemClickListener {

    @InjectView(R.id.search_view)
    SearchView mSearchView;

    @InjectView(R.id.listView)
    EnhanceListView mListView;

    @InjectView(R.id.tip_info)
    TipInfoLayout mTipInfo;

    @InjectView(R.id.search_content)
    FrameLayout mSearchContent;


    private InputMethodManager imm;

    private ProjectAdapter adapter;

    private String mKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.inject(this);
        initView();
        setupList();
    }

    private void initView(){
        imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setIconifiedByDefault(false);
        mTipInfo.setHiden();
        mTipInfo.setOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load(mKey, 1);
            }
        });
    }

    private void setupList(){

        adapter = new ProjectAdapter(this,R.layout.exploreproject_listitem);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(this);
        mListView.setPageSize(15);
        mListView.setOnLoadMoreListener(new EnhanceListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore(int pageNum, int pageSize) {
                load(mKey, pageNum);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Project p = adapter.getItem(position);
        if (p != null) {
            UIHelper.showProjectDetail(this, p, p.getId());
        }
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        mKey = s;
        adapter.clear();
        load(s, 1);
        imm.hideSoftInputFromWindow(mListView.getWindowToken(), 0);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return true;
    }


    private void load(final String key, final int page) {
        XsFeedbackApi.searchProjects(key, page, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                List<Project> projects = JsonUtils.getList(Project[].class, responseBody);
                mTipInfo.setHiden();
                if (projects.size() > 0) {
                    adapter.addItem(projects);
                    mListView.setVisibility(View.VISIBLE);
                } else {
                    if (page == 1 || page == 0) {
                        mListView.setVisibility(View.GONE);
                        mTipInfo.setEmptyData("未找到相关的项目");
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                mTipInfo.setLoadError();
            }

            @Override
            public void onStart() {
                super.onStart();
                if (page != 0 && page != 1) {

                } else {
                    mTipInfo.setLoading();
                    mListView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        });
    }
}
