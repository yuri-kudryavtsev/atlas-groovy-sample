package ru.test.yandex.model

import ru.test.yandex.model.pages.YandexResults
import ru.test.yandex.model.pages.YandexSearchPage

/**
 * Created on 14.09.2018
 *
 * @author Yuri Kudryavtsev
 *         skype: yuri.kudryavtsev.indeed
 *         email: ykudryavtsev@maxilect.com
 */

interface Site {
    YandexResults yandexResults()
    YandexSearchPage yandexSearchPage()
}
