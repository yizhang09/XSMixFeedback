package com.xcmgxs.xsmixfeedback.ui.fragments;

import android.os.Bundle;
import android.widget.BaseAdapter;

import com.xcmgxs.xsmixfeedback.AppContext;
import com.xcmgxs.xsmixfeedback.AppException;
import com.xcmgxs.xsmixfeedback.R;
import com.xcmgxs.xsmixfeedback.adapter.ProjectSendIssueListAdapter;
import com.xcmgxs.xsmixfeedback.api.URLs;
import com.xcmgxs.xsmixfeedback.bean.CommonList;
import com.xcmgxs.xsmixfeedback.bean.MessageData;
import com.xcmgxs.xsmixfeedback.bean.Project;
import com.xcmgxs.xsmixfeedback.bean.ProjectSendIssue;
import com.xcmgxs.xsmixfeedback.common.Contanst;
import com.xcmgxs.xsmixfeedback.ui.ImagePreviewActivity;
import com.xcmgxs.xsmixfeedback.ui.basefragment.BaseSwipeRefreshFragment;
import com.xcmgxs.xsmixfeedback.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyi on 2015-08-25.
 */
public class ProjectSendIssueListFragment extends BaseSwipeRefreshFragment<ProjectSendIssue,CommonList<ProjectSendIssue>> {


    private Project mProject;

    private static boolean IS_ALL = false;

    private AppContext mContext;


    public static ProjectSendIssueListFragment newInstance(Project project) {
        ProjectSendIssueListFragment fragment = new ProjectSendIssueListFragment();
        Bundle args = new Bundle();
        args.putSerializable(Contanst.PROJECT, project);
        fragment.setArguments(args);
        IS_ALL = false;
        return fragment;
    }

    public static ProjectSendIssueListFragment newInstance() {
        ProjectSendIssueListFragment fragment = new ProjectSendIssueListFragment();
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
        mContext = getXsApplication();
    }

    @Override
    public BaseAdapter getAdapter(List<ProjectSendIssue> list) {
        return new ProjectSendIssueListAdapter(getActivity(), list, R.layout.projectsendissue_listitem,IS_ALL);
    }

    @Override
    protected MessageData<CommonList<ProjectSendIssue>> asyncLoadList(int page, boolean refresh) {
        MessageData<CommonList<ProjectSendIssue>> msg = null;
        try {
            String pid = "-1";
            if(!IS_ALL){
                pid = mProject.getId();
            }
            CommonList<ProjectSendIssue> list = getList(page, refresh,pid);
            msg = new MessageData<CommonList<ProjectSendIssue>>(list);
        } catch (AppException e) {
            e.makeToast(mApplication);
            e.printStackTrace();
            msg = new MessageData<CommonList<ProjectSendIssue>>(e);
        }
        return msg;
    }


    private CommonList<ProjectSendIssue> getList(int page, boolean refresh,String projectid) throws AppException {
        CommonList<ProjectSendIssue> list = mApplication.getProjectSendIssuesByProjectID(page, refresh, projectid);
        return list;
    }



    @Override
    public void onItemClick(int position, ProjectSendIssue issue) {

        ArrayList<String> arrUrls = new ArrayList<String>();
        if (!StringUtils.isEmpty(issue.getPic1())) {
            String url = URLs.URL_UPLOAD_SENDISSUEPIC + issue.getPic1();
            arrUrls.add(url);
        }
        if (!StringUtils.isEmpty(issue.getPic2())) {
            String url = URLs.URL_UPLOAD_SENDISSUEPIC + issue.getPic2();
            arrUrls.add(url);
        }
        if (!StringUtils.isEmpty(issue.getPic3())) {
            String url = URLs.URL_UPLOAD_SENDISSUEPIC + issue.getPic3();
            arrUrls.add(url);
        }
        if (!StringUtils.isEmpty(issue.getPic4())) {
            String url = URLs.URL_UPLOAD_SENDISSUEPIC + issue.getPic4();
            arrUrls.add(url);
        }
        if (!StringUtils.isEmpty(issue.getPic5())) {
            String url = URLs.URL_UPLOAD_SENDISSUEPIC + issue.getPic5();
            arrUrls.add(url);
        }
        String[] picURL = arrUrls.toArray(new String[arrUrls.size()]);
        if(picURL.length > 0) {
            ImagePreviewActivity.showImagePreview(getActivity(), 0, picURL);
        }

    }
}
