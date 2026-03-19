package ru.stepup.pajeObj;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PopupInfo {

    @FindBy(xpath = "//div/a[@href='/information#flight']")
    private WebElement infoFlight;

    @FindBy(xpath = "//div/a[@href='/information#useful']")
    private WebElement infoUseful;

    @FindBy(xpath = "//div/a[@href='/information#company']")
    private WebElement infoCompany;

    public PopupInfo(WebDriver driver) {
        PageFactory.initElements(driver,this);
    }

    public boolean isInfoFlightVisible() {
        return infoFlight.isDisplayed();
    }

    public boolean isInfoUsefulVisible() {
        return infoUseful.isDisplayed();
    }

    public boolean isInfoCompanyVisible() {
        return infoCompany.isDisplayed();
    }



}
