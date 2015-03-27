package com.xcmgxs.xsmixfeedback.ui.fragments;

import android.widget.BaseAdapter;

import com.xcmgxs.xsmixfeedback.bean.CommonList;
import com.xcmgxs.xsmixfeedback.bean.MessageData;
import com.xcmgxs.xsmixfeedback.bean.Project;
import com.xcmgxs.xsmixfeedback.ui.basefragment.BaseSwipeRefreshFragment;

import java.util.List;

/**
 * 所有项目页面推荐项目列表Fragment
 * @author zhangyi
 *
 * 最后更新
 * 更新者
 */
public class ExploreListProjectFragment extends BaseSwipeRefreshFragment<Project, CommonList<Project>> {

    public final static String EXPLORE_TYPE = "explore_type";

    public final static byte TYPE_ALL = 0x0;

    public final static byte TYPE_MY = 0x1;

    public final static byte TYPE_LATEST = 0x2;

    private byte type = 0;

    @Override
    public BaseAdapter getAdapter(List<Project> list) {
        return null;
    }

    @Override
    protected MessageData<CommonList<Project>> asyncLoadList(int page, boolean reflash) {
        return null;
    }
}
