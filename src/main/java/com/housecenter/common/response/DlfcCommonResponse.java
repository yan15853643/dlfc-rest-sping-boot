package com.housecenter.common.response;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.housecenter.entity.base.DlfcCommonEntity;

/**
 * 服务端 共通响应类 响应的数据结果中 不存在null数据
 */
public class DlfcCommonResponse {

    private static Map<String, Gson> gsonMap = new HashMap<>();

    private static Gson gson;

    private DlfcCommonEntity resultBody;

    /**
     * 响应业务状态
     */
    private Integer status = 0;

    /**
     * 响应消息
     */
    private String msg = "";

    private DlfcCommonResponse() {

    }

    /**
     * 初始化 结果数据中不包含null数据 Gson
     *
     * @return
     */
    public static DlfcCommonResponse getInstance() {

        if (null == gsonMap.get("gson")) {
            // 转换的json不带null值
            gsonMap.put("gson", new Gson());
        }
        gson = gsonMap.get("gson");
        return new DlfcCommonResponse();
    }

    /**
     * 结果数据中不包含null数据
     *
     * @param entity
     * @return
     */
    public String build(DlfcCommonEntity entity) {

        resultBody = entity;
        return gson.toJson(this);
    }

}
