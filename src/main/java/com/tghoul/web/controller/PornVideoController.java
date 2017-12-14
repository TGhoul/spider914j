package com.tghoul.web.controller;

import com.github.pagehelper.PageInfo;
import com.tghoul.web.model.Video;
import com.tghoul.web.service.VideoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tghoul
 * @date 2017/11/23 17:19
 *
 * 视频控制器
 */
@RestController
@RequestMapping("/porn_video")
public class PornVideoController {

    @Resource
    private VideoService videoService;

    @GetMapping("/list")
    public PageInfo<Video> videoList(Video video) {
        List<Video> videoList = videoService.getAllVideo(video);
        return new PageInfo<>(videoList);
    }
}
