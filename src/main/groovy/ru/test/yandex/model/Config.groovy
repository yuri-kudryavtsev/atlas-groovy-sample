package ru.test.yandex.model

import org.aeonbits.owner.ConfigCache
import ru.test.yandex.properties.SeleniumConfig

/**
 * Created on 14.09.2018
 *
 * @author Yuri Kudryavtsev
 *         skype: yuri.kudryavtsev.indeed
 *         email: ykudryavtsev@maxilect.com
 */

class Config {
    static SeleniumConfig getSeleniumConfig() {
        return ConfigCache.getOrCreate(SeleniumConfig)
    }
}
