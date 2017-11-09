package com.tghoul.proxy;

import com.tghoul.util.SpiderUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.AbstractDownloader;

/**
 * @author zpj
 * @date 2017/11/9
 * 定制化downloader代理
 */
public class AbstractDownloaderProxy extends AbstractDownloader {

    private AbstractDownloader downloader;

    public AbstractDownloaderProxy(AbstractDownloader downloader) {
        this.downloader = downloader;
    }

    @Override
    public Page download(Request request, Task task) {
        task.getSite().addHeader("X-Forwarded-For", SpiderUtil.randomIP());
        return downloader.download(request, task);
    }

    @Override
    public void setThread(int i) {
        downloader.setThread(i);
    }
}
