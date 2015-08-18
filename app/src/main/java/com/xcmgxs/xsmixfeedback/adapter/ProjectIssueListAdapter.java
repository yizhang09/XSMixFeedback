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
import com.xcmgxs.xsmixfeedback.ui.ImagePreviewActivity;
import com.xcmgxs.xsmixfeedback.util.ImageLoaderUtils;
import com.xcmgxs.xsmixfeedback.util.StringUtils;
import com.xcmgxs.xsmixfeedback.widget.CircleImageView;

import java.util.List;


/**
 * Created by zhangyi on 2015-3-20.
 */
public class ProjectIssueListAdapter extends MyBaseAdapter<ProjectIssue> {

    private boolean IS_SHOW_PROJECT_NAME = false;

    private Context mContext;

    static class ListItemView{
        public ImageView face;
        public TextView username;
        public TextView content;
        public TextView type;
        public TextView date;
        public TextView isdone;
        public TextView reason;
        public TextView solution;
        public TextView response;
        public ImageView pic1;
        public ImageView pic2;
        public ImageView pic3;
        public ImageView pic4;
        public ImageView pic5;
        public TextView projectname;
    }

    public ProjectIssueListAdapter(Context context, List<ProjectIssue> listData, int itemViewResource ,boolean isShowProjectName) {
        super(context, listData, itemViewResource);
        IS_SHOW_PROJECT_NAME = isShowProjectName;
        mContext = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ListItemView listItemView = null;
        if (convertView == null) {
            convertView = listContainer.inflate(this.itemViewResource, null);

            listItemView = new ListItemView();
            listItemView.face = (CircleImageView) convertView.findViewById(R.id.projectissue_listitem_face);
            listItemView.date = (TextView) convertView.findViewById(R.id.projectissue_listitem_date);
            listItemView.content = (TextView) convertView.findViewById(R.id.projectissue_listitem_content);
            listItemView.username = (TextView) convertView.findViewById(R.id.projectissue_listitem_username);
            listItemView.projectname = (TextView) convertView.findViewById(R.id.projectissue_listitem_projectname);
            listItemView.type = (TextView) convertView.findViewById(R.id.projectissue_listitem_type);
            listItemView.isdone = (TextView) convertView.findViewById(R.id.projectissue_listitem_isdone);
            listItemView.reason = (TextView) convertView.findViewById(R.id.projectissue_listitem_reason);
            listItemView.solution = (TextView) convertView.findViewById(R.id.projectissue_listitem_solution);
            listItemView.response = (TextView) convertView.findViewById(R.id.projectissue_listitem_response);
            listItemView.pic1 = (ImageView) convertView.findViewById(R.id.projectissue_listitem_pic1);
            listItemView.pic2 = (ImageView) convertView.findViewById(R.id.projectissue_listitem_pic2);
            listItemView.pic3 = (ImageView) convertView.findViewById(R.id.projectissue_listitem_pic3);
            listItemView.pic4 = (ImageView) convertView.findViewById(R.id.projectissue_listitem_pic4);
            listItemView.pic5 = (ImageView) convertView.findViewById(R.id.projectissue_listitem_pic5);
            convertView.setTag(listItemView);

        } else {
            listItemView = (ListItemView) convertView.getTag();
        }

        final ProjectIssue issue = listData.get(position);


        // 1.加载头像
        String portraitURL = issue.getCreator().getPortrait() == null ? "" : issue.getCreator().getPortrait();
        if (portraitURL.endsWith("portrait.gif") || StringUtils.isEmpty(portraitURL)) {
            listItemView.face.setImageResource(R.drawable.mini_avatar);
        } else {
            portraitURL = URLs.URL_PORTRAIT + portraitURL;
            ImageLoader.getInstance().displayImage(portraitURL, listItemView.face, ImageLoaderUtils.getOption());
        }

        // 加载图片
        String picURL1 = issue.getPic1();
        if (StringUtils.isEmpty(picURL1)) {
            listItemView.pic1.setVisibility(View.GONE);
        } else {
            listItemView.pic1.setVisibility(View.VISIBLE);
            picURL1 = URLs.URL_UPLOAD_ISSUEPIC + picURL1;
            //bmpManager.loadBitmap(picURL, listItemView.picture);
            ImageLoader.getInstance().displayImage(picURL1, listItemView.pic1, ImageLoaderUtils.getOption());
        }
        String picUR2 = issue.getPic2();
        if (StringUtils.isEmpty(picUR2)) {
            listItemView.pic2.setVisibility(View.GONE);
        } else {
            listItemView.pic2.setVisibility(View.VISIBLE);
            picUR2 = URLs.URL_UPLOAD_ISSUEPIC + picUR2;
            //bmpManager.loadBitmap(picURL, listItemView.picture);
            ImageLoader.getInstance().displayImage(picUR2, listItemView.pic2, ImageLoaderUtils.getOption());
        }
        String picURL3 = issue.getPic3();
        if (StringUtils.isEmpty(picURL3)) {
            listItemView.pic3.setVisibility(View.GONE);
        } else {
            listItemView.pic3.setVisibility(View.VISIBLE);
            picURL3 = URLs.URL_UPLOAD_ISSUEPIC + picURL3;
            //bmpManager.loadBitmap(picURL, listItemView.picture);
            ImageLoader.getInstance().displayImage(picURL3, listItemView.pic3, ImageLoaderUtils.getOption());
        }
        String picURL4 = issue.getPic4();
        if (StringUtils.isEmpty(picURL4)) {
            listItemView.pic4.setVisibility(View.GONE);
        } else {
            listItemView.pic4.setVisibility(View.VISIBLE);
            picURL4 = URLs.URL_UPLOAD_ISSUEPIC + picURL4;
            //bmpManager.loadBitmap(picURL, listItemView.picture);
            ImageLoader.getInstance().displayImage(picURL4, listItemView.pic4, ImageLoaderUtils.getOption());
        }
        String picURL5 = issue.getPic5();
        if (StringUtils.isEmpty(picURL5)) {
            listItemView.pic5.setVisibility(View.GONE);
        } else {
            listItemView.pic5.setVisibility(View.VISIBLE);
            picURL5 = URLs.URL_UPLOAD_ISSUEPIC + picURL5;
            //bmpManager.loadBitmap(picURL, listItemView.picture);
            ImageLoader.getInstance().displayImage(picURL5, listItemView.pic5, ImageLoaderUtils.getOption());
        }

        // 2.显示相关信息
        listItemView.username.setText(issue.getCreator().getName());
        listItemView.projectname.setText(issue.getProject().getName());

        listItemView.content.setText(issue.getContent());
        listItemView.date.setText(issue.getCreatedate());
        listItemView.type.setText(issue.getType());
        listItemView.isdone.setText(issue.getState());
        if (listItemView.isdone.getText().toString().equals("已处理")) {
            listItemView.isdone.setTextColor(mContext.getResources().getColor(R.color.green));
        }

        if(!StringUtils.isEmpty(issue.getReason())) {
            listItemView.reason.setText("原因分析：" + issue.getReason());
        }
        if(!StringUtils.isEmpty(issue.getSolution())) {
            listItemView.solution.setText("解决方案：" + issue.getSolution());
        }
        if(!StringUtils.isEmpty(issue.getResponsible())) {
            listItemView.response.setText("责任归属：" + issue.getResponsible());
        }




//        listItemView.pic1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String picURL = issue.getPic1();
//                if (!StringUtils.isEmpty(picURL)) {
//                    picURL = URLs.URL_UPLOAD_ISSUEPIC + picURL;
//                    ImagePreviewActivity.showImagePreview(mContext, 0, new String[]{picURL});
//                }
//            }
//        });

        return convertView;
    }
}
