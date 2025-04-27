package edu.czjtu.big_event_demo.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("article")
public class Article {
    @TableId(type = IdType.AUTO)
    private Integer id;//主键ID
    private String title;//文章标题
    private String content;//文章内容
    private String coverImg;//封面图像
    private String state;//发布状态 已发布|草稿
    private Integer categoryId;//文章分类id
    private Integer createUser;//创建人ID
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;// 创建时间    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;// 更新时间
}
