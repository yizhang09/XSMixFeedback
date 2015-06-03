package com.xcmgxs.xsmixfeedback.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xcmgxs.xsmixfeedback.R;
import com.xcmgxs.xsmixfeedback.bean.Project;
import com.xcmgxs.xsmixfeedback.bean.User;
import com.xcmgxs.xsmixfeedback.common.BitmapManager;
import com.xcmgxs.xsmixfeedback.common.UIHelper;
import com.xcmgxs.xsmixfeedback.util.ImageLoaderUtils;
import com.xcmgxs.xsmixfeedback.util.StringUtils;
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
        public ImageView languageImage;
        public TextView language;
        public TextView star;
        public TextView fork;
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
            listItemView.languageImage = (ImageView)convertView.findViewById(R.id.exploreproject_listitem_language_image);
            listItemView.language = (TextView)convertView.findViewById(R.id.exploreproject_listitem_language);
            listItemView.star = (TextView)convertView.findViewById(R.id.exploreproject_listitem_star);
            listItemView.fork = (TextView)convertView.findViewById(R.id.exploreproject_listitem_fork);

            convertView.setTag(listItemView);

        }
        else {
            listItemView = (ListItemView)convertView.getTag();
        }

        final Project project = listData.get(position);

        //加载头像
        //String portraitURL = project.getManager().getNew_portrait();
        String portraitURL = "portrait.gif";


        if(portraitURL != null) {
            if (portraitURL.endsWith("portrait.gif")) {
                listItemView.face.setImageResource(R.drawable.mini_avatar);
            } else {
                ImageLoader.getInstance().displayImage(portraitURL, listItemView.face, ImageLoaderUtils.getOption());
            }
        }
        else {
            listItemView.face.setImageResource(R.drawable.mini_avatar);
        }
        listItemView.face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                User user = project.getManager();
//                if(user == null){
//                    return;
//                }
//                UIHelper.showUserInfoDetail(context, user, null);
            }
        });

        //显示相关信息
        listItemView.title.setText(project.getPersons() + " / " + project.getName());

        String projectDesc = project.getEmState();
        if(!StringUtils.isEmpty(projectDesc)){
            listItemView.description.setText(projectDesc);
        }
        else {
            listItemView.description.setText(R.string.msg_project_empty_description);
        }

        //显示star fork信息
        listItemView.star.setText(project.getEmState());
        listItemView.fork.setText(project.getCustomer());

        return convertView;

    }
}
