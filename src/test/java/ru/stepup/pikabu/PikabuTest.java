package ru.stepup.pikabu;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@Slf4j
public class PikabuTest {

    WebDriver driver;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/ekolyshkina/Desktop/chromedriver-win64/chromedriver.exe");
        driver = new ChromeDriver();
        //1. Перейти на сайт «https://pikabu.ru/».
        driver.get("https://pikabu.ru/");
    }

    @Test
    public void pikabuAuthErrorTest()  {
        Assert.assertEquals(driver.getCurrentUrl(), "https://pikabu.ru/");

        //2. Убедиться, что заголовок сайта: «Горячее – самые интересные и обсуждаемые посты | Пикабу».
        Assert.assertEquals("Горячее – самые интересные и обсуждаемые посты | Пикабу", driver.getTitle());

        //3. Кликнуть на кнопку «Войти».
        driver.findElement(By.cssSelector("button.header-right-menu__login-button")).click();

        //4. Убедиться, что отображается модальное окно «Авторизация», отображаются поля «Логин» и «Пароль», отображается кнопка «Войти».
        driver.findElement(By.cssSelector("div.popup_auth-modal")).isDisplayed();
        driver.findElement(By.cssSelector("div.popup_auth-modal input[placeholder='Логин']")).isDisplayed();
        driver.findElement(By.cssSelector("div.popup_auth-modal input[placeholder='Пароль']")).isDisplayed();
        driver.findElement(By.cssSelector("div.popup_auth-modal div.auth__field_firstbtn button.button_success " +
                "span.button__title")).isDisplayed();

        //5. Ввести в поля данные в формате логин/пароль – Qwerty/Qwerty и нажать «Войти».
        driver.findElement(By.cssSelector("div.popup_auth-modal input[placeholder='Логин']")).sendKeys("Qwerty");
        driver.findElement(By.cssSelector("div.popup_auth-modal input[placeholder='Пароль']")).sendKeys("Qwerty");
        driver.findElement(By.cssSelector("div.popup_auth-modal div.auth__field_firstbtn button.button_success " +
                "span.button__title")).click();

        //Время прощелкать капчу, ожидание появления элемента с ошибкой
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.popup__content span.auth__error_top")));

        //6. Убедиться, что появилось сообщение об ошибке, и его текст: «Ошибка. Вы ввели неверные данные авторизации».
        driver.findElement(By.cssSelector("div.popup__content span.auth__error_top")).isDisplayed();
        String text = driver.findElement(By.cssSelector("div.popup__content span.auth__error_top")).getText();
        Assert.assertEquals("Ошибка. Вы ввели неверные данные авторизации", text);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
