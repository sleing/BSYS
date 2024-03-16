package com.mt.common.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)

public @interface DColumn {

    /**
     * 中文名
     * @return
     */
    public long index() default 0L;


    /**
     * 中文名
     * @return
     */
    public String label() ;


    /**
     * 约束
     * @return
     */
    public String constrains() default "";

    /**
     * 提示
     * @return
     */
    public String comment() default "";

    /**
     * 组件:
     * 如果是数字且小数 例如 数字，小数
     * 如果是数字且金额 例如 数字，金额
     * @return
     */
    public String component() default "";

    /**
     * 搜索条件
     * 默认是不参与搜索
     * @return
     */
    public boolean where() default false;

    /**
     * 外鍵表名
     * 如果不是外键，则为Object.class
     * @return
     */
    public String foreignEntity() default "";

    /**
     * 码表的键
     * @return
     */
    public String codeTable() default "";

    /**
     * 码表的键
     * @return
     */
    public String codeTableOptions() default "";
    /**
     * 界面上面是否展示，生成代码可以控制界面是否展示
     * @return
     */
    public boolean pageShow() default true;
}
