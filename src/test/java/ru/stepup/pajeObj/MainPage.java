package ru.stepup.pajeObj;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class MainPage {

    WebDriver driver;

    @FindBy(xpath = "/html/head/title")
    WebElement title;

    @FindBy(xpath = "//header/div/a/img")
    WebElement logo;

    @FindBy(xpath = "//div/a[@href='/information']")
    WebElement info;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    public String getMainTitle() {
        return driver.getTitle();
    }

    public boolean logoVisability() {
        return logo.isDisplayed();
    }

    public void hoverInfoMenu() {
        Actions actions = new Actions(driver);
        actions.moveToElement(info).perform();
    }
}
