package com.hvisions.mes.security;

import java.nio.charset.Charset;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

public class EncryptUtil {
    private static Charset CHARSET = Charset.forName("UTF-8");
    private static final String KEY_ALGORITHM = "AES";
    private static final String CIPHER_ALGORITHM_ECB = "AES/ECB/PKCS5Padding";

    private static byte[] KEY = new byte[] { -119, -11, 82, 79, 1, 4, 18, 21, 44, 60, 49, -121, 72,
        82, -117, 65, 93, -58, -96, 71, -118, -82, 87, -54, 47, 93, 57, 115, -5, -58, -19, -120 };
    private static Key GENERATE_KEY = generateKey(KEY);

    public static final String encrypt(String text) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB);
            cipher.init(Cipher.ENCRYPT_MODE, GENERATE_KEY);

            Encoder encoder = Base64.getEncoder();

            byte[] base64Bytes = encoder.encode(text.getBytes(CHARSET));
            byte[] encryptedBytes = cipher.doFinal(base64Bytes);
            String hexString = asHex(encryptedBytes);

            return Base64.getEncoder().encodeToString(hexString.getBytes(CHARSET));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static final String decrypt(String text) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB);
            cipher.init(Cipher.DECRYPT_MODE, GENERATE_KEY);

            Decoder decoder = Base64.getDecoder();
            String decodedText = new String(decoder.decode(text), CHARSET);
            byte[] decryptBytes = cipher.doFinal(asBin(decodedText));
            byte[] debase64edBytes = decoder.decode(decryptBytes);

            return new String(debase64edBytes, CHARSET);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 获得秘密密钥
     */
    private static Key generateKey(byte[] key) {
        try {
            //防止linux下 随机生成key
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(key);

            KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
            kg.init(128, secureRandom);
            //kg.init(56, secureRandom);

            // 生成密钥
            return kg.generateKey();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将字节数组转换成16进制字符串
     */
    private static String asHex(byte[] buf) {
        StringBuffer strbuf = new StringBuffer(buf.length * 2);
        int i;
        for (i = 0; i < buf.length; i++) {
            if (((int) buf[i] & 0xff) < 0x10) { //小于十前面补零
                strbuf.append("0");
            }
            strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
        }
        return strbuf.toString();
    }

    /**
     * 将16进制字符串转换成字节数组
     */
    private static byte[] asBin(String src) {
        if (src.length() < 1) {
            return null;
        }
        byte[] encrypted = new byte[src.length() / 2];
        for (int i = 0; i < src.length() / 2; i++) {
            int high = Integer.parseInt(src.substring(i * 2, i * 2 + 1), 16);//取高位字节
            int low = Integer.parseInt(src.substring(i * 2 + 1, i * 2 + 2), 16);//取低位字节
            encrypted[i] = (byte) (high * 16 + low);
        }
        return encrypted;
    }

}
