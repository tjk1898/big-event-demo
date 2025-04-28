package edu.czjtu.big_event_demo.except;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import edu.czjtu.big_event_demo.entity.Result;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // 处理Validation参数校验异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleValidationException(MethodArgumentNotValidException e) {
        // 从异常中提取出第一个错误信息
        String errorMessage = e.getBindingResult()
                               .getFieldErrors()
                               .stream()
                               .findFirst()
                               .map(error -> error.getDefaultMessage())
                               .orElse("参数校验失败");
        return Result.error(errorMessage);
    }
    
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        String errorMessage = StringUtils.hasText(e.getMessage()) ?e.getMessage():"操作失败";
        return Result.error(errorMessage);
    }
}
