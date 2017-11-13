package com.tghoul.proxy;

import com.tghoul.util.SpiderUtils;
import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.AbstractDownloader;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

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
                .addHeader("User-Agent", SpiderUtils.randomAgent());
        task.getSite()
                .addHeader("referer",
                        "http://91.91p18.space/v.php?next=watch");
        request.addHeader("X-Forwarded-For", SpiderUtils.randomIP());
        request.addHeader("User-Agent", SpiderUtils.randomAgent());
        request.addHeader("Accept-Language","zh-CN,zh;q=0.8,en;q=0.6");

        //设置动态代理
        if (downloader instanceof HttpClientDownloader) {
            ((HttpClientDownloader) downloader)
                    .setProxyProvider(SimpleProxyProvider.from(SpiderUtils.randomProxy()));
        }

        log.info("");
        return downloader.download(request, task);
    }

    @Override
    public void setThread(int i) {
        downloader.setThread(i);
    }
}
