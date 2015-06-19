package com.xcmgxs.xsmixfeedback.api;


import java.io.Serializable;

/**
 * 接口URL实体类
 * @author zhangyi
 * @version 1.0
 * @created 2015-03-19
 */
@SuppressWarnings("serial")
public class URLs implements Serializable {

    public final static String HOST  = "122.194.137.62:8002";
    private static final String API_VERSION = "/api";// API版本
    public final static String HTTPS = "https://";
    public final static String HTTP = "http://";

    public final static String URL_SPLITTER = "/";

    // 拼接的api根地址
    public final static String URL_HOST = HTTP + HOST + URL_SPLITTER;
    public final static String URL_API_HOST = HTTP + HOST + API_VERSION + URL_SPLITTER;

    //日志图片地址
    public final static String URL_UPLOAD_LOGPIC = HTTP + HOST + URL_SPLITTER +"upload/logpic/";

    //日志图片地址
    public final static String URL_UPLOAD_ISSUEPIC = HTTP + HOST + URL_SPLITTER +"upload/issuepic/";

    //文件地址
    public final static String URL_UPLOAD_FILE = HTTP + HOST + URL_SPLITTER +"upload/file/";

    //头像地址
    public final static String URL_PORTRAIT = HTTP + HOST + URL_SPLITTER + "upload/portrait/";

    // 项目
    public static String PROJECT = URL_API_HOST+ "project";

    // 项目日志
    public static String PROJECTLOG = URL_API_HOST+ "projectlog";

    // 项目问题
    public static String PROJECTISSUE = URL_API_HOST+ "projectissue";

    // 项目文件
    public static String PROJECTFILE = URL_API_HOST+ "projectfiles";

    public final static String LOGIN_HTTPS = HTTPS + HOST + API_VERSION + URL_SPLITTER + "user";
    public final static String LOGIN_HTTP = HTTP + HOST + API_VERSION + URL_SPLITTER + "user";
    public static String USER = URL_API_HOST + "user";
    public static String UPLOAD = URL_API_HOST + "upload";

}
