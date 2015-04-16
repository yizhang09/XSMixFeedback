package com.xcmgxs.xsmixfeedback.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.xcmgxs.xsmixfeedback.bean.CommonList;
import com.xcmgxs.xsmixfeedback.bean.MessageData;
import com.xcmgxs.xsmixfeedback.bean.Project;
import com.xcmgxs.xsmixfeedback.bean.ProjectLog;
import com.xcmgxs.xsmixfeedback.common.Contanst;
import com.xcmgxs.xsmixfeedback.ui.basefragment.BaseSwipeRefreshFragment;

import java.util.List;

/**
 * Created by zhangyi on 2015-4-16.
 */
public class ProjectLogListFragment extends BaseSwipeRefreshFragment<ProjectLog,CommonList<ProjectLog>> {
    public final int MENU_CREATE_ID = 01;

    private Project mProject;

    public static ProjectLogListFragment newInstance(Project project) {
        ProjectLogListFragment fragment = new ProjectLogListFragment();
        Bundle args = new Bundle();
        args.putSerializable(Contanst.PROJECT, project);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public BaseAdapter getAdapter(List<ProjectLog> list) {
        return null;
    }

    @Override
    protected MessageData<CommonList<ProjectLog>> asyncLoadList(int page, boolean reflash) {
        return null;
    }

}
