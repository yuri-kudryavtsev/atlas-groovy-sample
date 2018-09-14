package ru.test.yandex.internal

import io.qameta.atlas.api.Listener
import io.qameta.atlas.internal.Configuration
import io.qameta.atlas.util.MethodInfo
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Created on 13.09.2018
 *
 * @author Yuri Kudryavtsev
 *         skype: yuri.kudryavtsev.indeed
 *         email: ykudryavtsev@maxilect.com
 */

class LogListener implements Listener {

    private Logger LOGGER = LoggerFactory.getLogger(this.class)

    @Override
    void beforeMethodCall(MethodInfo methodInfo, Configuration configuration) {
        def methodName = methodInfo.method.name
        StringBuilder args = new StringBuilder()
        methodInfo.args.each {
            args.append(it.toString())
            args.append("; ")
        }
        LOGGER.info("Before Method: ${methodName}(${args.toString()})")
    }

    @Override
    void afterMethodCall(MethodInfo methodInfo, Configuration configuration) {
        def methodName = methodInfo.method.name
        StringBuilder args = new StringBuilder()
        methodInfo.args.each {
            args.append(it.toString())
            args.append("; ")
        }
        LOGGER.info("After Method: ${methodName}(${args.toString()})")
    }

    @Override
    void onMethodReturn(MethodInfo methodInfo, Configuration configuration, Object o) {
        def methodName = methodInfo.method.name
        StringBuilder args = new StringBuilder()
        methodInfo.args.each {
            args.append(it.toString())
            args.append("; ")
        }
        LOGGER.info("Method: ${methodName}(${args.toString()}) return data: ${o.toString()}")
    }

    @Override
    void onMethodFailure(MethodInfo methodInfo, Configuration configuration, Throwable throwable) {
        def methodName = methodInfo.method.name
        StringBuilder args = new StringBuilder()
        methodInfo.args.each {
            args.append(it.toString())
            args.append("; ")
        }
        LOGGER.info("Method: ${methodName}(${args.toString()}) throw exception: ${throwable.toString()}")
    }
}
