package com.tghoul.proxy;

import com.virjar.dungproxy.webmagic7.DungProxyDownloader;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;

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
            return StringUtils.containsIgnoreCase(page.getRawText(), "作为游客");
        }
    }
}
