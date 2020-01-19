/**
 * 
 */
package com.imooc.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * @author zhailiang
 *
 */
@Target({ElementType.METHOD, ElementType.FIELD})//用在方法上和字段上
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MyConstraintValidator.class)//当前这个注解用 MyConstraintValidator这个类完成校验逻辑
public @interface MyConstraint {


	//如果想用于@Valid 这三个属性必须写 参考@NotBlank

	String message();

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

}
