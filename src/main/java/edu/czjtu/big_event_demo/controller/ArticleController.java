package edu.czjtu.big_event_demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.czjtu.big_event_demo.entity.Article;
import edu.czjtu.big_event_demo.service.ArticleService;
import edu.czjtu.big_event_demo.util.Result;
import edu.czjtu.big_event_demo.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    // 新增文章
    @PostMapping
    public Result<String> add(@RequestBody Article article) {
        article.setCreateUser(ThreadLocalUtil.getUserId());
        articleService.save(article);
        return Result.success();
    }

    // 删除文章
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Integer id) {
        Article article = new Article();
        article.setId(id);
        article.setState("已删除"); // 假设"已删除"是用字符串表示的状态
        if (articleService.updateById(article)) {
            return Result.success();
        } else {
            return Result.error("删除失败");
        }
    }

    // 修改文章
    @PutMapping
    public Result<String> update(@RequestBody Article article) {
        article.setUpdateTime(null);
        if (articleService.updateById(article)) {
            return Result.success();
        } else {
            return Result.error("修改失败");
        }
    }

    // 根据ID查询文章
    @GetMapping("/{id}")
    public Result<Article> getById(@PathVariable Integer id) {
        Article article = articleService.getById(id);
        return Result.success(article);
    }

    // 分页查询文章列表
    @GetMapping("/page")
    public Result<Page<Article>> page(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String title) {

        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(title != null, Article::getTitle, title);
        queryWrapper.ne(Article::getState, "已删除"); // 排除已删除
        queryWrapper.orderByDesc(Article::getCreateTime);

        Page<Article> pageInfo = new Page<>(page, pageSize);
        articleService.page(pageInfo, queryWrapper);

        return Result.success(pageInfo);
    }
}