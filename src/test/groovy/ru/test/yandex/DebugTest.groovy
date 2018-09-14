package ru.test.yandex

import org.awaitility.Awaitility
import org.openqa.selenium.chrome.ChromeOptions
import org.testng.annotations.AfterMethod
import org.testng.annotations.Test
import ru.test.yandex.model.pages.YandexResults
import ru.test.yandex.model.pages.YandexSearchPage

import java.util.concurrent.TimeUnit

import static ru.test.yandex.internal.DriverFactory.endSession
import static ru.test.yandex.internal.DriverFactory.isPageOpened
import static ru.test.yandex.internal.DriverFactory.openPage

/**
 * Created on 11.09.2018
 *
 * @author Yuri Kudryavtsev
 *         skype: yuri.kudryavtsev.indeed
 *         email: ykudryavtsev@maxilect.com
 */

class DebugTest {

    @AfterMethod
    void tearDown() {
        endSession()
    }

    private static void paramTest(String value, ChromeOptions chromeOptions) {
        (openPage(YandexSearchPage, chromeOptions) as YandexSearchPage).with {
            search(value)
        }
        Awaitility.await("Страница не открылась!").
                ignoreExceptions().
                pollInSameThread().
                atMost(60, TimeUnit.SECONDS).
                pollInterval(1, TimeUnit.SECONDS).
                until({
                    isPageOpened(YandexResults, chromeOptions)
                })
    }

    static ChromeOptions optionsA() {
        def chromeOptions = new ChromeOptions()
        chromeOptions.addArguments("window-size=320,240")
        chromeOptions.setCapability("screenResolution", "320x240x24")
        chromeOptions.setCapability("enableVNC", true)
        chromeOptions.setCapability("enableVideo", true)
        return chromeOptions
    }

    static ChromeOptions optionsB() {
        def chromeOptions = new ChromeOptions()
        chromeOptions.addArguments("window-size=640,480")
        chromeOptions.setCapability("screenResolution", "640x480x24")
        chromeOptions.setCapability("enableVNC", true)
        chromeOptions.setCapability("enableVideo", true)
        return chromeOptions
    }

    @Test
     void simpleParallelTest() {
        paramTest("junit", optionsA())
    }
//    @Test
//    void simpleParallelTest2() {
//        paramTest("spock", optionsB())
//    }
//    @Test
//    void simpleParallelTest3() {
//        paramTest("testng", optionsB())
//    }
//    @Test
//    void simpleParallelTest4() {
//        paramTest("java", optionsA())
//    }

}
