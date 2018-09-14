package ru.test.yandex.internal.extensions

import io.qameta.atlas.api.MethodExtension
import io.qameta.atlas.context.WebDriverContext
import io.qameta.atlas.internal.Configuration
import io.qameta.atlas.util.MethodInfo
import org.openqa.selenium.JavascriptExecutor

import java.lang.reflect.Method
import java.util.regex.Pattern

import static ru.test.yandex.helpers.ReflectionHelper.getPageUrl
import static ru.test.yandex.helpers.ReflectionHelper.getUrlRegex

/**
 * Created on 13.09.2018
 *
 * @author Yuri Kudryavtsev
 *         skype: yuri.kudryavtsev.indeed
 *         email: ykudryavtsev@maxilect.com
 */

class IsPageOpenedExtension implements MethodExtension {
    @Override
    Boolean invoke(Object o, MethodInfo methodInfo, Configuration config) throws Throwable {
        //TODO: проверка параметра
        Class clazz = methodInfo.args[0] as Class
        def webDriver = ((WebDriverContext) config.getContext(WebDriverContext.class).get()).getValue()
        def regex = getUrlRegex(clazz)
        def currentUrl = webDriver.currentUrl
        if(!regex.isEmpty()) {
            //проверка регэкса
            if(!Pattern.matches(regex, currentUrl)) {
                return false
            }
        } else {
            //проверка текущего URL
            if(getPageUrl(clazz) != currentUrl) {
                return false
            }
        }
        //JS-проверка
        try {
            def state = (webDriver as JavascriptExecutor).executeScript("return document.readyState").toString()
            return state == "complete"
        } catch (Throwable ignored) {
            return false
        }
    }

    @Override
    boolean test(Method method) {
        return method.name == "isPageOpened" && method.parameterCount == 1
    }
}
