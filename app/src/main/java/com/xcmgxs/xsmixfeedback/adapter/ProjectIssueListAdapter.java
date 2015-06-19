package com.xcmgxs.xsmixfeedback.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xcmgxs.xsmixfeedback.R;
import com.xcmgxs.xsmixfeedback.adapter.base.MyBaseAdapter;
import com.xcmgxs.xsmixfeedback.api.URLs;
import com.xcmgxs.xsmixfeedback.bean.ProjectIssue;
import com.xcmgxs.xsmixfeedback.util.ImageLoaderUtils;
import com.xcmgxs.xsmixfeedback.util.StringUtils;
import com.xcmgxs.xsmixfeedback.widget.CircleImageView;

import java.util.List;


/**
 * Created by zhangyi on 2015-3-20.
 */
public class ProjectIssueListAdapter extends MyBaseAdapter<ProjectIssue> {

    private boolean IS_SHOW_PROJECT_NAME = false;

    static class ListItemView{
        public ImageView face;
        public TextView username;
        public TextView content;
        public TextView type;
        public TextView date;
        public ImageView pic1;
        public TextView projectname;
    }

    public ProjectIssueListAdapter(Context context, List<ProjectIssue> listData, int itemViewResource ,boolean isShowProjectName) {
        super(context, listData, itemViewResource);
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
            listItemView.projectname = (TextView)convertView.findViewById(R.id.projectissue_listitem_projectname);
            listItemView.type = (TextView)convertView.findViewById(R.id.projectissue_listitem_type);
            listItemView.pic1 = (ImageView)convertView.findViewById(R.id.projectissue_listitem_pic1);
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
            portraitURL = URLs.URL_PORTRAIT + portraitURL;
            ImageLoader.getInstance().displayImage(portraitURL, listItemView.face, ImageLoaderUtils.getOption());
        }

        // 加载图片
        String picURL = issue.getPic1();
        if (StringUtils.isEmpty(picURL)) {
            listItemView.pic1.setVisibility(View.GONE);
        } else {
            listItemView.pic1.setVisibility(View.VISIBLE);
            picURL = URLs.URL_UPLOAD_ISSUEPIC + picURL;
            //bmpManager.loadBitmap(picURL, listItemView.picture);
            ImageLoader.getInstance().displayImage(picURL, listItemView.pic1, ImageLoaderUtils.getOption());
        }

        // 2.显示相关信息
        listItemView.username.setText(issue.getCreator().getName());
        listItemView.projectname.setText(issue.getProject().getName());

        listItemView.content.setText(issue.getContent());
        listItemView.date.setText(issue.getCreatedate());
        listItemView.type.setText(issue.getType());

        return convertView;
    }
}
