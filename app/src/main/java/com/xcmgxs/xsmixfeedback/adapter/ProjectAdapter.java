package com.xcmgxs.xsmixfeedback.adapter;

import android.content.Context;

import com.xcmgxs.xsmixfeedback.R;
import com.xcmgxs.xsmixfeedback.adapter.base.CommonAdapter;
import com.xcmgxs.xsmixfeedback.adapter.base.ViewHolder;
import com.xcmgxs.xsmixfeedback.bean.Project;

/**
 * Created by zhangyi on 2015-06-05.
 */
public class ProjectAdapter extends CommonAdapter<Project> {

    public ProjectAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void convert(ViewHolder vh, Project project) {
        // 2.显示相关信息
        vh.setText(R.id.exploreproject_listitem_title, project.getPersons() + " / " + project.getName());

        // 判断是否有项目的介绍
        vh.setText(R.id.exploreproject_listitem_description, project.getEmState(), R.string.msg_project_empty_description);

        // 1.加载头像
        //String portraitURL = project.getOwner().getNew_portrait();
        String portraitURL = "portrait.gif";
        if (portraitURL.endsWith("portrait.gif")) {
            vh.setImageResource(R.id.exploreproject_listitem_userface, R.drawable.mini_avatar);
        } else {
            vh.setImageForUrl(R.id.exploreproject_listitem_userface, portraitURL);
        }

        vh.setText(R.id.exploreproject_listitem_customer,project.getCustomer());
        vh.setText(R.id.exploreproject_listitem_state,project.getEmState());
        vh.setText(R.id.exploreproject_listitem_station, project.getNum().toString());

//        vh.getView(R.id.iv_portrait).setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                User user = project.getOwner();
//                if (user == null) {
//                    return;
//                }
//                UIHelper.showUserInfoDetail(mContext, user, null);
//            }
//        });
    }
}
