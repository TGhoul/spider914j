package com.tghoul.proxy;

import com.tghoul.util.SpiderUtils;
import com.virjar.dungproxy.webmagic7.DungProxyDownloader;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NoHttpResponseException;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;

import java.io.IOException;

/**
 * @author zpj
 * @date 2017/11/14 13:45
 */
public class OfflineProxyDownloader extends DungProxyDownloader {
    @Override
    protected boolean needOfflineProxy(Page page) {
        // 父类默认下线 401和403
        if (super.needOfflineProxy(page)) {
            return true;
        } else {
            return StringUtils.containsIgnoreCase(page.getRawText(), "作为游客") ||
                   StringUtils.containsIgnoreCase(page.getRawText(), "400") ||
                   StringUtils.isBlank(page.getRawText());
        }
    }

    @Override
    protected boolean needOfflineProxy(IOException e) {
        //如果没有任何响应下线ip
        return e instanceof NoHttpResponseException;
    }

    @Override
    public Page download(Request request, Task task) {
        request.addHeader("User-Agent", SpiderUtils.randomAgent());
        request.addHeader("referer", "http://91.91p18.space/v.php?next=watch");
        request.setCharset("utf-8");
        request.setMethod("GET");
        return super.download(request, task);
    }
}
