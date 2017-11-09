package com.tghoul.util;

/**
 * @author zpj
 * @date 2017/11/9
 * 爬虫工具类
 */
public class SpiderUtil {

    /**
     * generate a random IPAddress
     * @return String
     */
    public static String randomIP() {
        return (int) Math.floor(Math.random() * 255) + "." +
               (int) Math.floor(Math.random() * 255) + "." +
               (int) Math.floor(Math.random() * 255) + "." +
               (int) Math.floor(Math.random() * 255);
    }
}
