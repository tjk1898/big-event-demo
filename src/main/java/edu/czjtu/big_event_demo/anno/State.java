package edu.czjtu.big_event_demo.anno;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import edu.czjtu.big_event_demo.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = StateValidation.class)
public @interface State {
    String message() default "state参数的值只能是已发布、草稿或者已删除状态";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
