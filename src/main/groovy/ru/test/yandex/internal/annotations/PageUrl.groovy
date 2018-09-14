package ru.test.yandex.internal.annotations

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

/**
 * Created on 13.09.2018
 *
 * @author Yuri Kudryavtsev
 *         skype: yuri.kudryavtsev.indeed
 *         email: ykudryavtsev@maxilect.com
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface PageUrl {
    String value() default ""
    String absoluteUrl() default ""
    String matchRegex() default ""
}