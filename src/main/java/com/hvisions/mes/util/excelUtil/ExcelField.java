package com.hvisions.mes.util.excelUtil;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**  自定义注解类  描述excel表的相关信息
 *
 * @author dpeng
 * @create 2019-03-19 14:42
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelField {

    /**
     *  属性的标题名称
     */
    String title();

    /**
     *  在excel中的顺序
     */
    int colum() default 9999;

    Class claz();

    int maxLength();
}
