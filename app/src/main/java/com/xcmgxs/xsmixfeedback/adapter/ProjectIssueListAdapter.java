package com.xcmgxs.xsmixfeedback.adapter;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xcmgxs.xsmixfeedback.R;
import com.xcmgxs.xsmixfeedback.api.URLs;
import com.xcmgxs.xsmixfeedback.bean.ProjectIssue;
import com.xcmgxs.xsmixfeedback.bean.ProjectLog;
import com.xcmgxs.xsmixfeedback.common.BitmapManager;
import com.xcmgxs.xsmixfeedback.util.StringUtils;
import com.xcmgxs.xsmixfeedback.widget.CircleImageView;

import org.w3c.dom.Text;

import java.util.List;


/**
 * Created by zhangyi on 2015-3-20.
 */
public class ProjectIssueListAdapter extends MyBaseAdapter<ProjectIssue> {

    private BitmapManager bmpManager;

    private boolean IS_SHOW_PROJECT_NAME = false;

    static class ListItemView{
        public ImageView face;
        public TextView username;
        public TextView content;
        public TextView type;
        public TextView date;
    }

    public ProjectIssueListAdapter(Context context, List<ProjectIssue> listData, int itemViewResource ,boolean isShowProjectName) {
        super(context, listData, itemViewResource);
        this.bmpManager = new BitmapManager(BitmapFactory.decodeResource(context.getResources(), R.drawable.widget_dface_loading));
        IS_SHOW_PROJECT_NAME = isShowProjectName;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ListItemView listItemView = null;
        if(convertView == null){
            convertView = listContainer.inflate(this.itemViewResource,null);

            listItemView = new ListItemView();
            listItemView.face = (CircleImageView)convertView.findViewById(R.id.projectissue_listitem_face);
            listItemView.date = (TextView)convertView.findViewById(R.id.projectissue_listitem_date);
            listItemView.content = (TextView)convertView.findViewById(R.id.projectissue_listitem_content);
            listItemView.username = (TextView)convertView.findViewById(R.id.projectissue_listitem_username);
            listItemView.type = (TextView)convertView.findViewById(R.id.projectissue_listitem_type);

            convertView.setTag(listItemView);

        }
        else {
            listItemView = (ListItemView)convertView.getTag();
        }

        ProjectIssue issue = listData.get(position);


        // 1.加载头像
        String portraitURL = issue.getCreator().getPortrait() == null ? "" : issue.getCreator().getPortrait();
        if (portraitURL.endsWith("portrait.gif") || StringUtils.isEmpty(portraitURL)) {
            listItemView.face.setImageResource(R.drawable.mini_avatar);
        } else {
            bmpManager.loadBitmap(portraitURL, listItemView.face);
        }

        // 2.显示相关信息
        listItemView.username.setText(IS_SHOW_PROJECT_NAME ? issue.getCreator().getName() + " - " + issue.getProject().getName(): issue.getCreator().getName());
        listItemView.content.setText(issue.getContent());
        listItemView.date.setText(StringUtils.friendly_time(issue.getCreatedate()));
        listItemView.type.setText(issue.getType());

        return convertView;
    }
}
