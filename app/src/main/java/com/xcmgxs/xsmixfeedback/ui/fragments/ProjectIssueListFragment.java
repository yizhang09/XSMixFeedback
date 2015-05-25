package com.xcmgxs.xsmixfeedback.ui.fragments;

import android.os.Bundle;
import android.widget.BaseAdapter;

import com.xcmgxs.xsmixfeedback.adapter.ProjectIssueListAdapter;
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

    public static ProjectIssueListFragment newInstance(Project project) {
        ProjectIssueListFragment fragment = new ProjectIssueListFragment();
        Bundle args = new Bundle();
        args.putSerializable(Contanst.PROJECT, project);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public BaseAdapter getAdapter(List<ProjectIssue> list) {
        //return new ProjectIssueListAdapter().
        return null;
    }

    @Override
    protected MessageData<CommonList<ProjectIssue>> asyncLoadList(int page, boolean refresh) {
        return null;
    }
}
