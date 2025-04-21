package edu.czjtu.big_event_demo.controller;

import java.util.Map;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.czjtu.big_event_demo.entity.Result;
import edu.czjtu.big_event_demo.entity.User;
import edu.czjtu.big_event_demo.service.UserService;
import edu.czjtu.big_event_demo.util.JWTUtil;
import edu.czjtu.big_event_demo.util.MD5Util;
import jakarta.validation.constraints.Pattern;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Validated
@RestController
@RequestMapping("/user")
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public Result<String> register(
            @Pattern(regexp = "^\\S{5,16}$", message = "用户名长度必须在5到16个字符之间，且不能包含空格") String username,
            @Pattern(regexp = "^\\S{5,16}$", message = "密码长度必须在5到16个字符之间，且不能包含空格") String password) {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            userService.register(username, MD5Util.getMD5String(password));
            return Result.success("注册成功");
        } else {
            return Result.error("用户名已存在");
        }
    }

    @PostMapping("/login")
    public Result login(
            @Pattern(regexp = "^\\S{5,16}$", message = "用户名长度必须在5到16个字符之间，且不能包含空格") String username,
            @Pattern(regexp = "^\\S{5,16}$", message = "密码长度必须在5到16个字符之间，且不能包含空格") String password) {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            return Result.error("用户名不存在");
        } else if (MD5Util.getMD5String(password).equals(user.getPassword())) {
            Map<String, Object> map = Map.of("id", user.getId(), "username", username);
            String token = JWTUtil.generateToken(map);
            return Result.success(token);
        } else {
            return Result.error("密码错误");
        }
    }

    @GetMapping("/userinfo")
    public Result<User> getUserInfo(
            @RequestParam(name = "username") String username) throws Exception {

        User user = userService.getUserByUsername(username);
        if (user == null) {
            return Result.error("用户不存在");
        } else {
            return Result.success(user);
        }
    }

}
