package com.xcmgxs.xsmixfeedback.ui.fragments;

import android.os.Bundle;
import android.widget.BaseAdapter;

import com.xcmgxs.xsmixfeedback.AppException;
import com.xcmgxs.xsmixfeedback.R;
import com.xcmgxs.xsmixfeedback.adapter.ProjectDocListAdapter;
import com.xcmgxs.xsmixfeedback.bean.CommonList;
import com.xcmgxs.xsmixfeedback.bean.MessageData;
import com.xcmgxs.xsmixfeedback.bean.Project;
import com.xcmgxs.xsmixfeedback.bean.ProjectDoc;
import com.xcmgxs.xsmixfeedback.bean.ProjectFile;
import com.xcmgxs.xsmixfeedback.common.Contanst;
import com.xcmgxs.xsmixfeedback.common.UIHelper;
import com.xcmgxs.xsmixfeedback.ui.basefragment.BaseSwipeRefreshFragment;

import java.util.List;

/**
 * Created by zhangyi on 2015-06-19.
 */
public class ProjectDocListFragment extends BaseSwipeRefreshFragment<ProjectDoc,CommonList<ProjectDoc>> {


    private Project mProject;

    public static ProjectDocListFragment newInstance(Project project) {
        ProjectDocListFragment fragment = new ProjectDocListFragment();
        Bundle args = new Bundle();
        args.putSerializable(Contanst.PROJECT, project);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        mProject = (Project) args.getSerializable(Contanst.PROJECT);
        super.update();

    }

    @Override
    public BaseAdapter getAdapter(List<ProjectDoc> list) {
        return new ProjectDocListAdapter(getActivity(), list, R.layout.projectdoc_listitem);
    }

    @Override
    protected MessageData<CommonList<ProjectDoc>> asyncLoadList(int page, boolean refresh) {
        MessageData<CommonList<ProjectDoc>> msg = null;
        try {
            String pid = "-1";
            pid = mProject.getId();
            CommonList<ProjectDoc> list = getList(page, refresh,pid);
            msg = new MessageData<CommonList<ProjectDoc>>(list);
        } catch (AppException e) {
            e.makeToast(mApplication);
            e.printStackTrace();
            msg = new MessageData<CommonList<ProjectDoc>>(e);
        }
        return msg;
    }


    private CommonList<ProjectDoc> getList(int page, boolean refresh,String projectid) throws AppException {
        CommonList<ProjectDoc> list = mApplication.getProjectDocByProjectID(page, refresh, projectid);
        return list;
    }



    @Override
    public void onItemClick(int position, ProjectDoc doc) {

        UIHelper.showProjectDetail(getActivity(), null, doc.getId());
    }
}
