package edu.czjtu.big_event_demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import edu.czjtu.big_event_demo.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
