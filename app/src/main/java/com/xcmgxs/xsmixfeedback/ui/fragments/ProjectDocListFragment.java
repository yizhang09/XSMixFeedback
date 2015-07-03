package com.xcmgxs.xsmixfeedback.ui.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.BaseAdapter;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.xcmgxs.xsmixfeedback.AppContext;
import com.xcmgxs.xsmixfeedback.AppException;
import com.xcmgxs.xsmixfeedback.R;
import com.xcmgxs.xsmixfeedback.adapter.ProjectDocListAdapter;
import com.xcmgxs.xsmixfeedback.api.XsFeedbackApi;
import com.xcmgxs.xsmixfeedback.bean.CommonList;
import com.xcmgxs.xsmixfeedback.bean.MessageData;
import com.xcmgxs.xsmixfeedback.bean.Project;
import com.xcmgxs.xsmixfeedback.bean.ProjectDoc;
import com.xcmgxs.xsmixfeedback.bean.ProjectFile;
import com.xcmgxs.xsmixfeedback.bean.ProjectIssue;
import com.xcmgxs.xsmixfeedback.common.Contanst;
import com.xcmgxs.xsmixfeedback.common.UIHelper;
import com.xcmgxs.xsmixfeedback.ui.basefragment.BaseSwipeRefreshFragment;
import com.xcmgxs.xsmixfeedback.util.ViewUtils;

import org.apache.http.Header;

import java.util.List;

/**
 * Created by zhangyi on 2015-06-19.
 */
public class ProjectDocListFragment extends BaseSwipeRefreshFragment<ProjectDoc,CommonList<ProjectDoc>> {


    private Project mProject;

    private AppContext mContext;

    public static ProjectDocListFragment newInstance(Project project) {
        ProjectDocListFragment fragment = new ProjectDocListFragment();
        Bundle args = new Bundle();
        args.putSerializable(Contanst.PROJECT, project);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        mProject = (Project) args.getSerializable(Contanst.PROJECT);
        super.update();
        mContext = getXsApplication();
    }

    @Override
    public BaseAdapter getAdapter(List<ProjectDoc> list) {
        return new ProjectDocListAdapter(getActivity(), list, R.layout.projectdoc_listitem);
    }

    @Override
    protected MessageData<CommonList<ProjectDoc>> asyncLoadList(int page, boolean refresh) {
        MessageData<CommonList<ProjectDoc>> msg = null;
        try {
            String pid = "-1";
            pid = mProject.getId();
            CommonList<ProjectDoc> list = getList(page, refresh,pid);
            msg = new MessageData<CommonList<ProjectDoc>>(list);
        } catch (AppException e) {
            e.makeToast(mApplication);
            e.printStackTrace();
            msg = new MessageData<CommonList<ProjectDoc>>(e);
        }
        return msg;
    }


    private CommonList<ProjectDoc> getList(int page, boolean refresh,String projectid) throws AppException {
        CommonList<ProjectDoc> list = mApplication.getProjectDocByProjectID(page, refresh, projectid);
        return list;
    }

    @Override
    public void onItemClick(int position, ProjectDoc doc) {

        UIHelper.showProjectDetail(getActivity(), null, doc.getId());
    }


    @Override
    protected boolean onItemLongClick(int position, final ProjectDoc doc) {
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setItems(new CharSequence[]{"删除"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if(mContext.getLoginUid() == doc.getUploaderid()){
                            AlertDialog builder = new AlertDialog.Builder(getActivity())
                                    .setTitle("删除单据")
                                    .setMessage("确认删除？")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            delDoc(doc);
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
                        else {
                            new AlertDialog.Builder(getActivity())
                                    .setTitle("删除单据")
                                    .setMessage("只可以删除自己创建的单据！")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).show();
                        }
                    }
                }).create();
        dialog.show();
        return true;
    }

    private void delDoc(ProjectDoc doc){
        XsFeedbackApi.delDoc(doc.getId(), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                ViewUtils.showToast("删除成功");
                updateDoc();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                ViewUtils.showToast("删除失败");
            }
        });
    }

    private void updateDoc(){
        super.update();
    }
}
