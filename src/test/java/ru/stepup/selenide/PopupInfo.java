package ru.stepup.selenide;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$x;

public class PopupInfo {

    private final SelenideElement infoFlight = $x("//div/a[@href='/information#flight']");
    private final SelenideElement infoUseful = $x("//div/a[@href='/information#useful']");
    private final SelenideElement infoCompany = $x("//div/a[@href='/information#company']");

    public PopupInfo() {
    }

    public boolean isInfoFlightVisible() {
        return infoFlight.is(Condition.visible);
    }

    public boolean isInfoUsefulVisible() {
        return infoUseful.is(Condition.visible);
    }

    public boolean isInfoCompanyVisible() {
        return infoCompany.is(Condition.visible);
    }
}
