package com.hvisions.mes.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author dpeng
 * @description 生成不重复的单据编号
 * @date 2019-08-27 11:04
 */
public class RandomNumber {

    public static String getRandomNumber() {

        String time = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

        // 随机六位数
        int random = (int) ((Math.random() * 9 + 1) * 1000000);

        // 拼接
        return time + random;
    }

    /**
     *  主子表ID
     * @return
     */
    public static int getRandomNumberToInt() {

        String time = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

        // 随机六位数
        int random = (int) ((Math.random() * 9 + 1) * 1000000);

        // 拼接
        return Integer.valueOf(time + random);
    }

    public static void main(String[] args) {
        System.out.println(getRandomNumber());
    }
}
