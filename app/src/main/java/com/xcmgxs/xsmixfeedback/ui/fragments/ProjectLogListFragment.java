package com.xcmgxs.xsmixfeedback.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.xcmgxs.xsmixfeedback.AppException;
import com.xcmgxs.xsmixfeedback.R;
import com.xcmgxs.xsmixfeedback.adapter.ProjectLogListAdapter;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        mProject = (Project)args.getSerializable(Contanst.PROJECT);
    }

    @Override
    public BaseAdapter getAdapter(List<ProjectLog> list) {
        return new ProjectLogListAdapter(getActivity(),list, R.layout.projectlog_listitem);
    }

    @Override
    protected MessageData<CommonList<ProjectLog>> asyncLoadList(int page, boolean refresh){
        MessageData<CommonList<ProjectLog>> msg = null;
        try {
            CommonList<ProjectLog> list = getList(page, refresh,mProject.getId());
            msg = new MessageData<CommonList<ProjectLog>>(list);
        } catch (AppException e) {
            e.makeToast(mApplication);
            e.printStackTrace();
            msg = new MessageData<CommonList<ProjectLog>>(e);
        }
        return msg;
    }


    private CommonList<ProjectLog> getList(int page, boolean refresh,String projectid) throws AppException {
        CommonList<ProjectLog> list = mApplication.getProjectLogByProjectID(page,refresh,projectid);
        return list;
    }

}
