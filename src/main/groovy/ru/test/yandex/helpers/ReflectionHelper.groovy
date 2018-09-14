package ru.test.yandex.helpers

import ru.test.yandex.internal.annotations.PageUrl
import ru.test.yandex.model.Config

import java.lang.annotation.Annotation

/**
 * Created on 13.09.2018
 *
 * @author Yuri Kudryavtsev
 *         skype: yuri.kudryavtsev.indeed
 *         email: ykudryavtsev@maxilect.com
 */

class ReflectionHelper {

    static String getClassAnnotationValue(Class classType, Class annotationType, String attributeName) {
        String value = null

        Annotation annotation = classType.getAnnotation(annotationType)
        if (annotation != null) {
            try {
                value = (String) annotation.annotationType().getMethod(attributeName).invoke(annotation)
            } catch (Exception ignored) {
            }
        }

        return value
    }

    static String getPageUrl(Class classType) {
        def relativeUrl = getClassAnnotationValue(classType, PageUrl, "value")
        def absoluteUrl = getClassAnnotationValue(classType, PageUrl, "absoluteUrl")
        def domain = Config.seleniumConfig.domain

        def url
        if(!absoluteUrl.isEmpty()) {
            url = absoluteUrl
        } else {
            if(domain != null) {
                url = domain + relativeUrl
            } else {
                url = relativeUrl
            }
        }
        return url
    }

    static String getUrlRegex(Class classType) {
        return getClassAnnotationValue(classType, PageUrl, "matchRegex")
    }
}
