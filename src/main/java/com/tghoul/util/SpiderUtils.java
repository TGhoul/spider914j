package com.tghoul.util;

import us.codecraft.webmagic.proxy.Proxy;

import java.util.ArrayList;
import java.util.List;
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
     * generate a random http proxy
     * @return
     */
    public static Proxy randomProxy() {
        String[] items = {
                "60.207.239.247:3128",
                "61.160.208.222:8080",
                "106.14.51.145:8118",
                "218.201.98.196:3128",
                "101.37.79.125:3128",
                "122.192.32.74:7280",
                "60.207.76.119:3128",
                "112.13.93.43:8088",
                "60.207.239.245:3128",
                "124.133.230.254:80",
                "139.217.24.50:3128",
                "122.192.32.79:7280",
                "122.192.32.77:7280",
                "27.159.126.133:8118"
        };

        List<Proxy> proxies = new ArrayList<>(14);

        for (String item : items) {
            String[] proxyStr = item.split(":");
            proxies.add(new Proxy(proxyStr[0], Integer.parseInt(proxyStr[1])));
        }
        Random random = new Random();
        return proxies.get(random.nextInt(proxies.size() - 1));
    }
}
