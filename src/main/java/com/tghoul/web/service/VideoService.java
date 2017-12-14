package com.tghoul.web.service;

import com.github.pagehelper.PageHelper;
import com.tghoul.web.mapper.VideoMapper;
import com.tghoul.web.model.Video;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 视频业务层
 * @author zpj
 * @date 2017/12/14 13:31
 */
@Service("videoService")
public class VideoService {
    @Resource
    private VideoMapper videoMapper;

    /**
     * 分页获取所有视频列表
     * @param video 视频实体
     * @return 视频列表
     */
    public List<Video> getAllVideo(Video video) {
        if (video.getPage() != null && video.getRows() != null) {
            PageHelper.startPage(video.getPage(), video.getRows(), "upload_time DESC");
        }
        return videoMapper.selectAll();
    }
}
