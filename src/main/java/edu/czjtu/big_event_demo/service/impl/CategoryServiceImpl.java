package edu.czjtu.big_event_demo.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import edu.czjtu.big_event_demo.entity.Category;
import edu.czjtu.big_event_demo.mapper.CategoryMapper;
import edu.czjtu.big_event_demo.service.CategoryService;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
}