package com.tghoul.pipeline;

import com.tghoul.mapper.VideoMapper;
import com.tghoul.model.Video;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.Resource;

/**
 * @author tghoul
 * @date 2017/11/25
 */
@Component("pornVideoPipeline")
@Transactional(rollbackFor = Exception.class)
public class PornVideoPipeline implements Pipeline {

    @Resource
    private VideoMapper videoMapper;

    @Override
    public void process(ResultItems resultItems, Task task) {
        Video video = resultItems.get("video");
        videoMapper.save(video);
    }
}
