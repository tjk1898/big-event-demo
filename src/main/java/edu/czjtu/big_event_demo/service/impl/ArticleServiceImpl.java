package edu.czjtu.big_event_demo.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.czjtu.big_event_demo.entity.Article;
import edu.czjtu.big_event_demo.mapper.ArticleMapper;
import edu.czjtu.big_event_demo.service.ArticleService;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
}