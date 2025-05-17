package edu.czjtu.big_event_demo.controller;

import edu.czjtu.big_event_demo.entity.ADD;
import edu.czjtu.big_event_demo.entity.Category;
import edu.czjtu.big_event_demo.entity.Result;
import edu.czjtu.big_event_demo.entity.UPDATE;
import edu.czjtu.big_event_demo.service.CategoryService;
import edu.czjtu.big_event_demo.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // 添加分类
    @PostMapping
    public Result<Void> add(@RequestBody @Validated(ADD.class) Category category) {
        category.setCreateUser(ThreadLocalUtil.getUserId());
        categoryService.save(category);
        return Result.success();
    }

    // 修改分类
    @PutMapping
    public Result<Void> update(@RequestBody @Validated(UPDATE.class) Category category) {
        category.setUpdateTime(null);
        categoryService.updateById(category);
        return Result.success();
    }

    // 获取分类详情
    @GetMapping("/{id}")
    public Result<Category> getById(@PathVariable Integer id) {
        Category category = categoryService.getById(id);
        return Result.success(category);
    }

    // 获取分类列表
    @GetMapping
    public Result<List<Category>> list() {
        List<Category> categories = categoryService.list();
        return Result.success(categories);
    }
}