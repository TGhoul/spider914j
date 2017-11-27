package com.tghoul.mapper;

import com.tghoul.model.Video;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author tghoul
 * @date 2017/11/24 9:18
 */
@Mapper
public interface VideoMapper {

    /**
     *
     * @return
     */
    @Select("SELECT * FROM PORN_VIDEO WHERE ID = #{id}")
    Video findById(@Param("id") Long id);

    /**
     * 保存视频信息
     * @param video 视频
     * @return
     */
    @Insert("INSERT INTO PORN_VIDEO(title, video_url, author, runtime, star, views, upload_time) " +
            "VALUES(#{title}, #{videoUrl}, #{author}, #{runtime}, #{star}, #{views}, #{uploadTime})")
    int save(Video video);
}
