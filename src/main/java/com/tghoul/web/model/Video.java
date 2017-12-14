package com.tghoul.web.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Table;
import java.util.Date;

/**
 * @author tghoul
 * @date 2017/11/23 17:51
 */
@Data
@Table(name = "porn_video")
public class Video extends BaseEntity {

    /** 视频作者 */
    private String author;

    /** 视频标题 */
    private String title;

    /** 视频链接 */
    private String videoUrl;

    /** 视频缩略图链接 */
    private String imageUrl;

    /** 视频时长 */
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private Date runtime;

    /** 浏览量 */
    private Long views;

    /** 收藏量 */
    private Long star;

    /** 上传时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date uploadTime;

}
