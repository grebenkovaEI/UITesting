package ru.stepup.pajeObj;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BookingManage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//button[contains(., 'Управление бронированием')]")
    private WebElement buttonBookingManage;

    @FindBy(xpath = "//input[@placeholder='Номер бронирования или билета']")
    private WebElement ticketOrOrderNumber;

    @FindBy(xpath = "//input[@placeholder='Фамилия клиента']")
    private WebElement clientLastName;

    @FindBy(xpath = "//button/span[contains(text(), 'Поиск')]")
    private WebElement buttonSearch;

    public BookingManage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver,this);
    }

    public boolean isTicketOrOrderNumberVisible() {
        wait.until(ExpectedConditions.visibilityOf(ticketOrOrderNumber));
        return ticketOrOrderNumber.isDisplayed();
    }

    public boolean isClientSurnameVisible() {
        return clientLastName.isDisplayed();
    }

    public boolean isButtonSearchVisible() {
        return buttonSearch.isDisplayed();
    }

    public void scrollToManageBlock() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", buttonBookingManage);
    }

    public void clickButtonSearch() {
        buttonSearch.click();
    }

    public void clickButtonBookingManage() {
        buttonBookingManage.click();
    }

    public void fillSearchForm(String orderNum, String clientSurname) {
        wait.until(ExpectedConditions.elementToBeClickable(ticketOrOrderNumber)).click();
        ticketOrOrderNumber.sendKeys(orderNum);

        wait.until(ExpectedConditions.elementToBeClickable(clientLastName)).click();
        clientLastName.sendKeys(clientSurname);
    }
}
