package com.tghoul.model;

import lombok.Data;

import java.util.Date;

/**
 * @author tghoul
 * @date 2017/11/23 17:51
 */
@Data
public class Video {

    /** 视频id */
    private Long id;

    /** 视频作者 */
    private String author;

    /** 视频标题 */
    private String title;

    /** 视频链接 */
    private String videoUrl;

    /** 视频缩略图链接 */
    private String imageUrl;

    /** 视频时长 */
    private Date runtime;

    /** 浏览量 */
    private Long views;

    /** 收藏量 */
    private Long star;

    /** 上传时间 */
    private Date uploadTime;

}
