package ru.test.yandex.model.pages

import io.qameta.atlas.AtlasWebElement
import io.qameta.atlas.extension.FindBy
import ru.test.yandex.internal.CustomWebPage
import ru.test.yandex.internal.annotations.PageUrl

/**
 * Created on 30.07.2018
 *
 * @author Yuri Kudryavtsev
 *         skype: yuri.kudryavtsev.indeed
 *         email: ykudryavtsev@maxilect.com
 */
@PageUrl(value = "/", matchRegex = ".*ya.*")
trait YandexSearchPage implements CustomWebPage {

    @FindBy("//input[@id='text']")
    abstract AtlasWebElement searchField()

    @FindBy("//div[@class='search2__button']/button")
    abstract AtlasWebElement searchButton()

    void search(String text) {
        searchField().sendKeys(text)
        searchButton().click()
    }
}
