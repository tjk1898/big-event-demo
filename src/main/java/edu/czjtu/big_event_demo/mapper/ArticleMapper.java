package edu.czjtu.big_event_demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.czjtu.big_event_demo.entity.Article;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
}