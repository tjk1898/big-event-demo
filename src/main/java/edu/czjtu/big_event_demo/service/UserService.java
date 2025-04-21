package edu.czjtu.big_event_demo.service;

import com.baomidou.mybatisplus.extension.service.IService;

import edu.czjtu.big_event_demo.entity.User;

public interface UserService extends IService<User> {

    User getUserByUsername(String username);
    
    boolean register(String username, String password);

}
