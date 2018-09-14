package ru.test.yandex.internal

import io.qameta.atlas.WebPage

/**
 * Created on 13.09.2018
 *
 * @author Yuri Kudryavtsev
 *         skype: yuri.kudryavtsev.indeed
 *         email: ykudryavtsev@maxilect.com
 */

interface CustomWebPage extends WebPage {
    void openPage(Class<? extends CustomWebPage> clazz)
    boolean isPageOpened(Class<? extends CustomWebPage> clazz)
}