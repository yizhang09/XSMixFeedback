package com.xcmgxs.xsmixfeedback.api;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xcmgxs.xsmixfeedback.AppContext;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URLEncoder;

import static com.xcmgxs.xsmixfeedback.api.AsyncHttpHelper.get;
import static com.xcmgxs.xsmixfeedback.api.AsyncHttpHelper.getHttpClient;
import static com.xcmgxs.xsmixfeedback.api.AsyncHttpHelper.getPrivateTokenWithParams;
import static com.xcmgxs.xsmixfeedback.api.AsyncHttpHelper.post;

/**
 * Created by zhangyi on 2015-06-02.
 */
public class XsFeedbackApi {

    public final static String HTTPS = "https://";
    public final static String HTTP = "http://";

    public final static String HOST = "122.194.137.62:8002";

    public final static String BASE_URL = HTTP + HOST + "/api";
    public final static String PROJECT = BASE_URL + "/project";
    public final static String PROJECT_ISSUE = BASE_URL + "/projectissue";
    public final static String PROJECT_LOG = BASE_URL + "/projectlog";
    public final static String USER = BASE_URL + "/user";
    public final static String UPLOAD = BASE_URL + "/upload";
    public final static String NOTIFICATION = BASE_URL + "/notification";
    public final static String VERSION = BASE_URL + "/app_version/new/android";


    /**
     * 创建一个issue
     *
     * @param projectId
     * @param title
     * @param description
     * @param type
     * @return
     */
    public static void pubCreateIssue(String projectId, String title, String description, String type, AsyncHttpResponseHandler handler) {
        RequestParams params = getPrivateTokenWithParams();
        params.put("Content", description);
        params.put("Title", title);
        params.put("Type", type);
        params.put("ProjectID", projectId);
        params.put("CreatorID", AppContext.getInstance().getLoginUid());
        post(PROJECT_ISSUE, params, handler);
    }

    /**
     * 上传文件
     *
     * @return
     */
    public static void upLoadFile(File file, AsyncHttpResponseHandler handler) throws FileNotFoundException {
        RequestParams params = getPrivateTokenWithParams();
        params.put("file", file);
        AsyncHttpClient client = getHttpClient();
        client.addHeader("Content-disposition", "filename=\"" + file.getName() + "\"");
        client.post(UPLOAD, params, handler);
    }

    /***
     * 更新用户头像
     * @param protraitUrl
     * @param handler
     */
    public static void updateUserProtrait(String protraitUrl, AsyncHttpResponseHandler handler) {
        RequestParams params = getPrivateTokenWithParams();
        params.put("path", protraitUrl);
        post(USER + "portrait", params, handler);
    }


    /**
     * 获得App更新的信息
     */
    public static void getUpdateInfo(AsyncHttpResponseHandler handler) {
        get(VERSION, handler);
    }

    //获得通知信息
    public static void getNotification(String all,AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", AppContext.getInstance().getLoginUid());
        params.put("all", all);
        AsyncHttpHelper.get(NOTIFICATION, params, handler);
    }


    /**
     * 设置通知为已读
     */
    public static void setNotificationReaded(String notificationId, AsyncHttpResponseHandler handler) {
        RequestParams params = getPrivateTokenWithParams();
        params.put("id", notificationId);
        params.put("isRead", true);
        AsyncHttpHelper.put(NOTIFICATION, params, handler);
    }


    public static void searchProjects(String query, int page, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("page", page);
        params.put("query", query);
        get(PROJECT, params, handler);
    }

}
