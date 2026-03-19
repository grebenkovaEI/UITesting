package ru.stepup.pajeObj;

import com.codeborne.selenide.As;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.stepup.BaseTest;

import java.time.Duration;

@Slf4j
public class PobedaTest {

    WebDriver driver;
    WebDriverWait wait;
    MainPage mainPage;
    PopupInfo popupInfo;

    @BeforeEach
    void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/ekolyshkina/Desktop/chromedriver-win64/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.manage().window().setSize(new Dimension(1466, 768));

        mainPage = new MainPage(driver);
        popupInfo = new PopupInfo(driver);

        //1. Перейти на сайт pobeda.aero.
        driver.get("https://www.flypobeda.ru/");
    }

    @Test
    @DisplayName("Тест №1. Всплывающее окно")
    void popupTest() {
        //2. Убедиться, что сайт открылся:
        //а) текст заголовка страницы: Авиакомпания «Победа» - купить билеты на самолёт дешево онлайн, прямые и трансферные рейсы;
        Assertions.assertEquals("Авиакомпания «Победа» - купить авиабилеты онлайн, дешёвые билеты на самолёт, прямые и трансферные рейсы с пересадками", mainPage.getMainTitle());

        //б) на странице есть логотип Победы.
        Assertions.assertTrue(mainPage.logoVisability(), "Логотип не отображен");

        //3. Навести мышку на пункт «Информация».
        mainPage.hoverInfoMenu();

        //4. Убедиться, что появилось всплывающее окно, которое содержит следующие заголовки: «Подготовка к полету», «Полезная информация», «О компании».
        Assertions.assertTrue(popupInfo.isInfoFlightVisible());
        Assertions.assertTrue(popupInfo.isInfoUsefulVisible());
        Assertions.assertTrue(popupInfo.isInfoCompanyVisible());

    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}

