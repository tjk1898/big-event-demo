package edu.czjtu.big_event_demo.controller;

import edu.czjtu.big_event_demo.entity.Result;
import edu.czjtu.big_event_demo.entity.User;
import edu.czjtu.big_event_demo.service.UserService;
import edu.czjtu.big_event_demo.util.JWTUtil;
import edu.czjtu.big_event_demo.util.MD5Util;
import edu.czjtu.big_event_demo.util.ThreadLocalUtil;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.constraints.Pattern;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
            Map<String, Object> map = Map.of("userId", user.getId(), "username", username);
            String token = JWTUtil.generateToken(map);
            return Result.success(token);
        } else {
            return Result.error("密码错误");
        }
    }

    @GetMapping()
    public Result<User> getUserInfo() throws Exception {
        User user = userService.getById(ThreadLocalUtil.getUserId());
        if (user == null) {
            return Result.error("用户不存在");
        } else {
            return Result.success(user);
        }
    }

    @PutMapping()
    public Result update(@Validated @RequestBody User user) {
        // 调用Service层方法进行更新
        userService.updateById(user);
        return Result.success();
    }

    @PatchMapping("/update_avatar")
    public Result updateAvatar(@RequestParam String avatarUrl) {
        User user = new User();
        user.setId(ThreadLocalUtil.getUserId());
        user.setUserPic(avatarUrl);
        if (userService.updateById(user)) {
            return Result.success("更新头像成功");
        } else {
            return Result.error("更新头像失败");
        }
    }

    @PatchMapping("/update_pwd")
    public Result updatePwd(@RequestBody Map<String, String> params) {
        String oldPwd = params.get("OPWD");
        String newPwd = params.get("NEWPWD");
        String repwd = params.get("R1PWD");

        // 校验参数是否为空
        if (StringUtils.isEmpty(oldPwd) || StringUtils.isEmpty(newPwd) || StringUtils.isEmpty(repwd)) {
            return Result.error("缺少必要的参数");
        }

        // 校验原密码是否正确
        User loginUser = userService.getUserByUsername(ThreadLocalUtil.getUserName());
        if (!MD5Util.getMD5String(oldPwd).equals(loginUser.getPassword())) {
            return Result.error("原密码填写不正确");
        }

        // 校验新密码与确认新密码是否一致
        if (!newPwd.equals(repwd)) {
            return Result.error("两次填写的新密码不一致");
        }

        User user = new User();
        user.setId(ThreadLocalUtil.getUserId());
        user.setPassword(MD5Util.getMD5String(newPwd));
        if (userService.updateById(user)) {
            return Result.success("密码修改成功");
        } else {
            return Result.error("密码修改失败");
        }
    }

    @GetMapping("/logout")
    public Result logout(@RequestHeader(value = "Authorization", required = false) String token) {
        if (token != null) {
            JWTUtil.deleteToken(token);
        }
        ThreadLocalUtil.clear();
        return Result.success("登出成功");
    }
}
