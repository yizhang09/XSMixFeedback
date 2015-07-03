package com.xcmgxs.xsmixfeedback.ui.fragments;

import android.os.Bundle;
import android.widget.BaseAdapter;

import com.xcmgxs.xsmixfeedback.AppException;
import com.xcmgxs.xsmixfeedback.R;
import com.xcmgxs.xsmixfeedback.adapter.MyProjectListAdapter;
import com.xcmgxs.xsmixfeedback.bean.CommonList;
import com.xcmgxs.xsmixfeedback.bean.MessageData;
import com.xcmgxs.xsmixfeedback.bean.Project;
import com.xcmgxs.xsmixfeedback.common.UIHelper;
import com.xcmgxs.xsmixfeedback.ui.basefragment.BaseFragment;
import com.xcmgxs.xsmixfeedback.ui.basefragment.BaseSwipeRefreshFragment;
import com.xcmgxs.xsmixfeedback.util.TLog;

import java.util.List;

/**
 * Created by zhangyi on 2015-07-03.
 */
public class ProjectStateListFragment extends BaseSwipeRefreshFragment<Project, CommonList<Project>> {

    public final static String STATE_TYPE = "state_type";

    public final static int STATE1 = 0;

    public final static int STATE2 = 1;

    public final static int STATE3 = 2;

    public final static int STATE4 = 3;

    public final static int STATE5 = 4;

    public final static int STATE6 = 5;

    public final static int STATE7 = 6;

    public final static String YEAR_TAG = "year";

    private int mState = -1;

    private int mYear = -1;

    public static ProjectStateListFragment newInstance(byte state,int year) {
        ProjectStateListFragment projectStateListFragment = new ProjectStateListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(STATE_TYPE, state);
        bundle.putInt(YEAR_TAG, year);
        projectStateListFragment.setArguments(bundle);
        return projectStateListFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        mState = args.getInt(STATE_TYPE);
        mYear = args.getInt(YEAR_TAG);
    }

    @Override
    public BaseAdapter getAdapter(List<Project> list) {
        return new MyProjectListAdapter(getActivity(), list, R.layout.exploreproject_listitem);
    }

    @Override
    protected MessageData<CommonList<Project>> asyncLoadList(int page, boolean refresh) {
        MessageData<CommonList<Project>> msg = null;
        try {
            CommonList<Project> list = getList(page,mYear,mState, refresh);
            msg = new MessageData<CommonList<Project>>(list);
        } catch (AppException e) {
            e.makeToast(mApplication);
            e.printStackTrace();
            msg = new MessageData<CommonList<Project>>(e);
        }
        return msg;
    }


    private CommonList<Project> getList(int page,int year,int state, boolean refresh) throws AppException {
        CommonList<Project> list = mApplication.getProjectList(page, year,state);
        return list;
    }

    @Override
    public void onItemClick(int position, Project project) {
        UIHelper.showProjectReportDetail(getActivity(), project, project.getId());
    }
}
