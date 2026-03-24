package ru.stepup.selenide;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import static com.codeborne.selenide.Selenide.open;

@Slf4j
@Epic("Авиакомпания «Победа»")
public class PobedaTest {
    ru.stepup.selenide.MainPage mainPage;
    PopupInfo popupInfo;
    TicketSearch ticketSearch;
    ru.stepup.selenide.BookingManage bookingManage;
    ru.stepup.selenide.ErrorPage errorPage;

    @BeforeEach
    @Step("Настройка тестового окружения, открытие браузера")
    void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/ekolyshkina/Desktop/chromedriver-win64/chromedriver.exe");
        Configuration.pageLoadTimeout = 30000;
        Configuration.timeout = 15000;
        Configuration.browserSize = "1466x768";

        mainPage = new MainPage();
        popupInfo = new PopupInfo();
        ticketSearch = new TicketSearch();
        bookingManage = new BookingManage();
        errorPage = new ErrorPage();

        //1. Перейти на сайт pobeda.aero.
        open("https://www.flypobeda.ru/");
    }

    @Test
    @Feature("Тест №1. Всплывающее окно")
    @Description("Проверка открытия сайта и появления всплывающего окна с заголовками: «Подготовка к полету», " +
            "«Полезная информация», «О компании» при наведении на пункт «Информация»")
    void popupTest() {
        //2. Убедиться, что сайт открылся:
        //а) текст заголовка страницы: Авиакомпания «Победа» - купить билеты на самолёт дешево онлайн, прямые и трансферные рейсы;
        Assertions.assertEquals("Авиакомпания «Победа» - купить авиабилеты онлайн, дешёвые билеты на самолёт, прямые и трансферные рейсы с пересадками",
                Selenide.title());
        //б) на странице есть логотип Победы.
        Assertions.assertTrue(mainPage.logoVisibility(), "Логотип не отображен");
        //3. Навести мышку на пункт «Информация».
        mainPage.hoverInfoMenu();
        //4. Убедиться, что появилось всплывающее окно, которое содержит следующие заголовки: «Подготовка к полету», «Полезная информация», «О компании».
        Assertions.assertTrue(popupInfo.isInfoFlightVisible());
        Assertions.assertTrue(popupInfo.isInfoUsefulVisible());
        Assertions.assertTrue(popupInfo.isInfoCompanyVisible());
    }

    @Test
    @Feature("Тест №2. Инициирование поиска")
    @Description("Проверка открытия сайта и отображения блока поиска билетов. Инициирование поиска. Проверка появления " +
            "красной обводки при попытке поиска с незаполненным полем «Туда»")
    void ticketSearchTest() {
        //2. Убедиться, что сайт открылся:
        //а) текст заголовка страницы: Авиакомпания «Победа» - купить билеты на самолёт дешево онлайн, прямые и трансферные рейсы;
        Assertions.assertEquals("Авиакомпания «Победа» - купить авиабилеты онлайн, дешёвые билеты на самолёт, прямые и трансферные рейсы с пересадками",
                Selenide.title());
        //б) на странице есть логотип Победы.
        Assertions.assertTrue(mainPage.logoVisibility(), "Логотип не отображен");
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
        Assertions.assertTrue(borderColor.contains("213"));
    }

    @Test
    @Feature("Тест №3. Результаты поиска")
    @Description("Проверка открытия сайта и отображения полей в блоке «Управление бронированием». Поиск по номеру " +
            "заказа и фамилии клиента. Проверка отображения ошибки в новой вкладке при неуспешном поиске")
    void searchResultsTest() {
        //2. Убедиться, что сайт открылся:
        //а) текст заголовка страницы: Авиакомпания «Победа» - купить билеты на самолёт дешево онлайн, прямые и трансферные рейсы;
        Assertions.assertEquals("Авиакомпания «Победа» - купить авиабилеты онлайн, дешёвые билеты на самолёт, прямые и трансферные рейсы с пересадками",
                Selenide.title());
        //б) на странице есть логотип Победы.
        Assertions.assertTrue(mainPage.logoVisibility(), "Логотип не отображен");
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
        //5. Ввести в поля ввода данные:
        //номер заказа – XXXXXX, фамилия – Qwerty
        //и нажать кнопку «Поиск».
        bookingManage.fillSearchForm("123456", "Qwerty");
        bookingManage.clickButtonSearch();
        //6. Убедиться, что в новой вкладке на экране отображается текст ошибки «Заказ с указанными параметрами не найден».
        errorPage.switchToNewTab();
        errorPage.clickCheckbox();
        errorPage.clickSearchButton();
        Assertions.assertEquals("Заказ с указанными параметрами не найден", errorPage.getErrorText());
    }

    @Test
    @Feature("Тест №4. Непроходящий тест")
    @Description("Тест, который упадет на проверке видимости заголовка «Подготовка к полету»")
    void errorTest() {
        Assertions.assertEquals("Авиакомпания «Победа» - купить авиабилеты онлайн, дешёвые билеты на самолёт, прямые и трансферные рейсы с пересадками",
                Selenide.title());
        Assertions.assertTrue(mainPage.logoVisibility(), "Логотип не отображен");
        mainPage.hoverInfoMenu();
        Assertions.assertFalse(popupInfo.isInfoFlightVisible()); //на этой строке тест упадет
    }

    @AfterEach
    @Step("Закрытие браузера")
    public void tearDown() {
        Selenide.closeWebDriver();
    }
}