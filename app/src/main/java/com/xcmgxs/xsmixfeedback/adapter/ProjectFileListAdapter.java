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
        public ImageView filetype;
        public TextView projectname;
        public TextView filename;
        public TextView date;
        public TextView uploader;
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
            listItemView.filetype = (ImageView)convertView.findViewById(R.id.projectfile_listitem_filetype);
            listItemView.date = (TextView)convertView.findViewById(R.id.projectfile_listitem_date);
            listItemView.filename = (TextView)convertView.findViewById(R.id.projectfile_listitem_name);
            listItemView.projectname = (TextView)convertView.findViewById(R.id.projectfile_listitem_projectname);
            listItemView.uploader = (TextView)convertView.findViewById(R.id.projectfile_listitem_uploader);
            convertView.setTag(listItemView);

        }
        else {
            listItemView = (ListItemView)convertView.getTag();
        }

        ProjectFile file = listData.get(position);

        String filetype = file.getFilename().split("\\.")[1];

        switch (filetype){
            case "pdf":
                listItemView.filetype.setImageResource(R.drawable.pdf);
                break;
            case "doc":
                listItemView.filetype.setImageResource(R.drawable.doc);
                break;
            case "docx":
                listItemView.filetype.setImageResource(R.drawable.doc);
                break;
            case "xls":
                listItemView.filetype.setImageResource(R.drawable.xls);
                break;
            case "xlsx":
                listItemView.filetype.setImageResource(R.drawable.xls);
                break;
            case "ppt":
                listItemView.filetype.setImageResource(R.drawable.ppt);
                break;
            case "pptx":
                listItemView.filetype.setImageResource(R.drawable.ppt);
                break;
            case "zip":
                listItemView.filetype.setImageResource(R.drawable.zip);
                break;
            default:
                listItemView.filetype.setImageResource(R.drawable.unknownfile);
                break;
        }

        // 2.显示相关信息
        listItemView.projectname.setText(file.getProject().getName());
        listItemView.uploader.setText(file.getUploader().getName());
        listItemView.date.setText(file.getUploaddate());
        listItemView.filename.setText(file.getFilename());

        return convertView;
    }
}
