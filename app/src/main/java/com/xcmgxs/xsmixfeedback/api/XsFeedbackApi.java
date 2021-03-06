package com.xcmgxs.xsmixfeedback.api;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xcmgxs.xsmixfeedback.AppContext;
import com.xcmgxs.xsmixfeedback.AppException;
import com.xcmgxs.xsmixfeedback.bean.ProjectIssue;
import com.xcmgxs.xsmixfeedback.bean.ProjectLog;
import com.xcmgxs.xsmixfeedback.bean.ProjectSendIssue;
import com.xcmgxs.xsmixfeedback.util.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URLEncoder;

import static com.xcmgxs.xsmixfeedback.api.AsyncHttpHelper.delete;
import static com.xcmgxs.xsmixfeedback.api.AsyncHttpHelper.get;
import static com.xcmgxs.xsmixfeedback.api.AsyncHttpHelper.getHttpClient;
import static com.xcmgxs.xsmixfeedback.api.AsyncHttpHelper.getPrivateTokenWithParams;
import static com.xcmgxs.xsmixfeedback.api.AsyncHttpHelper.post;
import static com.xcmgxs.xsmixfeedback.api.AsyncHttpHelper.put;

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
    public final static String PROJECT_SENDISSUE = BASE_URL + "/projectsendissue";
    public final static String PROJECT_LOG = BASE_URL + "/projectlog";
    public final static String PROJECT_DOC = BASE_URL + "/projectdoc";
    public final static String USER = BASE_URL + "/user";
    public final static String ROLE = BASE_URL + "/role";
    public final static String UPLOAD = BASE_URL + "/upload";
    public final static String NOTIFICATION = BASE_URL + "/notification";
    public final static String VERSION = BASE_URL + "/update";

    public final static String STAT = BASE_URL + "/stat";


    /**
     * 创建一个issue
     *
     * @param log
     * @return
     */
    public static void pubCreateLog(ProjectLog log,File[] imgFiles, AsyncHttpResponseHandler handler) {
        try {
            RequestParams params = getPrivateTokenWithParams();
            params.put("msg", log.getContent());
            params.put("projectid", log.getProjectid());
            params.put("createdate", log.getCreatedate());
            params.put("type", log.getType());
            params.put("step", log.getStep());
            params.put("pstate", log.getPstate());
            for (int i = 0; i < imgFiles.length; i++) {
                if (imgFiles[i] != null) {
                    params.put("file" + i, imgFiles[i]);
                }
            }

            params.put("uid", AppContext.getInstance().getLoginUid());
            post(PROJECT_LOG, params, handler);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 创建一个issue
     *
     * @param projectId
     * @param issue
     * @return
     */
    public static void pubCreateIssue(String projectId,ProjectIssue issue, File[] imgFiles, AsyncHttpResponseHandler handler) {
        try {
            RequestParams params = getPrivateTokenWithParams();
            params.put("Content", issue.getContent());
            params.put("Title",issue.getTitle());
            params.put("Type", issue.getType());
            params.put("PreReason", issue.getPreReason());
            params.put("Advice", issue.getAdvice());
            params.put("Reason", issue.getReason());
            params.put("Solution", issue.getSolution());
            params.put("State", issue.getState());
            params.put("ProjectID", projectId);
            for (int i = 0; i < imgFiles.length; i++) {
                if (imgFiles[i] != null) {
                    params.put("file" + i, imgFiles[i]);
                }
            }
            params.put("CreatorID", AppContext.getInstance().getLoginUid());
            post(PROJECT_ISSUE, params, handler);
        }catch(Exception e){
            e.printStackTrace();
        }

    }


    /**
     * 创建一个issue
     *
     * @param projectId
     * @param issue
     * @return
     */
    public static void pubCreateSendIssue(String projectId,ProjectSendIssue issue, File[] imgFiles, AsyncHttpResponseHandler handler) {
        try {
            RequestParams params = getPrivateTokenWithParams();
            params.put("MaterialName", issue.getMaterialName());
            params.put("ListNum",issue.getListNum());
            params.put("MaterialNo", issue.getMaterialNo());
            params.put("WrongNum", issue.getWrongNum());
            params.put("State", issue.getState());
            params.put("ProjectID", projectId);
            for (int i = 0; i < imgFiles.length; i++) {
                if (imgFiles[i] != null) {
                    params.put("file" + i, imgFiles[i]);
                }
            }
            params.put("CreatorID", AppContext.getInstance().getLoginUid());
            post(PROJECT_SENDISSUE, params, handler);
        }catch(Exception e){
            e.printStackTrace();
        }

    }


    /**
     * 创建一个doc
     *
     * @param projectId
     * @param name
     * @param desc
     * @param type
     * @return
     */
    public static void pubCreateDoc(String projectId,String desc,String name, String type,File imgFile, AsyncHttpResponseHandler handler) {
        try {
            RequestParams params = getPrivateTokenWithParams();
            params.put("Description", desc);
            params.put("Name", name);
            params.put("Type", type);
            params.put("ProjectID", projectId);
            if(imgFile != null) {
                params.put("img", imgFile);
            }
            params.put("UploaderID", AppContext.getInstance().getLoginUid());
            post(PROJECT_DOC, params, handler);
        }catch(Exception e){
            e.printStackTrace();
        }

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
    public static void getNotification(AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", AppContext.getInstance().getLoginUid());
        AsyncHttpHelper.get(NOTIFICATION, params, handler);
    }


    public static void getNotification(boolean isRead,AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", AppContext.getInstance().getLoginUid());
        params.put("isRead", isRead);
        AsyncHttpHelper.get(NOTIFICATION, params, handler);
    }

    public static void clearNotice(int uid, int type, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("type", type);
        AsyncHttpHelper.post(NOTIFICATION, params, handler);
    }

    /**
     * 设置通知为已读
     */
    public static void setNotificationReaded(String notificationId, AsyncHttpResponseHandler handler) {
        AsyncHttpHelper.put(NOTIFICATION + "/" + notificationId, handler);
    }


    public static void searchProjects(String query, int page, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("page", page);
        params.put("query", query);
        get(PROJECT, params, handler);
    }

    public static void delLog(String id, AsyncHttpResponseHandler handler) {
        try {
            delete(PROJECT_LOG + "/" + id, handler);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void delIssue(String id, AsyncHttpResponseHandler handler) {
        try {
            delete(PROJECT_ISSUE + "/" + id, handler);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void changeIssue(String id,String state, AsyncHttpResponseHandler handler) {
        try {
            RequestParams params = new RequestParams();
            params.put("id", id);
            params.put("state", state);
            put(PROJECT_ISSUE, params, handler);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void delSendIssue(String id, AsyncHttpResponseHandler handler) {
        try {
            delete(PROJECT_SENDISSUE + "/" + id, handler);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void changeSendIssue(String id,String state, AsyncHttpResponseHandler handler) {
        try {
            RequestParams params = new RequestParams();
            params.put("id", id);
            params.put("state", state);
            put(PROJECT_SENDISSUE,params,handler);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void delDoc(String id, AsyncHttpResponseHandler handler) {
        try {
            delete(PROJECT_DOC + "/" + id, handler);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void getStatData(String pid,String category,AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        if(!StringUtils.isEmpty(pid)) {
            params.put("pid", pid);
        }
        params.put("category", category);
        AsyncHttpHelper.get(STAT, params, handler);
    }

    public static void getStatData(int year,String category,AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("year", year);
        params.put("category", category);
        AsyncHttpHelper.get(STAT, params, handler);
    }

    public static void getUserRole(int uid,AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("userid", uid);
        AsyncHttpHelper.get(ROLE, params, handler);
    }


}
