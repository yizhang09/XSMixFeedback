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
import com.xcmgxs.xsmixfeedback.api.URLs;
import com.xcmgxs.xsmixfeedback.bean.CommonList;
import com.xcmgxs.xsmixfeedback.bean.MessageData;
import com.xcmgxs.xsmixfeedback.bean.Project;
import com.xcmgxs.xsmixfeedback.bean.ProjectLog;
import com.xcmgxs.xsmixfeedback.common.Contanst;
import com.xcmgxs.xsmixfeedback.ui.ImagePreviewActivity;
import com.xcmgxs.xsmixfeedback.ui.basefragment.BaseSwipeRefreshFragment;
import com.xcmgxs.xsmixfeedback.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyi on 2015-4-16.
 */
public class ProjectLogListFragment extends BaseSwipeRefreshFragment<ProjectLog,CommonList<ProjectLog>> {
    public final int MENU_CREATE_ID = 01;

    private Project mProject;

    private static boolean IS_ALL = false;

    public static ProjectLogListFragment newInstance(Project project) {
        ProjectLogListFragment fragment = new ProjectLogListFragment();
        Bundle args = new Bundle();
        args.putSerializable(Contanst.PROJECT, project);
        fragment.setArguments(args);
        IS_ALL = false;
        return fragment;
    }

    public static ProjectLogListFragment newInstance() {
        ProjectLogListFragment fragment = new ProjectLogListFragment();
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
    public BaseAdapter getAdapter(List<ProjectLog> list) {
        return new ProjectLogListAdapter(getActivity(), list, R.layout.projectlog_listitem,IS_ALL);
    }

    @Override
    protected MessageData<CommonList<ProjectLog>> asyncLoadList(int page, boolean refresh){
        MessageData<CommonList<ProjectLog>> msg = null;
        try {
            String pid = "-1";
            if(!IS_ALL){
                pid = mProject.getId();
            }
            CommonList<ProjectLog> list = getList(page, refresh,pid);
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

    @Override
    public void onItemClick(int position, ProjectLog projectLog) {

        ArrayList<String> arrUrls = new ArrayList<String>();
        if (!StringUtils.isEmpty(projectLog.getPic1())) {
            String url = URLs.URL_UPLOAD_LOGPIC + projectLog.getPic1();
            arrUrls.add(url);
        }
        if (!StringUtils.isEmpty(projectLog.getPic2())) {
            String url = URLs.URL_UPLOAD_LOGPIC + projectLog.getPic2();
            arrUrls.add(url);
        }
        if (!StringUtils.isEmpty(projectLog.getPic3())) {
            String url = URLs.URL_UPLOAD_LOGPIC + projectLog.getPic3();
            arrUrls.add(url);
        }
        if (!StringUtils.isEmpty(projectLog.getPic4())) {
            String url = URLs.URL_UPLOAD_LOGPIC + projectLog.getPic4();
            arrUrls.add(url);
        }
        if (!StringUtils.isEmpty(projectLog.getPic5())) {
            String url = URLs.URL_UPLOAD_LOGPIC + projectLog.getPic5();
            arrUrls.add(url);
        }
        String[] picURL = arrUrls.toArray(new String[arrUrls.size()]);
        if(picURL.length > 0) {
            ImagePreviewActivity.showImagePreview(getActivity(), 0, picURL);
        }

    }
}
