package com.tghoul.domain;

import com.tghoul.model.Video;
import com.tghoul.pipeline.PornVideoPipeline;
import com.tghoul.proxy.AbstractDownloaderProxy;
import com.tghoul.util.SpiderUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.processor.PageProcessor;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tghoul
 * @date 2017/11/6 17:40
 *
 * 爬虫进程类
 */
@Slf4j
@Component("s91RepoPageProcessor")
public class S91RepoPageProcessor implements PageProcessor {

    @Resource
    private PornVideoPipeline pornVideoPipeline;

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
            //抓取数据
            String userId = page.getHtml().xpath("//div[@id='videodetails-content']/a/@href").get().trim();
            String title = page.getHtml().xpath("//title/text()").get();
            String author = page.getHtml().xpath("//a[@href='" + userId + "']/span/text()").get().trim();
            String videoUrl = page.getHtml().xpath("//video[@id='vid']/source/@src").get().trim();
            String videoInfo = page.getHtml().xpath("//div[@class='boxPart']/text()").get().trim();
            String uploadTime =
                    page.getHtml().xpath("//div[@id='videodetails-content']/span[@class='title']/text()").get().trim();

            //封装
            Video video = new Video();
            video.setTitle(SpiderUtils.formatProperty("title", title));
            video.setAuthor(author);
            video.setVideoUrl(videoUrl);
            video.setRuntime(SpiderUtils.parseToDate(SpiderUtils.formatProperty("runtime", videoInfo)));
            video.setStar(Long.valueOf(SpiderUtils.formatProperty("star", videoInfo)));
            video.setViews(Long.valueOf(SpiderUtils.formatProperty("views", videoInfo)));
            video.setUploadTime(SpiderUtils.parseToDate(uploadTime));

            page.putField("video", video);

            log.info("title ---------- {}", page.getHtml().xpath("//title/text()"));
            log.info("author ---------- {}", page.getHtml().xpath("//a[@href='" + userId + "']/span/text()"));
            log.info("Video Url ---------- {}", page.getHtml().xpath("//video[@id='vid']/source/@src").get());
            log.info("runtime ---------- {}", page.getHtml().xpath("//div[@class='boxPart']/text()").get().trim().split("[\\s\\p{Zs}]+")[0]);
            log.info("views ---------- {}", page.getHtml().xpath("//div[@class='boxPart']/text()").get().trim().split("[\\s\\p{Zs}]+")[1]);
            log.info("star ---------- {}", page.getHtml().xpath("//div[@class='boxPart']/text()").get().trim().split("[\\s\\p{Zs}]+")[3]);
            log.info("uploadTime ---------- {}", page.getHtml().xpath("//div[@id='videodetails-content']/span[@class='title']/text()"));
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public void crawl() {
        HttpClientDownloader downloader = new HttpClientDownloader();

        Spider.create(new S91RepoPageProcessor())
                .addUrl("http://91.91p18.space/v.php?next=watch")
                .setDownloader(new AbstractDownloaderProxy(downloader))
                .addPipeline(pornVideoPipeline)
                .thread(10)
                .run();
    }

}
