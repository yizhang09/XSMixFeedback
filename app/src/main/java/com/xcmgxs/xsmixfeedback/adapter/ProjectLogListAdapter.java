package com.xcmgxs.xsmixfeedback.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xcmgxs.xsmixfeedback.R;
import com.xcmgxs.xsmixfeedback.bean.ProjectLog;
import com.xcmgxs.xsmixfeedback.bean.URLs;
import com.xcmgxs.xsmixfeedback.common.BitmapManager;
import com.xcmgxs.xsmixfeedback.util.StringUtils;
import com.xcmgxs.xsmixfeedback.widget.CircleImageView;

import java.util.List;

/**
 * Created by zhangyi on 2015-3-20.
 */
public class ProjectLogListAdapter extends MyBaseAdapter<ProjectLog> {

    private BitmapManager bmpManager;

    static class ListItemView{
        public ImageView face;
        public TextView username;
        public TextView content;
        public TextView date;
        public ImageView picture;
    }

    public ProjectLogListAdapter(Context context, List<ProjectLog> listData, int itemViewResource) {
        super(context, listData, itemViewResource);
        this.bmpManager = new BitmapManager(BitmapFactory.decodeResource(context.getResources(), R.drawable.widget_dface_loading));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ListItemView listItemView = null;
        if(convertView == null){
            convertView = listContainer.inflate(this.itemViewResource,null);

            listItemView = new ListItemView();
            listItemView.face = (CircleImageView)convertView.findViewById(R.id.projectlog_listitem_face);
            listItemView.date = (TextView)convertView.findViewById(R.id.projectlog_listitem_date);
            listItemView.content = (TextView)convertView.findViewById(R.id.projectlog_listitem_content);
            listItemView.username = (TextView)convertView.findViewById(R.id.projectlog_listitem_username);
            listItemView.picture = (ImageView)convertView.findViewById(R.id.projectlog_listitem_pic);

            convertView.setTag(listItemView);

        }
        else {
            listItemView = (ListItemView)convertView.getTag();
        }

        ProjectLog log = listData.get(position);


        // 1.加载头像
        String portraitURL = log.getAuthor() == null ? "" : "";
        if (portraitURL.endsWith("portrait.gif") || StringUtils.isEmpty(portraitURL)) {
            listItemView.face.setImageResource(R.drawable.mini_avatar);
        } else {
            bmpManager.loadBitmap(portraitURL, listItemView.face);
        }

        // 加载图片
        String picURL = log.getPic();
        if (StringUtils.isEmpty(picURL)) {
            listItemView.picture.setVisibility(View.GONE);
        } else {
            listItemView.picture.setVisibility(View.VISIBLE);
            picURL = URLs.URL_UPLOAD_LOGPIC + picURL;
            bmpManager.loadBitmap(picURL, listItemView.picture);
        }

        // 2.显示相关信息
        listItemView.username.setText(log.getAuthor());
        listItemView.content.setText(log.getContent());
        listItemView.date.setText(StringUtils.friendly_time(log.getCreatedate()));

        return convertView;
    }
}
