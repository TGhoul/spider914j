package com.tghoul.domain;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

import java.util.List;
import java.util.Random;

/**
 * @author zpj
 * @date 2017/11/6 17:40
 */
public class GithubRepoPageProcessor implements PageProcessor {

    private Site site = Site
            .me().setRetryTimes(3)
            .setSleepTime(1000)
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36");

    @Override
    public void process(Page page) {
        page.putField("text", page.getHtml().xpath("//a[@class='mnav'][1]/text()"));
    }

    @Override
    public Site getSite() {
        return site;
    }

    /**
     * generate a random IPAddress
     * @return
     */
    public static String randomIP() {
        return (int) Math.floor(Math.random() * 255) + "." +
               (int) Math.floor(Math.random() * 255) + "." +
               (int) Math.floor(Math.random() * 255) + "." +
               (int) Math.floor(Math.random() * 255);
    }

    public static void main(String[] args) {
//        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
//        httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(new Proxy("127.0.0.1", 1080)));

            Spider.create(new GithubRepoPageProcessor())
                    .addUrl("https://www.baidu.com/")
                    .thread(1)
                    .run();
    }
}
