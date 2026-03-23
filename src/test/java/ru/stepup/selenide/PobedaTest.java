package ru.stepup.selenide;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import static com.codeborne.selenide.Selenide.open;

@Slf4j
public class PobedaTest {
    ru.stepup.selenide.MainPage mainPage;
    PopupInfo popupInfo;
    TicketSearch ticketSearch;
    ru.stepup.selenide.BookingManage bookingManage;
    ru.stepup.selenide.ErrorPage errorPage;

    @BeforeEach
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
    @DisplayName("Тест №1. Всплывающее окно")
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
    @DisplayName("Тест №2. Инициирование поиска")
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
    @DisplayName("Тест №3. Результаты поиска")
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

    @AfterEach
    public void tearDown() {
        Selenide.closeWebDriver();
    }
}