package ru.test.yandex.internal.extensions

import io.qameta.atlas.api.MethodExtension
import io.qameta.atlas.context.WebDriverContext
import io.qameta.atlas.internal.Configuration
import io.qameta.atlas.util.MethodInfo

import java.lang.reflect.Method

import static ru.test.yandex.helpers.ReflectionHelper.getPageUrl

/**
 * Created on 13.09.2018
 *
 * @author Yuri Kudryavtsev
 *         skype: yuri.kudryavtsev.indeed
 *         email: ykudryavtsev@maxilect.com
 */

class PageUrlExtension implements MethodExtension {

    @Override
    Object invoke(Object o, MethodInfo methodInfo, Configuration config) throws Throwable {
        //TODO: проверка параметра
        Class clazz = methodInfo.args[0] as Class
        def webDriver = ((WebDriverContext) config.getContext(WebDriverContext.class).get()).getValue()
        webDriver.get(getPageUrl(clazz))
        return o
    }

    @Override
    boolean test(Method method) {
        return method.name == "openPage" && method.parameterCount == 1
    }
}
