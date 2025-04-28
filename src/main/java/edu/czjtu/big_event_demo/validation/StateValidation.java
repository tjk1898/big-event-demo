package edu.czjtu.big_event_demo.validation;

import edu.czjtu.big_event_demo.anno.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StateValidation implements ConstraintValidator<State, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null){
            return false;
        }
        // 验证逻辑
        return "已发布".equals(value) || "草稿".equals(value) || "已删除".equals(value);
    }
}
