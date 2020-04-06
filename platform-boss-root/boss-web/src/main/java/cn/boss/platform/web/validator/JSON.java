package cn.boss.platform.web.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = JSONValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JSON {

  String message() default "Invalid JSON string.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
