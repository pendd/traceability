package com.hvisions.mes.util;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

/*
 * MD5加密算法工具类
 *
 * @author wenkb
 */
public class MD5Util {

    /*
     * 加密字符串
     * 
     * @param orgString 需要加密的字符串
     * 
     * @return 加密后的字符串
     */
    public static final String encrypt(String orgString) {
        //需要加密的字符串不能为null
        if (orgString == null) {
            return null;
        }

        //加密后的字符串
        String encryptedStr = null;

        try {
            //初始化加密组件
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");

            //加密
            String addSaltStr = addSalt(orgString);
            byte[] encryptedBytes = messageDigest.digest(addSaltStr.getBytes());

            //加密后，转换为16进制的字符串
            encryptedStr = Hex.encodeHexString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //返回
        return encryptedStr;
    }

    private static final String addSalt(String str) {
        return str.concat("H-VISONS").concat(StringUtils.left(str, 3));
    }

    public static final String encryptMD5(String orgString) {
        //需要加密的字符串不能为null
        if (orgString == null) {
            return null;
        }

        //加密后的字符串
        String encryptedStr = null;

        try {
            //初始化加密组件
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");

            //加密
            byte[] encryptedBytes = messageDigest.digest(orgString.getBytes());

            //加密后，转换为16进制的字符串
            encryptedStr = Hex.encodeHexString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //返回
        return encryptedStr;
    }
}
