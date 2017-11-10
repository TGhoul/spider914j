package com.tghoul.proxy;

import com.tghoul.util.SpiderUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.AbstractDownloader;
import us.codecraft.webmagic.proxy.ProxyProvider;

import java.net.SocketTimeoutException;

/**
 * @author zpj
 * @date 2017/11/9
 * 定制化downloader代理
 */
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
                        SpiderUtil.randomIP());
        task.getSite()
                .addHeader("User-Agent",
                        "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36");
        task.getSite()
                .addHeader("referer",
                        "http://91.91p17.space/v.php?next=watch");

        return downloader.download(request, task);
    }

    @Override
    public void setThread(int i) {
        downloader.setThread(i);
    }
}
