package com.xcmgxs.xsmixfeedback.api;

import android.graphics.Bitmap;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.xcmgxs.xsmixfeedback.AppContext;
import com.xcmgxs.xsmixfeedback.AppException;
import com.xcmgxs.xsmixfeedback.bean.CommonList;
import com.xcmgxs.xsmixfeedback.bean.Project;
import com.xcmgxs.xsmixfeedback.bean.ProjectDoc;
import com.xcmgxs.xsmixfeedback.bean.ProjectFile;
import com.xcmgxs.xsmixfeedback.bean.ProjectIssue;
import com.xcmgxs.xsmixfeedback.bean.ProjectLog;
import com.xcmgxs.xsmixfeedback.bean.Result;
import com.xcmgxs.xsmixfeedback.bean.User;
import com.xcmgxs.xsmixfeedback.util.CyptoUtils;
import com.xcmgxs.xsmixfeedback.util.StringUtils;
import com.xcmgxs.xsmixfeedback.util.TLog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangyi
 * Created by zhangyi on 2015-3-18.
 */
public class ApiClient {

    private final static String PRIVATE_TOKEN = "private_token";
    public final static String XCMGXS_PRIVATE_TOKEN = "feedback@xcmgxs";

    public static final int PAGE_SIZE = 20;// 默认分页大小
    private static final int CACHE_TIME = 60 * 60000;// 缓存失效时间

    // 私有token，每个用户都有一个唯一的
    private static String private_token;

    public static void cleanToken() {
        private_token = "";
    }

    public static String getToken(AppContext appContext){
        if(private_token == null||private_token == ""){
            private_token = appContext.getProperty(PRIVATE_TOKEN);
        }
        return CyptoUtils.decode(XCMGXS_PRIVATE_TOKEN, private_token);
    }

    private static HTTPRequestor getHttpRequester(){
        return new HTTPRequestor();
    }

