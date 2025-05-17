package edu.czjtu.big_event_demo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.Default;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("category")
public class Category {
    @TableId(type = IdType.AUTO)
    @NotNull(groups = UPDATE.class)
    private Integer id;// 主键ID
    @NotNull(groups = Default.class)
    @Size(min = 1, max = 100, groups = {ADD.class, UPDATE.class})
    private String categoryName;// 分类名称
    private String categoryAlias;// 分类别名
    private Integer createUser;// 创建人ID
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;// 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;// 更新时间
}
