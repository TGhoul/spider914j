package com.tghoul.domain;

import com.tghoul.proxy.AbstractDownloaderProxy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tghoul
 * @date 2017/11/6 17:40
 *
 * 爬虫进程类
 */
@Slf4j
public class S91RepoPageProcessor implements PageProcessor {

    private Site site = Site
            .me()
            .setDomain("91.91p18.space")
            //重试次数
            .setRetryTimes(10)
            .setCycleRetryTimes(10)
            //抓取间隔
            .setSleepTime(1000)
            .setTimeOut(5000)
            .setCharset("utf-8");

    @Override
    public void process(Page page) {
        //url地址
        List<String> videoUrls = new ArrayList<>(20);
        for (int pageNo = 1; pageNo < 2; pageNo++) {
            page.addTargetRequest("http://91.91p18.space/v.php?next=watch&page=" + pageNo);
            videoUrls.addAll(page.getHtml()
                    .xpath("//div[@id='videobox']/table/tbody/tr/td/div[@class='listchannel']/a")
                    .links()
                    .regex("http://91\\.91p18\\.space/view_video\\.php.*")
                    .all());
        }

        if (CollectionUtils.isNotEmpty(videoUrls)) {
            page.addTargetRequests(videoUrls);
        }

        log.info("Request Url --------- {}", page.getRequest().getUrl());

        if (page.getUrl().toString().contains("view_video")) {
            page.putField("videoUrl", page.getHtml().xpath("//video[@id='vid']/source/@src").get());
            page.putField("title", page.getHtml().xpath("//title/text()"));
            log.info("title ---------- {}", page.getHtml().xpath("//title/text()"));
            log.info("Video Url ---------- {}", page.getHtml().xpath("//video[@id='vid']/source/@src").get());
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {

        HttpClientDownloader downloader = new HttpClientDownloader();

        Spider.create(new S91RepoPageProcessor())
              .addUrl("http://91.91p18.space/v.php?next=watch")
              .setDownloader(new AbstractDownloaderProxy(downloader))
              .thread(10)
              .run();

    }
}
