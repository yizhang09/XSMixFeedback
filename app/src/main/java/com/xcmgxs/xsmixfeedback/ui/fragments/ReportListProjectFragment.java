package com.xcmgxs.xsmixfeedback.ui.fragments;

import android.os.Bundle;
import android.widget.BaseAdapter;

import com.xcmgxs.xsmixfeedback.AppException;
import com.xcmgxs.xsmixfeedback.R;
import com.xcmgxs.xsmixfeedback.adapter.ReportProjectAdapter;
import com.xcmgxs.xsmixfeedback.bean.CommonList;
import com.xcmgxs.xsmixfeedback.bean.MessageData;
import com.xcmgxs.xsmixfeedback.bean.Project;
import com.xcmgxs.xsmixfeedback.common.UIHelper;
import com.xcmgxs.xsmixfeedback.ui.basefragment.BaseSwipeRefreshFragment;
import com.xcmgxs.xsmixfeedback.util.TLog;

import java.util.List;

/**
 * Created by zhangyi on 2015-06-16.
 */
public class ReportListProjectFragment extends BaseSwipeRefreshFragment<Project, CommonList<Project>> {

    private byte type = 0;

    public static ReportListProjectFragment newInstance() {
        ReportListProjectFragment fragment = new ReportListProjectFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.update();
    }

    @Override
    public BaseAdapter getAdapter(List<Project> list) {
        return new ReportProjectAdapter(getActivity(), list, R.layout.report_list_item);
    }

    @Override
    protected MessageData<CommonList<Project>> asyncLoadList(int page, boolean refresh) {
        MessageData<CommonList<Project>> msg = null;
        try {
            CommonList<Project> list = getList(type, page, refresh);
            msg = new MessageData<CommonList<Project>>(list);
        } catch (AppException e) {
            e.makeToast(mApplication);
            e.printStackTrace();
            msg = new MessageData<CommonList<Project>>(e);
        }
        return msg;
    }


    private CommonList<Project> getList(byte type, int page, boolean refresh) throws AppException {
        return mApplication.getExploreAllProject(page, refresh);
    }

    @Override
    public void onItemClick(int position, Project project) {
        UIHelper.showProjectReportDetail(getActivity(), project, project.getId());
    }
}
