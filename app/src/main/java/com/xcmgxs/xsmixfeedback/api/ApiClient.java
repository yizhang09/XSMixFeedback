package com.xcmgxs.xsmixfeedback.api;

import com.xcmgxs.xsmixfeedback.AppContext;
import com.xcmgxs.xsmixfeedback.bean.User;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author zhangyi
 * Created by zhangyi on 2015-3-18.
 */
public class ApiClient {

    private final static String PRIVATE_TOKEN = "private_token";
    private final static String GITOSC_PRIVATE_TOKEN = "git@osc_token";

    // 私有token，每个用户都有一个唯一的
    private static String private_token;
    public static final ObjectMapper MAPPER = new ObjectMapper().configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static User login(AppContext appContext, String account, String pwd) {
        return null;
    }

    public static void cleanToken() {

    }
}
