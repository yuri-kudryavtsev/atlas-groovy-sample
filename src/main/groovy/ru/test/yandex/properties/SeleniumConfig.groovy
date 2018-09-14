package ru.test.yandex.properties

import org.aeonbits.owner.Config
import org.aeonbits.owner.Config.Key

/**
 * Created on 14.09.2018
 *
 * @author Yuri Kudryavtsev
 *         skype: yuri.kudryavtsev.indeed
 *         email: ykudryavtsev@maxilect.com
 */

interface SeleniumConfig extends Config {
    @Key("domain")
    String getDomain()

    @Key("hub.url")
    String getHubUrl()
}