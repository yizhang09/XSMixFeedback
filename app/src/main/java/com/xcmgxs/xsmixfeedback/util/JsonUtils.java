package com.xcmgxs.xsmixfeedback.util;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zhangyi on 2015-05-26.
 */
public class JsonUtils {
    //Json处理器
    public static final ObjectMapper MAPPER = new ObjectMapper().configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static <T> T toBean(Class<T> type,byte[] bytes) throws IOException {
        return MAPPER.readValue(bytes,type);
    }

    public static <T> T toBean(Class<T> type,String jsonString) throws IOException {
        return MAPPER.readValue(jsonString,type);
    }

    public static <T> List<T> toList(Class<T[]> type,String jsonString) throws IOException{
        List<T> results = new ArrayList<T>();
        T[] _next = MAPPER.readValue(jsonString,type);
        results.addAll(Arrays.asList(_next));
        return results;
    }

    public static <T> List<T> toList(Class<T[]> type,byte[] bytes) throws IOException{
        List<T> results = new ArrayList<T>();
        T[] _next = MAPPER.readValue(bytes,type);
        results.addAll(Arrays.asList(_next));
        return results;
    }

}
