package edu.czjtu.big_event_demo.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;//主键ID
    private String username;//用户名
    @JsonIgnore
    private String password;//密码
    @NotNull(message = "昵称不能为空")
    @Pattern(regexp = "^\\S{2,10}$", message = "昵称只能包含中文、英文、数字，长度2-10")
    private String nickname;//昵称
    @Email(message = "邮箱格式不正确")
    private String email;//邮箱
    private String userPic;//用户头像地址
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
}
