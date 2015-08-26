package com.xcmgxs.xsmixfeedback.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.xcmgxs.xsmixfeedback.AppContext;
import com.xcmgxs.xsmixfeedback.AppException;
import com.xcmgxs.xsmixfeedback.AppManager;
import com.xcmgxs.xsmixfeedback.R;
import com.xcmgxs.xsmixfeedback.WelcomePage;
import com.xcmgxs.xsmixfeedback.api.ApiClient;
import com.xcmgxs.xsmixfeedback.bean.Notification;
import com.xcmgxs.xsmixfeedback.bean.Project;
import com.xcmgxs.xsmixfeedback.bean.ProjectDoc;
import com.xcmgxs.xsmixfeedback.bean.ProjectIssue;
import com.xcmgxs.xsmixfeedback.bean.ProjectLog;
import com.xcmgxs.xsmixfeedback.bean.User;
import com.xcmgxs.xsmixfeedback.ui.AboutActivity;
import com.xcmgxs.xsmixfeedback.ui.DocEditActivity;
import com.xcmgxs.xsmixfeedback.ui.IssueEditActivity;
import com.xcmgxs.xsmixfeedback.ui.LogEditActivity;
import com.xcmgxs.xsmixfeedback.ui.LoginActivity;
import com.xcmgxs.xsmixfeedback.ui.MainActivity;
import com.xcmgxs.xsmixfeedback.ui.MySelfInfoActivity;
import com.xcmgxs.xsmixfeedback.ui.NotificationActivity;
import com.xcmgxs.xsmixfeedback.ui.NotificationDetailActivity;
import com.xcmgxs.xsmixfeedback.ui.ProjectActivity;
import com.xcmgxs.xsmixfeedback.ui.ProjectInfoActivity;
import com.xcmgxs.xsmixfeedback.ui.ProjectReportActivity;
import com.xcmgxs.xsmixfeedback.ui.ProjectReportListActivity;
import com.xcmgxs.xsmixfeedback.ui.ProjectSomeInfoListActivity;
import com.xcmgxs.xsmixfeedback.ui.ProjectStatActivity;
import com.xcmgxs.xsmixfeedback.ui.ProjectStateActivity;
import com.xcmgxs.xsmixfeedback.ui.SearchActivity;
import com.xcmgxs.xsmixfeedback.ui.SendIssueEditActivity;
import com.xcmgxs.xsmixfeedback.ui.SettingActivity;
import com.xcmgxs.xsmixfeedback.util.FileUtils;
import com.xcmgxs.xsmixfeedback.util.ImageUtils;
import com.xcmgxs.xsmixfeedback.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import static com.xcmgxs.xsmixfeedback.common.Contanst.*;

/**
 * Created by zhangyi on 2015-3-18.
 * @author zhangyi
 */
public class UIHelper {

    private static Pattern facePattern = Pattern.compile("\\[{1}([0-9]\\d*)\\]{1}");

    /** 全局web样式 */
    public final static String WEB_STYLE = "<style>* {font-size:14px;line-height:20px;} p {color:#333;} a {color:#3E62A6;} img {max-width:310px;} "
            + "img.alignleft {float:left;max-width:120px;margin:0 10px 5px 0;border:1px solid #ccc;background:#fff;padding:2px;} "
            + "a.tag {font-size:15px;text-decoration:none;background-color:#bbd6f3;border-bottom:2px solid #3E6D8E;border-right:2px solid #7F9FB6;color:#284a7b;margin:2px 2px 2px 0;padding:2px 4px;white-space:nowrap;}</style>";

