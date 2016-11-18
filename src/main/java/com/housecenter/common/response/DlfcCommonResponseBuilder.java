package com.housecenter.common.response;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.housecenter.entity.base.DlfcCommonEntity;

/**
 * 服务端 共通响应类 响应的数据结果中 存在null数据
 */
public class DlfcCommonResponseBuilder {

    private static Map<String, Gson> gsonMap = new HashMap<>();

    private static Gson gsonBuilder;

    private DlfcCommonEntity resultBody;

    /**
     * 响应业务状态
     */
    private Integer status = 0;

    /**
     * 响应消息
     */
    private String msg = "";

    private DlfcCommonResponseBuilder() {

    }

    /**
     * 初始化 结果数据中包含null数据 GsonBuilder
     *
     * @return
     */
    public static DlfcCommonResponseBuilder getInstance() {

        if (null == gsonMap.get("gsonBuilder")) {
            // 转换的json可带null值
            gsonMap.put("gsonBuilder", new GsonBuilder().serializeNulls().create());
        }
        gsonBuilder = gsonMap.get("gsonBuilder");
        return new DlfcCommonResponseBuilder();
    }

    /**
     * 结果数据中包含null数据
     * 
     * @param entity
     * @return
     */
    public String build(DlfcCommonEntity entity) {

        resultBody = entity;
        return gsonBuilder.toJson(this);
    }

}
