package com.tghoul.domain;

import com.tghoul.proxy.AbstractDownloaderProxy;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author zpj
 * @date 2017/11/6 17:40
 */
public class GithubRepoPageProcessor implements PageProcessor {

    private Site site = Site
            .me()
            .setRetryTimes(3)
            .setSleepTime(1000)
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36");

    @Override
    public void process(Page page) {
        page.putField("videoUrl", page.getHtml().xpath("//div[@id='videobox']/table/tbody/tr/td/div[@class='listchannel']/a").links().all());
        System.out.println(page.getHtml().xpath("//div[@id='videobox']/table/tbody/tr/td/div[@class='listchannel']/a").links().all().size());
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
//        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
//        httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(new Proxy("127.0.0.1", 1080)));

        AbstractDownloaderProxy downloaderProxy = new AbstractDownloaderProxy(new HttpClientDownloader());
        Spider.create(new GithubRepoPageProcessor())
              .addUrl("http://91.91p18.space/v.php?next=watch")
              .setDownloader(downloaderProxy)
              .thread(1)
              .run();
    }
}
