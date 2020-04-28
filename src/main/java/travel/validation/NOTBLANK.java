package travel.validation;

import travel.util.ErrorCode;
import travel.validation.impl.NotBlankImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(ElementType.FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = NotBlankImpl.class)
@Documented
public @interface NOTBLANK {
    String message() default ErrorCode.Code.NOT_BLANK;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

