package com.mt.common.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 业务日志记录注解
 * Created by leadmax on 2021-7-26 10:56:28
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BusinessRecord {

    /**
     * 业务
     */
    String business();

    /**
     * 业务描述
     */
    String businessContent();

    /**
     * 实体
     */
    String entity();

    /**
     * 实体名称
     */
    String entityLabel();

    /**
     * 是否记录请求参数
     */
    boolean param() default true;

    /**
     * 是否记录返回结果
     */
    boolean result() default false;

}
