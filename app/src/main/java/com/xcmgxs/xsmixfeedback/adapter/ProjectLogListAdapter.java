package com.xcmgxs.xsmixfeedback.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xcmgxs.xsmixfeedback.AppContext;
import com.xcmgxs.xsmixfeedback.R;
import com.xcmgxs.xsmixfeedback.adapter.base.MyBaseAdapter;
import com.xcmgxs.xsmixfeedback.bean.ProjectLog;
import com.xcmgxs.xsmixfeedback.api.URLs;
import com.xcmgxs.xsmixfeedback.ui.ImagePreviewActivity;
import com.xcmgxs.xsmixfeedback.util.ImageLoaderUtils;
import com.xcmgxs.xsmixfeedback.util.StringUtils;
import com.xcmgxs.xsmixfeedback.widget.CircleImageView;

import java.util.List;

/**
 * Created by zhangyi on 2015-3-20.
 */
public class ProjectLogListAdapter extends MyBaseAdapter<ProjectLog> {

    private boolean IS_SHOW_PROJECT_NAME = false;

    private Context mContext;

    static class ListItemView{
        public ImageView face;
        public TextView username;
        public TextView content;
        public TextView date;
        public ImageView picture;
        public TextView projectname;
    }

    public ProjectLogListAdapter(Context context, List<ProjectLog> listData, int itemViewResource,boolean isShowProjectName) {
        super(context, listData, itemViewResource);
        IS_SHOW_PROJECT_NAME = isShowProjectName;
        mContext = context;
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
            listItemView.projectname = (TextView)convertView.findViewById(R.id.projectlog_listitem_projectname);
            listItemView.picture = (ImageView)convertView.findViewById(R.id.projectlog_listitem_pic);

            convertView.setTag(listItemView);

        }
        else {
            listItemView = (ListItemView)convertView.getTag();
        }

        final ProjectLog log = listData.get(position);


        // 1.加载头像
        String portraitURL = log.getAuthor().getPortrait() == null ? "" : log.getAuthor().getPortrait();
        if (portraitURL.endsWith("portrait.gif") || StringUtils.isEmpty(portraitURL)) {
            listItemView.face.setImageResource(R.drawable.mini_avatar);
        } else {
            portraitURL = URLs.URL_PORTRAIT + portraitURL;
            //bmpManager.loadBitmap(portraitURL, listItemView.face);
            ImageLoader.getInstance().displayImage(portraitURL, listItemView.face, ImageLoaderUtils.getOption());

        }

        // 加载图片
        String picURL = log.getPic();
        if (StringUtils.isEmpty(picURL)) {
            listItemView.picture.setVisibility(View.GONE);
        } else {
            listItemView.picture.setVisibility(View.VISIBLE);
            picURL = URLs.URL_UPLOAD_LOGPIC + picURL;
            //bmpManager.loadBitmap(picURL, listItemView.picture);
            ImageLoader.getInstance().displayImage(picURL, listItemView.picture, ImageLoaderUtils.getOption());
        }

        // 2.显示相关信息
        listItemView.username.setText(log.getAuthor().getName());
        listItemView.projectname.setText(log.getProject().getName());
        listItemView.content.setText(log.getContent());
        listItemView.date.setText(log.getCreatedate());

        listItemView.picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String picURL = log.getPic();
                if (!StringUtils.isEmpty(picURL)) {
                    picURL = URLs.URL_UPLOAD_LOGPIC + picURL;
                    ImagePreviewActivity.showImagePreview(mContext, 0, new String[]{picURL});
                }
            }
        });

        return convertView;
    }
}
