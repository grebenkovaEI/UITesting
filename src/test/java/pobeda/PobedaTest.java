package pobeda;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@Slf4j
public class PobedaTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeEach
    void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/ekolyshkina/Desktop/chromedriver-win64/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.manage().window().setSize(new Dimension(1466, 768));
        //1. Открыть сайт google.com
        driver.get("https://www.google.com/");
    }

    @Test
    void pobedaTest() {
        //и ввести в строку поиска «Сайт компании Победа», после чего нажать Enter.
        driver.findElement(By.cssSelector("[aria-label=\"Найти\"]")).click();
        driver.findElement(By.cssSelector("[aria-label=\"Найти\"]")).sendKeys("Сайт компании Победа");
        driver.findElement(By.cssSelector("[aria-label=\"Найти\"]")).sendKeys(Keys.ENTER);

        //2. Дождаться прогрузки страницы с результатами поиска, после чего кликнуть на первую ссылку (https://www.pobeda.aero/).
        driver.findElement(By.cssSelector("h3")).click();


        //3. Дождаться прогрузки страницы АК «Победа», после чего дождаться появления картинки с текстом
        // «Полетели в Калининград» и проверить, что текст на странице действительно совпадает с текстом «Полетели в Калининград».

//        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("button:nth-child(8) > div.dp-1aqo8nk-root-inner > div > div.dp-1ihjhh6-root"))));
//        driver.findElement(By.cssSelector("button:nth-child(8) > div.dp-1aqo8nk-root-inner > div > div.dp-1ihjhh6-root")).isDisplayed();
//        String text = driver.findElement(By.cssSelector("button:nth-child(8) > div.dp-1aqo8nk-root-inner > div > div.dp-1ihjhh6-root")).getText();
//        Assert.assertEquals("Полетели в Калининград!", text);

        boolean textFound = wait.until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver wb) {
                try {
                    WebElement element = wb.findElement(By.cssSelector("button:nth-child(8) > div.dp-1aqo8nk-root-inner > div > div.dp-1ihjhh6-root"));
                    return element.isDisplayed() && element.getText().contains("Полетели в Калининград");
                } catch (NoSuchElementException e) {
                    return false;
            }
        }});
        Assertions.assertTrue(textFound, "Текст не найден");

        //4. Кликнуть на переключатель языка, выбрать английский язык и убедиться, что на главной странице отображаются
        // тексты "Ticket search", "Online check-in", "Manage my booking"
        driver.findElement(By.cssSelector("button.dp-11x0mgu-root-root")).click();
        driver.findElement(By.cssSelector("div.dp-1g73gdz-root > div > div > div > div:nth-child(2)")).click();

        boolean checkText = wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("button:nth-child(1) > div > span:nth-child(2)"), "Ticket search"));
        Assertions.assertTrue(checkText, "Текст не найден");
        checkText = wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("button:nth-child(2) > div > span:nth-child(2)"), "Online check-in"));
        Assertions.assertTrue(checkText, "Текст не найден");
        checkText = wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("button:nth-child(3) > div > span:nth-child(2)"), "Manage my booking"));
        Assertions.assertTrue(checkText, "Текст не найден");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
