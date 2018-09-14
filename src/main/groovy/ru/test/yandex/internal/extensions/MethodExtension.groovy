package ru.test.yandex.internal.extensions

import io.qameta.atlas.internal.Configuration
import io.qameta.atlas.util.MethodInfo

import java.lang.invoke.MethodHandles
import java.lang.reflect.Constructor
import java.lang.reflect.Method

/**
 * Created on 13.09.2018
 *
 * @author Yuri Kudryavtsev
 *         skype: yuri.kudryavtsev.indeed
 *         email: ykudryavtsev@maxilect.com
 */

class MethodExtension implements io.qameta.atlas.api.MethodExtension {
    boolean test(Method method) {
        return method.name == "search"
    }

    Object invoke(Object proxy, MethodInfo methodInfo, Configuration config) throws Throwable {
        Class<?> declaringClass = methodInfo.getMethod().getDeclaringClass()
        Constructor<MethodHandles.Lookup> constructor = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class, Integer.TYPE)
        constructor.setAccessible(true)
        return ((MethodHandles.Lookup)constructor.newInstance(declaringClass, 2)).unreflectSpecial(methodInfo.getMethod(), declaringClass).bindTo(proxy).invokeWithArguments(methodInfo.getArgs())
    }
}
