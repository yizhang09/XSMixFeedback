package com.xcmgxs.xsmixfeedback.ui.fragments;

import android.os.Bundle;
import android.widget.BaseAdapter;

import com.xcmgxs.xsmixfeedback.AppException;
import com.xcmgxs.xsmixfeedback.R;
import com.xcmgxs.xsmixfeedback.adapter.ProjectIssueListAdapter;
import com.xcmgxs.xsmixfeedback.api.ApiClient;
import com.xcmgxs.xsmixfeedback.api.URLs;
import com.xcmgxs.xsmixfeedback.bean.CommonList;
import com.xcmgxs.xsmixfeedback.bean.MessageData;
import com.xcmgxs.xsmixfeedback.bean.Project;
import com.xcmgxs.xsmixfeedback.bean.ProjectIssue;
import com.xcmgxs.xsmixfeedback.bean.ProjectLog;
import com.xcmgxs.xsmixfeedback.common.Contanst;
import com.xcmgxs.xsmixfeedback.ui.ImagePreviewActivity;
import com.xcmgxs.xsmixfeedback.ui.basefragment.BaseFragment;
import com.xcmgxs.xsmixfeedback.ui.basefragment.BaseSwipeRefreshFragment;
import com.xcmgxs.xsmixfeedback.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyi on 2015-4-16.
 */
public class ProjectIssueListFragment extends BaseSwipeRefreshFragment<ProjectIssue,CommonList<ProjectIssue>> {
    public final int MENU_CREATE_ID = 01;

    private Project mProject;

    private static boolean IS_ALL = false;

    public static ProjectIssueListFragment newInstance(Project project) {
        ProjectIssueListFragment fragment = new ProjectIssueListFragment();
        Bundle args = new Bundle();
        args.putSerializable(Contanst.PROJECT, project);
        fragment.setArguments(args);
        IS_ALL = false;
        return fragment;
    }

    public static ProjectIssueListFragment newInstance() {
        ProjectIssueListFragment fragment = new ProjectIssueListFragment();
        IS_ALL = true;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(!IS_ALL) {
            mProject = (Project) args.getSerializable(Contanst.PROJECT);
        }
        super.update();
    }

    @Override
    public BaseAdapter getAdapter(List<ProjectIssue> list) {
        return new ProjectIssueListAdapter(getActivity(), list, R.layout.projectissue_listitem,IS_ALL);
    }

    @Override
    protected MessageData<CommonList<ProjectIssue>> asyncLoadList(int page, boolean refresh) {
        MessageData<CommonList<ProjectIssue>> msg = null;
        try {
            String pid = "-1";
            if(!IS_ALL){
                pid = mProject.getId();
            }
            CommonList<ProjectIssue> list = getList(page, refresh,pid);
            msg = new MessageData<CommonList<ProjectIssue>>(list);
        } catch (AppException e) {
            e.makeToast(mApplication);
            e.printStackTrace();
            msg = new MessageData<CommonList<ProjectIssue>>(e);
        }
        return msg;
    }

    private CommonList<ProjectIssue> getList(int page, boolean refresh,String projectid) throws AppException {
        CommonList<ProjectIssue> list = mApplication.getProjectIssuesByProjectID(page, refresh, projectid);
        return list;
    }


    @Override
    public void onItemClick(int position, ProjectIssue issue) {

        ArrayList<String> arrUrls = new ArrayList<String>();
        if (!StringUtils.isEmpty(issue.getPic1())) {
            String url = URLs.URL_UPLOAD_ISSUEPIC + issue.getPic1();
            arrUrls.add(url);
        }
        if (!StringUtils.isEmpty(issue.getPic2())) {
            String url = URLs.URL_UPLOAD_ISSUEPIC + issue.getPic2();
            arrUrls.add(url);
        }
        if (!StringUtils.isEmpty(issue.getPic3())) {
            String url = URLs.URL_UPLOAD_ISSUEPIC + issue.getPic3();
            arrUrls.add(url);
        }
        if (!StringUtils.isEmpty(issue.getPic4())) {
            String url = URLs.URL_UPLOAD_ISSUEPIC + issue.getPic4();
            arrUrls.add(url);
        }
        if (!StringUtils.isEmpty(issue.getPic5())) {
            String url = URLs.URL_UPLOAD_ISSUEPIC + issue.getPic5();
            arrUrls.add(url);
        }
        String[] picURL = arrUrls.toArray(new String[arrUrls.size()]);
        if(picURL.length > 0) {
            ImagePreviewActivity.showImagePreview(getActivity(), 0, picURL);
        }

    }
}
