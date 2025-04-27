package edu.czjtu.big_event_demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.czjtu.big_event_demo.entity.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}