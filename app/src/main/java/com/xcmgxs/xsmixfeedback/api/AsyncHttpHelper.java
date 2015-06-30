package com.xcmgxs.xsmixfeedback.api;

import java.util.Locale;

import com.xcmgxs.xsmixfeedback.AppContext;
import com.xcmgxs.xsmixfeedback.util.CyptoUtils;
import com.xcmgxs.xsmixfeedback.util.TLog;

import org.apache.http.client.params.ClientPNames;
import org.apache.http.protocol.HTTP;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
/**
 * Created by zhangyi on 2015-05-26.
 */

public class AsyncHttpHelper {


    public final static String PRIVATE_TOKEN = "private_token";
    public final static String XCMGXS_PRIVATE_TOKEN = "feedback@xcmgxs";

    public final static int TIMEOUT_CONNECTION = 20000;// 连接超时时间
    public final static int TIMEOUT_SOCKET = 20000;// socket超时

    public static AsyncHttpClient getHttpClient() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader(HTTP.USER_AGENT, getUserAgent());
        client.setTimeout(TIMEOUT_CONNECTION);
        client.setResponseTimeout(TIMEOUT_SOCKET);
        String private_token = AppContext.getInstance().getProperty(PRIVATE_TOKEN);
        private_token = CyptoUtils.decode(XCMGXS_PRIVATE_TOKEN, private_token);
        client.addHeader("private-token", private_token);
        return client;
    }

    public static void get(String url, AsyncHttpResponseHandler handler) {
        getHttpClient().get(url, handler);
        log(new StringBuilder("GET ").append(url).toString());
    }

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler handler) {
        getHttpClient().get(url, params, handler);
        log(new StringBuilder("GET ").append(url).append("?").append(params).toString());
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler handler) {
        getHttpClient().post(url, params, handler);
        log(new StringBuilder("POST ").append(url).append("?").append(params).toString());
    }

    public static void post(String url, AsyncHttpResponseHandler handler) {
        getHttpClient().post(url, handler);
        log(new StringBuilder("POST ").append(url).append("?").toString());
    }


    public static void put(String url, AsyncHttpResponseHandler handler) {
        getHttpClient().put(url, handler);
        log(new StringBuilder("PUT ").append(url).toString());
    }

    public static void put(String url, RequestParams params,AsyncHttpResponseHandler handler) {
        getHttpClient().put(url, params, handler);
        log(new StringBuilder("PUT ").append(url).append("&").append(params).toString());
    }


    public static void delete(String url, AsyncHttpResponseHandler handler) {
        getHttpClient().delete(url, handler);
        log(new StringBuilder("DELETE ").append(url).toString());
    }


    public static void cancelAll(Context context) {
        getHttpClient().cancelRequests(context, true);
    }

    /**
     * 获得UserAgent
     *
     * @return
     */
    private static String getUserAgent() {
        AppContext appContext = AppContext.getInstance();
        StringBuilder ua = new StringBuilder("Git@OSC.NET");
        ua.append('/' + appContext.getPackageInfo().versionName + '_' + appContext.getPackageInfo().versionCode);//App版本
        ua.append("/Android");//手机系统平台
        ua.append("/" + android.os.Build.VERSION.RELEASE);//手机系统版本
        ua.append("/" + android.os.Build.MODEL); //手机型号
        ua.append("/" + AppContext.getInstance().getAppId());//客户端唯一标识
        return ua.toString();
    }

    public static void log(String log) {
        Log.d("BaseApi", log);
        TLog.log("Test", log);
    }


    private static String appCookie;

    public static void setCookie(String cookie) {
        getHttpClient().addHeader("Cookie", cookie);
    }

    public static void cleanCookie() {
        appCookie = "";
    }

    public static String getCookie(AppContext appContext) {
        if (appCookie == null || appCookie == "") {
            appCookie = appContext.getProperty("cookie");
        }
        return appCookie;
    }

    /**
     * 获得请求的服务端数据的userAgent
     * @param appContext
     * @return
     */
    public static String getUserAgent(AppContext appContext) {
        StringBuilder ua = new StringBuilder("XCMGXS");
        ua.append('/' + appContext.getPackageInfo().versionName + '_'
                +  appContext.getPackageInfo().versionCode);// app版本信息
        ua.append("/Android");// 手机系统平台
        ua.append("/" + android.os.Build.VERSION.RELEASE);// 手机系统版本
        ua.append("/" + android.os.Build.MODEL); // 手机型号
        ua.append("/" + appContext.getAppId());// 客户端唯一标识
        return ua.toString();
    }


    public static RequestParams getPrivateTokenWithParams() {
        RequestParams params = new RequestParams();
        String private_token = AppContext.getInstance().getProperty(PRIVATE_TOKEN);
        private_token = CyptoUtils.decode(XCMGXS_PRIVATE_TOKEN, private_token);
        params.put(PRIVATE_TOKEN, private_token);
        return params;
    }
}
