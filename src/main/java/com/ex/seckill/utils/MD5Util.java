package com.ex.seckill.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 双MD5加盐加密
 * 第一层防明文，第二层防数据库泄露
 */
public class MD5Util {
    public static String md5(String str){
        return DigestUtils.md5Hex(str);
    }

    private static final String salt="1a2b3c4d";

    //第一层加密
    public static String inputPassToFormPass(String inputPass){
        String str=""+salt.charAt(0)+salt.charAt(2)+inputPass+salt.charAt(5)+salt.charAt(4);
        return md5(str);
    }
    //第二层加密
    public static String fromPassToDbPass(String formPass,String salt){
        String str=""+salt.charAt(0)+salt.charAt(2)+formPass+salt.charAt(5)+salt.charAt(4);
        return md5(str);
    }

    public static String inputPassToDbPass(String inputPass, String saltDB) {
        String formPass = inputPassToFormPass(inputPass);
        String dbPass = fromPassToDbPass(formPass, saltDB);
        return dbPass;
    }

    public static void main(String[] args) {
        String firstPass=inputPassToFormPass("123456");
        String secondPass=fromPassToDbPass(firstPass,"123456");
        System.out.println(firstPass);
        System.out.println(secondPass);
    }


}
