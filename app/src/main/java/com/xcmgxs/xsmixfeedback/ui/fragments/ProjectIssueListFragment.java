package com.xcmgxs.xsmixfeedback.ui.fragments;

import android.os.Bundle;
import android.widget.BaseAdapter;

import com.xcmgxs.xsmixfeedback.AppException;
import com.xcmgxs.xsmixfeedback.R;
import com.xcmgxs.xsmixfeedback.adapter.ProjectIssueListAdapter;
import com.xcmgxs.xsmixfeedback.api.ApiClient;
import com.xcmgxs.xsmixfeedback.bean.CommonList;
import com.xcmgxs.xsmixfeedback.bean.MessageData;
import com.xcmgxs.xsmixfeedback.bean.Project;
import com.xcmgxs.xsmixfeedback.bean.ProjectIssue;
import com.xcmgxs.xsmixfeedback.bean.ProjectLog;
import com.xcmgxs.xsmixfeedback.common.Contanst;
import com.xcmgxs.xsmixfeedback.ui.basefragment.BaseFragment;
import com.xcmgxs.xsmixfeedback.ui.basefragment.BaseSwipeRefreshFragment;

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
}
