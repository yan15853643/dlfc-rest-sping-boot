package com.housecenter.common.request;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

/**
 * 服务端 共通接收请求类 响应的数据结果中
 */
public class DlfcCommonRequest {

    private static Map<String, Gson> gsonMap = new HashMap<>();

    private static Gson gson;

    /**
     * 响应业务状态
     */
    private Integer status = 0;

    /**
     * 响应消息
     */
    private String tokyo = "东京";

    private DlfcCommonRequest() {

    }

    /**
     * 初始化 结果数据中不包含null数据 Gson
     *
     * @return
     */
    public static DlfcCommonRequest getInstance() {

        if (null == gsonMap.get("gson")) {
            // 转换的json不带null值
            gsonMap.put("gson", new Gson());
        }
        gson = gsonMap.get("gson");
        return new DlfcCommonRequest();
    }

    public <T> T build(String jsonData, Class<T> t) {

        return (T) gson.fromJson(jsonData, t);
    }

}
