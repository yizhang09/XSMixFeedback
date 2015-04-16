package com.xcmgxs.xsmixfeedback.ui.fragments;

import android.os.Bundle;

import com.xcmgxs.xsmixfeedback.bean.Project;
import com.xcmgxs.xsmixfeedback.common.Contanst;
import com.xcmgxs.xsmixfeedback.ui.basefragment.BaseFragment;

/**
 * Created by zhangyi on 2015-4-16.
 */
public class ProjectIssueListFragment extends BaseFragment {
    public final int MENU_CREATE_ID = 01;

    private Project mProject;

    public static ProjectIssueListFragment newInstance(Project project) {
        ProjectIssueListFragment fragment = new ProjectIssueListFragment();
        Bundle args = new Bundle();
        args.putSerializable(Contanst.PROJECT, project);
        fragment.setArguments(args);
        return fragment;
    }
}
