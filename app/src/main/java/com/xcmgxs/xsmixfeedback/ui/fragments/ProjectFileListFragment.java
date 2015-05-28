package com.xcmgxs.xsmixfeedback.ui.fragments;

import android.widget.BaseAdapter;

import com.xcmgxs.xsmixfeedback.bean.CommonList;
import com.xcmgxs.xsmixfeedback.bean.MessageData;
import com.xcmgxs.xsmixfeedback.bean.ProjectFile;
import com.xcmgxs.xsmixfeedback.ui.basefragment.BaseSwipeRefreshFragment;

import java.util.List;

/**
 * Created by zhangyi on 2015-05-28.
 */
public class ProjectFileListFragment extends BaseSwipeRefreshFragment<ProjectFile,CommonList<ProjectFile>> {


    @Override
    public BaseAdapter getAdapter(List<ProjectFile> list) {
        return null;
    }

    @Override
    protected MessageData<CommonList<ProjectFile>> asyncLoadList(int page, boolean refresh) {
        return null;
    }
}
