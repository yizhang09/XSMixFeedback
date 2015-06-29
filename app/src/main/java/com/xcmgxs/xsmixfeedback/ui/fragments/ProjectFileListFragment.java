package com.xcmgxs.xsmixfeedback.ui.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.BaseAdapter;

import com.xcmgxs.xsmixfeedback.AppException;
import com.xcmgxs.xsmixfeedback.R;
import com.xcmgxs.xsmixfeedback.adapter.ProjectFileListAdapter;
import com.xcmgxs.xsmixfeedback.api.URLs;
import com.xcmgxs.xsmixfeedback.bean.CommonList;
import com.xcmgxs.xsmixfeedback.bean.MessageData;
import com.xcmgxs.xsmixfeedback.bean.Project;
import com.xcmgxs.xsmixfeedback.bean.ProjectFile;
import com.xcmgxs.xsmixfeedback.common.Contanst;
import com.xcmgxs.xsmixfeedback.common.DownloadFileManager;
import com.xcmgxs.xsmixfeedback.common.UIHelper;
import com.xcmgxs.xsmixfeedback.ui.basefragment.BaseSwipeRefreshFragment;

import java.io.File;
import java.util.List;

/**
 * Created by zhangyi on 2015-05-28.
 */
public class ProjectFileListFragment extends BaseSwipeRefreshFragment<ProjectFile,CommonList<ProjectFile>> {

    public final int MENU_CREATE_ID = 01;

    private Project mProject;

    private static boolean IS_ALL = false;

    public static ProjectFileListFragment newInstance(Project project) {
        ProjectFileListFragment fragment = new ProjectFileListFragment();
        Bundle args = new Bundle();
        args.putSerializable(Contanst.PROJECT, project);
        fragment.setArguments(args);
        IS_ALL = false;
        return fragment;
    }

    public static ProjectFileListFragment newInstance() {
        ProjectFileListFragment fragment = new ProjectFileListFragment();
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
    public BaseAdapter getAdapter(List<ProjectFile> list) {
        return new ProjectFileListAdapter(getActivity(), list, R.layout.projectfile_listitem,IS_ALL);
    }

    @Override
    protected MessageData<CommonList<ProjectFile>> asyncLoadList(int page, boolean refresh) {
        MessageData<CommonList<ProjectFile>> msg = null;
        try {
            String pid = "-1";
            if(!IS_ALL){
                pid = mProject.getId();
            }
            CommonList<ProjectFile> list = getList(page, refresh,pid);
            msg = new MessageData<CommonList<ProjectFile>>(list);
        } catch (AppException e) {
            e.makeToast(mApplication);
            e.printStackTrace();
            msg = new MessageData<CommonList<ProjectFile>>(e);
        }
        return msg;
    }

    private CommonList<ProjectFile> getList(int page, boolean refresh,String projectid) throws AppException {
        CommonList<ProjectFile> list = mApplication.getProjectFileByProjectID(page, refresh, projectid);
        return list;
    }



    @Override
    public void onItemClick(int position, ProjectFile file) {
        DownloadFileManager.getDownloadFileManager(getActivity(),URLs.URL_DOWNLOAD_FILE +"?id="+ file.getId(),file.getFilename()).showOpenFileDialog();
        //UIHelper.showProjectDetail(getActivity(), file, file.getId());
    }


}
