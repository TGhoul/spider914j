package com.tghoul.proxy;

import com.tghoul.util.SpiderUtils;
import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.AbstractDownloader;

/**
 * @author zpj
 * @date 2017/11/9
 * 定制化downloader代理
 */
@Slf4j
public class AbstractDownloaderProxy extends AbstractDownloader {

    /** 被代理的downloader */
    private AbstractDownloader downloader;

    public AbstractDownloaderProxy(AbstractDownloader downloader) {
        this.downloader = downloader;
    }

    @Override
    public Page download(Request request, Task task) {
        task.getSite()
                .addHeader("X-Forwarded-For",
                        SpiderUtils.randomIP());
        task.getSite()
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:53.0) Gecko/20100101 Firefox/53.0");
        task.getSite()
                .addHeader("referer",
                        "http://91.91p18.space/v.php?next=watch");
        request.addHeader("X-Forwarded-For", SpiderUtils.randomIP());
        request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:53.0) Gecko/20100101 Firefox/53.0");
        request.addCookie("language", "cn_CN");
        request.setMethod("GET");
        log.info("{}", request.toString());
        return downloader.download(request, task);
    }

    @Override
    public void setThread(int i) {
        downloader.setThread(i);
    }


}