    public static void sendAppCrashReport(final Context context,final String crashReport){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setCancelable(false);
        builder.setTitle(R.string.app_error);
        builder.setMessage(R.string.app_error_message);
        builder.setPositiveButton(R.string.submit_report,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //发送异常报告
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("message/rfc822");
                        i.putExtra(Intent.EXTRA_EMAIL,new String[]{"zhangyi0037@qq.com"});
                        i.putExtra(Intent.EXTRA_SUBJECT,"XSFeedbackApp - 错误报告");
                        i.putExtra(Intent.EXTRA_TEXT,crashReport);
                        context.startActivity(Intent.createChooser(i,"发送错误报告"));
                        //退出
                        AppManager.getAppManager().AppExit(context);

                    }
                });
        builder.setNegativeButton(R.string.sure,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        AppManager.getAppManager().AppExit(context);
                    }
                });
        builder.show();
    }

    public static View.OnClickListener finish(final Activity activity){
        return new View.OnClickListener(){
            @Override
            public void onClick(View v){
                activity.finish();
            }
        };
    }

    /**
     * 弹出Toast消息
     *
     * @param msg
     */
    public static void ToastMessage(Context cont, String msg) {
        Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
    }

    public static void ToastMessage(Context cont, int msg) {
        Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
    }

    public static void ToastMessage(Context cont, String msg, int time) {
        Toast.makeText(cont, msg, time).show();
    }

    public static void sendBroadcast(Context context,Notification notification){
        if(!((AppContext)context.getApplicationContext()).isLogin() || notification == null){
            return;
        }
        Intent intent = new Intent(Contanst.INTENT_ACTION_NOTICE);
        Bundle bundle = new Bundle();
        bundle.putSerializable("notification_bean", notification);
        intent.putExtras(bundle);
        context.sendBroadcast(intent);
    }


    /**
     * 加载显示用户头像
     *
     * @param imgFace
     * @param faceURL
     */
    public static void showUserFace(final ImageView imgFace,final String faceURL) {
        showLoadImage(imgFace, faceURL, imgFace.getContext().getString(R.string.msg_load_userface_fail));
    }

    public static void showLoadImage(final ImageView imgView,final String imgURL,final String errMsg){
        //读取本地图片
        if(StringUtils.isEmpty(imgURL) || imgURL.endsWith("portrait.gif")){
            Bitmap bmp = BitmapFactory.decodeResource(imgView.getResources(),R.drawable.mini_avatar);
            imgView.setImageBitmap(bmp);
            return;
        }

        //读取缓存
        final String filename = FileUtils.getFileName(imgURL);
        String filepath = imgView.getContext().getFilesDir() + File.separator + filename;
        File file = new File(filepath);
        if(file.exists()){
            Bitmap bmp = ImageUtils.getBitmap(imgView.getContext(),filename);
            imgView.setImageBitmap(bmp);
            return;
        }

        //读取网络
        String _errString = imgView.getContext().getString(R.string.msg_load_image_fail);
        if(!StringUtils.isEmpty(errMsg)){
            _errString = errMsg;
        }
        final String _errMsg = _errString;
        final Handler handler = new Handler(){
            public void handleMessage(Message msg){
                if(msg.what == 1 && msg.obj != null){
                    imgView.setImageBitmap((Bitmap)msg.obj);
                    try {
                        ImageUtils.saveImage(imgView.getContext(),filename,(Bitmap)msg.obj);
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                }
                else {
                    ToastMessage(imgView.getContext(),_errMsg);
                }
            }
        };

        new Thread(){
            public void run(){
                Message msg = new Message();
                try {
                    Bitmap bmp = ApiClient.getNetBitmap(imgURL);
                    msg.what = 1;
                    msg.obj = bmp;
                }catch (AppException e){
                    e.printStackTrace();
                    msg.what = -1;
                    msg.obj = null;
                }
                handler.handleMessage(msg);
            }
        }.start();

    }

    public static void showMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public static void showLoginActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        //intent.putExtra("isChangeUser",true);
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void showUserInfoDetail(Context context, User user, Object o) {

    }

    public static void showSearch(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    public static void showNotification(Context context) {
        Intent intent = new Intent(context, NotificationActivity.class);
        context.startActivity(intent);
    }

    public static void showSetting(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    public static void showAbout(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        context.startActivity(intent);
    }

    /**
     * 显示用户信息详情
     *
     * @param context
     */
    public static void showMySelfInfoDetail(Context context) {
        Intent intent = new Intent(context, MySelfInfoActivity.class);
        context.startActivity(intent);
    }

    public static void showProjectDetail(Context context, Project project, String projectid) {
        Intent intent = new Intent(context, ProjectActivity.class);
        Bundle bundle = new Bundle();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        bundle.putSerializable(PROJECT,project);
        bundle.putString(PROJECTID,projectid);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void showProjectListActivity(Context context,Project project,int type){
        Intent intent = new Intent(context, ProjectSomeInfoListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Contanst.PROJECT, project);
        bundle.putInt("project_list_type", type);
        intent.putExtras(bundle);
        context.startActivity(intent);

    }

    public static void showProjectListActivity(Context context,Project project,int type,int state){
        Intent intent = new Intent(context, ProjectSomeInfoListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Contanst.PROJECT, project);
        bundle.putInt("project_list_type", type);
        bundle.putInt("project_list_state", state);
        intent.putExtras(bundle);
        context.startActivity(intent);

    }

    public static void showLogEditOrCreate(Context context,Project project,ProjectLog log){
        Intent intent = new Intent(context, LogEditActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Contanst.PROJECT, project);
        bundle.putSerializable(Contanst.PROJECT_LOG, log);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void showIssueEditOrCreate(Context context,Project project,ProjectIssue issue){
        Intent intent = new Intent(context, IssueEditActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Contanst.PROJECT, project);
        bundle.putSerializable(Contanst.PROJECT_ISSUE, issue);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void clearAppCache(SettingActivity activity) {
        final AppContext ac = (AppContext) activity.getApplication();
        final Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    ToastMessage(ac, "缓存清除成功");
                } else {
                    ToastMessage(ac, "缓存清除失败");
                }
            }
        };
        new Thread() {
            public void run() {
                Message msg = new Message();
                try {
                    ac.clearAppCache();
                    msg.what = 1;
                } catch (Exception e) {
                    e.printStackTrace();
                    msg.what = -1;
                }
                handler.sendMessage(msg);
            }
        }.start();
    }

    public static void goMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public static void showProjectInfo(Context context, Project project) {
        Intent intent = new Intent(context, ProjectInfoActivity.class);
        Bundle bundle = new Bundle();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        bundle.putSerializable(PROJECT,project);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void showProjectReportDetail(Context context, Project project, String id) {
        Intent intent = new Intent(context, ProjectReportActivity.class);
        Bundle bundle = new Bundle();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        bundle.putSerializable(PROJECT,project);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void showProjectReportListActivity(Context context) {
        Intent intent = new Intent(context, ProjectReportListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void showProjectStatActivity(Context context) {
        Intent intent = new Intent(context, ProjectStatActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void showProjectStateActivity(Context context,int year) {
        Intent intent = new Intent(context, ProjectStateActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("year", year);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void showDocEditOrCreate(Context context, Project project, ProjectDoc doc) {
        Intent intent = new Intent(context, DocEditActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Contanst.PROJECT, project);
        bundle.putSerializable(Contanst.PROJECT_DOC, doc);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void showSendIssueEditOrCreate(Context context, Project project, ProjectDoc doc) {
        Intent intent = new Intent(context, SendIssueEditActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Contanst.PROJECT, project);
        bundle.putSerializable(Contanst.PROJECT_SENDISSUE, doc);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public void showConfirmDialog(Context context,String title,String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create();
    }

    public static void showNotificationDetail(Context context, Notification notification) {
        Intent intent = new Intent(context, NotificationDetailActivity.class);
        Bundle bundle = new Bundle();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        bundle.putSerializable(Contanst.NOTIFICATOIN, notification);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
