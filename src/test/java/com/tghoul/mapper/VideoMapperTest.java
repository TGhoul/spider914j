package com.tghoul.mapper;

import com.tghoul.domain.S91RepoPageProcessor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author zpj
 * @date 2017/11/25
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class VideoMapperTest {
    @Resource
    private S91RepoPageProcessor s91RepoPageProcessor;

    @Test
    public void save() throws Exception {
        s91RepoPageProcessor.crawl();
    }
}