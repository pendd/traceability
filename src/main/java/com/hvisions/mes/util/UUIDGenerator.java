package com.hvisions.mes.util;

import java.util.UUID;

/**  生成uuid工具类
 *
 * @author dpeng
 * @create 2019-03-20 16:52
 */
public class UUIDGenerator {

        /**
      * 获取32位UUID字符串
      * @return
      */
        public static String getUUID() {
            return UUID.randomUUID().toString().replaceAll("-", "");
        }

        /**
      * 获取32位UUID大写字符串
      * @return
      */
        public static String getUpperCaseUUID() {
            return UUID.randomUUID().toString().toUpperCase();
        }

        public static void main(String[] args) {
            System.out.println(UUIDGenerator.getUpperCaseUUID());
        }

}
