package com.tghoul.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author tghoul
 * @date 2017/11/9
 * 爬虫工具类
 */
public class SpiderUtils {

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

    /**
     * generate a random user-agent
     * @return
     */
    public static String randomAgent() {
        String[] userAgent = {
                "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/58.0.3029.96 Chrome/58.0.3029.96 Safari/537.36",
                "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:17.0; Baiduspider-ads) Gecko/17.0 Firefox/17.0",
                "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9b4) Gecko/2008030317 Firefox/3.0b4",
                "Mozilla/5.0 (Windows; U; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 2.0.50727; BIDUBrowser 7.6)",
                "Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko",
                "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0",
                "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.99 Safari/537.36",
                "Mozilla/5.0 (Windows NT 6.3; Win64; x64; Trident/7.0; Touch; LCJB; rv:11.0) like Gecko"
        };

        Random random = new Random();
        return userAgent[random.nextInt(userAgent.length - 1)];
    }

    /**
     * 格式化标题
     * @param property 属性名称
     * @param src 抓取的属性值
     * @return
     */
    public static String formatProperty(String property, String src) {
        if (src != null) {
            switch (property) {
                case "title":
                    if (src.contains("-")) {
                        return src.split("-")[0].trim();
                    } else {
                        return src.trim();
                    }
                case "runtime":
                    String[] var1 = src.split("[\\s\\p{Zs}]+");
                    if (var1.length > 0) {
                        return var1[0];
                    }
                    break;
                case "views":
                    String[] var2 = src.split("[\\s\\p{Zs}]+");
                    if (var2.length > 1) {
                        return var2[1];
                    }
                    break;
                case "star":
                    String[] var3 = src.split("[\\s\\p{Zs}]+");
                    if (var3.length > 3) {
                        return var3[3];
                    }
                    break;
                default:
                    return null;
            }
        }

        return null;
    }

    /**
     * 字符串转日期
     * @param time 日期字符串
     * @return
     * @throws ParseException
     */
    public static Date parseToDate(String time) {
        SimpleDateFormat sdf = null;

        if (time.length() == 5) {
            sdf = new SimpleDateFormat("mm:ss");
        }

        if (time.length() == 8) {
            sdf = new SimpleDateFormat("HH:mm:ss");
        }

        if (sdf == null) {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
        }

        try {
            return sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
