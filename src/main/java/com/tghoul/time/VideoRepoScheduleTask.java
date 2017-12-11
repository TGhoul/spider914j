package com.tghoul.time;

import com.tghoul.domain.S91RepoPageProcessor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author tghoul
 * @date 2017/12/11 11:13
 */
@Component
public class VideoRepoScheduleTask {
    /** 爬虫主进程 */
    @Resource
    private S91RepoPageProcessor s91RepoPageProcessor;

    /** 定时爬取，持久化数据 */
    @Scheduled()
    public void doCrawl() {
        s91RepoPageProcessor.crawl();
    }
}
