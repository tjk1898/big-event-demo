package edu.czjtu.big_event_demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.czjtu.big_event_demo.entity.Article;
import edu.czjtu.big_event_demo.mapper.ArticleMapper;
import edu.czjtu.big_event_demo.service.ArticleService;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
}