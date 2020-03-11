package com.hvisions.mes.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.hvisions.mes.dto.ReadJson;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @author dpeng
 * @description 解析json
 * @date 2019-09-15 18:07
 */
public class ReadJsonUtil {

    public static <T> T readJsonFromClassPath(String path, Type type) throws IOException {

        ClassPathResource resource = new ClassPathResource(path);
        if (resource.exists()) {
            return JSON.parseObject(resource.getInputStream(), StandardCharsets.UTF_8, type,
                    // 自动关闭流
                    Feature.AutoCloseSource,
                    // 允许注释
                    Feature.AllowComment,
                    // 允许单引号
                    Feature.AllowSingleQuotes,
                    // 使用 Big decimal
                    Feature.UseBigDecimal);
        } else {
            throw new IOException();
        }
    }

    public static <T> T readJsonFromClassPath(Resource resource, Type type) throws IOException {

//        ClassPathResource resource1 = new ClassPathResource(resource);
        if (resource.exists()) {
            return JSON.parseObject(resource.getInputStream(), StandardCharsets.UTF_8, type,
                    // 自动关闭流
                    Feature.AutoCloseSource,
                    // 允许注释
                    Feature.AllowComment,
                    // 允许单引号
                    Feature.AllowSingleQuotes,
                    // 使用 Big decimal
                    Feature.UseBigDecimal);
        } else {
            throw new IOException();
        }
    }

    public static ReadJson parseJson(Resource resource) {
        ReadJson readJson = new ReadJson();
        try {
            readJson = ReadJsonUtil.readJsonFromClassPath(resource, ReadJson.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return readJson;
    }

    /**
     *  判断当前时间是否大于被给时间
     * @param resource
     * @return
     */
    public static int daysBetween(Resource resource) {
        Date endTime = parseJson(resource).getEndTime();

        DateTime giveTime = new DateTime(endTime);
        DateTime currentTime = new DateTime();

        Period period = new Period(currentTime,giveTime, PeriodType.days());

        return period.getDays();
    }

    /**
     *  拼接字符串
     * @param str
     * @return
     */
    public static String concatString(String str) {
        return str + "%" + System.currentTimeMillis();
    }
}
