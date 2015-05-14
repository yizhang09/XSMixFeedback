package com.xcmgxs.xsmixfeedback.api;

import android.graphics.Bitmap;

import com.xcmgxs.xsmixfeedback.AppContext;
import com.xcmgxs.xsmixfeedback.AppException;
import com.xcmgxs.xsmixfeedback.bean.CommonList;
import com.xcmgxs.xsmixfeedback.bean.Project;
import com.xcmgxs.xsmixfeedback.bean.ProjectLog;
import com.xcmgxs.xsmixfeedback.bean.Result;
import com.xcmgxs.xsmixfeedback.bean.Session;
import com.xcmgxs.xsmixfeedback.bean.URLs;
import com.xcmgxs.xsmixfeedback.bean.User;
import com.xcmgxs.xsmixfeedback.util.CyptoUtils;
import com.xcmgxs.xsmixfeedback.util.StringUtils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
    private final static String FEEDBACK_PRIVATE_TOKEN = "feedback@xcmgxs_token";

    // 私有token，每个用户都有一个唯一的
    private static String private_token;
    public static final ObjectMapper MAPPER = new ObjectMapper().configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static void cleanToken() {
        private_token = "";
    }

    public static String getToken(AppContext appContext){
        if(private_token == null||private_token == ""){
            private_token = appContext.getProperty(PRIVATE_TOKEN);
        }
        return CyptoUtils.decode(FEEDBACK_PRIVATE_TOKEN, private_token);
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

    public static User login(AppContext appContext, String username, String pwd) throws AppException {
        String urlString = URLs.LOGIN_HTTPS;
        Session session = getHttpRequester().init(appContext,HTTPRequestor.POST_METHOD,urlString)
                .with("username", username).with("password",pwd).to(Session.class);

        if(session != null && session.get_privateToken() != null){
            String token = CyptoUtils.encode(FEEDBACK_PRIVATE_TOKEN,session.get_privateToken());
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
     * @param userId
     * @param page
     * @return
     * @throws AppException
     */
    public static CommonList<Project> getUserProjects(final AppContext appContext,String userId,int page) throws AppException {
        CommonList<Project> lst = new CommonList<Project>();
        Map<String,Object> params = new HashMap<>();
        params.put(PRIVATE_TOKEN,getToken(appContext));
        params.put("page",page);
        String url = makeURL(URLs.PROJECT + URLs.URL_SPLITTER + userId +URLs.URL_SPLITTER + "projects",params);
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
        params.put("projectid",projectid);
        String url = makeURL(URLs.PROJECTLOG,params);
        List<ProjectLog> list = getHttpRequester().init(appContext,HTTPRequestor.GET_METHOD,url).getList(ProjectLog[].class);
        lst.setCount(list.size());
        lst.setList(list);
        lst.setPageSize(list.size());
        return lst;
    }

    /**
     * 发动弹
     * @param -uid & msg & image
     * @return
     * @throws AppException
     */
    public static Result pubTweet(AppContext appContext, ProjectLog log) throws AppException {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("uid", log.getAuthor());
        params.put("msg", log.getContent());

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

}
