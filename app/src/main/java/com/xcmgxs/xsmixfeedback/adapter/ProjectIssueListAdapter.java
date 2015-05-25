package com.xcmgxs.xsmixfeedback.adapter;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xcmgxs.xsmixfeedback.R;
import com.xcmgxs.xsmixfeedback.bean.ProjectIssue;
import com.xcmgxs.xsmixfeedback.common.BitmapManager;

import java.util.List;


/**
 * Created by zhangyi on 2015-3-20.
 */
public class ProjectIssueListAdapter extends MyBaseAdapter<ProjectIssue> {

    private BitmapManager bmpManager;

    static class ListItemView{
        public ImageView face;
        public TextView username;
        public TextView content;
        public TextView date;
    }

    public ProjectIssueListAdapter(Context context, List<ProjectIssue> listData, int itemViewResource) {
        super(context, listData, itemViewResource);
        this.bmpManager = new BitmapManager(BitmapFactory.decodeResource(context.getResources(), R.drawable.widget_dface_loading));
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
