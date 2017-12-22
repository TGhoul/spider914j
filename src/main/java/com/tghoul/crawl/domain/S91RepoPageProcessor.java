package com.tghoul.crawl.domain;

import com.tghoul.web.model.Video;
import com.tghoul.crawl.pipeline.PornVideoPipeline;
import com.tghoul.crawl.proxy.AbstractDownloaderProxy;
import com.tghoul.crawl.scheduler.FileCacheCustomScheduler;
import com.tghoul.crawl.util.SpiderUtils;
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
 * 爬取思路总结：首先将入口（也就是首页）加入待爬取队列中，抓取最后一页的页码。然后建立循环
 * 用获取的页码拼接列表页的url，依次将每个视频列表页url加入待抓取队列。最后编写xpath抓取规则，
 * 把抓取的数据封装成实体，持久化数据。
 */
@Slf4j
@Component("s91RepoPageProcessor")
public class S91RepoPageProcessor implements PageProcessor {

    /**  */
    private Integer lastPageNo;
    /**  */
    private static final Integer START_PAGE_NO = 1;

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


        //获取最后一页
        String endPage = page.getHtml().xpath("//div[@class='pagingnav']/form/a[5]/text()").get();
        if (lastPageNo == null) {
            lastPageNo = Integer.valueOf(endPage);
        }
        log.info("pageNo ------- {}", endPage);
        //url地址
        List<String> videoUrls = new ArrayList<>(20);
        for (int pageNo = START_PAGE_NO; pageNo <= lastPageNo; pageNo++) {
            page.addTargetRequest("http://91.91p18.space/v.php?next=watch&page=" + pageNo);
            videoUrls.addAll(
                    page
                        .getHtml()
                        .xpath("//div[@id='videobox']/table/tbody/tr/td/div[@class='listchannel']/a")
                        .links()
                        .regex("http://91\\.91p18\\.space/view_video\\.php.*")
                        .all()
            );
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
                .setScheduler(new FileCacheCustomScheduler(ClassLoader.getSystemResource("").getPath(), ".*watch$"))
                .addPipeline(pornVideoPipeline)
                .thread(10)
                .run();
    }

}
