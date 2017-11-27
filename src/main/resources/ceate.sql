CREATE TABLE `pornhub`.`porn_video`(
 `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '视频id',
 `title` VARCHAR(32) NOT NULL COMMENT '视频标题',
 `author` VARCHAR(32) COMMENT '作者',
 `video_url` VARCHAR(255) COMMENT '视频url',
 `image_url` VARCHAR(255) COMMENT '视频缩略图url',
 `views` BIGINT COMMENT '浏览量',
 `star` BIGINT COMMENT '收藏量',
 `runtime` TIME COMMENT '时长',
 `upload_time` DATETIME COMMENT '上传时间',
 `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 `modify_time` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
 PRIMARY KEY (`id`)
 ) ENGINE=INNODB CHARSET=utf8mb4
 COMMENT='视频信息表';

