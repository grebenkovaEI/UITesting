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
    TicketSearch ticketSearch;
    BookingManage bookingManage;
    ErrorPage errorPage;

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
        ticketSearch = new TicketSearch(driver);
        bookingManage = new BookingManage(driver);
        errorPage = new ErrorPage(driver);

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

    @Test
    @DisplayName("Тест №2. Инициирование поиска")
    void ticketSearchTest() {
        //2. Убедиться, что сайт открылся:
        //а) текст заголовка страницы: Авиакомпания «Победа» - купить билеты на самолёт дешево онлайн, прямые и трансферные рейсы;
        Assertions.assertEquals("Авиакомпания «Победа» - купить авиабилеты онлайн, дешёвые билеты на самолёт, прямые и трансферные рейсы с пересадками", mainPage.getMainTitle());

        //б) на странице есть логотип Победы.
        Assertions.assertTrue(mainPage.logoVisability(), "Логотип не отображен");

        //3. Проскроллить страницу к блоку поиска билета и убедиться, что блок с поиском билета действительно отображается
        // (есть поле Откуда, Куда, Дата вылета Туда, Дата вылета Обратно)
        ticketSearch.scrollToSearchBlock();
        Assertions.assertTrue(ticketSearch.isPlaceFromVisible());
        Assertions.assertTrue(ticketSearch.isPlaceToVisible());
        Assertions.assertTrue(ticketSearch.isDateFromVisible());
        Assertions.assertTrue(ticketSearch.isDateToVisible());

        //4. Выбрать (или ввести) следующие критерии поиска:
        //откуда – Москва (без выбора аэропорта) + нажать Enter
        //куда – Санкт-Петербург + нажать Enter.
        ticketSearch.fillSearchForm("Москва", "Санкт-Петербург");

        //5. Нажать кнопку «Поиск».
        ticketSearch.clickSearchButton();

        //6. Убедиться, что около поля «Туда» появилась красная обводка.
        String borderColor = ticketSearch.getFailDataBorderColor();
        //System.out.println(borderColor);
        Assertions.assertTrue(borderColor.contains("rgb(213, 0, 98)") || borderColor.contains("rgba(213, 0, 98, 1)"));
    }

    @Test
    @DisplayName("Тест №3. Результаты поиска")
    void searchResultsTest() {
        //2. Убедиться, что сайт открылся:
        //а) текст заголовка страницы: Авиакомпания «Победа» - купить билеты на самолёт дешево онлайн, прямые и трансферные рейсы;
        Assertions.assertEquals("Авиакомпания «Победа» - купить авиабилеты онлайн, дешёвые билеты на самолёт, прямые и трансферные рейсы с пересадками", mainPage.getMainTitle());

        //б) на странице есть логотип Победы.
        Assertions.assertTrue(mainPage.logoVisability(), "Логотип не отображен");

        //3. Проскроллить страницу чуть ниже и кликнуть на пункт «Управление бронированием».
        bookingManage.scrollToManageBlock();
        bookingManage.clickButtonBookingManage();

        //4. Убедиться, что открылась необходимая страница:
        //а) есть поле «Номер заказа или билета»;
        Assertions.assertTrue(bookingManage.isTicketOrOrderNumberVisible());
        //б) есть поле «Фамилия клиента»;
        Assertions.assertTrue(bookingManage.isClientSurnameVisible());
        //в) есть кнопка «Поиск».
        Assertions.assertTrue(bookingManage.isButtonSearchVisible());

        String window = driver.getWindowHandle(); //запоминаем текущую вкладку

        //5. Ввести в поля ввода данные:
        //номер заказа – XXXXXX, фамилия – Qwerty
        //и нажать кнопку «Поиск».
        bookingManage.fillSearchForm("123456", "Qwerty");
        bookingManage.clickButtonSearch();

        //6. Убедиться, что в новой вкладке на экране отображается текст ошибки «Заказ с указанными параметрами не найден».
        errorPage.switchToNewTab(window);
        errorPage.clickCheckbox();
        errorPage.clickSearchButton();
        Assertions.assertEquals("Заказ с указанными параметрами не найден", errorPage.getErrorText());
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}

