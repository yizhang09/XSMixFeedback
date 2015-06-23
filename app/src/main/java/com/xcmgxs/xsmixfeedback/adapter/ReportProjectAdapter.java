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
import com.xcmgxs.xsmixfeedback.bean.Project;
import com.xcmgxs.xsmixfeedback.util.ImageLoaderUtils;
import com.xcmgxs.xsmixfeedback.util.StringUtils;
import com.xcmgxs.xsmixfeedback.widget.CircleImageView;

import java.util.List;

/**
 * Created by zhangyi on 2015-06-18.
 */
public class ReportProjectAdapter extends MyBaseAdapter<Project> {

    static class ListItemView {
        public TextView title;
        public TextView manager;
        public ImageView face;
        public TextView description;
        public ImageView stepImage;
        public TextView starttime;
    }
    public ReportProjectAdapter(Context context, List<Project> listData, int itemViewResource) {
        super(context, listData, itemViewResource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ListItemView listItemView = null;
        if(convertView == null){
            convertView = listContainer.inflate(this.itemViewResource,null);

            listItemView = new ListItemView();
            listItemView.title = (TextView)convertView.findViewById(R.id.projectreport_listitem_title);
            listItemView.manager = (TextView)convertView.findViewById(R.id.projectreport_listitem_username);
            listItemView.face = (CircleImageView)convertView.findViewById(R.id.projectreport_listitem_face);
            listItemView.description = (TextView)convertView.findViewById(R.id.projectreport_listitem_description);
            listItemView.stepImage = (ImageView)convertView.findViewById(R.id.report_list_item_step_image);
            listItemView.starttime = (TextView)convertView.findViewById(R.id.projectreport_listitem_time);
            convertView.setTag(listItemView);

        }
        else {
            listItemView = (ListItemView)convertView.getTag();
        }

        final Project project = listData.get(position);

        //加载头像
        String portraitURL = project.getManager()!=null? project.getManager().getPortrait():"portrait.gif";

        if(portraitURL != null) {
            if (portraitURL.endsWith("portrait.gif")) {
                listItemView.face.setImageResource(R.drawable.mini_avatar);
            } else {
                portraitURL = URLs.URL_PORTRAIT + portraitURL;
                ImageLoader.getInstance().displayImage(portraitURL, listItemView.face, ImageLoaderUtils.getOption());
            }
        }
        else {
            listItemView.face.setImageResource(R.drawable.mini_avatar);
        }

        listItemView.title.setText(project.getName());
        String managerName = project.getManager()!=null?project.getManager().getName():"";
        listItemView.manager.setText(managerName);
        //listItemView.starttime.setText(project.getPackagedate());
        String projectDesc = project.getAddress();
        if(!StringUtils.isEmpty(projectDesc)){
            listItemView.description.setText(projectDesc);
        }
        else {
            listItemView.description.setText(R.string.msg_project_empty_description);
        }

        switch (project.getState()){
            case "基础未做":
                listItemView.stepImage.setImageResource(R.drawable.step1);
                break;
            case "基础制作":
                listItemView.stepImage.setImageResource(R.drawable.step2);
                break;
            case "筒仓施工":
                listItemView.stepImage.setImageResource(R.drawable.step3);
                break;
            case "正在发车":
                listItemView.stepImage.setImageResource(R.drawable.step4);
                break;
            case "正在安装":
                listItemView.stepImage.setImageResource(R.drawable.step5);
                break;
            case "安装完毕":
                listItemView.stepImage.setImageResource(R.drawable.step6);
                break;
            case "签字验收":
                listItemView.stepImage.setImageResource(R.drawable.step7);
                break;
        }

        return convertView;
    }
}