    /**
     * 给一个url拼接参数
     *
     * @param p_url
     * @param params
     * @return
     */
    private static String makeURL(String p_url,Map<String,Object> params){
        StringBuilder url = new StringBuilder(p_url);
        if(params.size() == 0){
            return p_url;
        }
        if(url.indexOf("?") < 0){
            url.append('?');
        }
        for (String name : params.keySet()) {
            String value = String.valueOf(params.get(name));
            if (value != null && !StringUtils.isEmpty(value)
                    && !value.equalsIgnoreCase("null")) {
                url.append('&');
                url.append(name);
                url.append('=');
                // 对参数进行编码
                try {
                    url.append(URLEncoder.encode(
                            String.valueOf(params.get(name)), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return url.toString().replace("?&", "?");
    }

    public static Result uploadFile(AppContext appContext,File file) throws AppException,IOException{
        Map<String,Object> params = new HashMap<String,Object>();
        params.put(PRIVATE_TOKEN,getToken(appContext));
        Map<String,File> files = new HashMap<String,File>();
        files.put("file",file);
        //String url = makeURL(URLs.UPLOAD,params);
        return getHttpRequester().http_post(appContext, URLs.UPLOAD, params, files);
        //return getHttpRequester().init(appContext,HTTPRequestor.POST_METHOD,url).with("file",file).to(UpLoadFile.class);
    }

    public static User login(AppContext appContext, String username, String pwd) throws AppException {
        Map<String,Object> params = new HashMap<>();
        params.put(PRIVATE_TOKEN,getToken(appContext));
        params.put("username",username);
        params.put("password",pwd);
        String urlString = makeURL(URLs.LOGIN_HTTP,params);
        User session = getHttpRequester().init(appContext, HTTPRequestor.GET_METHOD, urlString).to(User.class);

        if (session != null ){
            String token = CyptoUtils.encode(XCMGXS_PRIVATE_TOKEN,session.getId());
            appContext.setProperty(PRIVATE_TOKEN, token);
        }

        return session;
    }

    /**
     * 获得一个用户的信息
     *
     * @param context
     * @param userId
     * @return
     * @throws AppException
     */
    public static User getUser(AppContext context,int userId) throws AppException{
        String url = URLs.USER + URLs.URL_SPLITTER + userId;
        return getHttpRequester().init(context,HTTPRequestor.GET_METHOD,url).to(User.class);
    }

    public static Bitmap getNetBitmap(String url) throws AppException {
        return getHttpRequester().init(null,HTTPRequestor.GET_METHOD,url).getNetBitmap();
    }

    /**
     * 获得一个项目的信息
     *
     * @param appContext
     * @param projectId
     * @return
     * @throws AppException
     */
    @SuppressWarnings("serial")
    public static Project getProject(final AppContext appContext,String projectId) throws AppException {
        String url = makeURL(URLs.PROJECT + URLs.URL_SPLITTER + projectId,
                new HashMap<String, Object>(){
                    {
                        put(PRIVATE_TOKEN,getToken(appContext));
                    }
        });
        return getHttpRequester().init(appContext,HTTPRequestor.GET_METHOD,url).to(Project.class);
    }

    /**
     * 获得具体用户的项目列表
     * @param appContext
     * @param page
     * @return
     * @throws AppException
     */
    public static CommonList<Project> getUserProjects(final AppContext appContext,int page) throws AppException {
        CommonList<Project> lst = new CommonList<Project>();
        Map<String,Object> params = new HashMap<>();
        params.put(PRIVATE_TOKEN,getToken(appContext));
        params.put("page",page);
        params.put("mode","MY");
        String url = makeURL(URLs.PROJECT,params);
        List<Project> list = getHttpRequester().init(appContext,HTTPRequestor.GET_METHOD,url).getList(Project[].class);
        lst.setCount(list.size());
        lst.setList(list);
        lst.setPageSize(list.size());
        return lst;
    }

    public static CommonList<Project> getAllProjects(final AppContext appContext, int page) throws AppException {
        CommonList<Project> lst = new CommonList<Project>();
        Map<String,Object> params = new HashMap<>();
        params.put(PRIVATE_TOKEN,getToken(appContext));
        params.put("page",page);
        params.put("mode","ALL");
        String url = makeURL(URLs.PROJECT,params);
        List<Project> list = getHttpRequester().init(appContext,HTTPRequestor.GET_METHOD,url).getList(Project[].class);
        lst.setCount(list.size());
        lst.setList(list);
        lst.setPageSize(list.size());
        return lst;
    }

    public static CommonList<Project> getAllProjects(final AppContext appContext, int page,int year,int state) throws AppException {
        CommonList<Project> lst = new CommonList<Project>();
        Map<String,Object> params = new HashMap<>();
        params.put(PRIVATE_TOKEN,getToken(appContext));
        params.put("page",page);
        params.put("year",year);
        params.put("state",state);
        String url = makeURL(URLs.PROJECT,params);
        TLog.log(url);
        List<Project> list = getHttpRequester().init(appContext,HTTPRequestor.GET_METHOD,url).getList(Project[].class);
        lst.setCount(list.size());
        lst.setList(list);
        lst.setPageSize(list.size());
        return lst;
    }

    public static CommonList<Project> getUpdateProjects(final AppContext appContext, int page) throws AppException {
        CommonList<Project> lst = new CommonList<Project>();
        Map<String,Object> params = new HashMap<>();
        params.put(PRIVATE_TOKEN,getToken(appContext));
        params.put("page",page);
        params.put("mode","UPDATE");
        String url = makeURL(URLs.PROJECT,params);
        List<Project> list = getHttpRequester().init(appContext,HTTPRequestor.GET_METHOD,url).getList(Project[].class);
        lst.setCount(list.size());
        lst.setList(list);
        lst.setPageSize(list.size());
        return lst;
    }

    public static CommonList<ProjectLog> getProjectLogs(final AppContext appContext, int page,String projectid) throws AppException {
        CommonList<ProjectLog> lst = new CommonList<ProjectLog>();
        Map<String,Object> params = new HashMap<>();
        params.put(PRIVATE_TOKEN,getToken(appContext));
        params.put("page",page);
        if(projectid != "-1") {
            params.put("projectid", projectid);
        }
        String url = makeURL(URLs.PROJECTLOG,params);
        List<ProjectLog> list = getHttpRequester().init(appContext,HTTPRequestor.GET_METHOD,url).getList(ProjectLog[].class);
        lst.setCount(list.size());
        lst.setList(list);
        lst.setPageSize(list.size());
        return lst;
    }

    /**
     * 发日志
     * @param -uid & msg & image
     * @return
     * @throws AppException
     */
    public static Result pubProjectLog(AppContext appContext, ProjectLog log) throws AppException {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("uid", appContext.getLoginUid());
        params.put("msg", log.getContent());
        params.put("projectid", log.getProjectid());
        params.put("createdate", log.getCreatedate());
        params.put("type", log.getType());
        params.put("step", log.getStep());
        params.put("pstate", log.getPstate());

        Map<String, File> files = new HashMap<String, File>();
        if(log.getImagefile() != null)
            files.put("img", log.getImagefile());
        if (log.getAmrfile() != null)
            files.put("amr", log.getAmrfile());

        try{
            //String url = makeURL(URLs.PROJECTLOG + URLs.URL_SPLITTER + "projects",params);
            return getHttpRequester().http_post(appContext, URLs.PROJECTLOG, params, files);
        }catch(Exception e){
            if(e instanceof AppException)
                throw (AppException)e;
            throw AppException.network(e);
        }
    }

    public static CommonList<ProjectIssue> getProjectIssues(final AppContext appContext, int page,String projectid) throws AppException {
        CommonList<ProjectIssue> lst = new CommonList<ProjectIssue>();
        Map<String,Object> params = new HashMap<>();
        params.put(PRIVATE_TOKEN,getToken(appContext));
        params.put("page",page);
        if(projectid != "-1") {
            params.put("projectid", projectid);
        }
        String url = makeURL(URLs.PROJECTISSUE,params);
        List<ProjectIssue> list = getHttpRequester().init(appContext,HTTPRequestor.GET_METHOD,url).getList(ProjectIssue[].class);
        lst.setCount(list.size());
        lst.setList(list);
        lst.setPageSize(list.size());
        return lst;
    }

    public static void pubProjectIssue(AppContext appContext, ProjectIssue issue) throws AppException {
        RequestParams params = new RequestParams();
        params.put("uid", appContext.getLoginUid());
        params.put("title", issue.getTitle());
        params.put("content", issue.getContent());
        params.put("projectid", issue.getProjectid());
        params.put("type", issue.getType());

        try{
            AsyncHttpHelper.post(URLs.PROJECTISSUE, params, null);
        }catch(Exception e){
            if(e instanceof AppException)
                throw (AppException)e;
            throw AppException.network(e);
        }
    }

    public static CommonList<ProjectFile> getProjectFiles(final AppContext appContext, int page,String projectid) throws AppException {
        CommonList<ProjectFile> lst = new CommonList<ProjectFile>();
        Map<String,Object> params = new HashMap<>();
        params.put(PRIVATE_TOKEN,getToken(appContext));
        params.put("page",page);
        if(projectid != "-1") {
            params.put("projectid", projectid);
        }
        String url = makeURL(URLs.PROJECTFILE,params);
        List<ProjectFile> list = getHttpRequester().init(appContext,HTTPRequestor.GET_METHOD,url).getList(ProjectFile[].class);
        lst.setCount(list.size());
        lst.setList(list);
        lst.setPageSize(list.size());
        return lst;
    }

//    public static void getNotices(AsyncHttpResponseHandler handler) {
//        RequestParams params = new RequestParams();
//        params.put("uid", AppContext.getInstance().getLoginUid());
//        AsyncHttpHelper.get("notification", params, handler);
//    }


    public static CommonList<ProjectDoc> getProjectDocs(final AppContext appContext, int page,String projectid) throws AppException {
        CommonList<ProjectDoc> lst = new CommonList<ProjectDoc>();
        Map<String,Object> params = new HashMap<>();
        params.put(PRIVATE_TOKEN,getToken(appContext));
        params.put("page",page);
        params.put("projectid", projectid);
        String url = makeURL(URLs.PROJECTDOC,params);
        List<ProjectDoc> list = getHttpRequester().init(appContext,HTTPRequestor.GET_METHOD,url).getList(ProjectDoc[].class);
        lst.setCount(list.size());
        lst.setList(list);
        lst.setPageSize(list.size());
        return lst;
    }




}
