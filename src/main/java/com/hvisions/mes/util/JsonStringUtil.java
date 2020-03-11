package com.hvisions.mes.util;

import com.alibaba.fastjson.JSONObject;

/**
 * @author dpeng
 * @description Json字符串解析
 * @date 2019-08-05 13:32
 */
public class JsonStringUtil {

    /**
     *  解析json字符串通过key得到value
     * @param json     json字符串
     * @param key      key
     * @param clazz    类对象
     * @param <T>      泛型value的类型
     * @return         value
     */
    public static <T> T analyseJsonString(String json,String key,Class<T> clazz) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        return jsonObject.getObject(key, clazz);
    }

}
