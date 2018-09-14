package ru.test.yandex.model.pages

import ru.test.yandex.internal.CustomWebPage
import ru.test.yandex.internal.annotations.PageUrl

/**
 * Created on 30.07.2018
 *
 * @author Yuri Kudryavtsev
 *         skype: yuri.kudryavtsev.indeed
 *         email: ykudryavtsev@maxilect.com
 */

@PageUrl(matchRegex = ".*text=.*")
trait YandexResults implements CustomWebPage {

}
