package com.blog.mapper;

import com.blog.entity.Channel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 栏目 Mapper 接口
 * </p>
 *
 * @author zhaoguoshun
 * @since 2020-12-17
 */
@Repository
@Mapper
public interface ChannelMapper extends BaseMapper<Channel> {
    int create(Channel channel);

    int delete(Map<String,Object> paraMap);

    int update(Map<String,Object> paraMap);

    List<Channel> query(Map<String,Object> paramMap);

    Channel detail(Map<String,Object> paramMap);

    int count(Map<String,Object> paramMap);
}
