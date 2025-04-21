package edu.czjtu.big_event_demo.service.impl;

import java.time.LocalDateTime;

import javax.management.Query;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import edu.czjtu.big_event_demo.entity.User;
import edu.czjtu.big_event_demo.mapper.UserMapper;
import edu.czjtu.big_event_demo.service.UserService;
import edu.czjtu.big_event_demo.util.MD5Util;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User getUserByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return this.getOne(queryWrapper);
    }

    @Override
    public boolean register(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setUpdateTime(LocalDateTime.now());
        user.setCreateTime(LocalDateTime.now());
        return this.save(user);
    }

}
