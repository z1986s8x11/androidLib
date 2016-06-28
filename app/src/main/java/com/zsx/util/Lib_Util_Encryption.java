package com.zsx.util;

import com.zsx.debug.LogUtil;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * 加密解密工具包
 *
 * @author zsx
 * @date 2015-2-4
 */
public class Lib_Util_Encryption {

    /**
     * DES算法，加密
     *
     * @param data 待加密字符串
     * @param key  加密私钥，长度不能够小于8位
     * @return 加密后的字节数组，一般结合Base64编码使用
     * @throws InvalidAlgorithmParameterException
     * @throws Exception
     */
    public static String encodeDES(String key, String data) {
        if (data == null) {
            return null;
        }
        if (key == null || key.length() < 8) {
            if (LogUtil.DEBUG) {
                LogUtil.e(Lib_Util_Encryption.class, "key.length() < 8 !!!");
            }
            return data;
        }
        try {
            DESKeySpec dks = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            /**第一种*/
//            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
//            AlgorithmParameterSpec paramSpec = new IvParameterSpec("12345678".getBytes());
            /**第二种*/
            Cipher cipher = Cipher.getInstance("DES");
            // DES算法要求有一个可信任的随机数源
            SecureRandom paramSpec = new SecureRandom();
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
            byte[] bytes = cipher.doFinal(data.getBytes());
            return byte2hex(bytes);
        } catch (InvalidKeyException e) {
            if (LogUtil.DEBUG) {
                // 长度不对
                LogUtil.e(e);
            }
        } catch (Exception e) {
            if (LogUtil.DEBUG) {
                LogUtil.w(e);
            }
        }
        return data;
    }

    /**
     * DES算法，解密
     *
     * @param data 待解密字符串
     * @param key  解密私钥，长度不能够小于8位
     * @return 解密后的字节数组
     * @throws Exception 异常
     */
    public static String decodeDES(String key, String data) {
        if (data == null) {
            return null;
        }
        if (key == null || key.length() < 8) {
            if (LogUtil.DEBUG) {
                LogUtil.e(Lib_Util_Encryption.class, "key.length() < 8 !!!");
            }
            return data;
        }
        try {
            DESKeySpec dks = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            /**第一种*/
//            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
//            AlgorithmParameterSpec paramSpec = new IvParameterSpec("12345678".getBytes());
            /**第二种*/
            Cipher cipher = Cipher.getInstance("DES");
            // DES算法要求有一个可信任的随机数源
            SecureRandom paramSpec = new SecureRandom();
            cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
            return new String(cipher.doFinal(hex2byte(data.getBytes())));
        } catch (BadPaddingException e) {
            if (LogUtil.DEBUG) {
                LogUtil.e(e);
            }
        } catch (Exception e) {
            if (LogUtil.DEBUG) {
                LogUtil.w(e);
            }
        }
        return data;
    }

    /**
     * MD5算法 信息-摘要算法
     *
     * @param content
     * @return
     */
    public static String encodeMD5(String content) {
        if (content == null) {
            return content;
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(content.getBytes());
            return getHashString(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * SHA算法 安全散列算法
     *
     * @param content
     * @return
     */
    public static String encodeSHA(String content) {
        if (content == null) {
            return content;
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA");
            digest.update(content.getBytes());
            return getHashString(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param key     密钥
     * @param content 原文
     * @return 加密后的数据
     */
    public static String encodeAES(String key, String content) {
        try {
            /**第一种*/
            KeyGenerator kGen = KeyGenerator.getInstance("AES");
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed(key.getBytes());
            // 可以为 128 or 192 or 256 bits
            kGen.init(128, sr);
            SecretKey sKey = kGen.generateKey();
            byte[] rawKey = sKey.getEncoded();
            SecretKeySpec sKeySpec = new SecretKeySpec(rawKey, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            /**第二种*/
//            SecretKeySpec sKeySpec = new SecretKeySpec(key.getBytes(), "AES");
//            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, sKeySpec);
            byte[] result = cipher.doFinal(content.getBytes());
            return byte2hex(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param key  密钥
     * @param data 密文
     * @return 加密后的数据
     */
    public static String decodeAES(String key, String data) {
        try {
            /**第一种*/
            KeyGenerator kGen = KeyGenerator.getInstance("AES");
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed(key.getBytes());
            // 可以为 128 or 192 or 256 bits
            kGen.init(128, sr);
            SecretKey sKey = kGen.generateKey();
            byte[] rawKey = sKey.getEncoded();
            SecretKeySpec sKeySpec = new SecretKeySpec(rawKey, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            /**第二种*/
//            SecretKeySpec sKeySpec = new SecretKeySpec(key.getBytes(), "AES");
//            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, sKeySpec);
            byte[] result = cipher.doFinal(hex2byte(data.getBytes()));
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getHashString(MessageDigest digest) {
        StringBuilder builder = new StringBuilder();
        for (byte b : digest.digest()) {
            builder.append(Integer.toHexString((b >> 4) & 0xf));
            builder.append(Integer.toHexString(b & 0xf));
        }
        return builder.toString();
    }

    /**
     * 二行制转字符串
     *
     * @param b
     * @return
     */
    private static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toUpperCase();
    }

    private static byte[] hex2byte(byte[] b) {
        if ((b.length % 2) != 0)
            throw new IllegalArgumentException();
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }

}
