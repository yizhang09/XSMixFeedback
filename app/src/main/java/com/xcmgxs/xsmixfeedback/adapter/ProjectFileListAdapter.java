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
import com.xcmgxs.xsmixfeedback.bean.ProjectFile;
import com.xcmgxs.xsmixfeedback.util.ImageLoaderUtils;
import com.xcmgxs.xsmixfeedback.util.StringUtils;
import com.xcmgxs.xsmixfeedback.widget.CircleImageView;

import java.util.List;

/**
 * Created by zhangyi on 2015-3-20.
 */
public class ProjectFileListAdapter extends MyBaseAdapter<ProjectFile> {

    private boolean IS_SHOW_PROJECT_NAME = false;

    static class ListItemView{
        public ImageView face;
        public TextView username;
        public TextView description;
        public TextView filename;
        public TextView date;
    }

    public ProjectFileListAdapter(Context context, List<ProjectFile> listData, int itemViewResource ,boolean isShowProjectName) {
        super(context, listData, itemViewResource);
        IS_SHOW_PROJECT_NAME = isShowProjectName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListItemView listItemView = null;
        if(convertView == null){
            convertView = listContainer.inflate(this.itemViewResource,null);

            listItemView = new ListItemView();
            listItemView.face = (CircleImageView)convertView.findViewById(R.id.projectfile_listitem_face);
            listItemView.date = (TextView)convertView.findViewById(R.id.projectfile_listitem_date);
            listItemView.filename = (TextView)convertView.findViewById(R.id.projectfile_listitem_name);
            listItemView.username = (TextView)convertView.findViewById(R.id.projectfile_listitem_username);
            listItemView.description = (TextView)convertView.findViewById(R.id.projectfile_listitem_description);

            convertView.setTag(listItemView);

        }
        else {
            listItemView = (ListItemView)convertView.getTag();
        }

        ProjectFile file = listData.get(position);


        // 1.加载头像
        String portraitURL = file.getUploader().getPortrait() == null ? "" : file.getUploader().getPortrait();
        if (portraitURL.endsWith("portrait.gif") || StringUtils.isEmpty(portraitURL)) {
            listItemView.face.setImageResource(R.drawable.mini_avatar);
        } else {
            portraitURL = URLs.URL_PORTRAIT + portraitURL;
            ImageLoader.getInstance().displayImage(portraitURL, listItemView.face, ImageLoaderUtils.getOption());
        }

        // 2.显示相关信息
        listItemView.username.setText(IS_SHOW_PROJECT_NAME ? file.getUploader().getName() + " - " + file.getProject().getName(): file.getUploader().getName());
        listItemView.description.setText(file.getDescription());
        listItemView.date.setText(StringUtils.friendly_time(file.getUploaddate()));
        listItemView.filename.setText(file.getFilename());

        return convertView;
    }
}
