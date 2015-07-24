package com.xcmgxs.xsmixfeedback.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xcmgxs.xsmixfeedback.R;
import com.xcmgxs.xsmixfeedback.adapter.base.MyBaseAdapter;
import com.xcmgxs.xsmixfeedback.api.URLs;
import com.xcmgxs.xsmixfeedback.bean.Project;
import com.xcmgxs.xsmixfeedback.bean.User;
import com.xcmgxs.xsmixfeedback.common.BitmapManager;
import com.xcmgxs.xsmixfeedback.common.UIHelper;
import com.xcmgxs.xsmixfeedback.util.ImageLoaderUtils;
import com.xcmgxs.xsmixfeedback.util.StringUtils;
import com.xcmgxs.xsmixfeedback.util.TLog;
import com.xcmgxs.xsmixfeedback.widget.CircleImageView;

import java.util.List;

/**
 * @author zhangyi
 * Created by zhangyi on 2015-3-20.
 */
public class MyProjectListAdapter extends MyBaseAdapter<Project> {

    private BitmapManager bitmapManager;

    static class ListItemView {
        public CircleImageView face;
        public TextView title;
        public TextView description;
        public TextView type;
        public TextView state;
        public TextView manager;
        public ImageView statepic;
        public ImageView isstop;
    }

    public MyProjectListAdapter(Context context, List<Project> listData, int itemViewResource) {
        super(context, listData, itemViewResource);
        this.bitmapManager = new BitmapManager(BitmapFactory.decodeResource(context.getResources(), R.drawable.widget_dface_loading));

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ListItemView listItemView = null;
        if(convertView == null){
            convertView = listContainer.inflate(this.itemViewResource,null);

            listItemView = new ListItemView();

            listItemView.face = (CircleImageView)convertView.findViewById(R.id.exploreproject_listitem_userface);
            listItemView.title = (TextView)convertView.findViewById(R.id.exploreproject_listitem_title);
            listItemView.description = (TextView)convertView.findViewById(R.id.exploreproject_listitem_description);
            listItemView.type = (TextView)convertView.findViewById(R.id.exploreproject_listitem_type);
            listItemView.state = (TextView)convertView.findViewById(R.id.exploreproject_listitem_statename);
            listItemView.manager = (TextView)convertView.findViewById(R.id.exploreproject_listitem_manager);
            listItemView.statepic = (ImageView)convertView.findViewById(R.id.exploreproject_listitem_state);
            listItemView.isstop = (ImageView)convertView.findViewById(R.id.exploreproject_listitem_isstop);
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
        listItemView.face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = project.getManager();
                if(user == null){
                    return;
                }
                UIHelper.showUserInfoDetail(context, user, null);
            }
        });

        //显示相关信息
        String managerName = project.getManager()!=null?project.getManager().getName():"";
        listItemView.manager.setText(managerName);
        listItemView.title.setText(project.getName());

        String projectDesc = project.getAddress();
        if(!StringUtils.isEmpty(projectDesc)){
            listItemView.description.setText(projectDesc);
        }
        else {
            listItemView.description.setText(R.string.msg_project_empty_description);
        }

        listItemView.type.setText(project.getType());
        listItemView.state.setText(project.getState());

        switch (project.getState()) {
            case "基础未做":
                listItemView.statepic.setImageResource(R.drawable.s1);
                break;
            case "基础制作":
                listItemView.statepic.setImageResource(R.drawable.s2);
                break;
            case "筒仓施工":
                listItemView.statepic.setImageResource(R.drawable.s3);
                break;
            case "正在发车":
                listItemView.statepic.setImageResource(R.drawable.s4);
                break;
            case "正在安装":
                listItemView.statepic.setImageResource(R.drawable.s5);
                break;
            case "安装完毕":
                listItemView.statepic.setImageResource(R.drawable.s6);
                break;
            case "签字验收":
                listItemView.statepic.setImageResource(R.drawable.s7);
                break;
        }

        if(project.isStop()){
            listItemView.isstop.setVisibility(View.VISIBLE);
        }
        else {
            listItemView.isstop.setVisibility(View.GONE);
        }

        return convertView;

    }
}
