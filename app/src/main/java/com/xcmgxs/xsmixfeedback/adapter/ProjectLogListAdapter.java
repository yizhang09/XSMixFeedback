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
        public ImageView picture1;
        public ImageView picture2;
        public ImageView picture3;
        public ImageView picture4;
        public ImageView picture5;
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
            listItemView.picture1 = (ImageView)convertView.findViewById(R.id.projectlog_listitem_pic1);
            listItemView.picture2 = (ImageView)convertView.findViewById(R.id.projectlog_listitem_pic2);
            listItemView.picture3 = (ImageView)convertView.findViewById(R.id.projectlog_listitem_pic3);
            listItemView.picture4 = (ImageView)convertView.findViewById(R.id.projectlog_listitem_pic4);
            listItemView.picture5 = (ImageView)convertView.findViewById(R.id.projectlog_listitem_pic5);
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
        String picURL1 = log.getPic1();
        if (StringUtils.isEmpty(picURL1)) {
            listItemView.picture1.setVisibility(View.GONE);
        } else {
            listItemView.picture1.setVisibility(View.VISIBLE);
            picURL1 = URLs.URL_UPLOAD_LOGPIC + picURL1;
            //bmpManager.loadBitmap(picURL, listItemView.picture);
            ImageLoader.getInstance().displayImage(picURL1, listItemView.picture1, ImageLoaderUtils.getOption());
        }

        String picURL2 = log.getPic2();
        if (StringUtils.isEmpty(picURL2)) {
            listItemView.picture2.setVisibility(View.GONE);
        } else {
            listItemView.picture2.setVisibility(View.VISIBLE);
            picURL2 = URLs.URL_UPLOAD_LOGPIC + picURL2;
            //bmpManager.loadBitmap(picURL, listItemView.picture);
            ImageLoader.getInstance().displayImage(picURL2, listItemView.picture2, ImageLoaderUtils.getOption());
        }

        String picURL3 = log.getPic3();
        if (StringUtils.isEmpty(picURL3)) {
            listItemView.picture3.setVisibility(View.GONE);
        } else {
            listItemView.picture3.setVisibility(View.VISIBLE);
            picURL3 = URLs.URL_UPLOAD_LOGPIC + picURL3;
            //bmpManager.loadBitmap(picURL, listItemView.picture);
            ImageLoader.getInstance().displayImage(picURL3, listItemView.picture3, ImageLoaderUtils.getOption());
        }

        String picURL4 = log.getPic4();
        if (StringUtils.isEmpty(picURL4)) {
            listItemView.picture4.setVisibility(View.GONE);
        } else {
            listItemView.picture4.setVisibility(View.VISIBLE);
            picURL4 = URLs.URL_UPLOAD_LOGPIC + picURL4;
            //bmpManager.loadBitmap(picURL, listItemView.picture);
            ImageLoader.getInstance().displayImage(picURL4, listItemView.picture4, ImageLoaderUtils.getOption());
        }

        String picURL5 = log.getPic5();
        if (StringUtils.isEmpty(picURL5)) {
            listItemView.picture5.setVisibility(View.GONE);
        } else {
            listItemView.picture5.setVisibility(View.VISIBLE);
            picURL5 = URLs.URL_UPLOAD_LOGPIC + picURL5;
            //bmpManager.loadBitmap(picURL, listItemView.picture);
            ImageLoader.getInstance().displayImage(picURL5, listItemView.picture5, ImageLoaderUtils.getOption());
        }

        // 2.显示相关信息
        listItemView.username.setText(log.getAuthor().getName());
        listItemView.projectname.setText(log.getProject().getName());
        listItemView.content.setText(log.getContent());
        listItemView.date.setText(log.getCreatedate());

//        listItemView.picture.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String picURL = log.getPic();
//                if (!StringUtils.isEmpty(picURL)) {
//                    picURL = URLs.URL_UPLOAD_LOGPIC + picURL;
//                    ImagePreviewActivity.showImagePreview(mContext, 0, new String[]{picURL});
//                }
//            }
//        });

        return convertView;
    }
}
