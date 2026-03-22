package ru.stepup.pajeObj;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TicketSearch {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//input[@placeholder='Откуда']")
    private WebElement placeFrom;

    @FindBy(xpath = "//input[@placeholder='Куда']")
    private WebElement placeTo;

    @FindBy(xpath = "//input[@placeholder='Туда']")
    private WebElement dateFrom;

    @FindBy(xpath = "//input[@placeholder='Обратно']")
    private WebElement dateTo;

    @FindBy(xpath = "//button/span[contains(text(), 'Поиск')]")
    private WebElement searchButton;

    @FindBy(xpath = "//div[contains(@class, 'dp-sgi8y9-root-suggestionName') and contains(text(), 'Москва')]")
    private WebElement placeFromSuggest;
    @FindBy(xpath = "//div[contains(@class, 'dp-sgi8y9-root-suggestionName') and contains(text(), 'Санкт-Петербург')]")
    private WebElement placeToSuggest;

    //@FindBy(xpath = "//div[@data-failed='true']")
    @FindBy(xpath = "//div[@class='dp-1dr6zbu-root' and @data-failed='true']")
    private WebElement failedDataField;


    public TicketSearch(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver,this);
    }

    public boolean isPlaceFromVisible() {
        return placeFrom.isDisplayed();
    }

    public boolean isPlaceToVisible() {
        return placeTo.isDisplayed();
    }

    public boolean isDateFromVisible() {
        return dateFrom.isDisplayed();
    }

    public boolean isDateToVisible() {
        return dateTo.isDisplayed();
    }

    public void scrollToSearchBlock() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", placeFrom);
    }

    public void fillSearchForm(String from, String to) {
        wait.until(ExpectedConditions.elementToBeClickable(placeFrom)).click();
        placeFrom.sendKeys(from);

        WebElement fromCitySuggest = wait.until(ExpectedConditions.elementToBeClickable(placeFromSuggest));
        fromCitySuggest.click();

        wait.until(ExpectedConditions.elementToBeClickable(placeTo)).click();
        placeTo.sendKeys(to);

        WebElement toCitySuggest = wait.until(ExpectedConditions.elementToBeClickable(placeToSuggest));
        toCitySuggest.click();
    }

    public void clickSearchButton() {
        searchButton.click();
    }

    public String getFailDataBorderColor() {
        wait.until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver input) {
                String currentColor = failedDataField.getCssValue("border-bottom-color");
                return currentColor.contains("213");
            }
        });
        return failedDataField.getCssValue("border-bottom-color");
    }
}
