package ru.test.yandex.internal

import io.github.bonigarcia.wdm.WebDriverManager
import io.qameta.atlas.Atlas
import io.qameta.atlas.WebDriverConfiguration
import org.openqa.selenium.Capabilities
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.RemoteWebDriver
import ru.test.yandex.internal.extensions.IsPageOpenedExtension
import ru.test.yandex.internal.extensions.MethodExtension
import ru.test.yandex.internal.extensions.PageUrlExtension
import ru.test.yandex.model.Config
import ru.test.yandex.model.Site

/**
 * Created on 10.09.2018
 *
 * @author Yuri Kudryavtsev
 *         skype: yuri.kudryavtsev.indeed
 *         email: ykudryavtsev@maxilect.com
 */

class DriverFactory {

    private static ThreadLocal<WebDriver> wrappedDriver = new ThreadLocal<>()
    private static ThreadLocal<Atlas> wrappedAtlas = new ThreadLocal<>()
    private static ThreadLocal<Capabilities> wrappedOptions = new ThreadLocal<>()

    private static boolean driverIsDownloaded = false

    private static void downloadWebDriver() {
        if(!driverIsDownloaded) {
            WebDriverManager.chromedriver().setup()
            driverIsDownloaded = true
        }
    }

    private static WebDriver renewDriver(ChromeOptions desiredOptions) {
        def hubUrl = Config.seleniumConfig.hubUrl
        WebDriver driver
        if(hubUrl != null) {
            def url = new URL(hubUrl)
            driver = new RemoteWebDriver(url, desiredOptions)
        } else {
            downloadWebDriver()
            driver = new ChromeDriver(desiredOptions)
        }
        wrappedDriver.set(driver)
        wrappedOptions.set(desiredOptions)
        return driver
    }

    private static Atlas renewAtlas(ChromeOptions desiredOptions) {
        def atlas = new Atlas(new WebDriverConfiguration(getDriver(desiredOptions))).
                listener(new LogListener()).
                extension(new PageUrlExtension()).
                extension(new IsPageOpenedExtension()).
                extension(new MethodExtension())
        wrappedAtlas.set(atlas)
        return atlas
    }

    static WebDriver getDriver(ChromeOptions desiredOptions) {
        def driver = wrappedDriver.get()
        if(driver == null) {
            //Создаем драйвер
            driver = renewDriver(desiredOptions)
        } else {
            if(wrappedOptions.get() != desiredOptions) {
                //Закрываем существующий драйвер
                driver.quit()
                //Пересоздаем драйвер с новыми опциями
                driver = renewDriver(desiredOptions)
            }
        }
        return driver
    }

    static Atlas getAtlas(ChromeOptions desiredOptions) {
        def atlas = wrappedAtlas.get()
        if(atlas == null) {
            atlas = renewAtlas(desiredOptions)
        } else {
            if(wrappedOptions.get() != desiredOptions) {
                atlas = renewAtlas(desiredOptions)
            }
        }
        return atlas
    }

    static <T extends CustomWebPage> T initPage(Class<T> page, ChromeOptions chromeOptions) {
        return getAtlas(chromeOptions).create(getDriver(chromeOptions), page)
    }

    static <T extends CustomWebPage> T openPage(Class<T> page, ChromeOptions chromeOptions) {
        def preparedPage = initPage(page, chromeOptions)
        preparedPage.openPage(page)
        assert preparedPage.isPageOpened(page)
        return preparedPage
    }

    static boolean isPageOpened(Class page, ChromeOptions chromeOptions) {
        def preparedPage = initPage(page, chromeOptions)
        return preparedPage.isPageOpened(page)
    }

    static void endSession() {
        if(wrappedAtlas.get() != null) {
            wrappedAtlas.remove()
        }
        def driver = wrappedDriver.get()
        if(driver != null) {
            driver.quit()
            wrappedDriver.remove()
            wrappedOptions.remove()
        }
    }

    static void initSite(ChromeOptions chromeOptions) {
        Site.methods.each {
            Class clazz = it.returnType
            if(clazz instanceof CustomWebPage) {
                initPage(clazz as Class<CustomWebPage>, chromeOptions)
            }
        }
    }
}
