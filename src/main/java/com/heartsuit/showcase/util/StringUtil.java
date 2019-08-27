package com.heartsuit.showcase.util;

/**
 * Created by Administrator on 2019/8/24 0024 23:33
 */
public class StringUtil {

    public static boolean isNullOrEmpty(String str) {
        return null == str || str.trim().length() == 0;
    }

    public static String convertNullToEmpty(String str) {
        return isNullOrEmpty(str) ? "" : str;
    }
}

