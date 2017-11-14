package com.tghoul.domain;

import com.tghoul.proxy.AbstractDownloaderProxy;
import com.tghoul.proxy.OfflineProxyDownloader;
import com.virjar.dungproxy.client.ippool.IpPoolHolder;
import com.virjar.dungproxy.client.ippool.config.DungProxyContext;
import com.virjar.dungproxy.client.ippool.config.ProxyConstant;
import com.virjar.dungproxy.client.ippool.strategy.impl.WhiteListProxyStrategy;
import com.virjar.dungproxy.webmagic7.DungProxyDownloader;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.FpUtils;
import sun.security.krb5.internal.PAEncTSEnc;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.awt.*;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zpj
 * @date 2017/11/6 17:40
 */
public class S91RepoPageProcessor implements PageProcessor {
    /** 日志 */
    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private Site site = Site
            .me()
            //重试次数
            .setRetryTimes(10)
            .setCycleRetryTimes(10)
            //抓取间隔
            .setSleepTime(0)
            .setTimeOut(30000)
            .setUseGzip(true);

    @Override
    public void process(Page page) {
        //url地址
        List<String> videoUrls = new ArrayList<>(20);
        for (int pageNo = 1; pageNo < 10; pageNo++) {
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

        LOGGER.info("Request Url --------- {}", page.getRequest().getUrl());

        if (page.getUrl().toString().contains("view_video")) {
            page.putField("videoUrl", page.getHtml().xpath("//video[@id='vid']/source/@src").get());

            LOGGER.info("Video Url ---------- {}", page.getHtml().xpath("//video[@id='vid']/source/@src").get());
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {

        //WhiteListProxyStrategy whiteListProxyStrategy = new WhiteListProxyStrategy();
        //whiteListProxyStrategy.addAllHost("www.dytt8.net,www.ygdy8.net");

        // Step2 创建并定制代理规则
        //DungProxyContext dungProxyContext = DungProxyContext.create().setNeedProxyStrategy(whiteListProxyStrategy).setPoolEnabled(false);

        // Step3 使用代理规则初始化默认IP池
        IpPoolHolder.init(DungProxyContext.create().setPoolEnabled(true));

        Spider.create(new S91RepoPageProcessor())
              .addUrl("http://91.91p18.space/v.php?next=watch")
              .setDownloader(new OfflineProxyDownloader())
              .addPipeline(new JsonFilePipeline("D:\\webmagic\\"))
              .thread(10)
              .run();

    }
}
