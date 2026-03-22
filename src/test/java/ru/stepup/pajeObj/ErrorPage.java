package ru.stepup.pajeObj;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.Set;

public class ErrorPage {
    private WebDriver driver;

    @FindBy(xpath = "//div[contains(text(), 'Заказ с указанными параметрами не найден')]")
    private WebElement errorText;

    @FindBy(xpath = "//input[@id='searchOrderAgreeChb']/following-sibling::span")
    private WebElement checkbox;

    @FindBy(xpath = "//button[contains(text(), 'Найти заказ')]")
    private WebElement searchButton;

    public ErrorPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    public void switchToNewTab(String window) {
        Set<String> allWindows = driver.getWindowHandles();
        for (String windowHandle : allWindows) {
            if (!windowHandle.equals(window)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
    }

    public String getErrorText() {
        return errorText.getText();
    }

    public void clickCheckbox() {
        checkbox.click();
    }

    public void clickSearchButton() {
        searchButton.click();
    }
}
