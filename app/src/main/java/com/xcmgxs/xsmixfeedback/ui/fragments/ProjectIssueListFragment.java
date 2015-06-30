package com.xcmgxs.xsmixfeedback.ui.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.BaseAdapter;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.xcmgxs.xsmixfeedback.AppException;
import com.xcmgxs.xsmixfeedback.R;
import com.xcmgxs.xsmixfeedback.adapter.ProjectIssueListAdapter;
import com.xcmgxs.xsmixfeedback.api.ApiClient;
import com.xcmgxs.xsmixfeedback.api.URLs;
import com.xcmgxs.xsmixfeedback.api.XsFeedbackApi;
import com.xcmgxs.xsmixfeedback.bean.CommonList;
import com.xcmgxs.xsmixfeedback.bean.MessageData;
import com.xcmgxs.xsmixfeedback.bean.Project;
import com.xcmgxs.xsmixfeedback.bean.ProjectIssue;
import com.xcmgxs.xsmixfeedback.bean.ProjectLog;
import com.xcmgxs.xsmixfeedback.common.Contanst;
import com.xcmgxs.xsmixfeedback.ui.ImagePreviewActivity;
import com.xcmgxs.xsmixfeedback.ui.basefragment.BaseFragment;
import com.xcmgxs.xsmixfeedback.ui.basefragment.BaseSwipeRefreshFragment;
import com.xcmgxs.xsmixfeedback.util.StringUtils;
import com.xcmgxs.xsmixfeedback.util.ViewUtils;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyi on 2015-4-16.
 */
public class ProjectIssueListFragment extends BaseSwipeRefreshFragment<ProjectIssue,CommonList<ProjectIssue>> {
    public final int MENU_CREATE_ID = 01;

    private Project mProject;

    private static boolean IS_ALL = false;

    public static ProjectIssueListFragment newInstance(Project project) {
        ProjectIssueListFragment fragment = new ProjectIssueListFragment();
        Bundle args = new Bundle();
        args.putSerializable(Contanst.PROJECT, project);
        fragment.setArguments(args);
        IS_ALL = false;
        return fragment;
    }

    public static ProjectIssueListFragment newInstance() {
        ProjectIssueListFragment fragment = new ProjectIssueListFragment();
        IS_ALL = true;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(!IS_ALL) {
            mProject = (Project) args.getSerializable(Contanst.PROJECT);
        }
        super.update();
    }

    @Override
    public BaseAdapter getAdapter(List<ProjectIssue> list) {
        return new ProjectIssueListAdapter(getActivity(), list, R.layout.projectissue_listitem,IS_ALL);
    }

    @Override
    protected MessageData<CommonList<ProjectIssue>> asyncLoadList(int page, boolean refresh) {
        MessageData<CommonList<ProjectIssue>> msg = null;
        try {
            String pid = "-1";
            if(!IS_ALL){
                pid = mProject.getId();
            }
            CommonList<ProjectIssue> list = getList(page, refresh,pid);
            msg = new MessageData<CommonList<ProjectIssue>>(list);
        } catch (AppException e) {
            e.makeToast(mApplication);
            e.printStackTrace();
            msg = new MessageData<CommonList<ProjectIssue>>(e);
        }
        return msg;
    }

    private CommonList<ProjectIssue> getList(int page, boolean refresh,String projectid) throws AppException {
        CommonList<ProjectIssue> list = mApplication.getProjectIssuesByProjectID(page, refresh, projectid);
        return list;
    }


    @Override
    public void onItemClick(int position, ProjectIssue issue) {

        ArrayList<String> arrUrls = new ArrayList<String>();
        if (!StringUtils.isEmpty(issue.getPic1())) {
            String url = URLs.URL_UPLOAD_ISSUEPIC + issue.getPic1();
            arrUrls.add(url);
        }
        if (!StringUtils.isEmpty(issue.getPic2())) {
            String url = URLs.URL_UPLOAD_ISSUEPIC + issue.getPic2();
            arrUrls.add(url);
        }
        if (!StringUtils.isEmpty(issue.getPic3())) {
            String url = URLs.URL_UPLOAD_ISSUEPIC + issue.getPic3();
            arrUrls.add(url);
        }
        if (!StringUtils.isEmpty(issue.getPic4())) {
            String url = URLs.URL_UPLOAD_ISSUEPIC + issue.getPic4();
            arrUrls.add(url);
        }
        if (!StringUtils.isEmpty(issue.getPic5())) {
            String url = URLs.URL_UPLOAD_ISSUEPIC + issue.getPic5();
            arrUrls.add(url);
        }
        String[] picURL = arrUrls.toArray(new String[arrUrls.size()]);
        if(picURL.length > 0) {
            ImagePreviewActivity.showImagePreview(getActivity(), 0, picURL);
        }

    }


    @Override
    protected boolean onItemLongClick(int position, final ProjectIssue issue) {
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setItems(new CharSequence[]{"删除"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        AlertDialog builder = new AlertDialog.Builder(getActivity())
                                .setTitle("删除反馈")
                                .setMessage("确认删除？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        delIssue(issue);
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).create();
                        builder.show();
                    }
                }).create();
        dialog.show();
        return true;
    }

    private void delIssue(ProjectIssue issue){
        XsFeedbackApi.delIssue(issue.getId(), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                ViewUtils.showToast("删除成功");
                updateIssue();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                ViewUtils.showToast("删除失败");
            }
        });
    }

    private void updateIssue(){
        super.update();
    }
}
