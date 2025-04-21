package edu.czjtu.big_event_demo.except;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import edu.czjtu.big_event_demo.entity.Result;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        String errorMessage = StringUtils.hasText(e.getMessage()) ?e.getMessage():"操作失败";
        return Result.error(errorMessage);
    }
}
