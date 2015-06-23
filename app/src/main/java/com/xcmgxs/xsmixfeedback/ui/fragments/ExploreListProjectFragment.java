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
import com.xcmgxs.xsmixfeedback.ui.basefragment.BaseSwipeRefreshFragment;
import com.xcmgxs.xsmixfeedback.util.TLog;

import java.util.List;

/**
 * 所有项目页面推荐项目列表Fragment
 *
 * @author zhangyi
 *         <p/>
 *         最后更新
 *         更新者
 */

public class ExploreListProjectFragment extends BaseSwipeRefreshFragment<Project, CommonList<Project>> {

    public final static String EXPLORE_TYPE = "explore_type";

    public final static byte TYPE_ALL = 0x0;

    public final static byte TYPE_MY = 0x1;

    public final static byte TYPE_LATEST = 0x2;

    private byte type = 0;

    public static ExploreListProjectFragment newInstance(byte type) {
        ExploreListProjectFragment exploreFeaturedListProjectFragment = new ExploreListProjectFragment();
        Bundle bundle = new Bundle();
        bundle.putByte(EXPLORE_TYPE, type);
        exploreFeaturedListProjectFragment.setArguments(bundle);
        return exploreFeaturedListProjectFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        type = args.getByte(EXPLORE_TYPE);
    }

    @Override
    public BaseAdapter getAdapter(List<Project> list) {
        return new MyProjectListAdapter(getActivity(), list, R.layout.exploreproject_listitem);
    }

    @Override
    public MessageData<CommonList<Project>> asyncLoadList(int page, boolean refresh) {
        MessageData<CommonList<Project>> msg = null;
        try {
            CommonList<Project> list = getList(type, page, refresh);
            msg = new MessageData<CommonList<Project>>(list);
            TLog.log(String.valueOf(msg.result.getCount()));
        } catch (AppException e) {
            e.makeToast(mApplication);
            e.printStackTrace();
            msg = new MessageData<CommonList<Project>>(e);
        }
        return msg;
    }

    private CommonList<Project> getList(byte type, int page, boolean refresh) throws AppException {
        CommonList<Project> list = null;
        switch (type) {
            case TYPE_ALL:
                list = mApplication.getExploreAllProject(page, refresh);
                break;
            case TYPE_MY:
                list = mApplication.getExploreMyProject(page, refresh);
                break;
            case TYPE_LATEST:
                list = mApplication.getExploreUpdateProject(page, refresh);
                break;
        }
        return list;
    }

    @Override
    public void onItemClick(int position, Project project) {
        UIHelper.showProjectDetail(getActivity(), project, project.getId());
    }


}
