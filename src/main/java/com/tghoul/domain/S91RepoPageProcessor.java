package com.tghoul.domain;

import com.tghoul.proxy.AbstractDownloaderProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.FpUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zpj
 * @date 2017/11/6 17:40
 */
public class S91RepoPageProcessor implements PageProcessor {
    /** 日志 */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private List<String> url = new ArrayList<>();

    private Site site = Site
            .me()
            //重试次数
            .setRetryTimes(10)
            .setCycleRetryTimes(10)
            //抓取间隔
            .setSleepTime(3000);

    @Override
    public void process(Page page) {
        page.addTargetRequests(
                page.getHtml()
                        .xpath("//div[@id='videobox']/table/tbody/tr/td/div[@class='listchannel']/a")
                        .links()
                        .regex("http://91\\.91p18\\.space/view_video\\.php.*")
                        .all());
        page.putField("videoUrl", page.getHtml().xpath("//video[@id='vid']/source/@src").get());
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
//        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
//        httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(new Proxy("127.0.0.1", 1080)));

        //使用代理类定制化downloader
        AbstractDownloaderProxy downloaderProxy = new AbstractDownloaderProxy(new HttpClientDownloader());
        Spider.create(new S91RepoPageProcessor())
              .addUrl("http://91.91p18.space/v.php?next=watch")
              .setDownloader(downloaderProxy)
              .addPipeline(new JsonFilePipeline("D:\\webmagic\\"))
              .thread(5)
              .run();
    }
}
